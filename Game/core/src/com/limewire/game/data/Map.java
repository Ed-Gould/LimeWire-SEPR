package com.limewire.game.data;

import com.limewire.game.helpers.MapReader;
import jdk.nashorn.internal.ir.Symbol;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Map {
    private Square[][] grid;
    private Coords derwentCoords, jamesCoords, vanbrughCoords;

    public Map(String fileName) {
        grid = MapReader.getMap(fileName);

        for (int i = 0; i < Game.gridWidth; i++){
            for (int j = 0; j < Game.gridHeight; j++){
                if (grid[i][j].getType().equals("d")){
                    derwentCoords = new Coords(i, j);
                }

                else if (grid[i][j].getType().equals("j")){
                    jamesCoords = new Coords(i,j);
                }

                else if (grid[i][j].getType().equals("v")){
                    vanbrughCoords = new Coords(i,j);
                }
            }
        }
    }


    public boolean isValidSquare(Coords coordinates) {
        // Check if the coordinates are in the bounds of the map
        if (coordinates.getX() >= Game.gridWidth || coordinates.getX() < 0) {
            return false;
        }
        if (coordinates.getY() >= Game.gridHeight || coordinates.getY() < 0) {
            return false;
        }
        // Check if the square is pathable and not occupied
        return grid[coordinates.getX()][coordinates.getY()].isPathable && grid[coordinates.getX()][coordinates.getY()].ship == null;
    }

    public boolean isNewSquare(Set<Coords> squares, Coords square) {
        for (Coords s : squares) {
            if (s.getX() == square.getX() && s.getY() == square.getY()) {
                return false;
            }
        }
        return true;
    }

    public Set<Coords> getPossibleMoves(Ship ship) {
        Set<Coords> possibleSquares = new HashSet<Coords>();
        Set<Coords> visitedSquares = new HashSet<Coords>();
        Set<Coords> newSquares = new HashSet<Coords>();
        newSquares.add(ship.getCoords());

        int movesLeft = ship.getMovesLeft() + 1;

        while (newSquares.size() > 0 && movesLeft != 0) {
            Set<Coords> currentSquares = new HashSet<Coords>(newSquares);
            for (Coords square : currentSquares) {
                possibleSquares.add(square);
                visitedSquares.add(square);
                newSquares.remove(square);

                Set<Coords> moveSquares = new HashSet<Coords>();
                moveSquares.add(new Coords(square.getX() - 1, square.getY()));
                moveSquares.add(new Coords(square.getX() + 1, square.getY()));
                moveSquares.add(new Coords(square.getX(), square.getY() + 1));
                moveSquares.add(new Coords(square.getX(), square.getY() - 1));

                for (Coords moveSquare : moveSquares) {
                    if (isNewSquare(visitedSquares, moveSquare) && isValidSquare(moveSquare)) {
                        newSquares.add(moveSquare);
                    }
                    visitedSquares.add(moveSquare);
                }
            }
            movesLeft -= 1;
        }
        return possibleSquares;
    }

    public Square[][] getGrid() {
        return this.grid;
    }

    public Ship getShip(Coords coordinates) {
        return grid[coordinates.getX()][coordinates.getY()].ship;
    }

    public void deleteShip(Coords coords) {
        grid[coords.getX()][coords.getY()].ship = null;
    }

    public void setShip(Ship ship) {
        grid[ship.getX()][ship.getY()].ship = ship;
    }

    public Square getSquare(Coords coords){
        return this.grid[coords.getX()][coords.getY()];
    }

    public Coords getDerwentCoords() {
        return this.derwentCoords;
    }

    public Coords getJamesCoords() {
        return this.jamesCoords;
    }

    public Coords getVanbrughCoords() {
        return this.vanbrughCoords;
    }
}
