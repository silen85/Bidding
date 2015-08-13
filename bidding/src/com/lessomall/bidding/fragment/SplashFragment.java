package com.lessomall.bidding.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.LoginActivity;
import com.lessomall.bidding.ui.SplashView;


/**
 * Created by meisl on 2015/6/9.
 */
public class SplashFragment extends Fragment {

    private SplashView view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = new SplashView(getActivity());

        final FrameLayout login_loding = (FrameLayout) view.findViewById(R.id.login_loding);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                login_loding.setVisibility(View.VISIBLE);

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        }, 3000);

        return view;
    }
}
