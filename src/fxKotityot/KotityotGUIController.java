package fxKotityot;

import javafx.application.Platform;
import javafx.fxml.FXML;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;

/**
 * @author Anniina
 * @version 19.1.2022
 *
 */
public class KotityotGUIController {
    @FXML
    private ListChooser<String> lcKayttajat;

    @FXML void handleAvaa() {
        avaaPaaikkuna();
        //avaa();
    }
    
        
    @FXML void handleAvaaAlkuikkuna() {
        avaaAlkuikkuna();
    }

        
    @FXML void handleAvaaApuja() {
        avustus();
    }

        
    @FXML void handleLopeta() {
        tallenna();
        Platform.exit();
    }

        
    @FXML void handleMuokkaaKayttajia() {
        muokkaaKayttajia();
    }

        
    @FXML void handleMuokkaaTehtavia() {
        muokkaaTehtavia();
    }

        
    @FXML void handleUusiKayttaja() {
        Dialogs.showMessageDialog("Ei osata vielä tehdä uusia käyttäjiä.");
    }

        
    @FXML void handleUusiTehtava() {
        KotityotUusiTehtavaController.aloita(null, "");
    }

    // ====================oma osuus======================================


    /*
     * Avaa alkuikkunan
     */
    private void avaaAlkuikkuna() {
        ModalController.showModal(KotityotGUIController.class.getResource(
                "KotityotAlkuikkunaView.fxml"), "Aloitus", null, "");
    }


    /*
     * Avaa pääikkunan
     */
    private void avaaPaaikkuna() {
        ModalController.showModal(KotityotGUIController.class.getResource(
                "KotityotPaaikkunaView.fxml"), "Kotityöt", null, "");
    }


    /*
     * Avaa käyttäjienmuokkaus-ikkunan
     */
    private void muokkaaKayttajia() {
        KotityotMuokkaaKayttajiaController.aloita(null, "");
    }


    private void muokkaaTehtavia() {
        KotityotMuokkaaTehtaviaController.aloita(null, "");
    }


    /**
     * @return palauttaa false, jos painaa "cancel" - ei vielä toimi oikein
     * 
     */
    public boolean avaa() {
        ModalController.showModal(KotityotGUIController.class.getResource(
                "KotityotAlkuikkunaView.fxml"), "Aloitus", null, "");
        return true;
    }


    /*
     * Tallennuksen toiminnallisuus
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
    }


    /*
     * Näytetään ohjelman suunnitelma erillisessä selaimessa
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI(
                    "https://tim.jyu.fi/view/kurssit/tie/ohj2/2022k/ht/aarnipex");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }

    }
}