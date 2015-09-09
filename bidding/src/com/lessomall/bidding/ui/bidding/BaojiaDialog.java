package com.lessomall.bidding.ui.bidding;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
public class BaojiaDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.bidding.BaojiaDialog";

    private Context context;

    private String qid = "";
    private String biddingDetailId = "";

    private ClickListenerInterface clickListenerInterface;

    private TextView baojia_finish, baojia_total;

    private EditText baojia_intentPrice, baojia_number, baojia_comment;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public BaojiaDialog(Context context, String qid, String biddingDetailId) {

        super(context);

        this.context = context;

        this.qid = qid;

        this.biddingDetailId = biddingDetailId;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_baojia, null);

        baojia_finish = (TextView) dialog.findViewById(R.id.baojia_finish);
        baojia_intentPrice = (EditText) dialog.findViewById(R.id.baojia_intentPrice);
        baojia_number = (EditText) dialog.findViewById(R.id.baojia_number);
        baojia_comment = (EditText) dialog.findViewById(R.id.baojia_comment);
        baojia_total = (TextView) dialog.findViewById(R.id.baojia_total);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        baojia_intentPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    float intentPrice = Float.parseFloat(baojia_intentPrice.getText().toString().trim());
                    float number = Float.parseFloat(baojia_number.getText().toString().trim());

                    baojia_total.setText((intentPrice * number) + "");
                } catch (Exception e) {
                    //   Log.e(TAG, e.getMessage(), e);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        baojia_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    float intentPrice = Float.parseFloat(baojia_intentPrice.getText().toString().trim());
                    float number = Float.parseFloat(baojia_number.getText().toString().trim());

                    baojia_total.setText((intentPrice * number) + "");
                } catch (Exception e) {
                    //   Log.e(TAG, e.getMessage(), e);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        baojia_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(baojia_intentPrice.getText().toString().trim())) {
                    Toast.makeText(context, "请输入单价", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(baojia_number.getText().toString().trim())) {
                    Toast.makeText(context, "请输入实际供货量", Toast.LENGTH_SHORT).show();
                    return;
                }

                BaojiaDialog.this.dismiss();

                baojia();

            }
        });


    }

    private void baojia() {

        Map params = Tools.generateRequestMap();

        params.put("sessionid", ((BaseActivity) context).loginUser.getSessionid());

        params.put("customCode", ((BaseActivity) context).loginUser.getCustomCode());
        params.put("customName", ((BaseActivity) context).loginUser.getCustomName());

        params.put("qid", qid);
        params.put("biddingDetailId", biddingDetailId);
        params.put("price", baojia_intentPrice.getText().toString().trim());
        params.put("actualSupplyTotalNumber", baojia_number.getText().toString().trim());
        params.put("memo", baojia_comment.getText().toString().trim());

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
                            Toast.makeText(context, context.getString(R.string.tips_success_baojia), Toast.LENGTH_SHORT).show();
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
        client.post(context, Constant.SUPPLIER_SUBMIT_PRICE, requestParams, asyncHttpResponseHandler);

    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

}
