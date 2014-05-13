/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;

import java.util.LinkedList;

/**
 *
 * @author Дима
 */
public class LISP_object {
    public Function<LinkedList<LISP_object>, LISP_object> proc = null;
    public Atom var = null;
    public boolean res = false;
    public LinkedList<LISP_object> list = null;
    
    public LISP_object copy(){
        LISP_object res = new LISP_object();
        res.proc = this.proc;
        if (this.var != null)
            res.var = this.var.copy();
        else
            res.var = null;
        res.res = this.res;
        if (this.list == null) res.list = null;
        else{
            res.list = new LinkedList<LISP_object>();
            for (LISP_object it: this.list){
                res.list.add(it.copy());
            }
        }
        return res;
    }
    public String toString(){
        if (proc != null) return "FUNCTION";
        else if (list != null){
            String res = "(";
            for (LISP_object it: list) res = res+it.toString()+" ";
            res = res+")";
            return res;
        }else if (var!= null) return var.data;
        else return "";
    }
    
    public boolean equals(Object o1){
        LISP_object o = (LISP_object)o1;
        if (this.proc == o.proc){            
            if ((this.var != null && o.var!=null && this.var.equals(o.var))
                    || (this.var == null && o.var == null)){
                if (this.res == o.res){
                    if ((this.list == null && o.list == null) 
                            || this.list != null && o.list != null && this.list.equals(o.list)) 
                        return true;                                    
                        else return false;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }
        else{
            return false;
        }
    }
}
