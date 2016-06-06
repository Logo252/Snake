package com.company.Fruits;

import java.awt.Color;

/**
 * Class FruitLessScore is responsible for managing fruit on the board and fruit stats.
 */
public class FruitLessScore extends Fruit {

    //**********************************************************************************************
    // CONSTRUCTORS...

    /**
     * FruitLessScore's constructor.
     */
    public FruitLessScore() {

        super(); // calling super class constructor...

        myTile = FruitTile.LESS_SCORE_FRUIT;


        setColorFruit(Color.YELLOW);
        setNextFruitScore(50);

    }

}
