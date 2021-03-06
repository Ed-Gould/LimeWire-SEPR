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
        this.ship = null; // Initially assume there is no ship, set these after
        setTexture(type);
        setPathable(type);
    }

    public Texture getTexture(){
        return this.texture;
    }

    public void setTexture(String type){ // Sets the texture depending on the type of the square
        if (type.equals("w")){ // Water Squares
            this.texture =  new Texture("32water.png");
        }
        else if (type.equals("l")){ // Land squares
            this.texture = new Texture("32land.png");
        }
        else if (type.equals("d")){ // Derwent college
            this.texture = new Texture("32derwentCollege.png");
        }
        else if (type.equals("j")){ // James college
            this.texture = new Texture("32jamesCollege.png");
        }
        else if (type.equals("v")){ // Vanbrugh college
            this.texture = new Texture("32vanbrughCollege.png");
        }
        else if (type.equals("h")){ // History department
            this.texture = new Texture("32historyDepartment.png");
        }
        else if (type.equals("p")){ // Physics department
            this.texture = new Texture("32physicsDepartment.png");
        }
    }

    public String getType(){
        return this.type;
    }

    public void setPathable(String type){ // If the square is water, allow ships to move through it
        if (type.equals("w")){
            this.isPathable = true;
        }
        else {
            this.isPathable = false;
        }
    }
}
