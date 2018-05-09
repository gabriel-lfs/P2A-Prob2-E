/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.view;

import banco.model.Cliente;
import banco.model.ClientePessoaFisica;
import banco.model.ClientePessoaJuridica;
import banco.model.ContaCorrente;
import banco.model.CadastroException;
import banco.model.dao.DaoCliente;
import banco.model.dao.DaoConta;
import banco.model.rn.RnCliente;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

/**
 * FXML Controller class
 *
 * @author GUILHERME.BRISIDA
 */
public class CadastroController implements Initializable {

    private final ObservableList<ContaCorrente> contasParaRegistrar = FXCollections.observableArrayList();

    @FXML
    private Label lbServJms;
    @FXML
    private Label lbDocumento;

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfSenha;
    @FXML
    private PasswordField pfConfirma;

    @FXML
    private TextField tfDocumento;
    @FXML
    private TextField tfServJms;
    @FXML
    private TextField tfNome;
    @FXML
    private TextField tfTelefone;
    @FXML
    private TextField tfCelular;

    @FXML
    private RadioButton rbPessoaFisica;
    @FXML
    private RadioButton rbPessoaJuridica;

    @FXML
    private Button btSalvar;
    @FXML
    private Button btCancelar;
    @FXML
    private Button btNovaConta;

    @FXML
    private ListView<ContaCorrente> lvContas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Controle dos campos da interface
        tfServJms.visibleProperty().bind(rbPessoaJuridica.selectedProperty());
        tfServJms.editableProperty().bind(rbPessoaJuridica.selectedProperty());
        lbServJms.visibleProperty().bind(rbPessoaJuridica.selectedProperty());

        rbPessoaFisica.selectedProperty()
                .addListener((prop, old, newVal) -> {
                    lbDocumento.setText(newVal ? "CPF: " : "CNPJ: ");
                    tfDocumento.setText("");
                });

        btSalvar.setOnAction(this::salvar);
        btCancelar.setOnAction(this::cancelar);
        btNovaConta.setOnAction(this::novaConta);

        lvContas.setItems(contasParaRegistrar);
    }

    private void novaConta(ActionEvent evt) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Informe a agencia");
        dialog.setContentText("Número da agencia da conta:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ContaCorrente conta = DaoConta.novaConta(Integer.parseInt(result.get()));
            contasParaRegistrar.add(conta);
        }

    }

    private void salvar(ActionEvent evt) {
        evt.consume();

        //  telFixo, String email, String senha, String cpf
        // String email, String senha, String cnpj, String servidorJMS
        Cliente cliente;

        if (rbPessoaFisica.isSelected()) {
            cliente = new ClientePessoaFisica(
                    tfEmail.getText(),
                    pfSenha.getText(),
                    tfNome.getText(),
                    tfCelular.getText(),
                    tfTelefone.getText(),
                    tfDocumento.getText());
        } else {
            cliente = new ClientePessoaJuridica(
                    tfEmail.getText(),
                    pfSenha.getText(),
                    tfNome.getText(),
                    tfCelular.getText(),
                    tfTelefone.getText(),
                    tfDocumento.getText(),
                    tfServJms.getText());
        }

        try {

            RnCliente.validarCadastroCliente(
                    pfSenha.getText(),
                    pfConfirma.getText(),
                    contasParaRegistrar
            );

            // Adiciona as contas ao cliente
            contasParaRegistrar.forEach(conta -> {
                cliente.addConta(conta);
            });

            DaoCliente.salvarCliente(cliente);
            ControlarConta.abrir(cliente);

        } catch (CadastroException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Problema no cadastro");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void cancelar(ActionEvent evt) {
        evt.consume();
        Home.abrir();
    }

    public static void abrir() {
        Utility.switchScene(Home.class.getResource("/banco/view/fxml/VCadastro.fxml"));
    }

}
