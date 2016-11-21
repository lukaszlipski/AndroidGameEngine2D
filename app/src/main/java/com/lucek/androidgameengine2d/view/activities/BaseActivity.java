package com.lucek.androidgameengine2d.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lucek.androidgameengine2d.Main;
import com.lucek.androidgameengine2d.eventBus.Bus;
import com.lucek.androidgameengine2d.eventBus.events.SampleResponseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

abstract class BaseActivity extends AppCompatActivity {

    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        context = this;
        afterBind();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bus.getInstance().unregister(this);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void afterBind() {
    }

    @Subscribe
    public void initBus(SampleResponseEvent event) {
    }
}
