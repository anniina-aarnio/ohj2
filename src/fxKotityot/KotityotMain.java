package fxKotityot;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import kotitalous.Kotitalous;

/**
 * @author Anniina
 * @version 19.1.2022
 *
 */
public class KotityotMain extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KotityotPaaikkunaView.fxml"));
            final Pane root = ldr.load();
            final KotityotGUIController kotityotCtrl = (KotityotGUIController) ldr.getController();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kotityot.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kotityöt");
            
            Kotitalous ktalous = new Kotitalous();
            kotityotCtrl.setKotitalous(ktalous);
            
            primaryStage.show();
            
            if (!kotityotCtrl.avaa()) Platform.exit(); 
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Käynnistetään käyttöliittymä
     * @param args komentorivin parametrit
     */
    public static void main(String[] args) {
        launch(args);
    }
}