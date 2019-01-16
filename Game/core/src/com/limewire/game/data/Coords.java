package com.limewire.game.data;

/**so your player, item, asset, etc. all have a coords field
you could make them extend an abstract class,
Entity or something, then have all the coords methods on there to avoid duplication


I mean, you would have a Coords class which has moveLeft or whatever, which updates Coord's internal state of x and y
then an abstract class Entity which has a Coords field
then inside Entity, moveLeft or whatever, which calls coords.MoveLeft
then you don't need to declare moveLeft for player, item, asset
because they inherit it from Entity
you could not have the methods on Entity, and instead do entity.getCoords.moveLeft,
but it would be cleaner to do entity.moveLeft, and let the entity class deal with calling Coords
*/

public class Coords {
    int x, y;

    public Coords(){

    }

    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int[] getCoordsAsList(){
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
