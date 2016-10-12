package com.lucek.androidgameengine2d.entities;

/**
 * Created by lukas on 12.10.2016.
 */

public class BasicEntity {

    private int m_PositionX;
    private int m_PositionY;
    private float[] m_Colors;

    public BasicEntity(int x,int y, float[] colors){
        this.m_PositionX = x;
        this.m_PositionY = y;
        this.m_Colors = colors;
    }

    public void setPositionX(int x){
        this.m_PositionX = x;
    }

    public void setPositionY(int y){
        this.m_PositionY = y;
    }

    public int getPositionX(){
        return this.m_PositionX;
    }

    public int getPositionY(){
        return this.m_PositionY;
    }

    public float[] getColors() {
        return this.m_Colors;
    }

}
