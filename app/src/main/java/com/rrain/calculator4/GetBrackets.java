package com.rrain.calculator4;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GetBrackets {

    private int radix;
    private String expression;


    public GetBrackets(String expression, int radix) {
        this.radix = radix;
        this.expression = expression;
    }





    /*private ArrayList<Object> exprList = new ArrayList<>();*/


    public List<Bracket> getBrackets() throws Exception{
        /*String expression = this.expression;*/

        if(expression.isEmpty()) {throw new EmptyExpressionException();}

        bracketsStack = new ArrayList<>(2);
        brackets = new ArrayList<>(2);

        /*brackets.add(new Bracket(1, true, false));*/
        convert("(" + expression + ")");

        /*loop: while(true){
            switch (bracketsStack.get(bracketsStack.size()-1)){
                case 0: *//*exprList.add(")");*//* bracketsStack.remove(bracketsStack.size()-1); brackets.add(new Bracket(i, false, true));
                    if (bracketsStack.size()>0 && bracketsStack.get(bracketsStack.size() - 1) == 2) continue loop;

                    prevObjectType = 2;
                    break loop;

                case 1: *//*exprList.add(")");*//* bracketsStack.remove(bracketsStack.size()-1); brackets.add(new Bracket(i, false, false)); break;
                case 2: *//*exprList.add(")");*//* bracketsStack.remove(bracketsStack.size()-1); prevObjectType = 4; brackets.add(new Bracket(i, false, false)); break loop;
                case 3: *//*exprList.add(")");*//* bracketsStack.remove(bracketsStack.size()-1); prevObjectType = 4; brackets.add(new Bracket(i, false, true)); break loop;
            }
        }*/

        /*brackets.add(new Bracket(expression.length()-1, false, false));*/


        return brackets;
    }









    private static Object[] toDec(String expr, int start, int radix) throws Exception{
        Object[] numberArray = new Object[2];

        if (expr.length()-start>0) if (expr.substring(start, start+1).equals("∞")) {numberArray[0]="Infinity"; numberArray[1]=1; return numberArray;}
        if (expr.length()-start>1) if (expr.substring(start, start+2).equals("-∞") || expr.substring(start, start+2).equals("−∞")) {numberArray[0]="-Infinity"; numberArray[1]=2; return numberArray;}
        if (expr.length()-start>7) if (expr.substring(start, start+8).equals("Infinity")) {numberArray[0]="Infinity"; numberArray[1]=8; return numberArray;}
        if (expr.length()-start>8) if (expr.substring(start, start+9).equals("-Infinity") || expr.substring(start, start+9).equals("−Infinity")) {numberArray[0]="-Infinity"; numberArray[1]=9; return numberArray;}
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
        public static final int BRACKET_NOT_AUTOCLOSE_PUT_MULT = 0;
        public static final int BRACKET_DO_AUTOCLOSE_PUT_MULT = 1;
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

    private List<Bracket> brackets;

    /*int code = 0;*/
    private int currentDepth = 0;
    public class Bracket {
        public int depth;
        public int pos;
        public boolean isOpen;
        public boolean isReal;
        public Bracket pair = null;


        private Bracket(int pos, boolean isOpen, boolean isReal) {
            this.pos = pos==0 ? pos : pos-1;
            this.isOpen = isOpen;
            /*Log.w("coverWithBrackets", pos+"***"+isReal+"***"+expression.length());*/
            if( (pos==0 || pos == expression.length()+1) && isReal ){
                this.isReal = false;
                depth = Integer.MIN_VALUE+1;
            } else {
                this.isReal = isReal;
                depth = isOpen ? currentDepth++ : --currentDepth;
            }

            if (!isOpen){
                for (int i = brackets.size()-1; i >= 0; i--) {
                    if (brackets.get(i).isOpen && brackets.get(i).pair == null
                            && brackets.get(i).isReal == this.isReal
                            && brackets.get(i).depth == this.depth){

                        brackets.get(i).pair = this;
                        this.pair = brackets.get(i);
                        break;
                    }
                }
            }


            /*code = GetBrackets.this.code++;*/
        }

        /*public Bracket(int pos, boolean isOpen, boolean isReal, int depth) {
            this.depth = depth;
            this.pos = pos;
            this.isOpen = isOpen;
            this.isReal = isReal;
        }

        public Bracket(int pos, boolean isOpen, boolean isReal, Bracket pair, int depth) {
            this.depth = depth;
            this.pos = pos;
            this.isOpen = isOpen;
            this.isReal = isReal;
            this.pair = pair;
        }

        int code;
        @Override
        public String toString() {
            return "Bracket{" +
                    "depth=" + depth +
                    ", pos=" + pos +
                    ", isOpen=" + isOpen +
                    ", isReal=" + isReal +
                    ", code=" + code +
                    ", pairCode=" + (pair!=null ? pair.code : "null") +
                    '}';
        }*/
    }

    private ArrayList<Integer> bracketsStack;
    //0_All_is_ok---Not_need_to_close_and_with_after_sign_*
    //1_Need_to_close_and_with_after_sign_*
    //2_Need_to_close_and_without_after_sign
    //3_Not_need_to_close_and_without_after_sign
    private void autocloseBracketsType1(int i){
        while (bracketsStack.size()>0 && bracketsStack.get(bracketsStack.size()-1)==1) {
            /*exprList.add(")");*/
            bracketsStack.remove(bracketsStack.size()-1);

            brackets.add(new Bracket(i, false, false));
        }
    }





    private Object[] numberArray;


    private void switchForNumbers(int i){
        switch (prevObjectType){
            case 0: case 2: /*exprList.add("*");*/ break;
            case 6: case 7: case 8:
                autocloseBracketsType1(i);
                /*exprList.add("*");*/
                bracketsStack.add(1); brackets.add(new Bracket(i, true, false));
                break;
            case 1: case 3: case 4: /*exprList.add("(");*/ bracketsStack.add(1); brackets.add(new Bracket(i, true, false)); break;
            case 10: /*exprList.add("(");*/ bracketsStack.add(2); brackets.add(new Bracket(i, true, false)); break;
        }
        prevObjectType = 0;
    }

    private void switchForFunctions(int i){
        switch (prevObjectType){
            case 2: /*exprList.add("*");*/ break;
            case 0: case 6: case 7: case 8:
                autocloseBracketsType1(i);
                /*exprList.add("*");*/
                bracketsStack.add(1); brackets.add(new Bracket(i, true, false));
                break;
            case 1: case 3: case 4:/* exprList.add("(");*/ bracketsStack.add(1); brackets.add(new Bracket(i, true, false)); break;
            case 10: /*exprList.add("(");*/ bracketsStack.add(2); brackets.add(new Bracket(i, true, false)); break;
        }
        prevObjectType = 4;
    }

    private void switchForDoubleFunctions(int i){
        switch (prevObjectType){
            case 0: case 6: case 7: case 8:
                autocloseBracketsType1(i);
                /*exprList.add("*");*/
                bracketsStack.add(1); brackets.add(new Bracket(i, true, false));
                break;
            case 2: /*exprList.add("*");*/ break;
            case 1: case 3: case 4: /*exprList.add("(");*/ bracketsStack.add(1); brackets.add(new Bracket(i, true, false)); break;
            case 10: /*exprList.add("(");*/ bracketsStack.add(2); brackets.add(new Bracket(i, true, false)); break;
        }
        prevObjectType = 10;
    }

    private void switchForPostFunctions(int i){
        if (bracketsStack.size()>0 && bracketsStack.get(bracketsStack.size()-1)==1) {
            /*exprList.add(")");*/ bracketsStack.remove(bracketsStack.size()-1);
            brackets.add(new Bracket(i, false, false));
        }

        if(!brackets.get(brackets.size()-1).isOpen && brackets.get(brackets.size()-1).pair!=null){

            for (int j = brackets.indexOf(brackets.get(brackets.size()-1).pair); j < brackets.size(); j++) {
                brackets.get(j).depth++;
            }

            brackets.add(brackets.indexOf(brackets.get(brackets.size()-1).pair),
                    new Bracket(brackets.get(brackets.size()-1).pair.pos+1, true, false));
            bracketsStack.add(1);
        }
    }

    private void switchForQuickPowers(int i){

        autocloseBracketsType1(i);

        if(!brackets.get(brackets.size()-1).isOpen && brackets.get(brackets.size()-1).pair!=null){

            for (int j = brackets.indexOf(brackets.get(brackets.size()-1).pair); j < brackets.size(); j++) {
                brackets.get(j).depth++;
            }

            brackets.add(brackets.indexOf(brackets.get(brackets.size()-1).pair),
                    new Bracket(brackets.get(brackets.size()-1).pair.pos+1, true, false));
            bracketsStack.add(1);
        }
    }




    private void convert(String expression) throws Exception{

        prevObjectType = -1;
        backupPrevObjectType = -1;
        n = "";

        for (int i = 0; i < expression.length(); i++) {

            switch (expression.charAt(i)){case ' ': case '\n': case '_': continue;}//space&enter&_




            switch(expression.charAt(i)){//0.,123456789ABCDEF
                case '0':case '.':case ',':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':
                    switchForNumbers(i);
                    numberArray = toDec(expression, i, radix);
                    /*exprList.add(Double.parseDouble((String)numberArray[0]));*/
                    i+=((int)numberArray[1]-1);
                    continue;
            }




            try {
                switch (expression.substring(i, i+1)){//√³_√⁴
                    case "√³":
                        switchForFunctions(i);
                        /*exprList.add("∛");*/
                        i++;
                        continue;

                    case "√⁴":
                        switchForFunctions(i);
                        /*exprList.add("∜");*/
                        i++;
                        continue;
                }
            } catch (Exception e) {/*e.printStackTrace();*/}

            switch (expression.charAt(i)){//π_e_∞_!&!!_°_%_√_∛_∜_√³_√⁴
                case 'π':
                    switchForNumbers(i);
                    /*exprList.add(Math.PI);*/
                    continue;

                case 'e':
                    switchForNumbers(i);
                    /*exprList.add(Math.E);*/
                    continue;

                case '∞':
                    switchForNumbers(i);
                    /*exprList.add(Double.POSITIVE_INFINITY);*/
                    continue;

                case '!':
                    switch (prevObjectType){
                        case 6: /*exprList.set(exprList.size()-1, "!!");*/ prevObjectType = 8; break;
                        case 8: throw new Exception();
                        default:
                            /*exprList.add("!");*/

                            switchForPostFunctions(i);

                            /*autocloseBracketsType1(i); */

                            prevObjectType = 6;
                            break;
                    }
                    continue;

                case '°':
                    /*exprList.add("°");*/  switchForPostFunctions(i); prevObjectType = 7; continue;

                case '%':
                    /*exprList.add("%");*/  switchForPostFunctions(i); prevObjectType = 7; continue;

                case '√':
                    switch (expression.charAt(i+1)) {
                        case '³':
                            switchForFunctions(i);
                            /*exprList.add("∛");*/
                            i++;
                            continue;

                        case '⁴':
                            switchForFunctions(i);
                            /*exprList.add("∜");*/
                            i++;
                            continue;
                    }
                    switchForFunctions(i);
                    /*exprList.add("√");*/ continue;

                case '∛':
                    switchForFunctions(i);
                    /*exprList.add("∛");*/ continue;

                case '∜':
                    switchForFunctions(i);
                    /*exprList.add("∜");*/ continue;

                case '²':
                    /*exprList.add("^^"); exprList.add(2d);*/
                    switchForQuickPowers(i);
                    prevObjectType=0;
                    continue;

                case '³':
                    /* exprList.add("^^"); exprList.add(3d);*/
                    switchForQuickPowers(i);
                    prevObjectType=0;
                    continue;

                case '⁴':
                    /*exprList.add("^^"); exprList.add(4d);*/
                    switchForQuickPowers(i);
                    prevObjectType=0;
                    continue;
            }



            switch (expression.charAt(i)){//()[]
                case '[': case '(':
                    switch (prevObjectType){
                        case 0: case 2: case 6: case 7: case 8:
                            if (bracketsStack.size()>0 && bracketsStack.get(bracketsStack.size()-1)==2) {
                                /*exprList.add(")");*/ bracketsStack.remove(bracketsStack.size()-1);
                                brackets.add(new Bracket(i, false, false));
                            }
                            else {
                                autocloseBracketsType1(i);
                                /*exprList.add("*");*/
                            }
                            bracketsStack.add(0); brackets.add(new Bracket(i, true, true)); break;
                        case 10: bracketsStack.add(3); brackets.add(new Bracket(i, true, true)); break;
                        default: bracketsStack.add(0); brackets.add(new Bracket(i, true, true));
                    }
                    /*exprList.add("(");*/
                    prevObjectType = 1;
                    continue;


                case ']': case ')':
                    loop: while(true){
                        if (bracketsStack.size()>0)
                        switch (bracketsStack.get(bracketsStack.size()-1)){
                            case 0: /*exprList.add(")");*/ bracketsStack.remove(bracketsStack.size()-1); brackets.add(new Bracket(i, false, true));
                                if (bracketsStack.size()>0 && bracketsStack.get(bracketsStack.size() - 1) == 2) continue loop;

                                prevObjectType = 2;
                                break loop;

                            case 1: /*exprList.add(")");*/ bracketsStack.remove(bracketsStack.size()-1); brackets.add(new Bracket(i, false, false)); break;
                            case 2: /*exprList.add(")");*/ bracketsStack.remove(bracketsStack.size()-1); prevObjectType = 4; brackets.add(new Bracket(i, false, false)); break loop;
                            case 3: /*exprList.add(")");*/ bracketsStack.remove(bracketsStack.size()-1); prevObjectType = 4; brackets.add(new Bracket(i, false, true)); break loop;
                        }
                        else {
                            brackets.add(new Bracket(i, false, true));
                            prevObjectType = 2;
                            break loop;
                        }
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
                        case 4: /*exprList.add("(");*/ bracketsStack.add(1); brackets.add(new Bracket(i, true, false)); prevObjectType = 3; break;
                        case 10: /*exprList.add("(");*/ bracketsStack.add(2); brackets.add(new Bracket(i, true, false)); prevObjectType = 3; break;
                        default:
                            autocloseBracketsType1(i);
                            /*exprList.add("+");*/ prevObjectType = 3; break;
                    }
                    continue;

                case '-': case '−':
                    switch (prevObjectType){
                        /*case -1: case 1:
                            while (bracketsStack.get(bracketsStack.size()-1)==1) {bracketsStack.remove(bracketsStack.size()-1); exprList.add(")");}
                            *//*exprList.add("0");*//* exprList.add("-"); prevObjectType = 3; break;*/
                        /*case 5: n+="-"; prevObjectType=backupPrevObjectType; break;*/
                        case 4: /*exprList.add("(");*/ bracketsStack.add(1); brackets.add(new Bracket(i, true, false)); /*exprList.add("-");*/ prevObjectType = 3; break;
                        case 10: /*exprList.add("(");*/ bracketsStack.add(2); brackets.add(new Bracket(i, true, false)); /*exprList.add("-");*/ prevObjectType = 3; break;
                        default:
                            autocloseBracketsType1(i);
                            /*exprList.add("-");*/ prevObjectType = 3; break;
                    }
                    continue;

                case '×': case '*':
                    autocloseBracketsType1(i);
                    /*exprList.add("*");*/ prevObjectType = 3; continue;

                case '/': case '÷':
                    autocloseBracketsType1(i);
                    /*exprList.add("/");*/ prevObjectType = 3; continue;

                case '^':
                    autocloseBracketsType1(i);
                    /*exprList.add("^");*/ prevObjectType = 4; continue;
            }






            switch (expression.substring(i, i+2)) {//tg_ln_lg_sh_ch_th_pi
                case "tg":
                    switchForFunctions(i);
                    /*exprList.add("tg");*/
                    i++;
                    continue;

                case "ln":
                    switchForFunctions(i);
                    /*exprList.add("ln");*/
                    i++;
                    continue;

                case "lg":
                    switchForFunctions(i);
                    /*exprList.add("lg");*/
                    i++;
                    continue;

                case "lb":
                    switchForFunctions(i);
                    /*exprList.add("lb");*/
                    i++;
                    continue;

                case "sh":
                    switchForFunctions(i);
                    /*exprList.add("sh");*/
                    i++;
                    continue;

                case "ch":
                    switchForFunctions(i);
                    /*exprList.add("ch");*/
                    i++;
                    continue;

                case "th":
                    switchForFunctions(i);
                    /*exprList.add("th");*/
                    i++;
                    continue;

                case "pi":
                    switchForNumbers(i);
                    /*exprList.add(Math.PI);*/
                    i++;
                    continue;
            }


            try {
                switch (expression.substring(i, i+4)){//cosh_sinh_tanh_coth
                    case "sinh":
                        switchForFunctions(i);
                        /*exprList.add("sh");*/
                        i+=3;
                        continue;

                    case "cosh":
                        switchForFunctions(i);
                        /*exprList.add("ch");*/
                        i+=3;
                        continue;

                    case "tanh":
                        switchForFunctions(i);
                        /*exprList.add("th");*/
                        i+=3;
                        continue;

                    case "coth":
                        switchForFunctions(i);
                        /*exprList.add("cth");*/
                        i+=3;
                        continue;
                }
            } catch (Exception e) {/*e.printStackTrace();*/}


            switch (expression.substring(i, i+3)){//abs_sin_cos_cot&ctg_tan_log_cth_bin_oct_dec_hex_inv
                case "abs":
                    switchForFunctions(i);
                    /*exprList.add("abs");*/
                    i+=2;
                    continue;

                case "sin":
                    switchForFunctions(i);
                    /*exprList.add("sin");*/
                    i+=2;
                    continue;

                case "cos":
                    switchForFunctions(i);
                    /*exprList.add("cos");*/
                    i+=2;
                    continue;

                case "cot": case "ctg":
                    switchForFunctions(i);
                    /*exprList.add("ctg");*/
                    i+=2;
                    continue;

                case "tan":
                    switchForFunctions(i);
                    /*exprList.add("tg");*/
                    i+=2;
                    continue;

                case "log":
                    switchForDoubleFunctions(i);
                    /*exprList.add("log");*/
                    i+=2;
                    continue;

                case "cth":
                    switchForFunctions(i);
                    /*exprList.add("cth");*/
                    i+=2;
                    continue;

                case "inv":
                    switchForFunctions(i);
                    /*exprList.add("inv");*/
                    i+=2;
                    continue;

                case "sgn":
                    switchForFunctions(i);
                    /*exprList.add("sgn");*/
                    i+=2;
                    continue;


                case "bin":
                    switchForNumbers(i);
                    numberArray = toDec(expression, i+3, 2);
                    /*exprList.add(Double.parseDouble((String)numberArray[0]));*/
                    i+=((int)numberArray[1]-1+3);
                    continue;
                case "oct":
                    switchForNumbers(i);
                    numberArray = toDec(expression, i+3, 8);
                    /*exprList.add(Double.parseDouble((String)numberArray[0]));*/
                    i+=((int)numberArray[1]-1+3);
                    continue;
                case "dec":
                    switchForNumbers(i);
                    numberArray = toDec(expression, i+3, 10);
                    /*exprList.add(Double.parseDouble((String)numberArray[0]));*/
                    i+=((int)numberArray[1]-1+3);
                    continue;
                case "hex":
                    switchForNumbers(i);
                    numberArray = toDec(expression, i+3, 16);
                    /*exprList.add(Double.parseDouble((String)numberArray[0]));*/
                    i+=((int)numberArray[1]-1+3);
                    continue;

            }



            switch (expression.substring(i, i+4)){//sqrt_cbrt_asin_acos_atan_acot_arsh_arch_arth
                case "sqrt":
                    switchForFunctions(i);
                    /*exprList.add("√");*/
                    i+=3;
                    continue;

                case "cbrt":
                    switchForFunctions(i);
                    /*exprList.add("∛");*/
                    i+=3;
                    continue;

                case "asin":
                    switchForFunctions(i);
                    /*exprList.add("arcsin");*/
                    i+=3;
                    continue;

                case "acos":
                    switchForFunctions(i);
                    /*exprList.add("arccos");*/
                    i+=3;
                    continue;

                case "atan":
                    switchForFunctions(i);
                    /*exprList.add("arctg");*/
                    i+=3;
                    continue;

                case "acot":
                    switchForFunctions(i);
                    /*exprList.add("arcctg");*/
                    i+=3;
                    continue;

                case "arsh":
                    switchForFunctions(i);
                    /*exprList.add("arsh");*/
                    i+=3;
                    continue;

                case "arch":
                    switchForFunctions(i);
                    /*exprList.add("arch");*/
                    i+=3;
                    continue;

                case "arth":
                    switchForFunctions(i);
                    /*exprList.add("arth");*/
                    i+=3;
                    continue;
            }




            switch (expression.substring(i, i+5)) {//arctg_arcth
                case "arctg":
                    switchForFunctions(i);
                    /*exprList.add("arctg");*/
                    i+=4;
                    continue;

                case "arcth":
                    switchForFunctions(i);
                    /*exprList.add("arcth");*/
                    i+=4;
                    continue;
            }



            switch (expression.substring(i, i+6)){//arcsin_arccos_arcctg
                case "arcsin":
                    switchForFunctions(i);
                    /*exprList.add("arcsin");*/
                    i+=5;
                    continue;

                case "arccos":
                    switchForFunctions(i);
                    /*exprList.add("arccos");*/
                    i+=5;
                    continue;

                case "arcctg":
                    switchForFunctions(i);
                    /*exprList.add("arcctg");*/
                    i+=5;
                    continue;
            }



            /*switch (expression.substring(i, i+8)) {//quadroot
                case "quadroot":
                    switchForFunctions(i);
                    exprList.add("quadroot");
                    i+=7;
                    continue;
            }
            */

            throw new Exception();
        }

    }









    public static class EmptyExpressionException extends Exception { }
    public static class IncorrectExpressionException extends Exception { }
    public static class UnknownFunctionException extends Exception {
        int position;
        public UnknownFunctionException(){}
        public UnknownFunctionException(int position){this.position=position;}
    }
    public static class WrongParenthesesException extends Exception { }
}
