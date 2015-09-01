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


    public QuoteItemAdapter(Context context, List<QuotePrice> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.list = list;
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
        supplier_name.setText(quotePrice.getSupplierName());
        actual_num.setText(quotePrice.getActualSupplyTotalNumber());
        supplier_price.setText(quotePrice.getPrice());

        try {
            float _total = (Float.parseFloat(quotePrice.getActualSupplyTotalNumber()) * Float.parseFloat(quotePrice.getPrice()));
            total.setText(_total + "");
        } catch (Exception e) {

        }


        supplier_comment.setText(quotePrice.getMemo());

        if ("40".equals(quotePrice.getBiddingStatus()) ||
                "50".equals(quotePrice.getBiddingStatus()) ||
                "60".equals(quotePrice.getBiddingStatus())) {
            tick.setSelected(true);
        } else {
            tick.setSelected(false);
        }

        return view;
    }
}
