package com.lucek.androidgameengine2d.view.activities;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.eventBus.events.GameOverEvent;
import com.lucek.androidgameengine2d.game.Field;
import com.lucek.androidgameengine2d.storage.PreferencesManager;
import com.lucek.androidgameengine2d.view.fragments.SimulateSurfaceFragment;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

public class SimulateActivity extends BaseActivity {

    @BindView(R.id.simulate_container)
    FrameLayout simulateContainer;
    @BindView(R.id.score)
    TextView score;

    private int white = 0, black = 0;
    private int gameCount;

    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

    private enum DialogActions {
        EXIT,
        RESTART
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_simulate;
    }

    @Override
    protected void afterBind() {
        gameCount = PreferencesManager.NoGo.getGamesCount();
        score.setText(String.format(getString(R.string.simulate_score_holder), white, black));
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.simulate_container, new SimulateSurfaceFragment());
        ft.commit();
    }

    @Subscribe
    public void gameOverDialog(GameOverEvent event) {
        if (event.winningColor == Field.WHITE) white++;
        else black++;

        score.setText(String.format(getString(R.string.simulate_score_holder), white, black));

        if (black + white < gameCount) {
            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.simulate_container, new SimulateSurfaceFragment());
            ft.commit();
        }
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
                        Intent intent = new Intent(context, SimulateActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                removeDialog();
            }
        };
    }
}
