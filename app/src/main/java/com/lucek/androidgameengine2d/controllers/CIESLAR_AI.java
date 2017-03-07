package com.lucek.androidgameengine2d.controllers;
import android.graphics.Point;

import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.gameplay.Group;

import java.util.Random;

import java.util.ArrayList;
import java.util.List;


public class CIESLAR_AI extends AbstractPlayerController {

    private Field [][] board;
    private Field color;
    private Field enemyColor;
    private List<Point> possibleMoves;
    private int possibleMovesCount;
    private int[] moveWins;
    private List<Integer> bestMoves;
    private List<Point> emptyFields;



    public CIESLAR_AI(long movementTime)
    {

        super(movementTime);

        emptyFields = new ArrayList<>();
        possibleMoves = new ArrayList<>();
        bestMoves = new ArrayList<>();

        for( int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                emptyFields.add(new Point(i,j));
            }
        }


    }

    @Override
    public Point MakeMove(Point lastOpponentsMove) throws NoMoveMadeException
    {
        board = GetBoardState();

        if(GetColour() == Field.WHITE)
        {
            enemyColor = Field.BLACK;
            color = Field.WHITE;
        }
        else
        {
            enemyColor = Field.WHITE;
            color = Field.BLACK;
        }

        if(lastOpponentsMove != null)
        {
            emptyFields.remove(lastOpponentsMove);
        }

        possibleMoves.clear();

        for(Point field : emptyFields)
        {
            if(IsMoveValid(field))
            {
                possibleMoves.add(field);
            }
        }

        possibleMovesCount = possibleMoves.size();
        moveWins = new int [possibleMovesCount];
        resetWins();

        Point bestMove;

        while(GetRemainingTimeMS()>0);
        {
            for(int i = 0; i < possibleMovesCount; i++)
            {
                simulation(i);
            }
        }


        bestMove = getBestMove();

        emptyFields.remove(bestMove);
        return bestMove;
    }


    private void simulation(int move)
    {
        Field [][] subBoard = new Field [board.length][board[0].length];
        Field tempEnemyColor = enemyColor;

        Random randomGenerator = new Random();
        List<Point> simulationPossibleMoves = new ArrayList<>();

        Point point = possibleMoves.get(move);

        for(int i = 0; i < subBoard.length; i++)
        {
            for(int j = 0; j < subBoard[0].length; j++)
            {
                subBoard[i][j] = board[i][j];
            }
        }

        subBoard[point.x][point.y] = GetColour();

        simulationPossibleMoves = availableMoves(subBoard, tempEnemyColor);

        boolean simulation = true;

        while(simulation)
        {

            if(GetRemainingTimeMS() <= 0)
                break;

            if(simulationPossibleMoves.size() != 0)
            {
                int nextMove = randomGenerator.nextInt(simulationPossibleMoves.size());
                subBoard[simulationPossibleMoves.get(nextMove).x][simulationPossibleMoves.get(nextMove).y] = tempEnemyColor;

                if(tempEnemyColor == color)
                    tempEnemyColor = enemyColor;
                else
                    tempEnemyColor = color;

                simulationPossibleMoves = availableMoves(subBoard, tempEnemyColor);

            }
            else
            {
                if(tempEnemyColor == enemyColor)
                {
                    moveWins[move] += 1;
                }
                simulation = false;
            }
        }
    }
    private List<Point> availableMoves(Field [][] subBoard, Field color )
    {
        List<Point> moves = new ArrayList<>();

        for(int i = 0; i < subBoard.length; i ++)
        {
            for(int j = 0; j < subBoard[0].length; j++)
            {
                if(checkMove(new Point(i,j), subBoard, color))
                    moves.add(new Point(i,j));
            }
        }
        return  moves;
    }

    private boolean checkMove(Point point, Field[][] subBoard, Field color)
    {
        if(!(point.x < subBoard.length && point.y < subBoard[0].length && point.x >= 0 && point.y >= 0))
            return false;

        if(subBoard[point.x][point.y] != Field.EMPTY)
            return false;

        Field[][] subSubBoard = new Field[subBoard.length][subBoard[0].length];


        for(int i = 0; i < subBoard.length; i++)
        {
            for(int j = 0; j < subBoard[0].length; j++)
                subSubBoard[i][j] = subBoard[i][j];
        }

        subSubBoard[point.x][point.y] = color;
        Group [] groups = Group.FindAllGroups(subSubBoard);

        for(Group group : groups)
        {
            if(!group.HasLiberty(subSubBoard))
                return false;
        }

        return true;
    }

    private Point getBestMove()
    {
        int max = moveWins[0];
        int point;
        Point move;

        for(int i = 0; i < moveWins.length; i ++)
            if(moveWins[i] > max)
                max = moveWins[i];

        for(int i = 0; i < moveWins.length; i ++)
            if(moveWins[i] == max)
                bestMoves.add(i);

        Random generator = new Random();

        point = bestMoves.get(generator.nextInt(bestMoves.size()));
        move = possibleMoves.get(point);

        return move;
    }

    private void resetWins()
    {
        for (int i = 0; i < moveWins.length; i++)
        {
            moveWins[i] = 0;
        }

        bestMoves.clear();
    }

}

