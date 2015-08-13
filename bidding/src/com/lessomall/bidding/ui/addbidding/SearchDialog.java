package com.lessomall.bidding.ui.addbidding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;

/**
 * Created by meisl on 2015/8/12.
 */
public class SearchDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.addbidding.SearchDialog";

    private Context context;

    private String txt;

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public SearchDialog(Context context, String txt) {

        super(context);

        this.context = context;

        this.txt = txt;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_rs_search, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;


    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

}
