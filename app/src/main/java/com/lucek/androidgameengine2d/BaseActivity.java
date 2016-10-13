package com.lucek.androidgameengine2d;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lucek.androidgameengine2d.core.graphics.CustomSurfaceView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomSurfaceView m_GLSurfaveView = new CustomSurfaceView(this);
        setContentView(m_GLSurfaveView);

    }
}
