package com.lessomall.bidding.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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
import com.lessomall.bidding.common.MD5;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.model.Bidding;
import com.lessomall.bidding.ui.TimeChooserDialog;
import com.lessomall.bidding.ui.bidding.BaojiaDialog;
import com.lessomall.bidding.ui.bidding.FahuoDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class BaseActivity extends FragmentActivity {

    private int type = 0;

    private String TAG = "com.lessomall.bidding.activity.BaseActivity";

    public interface CameraCallback {
        void callback(String file);

        void callback(Uri uri);
    }

    private CameraCallback cameraCallback;

    public void setCameraCallback(CameraCallback cameraCallback) {
        this.cameraCallback = cameraCallback;
    }

    private Uri imageUri;

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    public static final int RESULT_GALLERY_IMAGE = 2;// 相册的requestCode

    public static final int HANDLER_DATA = 1;
    public static final int HANDLER_NETWORK_ERR = 2;

    public LessoApplication.LoginUser loginUser;

    private InputMethodManager mSoftManager;
    private LocationManager locationManager;

    private ProgressDialog loadingDialog;

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected String sBeginDate, sEndDate;

    private double longitude = 0.0;//经度
    private double latitude = 0.0;//维度

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loginUser = ((LessoApplication) getApplication()).getUser();
        if ((loginUser == null || loginUser.getSessionid() == null || "".equals(loginUser.getSessionid().trim())) && !(this instanceof LoginActivity)) {
            doLogin();
        }

        // TODO Auto-generated method stub
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mSoftManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        initLocation();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        loginUser = ((LessoApplication) getApplication()).getUser();
        if ((loginUser == null || loginUser.getSessionid() == null || "".equals(loginUser.getSessionid().trim())) && !(this instanceof LoginActivity)) {
            doLogin();
        }
    }

    private LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        }
    };

    private void initLocation() {

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
            }
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                longitude = location.getLongitude(); //经度
                latitude = location.getLatitude(); //纬度
            }
        }
    }

    private void doLogin() {

        SharedPreferences sp = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);

        String username = sp.getString(Constant.LESSO_BIDDING_USERNAME, "");
        String password = sp.getString(Constant.LESSO_BIDDING_USERPASSWORD, "");

        if (!"".equals(username) && !"".equals(password)) {

            Map params = Tools.generateRequestMap();

            params.put("username", username);
            params.put("password", new MD5().GetMD5Code(password));

            RequestParams requestParams = new RequestParams(params);

            AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e(TAG, throwable.getMessage(), throwable);
                    reLogin();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    Log.d(TAG, responseString);

                    if (statusCode == Constant.HTTP_STATUS_CODE_SUCCESS) {

                        String json = responseString;
                        try {
                            Map result = Tools.json2Map(json);

                            String recode = (String) result.get("recode");
                            String msg = (String) result.get("msg");

                            if (Constant.RECODE_SUCCESS.equals(recode)) {
                                JSONObject info = (JSONObject) result.get("info");
                                if (info != null) {
                                    handleLogin(info);
                                } else {
                                    reLogin();
                                }
                            } else {
                                reLogin();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.getMessage(), e);
                            reLogin();
                        }
                    } else {
                        reLogin();
                    }
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                }
            };
            asyncHttpResponseHandler.setCharset("UTF-8");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(Constant.CONNECT_TIMEOUT);
            client.post(this, Constant.URL_LOGIN, requestParams, asyncHttpResponseHandler);
        } else {
            reLogin();
        }

    }

    private void handleLogin(JSONObject data) throws Exception {

        SharedPreferences sp = getSharedPreferences(
                getString(R.string.app_name), Context.MODE_PRIVATE);

        String username = sp.getString(Constant.LESSO_BIDDING_USERNAME, "");
        String password = sp.getString(Constant.LESSO_BIDDING_USERPASSWORD, "");

        String userid = (String) data.get("userid");
        String _username = (String) data.get("username");
        String sessionid = (String) data.get("sessionid");
        String type = (String) data.get("type");
        String email = (String) data.get("email");
        String status = (String) data.get("status");
        String customName = (String) data.get("customName");
        String customCode = (String) data.get("customCode");
        String address = (String) data.get("address");
        String linkman = (String) data.get("linkman");
        String lastLoginTime = (String) data.get("lastLoginTime");
        String createTime = (String) data.get("createTime");

        if ("".equals(status)) {

            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Constant.LESSO_BIDDING_USERNAME, _username);
            editor.putString(Constant.LESSO_BIDDING_USERPASSWORD, password);
            editor.commit();

            LessoApplication.LoginUser loginUser = ((LessoApplication) getApplication()).new LoginUser();
            loginUser.setUserid(userid);
            loginUser.setUsername(_username);
            loginUser.setSessionid(sessionid);
            loginUser.setType(type);
            loginUser.setEmail(email);
            loginUser.setStatus(status);
            loginUser.setCustomName(customName);
            loginUser.setCustomCode(customCode);
            loginUser.setAddress(address);
            loginUser.setLinkman(linkman);
            loginUser.setLastLoginTime(lastLoginTime);
            loginUser.setCreateTime(createTime);

            ((LessoApplication) getApplication()).setUser(loginUser);

            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
            finish();

        } else {
            reLogin();
        }
    }

    public void reLogin() {
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

    public abstract void refreshList();

    public void goToDetail(Bidding bidding) {

    }

    public void backToList() {

    }

    public void showFahuoDialog(String qid) {

        if (qid == null || "".equals(qid.trim()))
            return;

        FahuoDialog fahuoDialog = new FahuoDialog(this, qid);
        fahuoDialog.getWindow().setGravity(Gravity.BOTTOM);
        fahuoDialog.setCanceledOnTouchOutside(true);
        fahuoDialog.setClickListenerInterface(new FahuoDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {
                refreshList();
            }
        });
        fahuoDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        fahuoDialog.show();

    }

    public void showBaojiaDialog(String qid, String biddingDetailId) {

        if (biddingDetailId == null || "".equals(biddingDetailId.trim()))
            return;

        BaojiaDialog baojiaDialog = new BaojiaDialog(this, qid, biddingDetailId);
        baojiaDialog.getWindow().setGravity(Gravity.BOTTOM);
        baojiaDialog.setCanceledOnTouchOutside(true);
        baojiaDialog.setClickListenerInterface(new BaojiaDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {
                refreshList();
            }
        });
        baojiaDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        baojiaDialog.show();

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


    public File getOutPutMediaFile() {

        if (!getExternalCacheDir().exists())
            getExternalCacheDir().mkdir();

        String strImgPath = getExternalCacheDir().getPath();      // 存放照片的文件夹

        String fileName = Tools.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg";// 照片命名

        return new File(strImgPath, fileName);
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

    public void confirm(String message, DialogInterface.OnClickListener y_button,
                        DialogInterface.OnClickListener n_button) {

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_info_details)
                .setTitle(getString(R.string.app_name)).setMessage(message)
                .setPositiveButton(getString(R.string.confirm_button1), y_button)
                .setNegativeButton(getString(R.string.confirm_button2), n_button)
                .show();
    }

    protected void hideSoftKeybord() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            mSoftManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CAPTURE_IMAGE:
                    try {
                        if (cameraCallback == null || imageUri == null)
                            return;

                        cameraCallback.callback(imageUri.getPath());
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    break;
                case RESULT_GALLERY_IMAGE:
                    try {
                        if (cameraCallback == null || (imageUri = data.getData()) == null)
                            return;

                        cameraCallback.callback(imageUri);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }

    }

    protected abstract void initTitle();

    protected abstract void initView();

    protected abstract void initData();

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}

