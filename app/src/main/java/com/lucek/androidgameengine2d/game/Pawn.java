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
    private Circle m_Marked;
    private float radius;

    public Pawn(float[] color, Shader shader, float radius){
        this.radius = radius;
        m_Pawn = new Circle(0,0,0,radius,shader,color,32);
        m_Shadow = new Circle(0,0,0,radius,shader, MaterialColors.Black(),32);
        m_Marked = new Circle(0,0,0,radius,shader, MaterialColors.Marked(),32);
    }

    public Pawn setPosition(float x,float y,float z){
        m_Pawn.transform(x,y,z);
        m_Marked.transform(x,y,z);
        m_Shadow.transform(x+radius/6,y+radius/6,0);
        return this;
    }

    public Pawn setPosition(float[] pos){
        float x = pos[0];
        float y = pos[1];
        m_Pawn.transform(x,y,0);
        m_Marked.transform(x,y,0);
        m_Shadow.transform(x+radius/6,y+radius/6,0);
        return this;
    }

    public Pawn draw(Window window){
        m_Shadow.draw(window.getCamera());
        m_Pawn.draw(window.getCamera());
        return this;
    }

    public Pawn drawCheck(Window window){
        m_Shadow.scale(1.3f,1.3f,1.3f);
        m_Pawn.scale(1.3f,1.3f,1.3f);
        m_Shadow.draw(window.getCamera());
        m_Pawn.draw(window.getCamera());
        m_Marked.scale(0.8f,0.8f,0.8f);
        m_Marked.draw(window.getCamera());
        return this;
    }

}
