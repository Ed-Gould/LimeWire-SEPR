package com.limewire.game.data;

public class PointSystem {
    private int points,gold;
    private String playerName;

    // Points given based on what is destroyed
    private int pointPerShip = 1;
    private int pointPerCollege = 10;
    private int pointPerDepartment = 5;

    public PointSystem(String playerName){
        this.playerName = playerName;
        this.points = 0;
    }

    public int getPoints(){
        return this.points;
    }

    public void setPoints(int point) {
        this.points = point;
    }

    public void addPoints(String type){ // Give the player points depending on the entity destroyed
        if(type.equals("ship")){this.points += pointPerShip;}
        if(type.equals("college")){this.points += pointPerCollege;}
        if(type.equals("department")){this.points += pointPerDepartment;}
    }
}
