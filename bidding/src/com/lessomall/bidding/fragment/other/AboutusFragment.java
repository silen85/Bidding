package com.lessomall.bidding.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/9/8.
 */
public class AboutusFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_aboutus, null);


        return view;
    }


}
