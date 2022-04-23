package fxKotityot;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kanta.Tietue;

/**
 * Kysytään tietueen tiedot luomalla sille uusi dialogi
 * @author Anniina Aarnio anniina.p.e.aarnio@student.jyu.fi
 * @version 21.4.2022
 * @param <TYPE> minkä tyyppisiä olioita käsitellään
 */
public class KotityotTietueController<TYPE extends Tietue> implements ModalControllerInterface<TYPE>, Initializable {

    @FXML private GridPane gridTietue;
    @FXML private ScrollPane panelTietue;
    @FXML private Label labelOtsikko;
    @FXML private Label labelVirhe;
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // setDefault hoitaa alustuksen
    }
    
    
    @Override
    public TYPE getResult() {
        return tietueKohdalla;
    }

    @Override
    public void handleShown() {
//        kentta = Math.max(apujasen.ekaKentta(), Math.min(kentta, apujasen.getKenttia() -1));
//        edits[kentta].requestFocus();       // tarvitaanko tätä? ei ehkä
        
    }

    @Override
    public void setDefault(TYPE oletus) {
        this.tietueKohdalla = oletus;
        
        // Ruksi pois käytöstä, koska se ei palauttanut null vaan virheellisen tietueen (esim. käyttäjän ilman nimeä)
        ModalController.getStage(labelOtsikko).setOnCloseRequest(event -> event.consume());
        
        alusta();
        naytaTietue(edits, tietueKohdalla);
    }

    
    @FXML private void handleOK() {
//        if (tietueKohdalla.onkoVirheita() == true) {    // pitäisi tarkistaa editsit...
//            naytaVirhe("Virheitä kentissä, korjaa");    //
//            return;
//        }
        if (tietueKohdalla != null && tietueKohdalla.anna(tietueKohdalla.ekaKentta()).trim().equals("")) { 
            kasitteleMuutosTietueeseen(edits[tietueKohdalla.ekaKentta()]);
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleCancel() { 
        tietueKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    //=============================================================

    private TYPE tietueKohdalla;
    private TextField[] edits;
    
    
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param obj tutkittava komponentti
     * @param oletus mikä arvo, jos id ei ole kunnollinen
     * @return komponentin id lukuna
     */
    public static int getFieldId(Object obj, int oletus) {
        if (!(obj instanceof Node)) return oletus;
        Node node = (Node) obj;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
    
    
    /**
     * Luodaan GridPaneen tietueen tiedot
     * @param gridTietue mihin tiedot luodaan
     * @param aputietue malli josta tiedot otetaan
     * @return luodut tekstikentät
     */
    public static<TYPE extends Tietue> TextField[] luoKentat(GridPane gridTietue, TYPE aputietue) {
        gridTietue.getChildren().clear();
        TextField[] edits = new TextField[aputietue.getKenttia()];
        
        for (int i = 0, k=aputietue.ekaKentta(); k < aputietue.getKenttia(); k++, i++) {
            Label label = new Label(aputietue.getKysymys(k));
            gridTietue.add(label,  0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridTietue.add(edit,  1, i);
        }
        return edits;
    }
    
    
    private void alusta() {
        this.edits = luoKentat(gridTietue, tietueKohdalla);
        for (TextField edit : edits) {
            if (edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutosTietueeseen((TextField)(e.getSource())));
            panelTietue.setFitToHeight(true);
        }
    }

    
    /**
     * Käsitellään tietueeseen tullut muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosTietueeseen(TextField edit) {
        if (tietueKohdalla == null) return;
        String s = edit.getText();
        int k = getFieldId(edit, tietueKohdalla.ekaKentta());
        String virhe = tietueKohdalla.aseta(k, s);
        
        if (virhe != null) {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
//    public void tarkistaTietueet() {      TODO tee for loop jossa tarkistat kaikkien tietueiden virheet
//        for (int i = 0; thi) {
//            
//        }
//    }
    
    
    /**
     * Näytetään tietuuen tiedot TextField-komponentteihin
     * @param edits taulukko TextFieldeistä johon näytetään
     * @param tietue näytettävä tietue
     */
    public static void naytaTietue(TextField[] edits, Tietue tietue) {
        if (tietue == null) return;
        for (int k = tietue.ekaKentta(); k < tietue.getKenttia(); k++) {
            edits[k].setText(tietue.anna(k));
        }
    }
    
    
    /**
     * Muokkaa virheilmoituksen tyyliä ja tekstiä
     * riippuen annetusta merkkijonosta
     * @param virhe jos null, ei virhettä, jos teksti, on virhe, joka näytetään
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
    
    
    /**
     * Laitetaan sopiva otsikko
     * @param otsikko tietojen kysymislaatikoiden yläpuolelle otsikko
     */
    public void setOtsikko(String otsikko) {
        this.labelOtsikko.setText(otsikko);
    }
    
    
    /**
     * Luodaan tietueen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä dataa näytetään oletuksena
     * @param otsikko mikä otsikko laitetaan kyselyn ylle
     * @return null, jos painetaan cancel, muuten täytetty tietue
     */
    public static<TYPE extends Tietue> TYPE kysyTietue(Stage modalityStage, TYPE oletus, String otsikko) {
        return ModalController.<TYPE, KotityotTietueController<TYPE>>showModal(
                KotityotTietueController.class.getResource("KotityotTietueView.fxml"), "Kotityöt",
                modalityStage, oletus, ctrl -> {ctrl.setOtsikko(otsikko);} );
                //TODO lisää otsikko, jolla saa muutettua labelOtsikko toivotuksi, kun nyt kaikki on "Uusi käyttäjä" vaikka kyseessä olisi tehtävä
    }
}
