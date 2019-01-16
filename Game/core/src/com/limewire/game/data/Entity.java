package com.limewire.game.data;

/**so your player, item, asset, etc. all have a coords field
you could make them extend an abstract class,
Entity or something, then have all the coords methods on there to avoid duplication


I mean, you would have a Coords class which has moveLeft or whatever, which updates Coord's internal state of x and y
then an abstract class Entity which has a Coords field
then inside Entity, moveLeft or whatever, which calls coords.MoveLeft
then you don't need to declare moveLeft for player, item, asset
because they inherit it from Entity
you could not have the methods on Entity, and instead do entity.getCoords.moveLeft,
but it would be cleaner to do entity.moveLeft, and let the entity class deal with calling Coords
*/

public abstract class Entity extends Coords{
    private int x, y;


}
