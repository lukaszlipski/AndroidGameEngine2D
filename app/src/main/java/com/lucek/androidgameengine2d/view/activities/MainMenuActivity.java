package com.lucek.androidgameengine2d.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.storage.PreferencesManager;

import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;

public class MainMenuActivity extends BaseActivity {
    @BindView(R.id.btn_new_game)
    View btnStart;
    @BindView(R.id.btn_exit)
    View btnExit;
    @BindView(R.id.control_spinner_1)
    Spinner controlSpinner1;
    @BindView(R.id.control_spinner_2)
    Spinner controlSpinner2;
    @BindView(R.id.main_menu_items)
    RelativeLayout mainMenuItems;
    @BindView(R.id.et_turn_time)
    EditText etTurnTime;
    @BindView(R.id.til_turn_time)
    TextInputLayout tilTurnTime;
    @BindView(R.id.et_game_count)
    EditText etGameCount;
    @BindView(R.id.til_game_count)
    TextInputLayout tilGameCount;
    @BindView(R.id.play_preferences_items)
    LinearLayout playPreferencesItems;
    @BindView(R.id.btn_back)
    ImageView btnBack;

    private Set<String> playersSet = new HashSet<>();

    private int turnCounter = 0;
    private boolean isTournamentModeTurnedOn = PreferencesManager.NoGo.getAlgorithmTournament();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_menu;
    }

    @Override
    protected void afterBind() {
        //TODO Get algorithms to list
        playersSet.add(getString(R.string.human_dropdown_value));
        playersSet.add(getString(R.string.simple_ai_dropdown_value));

        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.addAll(playersSet);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        controlSpinner1.setAdapter(spinnerAdapter);
        controlSpinner2.setAdapter(spinnerAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        etTurnTime.setText(String.valueOf(PreferencesManager.NoGo.getTimeForTurn()));
        etGameCount.setText(String.valueOf(PreferencesManager.NoGo.getGamesCount()));
        controlSpinner1.setSelection(0); //TODO
        controlSpinner2.setSelection(0);
    }

    @OnClick({R.id.btn_new_game, R.id.btn_exit, R.id.btn_start, R.id.btn_back, R.id.game_logo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_game:
                playPreferencesItems.setVisibility(View.VISIBLE);
                btnBack.setVisibility(View.VISIBLE);
                mainMenuItems.setVisibility(View.GONE);
                break;

            case R.id.btn_exit:
                finish();
                break;

            case R.id.btn_start:
                PreferencesManager.NoGo.setPlayer1(controlSpinner1.getSelectedItem().toString());
                PreferencesManager.NoGo.setPlayer2(controlSpinner2.getSelectedItem().toString());
                PreferencesManager.NoGo.setTimeForTurn(Long.parseLong(etTurnTime.getText().toString()));
                PreferencesManager.NoGo.setGamesCount(Integer.parseInt(etGameCount.getText().toString()));

                Intent intent = new Intent(this, GameLoopActivity.class);
                startActivity(intent);

                playPreferencesItems.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                mainMenuItems.setVisibility(View.VISIBLE);
                break;

            case R.id.btn_back:
                playPreferencesItems.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                mainMenuItems.setVisibility(View.VISIBLE);
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