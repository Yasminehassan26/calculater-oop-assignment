package com.project.backendd;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class controller{

    @GetMapping ("/op1")
    public  String operation1(@RequestParam(name="one")String one, @RequestParam(name="two")String two, @RequestParam(name="op")String op) {
        String result="";
        switch(op){
            case"+":
                result=add(one,two);
                break;
            case"-":
                result=minus(one,two);
                break;
            case"*":
                result=multiply(one,two);
                break;
            case"/":
                result=division(one,two);
                break;

        }
        return result;
    }
    public String add(String one,String two){
        double var1=Double.parseDouble(one);
        double var2=Double.parseDouble(two);
        return String.valueOf(var1+var2) ;
    }
    public String minus(String one,String two){
        double var1=Double.parseDouble(one);
        double var2=Double.parseDouble(two);
        return String.valueOf(var1-var2) ;
    }
    public String multiply(String one,String two){
        double var1=Double.parseDouble(one);
        double var2=Double.parseDouble(two);
        return String.valueOf(var1 * var2) ;
    }
    public String division(String one,String two){
        double var1=Double.parseDouble(one);
        double var2=Double.parseDouble(two);
        if(var2==0){
            return ("E");
        } else {
            return String.valueOf(var1/var2) ;
        }
    }
    public String power(String one){
        double var1=Double.parseDouble(one);
        double var2=Math.pow(var1,2);
        return String.valueOf(var2) ;
    }
    public String percent(String one){
        double var1=Double.parseDouble(one);
        double var2=var1/100;
        return String.valueOf(var2) ;
    }
    public String inverse(String one){
        double var1=Double.parseDouble(one);
        double var2=1/var1;
        return String.valueOf(var2) ;
    }
    public String rootSquare(String one){
        double var1=Double.parseDouble(one);
        if(var1<0){
            return ("E");
        }
        else{
            double var2=Math.sqrt(var1);
            return String.valueOf( var2) ;
        }

    }
    @GetMapping ("/neg")
    public String negate(@RequestParam(name="one")String one){
        double var1=Double.parseDouble(one);
        double var2=-1*var1;
        return String.valueOf(var2) ;
    }
    @GetMapping ("/op2")
    public  String operation(@RequestParam(name="one")String one,  @RequestParam(name="op")String op) {
        String result="";
        switch(op){
        case "pow":
        result = power(one);
        break;
        case "percent":
        result = percent(one);
        break;
        case "inv":
        result = inverse(one);
        break;
        case "root":
        result = rootSquare(one);
        break;

    }
        return result;

    }
}
