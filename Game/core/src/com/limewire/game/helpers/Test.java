package com.limewire.game.helpers;

import com.limewire.game.data.Coords;
import jdk.nashorn.internal.ir.Symbol;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args){
        Set<Coords> moveSquares = new HashSet<Coords>();
        moveSquares.add(new Coords(2,3));
        moveSquares.add(new Coords(0,0));
        moveSquares.add(new Coords(1,0));
        moveSquares.add(new Coords(0,1));

        Coords selection = new Coords(2,3);
        Coords move = new Coords(2, 3);

        System.out.println(selection.equals(move));

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
