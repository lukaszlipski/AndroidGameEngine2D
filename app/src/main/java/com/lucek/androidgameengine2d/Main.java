package com.lucek.androidgameengine2d;

import android.opengl.GLES20;
import android.text.InputType;
import android.util.Log;

import com.lucek.androidgameengine2d.entities.BasicEntity;
import com.lucek.androidgameengine2d.entities.Circle;
import com.lucek.androidgameengine2d.entities.Square;
import com.lucek.androidgameengine2d.extra.MaterialColors;
import com.lucek.androidgameengine2d.graphics.Camera;
import com.lucek.androidgameengine2d.graphics.Shader;
import com.lucek.androidgameengine2d.graphics.Window;
import com.lucek.androidgameengine2d.input.TouchInput;

import java.util.ArrayList;

/**
 * Created by lukas on 12.10.2016.
 */

public class Main {

    // object m_Window keeps width and height of screen and Camera
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
        float colors2[] = MaterialColors.Lime();

        // Default Shader
        shr = new Shader(R.raw.vshader,R.raw.fshader);

        // Entities
        sqr = new Square(400,500,0,100,shr,colors2);
        circle = new Circle(0,-200,0,100,shr,colors,32);

    }

    public void Update(){
        // updated every frame

        //circle.transform(this.m_Window.getWidth()/2,this.m_Window.getHeight()/2,2);
        circle.draw(this.m_Window.getCamera());
        sqr.draw(this.m_Window.getCamera());

        circle.transform(TouchInput.getPositionX(),TouchInput.getPositionY(),0);
    }


}
