package com.lucek.androidgameengine2d.view.activities;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.eventBus.events.GameOverEvent;
import com.lucek.androidgameengine2d.eventBus.events.PlayerTurnEvent;
import com.lucek.androidgameengine2d.game.PlayerTypes;
import com.lucek.androidgameengine2d.storage.PreferencesManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;

public class GameLoopActivity extends BaseActivity {

    @BindView(R.id.current_turn_dialog)
    RelativeLayout currentTurnDialog;
    @BindView(R.id.current_turn_text)
    TextView currentTurnText;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;

    private enum DialogActions {
        EXIT,
        RESTART
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game_loop;
    }

    @Subscribe
    public void playerTurnEvent(PlayerTurnEvent event) {
        if (PreferencesManager.NoGo.getPlayer1().equals(PlayerTypes.Types.HUMAN.name) || PreferencesManager.NoGo.getPlayer2().equals(PlayerTypes.Types.HUMAN.name)) {
            currentTurnText.setText(String.format(getString(R.string.player_turn_text), getString(event.currentPlayerString)));
            currentTurnDialog.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    currentTurnDialog.setVisibility(View.GONE);
                }
            }, TimeUnit.SECONDS.toMillis(2));
        }
    }

    @Subscribe
    public void gameOverDialog(GameOverEvent event) {
        showYesNoDialog(this, getString(R.string.game_is_over_dialog_title), String.format(getString(R.string.winning_dialog_text), event.winningColor),
                getString(R.string.positive_dialog), onDialogActionClickListener(DialogActions.RESTART),
                getString(R.string.negative_dialog), onDialogActionClickListener(DialogActions.EXIT),
                false);
    }

    @Override
    public void onBackPressed() {
        showYesNoDialog(this, getString(R.string.stop_game_dialog_title), getString(R.string.stop_game_dialog_text),
                getString(R.string.positive_dialog), onDialogActionClickListener(DialogActions.EXIT),
                getString(R.string.negative_dialog), onDialogActionClickListener(null),
                false);
    }

    private View.OnClickListener onDialogActionClickListener(final DialogActions action) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action != null) {
                    if (action.equals(DialogActions.EXIT)) {
                        finish();
                    } else if (action.equals(DialogActions.RESTART)) {
                        Intent intent = new Intent(context, GameLoopActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                removeDialog();
            }
        };
    }
}
