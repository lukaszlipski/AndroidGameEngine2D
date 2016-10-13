package com.lucek.androidgameengine2d.input;

/**
 * Created by lukas on 13.10.2016.
 */

public class TouchInput {

    static private float m_PositionX;
    static private float m_PositionY;

    static public void setPositionX(float x){
        m_PositionX = x;
    }

    static public void setPositionY(float y){
        m_PositionY = y;
    }

    static public float getPositionX(){
        return m_PositionX;
    }

    static public float getPositionY(){
        return m_PositionY;
    }

}
