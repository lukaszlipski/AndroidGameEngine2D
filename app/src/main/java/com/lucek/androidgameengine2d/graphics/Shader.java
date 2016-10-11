package com.lucek.androidgameengine2d.graphics;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.lucek.androidgameengine2d.extra.FileReader;

/**
 * Created by lukas on 12.10.2016.
 */

public class Shader {

    private int m_ProgramID;

    public Shader(Context ctx,int idVertexShader, int idFragmentShader) {
        int vShader = this.loadShader(GLES20.GL_VERTEX_SHADER, FileReader.readFromResource(ctx,idVertexShader) );
        int fShader = this.loadShader(GLES20.GL_FRAGMENT_SHADER, FileReader.readFromResource(ctx, idFragmentShader) );

        m_ProgramID = GLES20.glCreateProgram();
        GLES20.glAttachShader(m_ProgramID, vShader);
        GLES20.glAttachShader(m_ProgramID, fShader);
        GLES20.glLinkProgram(m_ProgramID);
    }

    public int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        GLES20.glGetShaderInfoLog(shader);

        // TODO : Add to logging system
        if(compileStatus[0] == 0)
            Log.e("ERROR","Cannot compile shader");

        return shader;
    }

    public int GetProgramID() {
        return m_ProgramID;
    }

}
