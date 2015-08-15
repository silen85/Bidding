package com.lessomall.bidding.fragment.bid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/7/24.
 */
public class BiddingListFragment extends Fragment {

    private LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_biddinglist, null);


        return view;

    }
}
