package com.lucek.androidgameengine2d.core.graphics;

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
    private long m_DeltaTime;
    private long m_LastFrameTime;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        m_DeltaTime = 0;
        m_LastFrameTime = System.currentTimeMillis();

        GLES20.glClearColor(63.0f/255,81.0f/255,181.0f/255,1);
        window = new Window();

        main = new Main(window);
        main.Create();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0,0,width,height);
        this.window.setHeight(height);
        this.window.setWidth(width);

        this.window.getCamera().setProjectionMatrix(false,width,height);

        main.OnWindowChange();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        long currentTime = System.currentTimeMillis();
        m_DeltaTime = currentTime - m_LastFrameTime;
        m_LastFrameTime = currentTime;

        main.Update(m_DeltaTime/1000.0f);
    }

}
