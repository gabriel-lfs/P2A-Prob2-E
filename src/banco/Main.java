/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author GUILHERME.BRISIDA
 */
public class Main extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        setMainStage(primaryStage);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/banco/view/fxml/VHome.fxml"));
        AnchorPane root = loader.load();

        primaryStage.setTitle("Aplicação bancaria");
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(370);
        primaryStage.setMinHeight(240);
        primaryStage.setScene(new Scene(root));

        primaryStage.show();
    }

    private static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
