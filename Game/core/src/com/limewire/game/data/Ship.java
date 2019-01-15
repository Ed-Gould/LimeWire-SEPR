package com.limewire.game.data;

public class Ship {
    private int x, y, health, moveSpeed, movesLeft;
    private String team;

    public Ship(int x, int y, int health, String team) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.team = team;
        this.moveSpeed = 3;
        this.movesLeft = moveSpeed;
    }

    public int getX(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public Coords getCoords(){
        return new Coords(x, y);
    }

    public int getMoveSpeed(){
        return this.moveSpeed;
    }

    public int getMovesLeft(){
        return this.movesLeft;
    }

    public void setMovesLeft(int moves){
        this.movesLeft = moves;
    }

    public void decMovesLeft(){ // Decrement moves left by one
        this.movesLeft -= 1;
    }
}
