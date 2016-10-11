package com.lucek.androidgameengine2d.graphics;

/**
 * Created by lukas on 12.10.2016.
 */

public class Window {

    public int m_Width,m_Height;

    public Window() {

    }

    public void setWidth(int width){
        this.m_Width = width;
    }

    public void setHeight(int height){
        this.m_Height = height;
    }

    public int getWidth(){
        return this.m_Width;
    }

    public int getHeight(){
        return this.m_Height;
    }

}
