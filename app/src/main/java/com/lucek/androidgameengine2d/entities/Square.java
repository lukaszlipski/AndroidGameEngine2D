package com.lucek.androidgameengine2d.entities;

import android.opengl.GLES20;

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

    static float squareCoords[] = {
            -0.5f,0.5f,0.0f,
            -0.5f,-0.5f,0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f,0.5f,0.0f };

    static private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };


    public Square(int x,int y,float[] colors) {
        super(x,y,colors);

        ByteBuffer bb = ByteBuffer.allocateDirect(this.squareCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vbo = bb.asFloatBuffer();
        vbo.put(this.squareCoords);
        vbo.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(this.drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ibo = dlb.asShortBuffer();
        ibo.put(this.drawOrder);
        ibo.position(0);

    }

    public void draw(Shader shader){

        GLES20.glUseProgram(shader.GetProgramID());
        int attrib = GLES20.glGetAttribLocation(shader.GetProgramID(),"vPosition");
        GLES20.glEnableVertexAttribArray(attrib);

        GLES20.glVertexAttribPointer(attrib,3,GLES20.GL_FLOAT,false,3*4,vbo);

        int color = GLES20.glGetUniformLocation(shader.GetProgramID(),"vColor");
        GLES20.glUniform4fv(color,1,this.getColors(),0);

        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, ibo);
    }

}
