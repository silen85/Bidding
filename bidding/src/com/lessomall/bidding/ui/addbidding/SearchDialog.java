package com.lessomall.bidding.ui.addbidding;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.adapter.TwoGridAdapter;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by meisl on 2015/8/12.
 */
public class SearchDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.addbidding.SearchDialog";

    private Context context;

    private String txt;
    private String sessionid;

    private TextView dialog_search_txt;

    private ProgressBar loading;

    private LinearLayout list_content;
    private ListView dialog_search_list;
    private TwoGridAdapter adapter;

    private List<Map<String, String>> list = new ArrayList();

    private String productName = "";
    private String productCode = "";
    private String firstCategoryCode = "";
    private String baseMeasureUnit = "";

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public SearchDialog(Context context, String txt, String sessionid) {

        super(context);

        this.context = context;

        this.txt = txt;

        this.sessionid = sessionid;

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


        dialog_search_txt = (TextView) findViewById(R.id.dialog_search_txt);
        dialog_search_txt.setText((this.txt != null && !"".equals(this.txt.trim())) ? this.txt : "全部");

        loading = (ProgressBar) findViewById(R.id.loading);

        list_content = (LinearLayout) findViewById(R.id.list_content);
        dialog_search_list = (ListView) findViewById(R.id.dialog_search_list);
        dialog_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String productName = list.get(i).get("productName");
                String productCode = list.get(i).get("productCode");
                String firstCategoryCode = list.get(i).get("firstCategoryCode");
                String baseMeasureUnit = list.get(i).get("baseMeasureUnit");

                SearchDialog.this.productName = productName;
                SearchDialog.this.productCode = productCode;
                SearchDialog.this.firstCategoryCode = firstCategoryCode;
                SearchDialog.this.baseMeasureUnit = baseMeasureUnit;

                clickListenerInterface.doFinish();
                SearchDialog.this.dismiss();

            }
        });

        LinearLayout header = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_2grid, null);

        TextView a = ((TextView) header.findViewById(R.id.colum1));
        a.setText("名称型号");
        a.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        a.setTextSize(0, context.getResources().getDimensionPixelSize(R.dimen.F3));
        a.setBackgroundColor(context.getResources().getColor(R.color.MALL_C7));
        TextView b = ((TextView) header.findViewById(R.id.colum2));
        b.setText("单位");
        b.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        b.setTextSize(0, context.getResources().getDimensionPixelSize(R.dimen.F3));
        b.setBackgroundResource(R.drawable.two_grid_bg1);

        list_content.addView(header, 0);

        getData();

    }

    private void getData() {

        Map params = Tools.generateRequestMap();
        params.put("sessionid", sessionid);
        params.put("txt", txt);
        params.put("pageno", "1");
        params.put("pageSize", "5");

        RequestParams requestParams = new RequestParams(params);

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
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
                            List<Map<String, String>> datalist = (List<Map<String, String>>) result.get("datalist");
                            if (datalist != null && datalist.size() > 0) {
                                for (int i = 0; i < datalist.size(); i++) {

                                    Map<String, String> map = new HashMap();
                                    map.put("colum1", datalist.get(i).get("productName"));
                                    map.put("colum2", datalist.get(i).get("baseMeasureUnit"));

                                    map.put("productName", datalist.get(i).get("productName"));
                                    map.put("productCode", datalist.get(i).get("productCode"));
                                    map.put("firstCategoryCode", datalist.get(i).get("firstCategoryCode"));
                                    map.put("baseMeasureUnit", datalist.get(i).get("baseMeasureUnit"));

                                    list.add(map);

                                }

                                adapter = new TwoGridAdapter(context, list);
                                dialog_search_list.setAdapter(adapter);

                                loading.setVisibility(View.GONE);
                                list_content.setVisibility(View.VISIBLE);

                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                }
            }
        };

        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(Constant.CONNECT_TIMEOUT);
        client.post(context, Constant.SEARCH_PRODUCT, requestParams, asyncHttpResponseHandler);

    }


    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getFirstCategoryCode() {
        return firstCategoryCode;
    }

    public String getBaseMeasureUnit() {
        return baseMeasureUnit;
    }
}
