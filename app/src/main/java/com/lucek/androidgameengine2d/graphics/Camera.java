package com.lucek.androidgameengine2d.graphics;

import android.opengl.Matrix;

/**
 * Created by lukas on 12.10.2016.
 */

public class Camera {

    private float m_PositionX;
    private float m_PositionY;
    private float m_PositionZ;

    private float m_LookAtPointX;
    private float m_LookAtPointY;
    private float m_LookAtPointZ;

    private float m_UpX;
    private float m_UpY;
    private float m_UpZ;

    private float[] m_ViewMatrix;
    private float[] m_ProjectionMatrix;
    private float[] m_VPMatrix;

    /**
     * Camera for controlling view of world
     * @param pos - array of 3 float elements which defines where camera is at
     * @param look - array of 3 float elements which defines where camera look at
     * @param up - array of 3 float elements which defines where is up for camera (standard is [0,1,0])
     */
    Camera(float[] pos, float[] look, float[] up){
        this.m_ProjectionMatrix = new float[16];
        Matrix.setIdentityM(this.m_ProjectionMatrix,0);
        this.m_ViewMatrix = new float[16];
        this.m_VPMatrix = new float[16];

        this.setPosition(pos,look,up);
    }

    private void setViewMatrix(){
        Matrix.setLookAtM(
                this.m_ViewMatrix, 0, this.m_PositionX, this.m_PositionY,
                this.m_PositionZ, this.m_LookAtPointX, this.m_LookAtPointY, this.m_LookAtPointZ,
                this.m_UpX, this.m_UpY, this.m_UpZ);
    }

    /**
     * Set projection matrix for specified width and height
     * @param perspective TRUE = perspective, FALSE = orthographic
     * @param width - width of surface
     * @param height - height of surface
     */
    public void setProjectionMatrix(Boolean perspective,float width,float height){
        // TODO: !!!!!!!! Check why x is inversed !!!!!!!
        if(perspective)
            Matrix.frustumM(this.m_ProjectionMatrix, 0, 0, -width, 0, height, 0, 10);
        else
            Matrix.orthoM(this.m_ProjectionMatrix, 0,0, -width, height, 0, 0, 10);

        this.updateVPMatrix();

    }

    public float[] getViewMatrix(){
        return this.m_ViewMatrix;
    }

    public float[] getM_ProjectionMatrix(){
        return this.m_ProjectionMatrix;
    }

    public void setPosition(float[] pos, float[] look, float[] up){
        this.m_PositionX = pos[0];
        this.m_PositionY = pos[1];
        this.m_PositionZ = pos[2];

        this.m_LookAtPointX = look[0];
        this.m_LookAtPointY = look[1];
        this.m_LookAtPointZ = look[2];

        this.m_UpX = up[0];
        this.m_UpY = up[1];
        this.m_UpZ = up[2];

        this.setViewMatrix();
        this.updateVPMatrix();
    }

    public float[] getVPMatrix(){
        return this.m_VPMatrix;
    }

    private void updateVPMatrix(){
        //Matrix.setIdentityM(this.m_VPMatrix,0);
        Matrix.multiplyMM(this.m_VPMatrix,0,this.m_ProjectionMatrix,0,this.m_ViewMatrix,0);
    }

}
