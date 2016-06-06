package com.company.Fruits;

import java.awt.Color;

/**
 * Class FruitMoreScore is responsible for managing fruit on the board and fruit stats.
 *
 */
public class FruitMoreScore extends Fruit {

    //**********************************************************************************************
    // CONSTRUCTORS...

    /**
     * FruitMoreScore's constructor.
     */
    public FruitMoreScore() {

        super();  // calling super class constructor...

        myTile = FruitTile.MORE_SCORE_FRUIT;

        setColorFruit(Color.ORANGE);
        setNextFruitScore(200);

    }

}


