package com.limewire.game.helpers;

import com.limewire.game.data.Coords;
import jdk.nashorn.internal.ir.Symbol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args){
        new Test();
    }

    public Test(){
        int x = 0;
        int a = 1;
        int b = 12;
        int c = 123;
        int d = 1234;

        printDigits(d);
    }

    public void printDigits(int n) {
        for (char c : String.valueOf(n).toCharArray()) {
            if (c == "3".charAt(0)){
                System.out.println(c);
            }
        }
    }
}

    /*public static void main(String[] args){
        Set<Coords> moveSquares = new HashSet<Coords>();
        moveSquares.add(new Coords(2,3));
        moveSquares.add(new Coords(0,0));
        moveSquares.add(new Coords(1,0));
        moveSquares.add(new Coords(0,1));

        Coords selection = new Coords(2,3);
        Coords move = new Coords(2, 3);

        System.out.println(selection.equals(move));
    }*/