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
          Env e = new Env();
          while ((s = br.readLine()) != null){
              LISP_object program = parser.getParseTree(s);
              System.out.println(program);
              
              LISP_object res = e.eval(program, e);
              System.out.println(res);              
          }
          br.close();
          compiler();
          //System.out.println(evaluate());
      }catch(Exception e){
        System.out.println(e);
      }
      
    }
    public static void compiler() throws Exception{
          File f = new File("src"+File.separator+"input"+File.separator+"program.lsp");                       
          BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
          String s = null; 
          Parser parser = new Parser();
          Env e = new Env();
          Compiler comp = new Compiler();
          while ((s = br.readLine()) != null){
              LISP_object program = parser.getParseTree(s);                           
              String res = comp.compile(program);
              System.out.println("public static LinkedList<LISP_object> forglobals = new LinkedList();\n"
                      +"public static Env globals = new Env();\n"
                      + "public static LISP_object evaluate(){LinkedList<LISP_object> res = new LinkedList();\n"+res+"}");              
          }
          for (String it: comp.functions.values()) System.out.println(it);
          br.close();
    }    
}
