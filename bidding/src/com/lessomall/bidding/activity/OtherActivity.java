package com.lessomall.bidding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.fragment.GuideFragment;
import com.lessomall.bidding.fragment.other.AboutusFragment;
import com.lessomall.bidding.fragment.other.HelpFragment;
import com.lessomall.bidding.fragment.other.OtherFragment;
import com.lessomall.bidding.ui.bidding.LogoutDialog;

/**
 * Created by meisl on 2015/8/18.
 */
public class OtherActivity extends BaseActivity {

    private OtherFragment otherFragment;
    private HelpFragment helpFragment;
    private AboutusFragment aboutusFragment;
    private GuideFragment guideFragment;

    private RelativeLayout main_title;

    private ImageView btn_back;
    private TextView title_txt;

    private LogoutDialog logoutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_other);

        initTitle();

        initView();

        initData();

    }

    @Override
    protected void initTitle() {

        main_title = (RelativeLayout) findViewById(R.id.main_title);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (otherFragment.isVisible()) {
                    Intent intent = new Intent(OtherActivity.this, MainActivity.class);
                    startActivity(intent, true);
                } else {
                    backToList();
                }

            }
        });

        title_txt = (TextView) findViewById(R.id.title_txt);

    }

    @Override
    protected void initView() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        otherFragment = new OtherFragment();
        fragmentTransaction.add(R.id.main, otherFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void initData() {

    }


    @Override
    public void refreshList() {

    }

    public void aboutusView() {

        aboutusFragment = new AboutusFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, aboutusFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        title_txt.setText("关于我们");

    }

    public void helpView() {

        helpFragment = new HelpFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, helpFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        title_txt.setText("联系客服");

    }

    public void introduceView() {

        main_title.setVisibility(View.GONE);

        guideFragment = new GuideFragment();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main, guideFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void showLogoutDialog() {

        if (logoutDialog == null) {
            logoutDialog = new LogoutDialog(this);
            logoutDialog.getWindow().setGravity(Gravity.BOTTOM);
            logoutDialog.setCanceledOnTouchOutside(true);
            logoutDialog.getWindow().setWindowAnimations(R.style.DIALOG);
        }
        logoutDialog.show();

    }

    @Override
    public void backToList() {
        super.backToList();

        main_title.setVisibility(View.VISIBLE);
        title_txt.setText("其它");
        getSupportFragmentManager().popBackStackImmediate();
    }

    @Override
    public void onBackPressed() {
        btn_back.performClick();
    }
}
