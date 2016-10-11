package com.lucek.androidgameengine2d.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by lukas on 11.10.2016.
 */
public class CustomSurfaceView extends GLSurfaceView {

    private SimpleRenderer m_Renderer;

    public CustomSurfaceView(Context ctx) {
        super(ctx);

        setEGLContextClientVersion(2);
        m_Renderer = new SimpleRenderer();

        setRenderer(m_Renderer);

    }

}
