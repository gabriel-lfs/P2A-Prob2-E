/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.view;

import banco.model.Cliente;
import banco.model.dao.AutenticacaoException;
import banco.model.dao.DaoCliente;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author GUILHERME.BRISIDA
 */
public class Home implements Initializable {

    @FXML
    private TextField tfIdentifica;
    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btCadastrar;
    @FXML
    private Button btEntrar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Inicializando a interface
        btEntrar.setOnAction(this::entrar);
        btCadastrar.setOnAction(this::cadastro);

    }

    private void entrar(ActionEvent evt) {
        evt.consume();

        String login = tfIdentifica.getText();
        String senha = pfSenha.getText();

        try {
            Cliente cliente = DaoCliente.autenticarCliente(login, senha);

            // Abrir tela de cliente
            ControlarConta.abrir(cliente);

        } catch (AutenticacaoException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Problema de autenticação");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public static void abrir() {
        Utility.switchScene(Home.class.getResource("/banco/view/fxml/VHome.fxml"));
    }

    private void cadastro(ActionEvent evt) {
        evt.consume();
        CadastroController.abrir();
    }
}
