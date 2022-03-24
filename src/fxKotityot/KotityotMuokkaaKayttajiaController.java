package fxKotityot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;
import kotitalous.SovittuTehtava;
import kotitalous.Tehtava;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;

/**
 * @author Anniina
 * @version 14.2.2022
 *
 */
public class KotityotMuokkaaKayttajiaController implements ModalControllerInterface<Kayttaja>, Initializable {
    
    @FXML private ListChooser<Kayttaja> lcKayttajat;
    @FXML private ScrollPane panelKayttaja;

    @FXML private TextField editIka;
    @FXML private TextField editNimi;

    @FXML void handlePoista() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa käyttäjiä.");
    }

    @FXML void handleTallennaKayttaja() {
        tallennaKayttaja();
    }
    
    @FXML
    void handleTallennaJaPoistu() {
        tallenna();
    }
    
    @FXML
    void handlePeruuta() {
        ModalController.closeStage(editNimi);
    }

    @FXML void handleUusi() {
        uusiKayttaja();
    }
    
    @FXML void handleUusiTehtava() {
        uusiTehtava();
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }
    
    @Override
    public Kayttaja getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Mitä tehdään, kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        alusta();
        editNimi.requestFocus();
    }

    
    @Override
    public void setDefault(Kayttaja oletus) {
        this.kayttajaKohdalla = oletus;
        naytaKayttaja();
    }
    
    
    // =========================================
    private static Kotitalous ktalous;
    private Kayttaja kayttajaKohdalla;
    private TextField[] edits;
    
//    private TextArea areaKayttaja = new TextArea(); VANHA JOSSA TEXTAREA
    
    /**
     * Alustaa kokonaisuuden....
     */
    private void alusta() {
//        avaa();
        edits = new TextField[]{editNimi, editIka};

        lcKayttajat.clear(); //tyhjentää
        tayta();
        lcKayttajat.addSelectionListener(e -> naytaKayttaja());
    }
    
    private void naytaKayttaja() {
        kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        edits[0].setText(kayttajaKohdalla.getNimi());
        edits[1].setText("" +kayttajaKohdalla.getIka());
    }

    private void tallennaKayttaja() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    private void tallenna() {
        tallennaKayttaja();
        ModalController.closeStage(lcKayttajat);
    }
    
 
    private void tayta() {
        for (int i = 0; i < ktalous.getKayttajia(); i++) {
            Kayttaja kayttaja = ktalous.annaKayttaja(i);
            lcKayttajat.add("" + kayttaja.getNimi(), kayttaja);
        }
    }
    
    private void hae(Kayttaja haettavaKayttaja) {
        lcKayttajat.clear();
        
        int haettavaId = haettavaKayttaja.getKid();
        
        int index = 0;
        for (int i = 0; i < ktalous.getKayttajia(); i++) {
            Kayttaja kayttaja = ktalous.annaKayttaja(i);
            if (kayttaja.getKid() == haettavaId) index = i;
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
        ktalous.lisaa(uusi);
        
        hae(uusi);
    }
    
    private void uusiTehtava() {
        Tehtava uusi = new Tehtava();
        uusi.rekisteroi();
        uusi.taytaImurointiTiedoilla();
        ktalous.lisaa(uusi);
        
        SovittuTehtava st = new SovittuTehtava();
        st.setKayttaja(kayttajaKohdalla);
        st.setTehtava(uusi);
        ktalous.lisaa(st);
        hae(kayttajaKohdalla);
    }
    
    
    private void lueTiedosto(String nimi) {
        try {
            ktalous.lueTiedostosta(nimi);
            hae(new Kayttaja());
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    /**
     * Lukee tiedoston "kotitalous"
     * @return true, jos onnistui, false jos ei
     */
    public boolean avaa() {
        lueTiedosto("kotitalous");
        return true;
    }
    
    
    /** 
     * Luodaan käyttäjän muokkaus-dialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä käyttäjää käytetään oletuksena
     * @return null, jos painetaan Cancel, muuten täytetty käyttäjä
     */
    public static Kayttaja kysyKayttaja(Stage modalityStage, Kayttaja oletus) {
        return ModalController.showModal(KotityotMuokkaaKayttajiaController.class.getResource("KotityotMuokkaaKayttajiaView.fxml"),
                "Käyttäjien muokkaus", modalityStage, oletus);
    }

    /**
     * Asettaa kotitalouden
     * @param kotitalous asetettava kotitalous
     */
    public static void setKotitalous(Kotitalous kotitalous) {
        ktalous = kotitalous;
    }
}
