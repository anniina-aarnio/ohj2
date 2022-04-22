package fxKotityot;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
 * Luokka käyttöliittymän tapahtumien hoitamiseksi
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
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
    
    @FXML void handleTulosta() {
        Dialogs.showMessageDialog("Ei onnistu tulostus, ei ehkä koskaan...");
    }

        
    @FXML void handleMuokkaaKayttajaa() {
        muokkaaKayttajaa();
    }
    
    @FXML void handlePoistaKayttaja() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa käyttäjää");
    }

        
    @FXML void handleMuokkaaTehtavia() {
        muokkaaTehtavia();
    }

        
    @FXML void handleUusiKayttaja() {
        uusiKayttaja();
    }
  
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    // ====================oma osuus======================================
    // TODO: kokonaiskeston laskenta kullekin käyttäjälle
    private Kotitalous ktalous;
    private Tehtava aputehtava = new Tehtava();
    
    
    /**
     * Alustaa kokonaisuuden....
     */
    private void alusta() {
        lcKayttajat.clear();
        alustaTehtavat();
        lcKayttajat.addSelectionListener(e -> naytaKayttaja());
        lcKayttajat.setOnMouseClicked(
                e -> {if ( e.getClickCount() > 1) muokkaaKayttajaa();}
                ); // voi muokata käyttäjää tuplaklikkauksella
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
     * Tallennuksen toiminnallisuus
     */
    private void tallenna() {
        try {
            ktalous.tallenna();
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

    
    /**
     * Laittaa hiirellä valitulle käyttäjälle sovitut tehtävät näkyviin
     */
    private void naytaKayttaja() {
        Kayttaja kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        
        naytaTehtavat(kayttajaKohdalla);
    }
    
    
    /** 
     * Hakee ja näyttää annetulle käyttäjälle sovitut tehtävät
     * @param kayttaja käyttäjä, jonka tehtävät näytetään
     */
    private void naytaTehtavat(Kayttaja kayttaja) {
        tableTehtavat.clear();
        if (kayttaja == null) return;
        
        List<SovittuTehtava> sovitut = ktalous.annaSovitutTehtavat(kayttaja);
        if (sovitut.size() == 0) return;
        
        for (SovittuTehtava st : sovitut) {
            naytaTehtava(st);
        }
    }
    
    
    /**
     * Näyttää yksittäisen sovitun tehtävän
     * @param st sovittu tehtävä
     */
    private void naytaTehtava(SovittuTehtava st) {
        try {
            Tehtava t = ktalous.etsiTehtava(st.getTid());
            int kenttia = t.getKenttia();
            String[] rivi = new String[kenttia - t.ekaKentta()];
            for (int i = 0, k = t.ekaKentta(); k < kenttia; i++, k++) {
                rivi[i] = t.anna(k);
            }
            tableTehtavat.add(t, rivi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Jotain meni pieleen: " + e.getMessage());
        }
    }
    
    
    /**
     * Alustaa harrastustaulukon otsikot
     */
    private void alustaTehtavat() {
        int eka = aputehtava.ekaKentta();
        int lkm = aputehtava.getKenttia();
        
        String[] otsikot = new String[lkm-eka];
        for (int i = 0, k = eka; k < lkm; i++, k++) {
            otsikot[i] = aputehtava.getKysymys(k);
        }
        
        tableTehtavat.initTable(otsikot);
        tableTehtavat.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableTehtavat.setEditable(false);
        tableTehtavat.setPlaceholder(new Label("Ei vielä tehtäviä"));
        
        for (int i = eka; i < lkm; i++) {
            tableTehtavat.setColumnSortOrderNumber(i);
            tableTehtavat.setColumnWidth(i, 60);
        }
    }
    
    
    /**
     * Lisätään uusi käyttäjä
     */
    private void uusiKayttaja() {
        Kayttaja uusi = new Kayttaja();
        uusi = KotityotTietueController.kysyTietue(null, uusi);
        if (uusi == null) return;
        
        uusi.rekisteroi();
        ktalous.lisaa(uusi);
        tallenna();
        hae(uusi.getKid());
    }

    
    /*
     * Avaa käyttäjienmuokkaus-ikkunan
     */
    private void muokkaaKayttajaa() {
        Kayttaja kayttajaKohdalla = lcKayttajat.getSelectedObject();
        if (kayttajaKohdalla == null) return;
        
        try {
            Kayttaja kayttaja = KotityotTietueController.kysyTietue(null, kayttajaKohdalla.clone());
            if (kayttaja == null) return;
            
            ktalous.korvaaTaiLisaa(kayttaja);
            tallenna();
            hae(kayttaja.getKid());
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog(e.getMessage());
        }
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


    private void muokkaaTehtavia() {
        KotityotMuokkaaTehtaviaController.aloita(null, ktalous);
        hae(0);
    }


    /**
     * @return palauttaa false, jos painaa "cancel" - ei vielä toimi oikein
     */
    public boolean avaa() {
        ModalController.showModal(KotityotGUIController.class.getResource(
                "KotityotAlkuikkunaView.fxml"), "Aloitus", null, "");
        lueTiedosto("kotitalous");
        return true;
    }

    
    /**
     * Asetetaan käytettävä kotitalous
     * @param kotitalous käytettävä kotitalous
     */
    public void setKotitalous(Kotitalous kotitalous) {
        this.ktalous = kotitalous;
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