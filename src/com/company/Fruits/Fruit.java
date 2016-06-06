package com.company.Fruits;

/*
 *                          PACKAGES
 * Awt contains all of the classes for creating user interfaces and for painting graphics and images.
 * -----------------------------------------------------------------------------
 *                          CLASSES
 * Color class encapsulates colors using the RGB format.
 * Point class representing a location in (x,y) coordinate space, specified in integer precision.
 *
 * -----------------------------------------------------------------------------
 * A tile is a small image, usually rectangular or isometric, that acts like a puzzle piece of art for building larger images.
 */

import java.awt.Color;
import java.awt.Point;

/**
 * Class FruitNormal is responsible for managing fruit on the board and fruit stats.
 *
 */
public class Fruit extends FruitAbstract {


    //**********************************************************************************************
    // CONSTRUCTORS...

    /**
     * Fruit constructor.
     */
    public Fruit(){

        myTile = FruitTile.NORMAL_FRUIT;
        myPoint = new Point();

        // declaring coordinates as -1 for not adding fruit at the beginning...
        myPoint.x = -1;
        myPoint.y = -1;

        setColorFruit(Color.RED);
        setNextFruitScore(100);
    }

    //**********************************************************************************************
    // METHODS FOR FRUIT TILES(SETTERS AND GETTERS)...

    /**
     * Setter for the fruit tile at the desired coordinate.
     * @param point Coordinate of the tile.
     */
    public void setFruitTile(Point point) {

        myPoint = point;
    }

    /**
     * Sets the fruit tile at the desired coordinate.
     * @param x X coordinate of the tile.
     * @param y Y coordinate of the tile.
     */
    public void setFruitTile(int x, int y) {

        myPoint.x = x;
        myPoint.y = y;
    }

    /**
     * Gets the fruit tile at the desired coordinate.
     * @param x X coordinate of the tile.
     * @param y Y coordinate of the tile.
     * @return Tile on the board.
     */
    public FruitTile getFruitTile(int x, int y) {

        if( (myPoint.x == x) && (myPoint.y == y ) )
            return myTile;
        else
            return null;
    }

    /**
     * Gets Fruit tile.
     * @return Tile on the board.
     */
    public FruitTile getMyTile(){

        return myTile;
    }

    //**********************************************************************************************
    // METHODS FOR FRUIT (SETTERS AND GETTERS)...

    /**
     * Setter for variable nextFruitScore.
     * @param nextFruitScore The next fruit score.
     */
    public void setNextFruitScore(int nextFruitScore){

        this.nextFruitScore = nextFruitScore;
    }

    /**
     * Getter for variable nextFruitScore.
     * @return The next fruit score.
     */
    public int getNextFruitScore() {

        return nextFruitScore;
    }

    /**
     * Setter for variable colorFruit.
     * @param colorFruit The color of the fruit.
     */
    public void setColorFruit(Color colorFruit){

        this.colorFruit = colorFruit;
    }

    /**
     * Getter for variable colorFruit.
     * @return The color of the fruit.
     */
    public Color getColorFruit() {

        return colorFruit;
    }

}
