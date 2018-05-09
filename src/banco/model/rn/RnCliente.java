/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model.rn;

import banco.model.CadastroException;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author GUILHERME.BRISIDA
 */
public class RnCliente {

    // RnCliente.validarCadastroCliente
    public static void validarCadastroCliente(String senha, String confirmacao, Collection contas) throws CadastroException {

        // Valida tamanho da senha
        if (senha.length() < 10) {
            throw new CadastroException("Senha muito curta");
        }

        // Valida a senha
        if (!Objects.equals(senha, confirmacao)) {
            throw new CadastroException("Confirmação e senha não batem");
        }

        // Valida se existem contas criadas
        if (contas.isEmpty()) {
            throw new CadastroException("Deve ser registrada no minimo uma conta para o usuario");
        }
    }

}
