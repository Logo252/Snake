package com.company.Board;

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
 * Point class representing a location in (x,y) coordinate space, specified in integer precision.
 * Graphics class is the abstract base class for all graphics contexts that allow an application to draw onto components
   that are realized on various devices, as well as onto off-screen images.

 * -----------------------------------------------------------------------------
 * A tile is a small image, usually rectangular or isometric, that acts like a puzzle piece of art for building larger images.
 */

import com.company.Fruits.Fruit;
import com.company.Fruits.FruitTile;
import com.company.Snake.Snake;
import com.company.Snake.SnakeTile;
import com.company.SnakeGame;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Class Board is responsible for managing and displaying content of game board.
 */
public class Board extends JPanel{

    // CONSTANTS...

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -1102632585936750607L;

    /**
     * The number of columns on the board.
     */
    public static final int COL_COUNT = 25;

    /**
     * The number of rows on the board.
     */
    public static final int ROW_COUNT = 25;

    /**
     * The size of each tile in pixels.
     */
    public static final int TILE_SIZE = 20;

    /**
     * The font to draw the text.
     *
     * Font(String name, int style, int size)
     * Creates a new Font from the specified name, style and point size.
     */
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);

    //**********************************************************************************************
    // FIELDS...

    /**
     * The SnakeGame object.
     */
    private SnakeGame game;

    //**********************************************************************************************
    // Declaring fruits...

    /**
     * The Normal fruit object.
     */
    private Fruit fruit;

    //**********************************************************************************************

    /**
     * The Snake object.
     */
    private Snake snake;

    private Snake snakeSecond;

    private Color color;

    //private ArrayList<Snake> snakeList;

    //**********************************************************************************************
    // CONSTRUCTOR...

    /**
     * Board's constructor.
     * @param game Snake game object.
     */
    public Board(SnakeGame game, Snake snake, Snake snakeSecond, Fruit fruit){

        this.game = game;

        //this.snakeList = snakeList;

        this.snake = snake;
        this.snakeSecond = snakeSecond;



        // fruits ------------------------------------
        this.fruit = fruit;

        //--------------------

        //setPreferredSize(new Dimension(500, 500) );
        setPreferredSize(new Dimension(COL_COUNT * TILE_SIZE, ROW_COUNT * TILE_SIZE));
        setBackground(Color.BLACK);
    }

    //**********************************************************************************************
    // METHODS...

    /**
     * Clears the board.
     */
    public void clearBoard(){

        // cleaning all tiles on the board
        snake.cleanSnakeTiles();
        snakeSecond.cleanSnakeTiles();

    }

    @Override
    public void paintComponent(Graphics graphics) {

        super.paintComponent(graphics); // Calls the UI delegate's paint method, if the UI delegate is non-null

        // Looping through each tile on the board drawing it if it is not null...
        for(int x = 0; x < COL_COUNT; x++) {

            for(int y = 0; y < ROW_COUNT; y++) {

                /*for(int u = 0; u < snakeList.size(); u++){ // all array

                    SnakeTile sn_tile = snakeList.get(u).getSnakeTile(x, y);

                    if (sn_tile != null)
                        drawSnakeTile(x * TILE_SIZE, y * TILE_SIZE, sn_tile, graphics);
                } */

                //-----------------------------------------------------------------

                FruitTile fr_tile = fruit.getFruitTile(x, y);

                SnakeTile sn_tile = snake.getSnakeTile(x, y);

                if (fr_tile != null)
                    drawFruitTile(x * TILE_SIZE, y * TILE_SIZE, fr_tile, graphics);

                if (sn_tile != null){
                    color = snake.getColorSnake();
                    drawSnakeTile(x * TILE_SIZE, y * TILE_SIZE, sn_tile, graphics);

                }


                //---------------------------------------------------------------
                 if (!game.getSnakeGame() ){ // IF MULTI PLAYER

                    SnakeTile sn_tile_second = snakeSecond.getSnakeTile(x, y);

                    if (sn_tile_second != null){
                        color = Color.LIGHT_GRAY;
                        drawSnakeTile(x * TILE_SIZE, y * TILE_SIZE, sn_tile_second, graphics);
                    }

                }

            }

        }

        /*
        * Drawing the grid on the board to see exactly where we in relation to the fruit.
        *
        * The panel is one pixel too small to draw the bottom and right
        * outlines(konturai), so we outline(nupiesti kontura) the board with a rectangle separately.
        */
        graphics.setColor(Color.DARK_GRAY); // Sets this graphics context's current color to the specified color

        /*
         * Drawing the outline of the specified rectangle. The left and right edges of the rectangle are at x and x + width.
         * The top and bottom edges are at y and y + height. The rectangle is drawn using the graphics context's current color.
         */
        graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        // getWidth() - Returns the current width of this component
        // getHeight() - Returns the current height of this component

        for(int x = 0; x < COL_COUNT; x++) {

            for(int y = 0; y < ROW_COUNT; y++) {

                // drawLine - draws a line, using the current color, between the points (x1, y1) and (x2, y2)
                // in this graphics context's coordinate system.
                graphics.drawLine(x * TILE_SIZE, 0, x * TILE_SIZE, getHeight());
                graphics.drawLine(0, y * TILE_SIZE, getWidth(), y * TILE_SIZE);
            }
        }

        if (game.getSnake_amount_message() ){

            graphics.setColor(Color.WHITE);

            int centerForX = getWidth() / 2;
            int centerForY = getHeight() / 2;

            String Message = "";
            String Message2 = "";

            Message = "Press 1 for Single player";
            Message2 = "Press 2 for Multi Player";

            graphics.setFont(FONT); // Sets this graphics context's font to the specified font.

            graphics.drawString(Message, centerForX - graphics.getFontMetrics().stringWidth(Message) / 2, centerForY - 50);
            graphics.drawString(Message2, centerForX - graphics.getFontMetrics().stringWidth(Message2) / 2, centerForY );

        }
        else // show a message on the screen based on the current game state
        if(game.getIsGameOver() || game.getIsNewGame() || game.getIsPaused() ) {

            graphics.setColor(Color.WHITE);

            // getting the center coordinates of the board
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2;

            // Allocate the messages for and set their values based on the game state
            String largeMessage = "";
            String smallMessage = "";

            if(game.getIsNewGame() ) {

                largeMessage = "Snake Game!";
                smallMessage = "Press Enter to Start";
            } else if(game.getIsGameOver() ) {

                largeMessage = "Game Over!";
                smallMessage = "Press Enter to Restart";
            } else if(game.getIsPaused() ) {

                largeMessage = "Paused!";
                smallMessage = "Press P to Resume";
            }

            // setting message font and draw messages in the center of the board...
            graphics.setFont(FONT); // Sets this graphics context's font to the specified font.

            /*
             * drawString(string, x, y) - draws the text given by the specified string, using this graphics
             * context's current font and color
             *
             * getFontMetric() - Gets the font metrics of the current font.
             */
            graphics.drawString(largeMessage, centerX - graphics.getFontMetrics().stringWidth(largeMessage) / 2, centerY - 50);
            graphics.drawString(smallMessage, centerX - graphics.getFontMetrics().stringWidth(smallMessage) / 2, centerY + 50);
        }

    }

    /**
     * Setter for the specific fruit.
     * @param fruit One of the many fruits.
     */
    public void setFruit(Fruit fruit){

        this.fruit = fruit;
    }

    /**
     * Draws a fruit tile on the board.
     * @param x The x coordinate of the tile (in pixels).
     * @param y The y coordinate of the tile (in pixels).
     * @param tile The fruit tile to draw.
     * @param graphics The graphics object to draw.
     */
    private void drawFruitTile(int x, int y, FruitTile tile, Graphics graphics){

        /*
         * A fruit is depicted as a small circle that with a bit of padding
         * on each side.
        */
        graphics.setColor(fruit.getColorFruit());
        graphics.fillOval(x + 2, y + 2, TILE_SIZE - 4, TILE_SIZE - 4); // Fills an oval bounded by the specified rectangle with the current color.

    }

    /**
     * Draws a snake tile on the board.
     * @param x The x coordinate of the tile (in pixels).
     * @param y The y coordinate of the tile (in pixels).
     * @param tile The snake tile to draw.
     * @param graphics The graphics object to draw.
     */
    private void drawSnakeTile(int x, int y, SnakeTile tile, Graphics graphics){

        switch(tile){

            /*
             * The snake body is depicted as a green square that takes up the
             * entire tile.
            */
            case SNAKE_BODY:
                graphics.setColor(color);
                graphics.fillRect(x, y, TILE_SIZE, TILE_SIZE); // Fills the specified rectangle.
                break;

            /*
             * The snake head is depicted similarly to the body, but with two
             * lines (representing eyes) that indicate it's direction.
             */
            case SNAKE_HEAD:

                //Fill the tile in with green color.
                graphics.setColor(color);
                graphics.fillRect(x, y, TILE_SIZE, TILE_SIZE);
                break;
        }
    }

}
