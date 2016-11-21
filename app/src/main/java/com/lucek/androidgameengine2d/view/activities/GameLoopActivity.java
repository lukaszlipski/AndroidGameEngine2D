package com.lucek.androidgameengine2d.view.activities;

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
    public void gameOverDialog(GameOverEvent event) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.game_is_over_dialog_title)
                .setMessage(String.format(getString(R.string.winning_dialog_text), event.winningColor))
                .setPositiveButton(R.string.positive_dialog, onDialogActionClickListener(DialogActions.RESTART))
                .setNegativeButton(R.string.negative_dialog, onDialogActionClickListener(DialogActions.EXIT))
                .setCancelable(false)
                .show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.stop_game_dialog_title)
                .setMessage(R.string.stop_game_dialog_text)
                .setPositiveButton(R.string.positive_dialog, onDialogActionClickListener(DialogActions.EXIT))
                .setNegativeButton(R.string.negative_dialog, null)
                .show();
    }

    private DialogInterface.OnClickListener onDialogActionClickListener(final DialogActions action) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (action.equals(DialogActions.EXIT)) {
                    finish();
                } else if (action.equals(DialogActions.RESTART)) {
                    Intent intent = new Intent(context, GameLoopActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }
}
