/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model.dao;

import banco.model.Cliente;
import banco.model.ContaCorrente;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author GUILHERME.BRISIDA
 */
public class DaoConta {

    private static final HashSet<Integer> CONTAS_EM_USO = new HashSet<>();
    private static final HashMap<Cliente, ArrayList<ContaCorrente>> ARMAZENAMENTO = new HashMap<>();

    // DaoConta.pegarContaCorrente
    public static Optional<ContaCorrente> pegarContaCorrente(String chave) {

        ArrayList<ContaCorrente> todasContas = new ArrayList<>();

        ARMAZENAMENTO.forEach((cliente, contas) -> {
            todasContas.addAll(contas);
        });

        return todasContas.stream()
                .filter(conta -> conta.getChave().equals(chave))
                .findFirst();

    }

    // DaoConta.salvarContaCorrente
    public static void salvarContaCorrente(ContaCorrente conta, Cliente cliente) {

        // Registra o numero da conta
        CONTAS_EM_USO.add(conta.getNumero());

        // Salva a conta no armazenamento
        if (ARMAZENAMENTO.containsKey(cliente)) {
            ARMAZENAMENTO.get(cliente).add(conta);
        } else {
            ARMAZENAMENTO.put(cliente, new ArrayList(Arrays.asList(conta)));
        }
    }

    // DaoConta.novaConta()
    public static ContaCorrente novaConta(int agencia) {
        Random r = new Random();
        int nrConta;

        do {
            nrConta = r.nextInt((999999)) + 1;

        } while (CONTAS_EM_USO.contains(nrConta));

        return new ContaCorrente(nrConta, agencia);
    }

}
