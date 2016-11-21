package com.lucek.androidgameengine2d.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.lucek.androidgameengine2d.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainMenuActivity extends BaseActivity {
    @BindView(R.id.btn_start)
    View btnStart;
    @BindView(R.id.btn_exit)
    View btnExit;

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

    @OnClick({R.id.btn_start, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                Intent intent = new Intent(this, GameLoopActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_exit:
                finish();
                break;
        }
    }
}