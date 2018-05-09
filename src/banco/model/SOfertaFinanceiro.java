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
public class SOfertaFinanceiro implements Observador<ContaCorrente, Operacao>{
  
    private static SOfertaFinanceiro instance;

    // Bloquear acesso ao construtor
    private SOfertaFinanceiro() {
    }

    public static SOfertaFinanceiro getInstance() {
        if (instance == null) {
            instance = new SOfertaFinanceiro();
        }
        return instance;
    }

    @Override
    public void update(ContaCorrente o, Operacao op) {
        if(op.getTipo() == TipoOperacao.SAIDA){
            System.out.println("Serviço de oferta de financeiro");
            System.out.println(" > Cliente  : " + op.getConta().getCliente().getNome());
            System.out.println(" > Conta    : " + op.getConta().getChave());
            System.out.println(" > Operação : " + op.getTipo());
            System.out.println(" > Valor    : " + op.getValor());
        }
    }
}
