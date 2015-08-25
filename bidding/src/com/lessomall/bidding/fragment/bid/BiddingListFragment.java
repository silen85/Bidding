package com.lessomall.bidding.fragment.bid;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.bid.BiddingListActivity;
import com.lessomall.bidding.adapter.BiddingAdapter;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.model.Bidding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meisl on 2015/7/24.
 */
public class BiddingListFragment extends Fragment {

    private String TAG = "com.lessomall.bidding.fragment.bid.BiddingListFragment";

    private BiddingListActivity activity;

    private int pageno = 1;

    private int status = 0;

    private LinearLayout view;

    private List<Bidding> list = new ArrayList<Bidding>();

    private BiddingAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_biddinglist, null);

        initView();

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
    }


    private void initView() {


        ListView biddinglist = (ListView) view.findViewById(R.id.biddinglist);

        adapter = new BiddingAdapter(activity, list);
        biddinglist.setAdapter(adapter);

    }

    private void initData() {

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
    }


    private Map<String, String> generateParam() {

        Map<String, String> params = activity.generateRequestMap();

        params.put("sessionid", activity.loginUser.getSessionid());
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

                        if (Constant.RECODE_SUCCESS.equals(recode)) {

                            List<Map> datalist = (List<Map>) result.get("datalist");

                            if (datalist != null && datalist.size() > 0) {
                                fillData(datalist);
                            } else {

                            }
                        } else {
                            activity.tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(activity, getResources().getString(R.string.no_data_tips), Toast.LENGTH_SHORT).show();
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
        this.activity = (BiddingListActivity) activity;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
