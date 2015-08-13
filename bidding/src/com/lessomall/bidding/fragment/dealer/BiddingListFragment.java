package com.lessomall.bidding.fragment.dealer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/7/24.
 */
public class BiddingListFragment extends Fragment {

    private ScrollView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (ScrollView) inflater.inflate(R.layout.fragment_dealer_biddinglist, null);


        return view;

    }
}
