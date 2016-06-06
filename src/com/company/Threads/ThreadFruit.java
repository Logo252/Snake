package com.company.Threads;

/*                                   CLASSES
/* Runnable interface should be implemented by any class whose instances are intended to be executed by a thread.
/*
 */

import com.company.SnakeGame;

/**
 * Class ThreadFruit is responsible for the thread spawning fruit on the board.
 */
public class ThreadFruit implements Runnable {

    // FIELDS...

     /**
      * Flag, to spawn fruit or not.
      */
     private boolean paused;

     /**
      * The SnakeGame object.
      */
    private SnakeGame snakeGame;

    // CONSTRUCTOR...

     /**
      * ThreadFruit's constructor.
      */
    public ThreadFruit(SnakeGame snakeGame){

        this.snakeGame = snakeGame;
        paused = false;

    }

    // METHOD...

    /**
     * Runs spawnFruit method after 5 seconds from the time then fruit spawned...
     */
    public void run(){

        while (true){

            try{
                Thread.sleep(5000); // for 5 seconds... 5000 - MilliSeconds (1 s - 1000 mili s)
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }

            if (!paused)
                snakeGame.spawnFruit();

        }

    }

     /**
      * Setter for variable paused.
      * @param paused The thread is paused or not.
      */
     public void setPaused(boolean paused){

         this.paused = paused;

     }

}
