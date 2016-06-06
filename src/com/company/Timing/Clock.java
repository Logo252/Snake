package com.company.Timing;

/*
 *                          PACKAGES
 * Util - Contains the collections framework, legacy collection classes, event model, date and time facilities,
   internationalization, and miscellaneous utility classes (a string tokenizer, a random-number generator, and a bit array).
 * Text - Provides classes and interfaces for handling text, dates, numbers, and messages in a manner independent of natural languages.
 * -----------------------------------------------------------------------------
 *                          CLASSES
 * Date class represents a specific instant in time with millisecond precision.
 * DateFormat class is an abstract class for date/time formatting subclasses which formats and parses
   dates or time in a language-independent manner.
 * SimpleDateFormat class is a concrete class for formatting and parsing dates in a locale-sensitive manner.
   It allows for formatting (date -> text), parsing (text -> date), and normalization.
 */

import java.util.Date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 * Class Clock is responsible for managing time of the game.
 */
public class Clock {

    // FIELDS...

    /**
     * The number of milliseconds for one cycle.
     */
    private float millisPerCycle;

    /**
     * The last time that the clock was updated (used for calculating the delta time).
     */
    private long lastUpdate;

    /**
     * The number of cycles that have elapsed(prabego) and have not yet been polled(gauti). (elapse - einantis)
     */
    private int elapsedCycles;

    /**
     * The amount of excess(perteklius) time towards the next elapsed(praejes) cycle.
     */
    private float excessCycles;

    /**
     * The clock is paused or not.
     */
    private boolean isPaused;

    //**********************************************************************************************
    // CONSTRUCTORS...
    /*
     * Clock's constructor.
     * @param cyclesPerSecond The number of cycles that elapse per second.
     */
    public Clock(float cyclesPerSecond){

        setCyclesPerSecond(cyclesPerSecond); // cyclesPerSecond - 9.0f
        reset(); // resetting the clock stats...

    }

    //**********************************************************************************************
    // METHODS...

    /**
     * Seter for variable cyclesPerSecond.
     * @param cyclesPerSecond The number of cycles per second.
     */
    public void setCyclesPerSecond(float cyclesPerSecond) {

        this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000; // (1.0f / 9.0f) * 1000 = 111,111111111
    }

    /**
     * Reseter for clock stats.
     *
     * Elapsed cycles and cycle excess will be reset to 0, the last update time will be reset to the current time,
     * and the paused flag will be set to false.
     */
    public void reset() {

        this.elapsedCycles = 0; // einamieji ciklai
        this.excessCycles = 0.0f; // virsyti ciklai
        this.lastUpdate = getCurrentTimeMilliSeconds();
        this.isPaused = false;
    }

    /**
     * Updater for clock stats.
     *
     * The number of elapsed cycles, as well as the cycle excess will be calculated only if the clock is not paused.
     * This method should be called every frame even when paused to prevent any nasty surprises with the delta time.
     */
    public void update() {

        //Get the current time and calculate the delta time.
        long currUpdate = getCurrentTimeMilliSeconds(); // System.nanoTime() / 1000000L
        float delta = (float)(currUpdate - lastUpdate) + excessCycles;

        //Update the number of elapsed and excess ticks if we're not paused.
        if(!isPaused){
        // Math.floor - the largest floating-point value that less than or equal to the argument and is equal to a mathematical integer.
            this.elapsedCycles += (int)Math.floor(delta / millisPerCycle);
            this.excessCycles = delta % millisPerCycle; // % - modul
        }

        //Set the last update time for the next update cycle.
        this.lastUpdate = currUpdate;
    }

    /**
     * Checker for seeing if a cycle has elapsed for this clock yet. If so,
     * the number of elapsed cycles will be decremented by one.
     * @return Whether or not a cycle has elapsed.
     */
    public boolean hasElapsedCycle() { // Ar prabego ciklas

        if(elapsedCycles > 0) {
            this.elapsedCycles--;
            return true;
        }
        return false;
    }

    /**
     * Checker for seeing if a cycle has elapsed for this clock yet.
     * Unlike hasElapsedCycle, the number of cycles will not be decremented.
     * If the number of elapsed cycles is greater than 0.
     * @return Whether or not a cycle has elapsed.
     */
    /* public boolean peekElapsedCycle() {

        return (elapsedCycles > 0);
    } */

    /**
     * Getter for the current time in milliseconds using the computer's high resolution clock.
     * @return The current time in milliseconds.
     */
    private long    getCurrentTimeMilliSeconds() {

        return (System.nanoTime() / 1000000L);
    }

    /**
     * Getter for current date and time.
     * @return Current date and time.
     */
    public String getCurrentDateTime(){

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");
        Date date = new Date();

        return dateFormat.format(date);
    }

    /**
     * Setter for variable isPaused.
     * @param paused Whether or not to pause this clock.
     *
     * Pauses or unpauses the clock. While paused, a clock will not update
     * elapsed cycles or cycle excess, though the method should
     * still be called every frame to prevent issues.
     */
    public void setIsPaused(boolean paused) {

        this.isPaused = paused;
    }

    /**
     * Getter for variable isPaused.
     * @return Whether or not this clock is paused.
     */
    public boolean getIsPaused() {

        return isPaused;
    }

}
