package com.lucek.androidgameengine2d;

import android.text.InputType;

import com.lucek.androidgameengine2d.core.entities.Circle;
import com.lucek.androidgameengine2d.core.entities.Line;
import com.lucek.androidgameengine2d.core.entities.Square;
import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;
import com.lucek.androidgameengine2d.core.input.TouchInput;
import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.game.Map;

/**
 * Created by lukas on 12.10.2016.
 */

public class Main {

    // object m_Window keeps width and height of screen and Camera
    private Window m_Window;

    // --- BASIC ---
    private Boolean m_FirstWindowOpen;
    private Map map;
    private Shader shr;
    // -------------


    public Main(Window win) {
        m_Window = win;
    }

    public void Create(){
        // when context is created
        // Default Shader
        TouchInput.clearPositions();
        shr = new Shader(R.raw.vshader,R.raw.fshader);
        // Map
        map = new Map(MaterialColors.Purple(),MaterialColors.Lime(),9,m_Window,shr);
        m_FirstWindowOpen = true;
        // ************** HERE IS YOUR CODE *************


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

        if(x != -1 && y != -1 && map.getField(x,y) == Field.EMPTY){
            map.setField(x,y,Field.EMPTY_MARK);
        }

        // **********************************************

        // draw every pawns
        if(m_FirstWindowOpen) {

            m_FirstWindowOpen = false;
        } else {
            map.draw();
        }
    }


}
