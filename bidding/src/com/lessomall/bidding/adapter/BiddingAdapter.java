package com.lessomall.bidding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.model.Bidding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meisl on 2015/8/24.
 */
public class BiddingAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Bidding> list = new ArrayList<Bidding>();

    public BiddingAdapter(Context context, List<Bidding> list) {
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

            Bidding bidding = (Bidding) getItem(position);

            if ("0".equals(bidding.getOrderType()))
                view = layoutInflater.inflate(R.layout.item_bidding, null);
            else
                view = layoutInflater.inflate(R.layout.item_quote, null);
        }

        this.writeData(view, position);

        return view;
    }

    private void writeData(View view, int position) {

        Bidding bidding = (Bidding) getItem(position);

        if ("1".equals(bidding.getOrderType())) {


        } else {

            TextView biddingid = (TextView) view.findViewById(R.id.biddingid);
            TextView biddingstatus = (TextView) view.findViewById(R.id.biddingstatus);
            TextView topic = (TextView) view.findViewById(R.id.topic);
            TextView brand = (TextView) view.findViewById(R.id.brand);
            TextView num = (TextView) view.findViewById(R.id.num);
            TextView date = (TextView) view.findViewById(R.id.date);

            ImageView pictures = (ImageView) view.findViewById(R.id.pictures);

            biddingid.setText(bidding.getBiddingCode());
            biddingstatus.setText(bidding.getBiddingStatusName());
            topic.setText(bidding.getBiddingTitle());
            brand.setText(bidding.getBrand() + "  " + bidding.getNameType());
            num.setText(bidding.getRequiredQuantity() + " " + bidding.getUnit());
            date.setText(bidding.getBiddingDeadline() + "-" + bidding.getExpectDeliveryDate());

        }

    }


}
