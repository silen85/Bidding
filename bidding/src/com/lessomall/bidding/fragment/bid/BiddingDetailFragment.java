package com.lessomall.bidding.fragment.bid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.fragment.BaseFragment;
import com.lessomall.bidding.model.Bidding;

/**
 * Created by meisl on 2015/8/28.
 */
public class BiddingDetailFragment extends BaseFragment {

    private Bidding bidding;

    private LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_bidding_detail, null);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public Bidding getBidding() {
        return bidding;
    }

    public void setBidding(Bidding bidding) {
        this.bidding = bidding;
    }

}
