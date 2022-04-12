package fxKotityot;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;
import kotitalous.SovittuTehtava;
import kotitalous.Tehtava;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;

/**
 * @author Anniina
 * @version 19.1.2022
 *
 */
public class KotityotGUIController implements Initializable {
    @FXML private ListChooser<Kayttaja> lcKayttajat;
    @FXML private StringGrid<Tehtava> tableTehtavat;

    @FXML void handleAvaa() {
        avaaPaaikkuna();
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

        
    @FXML void handleMuokkaaKayttajaa() {
        muokkaaKayttajaa();
    }

        
    @FXML void handleMuokkaaTehtavia() {
        muokkaaTehtavia();
    }

        
    @FXML void handleUusiKayttaja() {
        //Dialogs.showMessageDialog("Ei osata vielä tehdä uusia käyttäjiä.");
        uusiKayttaja();
    }

        
    @FXML void handleUusiTehtava() {
        //KotityotUusiTehtavaController.aloita(null, "");
        uusiTehtava(); // 5-vaihetta varten tehdään höpöversio
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
        lcKayttajat.addSelectionListener(e -> naytaKayttaja());
    }
    
    private void lueTiedosto(String nimi) {
        try {
            ktalous.lueTiedostosta(nimi);
            hae(0);
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
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

    
    private void naytaKayttaja() {
        Kayttaja kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        naytaTehtavat(kayttajaKohdalla);
    }
    
    
    private void naytaTehtavat(Kayttaja kayttaja) {
        tableTehtavat.clear();
        if (kayttaja == null) return;
        List<SovittuTehtava> sovitut = ktalous.annaSovitutTehtavat(kayttaja);
        if (sovitut.size() == 0) return;
        for (SovittuTehtava st : sovitut) {
            naytaTehtava(st);
        }
    }
    
    private void naytaTehtava(SovittuTehtava st) {
        try {
            Tehtava t = ktalous.etsiTehtava(st.getTid());
            String[] rivi = t.toString().split("\\|");
            tableTehtavat.add(t, rivi[1], rivi[2]);    //TODO korjaa joskus
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Jotain meni pieleen: " + e.getMessage());
        }
    }
    

    /*
     * Avaa käyttäjienmuokkaus-ikkunan
     */
    private void muokkaaKayttajaa() {
        Kayttaja kayttajaKohdalla = lcKayttajat.getSelectedObject();
//        KotityotMuokkaaKayttajiaController.setKotitalous(ktalous);
        KotityotMuokkaaKayttajaaController.kysyKayttaja(null, kayttajaKohdalla);
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
        lueTiedosto("kotitalous");
        return true;
    }


    /*
     * Tallennuksen toiminnallisuus
     */
    private void tallenna() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
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
        KotityotMuokkaaKayttajaaController.uusiKayttaja(null);
    }
    
    
    private void uusiTehtava() {
        Kayttaja kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        Tehtava teht = new Tehtava();
        teht.taytaImurointiTiedoilla();
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