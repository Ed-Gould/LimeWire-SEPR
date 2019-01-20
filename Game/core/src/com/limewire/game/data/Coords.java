package com.limewire.game.data;

/**
 * Class to represent coordinates of entities and squares in the game.
 * int x = x coordinate
 * int y = y coordinate
*/

public class Coords {
    int x, y;

    public Coords(){

    }

    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int[] getCoordsAsList(){ // Returns the Coords as a primitive int list
        return new int[] {x, y};
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCoords(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object other){
        if (other == this){
            return true;
        }
        if (!(other instanceof Coords)){
            return false;
        }
        Coords coords = (Coords) other;
        return (coords.x == this.x && coords.y == this.y);
    }
}
