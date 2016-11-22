package com.lucek.androidgameengine2d.controllers;

import android.graphics.Point;
import android.support.annotation.NonNull;

import com.lucek.androidgameengine2d.util.LimitedQueue;

import java.nio.channels.Pipe;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


/**
 * Created by Daniel on 2016-10-17.
 */

public class HumanPlayerController extends AbstractPlayerController{

    public static java.util.Queue<Point> playerInputStream = new LimitedQueue<>(8);



    @Override
    public Point MakeMove(float currentTime, float maxAvailableTime, Point lastOpponentsMove) throws NoMoveMadeException{
        Point input = playerInputStream.poll();

        while(input!=null){
            if(IsMoveValid(input))  return input;
            else  input=playerInputStream.poll();

        }

        throw new NoMoveMadeException("No valid input from player available.");
    }
}
