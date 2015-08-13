package com.lessomall.bidding.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Window;
import android.widget.RelativeLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.ui.TimeChooserDialog;

public abstract class BaseActivity extends FragmentActivity {

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected RelativeLayout time_chooser;
    protected String sBeginDate, sEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    protected void showTimerDialog() {

        timerDialog = new TimeChooserDialog(this, timeType, sBeginDate, sEndDate);
        timerDialog.getWindow().setGravity(Gravity.BOTTOM);
        timerDialog.setCanceledOnTouchOutside(true);
        timerDialog.setClickListenerInterface(new TimeChooserDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

                timeType = timerDialog.getType();
                sBeginDate = timerDialog.getsBeaginDate();
                sEndDate = timerDialog.getsEndDate();

                initData();

            }
        });
        timerDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        timerDialog.show();

    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initBottom();

}

