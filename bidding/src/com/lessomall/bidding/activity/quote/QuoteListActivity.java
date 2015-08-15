package com.lessomall.bidding.activity.quote;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.fragment.quote.QuoteListFragment;

/**
 * Created by meisl on 2015/8/12.
 */
public class QuoteListActivity extends BaseActivity {

    private String TAG = "com.lessomall.bidding.activity.quote.QuoteListActivity";

    private FragmentManager fragmentManager;
    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quotelist);

        fragmentManager = getSupportFragmentManager();

        initTitle();

        initBottom();

        initView();

        initData();

    }

    private void initFragment() {

        fragment = new QuoteListFragment();

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
