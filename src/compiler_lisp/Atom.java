/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;

/**
 *
 * @author Дима
 */
public class Atom{
    public String data = null;
    public String type = null;
    
    public Atom copy(){
        Atom res = new Atom();
        res.data = this.data;
        res.type = this.type;
        return res;
    }
    
    public boolean equals(Atom o){
        if (this.data.equals(o.data) && this.type.equals(o.type)) return true;
        else return false;
    }
    public String toString(){
        return data;
    }
    
}
