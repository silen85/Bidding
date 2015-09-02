package com.lessomall.bidding.ui.addbidding;

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
public class FaPiaoDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.addbidding.FaPiaoDialog";

    private Context context;

    private LinearLayout dialog_content;

    private String[] types = new String[]{"增值税发票", "普通发票", "无需发票"};
    private String type = "增值税发票";       //增值税发票  普通发票   无需发票

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public FaPiaoDialog(Context context) {

        super(context);

        this.context = context;

    }

    public FaPiaoDialog(Context context, String type) {

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
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_fapiao, null);
        dialog_content = (LinearLayout) dialog.findViewById(R.id.dialog_content);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        final Button fapiao_zengzhi = (Button) dialog_content.findViewById(R.id.fapiao_zengzhi);
        final Button fapiao_putong = (Button) dialog_content.findViewById(R.id.fapiao_putong);
        final Button fapiao_wuxu = (Button) dialog_content.findViewById(R.id.fapiao_wuxu);

        if (type.equals(fapiao_zengzhi.getText().toString())) {
            clearSelect();
            select(fapiao_zengzhi);
        }

        if (type.equals(fapiao_putong.getText().toString())) {
            clearSelect();
            select(fapiao_putong);
        }

        if (type.equals(fapiao_wuxu.getText().toString())) {
            clearSelect();
            select(fapiao_wuxu);
        }

        fapiao_zengzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = fapiao_zengzhi.getText().toString();
                clearSelect();
                select(fapiao_zengzhi);

                fapiao_zengzhi.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        FaPiaoDialog.this.dismiss();
                    }
                }, 100);
            }
        });

        fapiao_putong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = fapiao_putong.getText().toString();
                clearSelect();
                select(fapiao_putong);

                fapiao_putong.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        FaPiaoDialog.this.dismiss();
                    }
                }, 100);
            }
        });

        fapiao_wuxu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                type = fapiao_wuxu.getText().toString();
                clearSelect();
                select(fapiao_wuxu);

                fapiao_wuxu.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clickListenerInterface.doFinish();
                        FaPiaoDialog.this.dismiss();
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
