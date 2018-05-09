/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model;

import java.util.Optional;

/**
 *
 * @author GUILHERME.BRISIDA
 */
public class ClienteFactory {

    private final String nome;
    private final String telCelular;
    private final String telFixo;
    private final String email;
    private final String senha;

    private final Optional<String> cpf;
    private final Optional<String> cnpj;
    private final Optional<String> servidorJMS;

    public ClienteFactory(String nome, String telCelular,
            String telFixo, String email, String senha, String cpf) {
        this.nome = nome;
        this.telCelular = telCelular;
        this.telFixo = telFixo;
        this.email = email;
        this.senha = senha;

        this.cpf = Optional.of(cpf);
        this.cnpj = Optional.empty();
        this.servidorJMS = Optional.empty();
    }

    public ClienteFactory(String nome, String telCelular, String telFixo,
            String email, String senha, String cnpj, String servidorJMS) {
        this.nome = nome;
        this.telCelular = telCelular;
        this.telFixo = telFixo;
        this.email = email;
        this.senha = senha;

        this.cpf = Optional.empty();
        this.cnpj = Optional.of(cnpj);
        this.servidorJMS = Optional.of(servidorJMS);
    }

    public Cliente construir() {
        if (cpf.isPresent()) {
            return new ClientePessoaFisica(email, senha, nome, telCelular, telFixo, cpf.get());
        }

        return new ClientePessoaJuridica(email, senha, nome, telCelular, telFixo, cnpj.get(), servidorJMS.get());
    }

}
