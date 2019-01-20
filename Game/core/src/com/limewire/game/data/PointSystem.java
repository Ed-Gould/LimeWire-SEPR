package com.limewire.game.data;

public class PointSystem {
    private int points,gold;
    private String playerName;
    //point per ship/college/department destroy, could be change during the develop.
    private int pointPerShip = 1;
    private int pointPerCollege = 10;
    private int pointPerDepartment = 5;

    public PointSystem(String playerName){
        this.playerName = playerName;
        this.gold = 0;
        this.points = 12345;
    }



    public int getPoints(){return this.points;}

    public void setPoints(int point){this.points = point;}

    public void addPoints(String type){
        if(type.equals("ship")){this.points += pointPerShip;}
        if(type.equals("college")){this.points += pointPerCollege;}
        if(type.equals("department")){this.points += pointPerDepartment;}
    }

    public void setGold(int gold){this.gold = gold;}

    public int getGold(){return this.gold;}

    public void earnGold(int number){
        this.gold += number;
    }
    public void lossGold(int number){
        this.gold -= number;
    }



}
