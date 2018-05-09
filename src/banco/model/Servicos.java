/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model;

import java.util.ArrayList;

/**
 *
 * @author guilh
 */
public abstract class Servicos {

    private final ArrayList<Observador<ContaCorrente, Operacao>> observadores;

    public Servicos() {
        this.observadores = new ArrayList<>();
    }

    public boolean servicoAtivo(Observador obs) {
        return observadores.contains(obs);
    }

    public void registraServico(Observador obs) {
        if (!servicoAtivo(obs)) {
            observadores.add(obs);
        }
    }

    public void desativarServico(Observador obs) {
        observadores.remove(obs);
    }

    public void notificaOperacao(Operacao ope) {
        observadores.forEach(it -> it.update(ope.getConta(), ope));
    }

}
