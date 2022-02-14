package fxKotityot;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;

/**
 * @author Anniina
 * @version 19.1.2022
 *
 */
public class KotityotGUIController {
    // FXML puoli

        @FXML void handleAvaa() {
            ModalController.showModal(KotityotGUIController.class.getResource("KotityotPaaikkunaView.fxml"), "Kotityöt", null, "");
            //avaa();
        }
    
        @FXML void handleAvaaAlkuikkuna() {
            ModalController.showModal(KotityotGUIController.class.getResource("KotityotAlkuikkunaView.fxml"), "Aloitus", null, "");
        }

        @FXML void handleAvaaApuja() {
            avustus();
        }

        @FXML void handleLopeta() {
            tallenna();
            Platform.exit();
        }

        @FXML void handleMuokkaaKayttajia() {
            ModalController.showModal(KotityotGUIController.class.getResource("KotityotMuokkaaKayttajiaView.fxml"), "Käyttäjien muokkaus", null, "");
        }

        @FXML void handleMuokkaaTehtavia() {
            ModalController.showModal(KotityotGUIController.class.getResource("KotityotMuokkaaTehtaviaView.fxml"), "Käyttäjien muokkaus", null, "");
        }

        @FXML void handleUusiKayttaja() {
            Dialogs.showMessageDialog("Ei osata vielä tehdä uusia käyttäjiä.");
        }

        @FXML void handleUusiTehtava() {
            Dialogs.showMessageDialog("Ei osata vielä tehdä uusia tehtäviä.");
        }

    // ====================oma osuus======================================
    
    /**
     * @return palauttaa false, jos painaa "cancel"
     * 
     */
    public boolean avaa() {
        KotityotAlkuikkunaController.kysyNimi(null, "testi");
//        if (uusinimi != null) return false;
//        lueTiedosto(uusinimi);
        return true;
    }
    
    private void tallenna() {
        // TODO Auto-generated method stub
        
    }
    
    /*
     * Näytetään ohjelman suunnitelma erillisessä selaimessa
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2022k/ht/aarnipex");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }

        
    }
}