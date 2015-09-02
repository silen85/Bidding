package com.lessomall.bidding.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.lessomall.bidding.LessoApplication;
import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.ui.TimeChooserDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

public abstract class BaseActivity extends FragmentActivity {

    private String TAG = "com.lessomall.bidding.activity.BaseActivity";


    public interface CamerCallback {
        public void callback(File file);
    }

    public Uri cameraUri;
    private CamerCallback camerCallback;

    public void setCamerCallback(CamerCallback camerCallback) {
        this.camerCallback = camerCallback;
    }

    public static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    public static final int RESULT_GALLERY_IMAGE = 2;// 相册的requestCode

    public static final int HANDLER_DATA = 1;
    public static final int HANDLER_NETWORK_ERR = 2;

    public LessoApplication.LoginUser loginUser;

    private InputMethodManager mSoftManager;
    private ProgressDialog loadingDialog;

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected String sBeginDate, sEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loginUser = ((LessoApplication) getApplication()).getUser();
        if ((loginUser == null || loginUser.getSessionid() == null || "".equals(loginUser.getSessionid().trim())) && !(this instanceof LoginActivity)) {
            reLogin();
        }

        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mSoftManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        loginUser = ((LessoApplication) getApplication()).getUser();
        if ((loginUser == null || loginUser.getSessionid() == null || "".equals(loginUser.getSessionid().trim())) && !(this instanceof LoginActivity)) {
            reLogin();
        }
    }

    private void reLogin() {
        sendBroadcast(new Intent(Constant.FINISH_ACTION));
        startActivity(new Intent(this, LoginActivity.class), true);
    }


    protected void loadCategory() {

        Map params = Tools.generateRequestMap();
        params.put("sessionid", loginUser.getSessionid());

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
                            List<Map> datalist = (List<Map>) result.get("datalist");
                            if (datalist != null && datalist.size() > 0) {
                                Constant.CATEGORY_CACHE_LEVEL1 = new String[datalist.size()];
                                for (int i = 0; i < datalist.size(); i++) {

                                    String firstCategoryCode = (String) datalist.get(i).get("firstCategoryCode");
                                    String firstCategoryName = (String) datalist.get(i).get("firstCategoryName");

                                    Constant.CATEGORY_CACHE_LEVEL1[i] = firstCategoryCode + "-" + firstCategoryName;

                                }
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
        client.post(this, Constant.URL_FIRST_CATEGORY, requestParams, asyncHttpResponseHandler);

    }

    protected void showTimerDialog() {

        timerDialog = new TimeChooserDialog(this, timeType, sBeginDate, sEndDate);
        timerDialog.getWindow().setGravity(Gravity.BOTTOM);
        timerDialog.setCanceledOnTouchOutside(true);
        timerDialog.setClickListenerInterface(new TimeChooserDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

                timeType = timerDialog.getType();
                sBeginDate = timerDialog.getsBeaginDate();
                sEndDate = timerDialog.getsEndDate();

                initData();

            }
        });
        timerDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        timerDialog.show();

    }

    public void startActivity(Intent intent, boolean flag) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
        if (flag) finish();
    }

    public void tipsOutput(String recode, String msg) {
        if (Constant.RECODE_ERROR_TIPS.equals(recode)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_NODATA.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.no_data_tips), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_USER_LOGIN.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_LOGIN), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_USER_NOTEXIST.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_USER_NOTEXIST), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_PASSWORD_WRONG.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_FAILED_PASSWORD_WRONG), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_SESSION_WRONG.equals(recode)) {
            reLogin();
        } else if (Constant.RECODE_ERROR_SYSTEM.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_ERROR_OTHERS), Toast.LENGTH_SHORT).show();
        } else if (Constant.RECODE_FAILED_PARAM_WRONG.equals(recode)) {
            Toast.makeText(this, getResources().getString(R.string.RECODE_ERROR_OTHERS), Toast.LENGTH_SHORT).show();
        } else {

        }
    }

    /**
     * 加载对话框(显示)
     */
    public void loading() {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(this);
            loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loadingDialog.setMessage(getString(R.string.loading));
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.show();
    }

    /**
     * 加载对话框(关闭)
     */
    public void disLoading() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    protected void hideSoftKeybord() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            mSoftManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 256) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CAPTURE_IMAGE:
                    try {

                        if (cameraUri != null) {
                            File imageFile = new File(cameraUri.getPath());
                            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                            bitmap = compressImage(bitmap);

                            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(imageFile));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                            bos.flush();
                            bos.close();

                            if (camerCallback != null) {
                                camerCallback.callback(imageFile);
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    break;
                case RESULT_GALLERY_IMAGE:

                    getContentResolver();
                    break;
                default:
                    break;
            }
        }

    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

}

