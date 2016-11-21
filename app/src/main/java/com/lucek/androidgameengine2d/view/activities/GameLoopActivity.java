package com.lucek.androidgameengine2d.view.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.eventBus.events.GameOverEvent;

import org.greenrobot.eventbus.Subscribe;

public class GameLoopActivity extends BaseActivity {

    private enum DialogActions {
        EXIT,
        RESTART
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_loop;
    }

    @Override
    protected void afterBind() {

    }

    @Subscribe
    public void gameOverDialog(GameOverEvent event){
        new AlertDialog.Builder(this)
                .setTitle("Game is Over")
                .setMessage(String.format("Player %s win this game. \nDo you want to restart the game?", event.winningColor))
                .setPositiveButton("Yes", onDialogActionClickListener(DialogActions.RESTART))
                .setNegativeButton("No", onDialogActionClickListener(DialogActions.EXIT))
                .show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Stop game")
                .setMessage("Are you sure you want to stop the game?")
                .setPositiveButton("Yes", onDialogActionClickListener(DialogActions.EXIT))
                .setNegativeButton("No", null)
                .show();
    }

    private DialogInterface.OnClickListener onDialogActionClickListener(final DialogActions action) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (action.equals(DialogActions.EXIT)){
                    finish();
                } else if (action.equals(DialogActions.RESTART)){
                    Intent intent = new Intent(context, GameLoopActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}
