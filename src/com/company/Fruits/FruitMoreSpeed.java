package com.company.Fruits;

import java.awt.Color;

/**
 * Class FruitMoreSpeed is responsible for managing fruit on the board and fruit stats.
 *
 */
public class FruitMoreSpeed extends Fruit {

    //**********************************************************************************************
    // CONSTRUCTORS...

    /**
     * FruitMoreSpeed's constructor.
     */
    public FruitMoreSpeed() {

        super();  // calling super class constructor...

        myTile = FruitTile.MORE_SPEED_FRUIT;

        setColorFruit(Color.WHITE);
        setNextFruitScore(100);

    }

}
