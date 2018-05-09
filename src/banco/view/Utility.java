/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.view;

import banco.Main;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility.switchScene();
 *
 * @author GUILHERME.BRISIDA
 */
public class Utility {

    public static <T> T switchScene(URL path) {

        Stage stage = Main.getMainStage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(path);

        try {
            Parent root = loader.load();
            stage.setScene(new Scene(root));

            return loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
