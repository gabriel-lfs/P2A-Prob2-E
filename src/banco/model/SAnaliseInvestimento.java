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
public class SAnaliseInvestimento implements Observador<ContaCorrente, Operacao> {

    private static SAnaliseInvestimento instance;

    // Bloquear acesso ao construtor
    private SAnaliseInvestimento() {
    }

    public static SAnaliseInvestimento getInstance() {
        if (instance == null) {
            instance = new SAnaliseInvestimento();
        }
        return instance;
    }

    @Override
    public void update(ContaCorrente o, Operacao op) {
        if (op.getTipo() == TipoOperacao.ENTRADA) {
            System.out.println("Serviço de analise de investimento");
            System.out.println(" > Cliente  : " + op.getConta().getCliente().getNome());
            System.out.println(" > Conta    : " + op.getConta().getChave());
            System.out.println(" > Operação : " + op.getTipo());
            System.out.println(" > Valor    : " + op.getValor());
        }
    }
}
