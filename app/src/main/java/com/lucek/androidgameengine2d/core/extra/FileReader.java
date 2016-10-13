package com.lucek.androidgameengine2d.core.extra;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lukas on 12.10.2016.
 */

public class FileReader {

    static public String readFromResource(Context ctx, int id){

        InputStream inputStream = ctx.getResources().openRawResource(id);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffreader.readLine()) != null)
                text.append(line);

        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }
}
