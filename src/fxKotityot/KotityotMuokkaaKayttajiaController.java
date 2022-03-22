package fxKotityot;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;
import kotitalous.SovittuTehtava;
import kotitalous.Tehtava;

import java.io.PrintStream;
import java.util.List;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.TextAreaOutputStream;

/**
 * @author Anniina
 * @version 14.2.2022
 *
 */
public class KotityotMuokkaaKayttajiaController implements ModalControllerInterface<String> {
    
    @FXML private ListChooser<Kayttaja> lcKayttajat;
    @FXML private ScrollPane panelKayttaja;

    @FXML private TextField textIka;

    @FXML private TextField textNimi;

    @FXML void handlePoista() {
        Dialogs.showMessageDialog("Ei osata vielä poistaa käyttäjiä.");
    }

    @FXML void handleTallenna() {
        tallenna();
    }

    @FXML void handleUusi() {
        //Dialogs.showMessageDialog("Ei osata vielä lisätä uusia käyttäjiä.");
        uusiKayttaja();
    }
    
    @FXML void handleUusiTehtava() {
        uusiTehtava();
    }

    // =========================================
    private static Kotitalous ktalous;
    private Kayttaja kayttajaKohdalla;
    private TextArea areaKayttaja = new TextArea();
    
    /**
     * Alustaa kokonaisuuden....
     */
    private void alusta() {
        avaa();
        panelKayttaja.setContent(areaKayttaja);
        areaKayttaja.setFont(new Font("Courier New", 12));
        panelKayttaja.setFitToHeight(true);
        
        lcKayttajat.clear(); //tyhjentää
        tayta();
        lcKayttajat.addSelectionListener(e -> naytaKayttaja());
    }
    
    private void naytaKayttaja() {
        kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        
        areaKayttaja.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKayttaja)) { //import printstream
          kayttajaKohdalla.tulosta(os);
          os.println("-- Tehtävät --");
          List<SovittuTehtava> sovitut = ktalous.annaSovitutTehtavat(kayttajaKohdalla);
          for (SovittuTehtava st : sovitut) {
              try {
                ktalous.etsiTehtava(st.getTid()).tulosta(os);
            } catch (SailoException e) {
                Dialogs.showMessageDialog(e.getMessage());
            }
          }
        }
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
        alusta();
        textNimi.requestFocus();
    }

    
    @Override
    public void setDefault(String oletus) {
        textNimi.setText(oletus);
        
    }
    
    
    private void tallenna() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
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
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static String aloita(Stage modalityStage, String oletus) {
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
