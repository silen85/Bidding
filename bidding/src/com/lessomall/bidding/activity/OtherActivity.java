package com.lessomall.bidding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/8/18.
 */
public class OtherActivity extends BaseActivity {

    private ImageView btn_back;
    private Button about_us, suggestion, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other);

        initTitle();

        initView();

        initBottom();

        initData();

    }

    @Override
    protected void initTitle() {

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OtherActivity.this, MainActivity.class);
                startActivity(intent, true);
            }
        });

    }

    @Override
    protected void initView() {

        about_us = (Button) findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        suggestion = (Button) findViewById(R.id.suggestion);
        suggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LessoApplication) getApplication()).setUser(null);

                /*SharedPreferences sp = getSharedPreferences(Constant.LESSOBI, Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constant.LESSOBI_USERID);
                editor.remove(Constant.LESSOBI_USERNAME);
                editor.remove(Constant.LESSOBI_USERPASSWORD);
                editor.remove(Constant.LESSOBI_USERSCRATPWD);
                editor.commit();

                Intent intent = new Intent(SetupActivity.this, SplashLoginActivity.class);
                startActivity(intent);
                finish();*/
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initBottom() {

    }


    @Override
    public void onBackPressed() {
        btn_back.performClick();
    }
}
