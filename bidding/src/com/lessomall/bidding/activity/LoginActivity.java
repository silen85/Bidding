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
import com.lessomall.bidding.activity.dealer.BiddingListActivity;


public class LoginActivity extends Activity implements View.OnClickListener {

    private static final int FRAGMENT_TYPE_DEALER = 1;
    private static final int FRAGMENT_TYPE_SUPPLIER = 2;
    private static final int FRAGMENT_TYPE_ADMIN = 3;

    private Button btn_back;
    private TextView main_title_txt;

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
                    login.setTextColor(getResources().getColor(R.color.MALL_C3));
                    delete.setVisibility(View.VISIBLE);
                } else {
                    login.setBackgroundResource(R.drawable.button_normal);
                    login.setTextColor(getResources().getColor(R.color.MALL_C4));
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
                    login.setTextColor(getResources().getColor(R.color.MALL_C3));
                    delete.setVisibility(View.VISIBLE);
                } else {
                    login.setBackgroundResource(R.drawable.button_normal);
                    login.setTextColor(getResources().getColor(R.color.MALL_C4));
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

        btn_back = (Button) findViewById(R.id.btn_back);
        main_title_txt = (TextView) findViewById(R.id.main_title_txt);

        main_title_txt.setText(getString(R.string.text_login));
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
        int fragmentType = Integer.parseInt(accountEditText.getText().toString());
        switch (fragmentType) {
            case FRAGMENT_TYPE_DEALER:
                it.setClass(LoginActivity.this, BiddingListActivity.class);
                break;
            case FRAGMENT_TYPE_SUPPLIER:
                //it.setClass(LoginActivity.this, SupplierActivity.class);
                break;
            case FRAGMENT_TYPE_ADMIN:
                //it.setClass(LoginActivity.this, AdminActivity.class);
                break;
            default:

                break;
        }

        startActivity(it);
        finish();
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);

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
