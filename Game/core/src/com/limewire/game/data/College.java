package com.limewire.game.data;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.Random;

public class College {
    private String name;
    private ArrayList<Ship> ships;
    private Coords coords;
    private int health;
    private Boolean conquered;

    public College(String name, Coords coords, int health, ArrayList<Ship> ships){
        this.name = name;
        this.coords = coords;
        this.health = health;
        this.ships = ships;
        conquered = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public Coords getCoords() {
        return coords;
    }

    public Boolean getConquered() {
        return conquered;
    }

    public void setConquered(Boolean conquered) {
        this.conquered = conquered;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
