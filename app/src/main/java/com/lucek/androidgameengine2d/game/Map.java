package com.lucek.androidgameengine2d.game;

import android.util.DisplayMetrics;

import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;

/**
 * Created by lukas on 13.10.2016.
 */


public class Map {

    private Field[][] Fields;
    private Pawn m_PawnW,m_PawnB;
    private int m_cuts;
    private float h;
    private float i;
    private float s;
    private Window m_Win;
    private float[] m_colorWhite;
    private float[] m_colorBlack;
    private Shader m_Shader;

    /**
     *
     * @param colorWhite
     * @param colorBlack
     * @param cuts
     * @param window
     * @param shader
     */
    public Map(float[] colorWhite, float[] colorBlack, int cuts, Window window,Shader shader){

        m_colorWhite = colorWhite;
        m_colorBlack = colorBlack;
        m_Shader = shader;
        Fields = new Field[cuts][cuts];

        this.m_cuts = cuts;
        //this.UpdateMap(window).clearMap();
        m_PawnW = new Pawn(m_colorWhite,m_Shader,10);
        m_PawnB = new Pawn(m_colorBlack,m_Shader,10);
    }

    public Map UpdateMap(Window window){

        this.m_Win = window;

        this.h = Math.min(window.getWidth(),window.getHeight());
        this.i = h/(this.m_cuts+2);
        if(this.h == window.getWidth())
            this.s = (window.getHeight() - (this.i*this.m_cuts))/2;
        else
            this.s = (window.getWidth() - (this.i*this.m_cuts))/2;

        m_PawnW = new Pawn(m_colorWhite,m_Shader,this.i/2);
        m_PawnB = new Pawn(m_colorBlack,m_Shader,this.i/2);
        return this;
    }

    public Map setField(int pos[], Field field){
        int x = pos[0];
        int y = pos[1];
        this.Fields[y][x] = field;
        return this;
    }

    public Map setField(int x, int y, Field field){
        this.Fields[y][x] = field;
        return this;
    }

    public Field checkField(int pos[], Field field){
        int x = pos[0];
        int y = pos[1];
        return this.Fields[y][x];
    }

    public Field checkField(int x, int y, Field field){
        return this.Fields[y][x];
    }


    public Map clearMap(){
        for(int y=0;y<Fields.length;y++) {
            for(int x=0;x<Fields[0].length;x++){
                Fields[y][x] = Field.EMPTY;
            }
        }
        return this;
    }

    public Map draw(){

        for(int y=0;y<Fields.length;y++) {
            for(int x=0;x<Fields[0].length;x++){
                if(Fields[y][x] != Field.EMPTY){

                    if(Fields[y][x] == Field.WHITE)
                    {
                        m_PawnW.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnW.draw(this.m_Win);
                    }

                    if(Fields[y][x] == Field.BLACK)
                    {
                        m_PawnB.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnB.draw(this.m_Win);
                    }

                }
            }
        }

        return this;
    }

    private float calcPositionX(int column){
        if(this.h == m_Win.getWidth())
            return this.i + (column * this.i);
        else
            return this.s + (column * this.i);
    }

    private float calcPositionY(int row){
        if(this.h == m_Win.getWidth())
            return this.s + (row * this.i);
        else
            return this.i + (row * this.i);
    }


}
