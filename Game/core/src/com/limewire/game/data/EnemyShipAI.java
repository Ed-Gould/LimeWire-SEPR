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
        this.possibleMove = map.getPossibleMoves(ship);
        this.nearAShip = isNearAShip();
        if(nearAShip){
            moveShip();
        }
    }

    public boolean isNearAShip(){
        for(Coords possibleMove : possibleMove){
            if(map.getGrid()[possibleMove.x][possibleMove.y].ship != null){
                if(! map.getShip(possibleMove).getTeam().equals(ship.getTeam())){
                    this.nearestShip.setX(possibleMove.x);
                    this.nearestShip.setY(possibleMove.y);
                    nearAShip = true;
                    break;
                }
            }
        }
        return nearAShip;
    }

    public void moveShip(){
        Coords shipPosition = new Coords();
        shipPosition.x = ship.getX();
        shipPosition.y = ship.getY();
        map.deleteShip(shipPosition);
        for(Coords possibleMove : possibleMove){
            if(((Math.abs(possibleMove.x - nearestShip.x) == 1) && (possibleMove.y==nearestShip.y))
                    || ((Math.abs(possibleMove.y - nearestShip.y)== 1) && (possibleMove.x==nearestShip.x))){
                ship.setX(possibleMove.x);
                ship.setY(possibleMove.y);
            }
        }
        map.setShip(ship);
    }

    public Ship attackEnemyShip(){
        return map.getShip(nearestShip);
    }

}
