package com.lessomall.bidding.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
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
 * Created by meisl on 2015/9/6.
 */
public class OrderListFragment extends PullToRefreshListFragment {

    private String TAG = "com.lessomall.bidding.fragment.OrderListFragment";

    private BaseActivity activity;

    private int pageno = 1;

    private int totalPage = 1;

    private List<Bidding> list = new ArrayList();

    private PullToRefreshListView mPullRefreshListView;

    private ListView biddinglist;

    private BiddingAdapter adapter;

    private String txt, begDate, endDate;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        initData();
    }


    private void initView() {

        mPullRefreshListView = getPullToRefreshListView();

        mPullRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        mPullRefreshListView.setScrollAnimationInterpolator(new LinearInterpolator());

        // Set a listener to be invoked when the list should be refreshed.
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                if (pageno < totalPage) {

                    String label = DateUtils.formatDateTime(activity, System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                    pageno++;
                    sendRequest(generateParam());
                } else {
                    lastData();
                }
            }
        });

        biddinglist = mPullRefreshListView.getRefreshableView();

        biddinglist.setEmptyView(LayoutInflater.from(activity).inflate(R.layout.empty, null));

        biddinglist.setDivider(null);
        biddinglist.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.interval_C));

        biddinglist.setVerticalScrollBarEnabled(false);
        biddinglist.setHorizontalScrollBarEnabled(false);
        biddinglist.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, true));

        biddinglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bidding bidding = list.get(position - 1);

                activity.goToDetail(bidding);

            }
        });

        adapter = new BiddingAdapter(activity, list);
        biddinglist.setAdapter(adapter);

        setListShown(true);

    }

    public void initData() {

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

    private void lastData() {

        adapter.notifyDataSetChanged();
        biddinglist.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullRefreshListView.onRefreshComplete();
            }
        }, 1000);

        if (list.size() > 0) {
            Toast.makeText(activity, getString(R.string.last_data_tips), Toast.LENGTH_SHORT).show();
        }

    }


    private Map<String, String> generateParam() {

        Map<String, String> params = Tools.generateRequestMap();

        params.put("sessionid", activity.loginUser.getSessionid());
        params.put("customCode", activity.loginUser.getCustomCode());
        params.put("type", "0");

        if (txt != null && !"".equals(txt.trim())) {
            params.put("txt", txt);
        }

        if (begDate != null && !"".equals(begDate.trim())) {
            params.put("begDate", begDate);
        }

        if (endDate != null && !"".equals(endDate.trim())) {
            params.put("endDate", endDate);
        }

        params.put("pageno", pageno + "");
        params.put("pageSize", Constant.PAGE_SIZE + "");

        return params;

    }

    private void sendRequest(Map<String, String> parems) {

        RequestParams requestParams = new RequestParams(parems);

        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(activity, Constant.BIDDING_LIST, requestParams, new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                activity.loading();
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
                activity.disLoading();
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
                        String totalpage = (String) result.get("totalpage");

                        if (totalpage != null && !"".equals(totalpage.trim()))
                            totalPage = Integer.parseInt(totalpage);

                        if (Constant.RECODE_SUCCESS.equals(recode)) {

                            List<Map> datalist = (List<Map>) result.get("datalist");

                            if (datalist != null && datalist.size() > 0) {
                                fillData(datalist);
                            } else {
                                lastData();
                            }
                        } else {
                            activity.tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(activity, getString(R.string.no_data_tips), Toast.LENGTH_SHORT).show();
                    }

                    break;
                case HANDLER_NETWORK_ERR:
                    Toast.makeText(activity, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(message);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseActivity) activity;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getBegDate() {
        return begDate;
    }

    public void setBegDate(String begDate) {
        this.begDate = begDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}