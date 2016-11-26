package com.lucek.androidgameengine2d.gameplay;

import android.graphics.Point;
import android.util.Log;

import com.lucek.androidgameengine2d.controllers.AbstractPlayerController;
import com.lucek.androidgameengine2d.controllers.HumanPlayerController;
import com.lucek.androidgameengine2d.core.graphics.Window;
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
    private Window window;
    private Point lastMove = null;

    private final boolean DEBUG = false;

    ///////////////////////////

    public Game(AbstractPlayerController player1, AbstractPlayerController player2, Map graphics, Window window){
        //set up players
        this.player1=player1.setGameInstance(this).setColour(Field.BLACK);
        this.player2=player2.setGameInstance(this).setColour(Field.WHITE);
        currentPlayer=player1;
        HumanPlayerController.playerInputStream.clear();    //bug fix for automatic player input after minimising app

        //set up board
        boardState=new Field[9][9];
        for(int y=0;y<boardState.length;y++){
            for(int x=0;x<boardState[0].length;x++){
                boardState[x][y]=Field.EMPTY;
            }
        }

        //set up display
        this.graphics=graphics;
        //and time checking
        this.window=window;
    }

    public Field[][] GetBoardState(){
        Field[][] returnValue = new Field[boardState.length][boardState[0].length];
        for(int i=0;i<boardState.length;i++){
            System.arraycopy(boardState[i],0,returnValue[i],0,boardState[i].length);
        }
        return returnValue;
    }

    public boolean IsMoveValid(Point p){
        //does it fit the board?
        if(! (p.x<boardState.length && p.y<boardState[0].length && p.x>=0 && p.y>=0) ){
            return false;
        }
        //is it trying to put a stone where none is?
        if(boardState[p.x][p.y]!=Field.EMPTY) {
            return false;
        }

        //will all groups have at least one liberty after this move?
        Field[][] copyBoard=GetBoardState();
        copyBoard[p.x][p.y]=currentPlayer.GetColour();
        Group[] groups = Group.FindAllGroups(copyBoard);
        for (Group group:groups) {
            if(group.HasLiberty(copyBoard)==false) return false;
        }


        return true;
    }

    //TODO: <<THIS>>
    public Point[] FindAllValidMoves() throws Exception{
        throw new UnsupportedOperationException();
    }

    public void Update() throws GameIsOverException, InvalidMoveException {

        if(!IsGameOver()){
            if(DEBUG) {
                Log.d("Update()", "Starting update...");
            }

            Point move;
            float currentTime=GetCurrentTime();
            try {
                 move = currentPlayer.MakeMove(currentTime,currentTime+currentPlayer.getMovementTime(),lastMove);
            }
            catch (AbstractPlayerController.NoMoveMadeException e){
                return; //try to get input from player in the next frame.
            }
            if(DEBUG) {
                Log.d("Update()", "Move selected...");
            }

            if(IsMoveValid(move)==false) {
                if(DEBUG) {
                    Log.d("Update()", "MOVE IS INVALID!");
                }
                throw new InvalidMoveException("Invalid move!");
            }
            else {
                ApplyMove(move);
                lastMove=move;
                if(DEBUG) {
                    Log.d("Update()", "Move applied to the board");
                }
            }

            if(DEBUG) {
                Log.d("Update()", "Selecting next player...");
            }
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
                if(boardState[x][y]==Field.EMPTY){
                    if(IsMoveValid(new Point(x,y))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void ApplyMove(Point move){
        boardState[move.x][move.y]=currentPlayer.GetColour();
        graphics.setField(move.x,move.y,currentPlayer.GetColour());
        if(DEBUG) {
            Log.d("ApplyMove()", "Colour: " + currentPlayer.GetColour().toString());
        }
    }

    private void NextPlayer(){
        if(DEBUG) {
            Log.d("NextPlayer()", "Current player: " + currentPlayer.GetColour().toString());
        }
        if(currentPlayer==player1){
            currentPlayer=player2;
        }
        else {
            currentPlayer=player1;
        }
        if(DEBUG) {
            Log.d("NextPlayer()", "Changed to... " + currentPlayer.GetColour().toString());
        }
    }

    public float GetCurrentTime()
    {
        return window.getCurrentTimeMS();
    }

}
