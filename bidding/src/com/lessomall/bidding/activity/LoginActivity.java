package com.lessomall.bidding.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lessomall.bidding.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    private ImageView btn_back;
    private TextView title_txt;

    private EditText accountEditText;
    private EditText passwordEditText;
    private ImageView delete;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initTitle();

        initView();

        initData();

    }

    protected void initView() {

        accountEditText = (EditText) findViewById(R.id.accountEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

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
                    login.setBackgroundResource(R.drawable.button_enable);
                    login.setTextColor(getResources().getColor(R.color.MALL_C8));
                    delete.setVisibility(View.VISIBLE);
                } else {
                    login.setBackgroundResource(R.drawable.button_normal);
                    login.setTextColor(getResources().getColor(R.color.MALL_C5));
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
                    login.setBackgroundResource(R.drawable.button_enable);
                    login.setTextColor(getResources().getColor(R.color.MALL_C8));
                    delete.setVisibility(View.VISIBLE);
                } else {
                    login.setBackgroundResource(R.drawable.button_normal);
                    login.setTextColor(getResources().getColor(R.color.MALL_C5));
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

    protected void initData() {


    }

    protected void initTitle() {

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        title_txt = (TextView) findViewById(R.id.title_txt);
        title_txt.setText(getString(R.string.text_login));
    }

    private void delete() {
        accountEditText.setText("");
        passwordEditText.setText("");
    }

    private void login() {
        // TODO Auto-generated method stub

        if (TextUtils.isEmpty(accountEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入账号",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(passwordEditText.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请输入密码",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        login.setBackgroundResource(R.drawable.button_selected);

        Intent it = new Intent();
        it.setClass(LoginActivity.this, MainActivity.class);
        startActivity(it);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        finish();

    }


    private void backClick() {
        finish();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_back:
                backClick();
                break;
            case R.id.delete:
                delete();
                break;
            case R.id.login:
                login();
                break;


        }

    }


    @Override
    public void onBackPressed() {
        btn_back.performClick();
    }

}
