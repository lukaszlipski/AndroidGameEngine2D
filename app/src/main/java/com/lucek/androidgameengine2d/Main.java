package com.lucek.androidgameengine2d;

import com.lucek.androidgameengine2d.core.entities.Circle;
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
    private Map map;
    private Shader shr;
    // -----------

    public Main(Window win) {
        m_Window = win;
    }

    public void Create(){
        // when context is created

        // Default Shader

        shr = new Shader(R.raw.vshader,R.raw.fshader);

        // Map
        map = new Map(MaterialColors.Purple(),MaterialColors.Lime(),9,m_Window,shr);

        for(int y=0;y<9;y++) {
            for(int x=0;x<9;x++){
                if(y%2==0)
                    map.setField(x,y, Field.BLACK);
                else
                    map.setField(x,y,Field.WHITE);
            }
        }



    }

    public void OnWindowChange(){
        // when window change orientation
        map.UpdateMap(m_Window);
    }

    public void Update(){
        // updated every frame


        // draw every pawns
        map.draw();
    }


}
