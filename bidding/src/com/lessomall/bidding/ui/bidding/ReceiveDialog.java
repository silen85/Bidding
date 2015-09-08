package com.lessomall.bidding.ui.bidding;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/8/12.
 */
public class ReceiveDialog extends Dialog implements DialogInterface.OnCancelListener {

    private String TAG = "com.lessomall.bidding.ui.bidding.ReceiveDialog";

    private int clickTimes = 0;

    private Context context;

    private LinearLayout dialog_content;

    private String[] types = new String[]{"配送", "自提"};
    private String type = "配送";
    private String address = "";

    private TextView receive_finish;
    private EditText receive_address;

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public ReceiveDialog(Context context, String type, String address) {

        super(context);

        this.context = context;

        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) {
                this.type = type;
                break;
            }
        }

        this.address = address;

        if ("配送".equals(type)) clickTimes++;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_receive, null);
        dialog_content = (LinearLayout) dialog.findViewById(R.id.dialog_content);

        receive_finish = (TextView) dialog.findViewById(R.id.receive_finish);
        receive_address = (EditText) dialog.findViewById(R.id.receive_address);
        receive_address.setText(address);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        final Button receive_peisong = (Button) dialog_content.findViewById(R.id.receive_peisong);
        final Button receive_ziti = (Button) dialog_content.findViewById(R.id.receive_ziti);

        if (type.equals(receive_peisong.getText().toString())) {
            clearSelect();
            select(receive_peisong);
        }

        if (type.equals(receive_ziti.getText().toString())) {
            clearSelect();
            select(receive_ziti);
        }

        receive_peisong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTimes++;

                type = receive_peisong.getText().toString();
                address = receive_address.getText().toString();

                clearSelect();
                select(receive_peisong);

                if (clickTimes > 1) {
                    if (receive_peisong.getText().toString().equals(type) && "".equals(address.trim()))
                        Toast.makeText(context, "请填写交收地址", Toast.LENGTH_SHORT).show();
                    clickListenerInterface.doFinish();
                    dismiss();
                }

            }
        });

        receive_ziti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTimes = 0;

                type = receive_ziti.getText().toString();
                address = receive_address.getText().toString();

                clearSelect();
                select(receive_ziti);

                receive_ziti.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        ReceiveDialog.this.dismiss();
                    }
                }, 100);
            }
        });

        receive_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickTimes = 0;

                if (type.equals(receive_ziti.getText().toString())) {
                    type = receive_ziti.getText().toString();
                } else {
                    type = receive_peisong.getText().toString();
                }
                address = receive_address.getText().toString();

                if (receive_peisong.getText().toString().equals(type) && "".equals(address.trim())) {
                    Toast.makeText(context, "请填写交收地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                clickListenerInterface.doFinish();
                dismiss();

            }
        });

    }

    private void clearSelect() {
        for (int i = 0; i < dialog_content.getChildCount(); i++) {
            Button button = (Button) dialog_content.getChildAt(i);
            button.setBackgroundResource(R.drawable.border_normal);
            button.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));
        }
    }

    private void select(Button button) {
        button.setBackgroundResource(R.drawable.button_orange);
        button.setTextColor(context.getResources().getColor(R.color.MALL_C8));
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        address = receive_address.getText().toString();
        if ("配送".equals(type) && "".equals(address.trim()))
            Toast.makeText(context, "请填写交收地址", Toast.LENGTH_SHORT).show();

        clickListenerInterface.doFinish();
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }
}
