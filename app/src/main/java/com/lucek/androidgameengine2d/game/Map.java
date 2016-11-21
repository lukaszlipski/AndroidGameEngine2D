package com.lucek.androidgameengine2d.game;


import com.lucek.androidgameengine2d.core.entities.Line;
import com.lucek.androidgameengine2d.core.extra.MaterialColors;
import com.lucek.androidgameengine2d.core.graphics.Shader;
import com.lucek.androidgameengine2d.core.graphics.Window;

/**
 * Created by lukas on 13.10.2016.
 */


public class Map {

    private Field[][] Fields;
    private Pawn m_PawnW,m_PawnB;
    private Pawn m_PawnE;
    private Line m_Line;
    private int m_cuts;
    private float h;
    private float i;
    private float s;
    private Window m_Win;
    private float[] m_colorWhite;
    private float[] m_colorBlack;
    private Shader m_Shader;

    /**
     * Creates map witch cuts x cuts areas
     * @param colorWhite - white color (array of 4 float values between 0 and 1)
     * @param colorBlack - black color (array of 4 float values between 0 and 1)
     * @param cuts - how many areas
     * @param window - handle of window
     * @param shader - basic shader
     */
    public Map(float[] colorWhite, float[] colorBlack, int cuts, Window window, Shader shader){

        m_colorWhite = colorWhite;
        m_colorBlack = colorBlack;
        m_Shader = shader;
        Fields = new Field[cuts][cuts];

        this.m_cuts = cuts;
        this.UpdateMap(window).clearMap();
        m_PawnW = new Pawn(m_colorWhite,m_Shader,10);
        m_PawnB = new Pawn(m_colorBlack,m_Shader,10);
        m_PawnE = new Pawn(MaterialColors.Marked(),m_Shader,10);

        m_Line = new Line(0,0,0,1,1,m_Shader, MaterialColors.Black());
    }

    public Map UpdateMap(Window window){

        this.m_Win = window;

        this.h = Math.min(window.getWidth(),window.getHeight());
        this.i = h/(this.m_cuts+1);
        if(this.h == window.getWidth())
            this.s = (window.getHeight() - (this.i*this.m_cuts))/2;
        else
            this.s = (window.getWidth() - (this.i*this.m_cuts))/2;

        m_PawnW = new Pawn(m_colorWhite,m_Shader,this.i/3);
        m_PawnB = new Pawn(m_colorBlack,m_Shader,this.i/3);
        m_PawnE = new Pawn(MaterialColors.Marked(),m_Shader,this.i/3);

        m_Line = new Line(0,0,0,this.i*(this.m_cuts-1),this.i/3,m_Shader, MaterialColors.Black());


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

    public Field getField(int pos[]){
        int x = pos[0];
        int y = pos[1];
        return this.Fields[y][x];
    }

    public Field getField(int x, int y){
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

        // drawing lines
        for(int i=0;i<m_cuts;i++) {
            if (this.h == m_Win.getWidth())
                m_Line.setPosition(this.i, this.s + (this.i*i), 0);
            else
                m_Line.setPosition(this.s, this.i + (this.i * i), 0);

            m_Line.draw(this.m_Win.getCamera());
        }
        for(int i=0;i<m_cuts;i++) {
            if (this.h == m_Win.getWidth())
                m_Line.setPosition(this.i + (this.i*i), this.s, 0);
            else
                m_Line.setPosition(this.s + (this.i*i), this.i, 0);

            m_Line.setRotation(90);
            m_Line.draw(this.m_Win.getCamera());
        }


        // drawin pawns
        for(int y=0;y<Fields.length;y++) {
            for(int x=0;x<Fields[0].length;x++){
                if(Fields[y][x] != Field.EMPTY){

                    if(Fields[y][x] == Field.WHITE)
                    {
                        m_PawnW.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnW.draw(this.m_Win);
                    }

                    if(Fields[y][x] == Field.WHITE_MARK)
                    {
                        m_PawnW.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnW.drawCheck(this.m_Win);
                    }

                    if(Fields[y][x] == Field.BLACK)
                    {
                        m_PawnB.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnB.draw(this.m_Win);
                    }

                    if(Fields[y][x] == Field.BLACK_MARK)
                    {
                        m_PawnB.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnB.drawCheck(this.m_Win);
                    }

                    if(Fields[y][x] == Field.EMPTY_MARK)
                    {
                        m_PawnE.setPosition(this.calcPositionX(x),this.calcPositionY(y),0);
                        m_PawnE.draw(this.m_Win);
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

    public int convertFromCoordsToColRowX(float x){
        int newX;
        if(this.h == m_Win.getWidth())
            newX = (int)((x - this.i + this.i/3)/this.i);
        else
            newX = (int)((x - this.s + this.i/3)/this.i);
        if(newX >=0 && newX < this.m_cuts)
            return newX;
        else
            return -1;
    }

    public int convertFromCoordsToColRowY(float y){
        int newY;
        if(this.h == m_Win.getWidth())
            newY = (int)((y - this.s + this.i/3)/this.i);
        else
            newY =  (int) ((y - this.i + this.i/3) / this.i);
        if(newY >=0 && newY < this.m_cuts)
            return newY;
        else
            return -1;
    }



}
