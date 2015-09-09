package com.lessomall.bidding.fragment.other;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.OtherActivity;

/**
 * Created by meisl on 2015/9/8.
 */
public class OtherFragment extends Fragment {

    private View view;

    private Button about_us, help, introduce, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_other, null);


        about_us = (Button) view.findViewById(R.id.about_us);
        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OtherActivity) getActivity()).aboutusView();
            }
        });

        help = (Button) view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OtherActivity) getActivity()).helpView();
            }
        });

        introduce = (Button) view.findViewById(R.id.introduce);
        introduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OtherActivity) getActivity()).introduceView();
            }
        });

        logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((OtherActivity) getActivity()).showLogoutDialog();
            }
        });


        return view;
    }

}
