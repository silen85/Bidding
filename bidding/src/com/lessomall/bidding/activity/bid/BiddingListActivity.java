package com.lessomall.bidding.activity.bid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

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

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void initView() {


        initFragment();

    }

    @Override
    protected void initData() {

    }

}
