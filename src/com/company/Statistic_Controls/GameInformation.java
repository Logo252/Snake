package com.company.Statistic_Controls;

/*
 *                          PACKAGES
 * Swing is the principal GUI toolkit for the Java programming language.
 * Awt contains all of the classes for creating user interfaces and for painting graphics and images.
 * -----------------------------------------------------------------------------
 *                          CLASSES
 * JPanel class provides general-purpose containers for lightweight components.
 * Color class encapsulates colors using the RGB format.
 * Dimension class encapsulates the width and height of a component (in integer precision) in a single object.
 * Font class represents fonts, which are used to render text in a visible way.
 * Graphics class is the abstract base class for all graphics contexts that allow an application to draw
   onto components that are realized on various devices, as well as onto off-screen images.
 *
 * -----------------------------------------------------------------------------
 */

import com.company.Board.Board;
import com.company.Timing.Clock;
import com.company.Fruits.Fruit;
import com.company.Fruits.FruitTile;
import com.company.Snake.Snake;
import com.company.SnakeGame;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Class GameInformation is responsible for displaying statistics and controls to the player.
 *
 */
public class GameInformation extends JPanel{

    // CONSTANTS...

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -40557434900946408L;

    // ------------------Fonts------------------

    /**
     * The large font to draw.
     *
     * Font(String name, int style, int size)
     * Creates a new Font from the specified name, style and point size.
     */
    private static final Font LARGE_FONT = new Font("Centaur", Font.BOLD, 30);

    /**
     * The medium font to draw.
     */
    private static final Font MEDIUM_FONT = new Font("Centaur", Font.BOLD + Font.ITALIC, 20);

    /**
     * The small font to draw.
     */
    private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);

    /**
     * The other font to draw.
     */
    private static final Font OTHER_FONT = new Font("Centaur", Font.BOLD + Font.ITALIC, 14);

    // ------------------Offsets------------------

    /**
     * The statistics offset.
     */
    private static final int STATISTICS_OFFSET = 150;

    /**
     * The controls offset
     */
    private static final int CONTROLS_OFFSET = 320;

    /**
     * The message offset.
     */
    private static final int MESSAGE_OFFSET = 30;

    /**
     * The small offset.
     */
    private static final int LARGE_OFFSET = 100;

    /**
     * The large offset. (ofsetas)
     */
    private static final int SMALL_OFFSET = 50;

    //**********************************************************************************************
    // FIELDS...

    /**
     * The Snake object.
     */
    private Snake snake;

    private Snake snakeSecond;

    /**
     * The Clock object.
     */
    private Clock clock;

    //**********************************************************************************************
    // Declaring fruits...

    /**
     * The Normal fruit object.
     */
    private Fruit fruit;

    //----------------------

    /**
     * The FruitTile object.
     */
    private FruitTile fruit_tile;

    /**
     * The Snake Game object.
     */
    private SnakeGame game;

    //private ArrayList<Snake> snakeList;

    //**********************************************************************************************
    // CONSTRUCTORS...


    public GameInformation(Snake snake, Snake snakeSecond, Fruit fruit, SnakeGame game) {

        this.snake = snake;
        this.snakeSecond = snakeSecond;

        this.game = game;

        //this.snakeList = snakeList;

        clock = new Clock(10.0f);

        // Initializing fruits ------------------------------------
        this.fruit = fruit;
        fruit.setNextFruitScore(0);

        // Dimension(int width, int height) - Constructs a Dimension and initializes it to the specified width and specified height.
        setPreferredSize(new Dimension(300, Board.ROW_COUNT * Board.TILE_SIZE)); // (300,  25 * 25 = 625)
        setBackground(Color.BLACK);

    }

    //**********************************************************************************************
    // METHODS...

    /**
     * Setter for the specific fruit tile on the board.
     * @param fruit_tile One of the fruit tiles.
     */
    public void setFruitTile(FruitTile fruit_tile){

        this.fruit_tile = fruit_tile;
    }

    /**
     * Getter for the specific fruit tile on the board.
     * @return FruitTile on the board.
     */
    public FruitTile getFruitTile(){

        return this.fruit_tile;
    }

    /**
     * Setter for the specific fruit.
     * @param fruit One of the many fruits.
     */
    public void setFruit(Fruit fruit){

        this.fruit = fruit;
    }

    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics); // Calls the UI delegate's paint method, if the UI delegate is non-null.

        graphics.setColor(Color.WHITE); // setting the color to draw the font white

        // drawing the game name in window
        graphics.setFont(LARGE_FONT);

        // drawing the text given by the specified string, using this graphics context's current font and color.
        graphics.drawString("Snake Game", 45, 75); // drawString(string, x, y)

        // drawing categories on the window...
        graphics.setFont(MEDIUM_FONT);
        graphics.drawString("Statistics", LARGE_OFFSET, STATISTICS_OFFSET);
        graphics.drawString("Controls", LARGE_OFFSET, CONTROLS_OFFSET);

        // drawing category content on the window...
        graphics.setFont(SMALL_FONT);

        // drawing the date and time of computer...
        graphics.drawString("" + clock.getCurrentDateTime(), 80, 10);

        //  Checking if we need to print statistics and controls
        if (game.getNumberButtonPressed() ){

            //  Checking if SinglePlayer or MultiPlayer...
            if ( game.getSnakeGame() ){ // SINGLE PLAYER

                getSinglePlayer(graphics);
            }
            else        // MULTI PLAYER
            {
                getMultiPlayer(graphics);
            }
        }

    }

    /**
     * Getter for Single player controls and statistics.
     * @param graphics Graphics variable.
     */
    public void getSinglePlayer(Graphics graphics){


        //                       FIRST SNAKE
        //Draw the content for the statistics category.
        int drawY = STATISTICS_OFFSET + 10; // STATISTICS_OFFSET - 150

        graphics.drawString("Total Score: " + snake.getScore(), SMALL_OFFSET + 20, drawY += MESSAGE_OFFSET);
        graphics.drawString("Fruits Eaten: " + snake.getFruitsEaten(), SMALL_OFFSET + 20, drawY += MESSAGE_OFFSET);

        // drawing some specific fruit score on the board...
        graphics.drawString("Fruit Score: " + fruit.getNextFruitScore(), SMALL_OFFSET + 20, drawY += MESSAGE_OFFSET);

        //----------------------------------------------------------------------------------------

        //Draw the content for the controls category.
        drawY = CONTROLS_OFFSET; // CONTROLS_OFFSET - 320
        graphics.drawString("Move Up:   Up Arrow key", SMALL_OFFSET, drawY += MESSAGE_OFFSET);
        graphics.drawString("Move Down:     Down Arrow key", SMALL_OFFSET, drawY += MESSAGE_OFFSET);
        graphics.drawString("Move Left:     Left Arrow key", SMALL_OFFSET, drawY += MESSAGE_OFFSET);
        graphics.drawString("Move Right:    Right Arrow key", SMALL_OFFSET, drawY += MESSAGE_OFFSET);
        graphics.drawString("Pause Game:    P", SMALL_OFFSET, drawY += MESSAGE_OFFSET);

    }

    /**
     * Getter for Multi player controls and statistics.
     * @param graphics Graphics variable.
     */
    public void getMultiPlayer(Graphics graphics){

        //                       FIRST SNAKE
        //Draw the content for the statistics category.
        int drawY = STATISTICS_OFFSET; // STATISTICS_OFFSET - 150

        graphics.setFont(OTHER_FONT);

        graphics.drawString("1 Snake(Green) " , SMALL_OFFSET - 40, drawY += MESSAGE_OFFSET);

        graphics.setFont(SMALL_FONT);

        graphics.drawString("Total Score: " + snake.getScore(), SMALL_OFFSET - 40, drawY += MESSAGE_OFFSET);
        graphics.drawString("Fruits Eaten: " + snake.getFruitsEaten(), SMALL_OFFSET - 40, drawY += MESSAGE_OFFSET);

        // drawing some specific fruit score on the board...
        graphics.drawString("Fruit Score: " + fruit.getNextFruitScore(), SMALL_OFFSET - 40, drawY += MESSAGE_OFFSET);

        //                       SECOND SNAKE
        int drawSecondY = STATISTICS_OFFSET;

        graphics.setFont(OTHER_FONT);

        graphics.drawString("2 Snake(Light Gray) ", SMALL_OFFSET + 90, drawSecondY += MESSAGE_OFFSET);

        graphics.setFont(SMALL_FONT);

        graphics.drawString("Total Score: " + snakeSecond.getScore(), SMALL_OFFSET + 90, drawSecondY += MESSAGE_OFFSET);
        graphics.drawString("Fruits Eaten: " + snakeSecond.getFruitsEaten(), SMALL_OFFSET + 90, drawSecondY += MESSAGE_OFFSET);

        // drawing some specific fruit score on the board...
        graphics.drawString("Fruit Score: " + fruit.getNextFruitScore(), SMALL_OFFSET + 90, drawSecondY += MESSAGE_OFFSET);

        //----------------------------------------------------------------------------------------

        //Draw the content for the controls category.
        drawY = CONTROLS_OFFSET; // CONTROLS_OFFSET - 320
        graphics.drawString("Move Up:   Up Arrow key /  W", SMALL_OFFSET - 20, drawY += MESSAGE_OFFSET);
        graphics.drawString("Move Down:     Down Arrow key /  S", SMALL_OFFSET - 20, drawY += MESSAGE_OFFSET);
        graphics.drawString("Move Left:     Left Arrow key /  A", SMALL_OFFSET - 20, drawY += MESSAGE_OFFSET);
        graphics.drawString("Move Right:    Right Arrow key /  D", SMALL_OFFSET - 20, drawY += MESSAGE_OFFSET);
        graphics.drawString("Pause Game:    P", SMALL_OFFSET - 20, drawY += MESSAGE_OFFSET);
    }

}
