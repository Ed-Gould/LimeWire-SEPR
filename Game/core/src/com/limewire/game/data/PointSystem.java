package com.limewire.game.data;

public class PointSystem {
    private int point,gold;
    private String playerName;
    //point per ship/college/department destroy, could be change during the develop.
    private int pointPerShip = 1;
    private int pointPerCollege = 2;
    private int pointPerDepartment = 2;

    public PointSystem(String playerName){
        this.playerName = playerName;
        this.gold = 0;
        this.point = 0;
    }

    public void earnPoint(String type){
        if(type.equals("ship")){this.point += pointPerShip;}
        if(type.equals("college")){this.point += pointPerCollege;}
        if(type.equals("department")){this.point += pointPerDepartment;}
    }

    public void earnGold(int number){
        this.gold += number;
    }

    public void lossGold(int number){
        this.gold -= number;
    }

    public void setPoint(int point){this.point = point;}
    public void setGold(int gold){this.gold = gold;}
    public int getPoint(){return this.point;}
    public int getGold(){return this.gold;}
}
