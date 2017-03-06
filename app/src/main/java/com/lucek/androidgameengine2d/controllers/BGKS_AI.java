package com.lucek.androidgameengine2d.controllers;
import android.graphics.Point;
import android.util.Log;


import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.gameplay.Game;
import com.lucek.androidgameengine2d.gameplay.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;







/**
 * Created by Shineko on 27/02/17.
 */

public class BGKS_AI extends AbstractPlayerController {

    Field [][] plansza;

    List<Point> mozliweRuchy;
    int liczbaMozliwychRuchow;
    int[] liczbaWygranychRuchu;
    List<Integer> najlepszeRuchy;
    List<Point> pustePola;

    Field naszKolor, kolorPrzeciwnika;

    boolean corners;
    Point cornerA = new Point (0,0);
    Point cornerB = new Point (0,8);
    Point cornerC = new Point (8,8);
    Point cornerD = new Point (8,0);


    public BGKS_AI(long movementTime)
    {

        super(movementTime);

        pustePola = new ArrayList<>();
        mozliweRuchy = new ArrayList<>();
        najlepszeRuchy = new ArrayList<>();

        for( int i = 0; i < 9; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                pustePola.add(new Point(i,j));
            }
        }

        corners = true;

    }

    @Override
    public Point MakeMove(Point lastOpponentsMove) throws NoMoveMadeException
    {
        plansza = GetBoardState();

        if(GetColour() == Field.WHITE)
        {
            kolorPrzeciwnika = Field.BLACK;
            naszKolor = Field.WHITE;
        }
        else
        {
            kolorPrzeciwnika = Field.WHITE;
            naszKolor = Field.BLACK;
        }

        if(lastOpponentsMove != null)
        {
            pustePola.remove(lastOpponentsMove);
        }

        mozliweRuchy.clear();

        for(Point pole : pustePola)
        {
            if(IsMoveValid(pole))
            {
                mozliweRuchy.add(pole);
            }
        }

        if(corners)
        {
            if (mozliweRuchy.contains(cornerA))
            {
                pustePola.remove(cornerA);
                return cornerA;
            }
            else if (mozliweRuchy.contains(cornerB))
            {
                pustePola.remove(cornerB);
                return cornerB;
            }
            else if (mozliweRuchy.contains(cornerC))
            {
                pustePola.remove(cornerC);
                return cornerC;
            }
            else if (mozliweRuchy.contains(cornerD))
            {
                corners = false;
                pustePola.remove(cornerD);
                return cornerD;
            }
        }




        liczbaMozliwychRuchow = mozliweRuchy.size();
        liczbaWygranychRuchu = new int [liczbaMozliwychRuchow];
        resetWygranych();

        Point najlepszyRuch;

/*
        //Random, do testów
        if(true) {
            Random rng = new Random();
            najlepszyRuch = mozliweRuchy.get(rng.nextInt(mozliweRuchy.size()));
            pustePola.remove(najlepszyRuch);
            return najlepszyRuch;
        }
*/

        do
        {
            for(int ruch = 0; ruch < liczbaMozliwychRuchow; ruch++)
            {
                //symulacja dla kazdego punktu
                //Log.d("Symulacja", ruch + "");
                symulacja(ruch);
            }
        }
        while(GetRemainingTimeMS()>=0);

        //wybor najlepszego punktu
        najlepszyRuch = wyborZNajlepszych();


        pustePola.remove(najlepszyRuch);
        return najlepszyRuch;
    }


    public void symulacja (int ruch)
    {
        Field [][] subPlansza = new Field [plansza.length][plansza[0].length];
        Game subGra;
        Field tempC = kolorPrzeciwnika;

        Random rng = new Random();
        List<Point> simMozliweRuchy = new ArrayList<>();

        Point punkt = mozliweRuchy.get(ruch);

        for(int i = 0; i < subPlansza.length; i++)
        {
            for(int j = 0; j < subPlansza[0].length; j++)
            {
                subPlansza[i][j] = plansza[i][j];
            }
        }

        subPlansza[punkt.x][punkt.y] = GetColour();

        simMozliweRuchy = dostepneRuchy(subPlansza, tempC);

        boolean sim = true;

        while(sim)
        {
            if(simMozliweRuchy.size() != 0)
            {
                int nastepnyRuch = rng.nextInt(simMozliweRuchy.size());
                subPlansza[simMozliweRuchy.get(nastepnyRuch).x][simMozliweRuchy.get(nastepnyRuch).y] = tempC;

                if(tempC == naszKolor)
                    tempC = kolorPrzeciwnika;
                else
                    tempC = naszKolor;

                simMozliweRuchy = dostepneRuchy(subPlansza, tempC);

            }
            else
            {
                if(tempC == kolorPrzeciwnika)
                {
                    liczbaWygranychRuchu[ruch] += 1;
                }
                sim = false;
            }

            if(GetRemainingTimeMS() <= 0)
                break;

        }




    }
    /*
        public void symulacja2(int ruch)
        {



            Field wygrana;

            subGra = new Game(new RandomMoveAI(0), kolorPrzeciwnika, new RandomMoveAI(0),naszKolor,subPlansza);

            while(true)
            {

                try {
                    subGra.Update();
                } catch (Game.GameIsOverException e) {
                    wygrana = e.winner;
                    break;

                } catch (Game.InvalidMoveException e) {
                    e.printStackTrace();
                }

            }

            if(wygrana == naszKolor)
                liczbaWygranychRuchu[ruch] += 1;

        }
    */
    public Point wyborZNajlepszych()
    {
        int max = liczbaWygranychRuchu[0];
        int punkt;
        Point point;

        for(int i = 0; i < liczbaWygranychRuchu.length; i ++)
            if(liczbaWygranychRuchu[i] > max) max = liczbaWygranychRuchu[i];

        for(int i = 0; i < liczbaWygranychRuchu.length; i ++)
            if(liczbaWygranychRuchu[i] == max) najlepszeRuchy.add(i);

        Random gen = new Random();

        punkt = najlepszeRuchy.get(gen.nextInt(najlepszeRuchy.size()));
        point = mozliweRuchy.get(punkt);

        return point;
    }


    public void resetWygranych()
    {
        for (int i = 0; i < liczbaWygranychRuchu.length; i++)
        {
            liczbaWygranychRuchu[i] = 0;
        }

        najlepszeRuchy.clear();
    }

    public boolean sprawdzRuch(Point punkt, Field[][] subPlansza, Field kolor)
    {
        if(!(punkt.x < subPlansza.length && punkt.y < subPlansza[0].length && punkt.x >= 0 && punkt.y >= 0))
            return false;

        if(subPlansza[punkt.x][punkt.y] != Field.EMPTY)
            return false;

        Field[][] subSubPlansza = new Field[subPlansza.length][subPlansza[0].length];

        for(int i = 0; i < subPlansza.length; i++)
        {
            for(int j = 0; j < subPlansza[0].length; j++)
                subSubPlansza[i][j] = subPlansza[i][j];
        }

        subSubPlansza[punkt.x][punkt.y] = kolor;
        Group [] grupy = Group.FindAllGroups(subSubPlansza);

        for(Group grupa : grupy)
        {
            if(grupa.HasLiberty(subSubPlansza) == false)
                return false;
        }

        return true;
    }

    public List<Point> dostepneRuchy(Field [][] subPlansza, Field kolor )
    {
        List<Point> ruchy = new ArrayList<>();

        for(int i = 0; i < subPlansza.length; i ++)
        {
            for(int j = 0; j < subPlansza[0].length; j++)
            {
                if(sprawdzRuch(new Point(i,j), subPlansza, kolor))
                    ruchy.add(new Point(i,j));
            }
        }

        return  ruchy;
    }

}



