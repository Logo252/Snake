package com.company.Exceptions;

/**
 * Class Exception is responsible for handling Snake game collision exceptions.
 *
 */
public class CollisionSnakeException extends Exception{

    /*
     * Coordinate x.
     */
    private int x;

    /*
     * Coordinate y.
     */
    private int y;

    /*
     * Default constructor.
     */
    public CollisionSnakeException(){

        super();
    }

    /*
     * Constructor with a message.
     */
    public CollisionSnakeException(String message){

        super(message);
    }

    /*
     * Constructor with a message and coordinates.
     */
    public CollisionSnakeException(String message, int x, int y) {

       super(message + " \n At coordinates: (" + x + ", " + y + ")");
    }

    /*
     * Constructor with a message and cause.
     */
    public CollisionSnakeException(String message, Throwable cause) {

        super(message, cause);
    }

}
