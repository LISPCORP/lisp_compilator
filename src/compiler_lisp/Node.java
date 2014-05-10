/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;
import java.util.*;
/**
 *
 * @author Дима
 */
public class Node {
    public LinkedList<Node> childList = new LinkedList<Node>();
    public Atom data = null;        
    public Node parent = null;
    public String toString(){
        String res = "";
        
        if (data==null){
            res = res+"(";
            for (Node t: childList) res = res+t.toString()+" ";
            res = res+")";
        }else{
            res = res+data.toString();
        }
        return res;
    }
    
    //Хрень
    public Node copy(){
        Node res = new Node();
        res.data = this.data.copy();
        res.parent = this.parent;
        res.childList = new LinkedList<Node>();
        for (Node it: this.childList){
            res.childList.add(it.copy());
        }
        return res;
    }
    
    
    public LISP_object quote(){
        LISP_object res = new LISP_object();
        if (this.data != null){
             res.var = this.data.copy();                
        }else{
              res.list = new LinkedList<LISP_object>();
              for (Node it: this.childList){
                  res.list.add(it.quote());
              }
       }
        return res;
    }
}
