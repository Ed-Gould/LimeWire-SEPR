package com.limewire.game.data;

import jdk.nashorn.internal.ir.Symbol;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Map {
    Square[][] grid;

    public Map(){
        this.grid = new Square[Game.gridWidth][Game.gridHeight];
        for (int i = 0; i < Game.gridWidth; i++){
            for (int j = 0; j < Game.gridHeight; j++){
                grid[i][j] = new Square("water");
            }
        }
    }

    public boolean isValidSquare(Coords coordinates){
        // Check if the coordinates are in the bounds of the map
        if (coordinates.getX() >= Game.gridWidth || coordinates.getX() < 0) {
            return false;
        }
        if (coordinates.getY() >= Game.gridHeight || coordinates.getY() < 0){
            return false;
        }
        // Check if the square is pathable and not occupied
        return grid[coordinates.getX()][coordinates.getY()].isPathable && grid[coordinates.getX()][coordinates.getY()].ship == null;
    }

    public boolean isNewSquare(Set<Coords> squares, Coords square){
        for (Coords s: squares){
            if (s.getX() == square.getX() && s.getY() == square.getY()) {
                return false;
            }
        }
        return true;
    }

    public Set<Coords> getPossibleMoves(Ship ship){
        Set<Coords> possibleSquares = new HashSet<Coords>();
        Set<Coords> visitedSquares = new HashSet<Coords>();
        Set<Coords> newSquares = new HashSet<Coords>();
        newSquares.add(ship.getCoords());

        int movesLeft = ship.getMoveSpeed() + 1;

        while (newSquares.size() > 0 && movesLeft != 0){
            Set<Coords> currentSquares = new HashSet<Coords>(newSquares);
            for (Coords square: currentSquares){
                possibleSquares.add(square);
                visitedSquares.add(square);
                newSquares.remove(square);

                Set<Coords> moveSquares = new HashSet<Coords>();
                moveSquares.add(new Coords(square.getX()-1, square.getY()));
                moveSquares.add(new Coords(square.getX()+1, square.getY()));
                moveSquares.add(new Coords(square.getX(), square.getY()+1));
                moveSquares.add(new Coords(square.getX(), square.getY()-1));

                for (Coords moveSquare: moveSquares){
                    if (isNewSquare(visitedSquares, moveSquare) && isValidSquare(moveSquare)){
                        newSquares.add(moveSquare);
                    }
                    visitedSquares.add(moveSquare);
                }
            }
            movesLeft -= 1;
        }
        return possibleSquares;
    }

    public Square[][] getGrid(){
        return this.grid;
    }

    public Ship getShip(Coords coordinates){
        return grid[coordinates.getX()][coordinates.getY()].ship;
    }

    /*public Set<Coords> getPossibleMoves(Ship ship){
        Set<Coords> visitedSquares = new HashSet<Coords>();
        Set<Coords> newSquares = new HashSet<Coords>();
        newSquares.add(ship.getCoords());

        int movesLeft = ship.getMoveSpeed();
        while (newSquares.size() > 0 && movesLeft > 0){
            Set<Coords> currentSquares = new HashSet<Coords>(newSquares);
            for (Coords square: currentSquares){
                Coords leftMove = new Coords(square.getX()-1, square.getY());
                Coords rightMove = new Coords(square.getX()+1, square.getY());
                Coords upMove = new Coords(square.getX(), square.getY()+1);
                Coords downMove = new Coords(square.getX(), square.getY()-1);

                visitedSquares.addAll(newSquares);
                if (isValidSquare(leftMove) && isNewSquare(visitedSquares, leftMove)){
                    newSquares.add(leftMove);
                }

                if (isValidSquare(rightMove) && isNewSquare(visitedSquares, rightMove)){
                    newSquares.add(rightMove);
                }

                if (isValidSquare(upMove) && isNewSquare(visitedSquares, upMove)){
                    newSquares.add(upMove);
                }

                if (isValidSquare(downMove) && isNewSquare(visitedSquares, downMove)){
                    newSquares.add(downMove);
                }

                newSquares.remove(square);
                visitedSquares.add(square);
            }
            movesLeft -= 1;
        }
        return visitedSquares;
    }*/
}