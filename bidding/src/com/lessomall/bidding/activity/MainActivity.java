package com.lessomall.bidding.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.bid.AddBiddingActivity;
import com.lessomall.bidding.activity.bid.BiddingListActivity;
import com.lessomall.bidding.activity.quote.QuoteListActivity;
import com.lessomall.bidding.common.Constant;

/**
 * Created by meisl on 2015/8/12.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "com.lessomall.bidding.activity.MainActivity";

    private EditText searcher_text;
    private LinearLayout searcher_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (Constant.DEVELOPER_MODE) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDialog()
                    .build());
        }

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initTitle();

        initView();

        initData();

    }

    @Override
    protected void initTitle() {

        LinearLayout searcher_date = (LinearLayout) findViewById(R.id.searcher_date);
        searcher_date.setOnClickListener(this);

        ImageView searcher_icon = (ImageView) findViewById(R.id.searcher_icon);
        searcher_icon.setOnClickListener(this);

        searcher_text = (EditText) findViewById(R.id.searcher_text);

        searcher_other = (LinearLayout) findViewById(R.id.searcher_other);
        searcher_other.setVisibility(View.VISIBLE);
        searcher_other.setOnClickListener(this);

    }

    @Override
    protected void initView() {

        LinearLayout addbidding = (LinearLayout) findViewById(R.id.addbidding);
        addbidding.setOnClickListener(this);

        LinearLayout biddinglist_all = (LinearLayout) findViewById(R.id.biddinglist_all);
        biddinglist_all.setOnClickListener(this);


        LinearLayout quotelist_all = (LinearLayout) findViewById(R.id.quotelist_all);
        quotelist_all.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.searcher_date:
                showTimerDialog();
                break;
            case R.id.searcher_icon:

                break;
            case R.id.searcher_other:
                startActivity(new Intent(MainActivity.this, OtherActivity.class), false);
                break;
            case R.id.addbidding:
                startActivity(new Intent(MainActivity.this, AddBiddingActivity.class), false);
                break;
            case R.id.biddinglist_all:
                startActivity(new Intent(MainActivity.this, BiddingListActivity.class), false);
                break;


            case R.id.quotelist_all:
                startActivity(new Intent(MainActivity.this, QuoteListActivity.class), false);
                break;
            default:

                break;
        }

    }
}
