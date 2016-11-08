package com.lucek.androidgameengine2d.game.animation;

import com.lucek.androidgameengine2d.core.entities.Circle;
import com.lucek.androidgameengine2d.core.graphics.Camera;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;

/**
 * Created by lukas on 08.11.2016.
 */

public class BackGroundCircle {

    private Circle m_Circle;
    private float m_growSpeed;
    private Camera m_Camera;
    private float m_GrowTime;
    private Shader m_Shader;
    private float[] m_Colors;


    public BackGroundCircle(float x, float y, float z,float radius,float[] color,float growSpeed,Shader shader,Window win) {
        m_Circle = new Circle(x,y,z,radius,shader,color,32);
        m_growSpeed = growSpeed;
        m_Camera = win.getCamera();
        m_GrowTime = (float)Math.random();
        m_Shader = shader;
        m_Colors = color;
    }

    public void draw(float DeltaTime){
        m_GrowTime += DeltaTime;
        m_Circle.setPosition(m_Circle.getPositionX(),m_Circle.getPositionY(),m_Circle.getPositionZ());
        float scale = (float)(Math.sin(m_GrowTime*m_growSpeed)+2)*m_Circle.getRadius();
        m_Circle.scale(scale,scale,0);
        m_Circle.draw(m_Camera);
    }

    public void update(Window win,float posX,float posY){

        m_Camera = win.getCamera();

        m_Circle = new Circle(posX,posY,0,(float)(win.getWidth()/70),m_Shader,m_Colors,32);
    }

    private BackGroundCircle(){}

}
