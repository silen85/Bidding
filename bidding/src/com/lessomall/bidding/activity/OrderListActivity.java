package com.lessomall.bidding.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.fragment.OrderListFragment;
import com.lessomall.bidding.fragment.bid.BiddingDetailFragment;
import com.lessomall.bidding.model.Bidding;

/**
 * Created by meisl on 2015/9/6.
 */
public class OrderListActivity extends BaseActivity {

    private String TAG = "com.lessomall.bidding.activity.OrderListActivity";

    private FragmentManager fragmentManager;
    private OrderListFragment fragment;

    private RelativeLayout main_title;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            switch (intent.getAction()) {
                case Constant.FINISH_ACTION_ORDERLIST:
                    OrderListActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orderlist);

        fragmentManager = getSupportFragmentManager();

        initTitle();

        initView();

        initData();

        if (Constant.CATEGORY_CACHE_LEVEL1 == null) {
            loadCategory();
        }

        IntentFilter finishFilter = new IntentFilter(Constant.FINISH_ACTION_ORDERLIST);
        registerReceiver(broadcastReceiver, finishFilter);

    }

    private void initFragment() {

        fragment = new OrderListFragment();

        if (getIntent().getExtras() != null) {

            String txt = (String) getIntent().getExtras().get("txt");
            String begDate = (String) getIntent().getExtras().get("begDate");
            String endDate = (String) getIntent().getExtras().get("endDate");

            fragment.setTxt(txt);
            fragment.setBegDate(begDate);
            fragment.setEndDate(endDate);

        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, fragment);

        fragmentTransaction.commit();

    }

    @Override
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
                Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
                startActivity(intent, true);
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
            Intent intent = new Intent(OrderListActivity.this, MainActivity.class);
            startActivity(intent, true);
        } else {
            backToList();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
        }

    }
}
