package com.lessomall.bidding.activity.bid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.lessomall.bidding.R;
import com.lessomall.bidding.adapter.BiddingAdapter;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.model.Bidding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meisl on 2015/8/27.
 */
public class TestActivity extends TestBaseActivity {

    private String TAG = "com.lessomall.bidding.activity.bid.TestActivity";

    private int pageno = 1;

    private int status = 0;

    private List<Bidding> list = new ArrayList();

    private PullToRefreshListView mPullRefreshListView;

    private ListView biddinglist;

    private BiddingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_biddinglist);

        mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.biddinglist);

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(TestActivity.this, System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                // Do work to refresh the list here.
                pageno++;
                sendRequest(generateParam());
            }
        });

        mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

            @Override
            public void onLastItemVisible() {

            }
        });


        biddinglist = mPullRefreshListView.getRefreshableView();

        /**
         * Add Sound Event Listener
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(TestActivity.this);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);

        biddinglist.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));

        adapter = new BiddingAdapter(TestActivity.this, list);
        biddinglist.setAdapter(adapter);


        pageno = 1;
        sendRequest(generateParam());

    }

    private void fillData(List<Map> data) {

        if (pageno == 1) list.clear();

        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {

                Bidding bidding = new Bidding();

                bidding.map2Bidding(data.get(i));

                list.add(bidding);
            }
        }

        adapter.notifyDataSetChanged();

        mPullRefreshListView.onRefreshComplete();
    }


    private Map<String, String> generateParam() {

        Map<String, String> params = generateRequestMap();

        params.put("sessionid", loginUser.getSessionid());
        params.put("type", "1");
        params.put("status", getStatus() + "");

        params.put("pageno", pageno + "");
        params.put("pageSize", Constant.PAGE_SIZE + "");

        return params;

    }

    private void sendRequest(Map<String, String> parems) {

        RequestParams requestParams = new RequestParams(parems);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(TestActivity.this, Constant.BIDDING_LIST, requestParams, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                loading();
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

                if (statusCode == 200) {
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
                disLoading();
            }
        });

    }


    private final int HANDLER_DATA = 1;
    private final int HANDLER_NETWORK_ERR = 0;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case HANDLER_DATA:

                    String json = message.getData().getString("json");
                    try {
                        Map result = Tools.json2Map(json);

                        String recode = (String) result.get("recode");
                        String msg = (String) result.get("msg");

                        if (Constant.RECODE_SUCCESS.equals(recode)) {

                            List<Map> datalist = (List<Map>) result.get("datalist");

                            if (datalist != null && datalist.size() > 0) {
                                fillData(datalist);
                            } else {

                            }
                        } else {
                            TestActivity.this.tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(TestActivity.this, getResources().getString(R.string.no_data_tips), Toast.LENGTH_SHORT).show();
                    }

                    break;
                case HANDLER_NETWORK_ERR:
                    Toast.makeText(TestActivity.this, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(message);
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
