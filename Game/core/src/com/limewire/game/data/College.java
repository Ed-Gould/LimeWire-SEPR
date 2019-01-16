package com.limewire.game.data;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Random;

public class College {
    private String name;
    private String team;
    private int numOfShips;
    private Boolean conquer;
    private int speed, health;
    public Ship ship;
    private Map map;

    public College(Map map, String name, int numOfShips, boolean status,int speed,int health){
        this.name = name;
        this.map = map;
        this.numOfShips = numOfShips;
        this.health = health;
        this.speed = speed;
        this.conquer = status;
        setTeam();
        setShips();
    }

    public void setShips(){
        ShipList shipList = new ShipList(map,numOfShips,health,team);
        while(numOfShips>0){
            numOfShips -= 1;
            setShips();
        }
    }

    public void setTeam()
    {
        if(conquer == false){
            this.team = name;
        }
        else{
            this.team = "player";
        }
    }

}
