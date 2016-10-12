package com.lucek.androidgameengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.lucek.androidgameengine2d.Main;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by lukas on 11.10.2016.
 */
public class SimpleRenderer implements GLSurfaceView.Renderer {

    private Main main;
    private Window window;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        GLES20.glClearColor(3.0f/255,169.0f/255,244.0f/255,1);
        window = new Window();

        main = new Main(window);
        main.Create();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0,0,width,height);
        this.window.setHeight(height);
        this.window.setWidth(width);

        //this.window.setProjectionMatrix();
        //this.window.camera.setViewMatrix();
        //this.window.calcVPMatrix();
        this.window.getCamera().setProjectionMatrix(false,width,height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        main.Update();
    }

}
