package com.lessomall.bidding.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by meisl on 2015/9/12.
 */
public class NotifyUpdate {

    String TAG = "com.lessomall.bidding.common.NotifyUpdate";

    public NotificationManager mNotificationManager;

    private Context context;

    private int notifyId = 8888;
    private NotificationCompat.Builder mBuilder;

    private String downloadURL = "";

    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/download/";
    private static final String saveFileName = savePath + "bidding.apk";

    private boolean interceptFlag = false;

    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int HANDLER_DATA = 3;
    private static final int HANDLER_NETWORK_ERR = 4;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:

                    mBuilder.setContentTitle("下载中");

                    mBuilder.setProgress(100, msg.arg1, false);
                    mNotificationManager.notify(notifyId, mBuilder.build());

                    break;
                case DOWN_OVER:
                    installApk();
                    break;
                case HANDLER_DATA:
                    String json = msg.getData().getString("json");
                    checkUpdateInfo(json);
                    break;
                case HANDLER_NETWORK_ERR:
                    Toast.makeText(context, context.getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public NotifyUpdate(Context context) {
        this.context = context;
    }

    public void sendUpdateRequest() {

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Message message = mHandler.obtainMessage();
                message.what = HANDLER_NETWORK_ERR;
                message.sendToTarget();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d(TAG, responseString);

                if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {
                    Message message = mHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("json", responseString);
                    message.what = HANDLER_DATA;
                    message.setData(bundle);
                    message.sendToTarget();
                }

            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(context, Constant.URL_UPDATE, null, asyncHttpResponseHandler);

    }

    private void checkUpdateInfo(String json) {

        final int oldVersionCode = Tools.getPackageInfo(context).versionCode;

        if (json != null) {
            Map versionMap = Tools.json2Map(json);

            String recede = (String) versionMap.get("recode");
            if (Constant.RECODE_SUCCESS.equals(recede)) {
                String newVersion = (String) versionMap.get("version");
                downloadURL = (String) versionMap.get("url");
                int newVersionCode = oldVersionCode;
                try {
                    newVersionCode = Integer.valueOf(newVersion);
                } catch (Exception e) {
                }

                if (newVersionCode > oldVersionCode && downloadURL != null && !"".equals(downloadURL)) {
                    showNoticeDialog();
                }
            }
        }
    }

    private void showNoticeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("软件版本更新");
        builder.setMessage(context.getString(R.string.update_tips));
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                startDownloadNotify();
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    private void startDownloadNotify() {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mBuilder = new NotificationCompat.Builder(context);

        mBuilder.setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);

        mBuilder.setContentTitle(context.getString(R.string.app_name));

        mNotificationManager.notify(notifyId, mBuilder.build());

        new DownloadTask().execute(downloadURL);

    }

    class DownloadTask extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);

            mNotificationManager.cancel(notifyId);
            if (flag) {
                mHandler.sendEmptyMessage(DOWN_OVER);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            Message message = mHandler.obtainMessage(DOWN_UPDATE);
            message.arg1 = values[0];

            message.sendToTarget();

        }

        @Override
        protected Boolean doInBackground(String... params) {

            int temp = 0;
            boolean finishFlag = false;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do {

                    int numread = is.read(buf);
                    count += numread;
                    int progress = (int) (((float) count / length) * 100);

                    if (progress - temp >= 10 || progress > 99) {
                        temp = progress;
                        publishProgress(progress);
                    }

                    if (numread <= 0) {
                        finishFlag = true;
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                Log.e(TAG, e.getMessage(), e);
            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return finishFlag;
        }
    }

    private void installApk() {

        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }

        /*
        Intent i = new Intent(Intent.ACTION_INSTALL_PACKAGE);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setData(Uri.fromFile(apkfile));
        i.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        i.putExtra(Intent.EXTRA_RETURN_RESULT, true);
        i.putExtra(Intent.EXTRA_ALLOW_REPLACE, true);
        i.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME, getVersionInfo().packageName);

        mContext.startActivity(i);
        ((Activity) mContext).finish();
        * */

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");

        context.startActivity(i);
        ((Activity) context).finish();

    }

}
