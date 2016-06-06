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

/**
 * Interface FruitInterface contains behaviors that a class implements.
 *
 */
public interface FruitInterface {

    // METHODS...

    /**
     * Gets the fruit tile at the desired coordinate.
     * @param x X coordinate of the tile.
     * @param y Y coordinate of the tile.
     * @return Tile on the board.
     */
    FruitTile getFruitTile(int x, int y);

    /**
     * Getter for variable nextFruitScore.
     * @return The next fruit score.
     */
    int getNextFruitScore();

    /**
     * Getter for variable colorFruit.
     * @return The color of the fruit.
     */
    Color getColorFruit();

}
