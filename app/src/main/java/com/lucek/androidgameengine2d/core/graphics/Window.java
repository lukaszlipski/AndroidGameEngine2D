package com.lucek.androidgameengine2d.core.graphics;

/**
 * Created by lukas on 12.10.2016.
 */

public class Window {

    private int m_Width,m_Height;
    private Camera camera;

    public Window() {

        float pos[] = {0,0,-3};
        float look[] = {0,0,0};
        float up[] = {0,1,0};

        camera = new Camera(pos,look,up);
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

    public Camera getCamera(){
        return this.camera;
    }

}
