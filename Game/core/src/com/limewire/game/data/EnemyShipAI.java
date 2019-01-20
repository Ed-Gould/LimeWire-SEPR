package com.limewire.game.data;

import java.util.HashSet;
import java.util.Set;

public class EnemyShipAI {
    private Ship ship;
    private Map map;
    private boolean nearAShip;
    private Coords nearestShip = new Coords();
    private Set<Coords> possibleMove;
    public EnemyShipAI(Ship ship,Map map){
        this.ship = ship;
        this.map = map;
        //get the possible moves for the enemy ship
        this.possibleMove = map.getPossibleMoves(ship);
        //move and attack if there is player's or other colleges' in the current ship's range.
        this.nearAShip = isNearAShip();
        if(nearAShip){
            moveShip();
        }
        //move randomly if there is no player's or other colleges' in the current ship's range.
        else{
            randomMove();
        }
    }

    //Check if there is a player's or other colleges' in the current ship's range.
    public boolean isNearAShip(){
        for(Coords possibleMove : possibleMove){//check all the possible move current ship have.

            if(map.getGrid()[possibleMove.x][possibleMove.y].ship != null){ //check if the grid contains a ship.
                if(! map.getShip(possibleMove).getTeam().equals(ship.getTeam())){//check if the ship is in the same team with the current ship, if not execute the following code.
                    //set the nearest ship as the ship founded, and return true to set nearAShip.
                    this.nearestShip.setX(possibleMove.x);
                    this.nearestShip.setY(possibleMove.y);
                    return true;
                }
            }
        }
        //return false if haven't find any player's or other colleges' in the current ship's range.
        return false;
    }
    //move the current ship to the nearest coordinate to the nearest ship founded.
    public void moveShip(){
        map.deleteShip(ship.getCoords());//remove the current ship from current position.
        //Search all the possible moves for current ship and find the coordinate that is adjacent to the nearest ship.
        for(Coords possibleMove : possibleMove){
            if((((Math.abs(possibleMove.x - nearestShip.x) == 1) && (possibleMove.y==nearestShip.y))
                    || ((Math.abs(possibleMove.y - nearestShip.y)== 1) && (possibleMove.x==nearestShip.x)))
            &&(map.getGrid()[possibleMove.x][possibleMove.y].ship == null)){
                //set the coordinate of current ship to the coordinate founded.
                ship.setX(possibleMove.x);
                ship.setY(possibleMove.y);
            }
        }
        //set the property of the map in current coordinate as occupied by this ship.
        map.setShip(ship);
    }

    //to delete the enemy ship founded.
    public Ship attackEnemyShip(){
        return map.getShip(nearestShip);//return the founded enemy ship for further uses.
    }

    //move the current ship randomly.
    public void randomMove(){
        //find a random coordinate in all possible moves for the current ship.
        Object object = possibleMove.toArray();
        int randomId = (int)(Math.random()*(((Object[]) object).length));
        Coords newPosition = new Coords();
        int count = 0;
        for(Coords possibleMove : possibleMove){
            if((count == randomId)&&(map.getGrid()[possibleMove.x][possibleMove.y].ship == null)){ //if the coordinate is the random one we chose and has no ship in it, execute the following code.
                //change the current ship to the new position.
                newPosition.setX(possibleMove.x);
                newPosition.setY(possibleMove.y);
                map.deleteShip(ship.getCoords());
                ship.setX(newPosition.x);
                ship.setY(newPosition.y);
                map.setShip(ship);
            }
            count+=1;
        }
    }

}