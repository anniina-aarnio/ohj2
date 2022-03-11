package fxKotityot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
        implements ModalControllerInterface<String> {

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

    // =========================================
    
    // private int ika = 0;
    // private String nimi = null;


    private void poista() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa tehtäviä.");
    }


    private void muokkaa() {
        KotityotMuokkaaKayttajiaController.aloita(null, "");
    }


    private static void uusi() {
        KotityotUusiTehtavaController.aloita(null, "");
    }


    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }


    /**
     * Mitä tehdään, kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        //textNimi.requestFocus();
    }


    @Override
    public void setDefault(String oletus) {
        //textNimi.setText(oletus);

    }

    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String aloita(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                KotityotMuokkaaTehtaviaController.class
                        .getResource("KotityotMuokkaaTehtaviaView.fxml"),
                "Tehtävien muokkaus", modalityStage, oletus);
    }

}
