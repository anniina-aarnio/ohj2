package fxKotityot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.Tehtava;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.CheckBoxChooser;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

/**
 * @author Anniina
 * @version 14.2.2022
 *
 */
public class KotityotMuokkaaTehtaviaController
        implements ModalControllerInterface<Kotitalous> {

    @FXML private CheckBox cbIka;
    @FXML private CheckBoxChooser<?> cbKayttajat;
    @FXML private CheckBox cbVapaat;
    @FXML private Slider sliderIka;
    @FXML private TextField textHaku;

    @FXML void handleMuokkaaKayttajia() {
        muokkaa();
    }


    @FXML void handlePoistaTehtava() {
        poista();
    }


    @FXML void handleTallennaPoistu() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa muutoksia.");
        ModalController.closeStage(textHaku);
    }


    @FXML void handleUusiTehtava() {
        uusi();
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
        // TODO hae ihmiset ja tehtävät
    }

    private void poista() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa tehtäviä.");
    }


    private void muokkaa() {    // TODO ei tule toimimaan oikein
        KotityotMuokkaaKayttajaaController.kysyKayttaja(null, null);    //TODO keksi ratkaisu
    }


    private void uusi() {
        Tehtava tehtavaKohdalla = null;
        
        try {
            Tehtava tehtava = KotityotTietueController.kysyTietue(null, tehtavaKohdalla.clone());
            if (tehtava == null) return;
            
            this.ktalous.korvaaTaiLisaa(tehtava);
            tallenna();
            hae(tehtava.getTid());
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    private void hae(@SuppressWarnings("unused") int id) {
        // TODO
    }
    
    private void tallenna() {
        // TODO
    }
    
    // TODO pitäisikö tehdä tänne stringGrid staattisena, jolloin voisi käyttää samaa GUIcontrollerin puolella?
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String aloita(Stage modalityStage, Kotitalous oletus) {
        return ModalController.showModal(
                KotityotMuokkaaTehtaviaController.class
                        .getResource("KotityotMuokkaaTehtaviaView.fxml"),
                "Tehtävien muokkaus", modalityStage, oletus);
    }


}
