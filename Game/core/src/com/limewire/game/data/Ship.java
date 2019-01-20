package com.limewire.game.data;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Ship {
    private int x, y, health, moveSpeed, movesLeft, attacksPerTurn, attacksLeft;
    private String team;
    private Texture texture;

    public Ship(int x, int y, int health, String team) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.moveSpeed = 3;
        this.movesLeft = moveSpeed;
        this.attacksPerTurn = 1;
        this.attacksLeft = attacksPerTurn;
        this.team = team;

        // Set the texture depending on the team
        if (team.equals("derwent")){
            this.texture = new Texture("derwentShip.png");
        }

        else if (team.equals("james")){
            this.texture = new Texture("jamesShip.png");
        }

        else if (team.equals("vanbrugh")){
            this.texture = new Texture("vanbrughShip.png");
        }
    }

    public boolean nextToBuilding(ArrayList<Coords> coordsList){ // Checks if the ship is next to a building
        for (Coords coords: coordsList){
            // If the squares are greater than 1 space apart on the x or y axis:
            if (nextToCoords(coords)){
                return true;
            }
        }
        return false;
    }

    public boolean nextToCoords(Coords coords){
        if (Math.abs(this.x - coords.getX()) > 1 || Math.abs(this.y - coords.getY()) > 1){
            return false;
        }
        return !(Math.abs(this.x - coords.getX()) > 0 && Math.abs(this.y - coords.getY()) > 0);
    }

    public boolean nextToShip(Ship otherShip){ // Checks if the ship is next to another ship
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

    public int getMovesLeft(){
        return this.movesLeft;
    }

    public void setMovesLeft(int moves){
        this.movesLeft = moves;
    }

    public void decMovesLeft(int n){ // Decrement moves left by n
        this.movesLeft -= n;
    }

    public int getAttacksPerTurn(){
        return this.attacksPerTurn;
    }

    public int getAttacksLeft(){
        return this.attacksLeft;
    }

    public void setAttacksLeft(int attacksLeft){
        this.attacksLeft = attacksLeft;
    }

    public void decAttacksLeft(){ // Decrement number of attacks left by 1.
        this.attacksLeft -= 1;
    }

    public boolean canAttack(){ // If the ship has attacks remaining, return true
        if (this.attacksLeft > 0){
            return true;
        }
        return false;
    }

    public String getTeam(){
        return this.team;
    }

    public Texture getTexture(){
        return this.texture;
    }



}
