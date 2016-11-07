package com.lucek.androidgameengine2d;


import com.lucek.androidgameengine2d.controllers.HumanPlayerController;
import com.lucek.androidgameengine2d.controllers.RandomMoveAI;

import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;
import com.lucek.androidgameengine2d.core.input.TouchInput;
import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.game.Map;
import com.lucek.androidgameengine2d.gameplay.Game;

/**
 * Created by lukas on 12.10.2016.
 */

public class Main {

    // object m_Window keeps width and height of screen and Camera
    private Window m_Window;

    // --- BASIC ---
    private Map map;
    private Shader shr;
    // -------------
    private Game gameInstance;


    public Main(Window win) {
        m_Window = win;
    }

    public void Create(){
        // when context is created
        // Default Shader
        shr = new Shader(R.raw.vshader,R.raw.fshader);
        // Map
        map = new Map(MaterialColors.Purple(),MaterialColors.Lime(),9,m_Window,shr);



        // ************** HERE IS YOUR CODE *************
        gameInstance=new Game(new RandomMoveAI(),new HumanPlayerController(),map);
        // **********************************************


    }

    public void OnWindowChange(){
        // when window change orientation
        map.UpdateMap(m_Window);


        // ************** HERE IS YOUR CODE *************


        // **********************************************

    }

    public void Update(){
        // updated every frame

        // ************** HERE IS YOUR CODE *************
        int x = map.convertFromCoordsToColRowX(TouchInput.getPositionX());
        int y = map.convertFromCoordsToColRowY(TouchInput.getPositionY());

//        if(x != -1 && y != -1 && map.getField(x,y) == Field.EMPTY){
//            map.setField(x,y,Field.EMPTY_MARK);
//        }
//
        HumanPlayerController.playerInputStream.add(new android.graphics.Point(x,y));

        // **********************************************

        try {
            gameInstance.Update();

        }catch (Game.GameIsOverException e) {
            android.util.Log.d("Game: Event","Game is over. Winner: "+e.winner.toString());
        }catch (Game.InvalidMoveException e){
            android.util.Log.d("Game: Exception","Player controller returned an invalid move.");
        }


        // draw every pawns
        map.draw();
    }


}
