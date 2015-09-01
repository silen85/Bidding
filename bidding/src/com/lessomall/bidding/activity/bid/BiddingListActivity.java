package com.lessomall.bidding.activity.bid;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.fragment.bid.BiddingDetailFragment;
import com.lessomall.bidding.fragment.bid.BiddingListFragment;
import com.lessomall.bidding.model.Bidding;

/**
 * Created by meisl on 2015/7/23.
 */
public class BiddingListActivity extends BaseActivity {

    private String TAG = "com.lessomall.bidding.activity.bid.BiddingListActivity";

    private FragmentManager fragmentManager;
    private BiddingListFragment fragment;

    private RelativeLayout main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_biddinglist);

        fragmentManager = getSupportFragmentManager();

        initTitle();

        initView();

        initData();

        if (Constant.CATEGORY_CACHE_LEVEL1 == null) {
            loadCategory();
        }

    }

    private void initFragment() {

        int _type = 0;
        if (getIntent().getExtras() != null) {
            String type = (String) getIntent().getExtras().get("type");
            try {
                _type = Integer.parseInt(type);
            } catch (Exception e) {

            }
        }

        fragment = new BiddingListFragment();

        fragment.setStatus(_type);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, fragment);

        fragmentTransaction.commit();

    }

    public void refreshList() {

        if (fragment != null)
            fragment.initData();

    }

    public void goToDetail(Bidding bidding) {

        BiddingDetailFragment fragment = new BiddingDetailFragment();

        fragment.setBidding(bidding);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        main_title.setVisibility(View.GONE);

    }

    public void backToList() {
        main_title.setVisibility(View.VISIBLE);
        fragmentManager.popBackStackImmediate();
    }

    @Override
    protected void initTitle() {

        main_title = (RelativeLayout) findViewById(R.id.main_title);

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

    @Override
    public void onBackPressed() {
        if (fragment.isVisible()) {
            finish();
        } else {
            backToList();
        }
    }
}
