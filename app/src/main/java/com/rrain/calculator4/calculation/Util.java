package com.rrain.calculator4.calculation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Util {


    public static String toDec2(String number, int radix) throws Exception{
        if (radix==10) return number;
        switch (number) {
            case "NaN":                                                 return "NaN";
            case "Infinity": case "∞":                                  return "∞";
            case "-Infinity": case "-∞": case "−Infinity": case "−∞":   return "−∞";
        }

        String sign = "";
        if (number.startsWith("+")) number = number.substring(1, number.length());
        if (number.startsWith("-") || number.startsWith("−")) {number = number.substring(1, number.length()); sign = "−";}


        List<String> part1 = new ArrayList<>();
        List<String> part2 = new ArrayList<>();

        List<String> list = part1;
        for (int i = 0; i < number.length(); i++) {
            switch (number.charAt(i)){
                case'.':case',':       list = part2;                               continue;
                case'0': if (radix>0)  list.add("0");  else throw new Exception(); continue;
                case'1': if (radix>1)  list.add("1");  else throw new Exception(); continue;
                case'2': if (radix>2)  list.add("2");  else throw new Exception(); continue;
                case'3': if (radix>3)  list.add("3");  else throw new Exception(); continue;
                case'4': if (radix>4)  list.add("4");  else throw new Exception(); continue;
                case'5': if (radix>5)  list.add("5");  else throw new Exception(); continue;
                case'6': if (radix>6)  list.add("6");  else throw new Exception(); continue;
                case'7': if (radix>7)  list.add("7");  else throw new Exception(); continue;
                case'8': if (radix>8)  list.add("8");  else throw new Exception(); continue;
                case'9': if (radix>9)  list.add("9");  else throw new Exception(); continue;
                case'A': if (radix>10) list.add("10"); else throw new Exception(); continue;
                case'B': if (radix>11) list.add("11"); else throw new Exception(); continue;
                case'C': if (radix>12) list.add("12"); else throw new Exception(); continue;
                case'D': if (radix>13) list.add("13"); else throw new Exception(); continue;
                case'E': if (radix>14) list.add("14"); else throw new Exception(); continue;
                case'F': if (radix>15) list.add("15"); else throw new Exception(); continue;
            }
            throw new Exception();
        }
        if (part1.size()==0) throw new Exception();


        BigDecimal finalNumber = BigDecimal.ZERO;

        for (int i = 0; i < part1.size(); i++)
            finalNumber=finalNumber.add(new BigDecimal(part1.get(i)).multiply(new BigDecimal(radix).pow(part1.size()-1-i)));

        for (int i = 0; i < part2.size(); i++)
            finalNumber=finalNumber.add(new BigDecimal(part2.get(i)).multiply(new BigDecimal(Math.pow(radix, -(i+1)))));

        return sign + stripTrailingZeros(finalNumber.toPlainString());
    }



    public static String toDec(String number, int radix) throws Exception{
        if (radix==10) return number;
        switch (number) {
            case "NaN":                                                 return "NaN";
            case "Infinity": case "∞":                                  return "∞";
            case "-Infinity": case "-∞": case "−Infinity": case "−∞":   return "−∞";
        }

        String sign = "";
        if (number.startsWith("+")) number = number.substring(1, number.length());
        if (number.startsWith("-") || number.startsWith("−")) {number = number.substring(1, number.length()); sign = "−";}


        List<String> part1 = new ArrayList<>();
        List<String> part2 = new ArrayList<>();

        List<String> list = part1;
        for (int i = 0; i < number.length(); i++) {
            switch (number.charAt(i)){
                case'.':case',':       list = part2;                               continue;
                case'0': if (radix>0)  list.add("0");  else throw new Exception(); continue;
                case'1': if (radix>1)  list.add("1");  else throw new Exception(); continue;
                case'2': if (radix>2)  list.add("2");  else throw new Exception(); continue;
                case'3': if (radix>3)  list.add("3");  else throw new Exception(); continue;
                case'4': if (radix>4)  list.add("4");  else throw new Exception(); continue;
                case'5': if (radix>5)  list.add("5");  else throw new Exception(); continue;
                case'6': if (radix>6)  list.add("6");  else throw new Exception(); continue;
                case'7': if (radix>7)  list.add("7");  else throw new Exception(); continue;
                case'8': if (radix>8)  list.add("8");  else throw new Exception(); continue;
                case'9': if (radix>9)  list.add("9");  else throw new Exception(); continue;
                case'A': if (radix>10) list.add("10"); else throw new Exception(); continue;
                case'B': if (radix>11) list.add("11"); else throw new Exception(); continue;
                case'C': if (radix>12) list.add("12"); else throw new Exception(); continue;
                case'D': if (radix>13) list.add("13"); else throw new Exception(); continue;
                case'E': if (radix>14) list.add("14"); else throw new Exception(); continue;
                case'F': if (radix>15) list.add("15"); else throw new Exception(); continue;
            }
            throw new Exception();
        }
        if (part1.size()==0) throw new Exception();


        BigDecimal finalNumber = BigDecimal.ZERO;

        for (int i = 0; i < part1.size(); i++)
            finalNumber=finalNumber.add(new BigDecimal(part1.get(i)).multiply(new BigDecimal(radix).pow(part1.size()-1-i)));

        for (int i = 0; i < part2.size(); i++)
            finalNumber=finalNumber.add(new BigDecimal(part2.get(i)).multiply(new BigDecimal(Math.pow(radix, -(i+1)))));

        return sign + stripTrailingZeros(finalNumber.toPlainString());
    }



    //убрать незначащие нули после точки (и саму точку при необходимости)
    public static String stripTrailingZeros(String number) throws Exception{ //убрать незначащие нули после запятой
        /*if (number.equals("NaN") || number.equals("Infinity") || number.equals("-Infinity")  ||
                number.equals("∞") || number.equals("-∞"))*/
        if (number.matches("NaN|([-−]?((Infinity)|(∞)))")) return number;
        number = number.replaceAll("−", "-");
        number = number.replaceAll(",", ".");

        if (number.contains("."))
            for (int i = number.length()-1; i >=0 ; i--) {
                if (number.charAt(i) != '0'){
                    if (number.charAt(i) != '.') number = number.substring(0, i+1);
                    else number = number.substring(0, i);
                    break;
                }
            }

        return number;
    }
}
