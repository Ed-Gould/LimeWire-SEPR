package com.limewire.game.data;

import com.badlogic.gdx.graphics.Texture;

public class Square {
    Ship ship;

    boolean isPathable;
    Texture texture;

    // Initialise the square with just the texture, then when creating the map set its contents,
    // e.g. Any ships, events, or buildings.
    public Square(String type){
        this.ship = null;
        setTexture(type);
        setPathable(type);
    }


    public Texture getTexture(){
        return this.texture;
    }

    public void setTexture(String type){
        if (type.equals("water")){
            this.texture =  new Texture("32water.png");
        }
        else {
            this.texture = new Texture("32land.png");
        }
    }

    public void setPathable(String type){
        if (type.equals("water")){
            this.isPathable = true;
        }
        else {
            this.isPathable = false;
        }
    }
}
