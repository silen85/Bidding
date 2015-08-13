package com.lessomall.bidding.activity.dealer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.fragment.dealer.BiddingListFragment;

/**
 * Created by meisl on 2015/7/23.
 */
public class BiddingListActivity extends BaseActivity {

    private String TAG = "com.lessomall.bidding.activity.dealer.BiddingListActivity";

    private FragmentManager fragmentManager;
    private Fragment fragment;

    private TextView main_title_txt;
    private ImageView main_setting;

    private RelativeLayout main;
    private LinearLayout main_top;
    private LinearLayout main_middle;
    private LinearLayout main_bottom;

    private LinearLayout dealer_release;
    private LinearLayout dealer_order;
    private LinearLayout dealer_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_template1);

        fragmentManager = getSupportFragmentManager();

        initTitle();

        initBottom();

        initView();

        initData();

    }

    private void initFragment() {

        fragment = new BiddingListFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_middle, fragment);

        fragmentTransaction.commit();

    }

    @Override
    protected void initTitle() {

        main_top = (LinearLayout) findViewById(R.id.main_top);

        View view = LayoutInflater.from(this).inflate(R.layout.title_level1, null);

        main_top.addView(view);

        main_title_txt = (TextView) findViewById(R.id.main_title_txt);
        main_setting = (ImageView) findViewById(R.id.main_setting);

    }

    @Override
    protected void initView() {

        main = (RelativeLayout) findViewById(R.id.main);
        main_middle = (LinearLayout) findViewById(R.id.main_middle);

        initFragment();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initBottom() {

        main_bottom = (LinearLayout) findViewById(R.id.main_bottom);

        View view = LayoutInflater.from(this).inflate(R.layout.bottom_dealer, null);

        main_bottom.addView(view);

        dealer_release = (LinearLayout) findViewById(R.id.dealer_release);
        dealer_order = (LinearLayout) findViewById(R.id.dealer_order);
        dealer_news = (LinearLayout) findViewById(R.id.dealer_news);

    }
}
