package com.limewire.game.helpers;

import com.limewire.game.data.Game;
import com.limewire.game.data.Map;
import com.limewire.game.data.Square;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MapReader {


    public static String[][] getRawMapData(String fileName) {
        String[][] map = new String[Game.gridHeight][Game.gridWidth];
        int row = 0;
        int col;

        try {
            Scanner in = new Scanner(new FileReader(fileName));

            while (in.hasNext()) {
                col = 0;
                for (String value: in.next().split(",")) {
                    map[row][col] = value;
                    col++;
                }
                row++;
            }
            in.close();

            ///* Demonstration
            for (int i = 0; i < Game.gridHeight; i++) {
                for (int j = 0; j < Game.gridWidth; j++) {
                    System.out.print(map[i][j]+" ");
                }
                System.out.println();
            }//*/
        } catch (FileNotFoundException err) {
            System.out.println("File specified could not be found");
        }
        return map;
    }

    public static Square[][] convertMap(String[][] stringMap){
        Square[][] mapGrid;
        mapGrid = new Square[Game.gridWidth][Game.gridHeight];

        for (int i = 0; i < Game.gridWidth; i++){
            for (int j = 0; j < Game.gridHeight; j++){
                mapGrid[j][i] = new Square(stringMap[Game.gridWidth - i - 1][j]);
            }
        }
        return mapGrid;
    }

    public static Square[][] getMap(String fileName){
        return convertMap(getRawMapData(fileName));
    }


/*    public static void main(String[] args) {
        MapReader mr = new MapReader();
        mr.getRawMapData("./Maps/8x8testMap.txt");
    }*/
}

