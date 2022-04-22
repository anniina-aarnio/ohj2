package fxKotityot;

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

import java.util.ArrayList;
import java.util.List;
import fi.jyu.mit.fxgui.CheckBoxChooser;
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
        // TODO Tarvitaanko jotain tähän
        
    }

    
    @Override
    public Kotitalous getResult() {
        // palauttaa null, koska ei tarvitse palauttaa muutakaan
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
    private Tehtava valittuTehtava;
    
    // TODO pitäisikö tehdä tänne stringGrid staattisena, jolloin voisi käyttää samaa GUIcontrollerin puolella?
    // TODO missä vaiheessa tallennus kun tehtävää muokkaa?
    // TODO tehtävien sopiminen (sovi tehtävä jollekin)

    private void alusta() {
        alustaKayttajat();
        alustaTehtavat();
        naytaTehtavat();
//        tableTehtavat.addSelectionListener(e -> naytaKayttaja()); TODO etsi vastaava, jotta voi vaihtaa sovittuja käyttäjiä
        tableTehtavat.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) muokkaa();
            this.valittuTehtava = tableTehtavat.getObject();
            naytaKayttajat();
        });
        cbKayttajat.addSelectionListener(e -> muokkaaSovittua());
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
        
        if (this.valittuTehtava == null) return;
        
//        int k = tableTehtavat.getColumnNr()+teht.ekaKentta();
        
        try {
            this.valittuTehtava = KotityotTietueController.kysyTietue(null, this.valittuTehtava.clone());
            if (this.valittuTehtava == null) return;
            ktalous.korvaaTaiLisaa(this.valittuTehtava);
            naytaTehtavat();
            // tallenna() ???   //TODO mieti milloin tallennetaan
            tableTehtavat.selectRow(r);
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog("Ongelmia muuttamisessa: " + e.getMessage());
        }
    }
    
    
    /**
     * Kun cbKayttajiin tulee muutos, tarkistaa valitun tehtävän tableTehtavista
     * Tarkistaa tehtävälle sovitut käyttäjät
     * Lisää uudet valinnat ja poistaa pois-valitut (SovittuTehtava-luokan avulla)
     */
    private void muokkaaSovittua() {
        if (this.valittuTehtava == null) return;
        
        // tähän tieto onko valittu vai otettu pois, toistaiseksi kaikki muutokset lisää..?
        kayKayttajatLapi();
       
        tallenna();
    }
    
    
    private void kayKayttajatLapi() {       // TODO vain lisää, ei poista valintoja
        
//        List<Kayttaja> kaikki = cbKayttajat.getObjects();  // tämän avulla saisi kaikki käyttäjät, jolloin voisi vertailla...
        List<Kayttaja> valitut = cbKayttajat.getSelectedObjects();
        List<SovittuTehtava> sovitut = ktalous.annaSovitutKayttajat(this.valittuTehtava);
        
        ArrayList<Integer> tekijat = new ArrayList<Integer>();
        for (SovittuTehtava st : sovitut) {
            tekijat.add(st.getKid());
        }
        
        for (Kayttaja kayt : valitut) {
            if (tekijat.contains(kayt.getKid())) continue;
            SovittuTehtava st = new SovittuTehtava();
            st.setKayttaja(kayt);
            st.setTehtava(this.valittuTehtava);
            ktalous.lisaa(st);
        }
    }
      
    
    /**
     * Tallentaa tiedostoon muutokset
     */
    private void tallenna() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa jokin virhe: " + e.getMessage());
        }
    }
     
    
    /**
     * Käy läpi kaikki tehtävät ja näyttää ne oikein tableTehtävissä
     */
    private void naytaTehtavat() { 
        tableTehtavat.clear();
        List<Tehtava> kaikki = ktalous.annaTehtavat();
        if (kaikki.size() == 0) return;
        
        for (Tehtava t : kaikki) {
            naytaTehtava(t);
        }
    }
    
    
    /**
     * Näyttää tehtävän tableTehtavat:ssa oikein
     * @param t näytettävä tehtävä
     */
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
        if (this.valittuTehtava == null) return;
        List<SovittuTehtava> sovitut = ktalous.annaSovitutKayttajat(this.valittuTehtava);
        if (sovitut.size() == 0) {
            alustaKayttajat();
            return;
        }
        
        List<Boolean> valinnat = new ArrayList<>();
        for (SovittuTehtava st : sovitut) {
            naytaKayttaja(st, valinnat);
        }
        cbKayttajat.setSelectedIndices(valinnat);
    }
    
    /**
     * Näyttää yksittäisen sovitun käyttäjän
     */
    private void naytaKayttaja(SovittuTehtava st, List<Boolean> valinnat) {
        try {
            Kayttaja k = ktalous.etsiKayttaja(st.getKid());
            int kID = k.getKid();
            cbKayttajat.clear();
            
            for (int i = 0; i < ktalous.getKayttajia(); i++) {
                Kayttaja kayttaja = ktalous.annaKayttaja(i);
                cbKayttajat.add(kayttaja.getNimi(), kayttaja);
                if (valinnat.size() < ktalous.getKayttajia()) valinnat.add(false);
                if (kID == kayttaja.getKid()) {
                    valinnat.set(i, true);
                }
            }
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
