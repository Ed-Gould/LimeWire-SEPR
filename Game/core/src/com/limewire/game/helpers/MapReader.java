package com.limewire.game.helpers;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class MapReader {

    public int[][] readMap(String file, int map_x, int map_y) {
        int[][] map1 = new int[map_y][map_x];
        int row = 0;
        int col;

        try {
            Scanner in = new Scanner(new FileReader(file));

            while (in.hasNext()) {
                col = 0;
                for (String value: in.next().split(",")) {
                    try {
                        map1[row][col] = Integer.parseInt(value);
                    }
                    catch (NumberFormatException err) {
                        System.out.println("String map value found when int expected");
                    }
                    col++;
                }
                row++;
            }
            in.close();

            ///* Demonstration
            for (int i = 0; i < map_y; i++) {
                for (int j = 0; j < map_x; j++) {
                    System.out.print(map1[i][j]+" ");
                }
                System.out.println();
            }//*/
        } catch (FileNotFoundException err) {
            System.out.println("File specified could not be found");
        }
        return map1;
    }
}

/*public static void main(String[] args) {
        MapReader mr = new MapReader();
        mr.readMap("src/map1.txt", 50, 50);
    }*/
