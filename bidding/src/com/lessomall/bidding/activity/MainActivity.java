package com.lessomall.bidding.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/8/12.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "com.lessomall.bidding.activity.MainActivity";

    private EditText searcher_text;
    private LinearLayout searcher_other;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initTitle();

        initView();

        initBottom();

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

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initBottom() {

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

                break;
            default:

                break;
        }

    }
}
