package com.lessomall.bidding.activity.quote;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    private int _type = 0;

    private RelativeLayout main_title;
    private TextView title_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quotelist);

        if (getIntent().getExtras() != null) {
            String type = (String) getIntent().getExtras().get("type");
            try {
                _type = Integer.parseInt(type);
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

        fragment = new QuoteListFragment();

        fragment.setStatus(_type);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, fragment);

        fragmentTransaction.commit();

    }

    @Override
    protected void initTitle() {

        main_title = (RelativeLayout) findViewById(R.id.main_title);
        title_txt = (TextView) main_title.findViewById(R.id.title_txt);

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    @Override
    public void refreshList() {
        if (fragment != null)
            fragment.initData();
    }

    private void setTitleTxt() {

        switch (_type) {
            case Constant.APP_QUOTE_STATUS_1:
                title_txt.setText("待报价");
                break;
            case Constant.APP_QUOTE_STATUS_2:
                title_txt.setText("已报价");
                break;
            case Constant.APP_QUOTE_STATUS_3:
                title_txt.setText("被退回");
                break;
            case Constant.APP_QUOTE_STATUS_4:
                title_txt.setText("待发货");
                break;
            case Constant.APP_QUOTE_STATUS_5:
                title_txt.setText("已发货");
                break;
            case Constant.APP_QUOTE_STATUS_6:
                title_txt.setText("已确认收货");
                break;
            default:
                break;
        }

    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
