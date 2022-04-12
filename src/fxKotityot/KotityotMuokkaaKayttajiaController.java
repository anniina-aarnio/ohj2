package fxKotityot;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML private Label labelVirhe;

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
        kayttajaKohdalla = null;
        ModalController.closeStage(editNimi);
    }

    @FXML void handleUusi() {
        uusiKayttaja();
    }
    
    @FXML void handleUusiTehtava() {
        uusiTehtava();
    }
    
    @Override
    public Kayttaja getResult() {
        return kayttajaKohdalla;
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
        this.kayttajaKohdalla = oletus;         // TODO ei näytä edellisestä ikkunasta valittua käyttäjää ensimmäisenä
        naytaKayttaja();                        // johtunee tästä, kun tämä metodi yrittää näyttää klikatun..
    }
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
//        alusta();   
    }
    
    // =========================================
    private static Kotitalous ktalous;
    private Kayttaja kayttajaKohdalla;
    private TextField[] edits;
    
    /**
     * Alustaa kokonaisuuden....
     */
    private void alusta() {
        edits = new TextField[]{editNimi, editIka};

        lcKayttajat.clear(); //tyhjentää
        tayta();
        lcKayttajat.addSelectionListener(e -> naytaKayttaja());
        editNimi.setOnKeyReleased(e -> kasitteleMuutosKayttajaan(1, editNimi));
        editIka.setOnKeyReleased(e -> kasitteleMuutosKayttajaan(2, editIka));
    }
    
    /**
     * Käsitellään käyttäjään tullut muutos
     */
    private void kasitteleMuutosKayttajaan(@SuppressWarnings("unused") int k, TextField edit) {
        if (kayttajaKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        virhe = kayttajaKohdalla.setNimi(s);
        if (virhe == null) {
            naytaVirhe(virhe);
        } else {
            naytaVirhe(virhe);
        }
    }
    
    /**
     * Tekee virhelabelin virhemuotoon, jos virhe tapahtunut
     * @param virhe virheteksti
     */
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
    
    private void naytaKayttaja() {
        kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        edits[0].setText(kayttajaKohdalla.getNimi());
        edits[1].setText("" +kayttajaKohdalla.getIka());
    }

    
    private void tallennaKayttaja() {
        if (kayttajaKohdalla != null && kayttajaKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + e.getMessage());
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
    
    
    /**
     * Tyhjennetään tekstikentät
     * @param edits taulukko, jossa tyhjennettäviä tekstikenttiä
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits) {
            edit.setText("");
        }
    }
    
    
    /**
     * Hakee käyttäjien tiedot listaan
     * @param haettavaKayttaja etsittävä käyttäjä, joka aktivoidaan haun jälkeen
     */
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
        KotityotMuokkaaKayttajaaController.uusiKayttaja(null);
//        Kayttaja uusi = new Kayttaja();
//        uusi.rekisteroi(); // tätä ei kannata oikeasti tähän kohtaan laittaa, vaan vasta tallennuksen kohdalla...
//        uusi.taytaAadaTiedoilla();
//        ktalous.lisaa(uusi);
//        
//        hae(uusi);
    }
    
    
    private void uusiTehtava() {        // tämä pois tästä kunhan muu toimii
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
    
    
    /** 
     * Luodaan käyttäjän muokkaus-dialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä käyttäjää käytetään oletuksena
     * @return null, jos painetaan Cancel, muuten viimeiseksi täytetty käyttäjä
     */
    public static Kayttaja kysyKayttaja(Stage modalityStage, Kayttaja oletus) {
        return ModalController.showModal(
                KotityotMuokkaaKayttajiaController.class.getResource("KotityotMuokkaaKayttajiaView.fxml"),
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
