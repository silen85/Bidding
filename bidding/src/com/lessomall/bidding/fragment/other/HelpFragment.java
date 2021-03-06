package com.lessomall.bidding.fragment.other;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/9/8.
 */
public class HelpFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_help, null);

        LinearLayout call = (LinearLayout) view.findViewById(R.id.call);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelephonyManager telephony = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
                int type = telephony.getPhoneType();
                if (type == TelephonyManager.PHONE_TYPE_NONE) {
                    Toast.makeText(getActivity(), "该设备不支持通话功能", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:400-930-2128");
                    intent.setData(data);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

}
