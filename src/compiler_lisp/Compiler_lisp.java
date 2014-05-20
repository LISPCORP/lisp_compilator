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
       //   File f1 = new File("outfile.txt");
       //   f1.delete();
          BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
        //  FileWriter writer = new FileWriter(f1,true);

         // Writer writer = new BufferedWriter(new FileWriter("D:\\file.txt"));
          String str = "";
          String s = null; 
          Parser parser = new Parser();
          Env e = new Env();
          
          while ((s = br.readLine()) != null){
              
            // System.out.println(s); 
             str = str+s;
          }
          s = str;
          
           
          
               
              LISP_object program = parser.getParseTree(s);
              System.out.println(program);
           //   writer.append("\r\n");
           //   writer.append(program.toString());
             
              LISP_object res = e.eval(program, e);
              System.out.println(res);  
            //  writer.append("\r\n");
           //   writer.append(res.toString());
             
         
        //  writer.close();
          compiler();
         // System.out.println(evaluate());
            br.close();
      }catch(Exception e){
        System.out.println(e);
      }
      
    }
    public static void compiler() throws Exception{
          Compiler comp = new Compiler();
          comp.compiler_start();
    }
}
