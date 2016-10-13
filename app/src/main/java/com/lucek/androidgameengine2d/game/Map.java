package com.lucek.androidgameengine2d.game;

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

    /**
     * Map with cuts x cuts fields eg (9x9 for NoGo)
     * @param cuts - how many cuts for map
     * @param window - main window of game
     */
    public Map(float[] colorWhite, float[] colorBlack, int cuts, Window window,Shader shader){

        Fields = new Field[cuts][cuts];

        this.UpdateMap(window).clearMap();
        m_PawnW = new Pawn(colorWhite,shader,50);
        m_PawnB = new Pawn(colorBlack,shader,50);
        this.m_cuts = cuts;

    }

    public Map UpdateMap(Window window){
        this.m_Win = window;

        this.h = Math.min(window.getWidth(),window.getHeight());
        this.i = h/(this.m_cuts+2);
        if(this.h == window.getWidth())
            this.s = (window.getHeight() - (this.i*this.m_cuts))/2;
        else
            this.s = (window.getWidth() - (this.i*this.m_cuts))/2;

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

//        for(int y=0;y<Fields.length;y++) {
//            for(int x=0;x<Fields[0].length;x++){
//                if(Fields[y][x] != Field.EMPTY){
//
//
//
//                }
//            }
//        }

        m_PawnB.setPosition(this.m_Win.getWidth()/2,this.m_Win.getHeight()/2,2);
        m_PawnB.draw(this.m_Win);

        return this;
    }


}
