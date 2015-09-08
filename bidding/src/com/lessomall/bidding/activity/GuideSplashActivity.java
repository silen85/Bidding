package com.lessomall.bidding.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.MD5;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.fragment.GuideFragment;
import com.lessomall.bidding.fragment.SplashFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by meisl on 2015/6/9.
 */
public class GuideSplashActivity extends FragmentActivity {

    private String TAG = "com.lessomall.bidding.activity.GuideSplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_guide_splash);

        LinearLayout main = (LinearLayout) findViewById(R.id.main);

        SharedPreferences sp = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);

        final String username = sp.getString(Constant.LESSO_BIDDING_USERNAME, "");
        final String password = sp.getString(Constant.LESSO_BIDDING_USERPASSWORD, "");

        if (!"".equals(username) && !"".equals(password)) {

            RelativeLayout view = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.fragment_splash, null);

            main.addView(view);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doLogin(username, password);
                }
            }, 2000);

        } else {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = null;
            if (sp.getInt("versionCode", 0) == Tools.getPackageInfo(GuideSplashActivity.this).versionCode) {
                fragment = new SplashFragment();
            } else {
                fragment = new GuideFragment();

                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("versionCode", Tools.getPackageInfo(GuideSplashActivity.this).versionCode);
                edit.commit();
            }

            fragmentTransaction.add(R.id.main, fragment);
            fragmentTransaction.commit();

        }

    }

    private void doLogin(String username, String password) {

        Map params = Tools.generateRequestMap();

        params.put("username", username);
        params.put("password", new MD5().GetMD5Code(password));

        RequestParams requestParams = new RequestParams(params);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                reLogin();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {

                    String json = responseString;
                    try {
                        Map result = Tools.json2Map(json);

                        String recode = (String) result.get("recode");
                        String msg = (String) result.get("msg");

                        if (Constant.RECODE_SUCCESS.equals(recode)) {
                            JSONObject info = (JSONObject) result.get("info");
                            if (info != null) {
                                handleLogin(info);
                            } else {
                                reLogin();
                            }
                        } else {
                            reLogin();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        reLogin();
                    }
                } else {
                    reLogin();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(this, Constant.URL_LOGIN, requestParams, asyncHttpResponseHandler);
    }

    private void handleLogin(JSONObject data) throws Exception {

        SharedPreferences sp = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);

        String username = sp.getString(Constant.LESSO_BIDDING_USERNAME, "");
        String password = sp.getString(Constant.LESSO_BIDDING_USERPASSWORD, "");

        String userid = (String) data.get("userid");
        String _username = (String) data.get("username");
        String sessionid = (String) data.get("sessionid");
        String type = (String) data.get("type");
        String email = (String) data.get("email");
        String status = (String) data.get("status");
        String customName = (String) data.get("customName");
        String customCode = (String) data.get("customCode");
        String address = (String) data.get("address");
        String linkman = (String) data.get("linkman");
        String lastLoginTime = (String) data.get("lastLoginTime");
        String createTime = (String) data.get("createTime");

        if ("".equals(status)) {

            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constant.LESSO_BIDDING_USERNAME, _username);
            editor.putString(Constant.LESSO_BIDDING_USERPASSWORD, password);
            editor.commit();

            LessoApplication.LoginUser loginUser = ((LessoApplication) getApplication()).new LoginUser();
            loginUser.setUserid(userid);
            loginUser.setUsername(_username);
            loginUser.setSessionid(sessionid);
            loginUser.setType(type);
            loginUser.setEmail(email);
            loginUser.setStatus(status);
            loginUser.setCustomName(customName);
            loginUser.setCustomCode(customCode);
            loginUser.setAddress(address);
            loginUser.setLinkman(linkman);
            loginUser.setLastLoginTime(lastLoginTime);
            loginUser.setCreateTime(createTime);

            ((LessoApplication) getApplication()).setUser(loginUser);

            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            finish();

        } else {
            reLogin();
        }
    }

    private void reLogin() {
        sendBroadcast(new Intent(Constant.FINISH_ACTION));
        startActivity(new Intent(GuideSplashActivity.this, LoginActivity.class));
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        finish();
    }


}
