package com.lessomall.bidding.ui.bidding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.Map;

/**
 * Created by meisl on 2015/9/6.
 */
public class FahuoDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.bidding.FahuoDialog";

    private Context context;

    private LinearLayout dialog_content;

    private String qid = "";

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public FahuoDialog(Context context, String qid) {
        super(context);

        this.context = context;
        this.qid = qid;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_fahuo, null);
        dialog_content = (LinearLayout) dialog.findViewById(R.id.dialog_content);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        Button fahuo_yes = (Button) dialog_content.findViewById(R.id.fahuo_yes);
        Button fahuo_no = (Button) dialog_content.findViewById(R.id.fahuo_no);


        fahuo_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FahuoDialog.this.dismiss();

                ensureFahuo();

            }
        });

        fahuo_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FahuoDialog.this.dismiss();
            }
        });

        if (qid == null || "".equals(qid.trim()))
            FahuoDialog.this.dismiss();

    }

    private void ensureFahuo() {

        Map params = Tools.generateRequestMap();

        params.put("sessionid", ((BaseActivity) context).loginUser.getSessionid());
        params.put("qid", qid);

        RequestParams requestParams = new RequestParams(params);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                ((BaseActivity) context).loading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Toast.makeText(context, context.getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {
                    try {
                        Map result = Tools.json2Map(responseString);

                        String recode = (String) result.get("recode");
                        String msg = (String) result.get("msg");

                        if (Constant.RECODE_SUCCESS.equals(recode)) {
                            Toast.makeText(context, context.getString(R.string.tips_success_send), Toast.LENGTH_SHORT).show();
                            clickListenerInterface.doFinish();
                        } else {
                            ((BaseActivity) context).tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(context, context.getString(R.string.RECODE_ERROR_OTHERS), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                ((BaseActivity) context).disLoading();
            }

        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(context, Constant.SUPPLIER_CONFIRM_STORE, requestParams, asyncHttpResponseHandler);

    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }


}
