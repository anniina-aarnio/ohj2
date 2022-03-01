package fxKotityot;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Hoitaa uusien tehtävien (ja mahd. tehtävien muokkauksen) ikkunan
 * @author Anniina
 * @version 14.2.2022
 *
 */
public class KotityotUusiTehtavaController implements ModalControllerInterface<String> {

    @FXML private TextField textIka;

    
    @FXML private TextField textKesto;

    
    @FXML private TextField textNimi;
    
    
    @FXML private void handleCancel() {
        ModalController.closeStage(textNimi);
    }
    
    @FXML private void handleTallenna() {
        tallenna();
        ModalController.closeStage(textNimi);
    }

    
    // ===============================
    
    @Override
    public void handleShown() {
        textNimi.requestFocus();      
    }


    @Override
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub     
    }
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata vielä tallentaa muutoksia.");
    }
    

    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String aloita(Stage modalityStage, String oletus) {
        return ModalController.showModal(KotityotUusiTehtavaController.class.getResource("KotityotUusiTehtavaView.fxml"),
                "Uusi tehtävä", modalityStage, oletus);
    }
}
