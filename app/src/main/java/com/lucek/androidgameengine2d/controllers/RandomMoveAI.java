package com.lucek.androidgameengine2d.controllers;
import android.graphics.Point;
import android.util.Log;

import com.lucek.androidgameengine2d.game.Field;

import java.util.Random;

/**
 * Created by Daniel on 2016-10-16.
 */

public class RandomMoveAI extends AbstractPlayerController {

    private Random rand = new Random();

    public RandomMoveAI(float movementTime){
        super(movementTime);
    }

    @Override
    public Point MakeMove(float currentTime, float maxAvailableTime, Point lastOpponentsMove){
        Field[][] board = GetBoardState();
        Log.d("Time:","currentTime: "+currentTime+" maxTime: "+maxAvailableTime);
        int x,y;
        Point returnValue;

        do{
            x=rand.nextInt(board.length);
            y=rand.nextInt(board[0].length);
            returnValue=new Point(x,y);
        }while(IsMoveValid(returnValue)==false);

        return returnValue;
    }
}
