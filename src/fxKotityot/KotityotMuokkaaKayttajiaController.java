package fxKotityot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * @author Anniina
 * @version 14.2.2022
 *
 */
public class KotityotMuokkaaKayttajiaController implements ModalControllerInterface<String> {

    @FXML private TextField textIka;

    @FXML private TextField textNimi;

    @FXML void handlePoista() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa käyttäjiä.");
    }

    @FXML void handleTallenna() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa muutoksia.");
        ModalController.closeStage(textNimi);
    }

    @FXML void handleUusi() {
        Dialogs.showMessageDialog("Ei osata vielä lisätä uusia käyttäjiä.");
    }

    // =========================================
    // TODO pitäisikö tehdä ns. muokkauscontroller, joka jakautuu sekä käyttäjien että tehtävien muokkauksen mukaan...
//    private int ika = 0;
//    private String nimi = null;
    
    
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
        textNimi.requestFocus();
    }

    
    @Override
    public void setDefault(String oletus) {
        textNimi.setText(oletus);
        
    }
    
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String aloita(Stage modalityStage, String oletus) {
        return ModalController.showModal(KotityotMuokkaaKayttajiaController.class.getResource("KotityotMuokkaaKayttajiaView.fxml"),
                "Käyttäjien muokkaus", modalityStage, oletus);
    }

}
