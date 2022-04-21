package fxKotityot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;
import kotitalous.Tehtava;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;

import java.util.List;

import fi.jyu.mit.fxgui.CheckBoxChooser;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;

/**
 * @author Anniina
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

    @FXML void handleMuokkaaKayttajia() {
        muokkaa();
        //TODO vaihda tämän tilalle "muokkaa tehtävää" ja sen mukaisesti ikkunat yms
        // Samalla edit-poisto string gridiin ehkä?
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

    private void alusta() {
        alustaKayttajat();
//        naytaTehtavatVANHA();
        alustaTehtavat();
        naytaTehtavat();
        tableTehtavat.setOnMouseClicked(e -> {if (e.getClickCount() > 1) muokkaa(); });
        // TODO hae ihmiset ja tehtävät
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
//        hae(uusi.getTid());
    }
    
    
    private void muokkaa() {
        int r = tableTehtavat.getRowNr();
        if ( r < 0) return;     // esim otsikkorivi
        
        Tehtava teht = tableTehtavat.getObject();
        if (teht == null) return;
        
        //int k = tableTehtavat.getColumnNr()+teht.ekaKentta();
        
        try {
            teht = KotityotTietueController.kysyTietue(null, teht.clone());
            if (teht == null) return;
            ktalous.korvaaTaiLisaa(teht);
            tableTehtavat.selectRow(r);
            naytaTehtava(teht);
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
     // pitäisi näyttää rivi[2] ja rivi[3] int että voisi katsoa suuruudet oikein jne...
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
        
        // TODO tee automaattisesti oikeankokoiseksi muuttuva alla olevasta
        tableTehtavat.setColumnSortOrderNumber(1);
        tableTehtavat.setColumnSortOrderNumber(2);
        tableTehtavat.setColumnSortOrderNumber(3);
        tableTehtavat.setColumnWidth(1, 60);
        tableTehtavat.setColumnWidth(2, 60);
        tableTehtavat.setColumnWidth(3, 60);
    }
    
    
    // TODO pitäisikö tehdä tänne stringGrid staattisena, jolloin voisi käyttää samaa GUIcontrollerin puolella?
    
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
