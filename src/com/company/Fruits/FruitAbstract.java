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


import com.company.Board.Board;

import java.awt.Color;
import java.awt.Point;

/**
 * Abstract class FruitAbstract cannot be instantiated, implements FruitInterface .
 *
 */
public abstract class FruitAbstract implements FruitInterface{

    // FIELDS...

    /**
     * The Board object.
     */
    protected Board board;

    /**
     * The number of points that the next fruit will award.
     */
    protected int nextFruitScore;

    /**
     * The color of the fruit.
     */
    protected Color colorFruit;

    /**
     * Point variable(coordinates x and y).
     */
    protected Point myPoint;

    /**
     * Fruit tile variable.
     */
    protected FruitTile myTile;

    //**********************************************************************************************
    // ABSTRACT METHODS...

    /**
     * Setter for variable nextFruitScore.
     * @param nextFruitScore The next fruit score.
     */
    public abstract void setNextFruitScore(int nextFruitScore);

    /**
     * Setter for variable colorFruit.
     * @param colorFruit The color of the fruit.
     */
    public abstract void setColorFruit(Color colorFruit);

}
