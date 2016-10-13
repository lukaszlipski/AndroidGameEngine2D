package com.lucek.androidgameengine2d.game;

import com.lucek.androidgameengine2d.core.entities.Circle;
import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;

/**
 * Created by lukas on 13.10.2016.
 */

public class Pawn {

    private Circle m_Pawn;
    private Circle m_Shadow;

    public Pawn(float[] color, Shader shader, float radius){
        m_Pawn = new Circle(0,0,0,radius,shader,color,32);
        m_Shadow = new Circle(0,0,0,radius,shader, MaterialColors.Black(),32);
    }

    public Pawn setPosition(float x,float y,float z){
        m_Pawn.transform(x,y,z);
        m_Shadow.transform(x+5,y+5,0);
        return this;
    }

    public Pawn setPosition(float[] pos){
        float x = pos[0];
        float y = pos[1];
        float z = pos[2];
        m_Pawn.transform(x,y,0);
        m_Shadow.transform(x+5,y+5,0);
        return this;
    }

    public Pawn draw(Window window){
        m_Shadow.draw(window.getCamera());
        m_Pawn.draw(window.getCamera());
        return this;
    }

}
