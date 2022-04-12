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

        @FXML void handleCancel() {
            ModalController.closeStage(textNimi);
        }

        @FXML void handleTallenna() {
            tallenna();
        }
        
        @Override
        public Kayttaja getResult() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void handleShown() {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void setDefault(Kayttaja kayttaja) {
            this.kayttajaKohdalla = kayttaja;
            naytaKayttaja(kayttajaKohdalla);
        }

        //=======================================================================
        
        private Kayttaja kayttajaKohdalla;
        
        
        private void tallenna() {
            //
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
         * @param oletus mitä dataa näytetään oletuksena
         * @return null, jos painetaan Cancel, muuten täytetty tietue
         */
        public static Kayttaja uusiKayttaja(Stage modalityStage, Kayttaja oletus) {
            return ModalController.showModal(KotityotMuokkaaKayttajaaController.class.getResource(
                    "KotityotMuokkaaKayttajaaView.fxml"), "Uusi käyttäjä", modalityStage, oletus);
        }


}
