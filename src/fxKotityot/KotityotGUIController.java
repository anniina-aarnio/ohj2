package fxKotityot;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;

/**
 * @author Anniina
 * @version 19.1.2022
 *
 */
public class KotityotGUIController implements Initializable {
    @FXML private ListChooser<Kayttaja> lcKayttajat;

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
        //Dialogs.showMessageDialog("Ei osata vielä tehdä uusia käyttäjiä.");
        uusiKayttaja();
    }

        
    @FXML void handleUusiTehtava() {
        KotityotUusiTehtavaController.aloita(null, "");
    }

    // ====================oma osuus======================================
    
    private Kotitalous ktalous;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }
    
    /**
     * Alustaa kokonaisuuden....
     */
    private void alusta() {
        lcKayttajat.clear(); //tyhjentää
        lcKayttajat.addSelectionListener(e -> naytaJasen()); // tätä ennen voisi olla esillä textArea, johon tulostetaan.. tee kayttajactrl:iin
    }
    
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

    
    private void naytaJasen() {
        Kayttaja kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        //areaJasen.setText("");
        //try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaJasen)) { //import printstream
        //  kayttajaKohdalla.tulosta(os);
        //}
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

    
    /**
     * Asetetaan käytettävä kotitalous
     * @param kotitalous käytettävä kotitalous
     */
    public void setKotitalous(Kotitalous kotitalous) {
        this.ktalous = kotitalous;
    }
    
    
    private void hae(int kayttajaId) {
        lcKayttajat.clear();
        
        int index = 0;
        for (int i = 0; i < ktalous.getKayttajia(); i++) {
            Kayttaja kayttaja = ktalous.annaKayttaja(i);
            if (kayttaja.getKid() == kayttajaId) index = i;
            lcKayttajat.add(""+kayttaja.getNimi(), kayttaja);
        }
        lcKayttajat.setSelectedIndex(index);
    }
    
    /**
     * Lisätään uusi käyttäjä
     */
    private void uusiKayttaja() {
        Kayttaja uusi = new Kayttaja();
        uusi.rekisteroi(); // tätä ei kannata oikeasti tähän kohtaan laittaa, vaan vasta tallennuksen kohdalla...
        uusi.taytaAadaTiedoilla();
        try {
            ktalous.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa: " + e.getMessage());
        }
        hae(uusi.getKid());
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