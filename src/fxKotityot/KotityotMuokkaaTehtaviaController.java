package fxKotityot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotitalous.Kayttaja;
import kotitalous.Kotitalous;
import kotitalous.SailoException;
import kotitalous.SovittuTehtava;
import kotitalous.Tehtava;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.fxgui.StringGrid;

import java.util.ArrayList;
import java.util.List;
import fi.jyu.mit.fxgui.CheckBoxChooser;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;

/**
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 *
 */
public class KotityotMuokkaaTehtaviaController
        implements ModalControllerInterface<Kotitalous> {

    @FXML private CheckBox cbIka;
    @FXML private CheckBoxChooser<Kayttaja> cbKayttajat;
    @FXML private CheckBox cbVapaat;
    @FXML private Slider sliderIka;
    @FXML private TextField textHaku;
    @FXML private StringGrid<Tehtava> tableTehtavat;

    @FXML void handleUusiKayttaja() {
        uusiKayttaja();
    }

    @FXML void handleHakuehto() {
        hae();
    }

    @FXML void handlePoistaTehtava() {
        poista();
    }


    @FXML void handleTallennaPoistu() {
        tallenna();
        ModalController.closeStage(textHaku);
    }


    @FXML void handleUusiTehtava() {
        uusi();
    }
    

    @Override
    public void handleShown() {
        // TODO Tarvitaanko jotain tähän
        
    }

    
    @Override
    public Kotitalous getResult() {
        // palauttaa null, koska ei tarvitse palauttaa muutakaan
        return null;
    }


    @Override
    public void setDefault(Kotitalous oletus) {
        this.ktalous = oletus;
        alusta();
        
    }


    // =========================================
    
    // private int ika = 0;
    // private String nimi = null;   
    private Kotitalous ktalous;
    private Tehtava aputehtava = new Tehtava();
    private Tehtava valittuTehtava;
    
    // pitäisikö tehdä tänne stringGrid staattisena, jolloin voisi käyttää samaa GUIcontrollerin puolella?

    private void alusta() {
        alustaKayttajat();
        alustaTehtavat();
        naytaTehtavat();
        tableTehtavat.setOnMouseClicked(e -> {
            // tuplaklikkauksella muokkaamaan tehtävää
            if (e.getClickCount() > 1) muokkaa();
            // muuten näyttää kyseiselle tehtävälle valitut käyttäjät
            this.valittuTehtava = tableTehtavat.getObject();   // TODO käyttäjien näyttäminen myös kun focus vaihtuu esim nuolinäppäimillä
            naytaKayttajat();
        });
        cbKayttajat.addSelectionListener(e -> muokkaaSovittua());
        
        // vapaana olevien tehtävien napin toiminta -- ei refreshaa jos valittuna ja lisää käyttäjiä tehtävälle ja vaihtaa tehtävää, pitäisikö olla?
        cbVapaat.setOnAction(e -> rajaaVapaat());
    }
    
    private void alustaKayttajat() {
        cbKayttajat.clear();
        
        for (int i = 0; i < ktalous.getKayttajia(); i++) {
            Kayttaja kayttaja = ktalous.annaKayttaja(i);
            String nimi = kayttaja.getNimi();
            cbKayttajat.add(nimi, kayttaja);
        }
    }
    

    private void poista() {
        if (!Dialogs.showQuestionDialog("Tehtävän poisto", "Poistetaanko tehtävä " + valittuTehtava.getNimi(), "Poista", "Peruuta")) return;
        ktalous.poista(valittuTehtava);
    }


    private void uusi() {
        Tehtava uusi = new Tehtava();
        uusi = KotityotTietueController.kysyTietue(null, uusi, "Uusi tehtävä");
        if (uusi == null) return;

        uusi.rekisteroi();
        ktalous.lisaa(uusi);
        tallenna();
        naytaTehtava(uusi);
    }
    
    
    /**
     * Muokkaa StringGridissä valittuna olevaa tehtävää
     */
    private void muokkaa() {
        int r = tableTehtavat.getRowNr();
        if ( r < 0) return;     // esim otsikkorivi
        
        if (this.valittuTehtava == null) return;
        
//        int k = tableTehtavat.getColumnNr()+teht.ekaKentta();
        
        try {
            this.valittuTehtava = KotityotTietueController.kysyTietue(null, this.valittuTehtava.clone(), "Muokkaa tehtävää");
            if (this.valittuTehtava == null) return;
            ktalous.korvaaTaiLisaa(this.valittuTehtava);
            naytaTehtavat();
            // tallenna() ???   //TODO mieti milloin tallennetaan
            tableTehtavat.selectRow(r);
        } catch (CloneNotSupportedException e) {
            Dialogs.showMessageDialog("Ongelmia muuttamisessa: " + e.getMessage());
        }
    }
    
    
    /**
     * Kun cbKayttajiin tulee muutos, tarkistaa valitun tehtävän tableTehtavista
     * Tarkistaa tehtävälle sovitut käyttäjät
     * Lisää uudet valinnat ja poistaa pois-valitut (SovittuTehtava-luokan avulla)
     */
    private void muokkaaSovittua() {
        if (this.valittuTehtava == null) return;
        
        // tähän tieto onko valittu vai otettu pois, toistaiseksi kaikki muutokset lisää..?
        kayKayttajatLapi();
       
        tallenna();
    }
    
    
    /**
     * Käy käyttäjät läpi siten, että jos tehtävällä ei ollut tekijää
     * nyt lisää uudet valitut. Ei vielä osaa poistaa (eli jos check lähtee pois,
     * jää edelleen sovittutehtävä listoille)
     */
    private void kayKayttajatLapi() {
        
        List<Kayttaja> valitut = cbKayttajat.getSelectedObjects();
        // lisää tähän voisi lisätä, että jos valitut tyhjä niin voi poistaa kaikki sovitut ja return
        
        // otetaan listat kaikista (nimellä eiValitut) ja tehtävälle sovituista käyttäjistä
        List<Kayttaja> eiValitut = cbKayttajat.getObjects();  // tässä vaiheessa kaikki...
        List<SovittuTehtava> sovitut = ktalous.annaSovitutKayttajat(this.valittuTehtava);
             
        // tekijät on lista sovittujen käyttäjien id:stä, lisätään sovittujen listasta
        ArrayList<Integer> tekijat = new ArrayList<Integer>();
        for (SovittuTehtava st : sovitut) {
            tekijat.add(st.getKid());
        }
        
        // käydään valittuina olevat läpi
        for (Kayttaja kayt : valitut) {
            // poistetaan kaikista jos on valittu, jolloin lopuksi saadaan puhdas eiValitut-lista
            eiValitut.remove(kayt);
            
            // jos tekijöissä on jo sama käyttäjä-id, ei tarvitse muokata
            if (tekijat.contains(kayt.getKid())) continue;
            
            // luodaan uusi sovittutehtava ja asetetaan siihen tiedot, lisätään kotitalouteen
            SovittuTehtava st = new SovittuTehtava();
            st.setKayttaja(kayt);
            st.setTehtava(this.valittuTehtava);
            ktalous.lisaa(st);
        }
        
        // käydään ei-valitut läpi
        for (Kayttaja kayt : eiValitut) {
            // jos tekijöistä löytyy ei-valittu, poistetaan yhteys
            if (tekijat.contains(kayt.getKid()))
                ktalous.poistaSovittu(kayt, valittuTehtava);
        }
    }
      
    
    /**
     * Tallentaa tiedostoon muutokset
     */
    private void tallenna() {
        try {
            ktalous.tallenna();
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa jokin virhe: " + e.getMessage());
        }
    }
     
    
    /**
     * Käy läpi kaikki tehtävät ja näyttää ne oikein tableTehtävissä
     */
    private void naytaTehtavat() { 
        tableTehtavat.clear();
        List<Tehtava> kaikki = ktalous.annaTehtavat();
        if (kaikki.size() == 0) return;
        
        for (Tehtava t : kaikki) {
            naytaTehtava(t);
        }
    }
    
    
    private void naytaValitutTehtavat(List<Tehtava> valitut) {
        tableTehtavat.clear();
        if (valitut.size() == 0) return;
        
        for (Tehtava t : valitut) {
            naytaTehtava(t);
        }
    }
    
    /**
     * Näyttää tehtävän tableTehtavat:ssa oikein
     * @param t näytettävä tehtävä
     */
    private void naytaTehtava(Tehtava t) {
        int kenttia = t.getKenttia();
        String[] rivi = new String[kenttia - t.ekaKentta()];
        for (int i = 0, k = t.ekaKentta(); k < kenttia; i++, k++) {
            rivi[i] = t.anna(k);
        }
        tableTehtavat.add(t, rivi);
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
     * Etsii tehtävät, joilla ei ole vielä tekijää ja näyttää vain ne
     */
    private void rajaaVapaat() {
        if (cbVapaat.isSelected()) {
            List<Tehtava> vapaat = ktalous.annaVapaatTehtavat();
            naytaValitutTehtavat(vapaat);
        } else {
            naytaTehtavat();
        }
    }
    
    
    private void hae() {    // TODO tulee vain tyhjä lista ulos
        String ehto = textHaku.getText();
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        List<Tehtava> haetut = ktalous.annaTehtavatHakuehdolla(ehto);
        naytaValitutTehtavat(haetut);
        
    }
    
    /**
     * Lisätään uusi käyttäjä
     */
    private void uusiKayttaja() {
        Kayttaja uusi = new Kayttaja();
        uusi = KotityotTietueController.kysyTietue(null, uusi, "Uusi käyttäjä");
        if (uusi == null) return;
        
        uusi.rekisteroi();
        ktalous.lisaa(uusi);
        tallenna();
        alustaKayttajat();
    }

    
    private void naytaKayttajat() { 
        if (this.valittuTehtava == null) return;
        List<SovittuTehtava> sovitut = ktalous.annaSovitutKayttajat(this.valittuTehtava);
        if (sovitut.size() == 0) {
            alustaKayttajat();
            return;
        }
        
        List<Boolean> valinnat = new ArrayList<>();
        for (SovittuTehtava st : sovitut) {
            naytaKayttaja(st, valinnat);
        }
        cbKayttajat.setSelectedIndices(valinnat);
    }
    
    /**
     * Näyttää yksittäisen sovitun käyttäjän
     */
    private void naytaKayttaja(SovittuTehtava st, List<Boolean> valinnat) {
        try {
            Kayttaja k = ktalous.etsiKayttaja(st.getKid());
            int kID = k.getKid();
            cbKayttajat.clear();
            
            for (int i = 0; i < ktalous.getKayttajia(); i++) {
                Kayttaja kayttaja = ktalous.annaKayttaja(i);
                cbKayttajat.add(kayttaja.getNimi(), kayttaja);
                if (valinnat.size() < ktalous.getKayttajia()) valinnat.add(false);
                if (kID == kayttaja.getKid()) {
                    valinnat.set(i, true);
                }
            }
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Jotain meni pieleen: " + e.getMessage());
        }
        
    }
    
    
    /**
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä nimeä näytetään oletuksena
     * @return null, jos painetaan Cancel, muuten kirjoitettu nimi
     */
    public static Kotitalous aloita(Stage modalityStage, Kotitalous oletus) {
        return ModalController.showModal(
                KotityotMuokkaaTehtaviaController.class
                        .getResource("KotityotMuokkaaTehtaviaView.fxml"),
                "Tehtävien muokkaus", modalityStage, oletus);
    }
}
