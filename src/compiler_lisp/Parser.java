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
    public LISP_object getParseTree(String s)throws Exception{        
        return parse(s);
    }
    
    private LISP_object parse(String s) throws Exception{
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
    
    private LISP_object read_from(LinkedList<String> tokens)throws Exception{            
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
            LISP_object L = new LISP_object();
            L.list = new LinkedList();
            
           
            
            while (!tokens.getFirst().equals(")")){
                //System.out.println("lol " + tokens.getFirst());
                if (tokens.getFirst().equals("\n"))
                {
                    //System.out.println("lol");
                    tokens.poll();
                }
                LISP_object l = read_from(tokens);               
                L.list.add(l);
            }                
            tokens.poll();
            return L;
        }        
        else 
        if (")".equals(token))
            throw new Exception("unexpected EOF while reading");
        else{
            LISP_object l = new LISP_object();
            l.var = atom(token);
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
