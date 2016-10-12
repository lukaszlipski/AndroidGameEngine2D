package com.lucek.androidgameengine2d.graphics;

import android.opengl.Matrix;

/**
 * Created by lukas on 12.10.2016.
 */

public class Window {

    private int m_Width,m_Height;
    private float[] m_ProjectionMatrix;
    private float[] m_ViewMatrix;
    private float[] m_VPMatrix;

    public Window() {
        this.m_ProjectionMatrix = new float[16];
        this.m_ViewMatrix = new float[16];
        this.m_VPMatrix = new float[16];
    }

    public void setWidth(int width){
        this.m_Width = width;
    }

    public void setHeight(int height){
        this.m_Height = height;
    }

    public int getWidth(){
        return this.m_Width;
    }

    public int getHeight(){
        return this.m_Height;
    }

    public void setProjectionMatrix(){
        float ratio = (float) this.m_Width / this.m_Height;
        Matrix.orthoM(m_ProjectionMatrix, 0, -ratio, ratio, -1, 1, 0, 5);
        //Matrix.frustumM(m_ProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public float[] getProjectionMatrix(){
        return this.m_ProjectionMatrix;
    }

    public void setViewMatrix(){
        Matrix.setLookAtM(this.m_ViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
    }

    public float[] getViewMatrix(){
        return this.m_ViewMatrix;
    }

    public void calcVPMatrix(){
        Matrix.multiplyMM(m_VPMatrix,0,this.m_ProjectionMatrix,0,this.m_ViewMatrix,0);
    }

    public float[] getVPMatrix(){
        return m_VPMatrix;
    }

}
