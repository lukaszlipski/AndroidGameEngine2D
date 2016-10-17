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

    /* SETTERS */
    public AbstractPlayerController setGameInstance(Game gameInstance){
        _gameInstance=gameInstance;
        return this;
    }
    public AbstractPlayerController setColour(Field colour){
        this.colour=colour;
        return this;
    }

    /* API */
    public Field GetColour(){
        return colour;
    }
    protected Field[][] GetBoardState(){
        return _gameInstance.GetBoardState();
    }
    protected boolean IsMoveValid(Point point)
    {
        return _gameInstance.IsMoveValid(point);
    }

    /* OVERRIDABLE FUNCTIONS */
    abstract public Point MakeMove() throws NoMoveMadeException;

}
