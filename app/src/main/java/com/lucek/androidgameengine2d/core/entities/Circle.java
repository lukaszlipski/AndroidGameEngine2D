package com.lucek.androidgameengine2d.core.entities;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.lucek.androidgameengine2d.core.graphics.Camera;
import com.lucek.androidgameengine2d.core.graphics.Shader;

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
    private float m_Radius;

    static private short drawOrder[] = { 0, 1, 2, 0, 2, 3 };

    /**
     * Creates circle at origin x,y,z
     * @param x - default position of origin x
     * @param y - default position of origin y
     * @param z - default position of origin z
     * @param radius - default radius of the circle
     * @param shader - default shader
     * @param colors - float array of 4 elements {r,g,b,a} of values between 0 and 1
     * @param cuts - for how many triangles it should be cut
     */
    public Circle(float x,float y,float z,float radius,Shader shader,float[] colors,int cuts){
        super(x,y,z,shader,colors);

        m_Radius = radius;

        float[] circleCoors = this.calculateCoords(cuts,radius);
        this.drawOrder = this.calculateDrawOrder(cuts);

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

    public float getRadius(){
        return m_Radius;
    }

    private float[] calculateCoords(int cuts,float radius){

        float[] circleCoords = new float[cuts*3+6];

        circleCoords[0] = 0;
        circleCoords[1] = 0;
        circleCoords[2] = 0;

        double jump = 2*3.14/cuts;
        double j =0;
        for(int i=3;i<=cuts*3;i+=3,j+=jump){
            circleCoords[i] = (float)Math.cos(j)*radius;
            circleCoords[i+1] = (float)Math.sin(j)*radius;
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
