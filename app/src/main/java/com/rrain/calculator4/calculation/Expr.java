package com.rrain.calculator4.calculation;

import java.util.ArrayList;
import java.util.List;

public class Expr {
    private ElemsContainer elems;
    private StringBuilder expr;

    public Expr() {
        elems = new ElemsContainer();
        expr = new StringBuilder();
    }

    public void insert(int idx, String str){
        if (str==null || str.length()==0) return;
        if (idx > expr.length()) idx = expr.length();
        else if (idx < 0) idx = 0;

        expr.insert(idx, str);
        updateElems(idx, str.length());
    }

    private void updateElems(int s, int len){
        int sElemIdx = 0;
        for (int i = elems.size()-1; i >= 0 ; i--) {
            Elem elem = elems.get(i);
            if (s==elem.e){
                sElemIdx=i;
                break;
            }
            elem.e+=len;
            if (s>elem.s && s<elem.e){
                sElemIdx=i;
                break;
            }
            elem.s+=len;
        }
        scanElems(sElemIdx);
    }

    private void scanElems(int sElemIdx){
        Elem elem;
        loop: for (int i = elems.get(sElemIdx).s; i < expr.length(); ) {

            switch(expr.charAt(i)){//0.,123456789
                case '0':case '.':case ',':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                    elem = findNumber(i);
                    i=elem.e;
                    elems.addElem(sElemIdx, elem);
                    elems.removeOldAfter(sElemIdx);
                    if (elems.nextIsSequential(sElemIdx)) break loop; else sElemIdx++;
                    continue;

                case '+':
                    elem = new Elem(i, i+1, Function.funcs.get("+"));
                    i=elem.e;
                    elems.addElem(sElemIdx, elem);
                    elems.removeOldAfter(sElemIdx);
                    if (elems.nextIsSequential(sElemIdx)) break loop; else sElemIdx++;
                    continue;

                case '-':
                    elem = new Elem(i, i+1, Function.funcs.get("-"));
                    i=elem.e;
                    elems.addElem(sElemIdx, elem);
                    elems.removeOldAfter(sElemIdx);
                    if (elems.nextIsSequential(sElemIdx)) break loop; else sElemIdx++;
                    continue;

                case '*':
                    elem = new Elem(i, i+1, Function.funcs.get("*"));
                    i=elem.e;
                    elems.addElem(sElemIdx, elem);
                    elems.removeOldAfter(sElemIdx);
                    if (elems.nextIsSequential(sElemIdx)) break loop; else sElemIdx++;
                    continue;

                case '/':
                    elem = new Elem(i, i+1, Function.funcs.get("/"));
                    i=elem.e;
                    elems.addElem(sElemIdx, elem);
                    elems.removeOldAfter(sElemIdx);
                    if (elems.nextIsSequential(sElemIdx)) break loop; else sElemIdx++;
                    continue;
            }

        }
    }

    private Elem findNumber(int s){
        int e;
        loop: for (e = s; e < expr.length(); e++) {
            switch (expr.charAt(e)){
                case '0':case '.':case ',':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
                    break;
                default: break loop;
            }
        }
        return new Elem(s, e, new Num(Double.parseDouble(expr.substring(s, e))));
    }





    private static class ElemsContainer{
        private List<Elem> elems;

        public ElemsContainer() {
            elems = new ArrayList<>();
            elems.add(new Elem(0, 0));
        }

        private Elem get(int idx){ return elems.get(idx); }
        private int size(){ return elems.size(); }
        private void replaceElem(int elemIdx, Elem elem){ elems.set(elemIdx, elem); }
        private void addElem(int elemIdx, Elem elem){ elems.add(elemIdx, elem); }
        private void removeOldAfter(int idx){
            Elem elem = elems.get(idx);
            while (true){
                if (idx+1<elems.size() && elems.get(idx+1).s<elem.e) elems.remove(idx+1);
            }
        }
        private boolean nextIsSequential(int currIdx){
            return currIdx+1==elems.size() || elems.get(currIdx).e==elems.get(currIdx+1).s;
        }
    }

}
