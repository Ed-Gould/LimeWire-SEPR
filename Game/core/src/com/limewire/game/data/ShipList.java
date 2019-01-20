package com.limewire.game.data;

import java.util.ArrayList;
import java.util.Random;

public class ShipList {
    private ArrayList ships = new ArrayList();
    private int numOfShip,health;
    private String team;
    private Map map;

    public ShipList(Map map,Ship ship){
        this.map = map;
        addShip(ship);
    }

    public ShipList(Map map,int numOfShip,int health, String team){
        this.numOfShip = numOfShip;
        this.health = health;
        this.team = team;
        this.map = map;
        generateShipList();
    }

    private void addShip(Ship ship)
    {
        ships.add(ship);
        map.setShip(ship);
    }

    private void generateShipList(){
        for(int i=0; i<numOfShip;i++){
            Random XCoordinate = new Random(Game.gridWidth);
            int x = XCoordinate.nextInt();
            Random YCoordinate = new Random(Game.gridHeight);
            int y = YCoordinate.nextInt();
            Ship ship = new Ship(x,y,health,team, 3);
            addShip(ship);
        }
    }

}
