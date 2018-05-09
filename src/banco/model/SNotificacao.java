/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author guilh
 */
public class SNotificacao implements Observador<ContaCorrente, Operacao> {

    private static SNotificacao instance;

    // Bloquear acesso ao construtor
    private SNotificacao() {
    }

    public static SNotificacao getInstance() {
        if (instance == null) {
            instance = new SNotificacao();
        }
        return instance;
    }

    @Override
    public void update(ContaCorrente o, Operacao op) {
        if (o.getOpcaoNotificacao().isPresent()) {
            System.out.println("Serviço de Notificação");
            System.out.println(" > Via: " + o.getOpcaoNotificacao().get());
            System.out.println(" > Cliente  : " + op.getConta().getCliente().getNome());
            System.out.println(" > Conta    : " + op.getConta().getChave());
            System.out.println(" > Operação : " + op.getTipo());
            System.out.println(" > Valor    : " + op.getValor());
        }
    }

    public static ArrayList<OpcoesNotificacao> opcoesNotificacao(Cliente cliente) {

        // Retorna opções de pessoa fisica
        if (cliente instanceof ClientePessoaFisica) {
            return new ArrayList<>(Arrays.asList(
                    OpcoesNotificacao.INATIVO,
                    OpcoesNotificacao.WHATSAPP,
                    OpcoesNotificacao.SMS,
                    OpcoesNotificacao.SMS_E_WHATSAPP
            ));
        }

        // Retorna opções para pessoa juridica
        return new ArrayList<>(Arrays.asList(
                OpcoesNotificacao.INATIVO,
                OpcoesNotificacao.WHATSAPP,
                OpcoesNotificacao.SMS,
                OpcoesNotificacao.SMS_E_WHATSAPP,
                OpcoesNotificacao.JMS
        ));
    }

}
