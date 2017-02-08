package com.lucek.androidgameengine2d;


import android.util.Log;

import com.lucek.androidgameengine2d.controllers.AbstractPlayerController;
import com.lucek.androidgameengine2d.controllers.HumanPlayerController;
import com.lucek.androidgameengine2d.controllers.RandomMoveAI;

import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;
import com.lucek.androidgameengine2d.core.input.TouchInput;
import com.lucek.androidgameengine2d.eventBus.Bus;
import com.lucek.androidgameengine2d.eventBus.events.GameOverEvent;
import com.lucek.androidgameengine2d.game.Map;
import com.lucek.androidgameengine2d.game.PlayerTypes;
import com.lucek.androidgameengine2d.gameplay.Game;
import com.lucek.androidgameengine2d.storage.PreferencesManager;

import static com.lucek.androidgameengine2d.game.PlayerTypes.Types.HUMAN;
import static com.lucek.androidgameengine2d.game.PlayerTypes.Types.SIMPLE_AI;

/**
 * Created by lukas on 12.10.2016.
 */

public class Main {

    private Window m_Window;

    private boolean m_FirstWindowOpen;
    private boolean gameIsInProgress;
    private Map map;
    private Shader shr;

    private Game gameInstance;

    private AbstractPlayerController player1, player2;

    public Main(Window win,Shader shr) {

        m_Window = win;
        this.shr = shr;
        getPlayerController();

    }

    private void getPlayerController() {
        PlayerTypes.Types temp = PlayerTypes.getTypeByName(PreferencesManager.NoGo.getPlayer1()) == null ? SIMPLE_AI : PlayerTypes.getTypeByName(PreferencesManager.NoGo.getPlayer1());
        assert temp != null;
        switch (temp){
            case HUMAN:
                player1 = new HumanPlayerController(PreferencesManager.NoGo.getTimeForTurn());
                break;
            case SIMPLE_AI:
            default:
                player1 = new RandomMoveAI(PreferencesManager.NoGo.getTimeForTurn());
        }

        temp = PlayerTypes.getTypeByName(PreferencesManager.NoGo.getPlayer2()) == null ? SIMPLE_AI : PlayerTypes.getTypeByName(PreferencesManager.NoGo.getPlayer2());
        assert temp != null;
        switch (temp){
            case HUMAN:
                player2 = new HumanPlayerController(PreferencesManager.NoGo.getTimeForTurn());
                break;
            case SIMPLE_AI:
            default:
                player2 = new RandomMoveAI(PreferencesManager.NoGo.getTimeForTurn());
        }
    }

    public void Create(){
        TouchInput.clearPositions();

        map = new Map(MaterialColors.White(),MaterialColors.Black(),9,m_Window,shr);

        m_FirstWindowOpen = true;
        gameIsInProgress = true;
        gameInstance = new Game(player1, player2, map,m_Window);
    }

    public void OnWindowChange(){
        map.UpdateMap(m_Window);
    }

    // DeltaTime in ms
    public void Update(float DeltaTime){
        int x = map.convertFromCoordsToColRowX(TouchInput.getPositionX());
        int y = map.convertFromCoordsToColRowY(TouchInput.getPositionY());
//        if(x != -1 && y != -1 && map.getField(x,y) == Field.EMPTY){
//            map.setField(x,y,Field.EMPTY_MARK);
//        }
//
        HumanPlayerController.playerInputStream.add(new android.graphics.Point(x,y));

        try {
            if (gameIsInProgress) {
                gameInstance.Update();
            }
        }catch (Game.GameIsOverException e) {
            if (gameIsInProgress) {
                Bus.getInstance().post(new GameOverEvent(e.winner));
                Log.d("Game: Event", "Game is over. Winner: " + e.winner.toString());
            }
            gameIsInProgress = false;
        }catch (Game.InvalidMoveException e){
            Log.d("Game: Exception","Player controller returned an invalid move.");
        }

        // draw every pawns
        if(m_FirstWindowOpen) {
            m_FirstWindowOpen = false;
        } else {
            map.draw();
        }
    }
}
