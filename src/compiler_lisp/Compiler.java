/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_lisp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedList;
import output.program;

/**
 *
 * @author Дима
 */
public class Compiler {
    public Env globals = new Env();
    
    public HashMap<String,String> functions = new HashMap();
    public void compiler_start() throws Exception{
        File f = new File("src"+File.separator+"input"+File.separator+"program.lsp");  
        // прописываем путь где будет храниттся файл
      File f1 = new File(System.getProperty("user.dir")+"/src/output/program.java"); 
          BufferedReader br = new BufferedReader(new FileReader(f.getAbsolutePath()));
          /*будем записывть в конец файла program.java
          ВНИМАНИЕ!!! Не обращать вниания на ошибку в файле program.java. Там не хватает одной скобки }
          При повторном запуске программы удалять содержимое файла program.java
          после 
          public static void main(String[] args){
           System.out.println(evaluate());
          }
          */
          Writer writer = new BufferedWriter(new FileWriter(f1,true));
          String s = null; 
          Parser parser = new Parser();
          
          
          while ((s = br.readLine()) != null){
              LISP_object program = parser.getParseTree(s);                           
              String res = this.compile(program);
              if (program.list.get(0).var!=null && program.list.get(0).var.type.equals("String") && program.list.get(0).var.data.equals("begin"))
                res = "public static LinkedList<LISP_object> forglobals = new LinkedList();\n"
                      +"public static Env globals = new Env();\n"
                      + "public static LISP_object evaluate(){LinkedList<LISP_object> res = new LinkedList();\n"+res+"}";
              else
                  res = "public static LinkedList<LISP_object> forglobals = new LinkedList();\n"
                      +"public static Env globals = new Env();\n"
                      + "public static LISP_object evaluate(){LinkedList<LISP_object> res = new LinkedList();\n"+res+"return res.pollLast();}";
              System.out.println(res);
              writer.append("\r\n");
              writer.append(res);
          }
          for (String it: this.functions.values()){
              System.out.println(it);
              writer.append("\r\n");
              writer.append(it);
          }
          //в конце файла progrm.java не хватает }, поэтому добавляем }
          writer.append("\r\n");
          writer.append("}");
          br.close();
          writer.close();
    }
    
    public String compile(final LISP_object x) throws Exception{
         String res = "";
         if (x.var != null && x.var.type.equals("String")){
            res = res + x.var.data;              
         }else
         if(!(x.list != null)){
             res = res + "new LISP_object(new Atom("+"\""+x.var.data+"\""+")"+")";
         }
         else          
         if (x.list.get(0).var!=null && x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("quote")){            
            throw new Exception("quote is not compile");
         }else
         if (x.list.get(0).var!=null && x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("if")){
            LISP_object test = x.list.get(1).copy();
            LISP_object conseq = x.list.get(2).copy();
            LISP_object alt = x.list.get(3).copy();
            String test_compiled = compile(test);
            
            if (conseq.var != null)
                res = res+test_compiled+"\nif(res.poll().res){return "+compile(conseq)+";}";
            else 
                res = res+test_compiled+"\nif(res.poll().res){"+compile(conseq)+"}";
            if (alt.var != null)
                res = res+"else{return "+compile(alt)+"}";
            else 
                res = res+"else{"+compile(alt)+"return res.poll();"+"}";            
         }else
         if (x.list.get(0).var!=null && x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("set!")){
            LISP_object var = x.list.get(1).copy();
            LISP_object exp = x.list.get(2).copy();              
            res = res+var.var.data+" = "+compile(exp)+";\n";            
         }else
         if (x.list.get(0).var!=null && x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("define")){
            LISP_object var = x.list.get(1).copy();
            LISP_object exp = x.list.get(2).copy();
            //env.env_dict.put(var.var.data, eval(exp, env));
            if (exp.list != null && exp.list.get(0).var != null && exp.list.get(0).var.data.equals("lambda")){
                LISP_object vars = exp.list.get(1).copy();
                LISP_object func = exp.list.get(2).copy();                
                String funcdef ="public static LISP_object "+var.var.data+"(";
                functions.put(var.var.data, funcdef);
                for (int i = 0; i< vars.list.size()-1; i++){
                    funcdef =funcdef+"LISP_object "+vars.list.get(i).var.data+",";
                }
                funcdef =funcdef+"LISP_object "+vars.list.get(vars.list.size()-1).var.data+"){";
                funcdef = funcdef+"LinkedList<LISP_object> res = new LinkedList();\n"+compile(func)+"}\n";
                functions.put(var.var.data, funcdef);
            }else{
                if (exp.list != null){
                    res = res + compile(exp)+"\n";
                    res = res + "LISP_object "+var.var.data+" = res.poll();\n";
                }
                else
                    res = res + "LISP_object "+var.var.data+" = "+compile(exp)+";\n";
            }
         }else
         if (x.list.get(0).var!=null && x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("begin")){
             res = res+"{\n";
             for (int i = 1; i<x.list.size(); i++){
                 if (x.list.get(i).var != null && x.list.get(i).var.type.equals("String"))
                    res = res + "res.add("+compile(x.list.get(i))+");";
                 else
                    res = res + compile(x.list.get(i));
             }            
             res =res+"return res.poll();"+"}\n";
         }else{
            LinkedList<String> exps = new LinkedList<String>();
            for (LISP_object it:x.list){
                if (it.var != null) 
                    exps.add(compile(it));
                else{
                    res = res + compile(it);
                    exps.add("res.poll()");
                }
            }
            if(globals.env_dict.get(exps.get(0))!=null){
                res = res+"forglobals.clear();\n";
                for (int i = 1; i<exps.size(); i++)
                    if (globals.env_dict.get(exps.get(i)) != null)
                        res = res+" forglobals.add(globals.env_dict.get("+"\""+exps.get(i)+"\""+"));\n";
                    else
                        res = res+" forglobals.add("+exps.get(i)+");\n";
                res = res + "res.add(globals.env_dict.get("+"\""+exps.get(0)+"\""+").proc.apply(forglobals));\n";
            }else
            if (functions.get(exps.get(0)) != null){
                res = res+"res.add( " + exps.get(0)+"(";
                for (int i = 1; i<exps.size()-1; i++)
                     res = res + exps.get(i)+",";
                res = res + exps.get(exps.size()-1)+"));";
            }else{
                for (String it:exps){
                    res = res + it;
                }
            }            
         }                                        
         return res;
     }
}
