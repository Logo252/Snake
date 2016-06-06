package com.company.Fruits;

import java.awt.Color;

/**
 * Class FruitLessSpeed is responsible for managing fruit on the board and fruit stats.
 *
 */
public class FruitLessSpeed extends Fruit {

    //**********************************************************************************************
    // CONSTRUCTORS...

    /**
     * FruitLessSpeed's constructor.
     */
    public FruitLessSpeed() {

        super();  // calling super class constructor...

        myTile = FruitTile.LESS_SPEED_FRUIT;

        setColorFruit(Color.BLUE);
        setNextFruitScore(100);

    }

}
