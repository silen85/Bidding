package com.lessomall.bidding.ui.bidding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/8/11.
 */
public class ZhifuDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.bidding.ZhifuDialog";

    private Context context;

    private LinearLayout dialog_content;

    private String[] types = new String[]{"信用支付", "现金支付", "汇款支付"};
    private String type = "信用支付";       //信用支付  现金支付  汇款支付

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public ZhifuDialog(Context context, String type) {

        super(context);

        this.context = context;

        for (int i = 0; i < types.length; i++) {
            if (types[i].equals(type)) {
                this.type = type;
                break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_zhifu, null);
        dialog_content = (LinearLayout) dialog.findViewById(R.id.dialog_content);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        final Button zhifu_xinyong = (Button) dialog_content.findViewById(R.id.zhifu_xinyong);
        final Button zhifu_xianjin = (Button) dialog_content.findViewById(R.id.zhifu_xianjin);
        final Button zhifu_huikuan = (Button) dialog_content.findViewById(R.id.zhifu_huikuan);

        if (type.equals(zhifu_xinyong.getText().toString())) {
            clearSelect();
            select(zhifu_xinyong);
        }

        if (type.equals(zhifu_xianjin.getText().toString())) {
            clearSelect();
            select(zhifu_xianjin);
        }

        if (type.equals(zhifu_huikuan.getText().toString())) {
            clearSelect();
            select(zhifu_huikuan);
        }

        zhifu_xinyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = zhifu_xinyong.getText().toString();
                clearSelect();
                select(zhifu_xinyong);

                zhifu_xinyong.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        ZhifuDialog.this.dismiss();
                    }
                }, 100);
            }
        });

        zhifu_xianjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = zhifu_xianjin.getText().toString();
                clearSelect();
                select(zhifu_xianjin);

                zhifu_xianjin.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        ZhifuDialog.this.dismiss();
                    }
                }, 100);
            }
        });

        zhifu_huikuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = zhifu_huikuan.getText().toString();
                clearSelect();
                select(zhifu_huikuan);

                zhifu_huikuan.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        ZhifuDialog.this.dismiss();
                    }
                }, 100);
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

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public String getType() {
        return type;
    }
}
