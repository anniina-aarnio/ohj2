package fxKotityot;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fxKotityot.KotityotAlkuikkunaController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Anniina Aarnio
 * @version 12.2.2022
 *
 */
public class KotityotAlkuikkunaController implements ModalControllerInterface<String> {

    // FXML puoli
    
    @FXML private TextField textVastaus;
    
    @FXML private void handleAvaa() {
        ModalController.closeStage(textVastaus);
    }
    
    @FXML private void handleCancel() {
        ModalController.closeStage(textVastaus);
    }  
    
        
    // ====================oma osuus======================================
        

    @Override
    public String getResult() {
        return textVastaus.getText();
    }

    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        textVastaus.requestFocus();
        
    }

    @Override
    public void setDefault(String oletus) {
        textVastaus.setText(oletus);
        
    }
    
    /**
     * Luodaan nimenkysymisdialogi ja palautetaan siihen kirjoitettu nimi tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String kysyNimi(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                KotityotAlkuikkunaController.class.getResource("KotityotAlkuikkunaView.fxml"),
                "Kerho",
                modalityStage, oletus);
    }
}