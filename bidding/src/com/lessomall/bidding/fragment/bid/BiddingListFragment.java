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
import com.lessomall.bidding.model.QuotePrice;
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

    protected void fillData(List<Map<String, String>> data) {

        if (pageno == 1) list.clear();

        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {

                Bidding bidding = new Bidding();

                bidding.setOrderType(data.get(i).get("OrderType"));
                bidding.setId(data.get(i).get("Id"));
                bidding.setBiddingCode(data.get(i).get("BiddingCode"));
                bidding.setBiddingDeadline(data.get(i).get("BiddingDeadline"));
                bidding.setBiddingStatus(data.get(i).get("BiddingStatus"));
                bidding.setBiddingTitle(data.get(i).get("BiddingTitle"));
                bidding.setConsumerCode(data.get(i).get("ConsumerCode"));
                bidding.setCommissionRate(data.get(i).get("CommissionRate"));
                bidding.setConsumerName(data.get(i).get("ConsumerName"));
                bidding.setDeliveryGoodsMode(data.get(i).get("DeliveryGoodsMode"));
                bidding.setDeliveryGoodsPlace(data.get(i).get("DeliveryGoodsPlace"));
                bidding.setDepositPaymentVouchers(data.get(i).get("DepositPaymentVouchers"));
                bidding.setEndInfoSubmittedTime(data.get(i).get("EndInfoSubmittedTime"));
                bidding.setExpectDeliveryDate(data.get(i).get("ExpectDeliveryDate"));
                bidding.setIntentPrice(data.get(i).get("IntentPrice"));
                bidding.setMemo(data.get(i).get("Memo"));
                bidding.setNameType(data.get(i).get("NameType"));
                bidding.setPaymentMode(data.get(i).get("PaymentMode"));
                bidding.setPictureURL(data.get(i).get("PictureURL"));
                bidding.setPrice(data.get(i).get("Price"));
                bidding.setRequiredQuantity(data.get(i).get("RequiredQuantity"));
                bidding.setStartInfoSubmittedTime(data.get(i).get("StartInfoSubmittedTime"));
                bidding.setTaxBillType(data.get(i).get("TaxBillType"));
                bidding.setUnit(data.get(i).get("Unit"));

                List<Map<String, String>> _data = (List<Map<String, String>>) (((Map) data.get(i)).get("datalist"));

                List<QuotePrice> quotePrices = new ArrayList<QuotePrice>();
                if (_data != null && _data.size() > 0) {
                    for (int j = 0; j < _data.size(); j++) {

                        QuotePrice quotePrice = new QuotePrice();

                        quotePrice.setId(_data.get(i).get("Id"));
                        quotePrice.setActualSupplyTotalNumber(_data.get(i).get("ActualSupplyTotalNumber"));
                        quotePrice.setBiddingStatus(_data.get(i).get("BiddingStatus"));
                        quotePrice.setBiddingDetailId(_data.get(i).get("BiddingDetailCountSupplierBidding"));
                        quotePrice.setCountSupplierBidding(_data.get(i).get("CountSupplierBidding"));
                        quotePrice.setCreateTime(_data.get(i).get("CreateTime"));
                        quotePrice.setLastUpdated(_data.get(i).get("LastUpdated"));
                        quotePrice.setMemo(_data.get(i).get("Memo"));
                        quotePrice.setPrice(_data.get(i).get("Price"));
                        quotePrice.setQuotationDate(_data.get(i).get("QuotationDate"));
                        quotePrice.setReturnState(_data.get(i).get("ReturnState"));
                        quotePrice.setSupplierCode(_data.get(i).get("SupplierCode"));
                        quotePrice.setSupplierName(_data.get(i).get("SupplierName"));

                        quotePrices.add(quotePrice);
                    }
                }

                bidding.setQuotePriceList(quotePrices);

                list.add(bidding);
            }
        }
        adapter.notifyDataSetChanged();
    }


    protected Map<String, String> generateParam() {

        Map<String, String> params = activity.generateRequestMap();

        params.put("sessionid", activity.loginUser.getSessionid());
        params.put("type", "1");
        params.put("status", status + "");

        params.put("pageno", pageno + "");
        params.put("pageSize", Constant.PAGE_SIZE + "");

        return params;

    }

    protected void sendRequest(Map<String, String> parems) {

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

                            List<Map<String, String>> datalist = (List<Map<String, String>>) result.get("datalist");

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
