package com.lucek.androidgameengine2d;


import android.util.Log;

import com.lucek.androidgameengine2d.controllers.HumanPlayerController;
import com.lucek.androidgameengine2d.controllers.RandomMoveAI;

import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;
import com.lucek.androidgameengine2d.core.input.TouchInput;
import com.lucek.androidgameengine2d.eventBus.Bus;
import com.lucek.androidgameengine2d.eventBus.events.GameOverEvent;
import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.game.Map;
import com.lucek.androidgameengine2d.gameplay.Game;

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

    public Main(Window win,Shader shr) {

        m_Window = win;
        this.shr = shr;

    }

    public void Create(){
        TouchInput.clearPositions();

        map = new Map(MaterialColors.White(),MaterialColors.Black(),9,m_Window,shr);

        m_FirstWindowOpen = true;
        gameIsInProgress = true;
        gameInstance = new Game(new RandomMoveAI(1000), new HumanPlayerController(1000), map,m_Window);
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
