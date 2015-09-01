package com.lessomall.bidding.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.bid.AddBiddingActivity;
import com.lessomall.bidding.activity.bid.BiddingListActivity;
import com.lessomall.bidding.activity.quote.QuoteListActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.common.UpdateManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by meisl on 2015/8/12.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener, Handler.Callback {

    private String TAG = "com.lessomall.bidding.activity.MainActivity";

    private EditText searcher_text;
    private LinearLayout searcher_other;

    private LinearLayout LinearLayout_b1, LinearLayout_b2, LinearLayout_b3, LinearLayout_b4, LinearLayout_b5, LinearLayout_b6, LinearLayout_b7,
            LinearLayout_q1, LinearLayout_q2, LinearLayout_q3, LinearLayout_q4, LinearLayout_q5, LinearLayout_q6;

    private ProgressBar loading_b1, loading_b2, loading_b3, loading_b4, loading_b5, loading_b6, loading_b7, loading_q1, loading_q2, loading_q3, loading_q4,
            loading_q5, loading_q6;
    private TextView text_b1, text_b2, text_b3, text_b4, text_b5, text_b6, text_b7, text_q1, text_q2, text_q3, text_q4, text_q5, text_q6;

    private Handler mHandler;

    BroadcastReceiver finishReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            MainActivity.this.finish();
        }
    };

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

        try {
            UpdateManager mUpdateManager = new UpdateManager(this);
            mUpdateManager.sendUpdateRequest();
        } catch (Exception e) {
        }

        mHandler = new Handler(this);

        if (Constant.CATEGORY_CACHE_LEVEL1 == null) {
            loadCategory();
        }

        IntentFilter finishFilter = new IntentFilter(Constant.FINISH_ACTION);
        registerReceiver(finishReceiver, finishFilter);

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

        LinearLayout_b1 = (LinearLayout) findViewById(R.id.LinearLayout_b1);
        LinearLayout_b2 = (LinearLayout) findViewById(R.id.LinearLayout_b2);
        LinearLayout_b3 = (LinearLayout) findViewById(R.id.LinearLayout_b3);
        LinearLayout_b4 = (LinearLayout) findViewById(R.id.LinearLayout_b4);
        LinearLayout_b5 = (LinearLayout) findViewById(R.id.LinearLayout_b5);
        LinearLayout_b6 = (LinearLayout) findViewById(R.id.LinearLayout_b6);
        LinearLayout_b7 = (LinearLayout) findViewById(R.id.LinearLayout_b7);
        LinearLayout_q1 = (LinearLayout) findViewById(R.id.LinearLayout_q1);
        LinearLayout_q2 = (LinearLayout) findViewById(R.id.LinearLayout_q2);
        LinearLayout_q3 = (LinearLayout) findViewById(R.id.LinearLayout_q3);
        LinearLayout_q4 = (LinearLayout) findViewById(R.id.LinearLayout_q4);
        LinearLayout_q5 = (LinearLayout) findViewById(R.id.LinearLayout_q5);
        LinearLayout_q6 = (LinearLayout) findViewById(R.id.LinearLayout_q6);

        LinearLayout_b1.setOnClickListener(this);
        LinearLayout_b2.setOnClickListener(this);
        LinearLayout_b3.setOnClickListener(this);
        LinearLayout_b4.setOnClickListener(this);
        LinearLayout_b5.setOnClickListener(this);
        LinearLayout_b6.setOnClickListener(this);
        LinearLayout_b7.setOnClickListener(this);
        LinearLayout_q1.setOnClickListener(this);
        LinearLayout_q2.setOnClickListener(this);
        LinearLayout_q3.setOnClickListener(this);
        LinearLayout_q4.setOnClickListener(this);
        LinearLayout_q5.setOnClickListener(this);
        LinearLayout_q6.setOnClickListener(this);


        loading_b1 = (ProgressBar) findViewById(R.id.loading_b1);
        loading_b2 = (ProgressBar) findViewById(R.id.loading_b2);
        loading_b3 = (ProgressBar) findViewById(R.id.loading_b3);
        loading_b4 = (ProgressBar) findViewById(R.id.loading_b4);
        loading_b5 = (ProgressBar) findViewById(R.id.loading_b5);
        loading_b6 = (ProgressBar) findViewById(R.id.loading_b6);
        loading_b7 = (ProgressBar) findViewById(R.id.loading_b7);
        loading_q1 = (ProgressBar) findViewById(R.id.loading_q1);
        loading_q2 = (ProgressBar) findViewById(R.id.loading_q2);
        loading_q3 = (ProgressBar) findViewById(R.id.loading_q3);
        loading_q4 = (ProgressBar) findViewById(R.id.loading_q4);
        loading_q5 = (ProgressBar) findViewById(R.id.loading_q5);
        loading_q6 = (ProgressBar) findViewById(R.id.loading_q6);


        text_b1 = (TextView) findViewById(R.id.text_b1);
        text_b2 = (TextView) findViewById(R.id.text_b2);
        text_b3 = (TextView) findViewById(R.id.text_b3);
        text_b4 = (TextView) findViewById(R.id.text_b4);
        text_b5 = (TextView) findViewById(R.id.text_b5);
        text_b6 = (TextView) findViewById(R.id.text_b6);
        text_b7 = (TextView) findViewById(R.id.text_b7);
        text_q1 = (TextView) findViewById(R.id.text_q1);
        text_q2 = (TextView) findViewById(R.id.text_q2);
        text_q3 = (TextView) findViewById(R.id.text_q3);
        text_q4 = (TextView) findViewById(R.id.text_q4);
        text_q5 = (TextView) findViewById(R.id.text_q5);
        text_q6 = (TextView) findViewById(R.id.text_q6);


    }

    @Override
    protected void initData() {

        loadMainCount();

    }

    private void loadMainCount() {

        Map params = generateRequestMap();

        params.put("sessionid", loginUser.getSessionid());

        RequestParams requestParams = new RequestParams(params);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Message message = mHandler.obtainMessage();
                message.what = HANDLER_NETWORK_ERR;
                message.sendToTarget();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {
                    Message message = mHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("json", responseString);
                    message.what = HANDLER_DATA;
                    message.setData(bundle);
                    message.sendToTarget();
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(this, Constant.URL_MAINCOUNT, requestParams, asyncHttpResponseHandler);

    }

    private void handleMainCount(JSONObject data) throws Exception {

        String j_noSubmit = (String) data.get("j_noSubmit");
        String j_pendingAudit = (String) data.get("j_pendingAudit");
        String j_audit = (String) data.get("j_audit");
        String j_bidding = (String) data.get("j_bidding");
        String j_quotation = (String) data.get("j_quotation");
        String j_delivered = (String) data.get("j_delivered");
        String j_received = (String) data.get("j_received");
        String b_pendingQuote = (String) data.get("b_pendingQuote");
        String b_quote = (String) data.get("b_quote");
        String b_return = (String) data.get("b_return");
        String b_confirmReceipt = (String) data.get("b_confirmReceipt");
        String b_pendingDelivery = (String) data.get("b_pendingDelivery");
        String b_delivery = (String) data.get("b_delivery");


        loading_b1.setVisibility(View.GONE);
        text_b1.setText(j_noSubmit);
        text_b1.setVisibility(View.VISIBLE);

        loading_b2.setVisibility(View.GONE);
        text_b2.setText(j_pendingAudit);
        text_b2.setVisibility(View.VISIBLE);

        loading_b3.setVisibility(View.GONE);
        text_b3.setText(j_bidding);
        text_b3.setVisibility(View.VISIBLE);

        loading_b4.setVisibility(View.GONE);
        text_b4.setText(j_audit);
        text_b4.setVisibility(View.VISIBLE);

        loading_b5.setVisibility(View.GONE);
        text_b5.setText(j_quotation);
        text_b5.setVisibility(View.VISIBLE);

        loading_b6.setVisibility(View.GONE);
        text_b6.setText(j_delivered);
        text_b6.setVisibility(View.VISIBLE);

        loading_b7.setVisibility(View.GONE);
        text_b7.setText(j_received);
        text_b7.setVisibility(View.VISIBLE);


        loading_q1.setVisibility(View.GONE);
        text_q1.setText(b_pendingQuote);
        text_q1.setVisibility(View.VISIBLE);

        loading_q2.setVisibility(View.GONE);
        text_q2.setText(b_quote);
        text_q2.setVisibility(View.VISIBLE);

        loading_q3.setVisibility(View.GONE);
        text_q3.setText(b_return);
        text_q3.setVisibility(View.VISIBLE);

        loading_q4.setVisibility(View.GONE);
        text_q4.setText(b_pendingDelivery);
        text_q4.setVisibility(View.VISIBLE);

        loading_q5.setVisibility(View.GONE);
        text_q5.setText(b_delivery);
        text_q5.setVisibility(View.VISIBLE);

        loading_q6.setVisibility(View.GONE);
        text_q6.setText(b_confirmReceipt);
        text_q6.setVisibility(View.VISIBLE);


    }

    @Override
    public boolean handleMessage(Message message) {
        if (message.what == HANDLER_DATA) {
            String json = message.getData().getString("json");
            try {
                Map result = Tools.json2Map(json);

                String recode = (String) result.get("recode");
                String msg = (String) result.get("msg");

                if (Constant.RECODE_SUCCESS.equals(recode)) {
                    JSONObject info = (JSONObject) result.get("info");
                    if (info != null) {
                        handleMainCount(info);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.no_data_tips), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tipsOutput(recode, msg);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
                Toast.makeText(this, getResources().getString(R.string.no_data_tips), Toast.LENGTH_SHORT).show();
            }
        } else if (message.what == HANDLER_NETWORK_ERR) {
            Toast.makeText(this, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
        }

        return true;
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
            case R.id.LinearLayout_b1:
            case R.id.LinearLayout_b2:
            case R.id.LinearLayout_b3:
            case R.id.LinearLayout_b4:
            case R.id.LinearLayout_b5:
            case R.id.LinearLayout_b6:
            case R.id.LinearLayout_b7:
                String tag = (String) v.getTag();
                Intent intent = new Intent(MainActivity.this, BiddingListActivity.class);
                intent.putExtra("type", tag);
                startActivity(intent, false);
                break;
            case R.id.quotelist_all:
                startActivity(new Intent(MainActivity.this, QuoteListActivity.class), false);
                break;
            case R.id.LinearLayout_q1:
            case R.id.LinearLayout_q2:
            case R.id.LinearLayout_q3:
            case R.id.LinearLayout_q4:
            case R.id.LinearLayout_q5:
            case R.id.LinearLayout_q6:
                String _tag = (String) v.getTag();
                Intent _intent = new Intent(MainActivity.this, QuoteListActivity.class);
                _intent.putExtra("type", _tag);
                startActivity(_intent, false);
                break;
            default:
                break;
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            unregisterReceiver(finishReceiver);
        } catch (Exception e) {
        }

    }
}
