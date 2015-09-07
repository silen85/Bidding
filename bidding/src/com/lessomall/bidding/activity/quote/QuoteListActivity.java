package com.lessomall.bidding.activity.quote;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.fragment.quote.QuoteListFragment;

/**
 * Created by meisl on 2015/8/12.
 */
public class QuoteListActivity extends BaseActivity {

    private String TAG = "com.lessomall.bidding.activity.quote.QuoteListActivity";

    private FragmentManager fragmentManager;
    private QuoteListFragment fragment;

    private RelativeLayout main_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quotelist);

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

        fragment = new QuoteListFragment();

        fragment.setStatus(_type);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, fragment);

        fragmentTransaction.commit();

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
    public void refreshList() {
        if (fragment != null)
            fragment.initData();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
