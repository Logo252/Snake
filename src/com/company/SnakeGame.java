package com.company;

/*
 *                  PACKAGES
 * Swing is the principal GUI toolkit for the Java programming language.
 * Awt contains all of the classes for creating user interfaces and for painting graphics and images.
 * Util contains the collections framework, legacy collection classes, event model, date and time facilities,
   internationalization, and miscellaneous utility classes (a string tokenizer, a random-number generator, and a bit array).
 * Awt.event provides interfaces and classes for dealing with different types of events fired by AWT components.
 * -----------------------------------------------------------------------------
 *                  CLASSES
 * JFrame class is an extended version of java.awt. Frame that adds support for the JFC/Swing component architecture.
   Frame class is a top-level window with a title and a border. The size of the frame includes any area designated for the border.
 * WindowConstants class used to control the window-closing operation.
 * BorderLayout class lays out a container, arranging and resizing its components to fit in five regions:
   north, south, east, west, and center.
 * Point class representing a location in (x,y) coordinate space, specified in integer precision.
 * LinkedList class is Doubly-linked list implementation of the List and Deque interfaces.
   Implements all optional list operations, and permits all elements (including null).
 * Random class. An instance of this class is used to generate a stream of pseudorandom numbers.
   The class uses a 48-bit seed, which is modified using a linear congruential formula.
 * KeyAdapter class is an abstract adapter class for receiving keyboard events. The methods in this class are empty.
    This class exists as convenience for creating listener objects.
 * KeyEvent class is the component-level keyboard event.
 * Color class encapsulates colors using the RGB format.
 *
 * Runnable interface should be implemented by any class whose instances are intended to be executed by a thread.
 */

import com.company.Board.Board;
import com.company.Directions.Direction;
import com.company.Exceptions.CollisionSnakeException;

import com.company.Fruits.Fruit;
import com.company.Fruits.FruitLessScore;
import com.company.Fruits.FruitMoreScore;
import com.company.Fruits.FruitLessSpeed;
import com.company.Fruits.FruitMoreSpeed;
import com.company.Fruits.FruitTile;

import com.company.Snake.Snake;
import com.company.Snake.SnakeTile;
import com.company.Statistic_Controls.GameInformation;
import com.company.Threads.ThreadFruit;
import com.company.Timing.Clock;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Point;

//import java.util.ArrayList;
import java.util.Random;

/**
 * Class SnakeGame is responsible for the game logic.
 */
public class SnakeGame extends JFrame {

    // CONSTANTS...

    /**
     * The Serial Version UID.
     */
    private static final long serialVersionUID = 6678292058307426314L;

    /**
     * The number of milliseconds that should pass between each frame.
     */
    private static final long FRAME_TIME = 20L;


    //**********************************************************************************************
    // FIELDS...

    // ----------------------Fruits---------------------

    /**
     * The NormalFruit object.
     */
    private Fruit fruit;

    // ********************************************************************************

    /**
     * The Snake object.
     */
    private Snake snake;

    private Snake snakeSecond;

    /**
     * The Board object.
     */
    private Board board;

    /**
     * The Game Information object.
     */
    private GameInformation gameInfo;

    /**
     * The random number generator (for spawning fruits).
     */
    private Random random;

    /**
     * The Clock object.
     */
    private Clock clock;

    /**
     * Whether or not we are running a new game.
     */
    private boolean isNewGame;

    /**
     * Whether or not the game is over.
     */
    private boolean isGameOver;

    /**
     * Whether or not the game is paused.
     */
    private boolean isPaused;

    //--------------------------------------------------------
    /**
     *  The ThreadFruit variable managing fruit position.
     */
    private ThreadFruit threadFruit;

    /**
     *  The Thread variable for managing threadFruit.
     */
    private Thread thread;

    /**
     *  Flag for pause button, thread.
     */
    private boolean pause_thread;

    //--------------------------------------------------------------------
    /**
     *  Flag for snake amount message showing on the board
     */
    private boolean snake_amount_message;

    /**
     *  Flag for snake singlePlayer or MultiPlayer
     */
    private boolean single_snake;

    //---------------------------------------------------------------------
    /**
     *  Flag if enter was pressed after new game or game over...
     */
    private boolean enter_button_pressed;

    /**
     *  Flag if one of the numbers was pressed (1 or 2)...
     */
    private boolean number_button_pressed;


    //private ArrayList<Snake> snakeList;



    //**********************************************************************************************
    // CONSTRUCTORS...

    /**
     * SnakeGame's constructor.
     * New window and controller input
     */
    public SnakeGame(){

        super("Snake"); // calls JFrame constructor - creates a new, initially invisible Frame with the specified title.

        setLayout(new BorderLayout()); // sets the LayoutManager
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // What happens when the frame closes...
        setResizable(false); // sets whether this frame is resizable by the user

        //------------------------------------------------------------------

        // Initializing snake...
        this.snake = new Snake();

        this.snakeSecond = new Snake();

        //this.snakeList = new ArrayList<Snake>();



        // Initializing fruit ------------------------------------
        this.fruit = new Fruit();

        //------------------------------------------------------------------

        // Initializing board and gameInfo, adding them to the window...
        this.board = new Board(this,snake,snakeSecond, fruit);
        this.gameInfo = new GameInformation(snake, snakeSecond, fruit, this);

        // adding the specified component to container at the given position
        add(board, BorderLayout.CENTER); // method from java.awt.Container
        add(gameInfo, BorderLayout.EAST);


        manageKeyboard(); // managing input from the keyboard...

        // Resizing the window to the appropriate size, center it on the screen and displaying it
        pack(); // size of the frame
        setLocationRelativeTo(null); // if the argument is null, the window is centered onscreen
        setVisible(true); // showing window

        // Creating an object of  ThreadFruit
        threadFruit = new ThreadFruit(this); // creating new thread...

        pause_thread = true; // at first snake game will be paused, that's why it is true

    }

    //**********************************************************************************************
    // METHODS...

    /**
     * Receives key event from the player.
     */
    private void manageKeyboard(){

        /*
        * addKeyListener(class Component) - Adds the specified key listener to receive key events from this component.
        *  If l is null, no exception is thrown and no action is performed.
        *
        * Adds a new key listener to the frame to process input.
        */
        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {

                switch(e.getKeyCode()) {

                    /*
                     * If the game is not paused, and the game is not over...
                     * NORTH
                     * Ensure that the direction list is not full, and that the most
                     * recent direction is adjacent to North before adding the
                     * direction to the list.
                     */
                    case KeyEvent.VK_UP:

                        if(!isPaused && !isGameOver) {

                            if(snake.getDirectionsSize()  < snake.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snake.getDirectionPeekLast();

                                if(last != Direction.DOWN && last != Direction.UP) {
                                    snake.addLastToDirectionsList(Direction.UP); // Appends to the end of this list.
                                }
                            }
                        }
                        break;

                    /*
                    * If the game is not paused, and the game is not over...
                    * SOUTH
                    * Ensure that the direction list is not full, and that the most
                    * recent direction is adjacent to South before adding the
                    * direction to the list.
                    */
                    case KeyEvent.VK_DOWN:

                        if(!isPaused && !isGameOver) {

                            if(snake.getDirectionsSize()  < snake.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snake.getDirectionPeekLast();

                                if(last != Direction.UP && last != Direction.DOWN) {
                                    snake.addLastToDirectionsList(Direction.DOWN); // Appends to the end of this list.
                                }
                            }
                        }
                        break;

                    /*
                    * If the game is not paused, and the game is not over...
                    * WEST
                    * Ensure that the direction list is not full, and that the most
                    * recent direction is adjacent to West before adding the
                    * direction to the list.
                    */
                    case KeyEvent.VK_LEFT:

                        if(!isPaused && !isGameOver) {

                            if(snake.getDirectionsSize()  < snake.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snake.getDirectionPeekLast();

                                if(last != Direction.RIGHT && last != Direction.LEFT) {
                                    snake.addLastToDirectionsList(Direction.LEFT);  // Appends to the end of this list.
                                }
                            }
                        }
                        break;

                    /*
                    * If the game is not paused, and the game is not over...
                    * EAST
                    * Ensure that the direction list is not full, and that the most
                    * recent direction is adjacent to East before adding the
                    * direction to the list.
                    */
                    case KeyEvent.VK_RIGHT:

                        if(!isPaused && !isGameOver) {

                            if(snake.getDirectionsSize() < snake.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snake.getDirectionPeekLast();

                                if(last != Direction.LEFT && last != Direction.RIGHT) {
                                    snake.addLastToDirectionsList(Direction.RIGHT); // Appends to the end of this list
                                }
                            }
                        }
                        break;

                    /*
                    * If the game is not over, toggle the paused flag and update
                    * the logicTimer's pause flag accordingly.
                    */
                    case KeyEvent.VK_P:

                        if(!isGameOver) {

                            isPaused = !isPaused;
                            clock.setIsPaused(isPaused);

                            //---------------------------------------------------
                            if (pause_thread){ // if game was paused

                                threadFruit.setPaused(true);
                                pause_thread = false;

                            } else // if game was not paused
                            {
                                threadFruit.setPaused(false);
                                pause_thread = true;

                            }

                        }
                        break;

                    /*
                    * Reset the game if one is not currently in progress.
                    */
                    case KeyEvent.VK_ENTER:

                        if(isNewGame || isGameOver) {

                            snake_amount_message = true;
                            setSnake_amount_message(snake_amount_message);

                            enter_button_pressed = true;

                        }
                        break;

                    case KeyEvent.VK_NUMPAD1: // Single Player

                        if( (isNewGame || isGameOver) && enter_button_pressed) {

                            if (isNewGame){
                                thread = new Thread(threadFruit);
                                thread.start();
                            }

                            if (isGameOver){
                                threadFruit.setPaused(false);  // fruit will be placed on the board

                            }

                            single_snake = true;
                            setSnakeGame(single_snake);

                            snake_amount_message = false;
                            setSnake_amount_message(snake_amount_message);

                            resetGame();

                        }
                        break;

                    case KeyEvent.VK_NUMPAD2: // Multi Player

                        if( (isNewGame || isGameOver) && enter_button_pressed) {

                             if (isNewGame){
                               thread = new Thread(threadFruit);
                               thread.start();
                             }

                             if (isGameOver){
                                threadFruit.setPaused(false); // fruit will be placed on the board


                             }

                            //-------------------------------------------------------
                            //KeyEvent a = new KeyEvent();

                            //Snake snake1 = new Snake();
                            //snake1.snake_key[1] = KeyEvent.VK_UP;

                            //snakeList.add(snake1);

                            //-------------------------------------------------------

                            single_snake = false;
                            setSnakeGame(single_snake);

                            snake_amount_message = false;
                            setSnake_amount_message(snake_amount_message);

                            resetGame();

                        }
                        break;

                    //--------------------------------- Second Snake Buttons---------------------------------

                    case KeyEvent.VK_W:

                        if(!isPaused && !isGameOver) {

                            if(snakeSecond.getDirectionsSize()  < snakeSecond.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snakeSecond.getDirectionPeekLast();

                                if(last != Direction.DOWN && last != Direction.UP) {
                                    snakeSecond.addLastToDirectionsList(Direction.UP); // Appends to the end of this list.
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_S:

                        if(!isPaused && !isGameOver) {

                            if(snakeSecond.getDirectionsSize()  < snakeSecond.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snakeSecond.getDirectionPeekLast();

                                if(last != Direction.UP && last != Direction.DOWN) {
                                    snakeSecond.addLastToDirectionsList(Direction.DOWN); // Appends to the end of this list.
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_A:

                        if(!isPaused && !isGameOver) {




                            if(snakeSecond.getDirectionsSize()  < snakeSecond.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snakeSecond.getDirectionPeekLast();

                                if(last != Direction.RIGHT && last != Direction.LEFT) {
                                    snakeSecond.addLastToDirectionsList(Direction.LEFT);  // Appends to the end of this list.
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_D:

                        if(!isPaused && !isGameOver) {

                            if(snakeSecond.getDirectionsSize() < snakeSecond.MAX_DIRECTIONS) {
                                // Retrieves, but does not remove, the last element of this list
                                Direction last = snakeSecond.getDirectionPeekLast();

                                if(last != Direction.LEFT && last != Direction.RIGHT) {
                                    snakeSecond.addLastToDirectionsList(Direction.RIGHT); // Appends to the end of this list
                                }
                            }
                        }
                        break;

                }
            }
        });

    }

    /**
     * Starts the game running.
     */
    public void startGame(){

        // Initialization
        this.random = new Random();
        this.clock = new Clock(10.0f); // float...

        this.isNewGame = true;

        clock.setIsPaused(true); // Time is paused at first...

        // The Snake game will run until the game window is closed
        while (true){

            manageKeyboard();

            //Get the current frame start time.---- nanosecond - 10 (-9) (1 billionth) of a second
            long start = System.nanoTime(); // nanoTime() - returns the current value of the system timer, in nanoseconds
            //System.out.println(" \n"+ start);

            //Update the clock timer.
            clock.update();

            //System.out.println("\n" + clock.hasElapsedCycle());
            // If a cycle has elapsed on the logic timer, then updating the game
            if(clock.hasElapsedCycle() ) {

                try{
                    updateGame();   // Game updater

                }
                catch(CollisionSnakeException e){

                    //System.out.println("Collision occurred!!!");
                    //e.printStackTrace();
                }
            }

            //Repaint the board and side panel with the new content.
            board.repaint();
            gameInfo.repaint();

            // Calculate the delta time between since the start of the frame
            // and sleep for the excess time to get the frame rate.
            long delta = (System.nanoTime() - start) / 1000000L;
            //System.out.println("\n"+ delta );
            if(delta < FRAME_TIME) { // < 20

                try {
                    Thread.sleep(FRAME_TIME - delta); // Causes the currently executing thread to sleep for the specified number of milliseconds
                } catch(InterruptedException e) {
                    e.printStackTrace(); // tells what happened and where in the code this happened.
                }
            }

            }

    }

    /**
     * Resets snake game stats if collision occured with wall or the snake.
     */
    private void resetStats(){

        isGameOver = true;
        clock.setIsPaused(true);
        threadFruit.setPaused(true);  // spawnFruit method will not be called...

    }

    /**
     * Updates the snake game.
     */
    private void updateGame() throws CollisionSnakeException {

        /*
        * Here we peek at the next direction rather than polling it. While
        * not game breaking, polling the direction here causes a small bug
        * where the snake's direction will change after a game over (though
        * it will not move).
        */
        Direction direction = snake.getDirectionPeekFirst();


        /*
        * Calculating the new point that the snake's head will be at after the update.
        */
        Point head = new Point( snake.getSnakeListPeekFirst() );

        switch(direction) {

            case UP:
                head.y--;
                break;
            case DOWN:
                head.y++;
                break;
            case LEFT:
                head.x--;
                break;
            case RIGHT:
                head.x++;
                break;
        }

        // Creating new snake tile...
        SnakeTile snake_tile = null;

        /*
        * If the snake has moved out of bounds (hit a wall), we can just
        * return that it's collided with itself, as both cases are handled identically.
        */
        if(head.x < 0 || head.x >= Board.COL_COUNT || head.y < 0 || head.y >= Board.ROW_COUNT) {

            snake_tile = SnakeTile.SNAKE_BODY; //Pretend we collided with our body.

            resetStats();

            throw new CollisionSnakeException("Snake hit a wall!", head.x + 1, head.y + 1);
        }

        /*
        * Getting the type of tile that the head of the snake collided with. If
        * the snake hit a wall, SnakeBody will be returned, as both conditions are handled identically.
        */
        SnakeTile old_snake;
        FruitTile old_fruit = null;

        if (snake_tile == null){

            /*
            * Here we get the tile that was located at the new head position and
            * remove the tail from of the snake and the board if the snake is
            * long enough, and the tile it moved onto is not a fruit.
            *
            * If the tail was removed, we need to retrieve the old tile again
            * in case the tile we hit was the tail piece that was just removed
            * to prevent a false game over.
            */

                old_snake = snake.getSnakeTile(head.x, head.y);

                old_fruit = fruit.getFruitTile(head.x, head.y);

                // if we didn't get any kind of fruit and snake size is more than 5 ...
                if ((old_fruit == null) && (snake.getSnakeListSize() > snake.MIN_SNAKE_LENGTH)) {

                    Point tail = snake.removeLastFromSnakeList(); // getting snake tail coordinates(point)
                    snake.setSnakeTile(tail, null);
                    old_snake = snake.getSnakeTile(head.x, head.y);

                }

            /*
            * Update the snake's position on the board if we didn't collide with
            * our tail:
            *
            * 1. Set the old head position to a body tile.
            * 2. Add the new head to the snake.
            * 3. Set the new head position to a head tile.
            *
            * If more than one direction is in the queue, poll it to read new input
            */
                if (old_snake != SnakeTile.SNAKE_BODY || old_fruit != null) {

                    snake.setSnakeTile(snake.getSnakeListPeekFirst(), SnakeTile.SNAKE_BODY);
                    snake.pushToSnakeList(head); // inserts element in the front of the list
                    snake.setSnakeTile(head, SnakeTile.SNAKE_HEAD); //setting tile as a snake head

                    //------------------------------------------------------------------------

                    if (snake.getDirectionsSize() > 1) { //getDirectionsSize -  Returns the number of elements in direction list.

                        snake.pollDirectionList(); // Retrieves and removes the head (first element) of this list.
                    }

                }

                snake_tile = old_snake;

                if(snake_tile == SnakeTile.SNAKE_BODY){

                    resetStats();

                   throw new CollisionSnakeException("Snake hit himself!", head.x, head.y);
                }

        }

        //--------------------------------------------------------------------------------------------
        /*
        * The different possible collisions.
        *
        * Fruit: If we collided with a fruit, we increment the number of
        * fruits that we've eaten, update the score, and spawn a new fruit.
        *
        * SnakeBody: If we collided with our tail (or a wall), we flag that
        * the game is over and pause the game.
        *
        * If no collision occurred, we simply decrement the number of points
        * that the next fruit will give us if it's high enough...
        *
        */
        if( (old_fruit != null) && (snake_tile == null) )  {

            int fruitsEaten = snake.getFruitsEaten();
            snake.setFruitsEaten(fruitsEaten + 1);
            int score = snake.getScore();

            // updating total score with any kind of fruit's score...
            score += fruit.getNextFruitScore();

            //------------------------------------------------------------------------
            snake.setScore(score);

            // setting speed for the snake...
            if ( (fruit.getMyTile() == FruitTile.NORMAL_FRUIT) || (fruit.getMyTile() == FruitTile.LESS_SCORE_FRUIT) ||
                    (fruit.getMyTile() == FruitTile.MORE_SCORE_FRUIT) ) // RED OR ORANGE OR YELLOW
                clock.setCyclesPerSecond(13.0f);

            else if (fruit.getMyTile() == FruitTile.LESS_SPEED_FRUIT) // BLUE
                clock.setCyclesPerSecond(8.0f);
            else if (fruit.getMyTile() == FruitTile.MORE_SPEED_FRUIT) // WHITE
                clock.setCyclesPerSecond(18.0f);


            spawnFruit(); // spawning a new fruit on the board

            throw new CollisionSnakeException("Snake hit a fruit!", head.x, head.y);

        } else {

            int next_fruit_score; // new variable for next score for the fruit on the board

            next_fruit_score = fruit.getNextFruitScore();

            if (next_fruit_score > 10){

                fruit.setNextFruitScore(next_fruit_score - 1);
            }

        }

        // ----------------------------------------------------------------
        //              SECOND SNAKE IF MULTI PLAYER

        if (!single_snake){

            Direction directionSecond = snakeSecond.getDirectionPeekFirst();

            Point headSecond = new Point( snakeSecond.getSnakeListPeekFirst() );

            switch(directionSecond) {

                case UP:
                    headSecond.y--;
                    break;
                case DOWN:
                    headSecond.y++;
                    break;
                case LEFT:
                    headSecond.x--;
                    break;
                case RIGHT:
                    headSecond.x++;
                    break;
            }

            // Creating new snake tile...
            SnakeTile snakeSecond_tile = null;

            if(headSecond.x < 0 || headSecond.x >= Board.COL_COUNT || headSecond.y < 0 || headSecond.y >= Board.ROW_COUNT) {

                snakeSecond_tile = SnakeTile.SNAKE_BODY; //Pretend we collided with our body.

                resetStats();

                throw new CollisionSnakeException("Snake hit a wall!", headSecond.x + 1, headSecond.y + 1);
            }

            SnakeTile old_snakeSecond;
            FruitTile old_fruitSecond = null;

            // ----------------------  if second snake hit first snake...     ------------
            snake_tile = snake.getSnakeTile(headSecond.x, headSecond.y);

            if (snake_tile == SnakeTile.SNAKE_BODY || snake_tile == SnakeTile.SNAKE_HEAD){

                resetStats();
                throw new CollisionSnakeException("Second Snake hit first snake!", headSecond.x, headSecond.y);
            }

            // if first snake hit second snake...
            snake_tile = snakeSecond.getSnakeTile(head.x, head.y);

            if (snake_tile == SnakeTile.SNAKE_BODY || snake_tile == SnakeTile.SNAKE_HEAD){

                resetStats();
                throw new CollisionSnakeException("First Snake hit second snake!", headSecond.x, headSecond.y);
            }

            //----------------------------------------------------------------------------------

            if (snakeSecond_tile == null){

                old_snakeSecond = snakeSecond.getSnakeTile(headSecond.x, headSecond.y);

                old_fruitSecond = fruit.getFruitTile(headSecond.x, headSecond.y);

                // if we didn't get any kind of fruit and snake size is more than 5 ...
                if ((old_fruitSecond == null) && (snakeSecond.getSnakeListSize() > snakeSecond.MIN_SNAKE_LENGTH)) {

                    Point tail = snakeSecond.removeLastFromSnakeList(); // getting snake tail coordinates(point)
                    snakeSecond.setSnakeTile(tail, null);
                    old_snakeSecond = snakeSecond.getSnakeTile(headSecond.x, headSecond.y);

                }

                if (old_snakeSecond != SnakeTile.SNAKE_BODY || old_fruit != null) {

                    snakeSecond.setSnakeTile(snakeSecond.getSnakeListPeekFirst(), SnakeTile.SNAKE_BODY);
                    snakeSecond.pushToSnakeList(headSecond); // inserts element in the front of the list
                    snakeSecond.setSnakeTile(headSecond, SnakeTile.SNAKE_HEAD); //setting tile as a snake head

                    //------------------------------------------------------------------------

                    if (snakeSecond.getDirectionsSize() > 1) { //getDirectionsSize -  Returns the number of elements in direction list.

                        snakeSecond.pollDirectionList(); // Retrieves and removes the head (first element) of this list.
                    }

                }

                snakeSecond_tile = old_snakeSecond;

                if(snakeSecond_tile == SnakeTile.SNAKE_BODY){

                    resetStats();

                    throw new CollisionSnakeException("Snake hit himself!", headSecond.x, headSecond.y);
                }

            }

            if( (old_fruitSecond != null) && (snakeSecond_tile == null) )  {

                int fruitsEaten = snakeSecond.getFruitsEaten();
                snakeSecond.setFruitsEaten(fruitsEaten + 1);
                int score = snakeSecond.getScore();

                // updating total score with any kind of fruit's score...
                score += fruit.getNextFruitScore();

                //------------------------------------------------------------------------
                snakeSecond.setScore(score);

                // setting speed for the snake...
                if ( (fruit.getMyTile() == FruitTile.NORMAL_FRUIT) || (fruit.getMyTile() == FruitTile.LESS_SCORE_FRUIT) ||
                        (fruit.getMyTile() == FruitTile.MORE_SCORE_FRUIT) ) // RED OR ORANGE OR YELLOW
                    clock.setCyclesPerSecond(13.0f);

                else if (fruit.getMyTile() == FruitTile.LESS_SPEED_FRUIT) // BLUE
                    clock.setCyclesPerSecond(8.0f);
                else if (fruit.getMyTile() == FruitTile.MORE_SPEED_FRUIT) // WHITE
                    clock.setCyclesPerSecond(18.0f);


                spawnFruit(); // spawning a new fruit on the board

                throw new CollisionSnakeException("Snake hit a fruit!", headSecond.x, headSecond.y);

            } else {

                int next_fruit_score; // new variable for next score for the fruit on the board

                next_fruit_score = fruit.getNextFruitScore();

                if (next_fruit_score > 10){

                    fruit.setNextFruitScore(next_fruit_score - 1);
                }

            }


        }

    }

    /**
     * Resets the game variables to default and starts a new game.
     */
    private void resetGame(){

        if (single_snake){ // SinglePlayer

            // resetting the score statistics...
            snake.setScore(0);
            snake.setFruitsEaten(0);

            // resetting new game, game over and paused flags...
            this.isNewGame = false;
            this.isGameOver = false;

            // creating snake head on the board, center
            Point head = new Point(Board.COL_COUNT / 2, Board.ROW_COUNT / 2);

            // clearing the snake list and adding head
            snake.clearSnakeList();
            snake.addToSnakeList(head);

            // clearing the board and adding head
            board.clearBoard();

            snake.setSnakeTile(head, SnakeTile.SNAKE_HEAD);

            // clearing the directions and adding north as the default direction
            snake.clearDirectionsList();
            snake.addToDirectionsList(Direction.UP);

        }
        else { // MultiPlayer

            snake.setScore(0);
            snake.setFruitsEaten(0);

            snakeSecond.setScore(0);
            snakeSecond.setFruitsEaten(0);

            this.isNewGame = false;
            this.isGameOver = false;

            Point head = new Point( (Board.COL_COUNT - Board.COL_COUNT / 3), Board.ROW_COUNT  / 2);
            Point head2 = new Point(Board.COL_COUNT / 4, Board.ROW_COUNT / 2);

            snake.clearSnakeList();
            snake.addToSnakeList(head);

            snakeSecond.clearSnakeList();
            snakeSecond.addToSnakeList(head2);

            board.clearBoard();

            snake.setSnakeTile(head, SnakeTile.SNAKE_HEAD);
            snakeSecond.setSnakeTile(head2, SnakeTile.SNAKE_HEAD);

            snake.clearDirectionsList();
            snake.addToDirectionsList(Direction.UP);

            snakeSecond.clearDirectionsList();
            snakeSecond.addLastToDirectionsList(Direction.UP);

        }

        // resetting the clock
        clock.reset();
        clock.setCyclesPerSecond(13.0f); // declaring speed like it was at the beginning

        // spawn a new fruit on the board
        spawnFruit();

        //----------------------------------------------------
        enter_button_pressed = false;

        number_button_pressed = true;
        setNumberButtonPressed(true);

    }

    /**
     * Spawns a new fruit on the board.
     */
    public void spawnFruit(){

        /*
         * Getting a random index based on the number 5 for all types of fruit.
         * 0 - NORMAL_FRUIT
         * 1 - LESS_SCORE_FRUIT
         * 2 - MORE_SCORE_FRUIT
         * 3 - LESS_SPEED_FRUIT
         * 4 - MORE_SPEED_FRUIT
         *
         */
        int index_fruit = random.nextInt(5);

        /*
        * Getting a random index based on the number of free spaces left on the board.
        *
        *  random.nextInt(625 - snake.getSnakeListSize() );      25 * 25 = 625
        *  snake.getSnakeListSize() - snake length...
        */
        int index_board = random.nextInt(Board.COL_COUNT * Board.ROW_COUNT - snake.getSnakeListSize() );

        // random.nextInt() - Returns int value between 0 (inclusive) and the specified value (exclusive)

        /*
        * While we could just as easily choose a random index on the board
        * and check it if it's free until we find an empty one, that method
        * tends to hang if the snake becomes very large.
        *
        * Looping through until it finds the nth free index
        * and selects uses that. The game will be able to
        * locate an index at a relatively constant rate regardless of the size of the snake.
        */
        int freeFound = -1;

        for(int x = 0; x < Board.COL_COUNT; x++) {

            for(int y = 0; y < Board.ROW_COUNT; y++) {

                SnakeTile sn_tile = snake.getSnakeTile(x, y);
                SnakeTile second_sn_tile = snakeSecond.getSnakeTile(x, y);

                FruitTile fr_tile = fruit.getFruitTile(x, y);

                if( (sn_tile == null && second_sn_tile == null && fr_tile == null) || fr_tile != null){

                    if(++freeFound == index_board) {

                        // Getting some fruit fruit from index_fruit and setting it on the board...
                        if (index_fruit == 0){

                            fruit = new Fruit();
                            fruit.setFruitTile(x, y);

                            board.setFruit(fruit);
                            gameInfo.setFruit(fruit);

                        }
                        else if (index_fruit == 1){

                            fruit = new FruitLessScore();
                            fruit.setFruitTile(x, y);

                            board.setFruit(fruit);
                            gameInfo.setFruit(fruit);

                        } else if (index_fruit == 2){

                            fruit = new FruitMoreScore();
                            fruit.setFruitTile(x, y);

                            board.setFruit(fruit);
                            gameInfo.setFruit(fruit);


                        } else if (index_fruit == 3){

                            fruit = new FruitLessSpeed();
                            fruit.setFruitTile(x, y);

                            board.setFruit(fruit);
                            gameInfo.setFruit(fruit);


                        } else if (index_fruit == 4){

                            fruit = new FruitMoreSpeed();
                            fruit.setFruitTile(x, y);

                            board.setFruit(fruit);
                            gameInfo.setFruit(fruit);

                        }

                        break;

                    }
                }
            }
        }
    }

    //**********************************************************************************************
    // SETTERS AND GETTERS...

    /**
     * Setter for variable isNewGame.
     * @param isNewGame The game is new or not.
     */
    public void setIsNewGame(boolean isNewGame){

        this.isNewGame = isNewGame;
    }

    /**
     * Getter for variable isNewGame.
     * @return The new game flag.
     */
    public boolean getIsNewGame() {

        return isNewGame;
    }

    /**
     * Setter for variable isGameOver.
     * @param isGameOver The game is over or not.
     */
    public void setIsGameOver(boolean isGameOver){

        this.isGameOver = isGameOver;
    }

    /**
     * Getter for variable isGameOver.
     * @return The game over flag.
     */
    public boolean getIsGameOver() {

        return isGameOver;
    }

    /**
     * Setter for variable isPaused.
     * @param isPaused The game is paused or not.
     */
    public void setIsPaused(boolean isPaused){

        this.isPaused = isPaused;
    }

    /**
     * Getter for variable isPaused.
     * @return The paused flag.
     */
    public boolean getIsPaused() {

        return isPaused;
    }

    //--------------------------------------------------------------------------------
    /**
     * Setter for variable snake_amount_message.
     * @param snake_amount_message The game to choose with 1 sake or 2 messages.
     */
    public void setSnake_amount_message(boolean snake_amount_message){

        this.snake_amount_message = snake_amount_message;
    }

    /**
     * Getter for variable snake_amount_message.
     * @return The game flag for 1 or 2 snakes messages.
     */
    public boolean getSnake_amount_message(){

        return snake_amount_message;
    }

    //------------------------  SinglePlayer or MultiPlayer methods   --------------------------------------------------------
    /**
     * Setter for variable single_snake.
     * @param single_snake The game to choose with 1 sake or 2.
     */
    public void setSnakeGame(boolean single_snake){

        this.single_snake = single_snake;
    }

    /**
     * Getter for variable snake_amount_message.
     * @return The game to choose with 1 snake or 2.
     */
    public boolean getSnakeGame(){

        return single_snake;
    }

    //----------------------------------------------------------------------------

    /**
     * Setter for variable number_button_pressed.
     * @param number_button_pressed Which number was pressed 1 or 2.
     */
    public void setNumberButtonPressed(boolean number_button_pressed){

        this.number_button_pressed = number_button_pressed;
    }

    /**
     * Getter for variable number_button_pressed.
     */
    public boolean getNumberButtonPressed(){

        return number_button_pressed;
    }

}
