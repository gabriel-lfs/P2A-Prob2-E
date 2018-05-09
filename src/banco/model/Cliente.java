package banco.model;

/*
 * Esta classe oferece as funcionalidades básicas para atender ao Problema 2.
 */
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author marcel
 */
public abstract class Cliente {

    private String nome;
    private String telCelular;
    private String telFixo;
    private HashMap<String, ContaCorrente> contas;

    private final String email;
    private final String senha;

    public Cliente(String nome, String telCelular, String telFixo, String identificacao, String senha) {
        // 
        this.setNome(nome);
        this.setTelCelular(telCelular);
        this.setTelFixo(telFixo);
        this.contas = new HashMap();

        // Informações de autenticação
        this.email = identificacao;
        this.senha = senha;
    }

    public ArrayList<String> listarContas() {
        ArrayList<String> listaContas = new ArrayList<>();

        contas.forEach((chave, valor) -> {
            listaContas.add(chave);
        });

        return listaContas;
    }

    public void addConta(ContaCorrente cc) {
        String chave = cc.getChave();
        if (this.contas.containsKey(chave)) {
            throw new IllegalArgumentException("Já existe conta corrente com este número/agência");
        } else {
            this.contas.put(chave, cc);
            cc.setCliente(this);
        }
    }

    public ContaCorrente pegarContaCorrente(String chave) {
        return contas.get(chave);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelFixo() {
        return telFixo;
    }

    public void setTelFixo(String telFixo) {
        this.telFixo = telFixo;
    }

    public String getEmail() {
        return email;
    }

    public boolean autenticar(String identificacao, String senha) {
        return identificacao.equals(this.email)
                && senha.equals(this.senha);
    }

}
