package com.limewire.game.data;

import com.badlogic.gdx.graphics.Texture;

public class Square {
    private String type;
    Ship ship;
    boolean isPathable;
    private Texture texture;

    // Initialise the square with just the texture, then when creating the map set its contents,
    // e.g. Any ships, events, or buildings.
    public Square(String type){
        this.type = type;
        this.ship = null;
        setTexture(type);
        setPathable(type);
    }

    public Texture getTexture(){
        return this.texture;
    }

    public void setTexture(String type){
        if (type.equals("w")){
            this.texture =  new Texture("32water.png");
        }
        else if (type.equals("l")){
            this.texture = new Texture("32land.png");
        }
        else if (type.equals("d")){
            this.texture = new Texture("32derwentCollege.png");
        }
        else if (type.equals("j")){
            this.texture = new Texture("32jamesCollege.png");
        }
        else {
            this.texture = new Texture("32vanbrughCollege.png");
        }
    }

    public String getType(){
        return this.type;
    }

    public void setPathable(String type){
        if (type.equals("w")){
            this.isPathable = true;
        }
        else {
            this.isPathable = false;
        }
    }
}
