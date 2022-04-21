package fxKotityot;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;
import kotitalous.SovittuTehtava;
import kotitalous.Tehtava;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;
import java.util.List;
import fi.jyu.mit.fxgui.CheckBoxChooser;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;

/**
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 *
 */
public class KotityotMuokkaaTehtaviaController
        implements ModalControllerInterface<Kotitalous> {

    @FXML private CheckBox cbIka;
    @FXML private CheckBoxChooser<Kayttaja> cbKayttajat;
    @FXML private CheckBox cbVapaat;
    @FXML private Slider sliderIka;
    @FXML private TextField textHaku;
    @FXML private StringGrid<Tehtava> tableTehtavat;

    @FXML void handleUusiKayttaja() {
        uusiKayttaja();
    }


    @FXML void handlePoistaTehtava() {
        poista();
    }


    @FXML void handleTallennaPoistu() {
        tallenna();
        ModalController.closeStage(textHaku);
    }


    @FXML void handleUusiTehtava() {
        uusi();
    }
    

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    
    @Override
    public Kotitalous getResult() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void setDefault(Kotitalous oletus) {
        this.ktalous = oletus;
        alusta();
        
    }


    // =========================================
    
    // private int ika = 0;
    // private String nimi = null;   
    private Kotitalous ktalous;
    private Tehtava aputehtava = new Tehtava();
    
    // TODO pitäisikö tehdä tänne stringGrid staattisena, jolloin voisi käyttää samaa GUIcontrollerin puolella?
    // TODO missä vaiheessa tallennus kun tehtävää muokkaa?
    // TODO tehtävien sopiminen (sovi tehtävä jollekin)
    // TODO jos tehtävää ei sovittu kellekään, olisiko "sovittu tehtävä" suunnattu käyttäjäid:lle nro -1 tms, joka ei ole kenenkään?

    private void alusta() {
        alustaKayttajat();
        alustaTehtavat();
        naytaTehtavat();
//        tableTehtavat.addSelectionListener(e -> naytaKayttaja()); TODO etsi vastaava, jotta voi vaihtaa sovittuja käyttäjiä
        tableTehtavat.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) muokkaa();
            else naytaKayttajat();
        });
    }
    
    private void alustaKayttajat() {
        cbKayttajat.clear();
        
        for (int i = 0; i < ktalous.getKayttajia(); i++) {
            Kayttaja kayttaja = ktalous.annaKayttaja(i);
            String nimi = kayttaja.getNimi();
            cbKayttajat.add(nimi, kayttaja);
        }
    }
    

    private void poista() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa tehtäviä.");
    }


    private void uusi() {
        Tehtava uusi = new Tehtava();
        uusi = KotityotTietueController.kysyTietue(null, uusi);
        if (uusi == null) return;

        uusi.rekisteroi();
        ktalous.lisaa(uusi);
        tallenna();
        naytaTehtava(uusi);
    }
    
    
    /**
     * Muokkaa StringGridissä valittuna olevaa tehtävää
     */
    private void muokkaa() {
        int r = tableTehtavat.getRowNr();
        if ( r < 0) return;     // esim otsikkorivi
        
        Tehtava teht = tableTehtavat.getObject();
        if (teht == null) return;
        
//        int k = tableTehtavat.getColumnNr()+teht.ekaKentta();
        
        try {
            teht = KotityotTietueController.kysyTietue(null, teht.clone());
            if (teht == null) return;
            ktalous.korvaaTaiLisaa(teht);
            naytaTehtavat();
            // tallenna() ???   //TODO mieti milloin tallennetaan
            tableTehtavat.selectRow(r);
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog("Ongelmia muuttamisessa: " + e.getMessage());
        }
    }
      
    
    private void tallenna() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa jokin virhe: " + e.getMessage());
        }
    }
     
    
    private void naytaTehtavat() { 
        tableTehtavat.clear();
        List<Tehtava> kaikki = ktalous.annaTehtavat();
        if (kaikki.size() == 0) return;
        
        for (Tehtava t : kaikki) {
            naytaTehtava(t);
        }
    }
    
    private void naytaTehtava(Tehtava t) {
        int kenttia = t.getKenttia();
        String[] rivi = new String[kenttia - t.ekaKentta()];
        for (int i = 0, k = t.ekaKentta(); k < kenttia; i++, k++) {
            rivi[i] = t.anna(k);
        }
        tableTehtavat.add(t, rivi);
    }
    
    
    /**
     * Alustaa harrastustaulukon otsikot
     */
    private void alustaTehtavat() {
        int eka = aputehtava.ekaKentta();
        int lkm = aputehtava.getKenttia();
        
        String[] otsikot = new String[lkm-eka];
        for (int i = 0, k = eka; k < lkm; i++, k++) {
            otsikot[i] = aputehtava.getKysymys(k);
        }
        
        tableTehtavat.initTable(otsikot);
        tableTehtavat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableTehtavat.setEditable(false);
        tableTehtavat.setPlaceholder(new Label("Ei vielä tehtäviä"));
        
        for (int i = eka; i < lkm; i++) {
            tableTehtavat.setColumnSortOrderNumber(i);
            tableTehtavat.setColumnWidth(i, 60);
        }
    }
    
    
    /**
     * Lisätään uusi käyttäjä
     */
    private void uusiKayttaja() {
        Kayttaja uusi = new Kayttaja();
        uusi = KotityotTietueController.kysyTietue(null, uusi);
        if (uusi == null) return;
        
        uusi.rekisteroi();
        ktalous.lisaa(uusi);
        tallenna();
        alustaKayttajat();
    }

    
    private void naytaKayttajat() {     //TODO ei ehkä näytä kaikkia!
        Tehtava t = tableTehtavat.getObject();
        List<SovittuTehtava> sovitut = ktalous.annaSovitutKayttajat(t);
        if (sovitut.size() == 0) return;
        
        for (SovittuTehtava st : sovitut) {
            naytaKayttaja(st);
        }
    }
    
    /**
     * Näyttää yksittäisen sovitun käyttäjän
     */
    private void naytaKayttaja(SovittuTehtava st) {
        try {
            Kayttaja k = ktalous.etsiKayttaja(st.getKid());
            int kID = k.getKid();
            cbKayttajat.clear();
            
            int index = 0;
            for (int i = 0; i < ktalous.getKayttajia(); i++) {
                Kayttaja kayttaja = ktalous.annaKayttaja(i);
                if (kID == kayttaja.getKid()) index = i;
                cbKayttajat.add(kayttaja.getNimi(), kayttaja);
            }
            cbKayttajat.setSelectedIndex(index);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Jotain meni pieleen: " + e.getMessage());
        }
        
    }
    
    
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static Kotitalous aloita(Stage modalityStage, Kotitalous oletus) {
        return ModalController.showModal(
                KotityotMuokkaaTehtaviaController.class
                        .getResource("KotityotMuokkaaTehtaviaView.fxml"),
                "Tehtävien muokkaus", modalityStage, oletus);
    }
}
