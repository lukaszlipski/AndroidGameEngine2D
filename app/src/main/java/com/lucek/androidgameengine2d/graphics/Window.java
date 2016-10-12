package com.lucek.androidgameengine2d.graphics;

import android.opengl.Matrix;

/**
 * Created by lukas on 12.10.2016.
 */

public class Window {

    private int m_Width,m_Height;
    //private float[] m_ProjectionMatrix;
    //private float[] m_ViewMatrix;
    //private float[] m_VPMatrix;
    private Camera camera;

    public Window() {
        //this.m_ProjectionMatrix = new float[16];
        //this.m_ViewMatrix = new float[16];
        //this.m_VPMatrix = new float[16];

        float pos[] = {0,0,-3};
        float look[] = {0,0,0};
        float up[] = {0,1,0};

        camera = new Camera(pos,look,up);
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

//    public void setProjectionMatrix(){
//
//        // TODO: !!!!!!!! Check why x is inversed !!!!!!!
//
//        Matrix.orthoM(m_ProjectionMatrix, 0,0, -this.m_Width, this.m_Height, 0, 0, 10);
//        //Matrix.frustumM(m_ProjectionMatrix, 0, 0, -this.m_Width, 0, this.m_Height, 3, 7);
//    }

    //public float[] getProjectionMatrix(){
    //    return this.m_ProjectionMatrix;
    //}


//    public void calcVPMatrix(){
//        Matrix.multiplyMM(m_VPMatrix,0,this.m_ProjectionMatrix,0,this.camera.getViewMatrix(),0);
//    }

    //public float[] getVPMatrix(){
    //    return m_VPMatrix;
    //}

    public Camera getCamera(){
        return this.camera;
    }

}
