package com.lessomall.bidding.activity.bid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.fragment.bid.BiddingListFragment;

/**
 * Created by meisl on 2015/7/23.
 */
public class BiddingListActivity extends BaseActivity {

    private String TAG = "com.lessomall.bidding.activity.bid.BiddingListActivity";

    private FragmentManager fragmentManager;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_biddinglist);

        fragmentManager = getSupportFragmentManager();

        initTitle();

        initBottom();

        initView();

        initData();

    }

    private void initFragment() {

        fragment = new BiddingListFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, fragment);

        fragmentTransaction.commit();

    }

    @Override
    protected void initTitle() {


    }

    @Override
    protected void initView() {


        initFragment();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initBottom() {


    }
}
