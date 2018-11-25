package com.limewire.game;

import com.badlogic.gdx.graphics.Texture;



public class Ship {
    int id, x, y;
    String selected;

    public Ship(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public String isSelected() {
        return selected;
    }

    public void setLocation(int[] newLocation) {
        x = newLocation[0];
        y = newLocation[1];
    }

    public int[] getLocation() {
        return new int[]{x, y};
    }

}
