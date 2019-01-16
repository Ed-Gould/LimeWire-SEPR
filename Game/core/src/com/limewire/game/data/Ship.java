package com.limewire.game.data;

import com.badlogic.gdx.graphics.Texture;

public class Ship {
    private int x, y, health, moveSpeed, movesLeft;
    private String team;
    private Texture texture;

    public Ship(int x, int y, int health, String team) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.moveSpeed = 3;
        this.team = team;
        this.movesLeft = moveSpeed;

        if (team.equals("player")){
            this.texture = new Texture("32ship.png");
        }
        else{
            this.texture = new Texture("32enemyShip.png");
        }
    }

   public boolean nextToShip(Ship otherShip){
        // If the ships are greater than 1 space apart on the x or y axis:
        if (Math.abs(this.x - otherShip.x) > 1 || Math.abs(this.y - otherShip.y) > 1){
            return false;
        }
        return !(Math.abs(this.x - otherShip.x) > 0 && Math.abs(this.y - otherShip.y) > 0);
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

    public String getTeam(){
        return this.team;
    }

    public Texture getTexture(){
        return this.texture;
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
