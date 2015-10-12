package com.lessomall.bidding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.model.QuotePrice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meisl on 2015/8/31.
 */
public class QuoteItemAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<QuotePrice> list = new ArrayList();

    private float commissionRate = 0;

    //0:竞价单；1：报价单
    private String orderType = "0";


    public QuoteItemAdapter(Context context, List<QuotePrice> list, String orderType, String commissionRate) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.list = list;
        this.orderType = orderType;

        try {
            this.commissionRate = Float.parseFloat(commissionRate);
            if (this.commissionRate < 0) {
                this.commissionRate = 0;
            }
        } catch (Exception e) {
            this.commissionRate = 0;
        }
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_quoteprice, null);
        }

        TextView supplier_name = (TextView) view.findViewById(R.id.supplier_name);
        TextView actual_num = (TextView) view.findViewById(R.id.actual_num);
        TextView supplier_price = (TextView) view.findViewById(R.id.supplier_price);
        TextView total = (TextView) view.findViewById(R.id.total);
        TextView supplier_comment = (TextView) view.findViewById(R.id.supplier_comment);

        ImageView tick = (ImageView) view.findViewById(R.id.tick);

        QuotePrice quotePrice = (QuotePrice) getItem(position);
        //    supplier_name.setText(quotePrice.getSupplierName());

        actual_num.setText(quotePrice.getActualSupplyTotalNumber());

        supplier_comment.setText(quotePrice.getMemo());

        if ("0".equals(orderType)) {

            supplier_name.setText("报价" + (position + 1));

            try {

                float price = Float.parseFloat(quotePrice.getPrice());
                price += price * commissionRate / 100;
                supplier_price.setText(price + "");

                float _total = (Float.parseFloat(quotePrice.getActualSupplyTotalNumber()) * Float.parseFloat(quotePrice.getPrice()));
                _total += _total * commissionRate / 100;
                total.setText(_total + "");
            } catch (Exception e) {

            }

            if ("40".equals(quotePrice.getBiddingStatus()) ||
                    "50".equals(quotePrice.getBiddingStatus()) ||
                    "60".equals(quotePrice.getBiddingStatus())) {
                tick.setBackgroundResource(R.drawable.agree_x);
                tick.setSelected(true);
            } else if ("10".equals(quotePrice.getBiddingStatus())) {
                tick.setBackgroundResource(R.mipmap.disagree_s);
            } else {
                tick.setBackgroundResource(R.drawable.agree_x);
                tick.setSelected(false);
            }


        } else {

            supplier_name.setText("我的报价");

            try {

                float price = Float.parseFloat(quotePrice.getPrice());
                supplier_price.setText(price + "");

                float _total = (Float.parseFloat(quotePrice.getActualSupplyTotalNumber()) * Float.parseFloat(quotePrice.getPrice()));
                total.setText(_total + "");
            } catch (Exception e) {

            }

            if ("10".equals(quotePrice.getBiddingStatus())) {
                tick.setBackgroundResource(R.mipmap.disagree_s);
            } else if ("20".equals(quotePrice.getBiddingStatus()) || "30".equals(quotePrice.getBiddingStatus())) {
                tick.setVisibility(View.GONE);
            } else {
                tick.setBackgroundResource(R.drawable.agree_x);
                tick.setSelected(true);
            }
        }

        return view;
    }
}
