package com.lessomall.bidding.activity.bid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.activity.MainActivity;
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
    private TextView title_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_biddinglist);

        if (getIntent().getExtras() != null) {
            String type = (String) getIntent().getExtras().get("type");
            try {
                setType(Integer.parseInt(type));
            } catch (Exception e) {

            }
        }

        fragmentManager = getSupportFragmentManager();

        initTitle();

        initView();

        initData();

        if (Constant.CATEGORY_CACHE_LEVEL1 == null) {
            loadCategory();
        }

    }

    private void initFragment() {

        fragment = new BiddingListFragment();

        fragment.setStatus(getType());

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, fragment);

        fragmentTransaction.commit();

    }

    @Override
    public void refreshList() {

        setTitleTxt();

        if (fragment != null) {
            fragment.setStatus(getType());
            fragment.initData();
        }
    }

    @Override
    public void goToDetail(Bidding bidding) {
        super.goToDetail(bidding);

        BiddingDetailFragment fragment = new BiddingDetailFragment();

        fragment.setBidding(bidding);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        main_title.setVisibility(View.GONE);
    }

    @Override
    public void backToList() {
        super.backToList();
        main_title.setVisibility(View.VISIBLE);
        fragmentManager.popBackStackImmediate();
    }

    @Override
    protected void initTitle() {

        main_title = (RelativeLayout) findViewById(R.id.main_title);
        title_txt = (TextView) main_title.findViewById(R.id.title_txt);

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BiddingListActivity.this, MainActivity.class);
                startActivity(intent, true);
            }
        });

        setTitleTxt();

    }

    @Override
    protected void initView() {


        initFragment();

    }

    @Override
    protected void initData() {

    }

    private void setTitleTxt() {

        switch (getType()) {
            case Constant.APP_BIDDING_STATUS_1:
                title_txt.setText("未提交");
                break;
            case Constant.APP_BIDDING_STATUS_2:
                title_txt.setText("待审核");
                break;
            case Constant.APP_BIDDING_STATUS_3:
                title_txt.setText("竞价中");
                break;
            case Constant.APP_BIDDING_STATUS_4:
                title_txt.setText("已审核");
                break;
            case Constant.APP_BIDDING_STATUS_5:
                title_txt.setText("已确认报价");
                break;
            case Constant.APP_BIDDING_STATUS_6:
                title_txt.setText("已发货");
                break;
            case Constant.APP_BIDDING_STATUS_7:
                title_txt.setText("已收货");
                break;
            default:
                break;
        }

    }

    @Override
    public void onBackPressed() {
        if (fragment.isVisible()) {
            Intent intent = new Intent(BiddingListActivity.this, MainActivity.class);
            startActivity(intent, true);
        } else {
            backToList();
        }
    }

}
