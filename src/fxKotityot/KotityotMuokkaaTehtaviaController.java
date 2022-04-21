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
import fi.jyu.mit.fxgui.CheckBoxChooser;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

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

    private void alusta() {
        alustaKayttajat();
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


    private void muokkaa() {    // TODO ei tule toimimaan oikein
        Dialogs.showMessageDialog("Käyttäjien muokkaaminen ei vielä onnistu tästä ikkunasta");
        //TODO keksi ratkaisu, poistetaanko käyttäjien muokkaaminen vai miten...
    }


    private void uusi() {
        Tehtava uusi = new Tehtava();
        uusi = KotityotTietueController.kysyTietue(null, uusi);
        if (uusi == null) return;

        uusi.rekisteroi();
        ktalous.lisaa(uusi);
        tallenna();
        hae(uusi.getTid());
    }
    
    
    private void hae(@SuppressWarnings("unused") int id) {
        // TODO
    }
    
    private void tallenna() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa jokin virhe: " + e.getMessage());
        }
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
