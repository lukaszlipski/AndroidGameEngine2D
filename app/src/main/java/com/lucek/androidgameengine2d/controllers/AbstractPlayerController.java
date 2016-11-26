package com.lucek.androidgameengine2d.controllers;

import android.graphics.Point;

import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.gameplay.Game;

/**
 * Created by Daniel on 2016-10-15.
 */

public abstract class AbstractPlayerController {

    public class NoMoveMadeException extends Exception {
        NoMoveMadeException(String message)
        {
            super(message);
        }
    }

    private Game _gameInstance;
    private Field colour;
    private float movementTime = 1000.0f;

    public AbstractPlayerController(float movementTime){
        this.movementTime=movementTime;
    }

    /* SETTERS */
    public AbstractPlayerController setGameInstance(Game gameInstance){
        _gameInstance=gameInstance;
        return this;
    }
    public AbstractPlayerController setColour(Field colour){
        this.colour=colour;
        return this;
    }
    /* GETTERS */
    public float getMovementTime(){
        return movementTime;
    }


    /* API */

    /**
     * Returns a value from enum Field corresponding to how this player
     * is represented on in-game board arrays.
     * @return Field.BLACK or Field.WHITE respectively.
     */
    public Field GetColour(){
        return colour;
    }

    /**
     * Creates a copy of how the board looks like at the moment in game.
     * @return Copy of current board state.
     */
    protected Field[][] GetBoardState(){
        return _gameInstance.GetBoardState();
    }
    protected boolean IsMoveValid(Point point)
    {
        return _gameInstance.IsMoveValid(point);
    }
    protected float GetCurrentTimeMS() {
        return _gameInstance.GetCurrentTime();
    }

    /*
        Functions outside of AbstractPlayerController that may be found useful:
          -Group[] Group.FindAllGroups(Field[][] board);
          -Group Group.FindGroup(Field[][] board, Point location);
        Consult them for details.
     */

    /* OVERRIDABLE FUNCTIONS */

    /**
     * Function that needs to be implemented in every PlayerController. It's called every time this player's turn comes to be.
     * Runs best-move algorithm in case of AI or looks for player input in case of controllers representing a physical player.
     * Returns coordinates of a field at which this player's trying to put his piece on.
     * @param currentTime System time (in milliseconds) at which this function is called.
     * @param maxAvailableTime System time (in milliseconds) at which it should return a value.
     * @param lastOpponentsMove Where in his last turn your opponent has put his piece. Is null when called for first move in a game.
     * @return android.graphics.Point(int x, int y) representing board position of a piece.
     * @throws NoMoveMadeException for Human/Network controllers only. Signalises waiting for player's input.
     * Same function is then called in next frame.
     */
    abstract public Point MakeMove(float currentTime, float maxAvailableTime, Point lastOpponentsMove) throws NoMoveMadeException;

}
