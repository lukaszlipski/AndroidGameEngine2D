package com.lucek.androidgameengine2d.view.activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lucek.androidgameengine2d.R;
import com.lucek.androidgameengine2d.eventBus.Bus;
import com.lucek.androidgameengine2d.eventBus.events.SampleResponseEvent;

import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

abstract class BaseActivity extends AppCompatActivity {

    protected Context context;
    protected Dialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

    protected void showYesNoDialog(Context mContext, String title, String message, String positiveText,
                                   View.OnClickListener onPositiveClickListener, String negativeText,
                                   View.OnClickListener onNegativeClickListener, boolean cancellable) {
        removeDialog();
        mDialog = new Dialog(mContext, android.R.style.Theme_Translucent_NoTitleBar);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View layout = mInflater.inflate(R.layout.dialog_yes_no, null);
        mDialog.setContentView(layout);

        TextView mTitle = (TextView) layout.findViewById(R.id.title);
        TextView mMessage = (TextView) layout.findViewById(R.id.message);
        Button mPositiveButton = (Button) layout.findViewById(R.id.positive_button);
        Button mNegativeButton = (Button) layout.findViewById(R.id.negative_button);

        mTitle.setText(title);
        mMessage.setText(message);
        mPositiveButton.setText(positiveText);
        mPositiveButton.setOnClickListener(onPositiveClickListener);
        mNegativeButton.setText(negativeText);
        mNegativeButton.setOnClickListener(onNegativeClickListener);

        mDialog.setCancelable(cancellable);
        mDialog.show();
    }

    protected void removeDialog() {
        if (mDialog != null)
            mDialog.dismiss();
    }
}
