package com.lucek.androidgameengine2d.core.graphics;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.lucek.androidgameengine2d.Main;
import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.game.animation.BackGroundCircle;

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
    private Shader m_Shader;

    // TEST
    private BackGroundCircle[] m_BgCircles;
    // TEST


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        m_Shader = new Shader(R.raw.vshader,R.raw.fshader);

        m_DeltaTime = 0;
        m_LastFrameTime = System.currentTimeMillis();

        GLES20.glClearColor(63.0f/255,81.0f/255,181.0f/255,1);
        window = new Window();

//        m_BgCircles = new BackGroundCircle[4];

//        m_BgCircles[0] = new BackGroundCircle(window.getHeight()/10, window.getHeight()/10, 1,10, MaterialColors.DarkBlue(),(float)Math.random()/2,m_Shader,window);
//        m_BgCircles[1] = new BackGroundCircle(window.getHeight()/2, window.getHeight()/2, 1,10, MaterialColors.Blue(),(float)Math.random()/2,m_Shader,window);
//        m_BgCircles[2] = new BackGroundCircle(window.getHeight()/3, window.getHeight()/3, 1,10, MaterialColors.DarkBlue(),(float)Math.random()/2,m_Shader,window);
//        m_BgCircles[3] = new BackGroundCircle(window.getHeight()/3, window.getHeight()/3, 1,10, MaterialColors.Blue(),(float)Math.random()/2,m_Shader,window);


        main = new Main(window,m_Shader);
        main.Create();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0,0,width,height);
        this.window.setHeight(height);
        this.window.setWidth(width);

        this.window.getCamera().setProjectionMatrix(false,width,height);

//        m_BgCircles[0].update(window,window.getWidth()/3,window.getWidth()/1);
//        m_BgCircles[1].update(window,window.getWidth()/1,window.getWidth()/1);
//        m_BgCircles[2].update(window,window.getWidth()/2,window.getWidth()/2);
//        m_BgCircles[3].update(window,window.getWidth()/3,window.getWidth()/3);

        main.OnWindowChange();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        long currentTime = System.currentTimeMillis();
        m_DeltaTime = currentTime - m_LastFrameTime;
        m_LastFrameTime = currentTime;

//        for(int i=0;i<m_BgCircles.length;i++) {
//            m_BgCircles[i].draw(m_DeltaTime / 1000.0f);
//        }

        main.Update(m_DeltaTime/1000.0f);
    }

}
