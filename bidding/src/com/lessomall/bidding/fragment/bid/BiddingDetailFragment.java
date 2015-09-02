package com.lessomall.bidding.fragment.bid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.ImagePagerActivity;
import com.lessomall.bidding.activity.bid.BiddingListActivity;
import com.lessomall.bidding.adapter.QuoteItemAdapter;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.fragment.CommonBiddingFragment;
import com.lessomall.bidding.model.Bidding;
import com.lessomall.bidding.model.QuotePrice;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meisl on 2015/8/28.
 */
public class BiddingDetailFragment extends CommonBiddingFragment {

    private String TAG = "com.lessomall.bidding.fragment.bid.BiddingDetailFragment";

    private BiddingListActivity activity;

    private Bidding bidding;

    private LinearLayout view;

    private ListView quotepricelist;

    private List<QuotePrice> list = new ArrayList();

    private String bid, qid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_bidding_detail, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initView();

        initData();

    }

    private void initView() {

        quotepricelist = (ListView) view.findViewById(R.id.quotepricelist);

        //竞价单:1:未提交 2:待审核 3:竞价中 4:已审核 5:已确认报价 6:已发货 7:已收货
        switch (getBidding().getBiddingStatusId()) {
            case STATUS_BIDDING_1:
                initView1();
                break;
            case STATUS_BIDDING_2:
                initView2();
                break;
            case STATUS_BIDDING_3:
                initView3();
                break;
            case STATUS_BIDDING_4:
                initView4();
                break;
            case STATUS_BIDDING_5:
                initView5();
                break;
            case STATUS_BIDDING_6:
                initView6();
                break;
            case STATUS_BIDDING_7:
                initView7();
                break;
            default:
                break;
        }


    }

    private void initData() {

        bid = bidding.getId();

        sBeginDate = getBidding().getBiddingDeadline();
        sEndDate = getBidding().getExpectDeliveryDate();

        biddingid.setText(getBidding().getBiddingCode());
        biddingstatus.setText(getBidding().getBiddingStatusName());

        topic_edit.setText(getBidding().getBiddingTitle());
        product_name_edit.setText(getBidding().getNameType());
        product_name_edit.setTag(getBidding().getProductCode());
        product_brand_edit.setText(getBidding().getBrand());

        for (int i = 0; i < Constant.CATEGORY_CACHE_LEVEL1.length; i++) {
            if (Constant.CATEGORY_CACHE_LEVEL1[i].split("-")[0].equals(getBidding().getProductBigCategory())) {
                product_category_txt.setTag(Constant.CATEGORY_CACHE_LEVEL1[i].split("-")[0]);
                product_category_txt.setText(Constant.CATEGORY_CACHE_LEVEL1[i].split("-")[1]);
                break;
            }
        }
        product_num_edit.setText(getBidding().getRequiredQuantity());
        product_unit_edit.setText(getBidding().getUnit());
        product_unit_price_edit.setText(getBidding().getIntentPrice());
        product_comment_edit.setText(getBidding().getMemo2());
        tax_txt.setText(getString(R.string.bidding_tax) + "：" + getBidding().getTaxBillType());
        tax_txt.setTag(getBidding().getTaxBillType());
        expdate_txt.setText(getString(R.string.bidding_expdate) + "：" + sBeginDate + "  -  " + sEndDate);
        expdate_txt.setTag(R.string.TAG_KEY_A, sBeginDate);
        expdate_txt.setTag(R.string.TAG_KEY_B, sEndDate);

        if (getBidding().getDeliveryGoodsPlace() != null && !"".equals(getBidding().getDeliveryGoodsPlace().trim())) {
            delivery_txt.setText(getString(R.string.bidding_delivery) + "：" + getBidding().getDeliveryGoodsMode() + "；地址：" + getBidding().getDeliveryGoodsPlace());
        } else {
            delivery_txt.setText(getString(R.string.bidding_delivery) + "：" + getBidding().getDeliveryGoodsMode());
        }
        delivery_txt.setTag(R.string.TAG_KEY_A, getBidding().getDeliveryGoodsMode());
        delivery_txt.setTag(R.string.TAG_KEY_B, getBidding().getDeliveryGoodsPlace());

        payment_txt.setText(getString(R.string.bidding_payment) + "：" + getBidding().getPaymentMode());
        payment_txt.setTag(getBidding().getPaymentMode());
        certificate_edit.setText(getBidding().getDepositPaymentVouchers());
        other_edit.setText(getBidding().getMemo());

        final String[] urls = getBidding().getPictureURL().split(",");
        if (urls != null && urls.length > 0) {

            for (int i = 0; i < urls.length; i++) {

                if (!"".equals(urls[i].trim())) {

                    ImageSize imageSize = new ImageSize(getResources().getDimensionPixelSize(R.dimen.product_pic_add_width),
                            getResources().getDimensionPixelSize(R.dimen.product_pic_add_height));

                    final ImageView imageView = new ImageView(activity);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.product_pic_add_width),
                            getResources().getDimensionPixelSize(R.dimen.product_pic_add_height));

                    layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.interval_D);
                    layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.interval_D);
                    layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.interval_B);

                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setClickable(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    ImageLoader.getInstance().loadImage(urls[i], imageSize, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            if (bitmap != null) {
                                product_pic.addView(imageView, 0);
                                imageView.setImageBitmap(bitmap);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String[] webUrl = new String[urls.length];
                                        for (int i = 0; i < urls.length; i++) {
                                            webUrl[i] = urls[i];
                                        }

                                        Intent intent = new Intent(activity, ImagePagerActivity.class);
                                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, webUrl);
                                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);

                                        activity.startActivity(intent, false);

                                    }
                                });
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                        }
                    });

                }
            }
        }

        //竞价单:1:未提交 2:待审核 3:竞价中 4:已审核 5:已确认报价 6:已发货 7:已收货
        switch (getBidding().getBiddingStatusId()) {
            case STATUS_BIDDING_1:

                break;
            case STATUS_BIDDING_2:

                break;
            case STATUS_BIDDING_3:
                initData3();
                break;
            case STATUS_BIDDING_4:

                break;
            case STATUS_BIDDING_5:
                initData5();
                break;
            case STATUS_BIDDING_6:
                initData6();
                break;
            case STATUS_BIDDING_7:
                initData7();
                break;
            default:
                break;
        }

    }

    private void initView1() {

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setText("返回");
        button2.setText("保存");
        button3.setText("提交");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrice();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPrice();
            }
        });

    }

    private void initView2() {

        disableInput();

        rule_layout.setVisibility(View.GONE);

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setText("返回");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

    }

    private void initView3() {

        disableInput();

        rule_layout.setVisibility(View.GONE);

        List<QuotePrice> _list = bidding.getQuotePriceList();
        if (_list != null && _list.size() > 0) {

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) quotepricelist.getLayoutParams();
            layoutParams.height = (getResources().getDimensionPixelSize(R.dimen.quote_list_item_height) * _list.size()) +
                    (getResources().getDimensionPixelSize(R.dimen.interval_C) * _list.size());

            quotepricelist.setLayoutParams(layoutParams);
        }

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setVisibility(View.GONE);
        button2.setText("返回");
        button3.setText("确认报价");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensurePrice();
            }
        });

    }

    private void initView4() {

        disableInput();

        rule_layout.setVisibility(View.GONE);

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setText("返回");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

    }

    private void initView5() {

        disableInput();

        rule_layout.setVisibility(View.GONE);

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setText("返回");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

    }

    private void initView6() {

        disableInput();

        rule_layout.setVisibility(View.GONE);

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setVisibility(View.GONE);
        button2.setText("返回");
        button3.setText("确认收货");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ensureReceived();
            }
        });

    }

    private void initView7() {

        disableInput();

        rule_layout.setVisibility(View.GONE);

        TextView button1 = (TextView) view.findViewById(R.id.button1);
        TextView button2 = (TextView) view.findViewById(R.id.button2);
        TextView button3 = (TextView) view.findViewById(R.id.button3);

        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setText("返回");

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.backToList();
            }
        });

    }

    private void initData3() {

        List<QuotePrice> _list = bidding.getQuotePriceList();
        if (_list != null && _list.size() > 0) {

            list = _list;

            QuoteItemAdapter quoteItemAdapter = new QuoteItemAdapter(getActivity(), list);

            quotepricelist.setAdapter(quoteItemAdapter);

            quoteItemAdapter.notifyDataSetChanged();

            quotepricelist.setVisibility(View.VISIBLE);

            quotepricelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    ImageView tick = (ImageView) view.findViewById(R.id.tick);

                    qid = list.get(position).getId();

                    Object tag = parent.getTag();
                    if (tag == null) {
                        parent.setTag(position + "");
                        tick.setSelected(true);
                    } else {

                        int previousPos = Integer.parseInt(tag.toString());

                        parent.getChildAt(previousPos).findViewById(R.id.tick).setSelected(false);

                        if (position != previousPos) {
                            parent.setTag(position + "");
                            tick.setSelected(true);
                        } else {
                            parent.setTag(null);
                            qid = null;
                        }
                    }

                }
            });
        }

    }

    private void initData5() {

        List<QuotePrice> _list = bidding.getQuotePriceList();
        if (_list != null && _list.size() > 0) {

            QuotePrice quotePrice = null;
            for (int i = 0; i < _list.size(); i++) {
                if ("40".equals(_list.get(i).getBiddingStatus())) {
                    quotePrice = _list.get(i);
                    break;
                }
            }

            if (quotePrice != null) {

                qid = quotePrice.getId();

                list.add(quotePrice);

                QuoteItemAdapter quoteItemAdapter = new QuoteItemAdapter(getActivity(), list);

                quotepricelist.setAdapter(quoteItemAdapter);

                quoteItemAdapter.notifyDataSetChanged();

                quotepricelist.setVisibility(View.VISIBLE);

            }

        }

    }

    private void initData6() {

        List<QuotePrice> _list = bidding.getQuotePriceList();
        if (_list != null && _list.size() > 0) {

            QuotePrice quotePrice = null;
            for (int i = 0; i < _list.size(); i++) {
                if ("50".equals(_list.get(i).getBiddingStatus())) {
                    quotePrice = _list.get(i);
                    break;
                }
            }

            if (quotePrice != null) {

                qid = quotePrice.getId();

                list.add(quotePrice);

                QuoteItemAdapter quoteItemAdapter = new QuoteItemAdapter(getActivity(), list);

                quotepricelist.setAdapter(quoteItemAdapter);

                quoteItemAdapter.notifyDataSetChanged();

                quotepricelist.setVisibility(View.VISIBLE);

            }

        }

    }

    private void initData7() {

        List<QuotePrice> _list = bidding.getQuotePriceList();
        if (_list != null && _list.size() > 0) {

            QuotePrice quotePrice = null;
            for (int i = 0; i < _list.size(); i++) {
                if ("60".equals(_list.get(i).getBiddingStatus())) {
                    quotePrice = _list.get(i);
                    break;
                }
            }

            if (quotePrice != null) {

                qid = quotePrice.getId();

                list.add(quotePrice);

                QuoteItemAdapter quoteItemAdapter = new QuoteItemAdapter(getActivity(), list);

                quotepricelist.setAdapter(quoteItemAdapter);

                quoteItemAdapter.notifyDataSetChanged();

                quotepricelist.setVisibility(View.VISIBLE);

            }

        }

    }

    private void ensureReceived() {

        Map params = Tools.generateRequestMap();

        params.put("sessionid", activity.loginUser.getSessionid());
        params.put("qid", qid);

        RequestParams requestParams = new RequestParams(params);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                activity.loading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Toast.makeText(activity, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {
                    try {
                        Map result = Tools.json2Map(responseString);

                        String recode = (String) result.get("recode");
                        String msg = (String) result.get("msg");

                        if (Constant.RECODE_SUCCESS.equals(recode)) {
                            Toast.makeText(activity, getString(R.string.tips_success_receive), Toast.LENGTH_SHORT).show();
                            activity.backToList();
                            activity.refreshList();
                        } else {
                            activity.tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(activity, getString(R.string.RECODE_ERROR_OTHERS), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                activity.disLoading();
            }

        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(activity, Constant.DEALER_CONFIRM_STORE, requestParams, asyncHttpResponseHandler);

    }

    private void ensurePrice() {

        Map params = Tools.generateRequestMap();

        params.put("sessionid", activity.loginUser.getSessionid());
        params.put("bid", bid);
        params.put("qid", qid);

        RequestParams requestParams = new RequestParams(params);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                activity.loading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Toast.makeText(activity, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {
                    try {
                        Map result = Tools.json2Map(responseString);

                        String recode = (String) result.get("recode");
                        String msg = (String) result.get("msg");

                        if (Constant.RECODE_SUCCESS.equals(recode)) {
                            Toast.makeText(activity, getString(R.string.tips_success_receive), Toast.LENGTH_SHORT).show();
                            activity.backToList();
                            activity.refreshList();
                        } else {
                            activity.tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(activity, getString(R.string.RECODE_ERROR_OTHERS), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                activity.disLoading();
            }

        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(activity, Constant.DEALER_CONFIRM_PRICE, requestParams, asyncHttpResponseHandler);

    }

    private void savePrice() {

    }

    private void submitPrice() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BiddingListActivity) activity;
    }


    public Bidding getBidding() {
        return bidding;
    }

    public void setBidding(Bidding bidding) {
        this.bidding = bidding;
    }

}
