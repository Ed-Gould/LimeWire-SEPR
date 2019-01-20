package com.limewire.game.data;

import java.util.ArrayList;

public class Department {
    private String name;
    private ArrayList<Ship> ships;
    private Coords coords;
    private int health;
    private Boolean conquer;

    public Department(String name, Coords coords, int health){
        this.name = name;
        this.coords = coords;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coords getCoords() {
        return coords;
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
