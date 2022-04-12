package fxKotityot;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kotitalous.Kayttaja;

/**
 * Kontrolleri yhden käyttäjän tietojen muokkaamista
 * tai uuden käyttäjän tietojen luomista varten
 * @author Anniina
 * @version 12.4.2022
 */
public class KotityotMuokkaaKayttajaaController implements ModalControllerInterface<Kayttaja> {


        @FXML private Label labelOtsikko;
        @FXML private TextField textIka;
        @FXML private TextField textNimi;
        @FXML private Label labelVirhe;

        @FXML void handleCancel() {
            kayttajaKohdalla = null;
            ModalController.closeStage(textNimi);
        }

        @FXML void handleTallenna() {
            tallenna();
        }
        
        @Override
        public Kayttaja getResult() {
            return kayttajaKohdalla;
        }

        @Override
        public void handleShown() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void setDefault(Kayttaja kayttaja) {
            if (kayttaja != null) labelOtsikko.setText("Muokkaa käyttäjää");
            if (kayttaja == null) this.kayttajaKohdalla = new Kayttaja();
            else this.kayttajaKohdalla = kayttaja;
            naytaKayttaja(kayttajaKohdalla);
            alusta();
        }

        //=======================================================================
        
        private Kayttaja kayttajaKohdalla;
        
        private void alusta() {
            textNimi.setOnKeyReleased(e -> kasitteleNimi());
            textIka.setOnKeyReleased(e -> kasitteleIka());
        }
        
        
        private void kasitteleNimi() {
            if (kayttajaKohdalla == null) return;
            String s = textNimi.getText();
            String virhe = null;
            virhe = kayttajaKohdalla.setNimi(s);
            if (virhe == null) {
                naytaVirhe(virhe);
            } else {
                naytaVirhe(virhe);
            }
        }
        
        
        private void kasitteleIka() {
            if (kayttajaKohdalla == null) return;
            String s = textIka.getText();
            String virhe = null;
            virhe = kayttajaKohdalla.setIka(s);
            if (virhe == null) {
                naytaVirhe(virhe);
            } else {
                naytaVirhe(virhe);
            }
        }
        
        
        private void tallenna() {
            if (kayttajaKohdalla != null && kayttajaKohdalla.getNimi().trim().equals("")) {
                naytaVirhe("Nimi ei saa olla tyhjä");
                return;
            }
            // TODO ei vielä tallenna tietoja oikeasti
            ModalController.closeStage(labelVirhe);
        }
        

        private void naytaVirhe(String virhe) {
            if (virhe == null || virhe.isBlank()) {
                labelVirhe.setText("");
                labelVirhe.getStyleClass().removeAll("virhe");
                return;
            }
            labelVirhe.setText(virhe);
            labelVirhe.getStyleClass().add("virhe");
        }
        
        private void naytaKayttaja(Kayttaja kayttaja) {
            if (kayttaja == null) return;
            textNimi.setText(kayttaja.getNimi());
            textIka.setText("" + kayttaja.getIka());
        }
        
        
        /**
         * Luodaan käyttäjän tietojen kysymisdialogi ja palautetaan sama tietue muutettuna tai null
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @param oletus mitä dataa näytetään oletuksena
         * @return null, jos painetaan Cancel, muuten täytetty tietue
         */
        public static Kayttaja kysyKayttaja(Stage modalityStage, Kayttaja oletus) {
            return ModalController.showModal(KotityotMuokkaaKayttajaaController.class.getResource(
                    "KotityotMuokkaaKayttajaaView.fxml"), "Käyttäjän muokkaus", modalityStage, oletus);
        }
        
        
        /**
         * Luodaan uuden käyttäjän tietojen kysymysdialogi ja palautetaan sama tietue muutettuna tai null
         * @param modalityStage mille ollaan modaalisia, null = sovellukselle
         * @return null, jos painetaan Cancel, muuten täytetty tietue
         */
        public static Kayttaja uusiKayttaja(Stage modalityStage) {
            return ModalController.showModal(KotityotMuokkaaKayttajaaController.class.getResource(
                    "KotityotMuokkaaKayttajaaView.fxml"), "Uusi käyttäjä", modalityStage, null);
        }


}
