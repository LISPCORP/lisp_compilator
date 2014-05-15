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
public class Env{            
    public HashMap<String, LISP_object> env_dict = new HashMap<String,LISP_object>();
    public Env outer = null;
    public double eps = 10e-7;
    public Env(LISP_object parms, LinkedList<LISP_object> args, Env outer){
        this.outer = outer;
        for (int i = 0; i<parms.list.size(); i++){
            env_dict.put(parms.list.get(i).var.data, args.get(i));
        }
    }
    public Env(){
        this.add_globals(this);
    }
    
    public Env find(String var){
        if (this.env_dict.containsKey(var)) return this;
        else return this.outer.find(var);
    }
    
    private Env add_globals(Env env){
        Function<LinkedList<LISP_object>, LISP_object> add = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object res = from.get(0).copy();                
                for (int i = 1; i<from.size(); i++){
                    if (from.get(i).var != null) {                                                
                        if (from.get(i).var.type.equals("Double")){
                            res.var.data = Double.parseDouble(res.var.data)+Double.parseDouble(from.get(i).var.data)+"";
                        }
                    }
                }                 
                return res;
            }
        };
        LISP_object p = new LISP_object();
        p.proc = add;
        env.env_dict.put("+", p);
        
        Function<LinkedList<LISP_object>, LISP_object> sub = new Function<LinkedList<LISP_object>, LISP_object>() {
           public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object res = from.get(0).copy();                
                for (int i = 1; i<from.size(); i++){
                    if (from.get(i).var != null) {                                                
                        if (from.get(i).var.type.equals("Double")){
                            res.var.data = Double.parseDouble(res.var.data)-Double.parseDouble(from.get(i).var.data)+"";
                        }
                    }
                }                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = sub;
        env.env_dict.put("-", p);
        
        
        Function<LinkedList<LISP_object>, LISP_object> mul = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object res = from.get(0).copy();                
                for (int i = 1; i<from.size(); i++){
                    if (from.get(i).var != null) {                                                
                        if (from.get(i).var.type.equals("Double")){
                            res.var.data = Double.parseDouble(res.var.data)*Double.parseDouble(from.get(i).var.data)+"";
                        }
                    }
                }                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = mul;
        env.env_dict.put("*", p);
        
        
        Function<LinkedList<LISP_object>, LISP_object> div = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object res = from.get(0).copy();                
                for (int i = 1; i<from.size(); i++){
                    if (from.get(i).var != null) {                                                
                        if (from.get(i).var.type.equals("Double")){
                            res.var.data = Double.parseDouble(res.var.data)/Double.parseDouble(from.get(i).var.data)+"";
                        }
                    }
                }                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = div;
        env.env_dict.put("/", p);
        
        Function<LinkedList<LISP_object>, LISP_object> not = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object res = from.get(0).copy();                                
                res.res = !Boolean.parseBoolean(res.var.data);
                res.var.data = !Boolean.parseBoolean(res.var.data)+"";
                res.var.type = "String";
                return res;
            }
        };
        p = new LISP_object();
        p.proc = not;
        env.env_dict.put("not", p);
        
        Function<LinkedList<LISP_object>, LISP_object> gt = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                
                res.res = Double.parseDouble(op1.var.data) > Double.parseDouble(op2.var.data);
                res.var = new Atom();
                res.var.data = ""+res.res;
                res.var.type = "String";
                                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = gt;
        env.env_dict.put(">", p);
        
        Function<LinkedList<LISP_object>, LISP_object> lt = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                res.res = Double.parseDouble(op1.var.data) < Double.parseDouble(op2.var.data);
                res.var.data = ""+res.res;
                res.var.type = "String";
                                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = lt;
        env.env_dict.put("<", p);
        
        Function<LinkedList<LISP_object>, LISP_object> ge = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                res.res = Double.parseDouble(op1.var.data) >= Double.parseDouble(op2.var.data);
                res.var.data = ""+res.res;
                res.var.type = "String";
                                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = ge;
        env.env_dict.put(">=", p);
        
        Function<LinkedList<LISP_object>, LISP_object> le = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                res.res = Double.parseDouble(op1.var.data) <= Double.parseDouble(op2.var.data);
                res.var.data = ""+res.res;
                res.var.type = "String";
                                 
                return res;
            }
        };
        p = new LISP_object();
        p.proc = le;
        env.env_dict.put("<=", p);
        
        Function<LinkedList<LISP_object>, LISP_object> eq = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                res.res = op1.equals(op2);
                if (op1.var != null && op2.var!=null && op1.var.type.equals("Double") && op2.var.type.equals("Double")){                
                    res.res = Math.abs(Double.parseDouble(op1.var.data) - Double.parseDouble(op2.var.data)) < eps;
                }                                
                res.var.data = ""+res.res;
                res.var.type = "String";
                return res;
            }
        };
        p = new LISP_object();
        p.proc = eq;
        env.env_dict.put("=", p);
        env.env_dict.put("equal?",p);
        
        
        p = new LISP_object();
        p.var = new Atom();
        p.res = true;
        p.var.type = "String";
        p.var.data = "true";
        env.env_dict.put("true", p);
        
        p = new LISP_object();
        p.var = new Atom();
        p.res = false;
        p.var.type = "String";
        p.var.data = "false";
        env.env_dict.put("false", p);
        
        Function<LinkedList<LISP_object>, LISP_object> and = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                res.res = Boolean.parseBoolean(op1.var.data) & Boolean.parseBoolean(op2.var.data);
                res.var.data = (Boolean.parseBoolean(op1.var.data) & Boolean.parseBoolean(op2.var.data))+"";
                res.var.type = "String";
                return res;
            }
        };
        p = new LISP_object();
        p.proc = and;
        env.env_dict.put("and", p);
        
        Function<LinkedList<LISP_object>, LISP_object> or = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                res.res = Boolean.parseBoolean(op1.var.data) | Boolean.parseBoolean(op2.var.data);
                res.var.data = (Boolean.parseBoolean(op1.var.data) | Boolean.parseBoolean(op2.var.data))+"";
                res.var.type = "String";
                return res;
            }
        };
        p = new LISP_object();
        p.proc = or;
        env.env_dict.put("or", p);
        
        Function<LinkedList<LISP_object>, LISP_object> length = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();                
                LISP_object res =  new LISP_object();
                res.var = new Atom();                
                if (op1.list == null){
                    res.var.data = "0.0";
                    res.var.type = "Double";
                }else{
                    res.var.data = ""+op1.list.size();
                    res.var.type = "Double";
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = length;
        env.env_dict.put("length", p);
        
        Function<LinkedList<LISP_object>, LISP_object> cons = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();
                LISP_object op2 = from.get(1).copy();
                LISP_object res =  new LISP_object();
                
                res.list = new LinkedList<LISP_object>();                
                res.list.add(op1);
                if (op2.list == null){
                    res.list.add(op2);
                }else{
                    for (LISP_object it: op2.list){
                        res.list.add(it);
                    }
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = cons;
        env.env_dict.put("cons", p);
        
        Function<LinkedList<LISP_object>, LISP_object> car = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();                
                LISP_object res = op1.list.get(0).copy();                
                return res;
            }
        };
        p = new LISP_object();
        p.proc = car;
        env.env_dict.put("car", p);
        
        Function<LinkedList<LISP_object>, LISP_object> cdr = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                
                LISP_object op1 = from.get(0).copy();                
                LISP_object res =  new LISP_object();
                res.list = new LinkedList<LISP_object>();
                for (int i = 1; i<op1.list.size(); i++){
                    res.list.add(op1.list.get(i).copy());
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = cdr;
        env.env_dict.put("cdr", p);
        
        Function<LinkedList<LISP_object>, LISP_object> append = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                                               
                LISP_object res =  new LISP_object();
                res.list = new LinkedList<LISP_object>();
                for (LISP_object it: from){
                    if (it.list == null){
                        res.list.add(it);
                    }else{
                        for (LISP_object it1: it.list)
                            res.list.add(it1);
                    }
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = append;
        env.env_dict.put("append", p);
        
        Function<LinkedList<LISP_object>, LISP_object> list = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                                               
                LISP_object res =  new LISP_object();
                res.list = new LinkedList<LISP_object>();
                for (LISP_object it: from){
                    res.list.add(it);
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = list;
        env.env_dict.put("list", p);
        
        Function<LinkedList<LISP_object>, LISP_object> list1 = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                                               
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                if (from.get(0).list != null){
                    res.var.data = "true";
                    res.var.type = "String";
                    res.res = true;
                }else{
                    res.var.data = "false";
                    res.var.type = "String";
                    res.res = false;
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = list1;
        env.env_dict.put("list?", p);
        
        Function<LinkedList<LISP_object>, LISP_object> null1 = new Function<LinkedList<LISP_object>, LISP_object>() {
            public LISP_object apply(LinkedList<LISP_object> from) {                                                               
                LISP_object res =  new LISP_object();
                res.var = new Atom();
                if (from.get(0).list == null || from.get(0).list.size() == 0){
                    res.var.data = "true";
                    res.var.type = "String";
                    res.res = true;
                }else{
                    res.var.data = "false";
                    res.var.type = "String";
                    res.res = false;
                }
                return res;
            }
        };
        p = new LISP_object();
        p.proc = null1;
        env.env_dict.put("null?", p);
        
        return env;
    }
    
    
    
    public LISP_object eval(final LISP_object x,final Env env){
        LISP_object res = new LISP_object();
        if (x.var != null && x.var.type.equals("String")){
            return env.find(x.var.data).env_dict.get(x.var.data);
        }
        else 
        if (!(x.list != null)){
           res.var = x.var.copy();
           return res;
        }
        else       
        if (x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("quote")){
            
            res = x.list.get(1).copy();
        }
        else        
        if (x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("if")){
            LISP_object test = x.list.get(1).copy();
            LISP_object conseq = x.list.get(2).copy();
            LISP_object alt = x.list.get(3).copy();
            boolean ts = eval(test, env).res;
            if (ts){
                return eval(conseq, env);
            }else return eval(alt, env);
        }
        else
        if (x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("set!")){
            LISP_object var = x.list.get(1).copy();
            LISP_object exp = x.list.get(2).copy();
            env.find(var.var.data).env_dict.put(var.var.data, eval(exp, env));            
        }
        else
        if (x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("define")){
            LISP_object var = x.list.get(1).copy();
            LISP_object exp = x.list.get(2).copy();
            env.env_dict.put(var.var.data, eval(exp, env));
        }
        else 
        if (x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("lambda")){            
            res.proc = new Function<LinkedList<LISP_object>, LISP_object>(){
                public LISP_object apply(LinkedList<LISP_object> ls){
                    final LISP_object vars = x.list.get(1).copy();
                    final LISP_object exp = x.list.get(2).copy();                                        
                    return eval(exp, new Env(vars, ls ,env));
                }
            };
        }
        else
        if (x.list.get(0).var.type.equals("String") && x.list.get(0).var.data.equals("begin")){
            for (int i = 1; i<x.list.size(); i++){
                res = eval(x.list.get(i), env);
            }
            return res;
        }else{
            LinkedList<LISP_object> exps = new LinkedList<LISP_object>();
            for (LISP_object exp: x.list){
                exps.add(eval(exp,env));
            }
            Function<LinkedList<LISP_object>, LISP_object> f = exps.poll().proc;
            return f.apply(exps);
        }
        return res;
    }
}
