package com.lessomall.bidding.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;

import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.fragment.GuideFragment;
import com.lessomall.bidding.fragment.SplashFragment;

/**
 * Created by meisl on 2015/6/9.
 */
public class GuideSplashActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*if (Constant.DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());
        }*/

        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_guide_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences(
                        getString(R.string.app_name), Context.MODE_PRIVATE);

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
        }).start();


    }
}
