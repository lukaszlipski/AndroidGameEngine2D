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
import com.lucek.androidgameengine2d.game.PlayerTypes;
import com.lucek.androidgameengine2d.storage.PreferencesManager;

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

    private int turnCounter = 0;
    private boolean isTournamentModeTurnedOn = PreferencesManager.NoGo.getAlgorithmTournament();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_menu;
    }

    @Override
    protected void afterBind() {
        ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerAdapter.addAll(PlayerTypes.getListOfTypes());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        controlSpinner1.setAdapter(spinnerAdapter);
        controlSpinner2.setAdapter(spinnerAdapter);
        checkTournamentState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMainMenu();
        etTurnTime.setText(String.valueOf(PreferencesManager.NoGo.getTimeForTurn()));
        etGameCount.setText(String.valueOf(PreferencesManager.NoGo.getGamesCount()));
        controlSpinner1.setSelection(PlayerTypes.getPositionByName(PreferencesManager.NoGo.getPlayer1()));
        controlSpinner2.setSelection(PlayerTypes.getPositionByName(PreferencesManager.NoGo.getPlayer2()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePlayerState();
    }

    private void checkTournamentState(){
        if (isTournamentModeTurnedOn){
            tilTurnTime.setVisibility(View.VISIBLE);
            tilGameCount.setVisibility(View.VISIBLE);
        } else {
            tilTurnTime.setVisibility(View.GONE);
            tilGameCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if (btnBack.getVisibility() == View.VISIBLE) {
            showMainMenu();
        } else {
            finish();
        }
    }

    private void showNewGameMenu() {
        playPreferencesItems.setVisibility(View.VISIBLE);
        btnBack.setVisibility(View.VISIBLE);
        mainMenuItems.setVisibility(View.GONE);
    }

    private void showMainMenu() {
        playPreferencesItems.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);
        mainMenuItems.setVisibility(View.VISIBLE);
    }

    private void savePlayerState() {
        PreferencesManager.NoGo.setPlayer1(controlSpinner1.getSelectedItem().toString());
        PreferencesManager.NoGo.setPlayer2(controlSpinner2.getSelectedItem().toString());
        PreferencesManager.NoGo.setTimeForTurn(Long.parseLong(etTurnTime.getText().toString()));
        PreferencesManager.NoGo.setGamesCount(Integer.parseInt(etGameCount.getText().toString()));
    }

    @OnClick({R.id.btn_new_game, R.id.btn_exit, R.id.btn_start, R.id.btn_back, R.id.game_logo})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_game:
                showNewGameMenu();
                break;

            case R.id.btn_exit:
                finish();
                break;

            case R.id.btn_start:
                savePlayerState();

                Intent intent = new Intent(this, GameLoopActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_back:
                showMainMenu();
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
                    checkTournamentState();
                }
                break;
        }
    }
}