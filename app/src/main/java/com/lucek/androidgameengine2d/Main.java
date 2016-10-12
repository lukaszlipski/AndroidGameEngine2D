package com.lucek.androidgameengine2d;

import android.opengl.GLES20;

import com.lucek.androidgameengine2d.entities.BasicEntity;
import com.lucek.androidgameengine2d.entities.Circle;
import com.lucek.androidgameengine2d.entities.Square;
import com.lucek.androidgameengine2d.extra.MaterialColors;
import com.lucek.androidgameengine2d.graphics.Camera;
import com.lucek.androidgameengine2d.graphics.Shader;
import com.lucek.androidgameengine2d.graphics.Window;

import java.util.ArrayList;

/**
 * Created by lukas on 12.10.2016.
 */

public class Main {

    // object m_Window keeps width and height of screen
    private Window m_Window;

    // --- TEST ---
    private Square sqr;
    private Circle circle;
    private Shader shr;
    // -----------

    public Main(Window win) {
        m_Window = win;
    }

    public void Create(){
        // when context is created

        float colors[] = MaterialColors.Purple();

        //sqr = new Square(2,2,colors);
        circle = new Circle(0,-200,0, colors,32,100);
        shr = new Shader(R.raw.vshader,R.raw.fshader);

    }

    public void Update(){
        // updated every frame

        //sqr.draw(shr);
        circle.draw(shr,this.m_Window.getCamera());
        circle.transform(this.m_Window.getWidth()/2,this.m_Window.getHeight()/2,2);

    }


}
