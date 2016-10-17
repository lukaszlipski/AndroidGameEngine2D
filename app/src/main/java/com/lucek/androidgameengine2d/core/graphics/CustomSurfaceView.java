package com.lucek.androidgameengine2d.core.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.lucek.androidgameengine2d.core.input.TouchInput;

/**
 * Created by lukas on 11.10.2016.
 */
public class CustomSurfaceView extends GLSurfaceView {

    private SimpleRenderer m_Renderer;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        TouchInput.setPositionX(e.getX());
        TouchInput.setPositionY(e.getY());

        return true;
    }

    public CustomSurfaceView(Context ctx) {
        super(ctx);
        Shader.ctx = ctx;
        setEGLContextClientVersion(2);
        m_Renderer = new SimpleRenderer();

        setRenderer(m_Renderer);

    }


}
