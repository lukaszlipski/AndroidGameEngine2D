package com.lucek.androidgameengine2d.view.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.lucek.androidgameengine2d.R;

public class GameLoopActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_loop;
    }

    @Override
    protected void afterBind() {

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
