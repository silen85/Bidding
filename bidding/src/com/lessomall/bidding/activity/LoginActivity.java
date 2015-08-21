package com.lessomall.bidding.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.MD5;
import com.lessomall.bidding.common.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;


public class LoginActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    private String TAG = "com.lessomall.bidding.activity.LoginActivity";


    private static final int HANDLER_DATA = 1;
    private static final int HANDLER_NETWORK_ERR = 2;

    private EditText accountEditText;
    private EditText passwordEditText;
    private ImageView delete;
    private Button login;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initTitle();

        initView();

        initData();

        mHandler = new Handler(this);

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

        accountEditText = (EditText) findViewById(R.id.accountEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        SharedPreferences sp = getSharedPreferences(getString(R.string.app_name), Activity.MODE_PRIVATE);
        String username = sp.getString(Constant.LESSO_BIDDING_USERNAME, "");
        if (username != null && !"".equals(username.trim())) {
            accountEditText.setText(username);
            passwordEditText.requestFocus();
        }

        delete = (ImageView) findViewById(R.id.delete);
        login = (Button) findViewById(R.id.login);

        delete.setOnClickListener(this);
        login.setOnClickListener(this);

        accountEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                if (accountEditText.getText().toString().length() != 0
                        && passwordEditText.getText().toString().length() != 0) {
                    login.setBackgroundResource(R.drawable.button_login_enable);
                    delete.setVisibility(View.VISIBLE);
                } else {
                    login.setBackgroundResource(R.drawable.button_login_normal);
                    delete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        passwordEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                if (accountEditText.getText().toString().length() != 0
                        && passwordEditText.getText().toString().length() != 0) {
                    login.setBackgroundResource(R.drawable.button_login_enable);
                    delete.setVisibility(View.VISIBLE);
                } else {
                    login.setBackgroundResource(R.drawable.button_login_normal);
                    delete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });


    }

    @Override
    protected void initData() {


    }


    private void delete() {

        accountEditText.setText("");
        passwordEditText.setText("");

        accountEditText.requestFocus();
    }

    private void login() {
        // TODO Auto-generated method stub

        if (TextUtils.isEmpty(accountEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_login_tips_account),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.text_login_tips_password),
                    Toast.LENGTH_SHORT).show();
            return;
        }


        login.setBackgroundResource(R.drawable.button_login_selected);
        login.setClickable(false);

        loading();

        String username = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        Map params = generateRequestMap();

        params.put("username", username);
        params.put("password", new MD5().GetMD5Code(password));

        doLogin(params);

    }

    protected void doLogin(Map<String, String> parems) {

        RequestParams requestParams = new RequestParams(parems);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Message message = mHandler.obtainMessage();
                message.what = HANDLER_NETWORK_ERR;
                message.sendToTarget();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {
                    Message message = mHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("json", responseString);
                    message.what = HANDLER_DATA;
                    message.setData(bundle);
                    message.sendToTarget();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();

                login.setBackgroundResource(R.drawable.button_login_enable);
                login.setClickable(true);

                disLoading();
            }
        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(this, Constant.URL_LOGIN, requestParams, asyncHttpResponseHandler);
    }

    private void handleLogin(JSONObject data) throws Exception {

        String username = accountEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        String userid = (String) data.get("userid");
        String _username = (String) data.get("username");
        String sessionid = (String) data.get("sessionid");
        String type = (String) data.get("type");
        String email = (String) data.get("email");
        String status = (String) data.get("status");
        String customName = (String) data.get("customName");
        String address = (String) data.get("address");
        String linkman = (String) data.get("linkman");
        String lastLoginTime = (String) data.get("lastLoginTime");
        String createTime = (String) data.get("createTime");

        if ("".equals(status)) {

            SharedPreferences sp = getSharedPreferences(getString(R.string.app_name), Activity.MODE_PRIVATE);
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
            loginUser.setAddress(address);
            loginUser.setLinkman(linkman);
            loginUser.setLastLoginTime(lastLoginTime);
            loginUser.setCreateTime(createTime);

            ((LessoApplication) getApplication()).setUser(loginUser);

            startActivity(new Intent(LoginActivity.this, MainActivity.class), true);

        } else {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_LOGIN), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean handleMessage(Message message) {
        if (message.what == HANDLER_DATA) {
            String json = message.getData().getString("json");
            try {
                Map result = Tools.json2Map(json);

                String recode = (String) result.get("recode");
                String msg = (String) result.get("msg");

                if (Constant.RECODE_SUCCESS.equals(recode)) {
                    JSONObject info = (JSONObject) result.get("info");
                    if (info != null) {
                        handleLogin(info);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_LOGIN), Toast.LENGTH_SHORT).show();
                    }
                } else {
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
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_LOGIN), Toast.LENGTH_SHORT).show();
            }
        } else if (message.what == HANDLER_NETWORK_ERR) {
            Toast.makeText(this, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.delete:
                delete();
                break;
            case R.id.login:
                hideSoftKeybord();
                login();
                break;

        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }


}
