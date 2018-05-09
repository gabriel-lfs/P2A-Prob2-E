/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model;

/**
 *
 * @author guilh
 */
public class SBaixaAutomatica implements Observador<ContaCorrente, Operacao> {

    private static SBaixaAutomatica instance;

    // Bloquear acesso ao construtor
    private SBaixaAutomatica() {
    }

    public static SBaixaAutomatica getInstance() {
        if (instance == null) {
            instance = new SBaixaAutomatica();
        }
        return instance;
    }

    @Override
    public void update(ContaCorrente o, Operacao op) {

        System.out.println("Serviço de baixa automatica");
        System.out.println(" > Cliente  : " + op.getConta().getCliente().getNome());
        System.out.println(" > Conta    : " + op.getConta().getChave());
        System.out.println(" > Operação : " + op.getTipo());
        System.out.println(" > Valor    : " + op.getValor());

    }

}
