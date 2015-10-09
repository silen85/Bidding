package com.lessomall.bidding.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.LoginActivity;
import com.lessomall.bidding.activity.OtherActivity;
import com.lessomall.bidding.activity.bid.BiddingListActivity;
import com.lessomall.bidding.activity.quote.QuoteListActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.util.List;
import java.util.Map;

/**
 * Created by meisl on 2015/10/6.
 */
public class PushReceiver extends PushMessageReceiver {

    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId, String requestId) {

        LessoApplication.LoginUser loginUser = ((LessoApplication) context).getUser();

        if (loginUser == null || loginUser.getSessionid() == null || "".equals(loginUser.getSessionid().trim())) {
            return;
        }

        Map params = Tools.generateRequestMap();
        params.put("sessionid", loginUser.getSessionid());
        params.put("channelId", channelId);

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
        client.post(context, Constant.URL_PUSH_BIND, requestParams, asyncHttpResponseHandler);

    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {

        SharedPreferences sp = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);

        String username = sp.getString(Constant.LESSO_BIDDING_USERNAME, "");
        String password = sp.getString(Constant.LESSO_BIDDING_USERPASSWORD, "");

        Intent intent;
        if (!"".equals(username) && !"".equals(password)) {

            Map custom_content = Tools.json2Map(s2);

            final String key = (String) custom_content.get("key");

            switch (key) {
                case "1":
                    intent = new Intent(context, BiddingListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "4");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "2":
                    intent = new Intent(context, BiddingListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "3":
                    intent = new Intent(context, BiddingListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "3");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "4":
                    intent = new Intent(context, BiddingListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "6");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "5":
                    intent = new Intent(context, QuoteListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "1");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "6":
                    intent = new Intent(context, QuoteListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "3");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "7":
                    intent = new Intent(context, QuoteListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "4");
                    context.getApplicationContext().startActivity(intent);
                    break;
                case "8":
                    intent = new Intent(context, QuoteListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("type", "6");
                    context.getApplicationContext().startActivity(intent);
                    break;
                default:
                    intent = new Intent(context, OtherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.getApplicationContext().startActivity(intent);
                    break;
            }

        } else {

            context.sendBroadcast(new Intent(Constant.FINISH_ACTION));
            intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);

        }
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
