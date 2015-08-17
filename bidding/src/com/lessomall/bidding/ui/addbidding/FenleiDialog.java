package com.lessomall.bidding.ui.addbidding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.lessomall.bidding.R;
import com.lessomall.bidding.ui.FlowLayout;

/**
 * Created by meisl on 2015/8/11.
 */
public class FenleiDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.addbidding.FenleiDialog";

    private Context context;

    private String code, name;

    private String[] content;

    private ProgressBar loading;

    private FlowLayout contentView;

    private float density;


    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public FenleiDialog(Context context, String code, String[] content) {

        super(context);

        this.context = context;

        this.code = code;

        this.content = content;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_fenlei, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();

        density = dm.density;
        int padding = (int) (8f * density);


        loading = (ProgressBar) findViewById(R.id.loading);

        contentView = (FlowLayout) dialog.findViewById(R.id.contentView);

        ViewGroup.MarginLayoutParams mlp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mlp.topMargin = context.getResources().getDimensionPixelSize(R.dimen.interval_C);   //(int) (12f * density);
        mlp.bottomMargin = 0;
        mlp.leftMargin = 0;
        mlp.rightMargin = (int) (8f * density);

        for (int i = 0; i < content.length; i++) {

            String code = content[i].split("-")[0];
            String name = content[i].split("-")[1];

            final Button button = new Button(context);
            button.setPadding(padding, padding, padding, padding);
            button.setSingleLine(true);
            button.setEllipsize(TextUtils.TruncateAt.END);
            button.setText(name);
            button.setTag(R.string.TAG_KEY_A, code);
            button.setTag(R.string.TAG_KEY_B, name);
            button.setTextSize(context.getResources().getDimension(R.dimen.dialog_text_size) / dm.scaledDensity);

            if (this.code != null && code.equals(this.code)) {
                button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_orange));
                button.setTextColor(context.getResources().getColor(R.color.MALL_C8));
            } else {
                button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_normal));
                button.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String code = (String) button.getTag(R.string.TAG_KEY_A);
                    String name = (String) button.getTag(R.string.TAG_KEY_B);

                    FenleiDialog.this.code = code;
                    FenleiDialog.this.name = name;

                    clearSelect();
                    select(button);

                }
            });

            contentView.addView(button, mlp);

        }


        loading.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);

    }

    private void clearSelect() {

        for (int i = 0; i < contentView.getChildCount(); i++) {

            Button button = (Button) contentView.getChildAt(i);
            button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.border_normal));
            button.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));

        }

    }

    private void select(Button button) {
        button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.button_orange));
        button.setTextColor(context.getResources().getColor(R.color.MALL_C8));
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
