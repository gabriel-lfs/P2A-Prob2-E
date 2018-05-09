/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model;

/**
 *
 * @author guilh
 * @param <T>
 * @param <X>
 */
public interface Observador<T, X> {

    public void update(T observavel, X observado);

}
