package com.lucek.androidgameengine2d.core.entities;

import android.opengl.Matrix;

import com.lucek.androidgameengine2d.core.graphics.Shader;

/**
 * Created by lukas on 12.10.2016.
 */

public class BasicEntity {

    private float m_PositionX;
    private float m_PositionY;
    private float m_PositionZ;
    private Shader m_Shader;
    private float[] m_Colors;
    private float[] m_ModelMatrix;

    public BasicEntity(float x, float y, float z, Shader shader, float[] colors){
        this.m_PositionX = x;
        this.m_PositionY = y;
        this.m_PositionZ = z;
        this.m_Shader = shader;
        this.m_Colors = colors;
        m_ModelMatrix = new float[16];
        Matrix.setIdentityM(m_ModelMatrix,0);

        float position[] = {m_PositionX,m_PositionY,m_PositionZ,0};

        Matrix.translateM(m_ModelMatrix,0,m_ModelMatrix,0,m_PositionX,m_PositionY,m_PositionZ);
    }

    public float getPositionX(){
        return this.m_PositionX;
    }

    public float getPositionY(){
        return this.m_PositionY;
    }

    public float getPositionZ(){
        return this.m_PositionZ;
    }

    public float[] getPosition(){
        float pos[] = {this.m_PositionX, this.m_PositionY, this.m_PositionZ};
        return pos;
    }

    public void setShader(Shader shader){
        this.m_Shader = shader;
    }

    public Shader getShader(){
        return this.m_Shader;
    }

    public float[] getColors() {
        return this.m_Colors;
    }

    public float[] getModelMatrix(){
        return this.m_ModelMatrix;
    }

    public void setPosition(float x,float y, float z){
        Matrix.setIdentityM(m_ModelMatrix,0);
        this.m_PositionX = x;
        this.m_PositionY = y;
        this.m_PositionZ = z;
        Matrix.translateM(m_ModelMatrix,0,m_ModelMatrix,0,m_PositionX,m_PositionY,m_PositionZ);
    }

    public void setRotation(float angle){
        Matrix.rotateM(m_ModelMatrix,0,angle,0,0,1);
    }

    public void scale(float x,float y,float z)
    {
        Matrix.scaleM(m_ModelMatrix,0,x,y,z);
    }

}
