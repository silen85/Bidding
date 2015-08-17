package com.lessomall.bidding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lessomall.bidding.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by meisl on 2015/8/15.
 */
public class TwoGridAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Map<String, String>> list = new ArrayList<Map<String, String>>();

    public TwoGridAdapter(Context context, List<Map<String, String>> listobject) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.list = listobject;
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_2grid, null);
        }

        this.writeData(view, i);

        return view;
    }


    private void writeData(View listviewitem, int position) {

        Map<String, String> map = (Map<String, String>) getItem(position);

        TextView colum1 = (TextView) listviewitem.findViewById(R.id.colum1);
        TextView colum2 = (TextView) listviewitem.findViewById(R.id.colum2);

        colum1.setText(map.get(colum1.getTag()));
        colum2.setText(map.get(colum2.getTag()));

        if (position % 2 == 0) {
            colum1.setBackgroundColor(context.getResources().getColor(R.color.MALL_C8));
            colum2.setBackgroundResource(R.drawable.two_grid_bg2);
            //amount.setBackground(activity.getResources().getDrawable(R.drawable.border_left1));
        } else {
            colum1.setBackgroundColor(context.getResources().getColor(R.color.MALL_C7));
            colum2.setBackgroundResource(R.drawable.two_grid_bg1);
            //amount.setBackground(activity.getResources().getDrawable(R.drawable.two_grid_bg2));
        }

    }

}