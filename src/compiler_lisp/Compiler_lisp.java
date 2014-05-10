/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;
import java.io.*;
import java.util.*;
/**
 *
 * @author Дима
 */
public class Compiler_lisp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
      try {
          File f = new File("src"+File.separator+"input"+File.separator+"program.lsp");                       
          BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
          String s = null; 
          Parser parser = new Parser();
          while ((s = br.readLine()) != null){
              Node program = parser.getParseTree(s);
              System.out.println(program);
              Env e = new Env();
              LISP_object res = e.eval(program, e);
              System.out.println(res);              
          }                    
      }catch(Exception e){
        System.out.println(e);
      }
      
    }
    
}
