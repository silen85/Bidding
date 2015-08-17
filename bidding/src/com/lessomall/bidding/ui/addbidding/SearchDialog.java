package com.lessomall.bidding.ui.addbidding;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
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

    private TextView dialog_search_txt;

    private ProgressBar loading;

    private LinearLayout list_content;
    private ListView dialog_search_list;
    private TwoGridAdapter adapter;

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


        dialog_search_txt = (TextView) findViewById(R.id.dialog_search_txt);
        dialog_search_txt.setText((this.txt != null && !"".equals(this.txt.trim())) ? this.txt : "全部");

        loading = (ProgressBar) findViewById(R.id.loading);

        list_content = (LinearLayout) findViewById(R.id.list_content);
        dialog_search_list = (ListView) findViewById(R.id.dialog_search_list);
        dialog_search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("colum1", "1");
        map1.put("colum2", "2");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("colum1", "3");
        map2.put("colum2", "4");

        list.add(map1);
        list.add(map2);

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

        adapter = new TwoGridAdapter(this.context, list);
        dialog_search_list.setAdapter(adapter);

        loading.setVisibility(View.GONE);
        list_content.setVisibility(View.VISIBLE);

    }


    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

}
