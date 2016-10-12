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

public class Circle extends BasicEntity {

    private FloatBuffer vbo;
    private ShortBuffer ibo;

    static private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

    public Circle(int x,int y,float[] colors,int cuts){
        super(x,y,colors);

        float[] circleCoors = this.calculateCoords(cuts);
        this.drawOrder = this.calculateDrawOrder(cuts);
        // TODO: make it

        ByteBuffer bb = ByteBuffer.allocateDirect(circleCoors.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vbo = bb.asFloatBuffer();
        vbo.put(circleCoors);
        vbo.position(0);

        ByteBuffer dlb = ByteBuffer.allocateDirect(drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ibo = dlb.asShortBuffer();
        ibo.put(drawOrder);
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

    private float[] calculateCoords(int cuts){

        float[] circleCoords = new float[cuts*3+6];

        circleCoords[0] = 0;
        circleCoords[1] = 0;
        circleCoords[2] = 0;

        double jump = 2*3.14/cuts;
        double j =0;
        for(int i=3;i<=cuts*3;i+=3,j+=jump){
            circleCoords[i] = (float)Math.cos(j);
            circleCoords[i+1] = (float)Math.sin(j);
            circleCoords[i+2] = 0;
        }

        return circleCoords;
    }

    private short[] calculateDrawOrder(int cuts) {
        short[] drawOrder = new short[cuts*3];

        int j=1;
        for(int i=0;i<cuts*3;i+=3,j+=1){
            if(i != cuts*3-3) {
                drawOrder[i] = 0;
                drawOrder[i + 1] = (short) (j);
                drawOrder[i + 2] = (short) (j + 1);
            }else{
                drawOrder[i] = 0;
                drawOrder[i + 1] = (short) (j);
                drawOrder[i + 2] = (short) (1);
            }
        }

        return drawOrder;
    }

}
