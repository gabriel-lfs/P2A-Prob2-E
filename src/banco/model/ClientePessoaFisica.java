package banco.model;

/**
 *
 * @author marcel
 */
public class ClientePessoaFisica extends Cliente {

    private String cpf;

    public ClientePessoaFisica(String id, String senha, String nome, String telCelular, String telFixo, String cpf) {
        super(nome, telCelular, telFixo, id, senha);
        this.setCpf(cpf);
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
