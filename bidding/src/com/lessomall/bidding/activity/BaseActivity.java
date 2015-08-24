package com.lessomall.bidding.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.MD5;
import com.lessomall.bidding.ui.TimeChooserDialog;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseActivity extends FragmentActivity {

    protected static final int HANDLER_DATA = 1;
    protected static final int HANDLER_NETWORK_ERR = 2;

    public LessoApplication.LoginUser loginUser;

    private InputMethodManager mSoftManager;
    private ProgressDialog loadingDialog;

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected String sBeginDate, sEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        loginUser = ((LessoApplication) getApplication()).getUser();
        if (loginUser == null && !(this instanceof LoginActivity)) {
            sendBroadcast(new Intent(Constant.FINISH_ACTION));
            startActivity(new Intent(this, LoginActivity.class), true);
        }

        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mSoftManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        loginUser = ((LessoApplication) getApplication()).getUser();
        if (loginUser == null && !(this instanceof LoginActivity)) {
            sendBroadcast(new Intent(Constant.FINISH_ACTION));
            startActivity(new Intent(this, LoginActivity.class), true);
        }
    }


    public Map<String, String> generateRequestMap() {

        Map<String, String> map = new HashMap<String, String>();

        map.put("appkey", Constant.APP_KEY_ANDROID);
        map.put("timestamp", (System.currentTimeMillis() / 1000) + "");
        map.put("token", new MD5().GetMD5Code(Constant.SECRET_KEY + Constant.DATE_FORMAT_1.format(new Date())));

        return map;
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

    protected void startActivity(Intent intent, boolean flag) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        if (flag) finish();
    }

    public void tipsOutput(String recode, String msg) {
        if (Constant.RECODE_ERROR_TIPS.equals(recode)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_USER_LOGIN.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_LOGIN), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_USER_NOTEXIST.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_NOTEXIST), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_PASSWORD_WRONG.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_PASSWORD_WRONG), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_LOGIN), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 加载对话框(显示)
     */
    public void loading() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setMessage(getString(R.string.loading));
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();
    }

    /**
     * 加载对话框(关闭)
     */
    public void disLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected void hideSoftKeybord() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            mSoftManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

}

