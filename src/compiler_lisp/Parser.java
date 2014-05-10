/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;

/**
 *
 * @author Дима
 */
public class Parser {
    public Node getParseTree(String s)throws Exception{
            // TODO code application logic here
        /*Node programTree = new Node();              
        programTree.childList.add(parse(s));                                                                    
        return programTree;*/
        return parse(s);
    }
    
    private Node parse(String s) throws Exception{
        return read_from(tokenize(s));
    }
    private LinkedList<String> tokenize(String s){                    
        s = s.replace("("," ( ").replace(")"," ) ");           
        LinkedList<String> lst = new LinkedList<String>();
        for (String st: s.split("\\s+")){
            lst.add(st);
        }
        return lst;
    }
    
    private Node read_from(LinkedList<String> tokens)throws Exception{            
        if (tokens.size() == 0){
            throw new Exception("unexpected EOF while reading");
        }
        String token = tokens.poll();
        if (token.equals("")){
           do{
               token = tokens.poll();
           }while (token.equals(""));
        }
        if ("(".equals(token)){
            Node L = new Node();
            while (!tokens.getFirst().equals(")")){
                Node l = read_from(tokens);
                l.parent = L;
                L.childList.add(l);
            }                
            tokens.poll();
            return L;
        }        
        else 
        if (")".equals(token))
            throw new Exception("unexpected EOF while reading");
        else{
            Node l = new Node();
            l.data = atom(token);
            return l;
        }
    }
    
    public static Atom atom(String token){
        Atom t = new Atom();        
        try{
            t.data = ""+Double.parseDouble(token);
            t.type = "Double";
            return t;
        }catch(NumberFormatException et){
            t.data = token;
            t.type = "String";
            return t;
        }                    
    }       
}
