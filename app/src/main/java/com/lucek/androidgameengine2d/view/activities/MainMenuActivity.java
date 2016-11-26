package com.lucek.androidgameengine2d.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.storage.PreferencesManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MainMenuActivity extends BaseActivity {
    @BindView(R.id.btn_start)
    View btnStart;
    @BindView(R.id.btn_exit)
    View btnExit;

    private int turnCounter = 0;
    private boolean isTournamentModeTurnedOn = PreferencesManager.NoGo.getAlgorithmTournament();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_menu;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void afterBind() {
    }

    @OnClick({R.id.btn_start, R.id.btn_exit, R.id.game_logo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, GameLoopActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                finish();
                break;
            case R.id.game_logo:
                turnCounter++;

                if (turnCounter == 5) {
                    Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vib.vibrate(500);

                    isTournamentModeTurnedOn = !isTournamentModeTurnedOn;
                    PreferencesManager.NoGo.setAlgorithmTournament(isTournamentModeTurnedOn);

                    if (isTournamentModeTurnedOn) {
                        Snackbar snackbar = Snackbar.make(btnExit, R.string.algorithm_tournament_on_text, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } else {
                        Snackbar snackbar = Snackbar.make(btnExit, R.string.algorithm_tournament_off_text, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    turnCounter = 0;
                }
                break;
        }
    }
}