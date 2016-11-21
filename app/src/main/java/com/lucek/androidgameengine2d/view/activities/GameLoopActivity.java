package com.lucek.androidgameengine2d.view.activities;

import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.lucek.androidgameengine2d.core.graphics.CustomSurfaceView;

public class GameLoopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomSurfaceView surfaceView = new CustomSurfaceView(this);
        setContentView(surfaceView);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Stop game")
                .setMessage("Are you sure you want to stop the game?")
                .setPositiveButton("Yes", onExitClickListener())
                .setNegativeButton("No", null)
                .show();
    }

    private DialogInterface.OnClickListener onExitClickListener() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        };
    }
}
