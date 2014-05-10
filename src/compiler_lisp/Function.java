/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;

/**
 *
 * @author Дима
 */
public interface Function<F, T> {
    T apply(F from);
}