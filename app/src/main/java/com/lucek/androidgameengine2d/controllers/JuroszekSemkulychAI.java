package com.lucek.androidgameengine2d.controllers;

import android.graphics.Point;

import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.gameplay.Game;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Bartek on 2017-01-24.
 */

public class JuroszekSemkulychAI extends AbstractPlayerController {

    /**
     * Bartłomiej Juroszek
     * Vitalii Semkulych
     */
    private double budget;
    public JuroszekSemkulychAI(long movementTime) {
        super(movementTime);
        this.budget=movementTime/4;
    }
    private ArrayList<Point> SHOTPoints=new ArrayList<Point>();

    private double possibleMoves=0;

    private double x=0;
    @Override
    public Point MakeMove(Point lastOpponentsMove) throws AbstractPlayerController.NoMoveMadeException {
        x++;
        this.budget+=(1/25)*Math.pow(x,(5/2));
        Field[][] board = GetBoardState();
        possibleMoves=countPossibleMoves();

        double[][] movesMade=new double[board.length][board[0].length];
        int[][] movesMadeNumber=new int[board.length][board[0].length];

        //this.budget=Math.ceil(startSimulations/possibleMoves);
        for (int x = 0; x < board.length;x++)
        {
            for(int y=0;y<board[0].length;y++)
            {

                if (IsMoveValid(new Point(x, y))) {
                    SHOTPoints.add(new Point(x,y));
                }
            }}

        if(IsMoveValid(new Point(0, 0)))
        {
            return new Point(0,0);
        }
        if(IsMoveValid(new Point(0, board.length-1)))
        {
            return new Point(0,board.length-1);
        }
        if(IsMoveValid(new Point(board.length-1, 0)))
        {
            return new Point(board.length-1,0);
        }
        if(IsMoveValid(new Point(board.length-1, board.length-1)))
        {
            return new Point(board.length-1,board.length-1);
        }

        while(GetRemainingTimeMS()>0) {


            if(possibleMoves<=1) {
                break;
            }
            ArrayList<Point> temporary=makeShotSimulations(movesMade, movesMadeNumber);
            SHOTPoints.clear();
            SHOTPoints.addAll(temporary);


        }

        Point result= SHOTPoints.get(SHOTPoints.size()-1);
        SHOTPoints.clear();
        return result;
    }

    private double makeSimulation(Point move)
    {
        double result=0;
        Field actualPlayerColour=GetColour();
        Field opponentPlayerColour=(actualPlayerColour==Field.BLACK)?Field.WHITE:Field.BLACK;
        Field[][] boardStateSimulation=new Field[GetBoardState().length][GetBoardState()[0].length];
        System.arraycopy( GetBoardState(), 0, boardStateSimulation, 0, GetBoardState().length );
        boardStateSimulation[move.x][move.y]=actualPlayerColour;
        Game simulation =new Game(new RandomMoveAI(50),opponentPlayerColour, new RandomMoveAI(50),actualPlayerColour,boardStateSimulation);
        while(true)
        {
            try{
                simulation.Update();
            }catch(Game.GameIsOverException ex)
            {
                result=(actualPlayerColour==ex.winner)?1:0;
                break;
            }
            catch(Game.InvalidMoveException ex)
            {

                break;
            }
            finally{

            }

        }
        return result;

    }

    private double countPossibleMoves()
    {
        double result=0;
        for (int x = 0; x < GetBoardState().length;x++)
        {
            for(int y=0;y<GetBoardState()[0].length;y++)
            {

                if (IsMoveValid(new Point(x, y))) {
                    result++;
                }
            }}
        return result;
    }

    private double getNumberofMoves()
    {
        return budget/(SHOTPoints.size()*Math.log(possibleMoves)/Math.log(2));
    }

    private ArrayList<Point> makeShotSimulations(double movesMade[][], int movesMadeNumber[][])
    {
        ArrayList<Point> resultSet=new ArrayList<Point>();
        for(Point p:SHOTPoints)
        {if(GetRemainingTimeMS()<0)
        {
            break;
        }
            for(int i=0; i<getNumberofMoves(); i++)
            {
                movesMade[p.x][p.y]+=makeSimulation(p);
                movesMadeNumber[p.x][p.y]++;
            }
        }
        for(int i=0; i<movesMade.length;i++)
        {
            for (int z=0;z<movesMade[0].length;z++)
            {
                if(IsMoveValid(new Point(i,z))) {
                    if(movesMadeNumber[i][z]!=0)
                        movesMade[i][z]= movesMade[i][z]/movesMadeNumber[i][z];
                }
            }
        }

        sort(SHOTPoints,0,SHOTPoints.size()-1,movesMade);
        for (int x = 0; x < GetBoardState().length;x++)
        {
            for(int y=0;y<GetBoardState()[0].length;y++)
            {
                movesMade[x][y]=0;
                movesMadeNumber[x][y]=0;
            }}
        double liczba=Math.ceil(SHOTPoints.size()/2);
        if(liczba<1) liczba=1;
        for(int i=0; i<liczba; i++) {
            resultSet.add(SHOTPoints.get(SHOTPoints.size() - (i+1)));
        }
        return resultSet;



    }

    public static void sort(ArrayList<Point> list, int from, int to, double movesMade[][]) {
        if (from < to) {
            int pivot = from;
            int left = from + 1;
            int right = to;
            double pivotValue = movesMade[list.get(pivot).x][list.get(pivot).y];
            while (left <= right) {
                // left <= to -> limit protection
                while (left <= to && pivotValue >=  movesMade[list.get(left).x][list.get(left).y]) {
                    left++;
                }
                // right > from -> limit protection
                while (right > from && pivotValue < movesMade[list.get(right).x][list.get(right).y]) {
                    right--;
                }
                if (left < right) {
                    Collections.swap(list, left, right);
                }
            }
            Collections.swap(list, pivot, left - 1);
            sort(list, from, right - 1,movesMade); // <-- pivot was wront!
            sort(list, right + 1, to,movesMade);   // <-- pivot was wront!
        }
    }

}
