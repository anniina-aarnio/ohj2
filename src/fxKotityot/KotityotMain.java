package fxKotityot;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Anniina
 * @version 19.1.2022
 *
 */
public class KotityotMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("KotityotharjGUIAlkuikkuna.fxml"));
            final Pane root = ldr.load();
            //final KotityotGUIController kotityotCtrl = (KotityotGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("kotityot.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Kotityot");
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei k�yt�ss�
     */
    public static void main(String[] args) {
        launch(args);
    }
}