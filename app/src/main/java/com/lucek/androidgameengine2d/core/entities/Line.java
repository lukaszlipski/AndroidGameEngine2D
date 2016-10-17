package com.lucek.androidgameengine2d.core.entities;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.lucek.androidgameengine2d.core.graphics.Camera;
import com.lucek.androidgameengine2d.core.graphics.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by lukas on 15.10.2016.
 */

public class Line extends BasicEntity{

    private FloatBuffer vbo;

    private Shader m_Shader;
    private float m_Width;

    private float lineCoords[];

    public Line(float x,float y,float z,float length,float width,Shader shader,float[] colors){
        super(x,y,z,shader,colors);

        lineCoords = this.calculateCoords(length);
        m_Width = width;

        ByteBuffer bb = ByteBuffer.allocateDirect(lineCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vbo = bb.asFloatBuffer();
        vbo.put(lineCoords);
        vbo.position(0);



    }

    public void draw(Camera camera){

        GLES20.glUseProgram(this.getShader().GetProgramID());
        int attrib = GLES20.glGetAttribLocation(this.getShader().GetProgramID(),"vPosition");
        GLES20.glEnableVertexAttribArray(attrib);

        GLES20.glVertexAttribPointer(attrib,3,GLES20.GL_FLOAT,false,3*4,vbo);

        int color = GLES20.glGetUniformLocation(this.getShader().GetProgramID(),"vColor");
        GLES20.glUniform4fv(color,1,this.getColors(),0);

        float mvpMatrix[] = new float[16];
        Matrix.multiplyMM(mvpMatrix,0,camera.getVPMatrix(),0,this.getModelMatrix(),0);

        int handle_mvpM = GLES20.glGetUniformLocation(this.getShader().GetProgramID(),"u_MVPMatrix");
        GLES20.glUniformMatrix4fv(handle_mvpM, 1, false, mvpMatrix, 0);

        GLES20.glLineWidth(m_Width);
        GLES20.glDrawArrays(GLES20.GL_LINES,0,2);
    }

    private float[] calculateCoords(float length) {
        float[] squareCoords = {
            0.0f,0.0f,0.0f,
            length,0.0f,0.0f
        };
        return squareCoords;
    }
}
