package com.lessomall.bidding.ui.bidding;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.activity.OtherActivity;
import com.lessomall.bidding.common.Constant;

/**
 * Created by meisl on 2015/9/8.
 */
public class LogoutDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.bidding.LogoutDialog";

    private Context context;

    public LogoutDialog(Context context) {

        super(context);

        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_logout, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        LinearLayout logout_layout = (LinearLayout) findViewById(R.id.logout_layout);
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = context.getSharedPreferences(
                        context.getString(R.string.app_name), Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = sp.edit();
                editor.remove(Constant.LESSO_BIDDING_USERPASSWORD);
                editor.commit();

                ((LessoApplication) ((OtherActivity) context).getApplication()).setUser(null);

                ((BaseActivity) context).reLogin();
            }
        });


        LinearLayout cancel_layout = (LinearLayout) findViewById(R.id.cancel_layout);
        cancel_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }

}
