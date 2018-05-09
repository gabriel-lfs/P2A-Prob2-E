package banco.model;

/**
 *
 * @author marcel
 */
public class ClientePessoaJuridica extends Cliente {

    private String cnpj;
    private String servidorJMS;

    public ClientePessoaJuridica(String id, String senha, String nome, String telCelular, String telFixo, String cnpj, String servidorJMS) {
        super(nome, telCelular, telFixo, id, senha);
        this.setCnpj(cnpj);
        this.setServidorJMS(servidorJMS);
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getServidorJMS() {
        return servidorJMS;
    }

    public void setServidorJMS(String servidorJMS) {
        this.servidorJMS = servidorJMS;
    }

}
