package com.lucek.androidgameengine2d.controllers;

import android.graphics.Point;
import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.gameplay.Game;
import com.lucek.androidgameengine2d.gameplay.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.util.Log;

// Autorzy: Wojciech Borkowski, Mikołaj Bartoszek

public class WBMBAlgorithm extends AbstractPlayerController {

    @Override
    public Point MakeMove(Point lastOpponentMove) {

        Field [][] startBoardState = copyArray(GetBoardState());
        List<Point> startValidMoves = getAllValidMoves(startBoardState, this.GetColour());
        List<Integer> startValidMovesWins = new ArrayList<>();
        List<Integer> startValidMovesSimCounter = new ArrayList<>();

        List<Point> tempValidMoves = new ArrayList<>(startValidMoves);
        Field [][] tempBoardState = copyArray(startBoardState);

        Field myColor = this.GetColour();
        Field enemyColor = ((myColor == Field.WHITE)? Field.BLACK: Field.WHITE);
        Field actualColor = this.GetColour();

        boolean gameSimulationEnded = false;

        Random rand = new Random();

        //wyzerowanie liczników wygranych dla symulacji ruchów
        for (int k = 0; k < startValidMoves.size(); k++)
        {
            startValidMovesWins.add(new Integer(0));
            startValidMovesSimCounter.add(new Integer(0));
        }

        //symulacja

		boolean simulation = true;

		while (simulation) {
			for (int i = 0; i < startValidMoves.size(); i++) {
				tempBoardState[tempValidMoves.get(i).x][tempValidMoves.get(i).y] = actualColor;
				actualColor = (actualColor == myColor) ? enemyColor : myColor;
				tempValidMoves = getAllValidMoves(tempBoardState, actualColor);


				while (!gameSimulationEnded) {
					if (tempValidMoves.size() != 0) {
						int nextMove = rand.nextInt(tempValidMoves.size());
						tempBoardState[tempValidMoves.get(nextMove).x][tempValidMoves.get(nextMove).y] = actualColor;
						actualColor = (actualColor == myColor) ? enemyColor : myColor;
						tempValidMoves = getAllValidMoves(tempBoardState, actualColor);
					} else {
						if (actualColor == enemyColor) {
							startValidMovesWins.set(i, new Integer(startValidMovesWins.get(i) + 1));          
						}
                                                startValidMovesSimCounter.set(i, new Integer(startValidMovesSimCounter.get(i) + 1));
						gameSimulationEnded = true;
					}

					if (GetRemainingTimeMS() < 0) break;
				}
				tempValidMoves = new ArrayList<>(startValidMoves);
				tempBoardState = copyArray(startBoardState);
				gameSimulationEnded = false;
				actualColor = myColor;
				if (GetRemainingTimeMS() < 0) break;
			}
			if (GetRemainingTimeMS() < 0) break;
		}
        
        //wybór najlepszego wyniku

        int bestMove = rand.nextInt(startValidMoves.size());
        float bestPercent = 0;

        for(int j = 0; j < startValidMovesWins.size(); j++)
        {
            if (startValidMovesSimCounter.get(j).intValue() != 0)
            {
            if ( (startValidMovesWins.get(j).intValue() / startValidMovesSimCounter.get(j).intValue()) > bestPercent)
            {
                bestMove = new Integer(j).intValue();
                bestPercent = (float)startValidMovesWins.get(j).intValue() / (float)startValidMovesSimCounter.get(j).intValue();
            }
            }
        }

        return startValidMoves.get(bestMove);
    }
    //###Dodatkowe funkcje

    public WBMBAlgorithm(long movementTime) {
        super(movementTime);
    }

    public Field[][] copyArray(Field tab[][]) {
        Field[][] returnValue = new Field[tab.length][tab[0].length];
        for (int i = 0; i < tab.length; i++) {
            System.arraycopy(tab[i], 0, returnValue[i], 0, tab[i].length);
        }
        return returnValue;
    }

    public boolean isValid(Point p, Field boardState[][], Field colour) {
        if (!(p.x < boardState.length && p.y < boardState[0].length && p.x >= 0 && p.y >= 0)) {
            return false;
        }

        //is it trying to put a stone where none is?
        if (boardState[p.x][p.y] != Field.EMPTY) {
            return false;
        }

        //will all groups have at least one liberty after this move?

        Field[][] copyBoard = copyArray(boardState);

        copyBoard[p.x][p.y] = colour;
        Group[] groups = Group.FindAllGroups(copyBoard);

        for (Group group : groups) {
            if (group.HasLiberty(copyBoard) == false) return false;
        }
        return true;
    }

    public List<Point> getAllValidMoves(Field boardState[][], Field colour) {
        List<Point> theList = new ArrayList<Point>();

        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                if (isValid(new Point(i, j), boardState, colour)) {
                    theList.add(new Point(i, j));
                }
            }
        }

        return theList;
    }
}