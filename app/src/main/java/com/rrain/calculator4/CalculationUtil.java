package com.rrain.calculator4;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ermac on 22.01.2018.
 */

public final class CalculationUtil {
    private CalculationUtil() {}



    public static double addition (double a, double b) {return a+b;}
    public static double subtraction (double a, double b) {return a-b;}
    public static double multiplication (double a, double b) {return a*b;}
    public static double division (double a, double b) {return a/b;}
    public static double power(double a, double b) {return Math.pow(a, b);}
    public static double sqrt (double a) {return Math.sqrt(a);}
    public static double cbrt (double a) {return Math.cbrt(a);}
    public static double absoluteValue (double a) {return Math.abs(a);}
    public static double signum (double a) {return Math.signum(a);}
    public static double sinus (double a, int angleUnit) {if (angleUnit==1) a=Math.toRadians(a); return Math.sin(a);}
    public static double cosine (double a, int angleUnit) {if (angleUnit==1) a=Math.toRadians(a); return Math.cos(a);}
    public static double tangent (double a, int angleUnit){
        if (angleUnit==1) a=Math.toRadians(a);
        /*if((a%(Math.PI/2d)==0)&&(a%(Math.PI)!=0))return Double.NaN;*/
        return Math.tan(a);
    }
    public static double cotangent (double a, int angleUnit){
        if (angleUnit==1) a=Math.toRadians(a);
        /*if(a%(Math.PI)==0)return Double.NaN;*/
        return 1d/Math.tan(a);
    }
    public static double arcsinus (double a, int angleUnit) {a = Math.asin(a); return angleUnit==0 ? a : Math.toDegrees(a);}
    public static double arccosine (double a, int angleUnit) {a = Math.acos(a); return angleUnit==0 ? a : Math.toDegrees(a);}
    public static double arctangent (double a, int angleUnit) {a = Math.atan(a); return angleUnit==0 ? a : Math.toDegrees(a);}
    public static double arccotangent (double a, int angleUnit) {
        a = Math.PI/2d - Math.atan(a);
        return angleUnit==0 ? a : Math.toDegrees(a);
    }
    public static double sh (double a) {return Math.sinh(a);}
    public static double ch (double a) {return Math.cosh(a);}
    public static double th (double a) {return Math.tanh(a);}
    public static double cth (double a) {return 1/Math.tanh(a);}
    public static double arsh (double a) {return ln(a+sqrt(power(a, 2)+1));}
    public static double arch (double a) {return ln(a+sqrt(power(a, 2)-1));}
    public static double arth (double a) {return ln((1+a)/(1-a))/2;}
    public static double arcth (double a) {return ln((a+1)/(a-1))/2;}
    public static double ln (double a) {return Math.log(a);}
    public static double lg (double a) {return Math.log10(a);}
    public static double log (double a, double b) {return Math.log(b)/Math.log(a);}
    public static double involute (double a, int angleUnit)  {return tangent(a, angleUnit) - ((angleUnit==1) ? Math.toRadians(a) : a);}
    public static double factorial (double a) throws Exception{
        int b = 0;
        try {
            b = new BigDecimal(a).intValueExact();//если было 2.0, то возвратит 2; но если было 2.1, то будет исключение
            if (b<0) throw new Exception();
        } catch (Exception e) {return Double.NaN;}
        if(b==0) return 1d;
        double factorial = 1d;
        for (int i = 2; i <= b; i++) factorial*=i;
        return factorial;
    }
    public static double doubleFactorial (double a) throws Exception{
        int b = 0;
        try {
            b = new BigDecimal(a).intValueExact();//если было 2.0, то возвпатит 2; но если было 2.1, то будет исключение
            if (b<0) throw new Exception();
        } catch (Exception e) {return Double.NaN;}
        if(b==0) return 1d;
        double doubleFactorial;
        int i;
        if (b%2==0) {doubleFactorial = 2d; i = 4;} else {doubleFactorial = 1d; i = 3;}
        for (; i <= b; i+=2) doubleFactorial*=i;
        return doubleFactorial;
    }
    public static double isDegrees(double a, int angleUnit) {
        if (angleUnit==1) return a;
        else return Math.toRadians(a);
    }
    public static double percent(double a) {return a*0.01d;}





    public static double roundUp(double number, int digits) throws Exception{
        if (number == Double.NaN || number == Double.POSITIVE_INFINITY || number == Double.NEGATIVE_INFINITY)
            return number;
        return new BigDecimal(number).setScale(digits, RoundingMode.HALF_UP).doubleValue();
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















    /*
    схема

    StringValue [from Double.toString] -> roundUp -> toPlainString -> stripTrailingZeros ->

                               |-> if targetRadix !=10 -> decToOther ------|
    -> redesignateInfinities --|                                           |-> divideDigits ->
                               |-> if targetRadix ==10 -> exponentFormat --|

    -> [toTextView]



    [toTextView] -> removeBitDividers -> toPlainString -> stripTrailingZeros -> toDec  ->

    -> redesignateInfinities -> decToOther -> divideDigits -> [toTextView]
    */


    public static String roundUp(String number, int newScale) throws Exception{
        /*if (number.equals("NaN") || number.equals("Infinity") || number.equals("-Infinity"))
            return number;*/
        if (number.matches("NaN|([-−]?((Infinity)|(∞)))")) return number;
        number = number.replaceAll("−", "-");
        number = number.replaceAll(",", ".");
        return new BigDecimal(number).setScale(newScale, RoundingMode.HALF_UP).toString();
    }

    //вернуть число без экспоненты
    public static String toPlainString(String number) throws Exception{ //вернуть число без экспоненты
        /*if (number.equals("NaN") || number.equals("Infinity") || number.equals("-Infinity") ||
                number.equals("∞") || number.equals("-∞"))
            return number;*/
        if (number.matches("NaN|([-−]?((Infinity)|(∞)))")) return number;
        number = number.replaceAll("−", "-");
        number = number.replaceAll(",", ".");
        return new BigDecimal(number).toPlainString();
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

    //переобозначить бесконечности символом ∞
    public static String redesignateInfinities(String number) throws Exception{
        switch (number){
            case "Infinity": case "∞":                                  return "∞";
            case "-Infinity": case "-∞": case "−Infinity": case "−∞":   return "-∞";
            default: return number;
        }
    }




    //перевести из десятичной в другую систему счисления
    public static String decToOther(String number, int targetRadix)throws Exception{
        if (targetRadix==10) return number;
        switch (number) {
            case "NaN":                                                 return "NaN";
            case "Infinity": case "∞":                                  return "∞";
            case "-Infinity": case "-∞": case "−Infinity": case "−∞":   return "-∞";
        }

        String sign = "";
        if (number.startsWith("+")) number = number.substring(1, number.length());
        if (number.startsWith("-") || number.startsWith("−")) {number = number.substring(1, number.length()); sign = "−";}

        String number1 = "";
        String number2 = "";

        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i)=='.' || number.charAt(i)==',') {
                number1 = number.substring(0, i);
                number2 = "0" + number.substring(i,number.length());
                break;
            }
            if (i == number.length()-1) number1 = number;
        }

        BigInteger[] arr = {
                new BigInteger("10"),
                new BigInteger("11"),
                new BigInteger("12"),
                new BigInteger("13"),
                new BigInteger("14"),
                new BigInteger("15"),
                new BigInteger(Integer.toString(targetRadix))
        };

        BigInteger n1 = new BigInteger(number1);
        String part1 = "";
        while(-1 != n1.compareTo(arr[6])){
            BigInteger p = n1.mod(arr[6]);
            n1 = n1.divide(arr[6]);
            if      (p.equals(arr[0])) part1 = "A" + part1;
            else if (p.equals(arr[1])) part1 = "B" + part1;
            else if (p.equals(arr[2])) part1 = "C" + part1;
            else if (p.equals(arr[3])) part1 = "D" + part1;
            else if (p.equals(arr[4])) part1 = "E" + part1;
            else if (p.equals(arr[5])) part1 = "F" + part1;
            else part1 = String.valueOf(p) + part1;
        }
        if      (n1.equals(arr[0])) part1 = "A" + part1;
        else if (n1.equals(arr[1])) part1 = "B" + part1;
        else if (n1.equals(arr[2])) part1 = "C" + part1;
        else if (n1.equals(arr[3])) part1 = "D" + part1;
        else if (n1.equals(arr[4])) part1 = "E" + part1;
        else if (n1.equals(arr[5])) part1 = "F" + part1;
        else part1 = String.valueOf(n1) + part1;



        if (number2.isEmpty()) return sign + part1;
        else {
            BigDecimal n2 = new BigDecimal(number2);
            String part2 = "";

            for (int i = 0; i < 15; i++) {
                if (n2.compareTo(BigDecimal.ZERO) == 0) break;

                n2 = n2.multiply(new BigDecimal(targetRadix));
                BigInteger p = n2.toBigInteger();
                n2 = n2.remainder(BigDecimal.ONE);

                if      (p.equals(arr[0])) part2 += "A";
                else if (p.equals(arr[1])) part2 += "B";
                else if (p.equals(arr[2])) part2 += "C";
                else if (p.equals(arr[3])) part2 += "D";
                else if (p.equals(arr[4])) part2 += "E";
                else if (p.equals(arr[5])) part2 += "F";
                else part2 += String.valueOf(p);
            }

            return stripTrailingZeros(sign + part1 + "." + part2);
        }

    }




    //степень десятки в экспоненту (только для dec чисел)
    //mode - интервал степени десятки экспоненты по принципу "каждая mode"
    public static String exponentFormat(String number, int mode) throws Exception{
        if (mode<=0) return number;
        switch (number) {
            case "NaN":                                                 return "NaN";
            case "Infinity": case "∞":                                  return "∞";
            case "-Infinity": case "-∞": case "−Infinity": case "−∞":   return "-∞";
        }

        number = number.replaceAll(",", ".");

        String sign = "";
        if (number.startsWith("+")) number = number.substring(1, number.length());
        if (number.startsWith("-") || number.startsWith("−")) {number = number.substring(1, number.length()); sign = "−";}

        /*int pointIndex = number.length();*/
        int pointIndex;
        if (!number.contains(".")) number += '.';
        pointIndex = number.indexOf('.');
        /*for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i)=='.' || number.charAt(i)==',') {
                pointIndex = i;
                break;
            }
        }*/

        int exponent;

        if (pointIndex > 6){

            exponent = pointIndex-1 - (pointIndex-1) % mode;

            number = number.substring(0, pointIndex-exponent) + '.' + number.substring(pointIndex-exponent, pointIndex) +
                    number.substring(pointIndex+1, number.length());
            number = stripTrailingZeros(number);
            number += "E" + exponent;
        } else if(number.startsWith("0.000000") || number.startsWith("0,000000")){
            exponent = 0;
            String number1 = number.substring(2, number.length());
            for (int i = 0; i < number1.length(); i++) {
                if (number1.charAt(i) != '0') {
                    exponent = (i+mode)/mode * mode;
                    number1 = number1.substring(i/mode * mode, number1.length());
                    break;
                }
            }

            while (number1.length() < mode) number1+="0";
            if (number1.length()>mode) number1 = number1.substring(0, mode)+"."+number1.substring(mode, number1.length());

            for (int i = 0; i < number1.length(); i++) {
                if (number1.charAt(i)!='0') {number1 = number1.substring(i, number1.length()); break;}
            }

            /*number = number1 + "E" + (-exponent);*/
            number = number1 + "E−" + exponent;
        } else if (pointIndex==number.length()-1) number = number.substring(0, number.length()-1);


        return sign+number;
    }


    //разделить разряды символом divider
    public static String divideDigits(String number, String divider, int radix) throws Exception{
        if (divider == null || divider.length() == 0) return number;
        switch (number) {
            case "NaN":                                                     return "NaN";
            case "Infinity": case "∞":                                      return "∞";
            case "-Infinity": case "-∞": case "−Infinity": case "−∞":       return "-∞";
        }

        int sectionLength;
        switch (radix){
            case 2:
            case 8: sectionLength = 4; break;
            case 10:sectionLength = 3; break;
            case 16:sectionLength = 2; break;
            default:sectionLength = 3;
        }

        String sign = "";
        if (number.startsWith("+")) number = number.substring(1, number.length());
        if (number.startsWith("-") || number.startsWith("−")) {number = number.substring(1, number.length()); sign = "−";}

        number = number.replaceAll(",", ".");

        StringBuilder part1 = null;
        StringBuilder part2 = null;
        StringBuilder expPart = null;

        int end;
        if (radix == 10 && number.contains("E")) {
            end = number.indexOf('E');
            expPart = new StringBuilder(number.substring(end, number.length()));
        } else end = number.length();

        if (number.contains(".")) {
            part1 = new StringBuilder(number.substring(0, number.indexOf('.')));
            part2 = new StringBuilder(number.substring(number.indexOf('.')+1, end));
        } else part1 = new StringBuilder(number.substring(0, end));

        part1.reverse();
        for (int i = part1.length()-1; i >= sectionLength; i--) {
            if (i%sectionLength == 0) part1.insert(i, divider);
        }
        part1.reverse();

        if (part2 != null)
            for (int i = part2.length()-1; i >= sectionLength; i--) {
                if (i%sectionLength == 0) part2.insert(i, divider);
            }



        String finalNumber = sign + part1;
        if (part2 != null) {finalNumber+="."+part2;}
        if (expPart != null) {finalNumber += divider + expPart;}

        return finalNumber;
        /*int expIndex;
        int pointIndex;

        if (!number.contains(".")) pointIndex = number.length();
        else pointIndex = number.indexOf('.');

        if (radix == 10 && number.contains("E")) expIndex = number.indexOf("E");
        else  expIndex = number.length();

        StringBuilder sb = new StringBuilder(number);

        for (int i = expIndex-1; i > pointIndex+sectionLength; i--) {
            if ((i-pointIndex-1)%sectionLength==0) sb.insert(i, divider);
        }

        for (int i = pointIndex-sectionLength; i > pointIndex+sectionLength; i--) {
            if ((i-pointIndex-1)%sectionLength==0) sb.insert(i, divider);
        }*/








        /*int pointIndex;
        if (!number.contains(".")) number += '.';
        pointIndex = number.indexOf('.');


        int exponent;

        if (pointIndex > 6){

            exponent = pointIndex-1 - (pointIndex-1) % mode;

            number = number.substring(0, pointIndex-exponent) + '.' + number.substring(pointIndex-exponent, pointIndex) +
                    number.substring(pointIndex+1, number.length());
            number = stripTrailingZeros(number);
            number += "E" + exponent;
        } else if(number.startsWith("0.") && number.length() - pointIndex - 1 >= 6){

            exponent = (number.length() - pointIndex - 1) - (number.length() - pointIndex - 1) % mode;

            number = number.substring(0, pointIndex) + number.substring(pointIndex+1, pointIndex+exponent+1) + '.' +
                    number.substring(pointIndex+exponent+1, number.length());
            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i)!='0') {number = number.substring(i, number.length()); break;}
            }
            number = stripTrailingZeros(number);
            number += "E" + (-exponent);
        } else if (pointIndex==number.length()-1) number = number.substring(0, number.length()-1);*/

        /*sb.insert(0, sign);
        return sb.toString();*/
    }



    //удалить разделители разрядов
    public static String removeBitDividers(String number){
        return number.replaceAll("[\n _']", "");
    }

















   /* public static String roundUp(String number, int digits) throws Exception{
        if (number.equals("NaN") || number.equals("Infinity") || number.equals("-Infinity"))
            return number;
        return Double.toString(new BigDecimal(number).setScale(digits, RoundingMode.HALF_UP).doubleValue());
    }




    public static String DecToOther(@PlainString String number, int targetRadix)throws Exception{ //отрефакторено
        if (targetRadix==10) return number;
        switch (number) {
            case "NaN":                     return "NaN";
            case "Infinity": case "∞":      return "∞";
            case "-Infinity": case "-∞":    return "-∞";
        }

        String number1 = "";
        String number2 = "";

        for (int i = 0; i < number.length(); i++) {
            if (number.charAt(i)=='.') {
                number1 = number.substring(0, i);
                number2 = "0" + number.substring(i,number.length());
                break;
            }
            if (i == number.length()-1) number1 = number;
        }

        BigInteger[] arr = {
                new BigInteger("10"),
                new BigInteger("11"),
                new BigInteger("12"),
                new BigInteger("13"),
                new BigInteger("14"),
                new BigInteger("15"),
                new BigInteger(Integer.toString(targetRadix))
        };

        BigInteger n1 = new BigInteger(number1);
        String part1 = "";
        while(-1 != n1.compareTo(arr[6])){
            BigInteger p = n1.mod(arr[6]);
            n1 = n1.divide(arr[6]);
            if      (p.equals(arr[0])) part1 = "A" + part1;
            else if (p.equals(arr[1])) part1 = "B" + part1;
            else if (p.equals(arr[2])) part1 = "C" + part1;
            else if (p.equals(arr[3])) part1 = "D" + part1;
            else if (p.equals(arr[4])) part1 = "E" + part1;
            else if (p.equals(arr[5])) part1 = "F" + part1;
            else part1 = String.valueOf(p) + part1;
        }
        if      (n1.equals(arr[0])) part1 = "A" + part1;
        else if (n1.equals(arr[1])) part1 = "B" + part1;
        else if (n1.equals(arr[2])) part1 = "C" + part1;
        else if (n1.equals(arr[3])) part1 = "D" + part1;
        else if (n1.equals(arr[4])) part1 = "E" + part1;
        else if (n1.equals(arr[5])) part1 = "F" + part1;
        else part1 = String.valueOf(n1) + part1;



        if (number2.isEmpty()) return part1;
        else {
            BigDecimal n2 = new BigDecimal(number2);
            String part2 = "";

            for (int i = 0; i < 15; i++) {
                if (0 == n2.compareTo(BigDecimal.ZERO)) break;
                n2 = n2.multiply(new BigDecimal(targetRadix));
                BigInteger p = n2.toBigInteger();
                n2 = n2.remainder(BigDecimal.ONE);

                if (p.equals(arr[0])) part2 += "A";
                else if (p.equals(arr[1])) part2 += "B";
                else if (p.equals(arr[2])) part2 += "C";
                else if (p.equals(arr[3])) part2 += "D";
                else if (p.equals(arr[4])) part2 += "E";
                else if (p.equals(arr[5])) part2 += "F";
                else part2 += String.valueOf(p);
            }

            return part1 + "." + part2;
        }

    }





    static String toDecPlainString(String number, int radix) throws Exception{
        switch (number) {
            case "NaN": return number;
            case "Infinity": case "∞": return "∞";
            case "-Infinity": case "-∞": return "-∞";
        }
        if (radix!=10) return (String)toDec(number, 0, radix)[0];

        return new BigDecimal(number).stripTrailingZeros().toPlainString();
    }






    @Documented
    @Target(ElementType.PARAMETER )
    @Retention(RetentionPolicy.SOURCE)
    @interface PlainString{}//должна быть обычная строка, содержащая     <<    1234567890.   >> и бесконечности и NaN
    *//*  ПРО АННОТАЦИИ:
        @Target – Эта аннотация позволит вам указать те java элементы, к которой аннотация должна быть применена.
    Возможные типы для применения такие: ANNOTATION_TYPE, CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER и TYPE.
    В нашей @ReconField аннотация для уровня FIELD.
        @Retention – Эта аннотация позволит вам указать, когда аннотация будет доступна. Возможные значения: CLASS, RUNTIME и SOURCE. Так как мы будет обрабатывать аннотации в RUNTIME, мы должны установить эти значения.
        @Documented - чтобы аннотация появлялась в параметрах метода при его вызове
*//*


*/











    public static class Expression {

        private int radix;
        private int angleUnit;
        private String expression;

        private boolean doSaveConverted;
        public String convertedExpression = null;

        public Expression(String expression, int angleUnit, int radix, boolean doSaveConverted) {
            this.radix = radix;
            this.angleUnit = angleUnit;
            this.expression = expression;
            this.doSaveConverted = doSaveConverted;
        }





        private ArrayList<Object> exprList = new ArrayList<>();


        public String calculateToDec() throws Exception{
            String expression = this.expression;

            if(expression.isEmpty()) {throw new EmptyExpressionException();}

            exprList.clear();
            expression = removeBitDividers(expression);
            expression = "(" + expression + ")";



            try {convert(expression);}
            catch (WrongBracketsException e) {throw e;}
            catch (ArrayIndexOutOfBoundsException e) {throw new WrongBracketsException();}
            catch (Exception e) {
                /*if (bracketsStack.size()!=0)throw new WrongBracketsException();
                else*/ throw new UnknownFunctionException();
            }

            if (doSaveConverted) saveConverted();

            try {calculateConvertedExpression();}
            catch (WrongBracketsException e) {throw new WrongBracketsException();}
            catch (Exception e) {throw new IncorrectExpressionException();}

            return Double.toString((double)exprList.get(0));
        }


        void saveConverted(){
            convertedExpression = new String();
            for (int i = 0; i < exprList.size(); i++) {
                convertedExpression+= exprList.get(i);
            }
        }






        private static Object[] toDec(String expr, int start, int radix) throws Exception{
            Object[] numberArray = new Object[2];

            if (expr.length()-start>0) if (expr.substring(start, start+1).equals("∞")) {numberArray[0]="Infinity"; numberArray[1]=1; return numberArray;}
            if (expr.length()-start>1) if (expr.substring(start, start+2).matches("[-−]∞")) {numberArray[0]="-Infinity"; numberArray[1]=2; return numberArray;}
            /*if (expr.length()-start>1) if (expr.substring(start, start+2).equals("-∞") || expr.substring(start, start+2).equals("−∞")) {numberArray[0]="-Infinity"; numberArray[1]=2; return numberArray;}*/
            if (expr.length()-start>7) if (expr.substring(start, start+8).equals("Infinity")) {numberArray[0]="Infinity"; numberArray[1]=8; return numberArray;}
            if (expr.length()-start>8) if (expr.substring(start, start+9).matches("[-−]Infinity")) {numberArray[0]="-Infinity"; numberArray[1]=9; return numberArray;}
            if (expr.length()-start>2) if (expr.substring(start, start+3).equals("NaN")) {numberArray[0]="NaN"; numberArray[1]=3; return numberArray;}

            ArrayList<String> number = new ArrayList<>();
            for (int i = start; i < expr.length(); i++) {
                switch (expr.charAt(i)){
                    case'0': if (radix>0) number.add("0"); else throw new Exception(); continue;
                    case'.':case',': number.add("."); continue;
                    case'1': if (radix>1) number.add("1"); else throw new Exception(); continue;
                    case'2': if (radix>2) number.add("2"); else throw new Exception(); continue;
                    case'3': if (radix>3) number.add("3"); else throw new Exception(); continue;
                    case'4': if (radix>4) number.add("4"); else throw new Exception(); continue;
                    case'5': if (radix>5) number.add("5"); else throw new Exception(); continue;
                    case'6': if (radix>6) number.add("6"); else throw new Exception(); continue;
                    case'7': if (radix>7) number.add("7"); else throw new Exception(); continue;
                    case'8': if (radix>8) number.add("8"); else throw new Exception(); continue;
                    case'9': if (radix>9) number.add("9"); else throw new Exception(); continue;
                    case'A': if (radix>10) number.add("10"); else throw new Exception(); continue;
                    case'B': if (radix>11) number.add("11"); else throw new Exception(); continue;
                    case'C': if (radix>12) number.add("12"); else throw new Exception(); continue;
                    case'D': if (radix>13) number.add("13"); else throw new Exception(); continue;
                    case'E': if (radix>14) number.add("14");
                    else if (radix==10){
                        switch (expr.charAt(i+1)){
                            case'+': number.add("E"); /*number.add("+");*/ i++; break;
                            case'-': case'−': number.add("E"); number.add("-"); i++; break;
                            default: number.add("E"); break;
                        }
                    } else throw new Exception(); continue;
                    case'F': if (radix>15) number.add("15"); else throw new Exception(); continue;
                }
                break;
            }
            if (number.size()==0) throw new Exception();

            numberArray[1] = number.size();

            if (radix==10) {
                StringBuilder n = new StringBuilder();
                for (int i = 0; i < number.size(); i++) n.append(number.get(i));
                numberArray[0] = n.toString();
                return numberArray;
            }

            int pointPosition = number.size();
            for (int i = 0; i < number.size(); i++)
                if (number.get(i).equals(".")){pointPosition = i; break;}



            BigDecimal finalNumber = new BigDecimal(0);
            for (int i = 0; i < pointPosition; i++) {
                finalNumber=finalNumber.add((new BigDecimal(number.get(i))).multiply((new BigDecimal(radix)).pow(pointPosition-1-i)));
            }
            int degree = -1;
            for (int i = pointPosition+1; i < number.size(); i++) {
                finalNumber=finalNumber.add((new BigDecimal(number.get(i))).multiply(new BigDecimal(Math.pow(radix, degree))));
                degree--;
            }

            numberArray[0]= finalNumber.stripTrailingZeros().toPlainString();
            return numberArray;
        }










/*
        public static final int BRACKET_NOT_AUTOCLOSE_MULT_AFTER = 0;
        public static final int BRACKET_DO_AUTOCLOSE_MULT_AFTER = 1;
        public static final int BRACKET_DO_AUTOCLOSE_NO_MULT = 2;
        public static final int BRACKET_NOT_AUTOCLOSE_NO_MULT = 3;



        public static final int TYPE_EMPTY = -1;
        public static final int TYPE_NUMBER = 0;
        public static final int TYPE_OPEN_BRACKET = 1;
        public static final int TYPE_CLOSE_BRACKET = 2;
        public static final int TYPE_ARITHMETIC = 3;
        public static final int TYPE_FUNC = 4;

        public static final int TYPE_FACTORIAL = 6;
        public static final int TYPE_POST_FUNC = 7;
        public static final int TYPE_DOUBLE_FACTORIAL = 8;

        public static final int TYPE_DOUBLE_FUNC = 10;
*/



        private byte prevObjectType;
        //-1_empty
        // 0_number_____π_pi_e_∞
        // 1_(_[    2_)_]
        // 3_arithmetic_operation_+_-_−_*_/_^
        // 4_function___func[arg1]___abs_sin_cos_tg_cot&ctg_sgn_sqrt_√_cbrt_∛_∜_ln_lg_asin_acos_atan_acot_arctg_arcsin_arccos_arcctg
        //      5_Exponent_____NOT_USED
        // 6_!    7_°_%    8_!!
        //      9_NOT_USED
        // 10_double_function___func[arg1][arg2]___log

        private byte backupPrevObjectType;
        private String n;

        private ArrayList<Integer> bracketsStack;
        //0_All_is_ok---Not_need_to_close_and_with_after_sign_*
        //1_Need_to_close_and_with_after_sign_*
        //2_Need_to_close_and_without_after_sign
        //3_Not_need_to_close_and_without_after_sign
        private void autocloseBracketsType1(){
            while (bracketsStack.get(bracketsStack.size()-1)==1) {
                exprList.add(")");
                bracketsStack.remove(bracketsStack.size()-1);
            }
        }





        private Object[] numberArray;


        private void switchForNumbers(){
            switch (prevObjectType){
                case 0: case 2: exprList.add("*"); break;
                case 6: case 7: case 8:
                    autocloseBracketsType1();
                    exprList.add("*");
                    break;
                case 4: exprList.add("("); bracketsStack.add(1); break;
                case 10: exprList.add("("); bracketsStack.add(2); break;
            }
            prevObjectType = 0;
        }

        private void switchForFunctions(){
            switch (prevObjectType){
                case 2: exprList.add("*"); break;
                case 0: case 6: case 7: case 8:
                    autocloseBracketsType1();
                    exprList.add("*");
                    break;
                case 4: exprList.add("("); bracketsStack.add(1); break;
                case 10: exprList.add("("); bracketsStack.add(2); break;
            }
            prevObjectType = 4;
        }

        private void switchForDoubleFunctions(){
            switch (prevObjectType){
                case 0: case 6: case 7: case 8:
                    autocloseBracketsType1();
                    exprList.add("*");
                    break;
                case 2: exprList.add("*"); break;
                case 4: exprList.add("("); bracketsStack.add(1); break;
                case 10: exprList.add("("); bracketsStack.add(2); break;
            }
            prevObjectType = 10;
        }





        private void convert(String expression) throws Exception{

            prevObjectType = -1;
            backupPrevObjectType = -1;
            n = "";
            bracketsStack = new ArrayList<>(2);

            for (int i = 0; i < expression.length(); i++) {

                /*switch (expression.charAt(i)){case ' ': case '\n': case '_': continue;}//space&enter&_*/




                switch(expression.charAt(i)){//0.,123456789ABCDEF
                    case '0':case '.':case ',':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':
                        switchForNumbers();
                        numberArray = toDec(expression, i, radix);
                        exprList.add(Double.parseDouble((String)numberArray[0]));
                        i+=((int)numberArray[1]-1);
                        continue;
                }




            /*switch(expression.charAt(i)){//0.123456789E
                case '0': n+="0"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '.': case ',': n+="."; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '1': n+="1"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '2': n+="2"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '3': n+="3"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '4': n+="4"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '5': n+="5"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '6': n+="6"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '7': n+="7"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '8': n+="8"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case '9': n+="9"; if(prevObjectType==5) prevObjectType=backupPrevObjectType; continue;
                case 'E': n+="E"; backupPrevObjectType = prevObjectType;  prevObjectType = 5; continue;
                default:
                    if(!(n.isEmpty()) && prevObjectType!=5) {
                        switchForNumbers();
                        exprList.add(n); n = "";
                    }
                    break;
            }*/

                /*try {

                    switch (expression.substring(i, i+1)){//√³_√⁴
                        case "√³":
                            switchForFunctions();
                            exprList.add("∛");
                            i++;
                            continue;

                        case "√⁴":
                            switchForFunctions();
                            exprList.add("∜");
                            i++;
                            continue;
                    }
                } catch (Exception e) {*//*e.printStackTrace();*//*}*/

                switch (expression.charAt(i)){//π_e_∞_!&!!_°_%_√_∛_∜_√³_√⁴
                    case 'π':
                        switchForNumbers();
                        exprList.add(Math.PI);
                        continue;

                    case 'e':
                        switchForNumbers();
                        exprList.add(Math.E);
                        continue;

                    case '∞':
                        switchForNumbers();
                        exprList.add(Double.POSITIVE_INFINITY);
                        continue;

                    case '!':
                        switch (prevObjectType){
                            case 6: exprList.set(exprList.size()-1, "!!"); prevObjectType = 8; break;
                            case 8: throw new Exception();
                            default: exprList.add("!"); prevObjectType = 6; break;
                        }
                        continue;

                    case '°':
                        exprList.add("°"); prevObjectType = 7; continue;

                    case '%':
                        exprList.add("%"); prevObjectType = 7; continue;

                    case '√':
                        switch (expression.charAt(i+1)) {
                            case '³':
                                switchForFunctions();
                                exprList.add("∛");
                                i++;
                                continue;

                            case '⁴':
                                switchForFunctions();
                                exprList.add("∜");
                                i++;
                                continue;
                        }
                        switchForFunctions();
                        exprList.add("√"); continue;

                    case '∛':
                        switchForFunctions();
                        exprList.add("∛"); continue;

                    case '∜':
                        switchForFunctions();
                        exprList.add("∜"); continue;

                    case '²':
                        autocloseBracketsType1();
                        exprList.add("^^"); exprList.add(2d); // TODO: 23.08.2018 power
                        prevObjectType=0;
                        continue;

                    case '³':
                        autocloseBracketsType1();
                        exprList.add("^^"); exprList.add(3d);
                        prevObjectType=0;
                        continue;

                    case '⁴':
                        autocloseBracketsType1();
                        exprList.add("^^"); exprList.add(4d);
                        prevObjectType=0;
                        continue;
                }



                switch (expression.charAt(i)){//()[]
                    case '[': case '(':
                        switch (prevObjectType){
                            case 0: case 2: case 6: case 7: case 8:
                                if (bracketsStack.get(bracketsStack.size()-1)==2) {
                                    exprList.add(")"); bracketsStack.remove(bracketsStack.size()-1);}
                                else {
                                    autocloseBracketsType1();
                                    exprList.add("*");
                                }
                                bracketsStack.add(0); break;
                            case 10: bracketsStack.add(3); break;
                            default: bracketsStack.add(0);
                        }
                        exprList.add("(");
                        prevObjectType = 1;
                        continue;

                    case ']': case ')':
                        loop: while(true){
                            if (bracketsStack.size()>0)
                            switch (bracketsStack.get(bracketsStack.size()-1)){
                                case 0: exprList.add(")"); bracketsStack.remove(bracketsStack.size()-1);
                                    if (bracketsStack.size()>0 && bracketsStack.get(bracketsStack.size() - 1) == 2) continue loop;

                                    prevObjectType = 2;
                                    break loop;

                                case 1: exprList.add(")"); bracketsStack.remove(bracketsStack.size()-1); break;
                                case 2: exprList.add(")"); bracketsStack.remove(bracketsStack.size()-1); prevObjectType = 4; break loop;
                                case 3: exprList.add(")"); bracketsStack.remove(bracketsStack.size()-1); prevObjectType = 4; break loop;
                            }
                            else throw new WrongBracketsException();
                        }
                        continue;
                }



                switch (expression.charAt(i)){//+-×*÷/^
                    case '+':
                        switch(prevObjectType){
                            /*case -1:*/
                            case 1: break;
                            /*while (bracketsStack.get(bracketsStack.size()-1)==1) {bracketsStack.remove(bracketsStack.size()-1); exprList.add(")");}*/
                            /*case 5: n+="+"; prevObjectType=backupPrevObjectType; break;*/
                            case 4: exprList.add("("); bracketsStack.add(1); prevObjectType = 3; break;
                            case 10: exprList.add("("); bracketsStack.add(2); prevObjectType = 3; break;
                            default:
                                autocloseBracketsType1();
                                exprList.add("+"); prevObjectType = 3; break;
                        }
                        continue;

                    case '-': case '−':
                        switch (prevObjectType){
                        /*case -1: case 1:
                            while (bracketsStack.get(bracketsStack.size()-1)==1) {bracketsStack.remove(bracketsStack.size()-1); exprList.add(")");}
                            *//*exprList.add("0");*//* exprList.add("-"); prevObjectType = 3; break;*/
                            /*case 5: n+="-"; prevObjectType=backupPrevObjectType; break;*/
                            case 4: exprList.add("("); bracketsStack.add(1); exprList.add("-"); prevObjectType = 3; break;
                            case 10: exprList.add("("); bracketsStack.add(2); exprList.add("-"); prevObjectType = 3; break;
                            default:
                                autocloseBracketsType1();
                                exprList.add("-"); prevObjectType = 3; break;
                        }
                        continue;

                    case '×': case '*':
                        autocloseBracketsType1();
                        exprList.add("*"); prevObjectType = 3; continue;

                    case '/': case '÷':
                        autocloseBracketsType1();
                        exprList.add("/"); prevObjectType = 3; continue;

                    case '^':
                        autocloseBracketsType1();
                        exprList.add("^"); prevObjectType = 4; continue;
                }






                switch (expression.substring(i, i+2)) {//tg_ln_lg_sh_ch_th_pi
                    case "tg":
                        switchForFunctions();
                        exprList.add("tg");
                        i++;
                        continue;

                    case "ln":
                        switchForFunctions();
                        exprList.add("ln");
                        i++;
                        continue;

                    case "lg":
                        switchForFunctions();
                        exprList.add("lg");
                        i++;
                        continue;

                    case "lb":
                        switchForFunctions();
                        exprList.add("lb");
                        i++;
                        continue;

                    case "sh":
                        switchForFunctions();
                        exprList.add("sh");
                        i++;
                        continue;

                    case "ch":
                        switchForFunctions();
                        exprList.add("ch");
                        i++;
                        continue;

                    case "th":
                        switchForFunctions();
                        exprList.add("th");
                        i++;
                        continue;

                    case "pi":
                        switchForNumbers();
                        exprList.add(Math.PI);
                        i++;
                        continue;
                }


                try {
                    switch (expression.substring(i, i+4)){//cosh_sinh_tanh_coth
                        case "sinh":
                            switchForFunctions();
                            exprList.add("sh");
                            i+=3;
                            continue;

                        case "cosh":
                            switchForFunctions();
                            exprList.add("ch");
                            i+=3;
                            continue;

                        case "tanh":
                            switchForFunctions();
                            exprList.add("th");
                            i+=3;
                            continue;

                        case "coth":
                            switchForFunctions();
                            exprList.add("cth");
                            i+=3;
                            continue;
                    }
                } catch (Exception e) {/*e.printStackTrace();*/}


                switch (expression.substring(i, i+3)){//abs_sin_cos_cot&ctg_tan_log_cth_bin_oct_dec_hex_inv
                    case "abs":
                        switchForFunctions();
                        exprList.add("abs");
                        i+=2;
                        continue;

                    case "sin":
                        switchForFunctions();
                        exprList.add("sin");
                        i+=2;
                        continue;

                    case "cos":
                        switchForFunctions();
                        exprList.add("cos");
                        i+=2;
                        continue;

                    case "cot": case "ctg":
                        switchForFunctions();
                        exprList.add("ctg");
                        i+=2;
                        continue;

                    case "tan":
                        switchForFunctions();
                        exprList.add("tg");
                        i+=2;
                        continue;

                    case "log":
                        switchForDoubleFunctions();
                        exprList.add("log");
                        i+=2;
                        continue;

                    case "cth":
                        switchForFunctions();
                        exprList.add("cth");
                        i+=2;
                        continue;

                    case "inv":
                        switchForFunctions();
                        exprList.add("inv");
                        i+=2;
                        continue;

                    case "sgn":
                        switchForFunctions();
                        exprList.add("sgn");
                        i+=2;
                        continue;


                    case "bin":
                        switchForNumbers();
                        numberArray = toDec(expression, i+3, 2);
                        exprList.add(Double.parseDouble((String)numberArray[0]));
                        i+=((int)numberArray[1]-1+3);
                        continue;
                    case "oct":
                        switchForNumbers();
                        numberArray = toDec(expression, i+3, 8);
                        exprList.add(Double.parseDouble((String)numberArray[0]));
                        i+=((int)numberArray[1]-1+3);
                        continue;
                    case "dec":
                        switchForNumbers();
                        numberArray = toDec(expression, i+3, 10);
                        exprList.add(Double.parseDouble((String)numberArray[0]));
                        i+=((int)numberArray[1]-1+3);
                        continue;
                    case "hex":
                        switchForNumbers();
                        numberArray = toDec(expression, i+3, 16);
                        exprList.add(Double.parseDouble((String)numberArray[0]));
                        i+=((int)numberArray[1]-1+3);
                        continue;

                }



                switch (expression.substring(i, i+4)){//sqrt_cbrt_asin_acos_atan_acot_arsh_arch_arth
                    case "sqrt":
                        switchForFunctions();
                        exprList.add("√");
                        i+=3;
                        continue;

                    case "cbrt":
                        switchForFunctions();
                        exprList.add("∛");
                        i+=3;
                        continue;

                    case "asin":
                        switchForFunctions();
                        exprList.add("arcsin");
                        i+=3;
                        continue;

                    case "acos":
                        switchForFunctions();
                        exprList.add("arccos");
                        i+=3;
                        continue;

                    case "atan":
                        switchForFunctions();
                        exprList.add("arctg");
                        i+=3;
                        continue;

                    case "acot":
                        switchForFunctions();
                        exprList.add("arcctg");
                        i+=3;
                        continue;

                    case "arsh":
                        switchForFunctions();
                        exprList.add("arsh");
                        i+=3;
                        continue;

                    case "arch":
                        switchForFunctions();
                        exprList.add("arch");
                        i+=3;
                        continue;

                    case "arth":
                        switchForFunctions();
                        exprList.add("arth");
                        i+=3;
                        continue;
                }




                switch (expression.substring(i, i+5)) {//arctg_arcth
                    case "arctg":
                        switchForFunctions();
                        exprList.add("arctg");
                        i+=4;
                        continue;

                    case "arcth":
                        switchForFunctions();
                        exprList.add("arcth");
                        i+=4;
                        continue;
                }



                switch (expression.substring(i, i+6)){//arcsin_arccos_arcctg
                    case "arcsin":
                        switchForFunctions();
                        exprList.add("arcsin");
                        i+=5;
                        continue;

                    case "arccos":
                        switchForFunctions();
                        exprList.add("arccos");
                        i+=5;
                        continue;

                    case "arcctg":
                        switchForFunctions();
                        exprList.add("arcctg");
                        i+=5;
                        continue;
                }



            /*switch (expression.substring(i, i+8)) {//quadroot
                case "quadroot":
                    switchForFunctions();
                    exprList.add("quadroot");
                    i+=7;
                    continue;
            }
            */

                throw new Exception();
            }

        }








        private void calculateConvertedExpression()throws Exception{
            ArrayList<Integer> position = new ArrayList<>(2);
            int size = exprList.size();
            int i = 0;
            while(i < size){
                if (exprList.get(i).equals("(")) {position.add(i); i++;}
                else if (exprList.get(i).equals(")")) {

                    calculatePart(position.get(position.size()-1)+1, i-1);
                    size-=(i-position.get(position.size()-1));
                    i-=(i-position.get(position.size()-1));

                    position.remove(position.size()-1);
                } else i++;
            }
            if (position.size()!=0) throw new WrongBracketsException();
            if (exprList.size()!=1) throw new Exception();
        }


        /*@param start - индекс открывающей скобки+1
        *@param end - индекс закрывающей скобки-1
        * внутри скобок нет больше скобок
        */
        private void calculatePart(final int start, int end) throws Exception{
            int u;

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("log")){
                    exprList.set(u, log((double)exprList.get(u+1), (double)exprList.get(u+2)));
                    exprList.remove(u+2); exprList.remove(u+1); end-=2;
                } else u++;
            }



            u = start;
            while(u<=end){
                if (exprList.get(u).equals("√")/* || exprList.get(u).equals("sqrt")*/){
                    exprList.set(u, sqrt((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("∛")/* || exprList.get(u).equals("cbrt")*/){
                    exprList.set(u, cbrt((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("∜")/* || exprList.get(u).equals("quadroot")*/){
                    exprList.set(u, power((double)exprList.get(u+1), 0.25d));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }



            u = start;
            while(u<=end){
                if (exprList.get(u).equals("sin")){
                    exprList.set(u, sinus((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("cos")){
                    exprList.set(u, cosine((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("tg")){
                    exprList.set(u, tangent((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("ln")){
                    exprList.set(u, ln((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("lg")){
                    exprList.set(u, lg((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("lb")){
                    exprList.set(u, log(2d, (double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("ctg")){
                    exprList.set(u, cotangent((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arcsin")){
                    exprList.set(u, arcsinus((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arccos")){
                    exprList.set(u, arccosine((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arctg")){
                    exprList.set(u, arctangent((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arcctg")){
                    exprList.set(u, arccotangent((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("sh")){
                    exprList.set(u, sh((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("ch")){
                    exprList.set(u, ch((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("th")){
                    exprList.set(u, th((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("cth")){
                    exprList.set(u, cth((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arsh")){
                    exprList.set(u, arsh((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arch")){
                    exprList.set(u, arch((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arth")){
                    exprList.set(u, arth((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("arcth")){
                    exprList.set(u, arcth((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("inv")){
                    exprList.set(u, (double)involute((double)exprList.get(u+1), angleUnit));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("abs")){
                    exprList.set(u, absoluteValue((double)exprList.get(u+1)));
                    exprList.remove(u+1); end-=1;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("sgn")){
                    exprList.set(u, signum(   roundUp((double)exprList.get(u+1), 15)   )); // TODO: 28.05.2018 rounding double
                    exprList.remove(u+1); end-=1;
                } else u++;
            }





            u = start;
            while(u<=end){

                if (exprList.get(u) instanceof String)

                switch ((String)exprList.get(u)){
                    case "^^": // TODO: 23.08.2018 power
                        exprList.set(u-1, power((double)exprList.get(u-1), (double)exprList.get(u+1)));
                        exprList.remove(u+1); exprList.remove(u); end-=2;
                        break;
                    case "!":
                        exprList.set(u-1, factorial(roundUp((double)exprList.get(u-1), 14)));
                        exprList.remove(u); end-=1;
                        break;
                    case "!!":
                        exprList.set(u-1, doubleFactorial(roundUp((double)exprList.get(u-1), 14)));
                        exprList.remove(u); end-=1;
                        break;
                    case "°":
                        exprList.set(u-1, isDegrees((double)exprList.get(u-1), angleUnit));
                        exprList.remove(u); end-=1;
                        break;
                    case "%":
                        exprList.set(u-1, percent((double)exprList.get(u-1)));
                        exprList.remove(u); end-=1;

                        boolean calculatePercent = false;
                        int index1 = u-3;

                        if (exprList.get(u-2).equals("+") || exprList.get(u-2).equals("-"))
                            if ((u-3)>=start)
                                while (true){
                                    if(exprList.get(index1).equals("+") || exprList.get(index1).equals("-")){index1++; calculatePercent = true; break;}
                                    else {if (index1>start) index1--; else {calculatePercent = true; break;}}
                                }


                        if (calculatePercent){
                            exprList.add(u-1, "*"); end++;
                            for (int i = u-3; i >= index1; i--) {
                                exprList.add(u-1, exprList.get(i));
                                end++;
                            }

                            int index2 = u-1;

                            while(true){
                                if ((index2)==end) break;
                                if (exprList.get(index2+1).equals("+") || exprList.get(index2+1).equals("-")) break;
                                index2++;
                            }

                            calculatePercentPart(index1, index2);
                            end -= (index2 - index1);
                            u -= (u - index1);
                        }

                        break;
                    default: u++; break;
                }

                else u++;
            }




        /*u = start;
        while(u<=end){
            if (exprList.get(u).equals("!")){
                exprList.set(u-1, String.valueOf(factorial(Double.parseDouble(roundUp(exprList.get(u-1), 14)))));
                exprList.remove(u); end-=1; count+=1;
            } else u++;
        }


        u = start;
        while(u<=end){
            if (exprList.get(u).equals("!!")){
                exprList.set(u-1, String.valueOf(doubleFactorial(Double.parseDouble(roundUp(exprList.get(u-1), 14)))));
                exprList.remove(u); end-=1; count+=1;
            } else u++;
        }


        u = start;
        while(u<=end){
            if (exprList.get(u).equals("°")){
                exprList.set(u-1, String.valueOf(isDegrees(Double.parseDouble(exprList.get(u-1)))));
                exprList.remove(u); end-=1; count+=1;
            } else u++;
        }*/






            u = start;
            while(u<=end){
                if (exprList.get(u).equals("^")){
                    exprList.set(u-1, power((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("/")){
                    exprList.set(u-1, division((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("*")){
                    exprList.set(u-1, multiplication((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("-")){
                    if (u==start){
                        exprList.set(u, -((double)exprList.get(u+1)));
                        exprList.remove(u+1); end-=1;
                    }
                    else{
                        exprList.set(u-1, subtraction((double)exprList.get(u-1), (double)exprList.get(u+1)));
                        exprList.remove(u+1); exprList.remove(u); end-=2;
                    }
                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("+")){
                    exprList.set(u-1, addition((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }


            exprList.remove(start+1); exprList.remove(start-1); // удаление скобок
        }

        private void calculatePercentPart(int start, int end) throws Exception {
            int u;

            u = start;
            while(u<=end) {

                if(exprList.get(u) instanceof String)

                switch ((String)exprList.get(u)) {
                    case "!":
                        exprList.set(u - 1, factorial(roundUp((double)exprList.get(u - 1), 14)));
                        exprList.remove(u);
                        end -= 1;
                        break;
                    case "!!":
                        exprList.set(u - 1, doubleFactorial(roundUp((double)exprList.get(u - 1), 14)));
                        exprList.remove(u);
                        end -= 1;
                        break;
                    case "°":
                        exprList.set(u - 1, isDegrees((double)exprList.get(u - 1), angleUnit));
                        exprList.remove(u);
                        end -= 1;
                        break;
                    case "%":
                        exprList.set(u - 1, percent((double)exprList.get(u - 1)));
                        exprList.remove(u);
                        end -= 1;
                        break;
                    default: u++;
                        break;
                }

                else u++;

            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("^")){
                    exprList.set(u-1, power((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("/")){
                    exprList.set(u-1, division((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("*")){
                    exprList.set(u-1, multiplication((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }

            u = start;
            while(u<=end){
                if (exprList.get(u).equals("-")){
                    exprList.set(u-1, subtraction((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;

                } else u++;
            }


            u = start;
            while(u<=end){
                if (exprList.get(u).equals("+")){
                    exprList.set(u-1, addition((double)exprList.get(u-1), (double)exprList.get(u+1)));
                    exprList.remove(u+1); exprList.remove(u); end-=2;
                } else u++;
            }
        }




        public static class EmptyExpressionException extends Exception { }
        public static class IncorrectExpressionException extends Exception { }
        public static class UnknownFunctionException extends Exception {
            int position;
            public UnknownFunctionException(){}
            public UnknownFunctionException(int position){this.position=position;}
        }
        public static class WrongBracketsException extends Exception { }
    }



}
