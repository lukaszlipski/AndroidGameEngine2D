package com.lucek.androidgameengine2d.entities;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.lucek.androidgameengine2d.graphics.Camera;
import com.lucek.androidgameengine2d.graphics.Shader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by lukas on 12.10.2016.
 */

public class Square extends BasicEntity {

    private FloatBuffer vbo;
    private ShortBuffer ibo;

    //static float squareCoords[] = {
    //        -1f,1f,0.0f,
    //        -1f,-1f,0.0f,
    //        1f, -1f, 0.0f,
    //        1f,1f,0.0f };

    static private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

    /**
     * Creates square at origin x,y,z
     * @param x - default position of origin x
     * @param y - default position of origin y
     * @param z - default position of origin z
     * @param height - height of side
     * @param shader - default shader
     * @param colors- float array of 4 elements {r,g,b,a} of values between 0 and 1
     */
    public Square(float  x,float y,float z,float height,Shader shader, float[] colors) {
        super(x,y,z,shader,colors);

        float[] squareCoords = this.calculateCoords(height);

        ByteBuffer bb = ByteBuffer.allocateDirect(squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vbo = bb.asFloatBuffer();
        vbo.put(squareCoords);
        vbo.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(this.drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ibo = dlb.asShortBuffer();
        ibo.put(this.drawOrder);
        ibo.position(0);

    }

    /**
     * Basic function for drawing element on surface
     * @param camera - camera of class Camera for information of view and perspective matrix
     */
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


        GLES20.glDrawElements(GLES20.GL_TRIANGLES, drawOrder.length, GLES20.GL_UNSIGNED_SHORT, ibo);
    }

    private float[] calculateCoords(float height){
        float halfHeight = height/2;
        float[] squareCoords = {
                -halfHeight,halfHeight,0.0f,
                -halfHeight,-halfHeight,0.0f,
                halfHeight, -halfHeight, 0.0f,
                halfHeight,halfHeight,0.0f
        };

        return squareCoords;
    }

}
