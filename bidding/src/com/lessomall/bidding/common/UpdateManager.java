package com.lessomall.bidding.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
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

public class UpdateManager {

    String TAG = "com.lessomall.bidding.common.UpdateManager";

    private String downloadURL = "";

    private Context mContext;
    private Dialog noticeDialog;
    private Dialog downloadDialog;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/download/";
    private static final String saveFileName = savePath + "bidding.apk";
    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private static final int HANDLER_DATA = 3;
    private static final int HANDLER_NETWORK_ERR = 4;
    private int progress;
    private Thread downLoadThread;
    private boolean interceptFlag = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    if (downloadDialog != null) downloadDialog.cancel();
                    installApk();
                    break;
                case HANDLER_DATA:
                    String json = msg.getData().getString("json");
                    checkUpdateInfo(json);
                    break;
                case HANDLER_NETWORK_ERR:
                    Toast.makeText(mContext, mContext.getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public UpdateManager(Context context) {
        this.mContext = context;
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
        client.post(mContext, Constant.URL_UPDATE, null, asyncHttpResponseHandler);

    }

    // 外部接口让主Activity调用
    private void checkUpdateInfo(String json) {

        int oldVersionCode = Tools.getPackageInfo(mContext).versionCode;

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

                if (newVersionCode > oldVersionCode) {
                    showNoticeDialog();
                }
            }
        }
    }

    private void showNoticeDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(mContext.getString(R.string.update_tips));
        builder.setPositiveButton("更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("以后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);
        builder.setView(v);
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                interceptFlag = true;
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(downloadURL);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
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
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
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

        mContext.startActivity(i);
        ((Activity) mContext).finish();

    }
}
