/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.view;

import banco.model.Cliente;
import banco.model.ContaCorrente;
import banco.model.OpcoesNotificacao;
import banco.model.SAnaliseFluxoCaixa;
import banco.model.SAnaliseInvestimento;
import banco.model.SBaixaAutomatica;
import banco.model.SNotificacao;
import banco.model.SOfertaFinanceiro;
import banco.model.dao.DaoConta;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author GUILHERME.BRISIDA
 */
public class ControlarConta implements Initializable {

    private Cliente cliente;
    private final ObservableList<String> contas = FXCollections.observableArrayList();
    private final ObservableList<OpcoesNotificacao> opcoesNotificacao = FXCollections.observableArrayList();

    // ----------------
    @FXML
    private Label lbNumero;
    @FXML
    private Label lbSaldo;
    @FXML
    private Label lbCliente;
    @FXML
    private Label lbAgencia;

    @FXML
    private Button btSaque;
    @FXML
    private Button btDeposito;
    @FXML
    private Button btTransferencia;
    @FXML
    private Button btAjuda;
    @FXML
    private Button btLogout;

    @FXML
    private ChoiceBox<OpcoesNotificacao> chNotificacoes;
    @FXML
    private CheckBox cbBaixaAutomatica;
    @FXML
    private CheckBox cbAnaliseFluxo;
    @FXML
    private CheckBox cbAnaliseInvestimento;
    @FXML
    private CheckBox cbOferFinanceiro;

    @FXML
    private ChoiceBox<String> chConta;

    @FXML
    private ListView lvHistorico;

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;

        // Atualiza informações na interface
        this.lbCliente.setText(cliente.getNome());
        this.lbSaldo.setText("");
        this.lbNumero.setText("");

        // Contas disponiveis
        this.contas.setAll(cliente.listarContas());
        this.chConta.getSelectionModel().selectFirst();         // Deixa a primeira conta como selecionada

        // Opções de notificação
        this.opcoesNotificacao.setAll(SNotificacao.opcoesNotificacao(cliente));
        this.chNotificacoes.getSelectionModel().selectFirst();  // Deixa a primeira conta como selecionada
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Inicializando interface
        this.chConta.setItems(contas);
        this.chNotificacoes.setItems(opcoesNotificacao);

        // Limpa labels
        this.lbCliente.setText("");
        this.lbSaldo.setText("");
        this.lbNumero.setText("");

        // Ações dos botões
        this.btLogout.setOnAction(this::logout);
        this.btSaque.setOnAction(this::sacar);
        this.btDeposito.setOnAction(this::depositar);
        this.btTransferencia.setOnAction(this::transerir);

        // Eventos escutados
        this.chConta.getSelectionModel()
                .selectedItemProperty()
                .addListener((prop, old, newVal) -> {
                    if (newVal != null) {
                        ContaCorrente ccCliente = cliente.pegarContaCorrente(newVal);

                        this.lbSaldo.setText(String.format("%.04f", ccCliente.getSaldo()));
                        this.lbNumero.setText(String.valueOf(ccCliente.getNumero()));
                        this.lbAgencia.setText(String.valueOf(ccCliente.getAgencia()));

                        this.cbBaixaAutomatica.setSelected(
                                ccCliente.servicoAtivo(SBaixaAutomatica.getInstance()));

                        this.cbAnaliseFluxo.setSelected(
                                ccCliente.servicoAtivo(SAnaliseFluxoCaixa.getInstance()));

                        this.cbAnaliseInvestimento.setSelected(
                                ccCliente.servicoAtivo(SAnaliseFluxoCaixa.getInstance()));

                        this.cbOferFinanceiro.setSelected(
                                ccCliente.servicoAtivo(SAnaliseFluxoCaixa.getInstance()));

                        if (ccCliente.getOpcaoNotificacao().isPresent()) {
                            chNotificacoes.getSelectionModel()
                                    .select(ccCliente.getOpcaoNotificacao().get());
                        } else {
                            chNotificacoes.getSelectionModel().selectFirst();
                        }
                    }
                });

        this.chNotificacoes.getSelectionModel().selectedItemProperty()
                .addListener((prop, oldVal, newVal) -> {
                    ContaCorrente ccCliente = cliente.pegarContaCorrente(this.chConta.getValue());

                    if (newVal == OpcoesNotificacao.INATIVO) {
                        ccCliente.setOpcaoNotificacao(null);
                        ccCliente.desativarServico(SNotificacao.getInstance());
                    } else {
                        ccCliente.setOpcaoNotificacao(newVal);
                        ccCliente.registraServico(SNotificacao.getInstance());
                    }
                });

        this.cbBaixaAutomatica.selectedProperty()
                .addListener((prop, oldVal, newVal) -> {
                    ContaCorrente ccCliente = cliente.pegarContaCorrente(this.chConta.getValue());
                    if (newVal) {
                        ccCliente.registraServico(SBaixaAutomatica.getInstance());
                    } else {
                        ccCliente.desativarServico(SBaixaAutomatica.getInstance());
                    }
                });

        this.cbAnaliseFluxo.selectedProperty()
                .addListener((prop, oldVal, newVal) -> {
                    ContaCorrente ccCliente = cliente.pegarContaCorrente(this.chConta.getValue());
                    if (newVal) {
                        ccCliente.registraServico(SAnaliseFluxoCaixa.getInstance());
                    } else {
                        ccCliente.desativarServico(SAnaliseFluxoCaixa.getInstance());
                    }
                });

        this.cbAnaliseInvestimento.selectedProperty()
                .addListener((prop, oldVal, newVal) -> {
                    ContaCorrente ccCliente = cliente.pegarContaCorrente(this.chConta.getValue());
                    if (newVal) {
                        ccCliente.registraServico(SAnaliseInvestimento.getInstance());
                    } else {
                        ccCliente.desativarServico(SAnaliseInvestimento.getInstance());
                    }
                });

        this.cbOferFinanceiro.selectedProperty()
                .addListener((prop, oldVal, newVal) -> {
                    ContaCorrente ccCliente = cliente.pegarContaCorrente(this.chConta.getValue());
                    if (newVal) {
                        ccCliente.registraServico(SOfertaFinanceiro.getInstance());
                    } else {
                        ccCliente.desativarServico(SOfertaFinanceiro.getInstance());
                    }
                });
    }

    private void sacar(ActionEvent evt) {
        evt.consume();

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Realizar saque");
        dialog.setContentText("Informe o valor do saque: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                ContaCorrente conta = cliente.pegarContaCorrente(this.chConta.getValue());
                conta.sacar(Double.parseDouble(result.get().replace(",", ".")));
                this.lbSaldo.setText(String.format("%.04f", conta.getSaldo()));

            } catch (IllegalArgumentException e) {
                mostrarMsgErro("Erro na operação", e);
            }
        }

    }

    private void mostrarMsgErro(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    private void transerir(ActionEvent evt) {
        evt.consume();

        ContaCorrente contaOrigem = cliente.pegarContaCorrente(this.chConta.getValue());

        Dialog<Pair<String, Double>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Look, a Custom Login Dialog");

        ButtonType btTypeEfetuarTrasnf = new ButtonType("Efetuar transferencia", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btTypeEfetuarTrasnf, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tfValor = new TextField();
        tfValor.setPromptText("9999,99");

        TextField tfContaDestino = new TextField();
        tfContaDestino.setPromptText("Agencia-Conta");

        grid.add(new Label("Valor: "), 0, 0);
        grid.add(tfValor, 1, 0);
        grid.add(new Label("Conta destino: "), 0, 1);
        grid.add(tfContaDestino, 1, 1);

        Node btEfetuarTransf = dialog.getDialogPane().lookupButton(btTypeEfetuarTrasnf);
        btEfetuarTransf.setDisable(true);

        tfValor.textProperty().addListener((observable, oldValue, newValue) -> {
            btEfetuarTransf.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> tfValor.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btTypeEfetuarTrasnf) {
                double valor = Double.parseDouble(tfValor.getText().replace(",", "."));
                return new Pair<>(tfContaDestino.getText(), valor);
            }
            return null;
        });

        Optional<Pair<String, Double>> result = dialog.showAndWait();

        result.ifPresent(resposta -> {
            String chaveConta = resposta.getKey();

            Optional<ContaCorrente> optContaDestino = DaoConta.pegarContaCorrente(chaveConta);

            if (optContaDestino.isPresent()) {
                try {
                    contaOrigem.transferir(resposta.getValue(), optContaDestino.get());
                    this.lbSaldo.setText(String.format("%.04f", contaOrigem.getSaldo()));
                } catch (IllegalArgumentException e) {
                    mostrarMsgErro("Erro na operação", e);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Transferencia não efetuada");
                alert.setContentText("Conta destino não encontrada");
                alert.showAndWait();
            }

        });

    }

    private void depositar(ActionEvent evt) {
        evt.consume();

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Realizar saque");
        dialog.setContentText("Informe o valor do deposito: ");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            ContaCorrente conta = cliente.pegarContaCorrente(this.chConta.getValue());
            conta.depositar(Double.parseDouble(result.get().replace(",", ".")));
            this.lbSaldo.setText(String.format("%.04f", conta.getSaldo()));
        }
    }

    private void logout(ActionEvent evt) {
        evt.consume();
        Home.abrir();
    }

    public static void abrir(Cliente cliente) {

        ControlarConta ctrl = Utility.switchScene(
                Home.class.getResource("/banco/view/fxml/VControlarConta.fxml"));

        ctrl.setCliente(cliente);
    }
}
