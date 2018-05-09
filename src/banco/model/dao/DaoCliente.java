/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model.dao;

import banco.model.CadastroException;
import banco.model.Cliente;
import banco.model.ClientePessoaFisica;
import banco.model.ContaCorrente;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author GUILHERME.BRISIDA
 */
public class DaoCliente {

    private static final ArrayList<Cliente> STORAGE_CLIENTES = new ArrayList<>();

    static {
        ClientePessoaFisica eu = new ClientePessoaFisica("gbrisida", "123", "Guilherme Brisida", "99999999", "88888888", "09018798940");

        ContaCorrente a = DaoConta.novaConta(666);
        ContaCorrente b = DaoConta.novaConta(666);
        ContaCorrente c = DaoConta.novaConta(493);

        a.depositar(2105.00000);
        b.depositar(1827.69000);
        c.depositar(12538.00000);

        eu.addConta(a);
        eu.addConta(b);
        eu.addConta(c);

        try {
            salvarCliente(eu);
        } catch (CadastroException ex) {
            Logger.getLogger(DaoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // DaoCliente.autenticarCliente()
    public static Cliente autenticarCliente(String email, String senha) throws AutenticacaoException {

        Optional<Cliente> cliente = STORAGE_CLIENTES.stream()
                .filter(c -> c.getEmail().equals(email))
                .findFirst();

        if (cliente.isPresent()) {
            if (!cliente.get().autenticar(email, senha)) {
                throw new AutenticacaoException("Senha incorreta");
            }
            return cliente.get();
        }

        throw new AutenticacaoException("Cliente não encontrado");
    }

    // DaoCliente.salvarCliente()
    public static void salvarCliente(Cliente cliente) throws CadastroException {

        Optional<Cliente> optCliente = STORAGE_CLIENTES.stream()
                .filter(c -> c.getEmail().equals(cliente.getEmail()))
                .findFirst();

        if (optCliente.isPresent()) {
            throw new CadastroException("Este e-mail já está em uso");
        }

        STORAGE_CLIENTES.add(cliente);
        cliente.listarContas().forEach(conta -> {
            DaoConta.salvarContaCorrente(
                    cliente.pegarContaCorrente(conta), cliente);
        });
    }

}
