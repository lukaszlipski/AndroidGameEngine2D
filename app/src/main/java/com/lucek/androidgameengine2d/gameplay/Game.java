package com.lucek.androidgameengine2d.gameplay;

import android.graphics.Point;

import com.lucek.androidgameengine2d.controllers.AbstractPlayerController;
import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.game.Map;

/**
 * Created by Daniel on 2016-10-15.
 */

public class Game{

    public class GameIsOverException extends Exception{
        public Field winner;

        GameIsOverException(String message, Field winner){
            super(message);
            this.winner=winner;
        }
    }
    public class InvalidMoveException extends Exception{
        InvalidMoveException(String message){
            super(message);
        }
    }

    private AbstractPlayerController player1,player2,currentPlayer;
    private Field[][] boardState;
    private Map graphics;

    public Game(AbstractPlayerController player1, AbstractPlayerController player2, Map graphics){
        this.player1=player1.setGameInstance(this).setColour(Field.BLACK);
        this.player2=player2.setGameInstance(this).setColour(Field.WHITE);
        currentPlayer=player1;

        boardState=new Field[9][9];

        this.graphics=graphics;
    }

    public Field[][] GetBoardState(){
        return boardState.clone();
    }

    public boolean IsMoveValid(Point p){
        //does it fit the board?
        if(!(p.x<boardState.length && p.y<boardState[0].length)){
            return false;
        }
        //is it trying to put a stone where none is?
        if(boardState[p.x][p.y]!=Field.EMPTY) {
            return false;
        }

        //will all groups have at least one liberty after this move?
        Field[][] copyBoard=GetBoardState();
        ApplyMove(copyBoard,p);
        Group[] groups = Group.FindAllGroups(copyBoard);
        for (Group group:groups) {
            if(group.HasLiberty(copyBoard)==false) return false;
        }


        return true;
    }

    //TODO: <<THIS>>
    public Point[] FindAllValidMoves() throws Exception{
        return null;
    }

    public void Update() throws GameIsOverException, InvalidMoveException {
        graphics.clearMap();

        if(!IsGameOver()){
            Point move;
            try {
                 move = currentPlayer.MakeMove();
            }
            catch (AbstractPlayerController.NoMoveMadeException e){
                return; //try to get input from player in the next frame.
            }


            if(IsMoveValid(move)==false) {
                throw new InvalidMoveException("Invalid move!");
            }
            else ApplyMove(boardState,move);

            NextPlayer();
        }
        //game finished
        //currentPlayer <- he lost the game.
        else {
            Field winningColour = (currentPlayer.GetColour()==Field.BLACK)?Field.WHITE:Field.BLACK;
            throw new GameIsOverException("Game is over",winningColour);
        }
    }

    private boolean IsGameOver(){
        for (int y=0;y<boardState.length;y++){
            for(int x=0;x<boardState[0].length;x++){
                if(boardState[x][y]==Field.EMPTY && IsMoveValid(new Point(x,y))){
                    return false;
                }
            }
        }
        return true;
    }

    private void ApplyMove(Field[][] board, Point move){
        board[move.x][move.y]=currentPlayer.GetColour();
        graphics.setField(move.x,move.y,currentPlayer.GetColour());
    }

    private void NextPlayer(){
        currentPlayer=(currentPlayer==player1)?player2:player1;
    }

}
