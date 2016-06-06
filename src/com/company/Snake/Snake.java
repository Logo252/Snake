package com.company.Snake;

/*
 *                  PACKAGES
 * Awt contains all of the classes for creating user interfaces and for painting graphics and images.
 * Util contains the collections framework, legacy collection classes, event model, date and time facilities,
   internationalization, and miscellaneous utility classes (a string tokenizer, a random-number generator, and a bit array).
 * -----------------------------------------------------------------------------
 *                  CLASSES
 * Point class representing a location in (x,y) coordinate space, specified in integer precision.
 * LinkedList class is Doubly-linked list implementation of the List and Deque interfaces.
   Implements all optional list operations, and permits all elements (including null).
 * Color class encapsulates colors using the RGB format.
 *
 */

import com.company.Board.Board;
import com.company.Directions.Direction;

import java.awt.Color;
import java.awt.Point;

import java.util.LinkedList;

/**
 * Class Snake is managing snake on the board.
 */
public class Snake {

    // CONSTANTS...

    /**
     * The minimum length of the snake.
     */
    public static final int MIN_SNAKE_LENGTH = 5;

    /**
     * The maximum number of directions.
     */
    public static final int MAX_DIRECTIONS = 3;

    //**********************************************************************************************
    // FIELDS...

    /**
     * The Board object.
     */
    private Board board;

    /**
     * The array of snake tiles.
     */
    private SnakeTile[] snakeTiles;

    /**
     * The list that contains queued directions.
     */
    private LinkedList<Direction> directionsList;

    /**
     * The list that contains the points for the snake.
     */
    private LinkedList<Point> snakeList;

    /**
     * The number of fruits that we have eaten.
     */
    private int fruitsEaten;

    /**
     * The current score of the game.
     */
    private int score;

    /**
     * The color of snake.
     */
    private Color colorSnake;

    //public KeyEvent[] snake_key;



    //**********************************************************************************************
    // CONSTRUCTORS...

    public Snake(){

        this.snakeTiles = new SnakeTile[board.ROW_COUNT * board.COL_COUNT];

        //this.snake_key = new KeyEvent[4];

        setColorSnake(Color.GREEN);

        this.snakeList = new LinkedList<Point>();
        this.directionsList = new LinkedList<Direction>();

    }

    //**********************************************************************************************
    // METHODS FOR SNAKE TILES...SnakeTile


    /**
     * Cleaner for all snake tiles.
     */
    public void cleanSnakeTiles(){

        for(int i = 0; i < snakeTiles.length; i++) {
            snakeTiles[i] = null;
        }
    }

    /**
     * Setter for the snake tile at the desired coordinate.
     * @param point Coordinate of the tile.
     * @param tile One of the snake tiles.
     */
    public void setSnakeTile(Point point, SnakeTile tile) {

        setSnakeTile(point.x, point.y, tile);
    }

    /**
     * Sets the snake tile at the desired coordinate.
     * @param x X coordinate of the tile.
     * @param y Y coordinate of the tile.
     * @param tile One of the snake tiles.
     */
    public void setSnakeTile(int x, int y, SnakeTile tile) {

        snakeTiles[y * board.ROW_COUNT + x] = tile;
    }

    /**
     * Gets the snake tile at the desired coordinate.
     * @param x X coordinate of the tile.
     * @param y Y coordinate of the tile.
     * @return Snake tile on the board.
     */
    public SnakeTile getSnakeTile(int x, int y) {

        return snakeTiles[y * board.ROW_COUNT + x];
    }

    //**********************************************************************************************
    // SETTERS AND GETTERS...


    // -------------------Snake Color-------------------

    /**
     * Setter for variable colorSnake.
     * @param colorSnake The color of the snake.
     */
    public void setColorSnake(Color colorSnake){

        this.colorSnake = colorSnake;
    }

    /**
     * Getter for variable colorSnake.
     * @return The color of the snake.
     */
    public Color getColorSnake() {

        return colorSnake;
    }

    // -------------------Snake Score-------------------

    /**
     * Setter for variable score.
     * @param score Total score of game.
     */
    public void setScore(int score){

        this.score= score;
    }

    /**
     * Getter for variable score.
     * @return The score.
     */
    public int getScore() {

        return score;
    }

    // -------------------Snake Fruit-------------------

    /**
     * Setter for variable fruitsEaten.
     * @param fruitsEaten The fruits eaten.
     */
    public void setFruitsEaten(int fruitsEaten) {

        this.fruitsEaten = fruitsEaten;
    }

    /**
     * Getter for variable fruitsEaten.
     * @return The fruits eaten.
     */
    public int getFruitsEaten() {

        return fruitsEaten;
    }

    //**********************************************************************************************

    //----------------------------------------SnakeList---------------------------------------

    /**
     * Remover last element from the snake list.
     */
    public Point removeLastFromSnakeList() {

        return snakeList.removeLast(); // Removes and returns the last element from this list.
    }

    /**
     * Clearer for the snake list.
     */
    public void clearSnakeList() {

        snakeList.clear(); // clear - Removes all of the elements from this list.
    }

    /**
     * Adder for some part of the snake to the linked list.
     * @param part (x, y) coordinate.
     */
    public void addToSnakeList(Point part) {

        snakeList.add(part); // add - Appends the specified element to the end of this list.
    }

    /**
     * Getter for snake list size.
     * @return The current snakeList size.
     */
    public int getSnakeListSize() {

        return snakeList.size();
    }

    /**
     * Getter for the current snake list peek first of the snake.
     * @return The current peek first of the snake list.
     */
    public Point getSnakeListPeekFirst() {

        return snakeList.peekFirst();
    }

    /**
     * Pusher to the snake list.
     */
    public void pushToSnakeList(Point element) {

        snakeList.push(element);
    }

    // -----------------------------Directions List------------------------------------------

    /**
     * Poll for the direction list
     */
    public void pollDirectionList() {

        directionsList.poll(); // poll - retrieves and removes the head (first element) of this list.
    }

    /**
     * Getter for the current direction peek first of the snake.
     * @return The current direction peek first.
     */
    public Direction getDirectionPeekFirst() {

        // Retrieves, but does not remove, the first element of this list, or returns null if this list is empty.
        return directionsList.peekFirst();
    }

    /**
     * Getter for the current direction of the snake.
     * @return The current direction.
     */
    public Direction getDirectionPeek() {

        return directionsList.peek(); // gets the head (first element) of the linked list
    }

    /**
     * Getter for the current last direction of the snake.
     * @return The current directions peek last.
     */
    public Direction getDirectionPeekLast() {

        // Retrieves, but does not remove, the last element of this list, or returns null if this list is empty.
        return directionsList.peekLast();
    }

    /**
     * Clearer for the directions list.
     */
    public void clearDirectionsList() {

        directionsList.clear(); // clear - Removes all of the elements from this list.
    }

    /**
     * Adder for some direction to the linked list.
     * @param direct One of the four directions.
     */
    public void addToDirectionsList(Direction direct) {

        directionsList.add(direct); // add - Appends the specified element to the end of this list.
    }

    /**
     * Adder for some direction to the linked list as last element.
     * @param direct One of the four directions.
     */
    public void addLastToDirectionsList(Direction direct) {

        directionsList.addLast(direct); // addLast - Appends the specified element to the end of this list.
    }

    /**
     * Getter for directions size.
     * @return The current direction size.
     */
    public int getDirectionsSize() {

        return directionsList.size();
    }


}
