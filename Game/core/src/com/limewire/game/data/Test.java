package com.limewire.game.data;

import jdk.nashorn.internal.ir.Symbol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args){
        Set<Integer> e = new HashSet<Integer>();
        Set<Integer> e2 = new HashSet<Integer>();

        System.out.println(e);
        System.out.println(e2);
        e2.add(1);
        System.out.println(e);
        System.out.println(e2);
    }
}
/*
public class Test {
    Set<int[]> squares = new HashSet<int[]>();


    public Test(){
        squares.add(new int[] {0,0});
        squares.add(new int[] {1,0});
        squares.add(new int[] {2,0});
        squares.add(new int[] {3,0});

        System.out.println(squares.contains(new int[] {0, 0}));
    }

    public static void main(String[] args){
        new Test();
    }

    public boolean isNewSquare(Set<int[]> squares, int[] square){
        for (int[] s: squares){
            if (s[0] == square[0] && s[1] == square[1]) {
                return false;
            }
        }
        return true;
    }
}*/
