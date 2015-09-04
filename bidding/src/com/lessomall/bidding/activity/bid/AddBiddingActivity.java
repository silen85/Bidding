package com.lessomall.bidding.activity.bid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.activity.ImagePagerActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.PictureUtil;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.ui.TimeChooserDialog;
import com.lessomall.bidding.ui.addbidding.FaPiaoDialog;
import com.lessomall.bidding.ui.addbidding.FenleiDialog;
import com.lessomall.bidding.ui.addbidding.PicDialog;
import com.lessomall.bidding.ui.addbidding.ReceiveDialog;
import com.lessomall.bidding.ui.addbidding.SearchDialog;
import com.lessomall.bidding.ui.addbidding.ZhifuDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by meisl on 2015/8/10.
 */
public class AddBiddingActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "com.lessomall.bidding.activity.bid.AddBiddingActivity";

    protected LinearLayout topic, product, product_category, product_pic, tax, expdate, delivery, payment, certificate, other;

    protected RelativeLayout rule_layout;

    protected ImageView product_serach, product_pic_add, sb_enable;

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected String sBeginDate, sEndDate;

    protected SearchDialog searchDialog;
    protected FenleiDialog fenleiDialog;
    protected PicDialog picDialog;
    protected FaPiaoDialog faPiaoDialog;
    protected ZhifuDialog zhifuDialog;
    protected ReceiveDialog receiveDialog;

    protected TextView biddingid, biddingstatus, product_category_txt, tax_txt, expdate_txt, delivery_txt, payment_txt;
    protected EditText topic_edit, product_brand_edit, product_name_edit, product_num_edit, product_unit_edit, product_unit_price_edit, product_comment_edit, certificate_edit, other_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addbidding);

        initTitle();

        initView();

        initData();

        if (Constant.CATEGORY_CACHE_LEVEL1 == null) {
            loadCategory();
        }

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

        biddingid = (TextView) findViewById(R.id.biddingid);

        biddingstatus = (TextView) findViewById(R.id.biddingstatus);

        topic = (LinearLayout) findViewById(R.id.topic);
        topic_edit = (EditText) findViewById(R.id.topic_edit);

        product = (LinearLayout) findViewById(R.id.product);
        product.setOnClickListener(this);

        product_name_edit = (EditText) findViewById(R.id.product_name_edit);

        product_serach = (ImageView) findViewById(R.id.product_serach);
        product_serach.setOnClickListener(this);

        product_brand_edit = (EditText) findViewById(R.id.product_brand_edit);

        product_category = (LinearLayout) findViewById(R.id.product_category);
        product_category.setOnClickListener(this);

        product_category_txt = (TextView) findViewById(R.id.product_category_txt);

        product_num_edit = (EditText) findViewById(R.id.product_num_edit);
        product_unit_edit = (EditText) findViewById(R.id.product_unit_edit);
        product_unit_price_edit = (EditText) findViewById(R.id.product_unit_price_edit);
        product_comment_edit = (EditText) findViewById(R.id.product_comment_edit);

        product_pic = (LinearLayout) findViewById(R.id.product_pic);

        product_pic_add = (ImageView) findViewById(R.id.product_pic_add);
        product_pic_add.setOnClickListener(this);

        tax = (LinearLayout) findViewById(R.id.tax);
        tax.setOnClickListener(this);

        tax_txt = (TextView) findViewById(R.id.tax_txt);

        expdate = (LinearLayout) findViewById(R.id.expdate);
        expdate.setOnClickListener(this);

        expdate_txt = (TextView) findViewById(R.id.expdate_txt);

        delivery = (LinearLayout) findViewById(R.id.delivery);
        delivery.setOnClickListener(this);

        delivery_txt = (TextView) findViewById(R.id.delivery_txt);

        payment = (LinearLayout) findViewById(R.id.payment);
        payment.setOnClickListener(this);

        payment_txt = (TextView) findViewById(R.id.payment_txt);

        certificate = (LinearLayout) findViewById(R.id.certificate);
        certificate_edit = (EditText) findViewById(R.id.certificate_edit);

        other = (LinearLayout) findViewById(R.id.other);
        other_edit = (EditText) findViewById(R.id.other_edit);

        rule_layout = (RelativeLayout) findViewById(R.id.rule_layout);

        sb_enable = (ImageView) findViewById(R.id.sb_enable);
        sb_enable.setSelected(true);
        sb_enable.setOnClickListener(this);


        setCameraCallback(new BaseActivity.CameraCallback() {
            @Override
            public void callback(String path) {
                if (path != null && picDialog.getImagePathList().size() < Constant.IMG_MAX_COUNT) {
                    new CompressImageTask(picDialog, product_pic, product_pic_add).execute(path);
                }
            }

            @Override
            public void callback(Uri uri) {
                if (uri != null) {
                    String path = getRealPathFromUri(uri);
                    if (path != null && picDialog.getImagePathList().size() < Constant.IMG_MAX_COUNT) {
                        new CompressImageTask(picDialog, product_pic, product_pic_add).execute(path);
                    }
                }
            }
        });

        TextView button1 = (TextView) findViewById(R.id.button1);
        TextView button2 = (TextView) findViewById(R.id.button2);
        TextView button3 = (TextView) findViewById(R.id.button3);

        button1.setText("返回");
        button2.setText("保存");
        button3.setText("提交");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrice();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPrice();
            }
        });


    }

    @Override
    protected void initData() {

    }

    public String getRealPathFromUri(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


    private void showSearchDialog(String txt) {

        if (searchDialog == null) {
            searchDialog = new SearchDialog(this, txt, loginUser.getSessionid());
            searchDialog.getWindow().setGravity(Gravity.BOTTOM);
            searchDialog.setCanceledOnTouchOutside(true);
            searchDialog.setClickListenerInterface(new SearchDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {

                    product_name_edit.setTag(searchDialog.getProductCode());
                    product_name_edit.setText(searchDialog.getProductName());
                    product_unit_edit.setText(searchDialog.getBaseMeasureUnit());

                    for (int i = 0; i < Constant.CATEGORY_CACHE_LEVEL1.length; i++) {
                        if (Constant.CATEGORY_CACHE_LEVEL1[i].split("-")[0].equals(searchDialog.getFirstCategoryCode())) {
                            product_category_txt.setTag(Constant.CATEGORY_CACHE_LEVEL1[i].split("-")[0]);
                            product_category_txt.setText(Constant.CATEGORY_CACHE_LEVEL1[i].split("-")[1]);
                            break;
                        }
                    }
                }
            });
            searchDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        }
        searchDialog.show();

    }

    private void showFenleiDialog() {

        if (fenleiDialog == null) {
            fenleiDialog = new FenleiDialog(this, Constant.CATEGORY_CACHE_LEVEL1);
            fenleiDialog.getWindow().setGravity(Gravity.BOTTOM);
            fenleiDialog.setCanceledOnTouchOutside(true);
            fenleiDialog.setClickListenerInterface(new FenleiDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {

                    product_category_txt.setTag(fenleiDialog.getCode());
                    product_category_txt.setText(fenleiDialog.getName());

                }
            });
            fenleiDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        }
        fenleiDialog.show();


    }

    private void showPicDialog() {

        if (picDialog == null) {
            picDialog = new PicDialog(this);
            picDialog.getWindow().setGravity(Gravity.BOTTOM);
            picDialog.setCanceledOnTouchOutside(true);
            picDialog.getWindow().setWindowAnimations(R.style.DIALOG);
        }
        picDialog.show();

    }

    private void showFaPiaoDialog() {

        if (faPiaoDialog == null) {
            faPiaoDialog = new FaPiaoDialog(this);
            faPiaoDialog.getWindow().setGravity(Gravity.BOTTOM);
            faPiaoDialog.setCanceledOnTouchOutside(true);
            faPiaoDialog.setClickListenerInterface(new FaPiaoDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {
                    tax_txt.setText(getString(R.string.bidding_tax) + "：" + faPiaoDialog.getType());
                    tax_txt.setTag(faPiaoDialog.getType());
                }
            });
            faPiaoDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        }
        faPiaoDialog.show();

    }

    protected void showTimerDialog() {

        if (timerDialog == null) {
            timerDialog = new TimeChooserDialog(this, timeType, sBeginDate, sEndDate);
            timerDialog.getWindow().setGravity(Gravity.BOTTOM);
            timerDialog.setCanceledOnTouchOutside(true);
            timerDialog.setClickListenerInterface(new TimeChooserDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {

                    timeType = timerDialog.getType();
                    sBeginDate = timerDialog.getsBeaginDate();
                    sEndDate = timerDialog.getsEndDate();

                    expdate_txt.setText(getString(R.string.bidding_expdate) + "：" + sBeginDate + "  -  " + sEndDate);
                    expdate_txt.setTag(R.string.TAG_KEY_A, sBeginDate);
                    expdate_txt.setTag(R.string.TAG_KEY_B, sEndDate);


                }
            });
            timerDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        }
        timerDialog.show();

    }

    private void showReceiveDialog() {

        if (receiveDialog == null) {
            receiveDialog = new ReceiveDialog(this);
            receiveDialog.getWindow().setGravity(Gravity.BOTTOM);
            receiveDialog.setCanceledOnTouchOutside(true);
            receiveDialog.setOnCancelListener(receiveDialog);
            receiveDialog.setClickListenerInterface(new ReceiveDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {

                    if (receiveDialog.getAddress() != null && !"".equals(receiveDialog.getAddress().trim())) {
                        delivery_txt.setText(getString(R.string.bidding_delivery) + "：" + receiveDialog.getType() + "；地址：" + receiveDialog.getAddress());
                    } else {
                        delivery_txt.setText(getString(R.string.bidding_delivery) + "：" + receiveDialog.getType());
                    }

                    delivery_txt.setTag(R.string.TAG_KEY_A, receiveDialog.getType());
                    delivery_txt.setTag(R.string.TAG_KEY_B, receiveDialog.getAddress());
                }
            });
            receiveDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        }
        receiveDialog.show();

    }

    private void showZhifuDialog() {

        if (zhifuDialog == null) {
            zhifuDialog = new ZhifuDialog(this);
            zhifuDialog.getWindow().setGravity(Gravity.BOTTOM);
            zhifuDialog.setCanceledOnTouchOutside(true);
            zhifuDialog.setClickListenerInterface(new ZhifuDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {
                    payment_txt.setText(getString(R.string.bidding_payment) + "：" + zhifuDialog.getType());
                    payment_txt.setTag(zhifuDialog.getType());

                }
            });
            zhifuDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        }
        zhifuDialog.show();

    }

    private void savePrice() {

        Map params = Tools.generateRequestMap();
        params.put("sessionid", loginUser.getSessionid());

        params.put("optype", Constant.OPTERATION_TYPE[0]);

        if (trimParams(params)) {


            sendPrice(params);
        }

    }

    private void submitPrice() {

        Map params = Tools.generateRequestMap();
        params.put("sessionid", loginUser.getSessionid());

        params.put("optype", Constant.OPTERATION_TYPE[1]);

        if (trimParams(params)) {
            sendPrice(params);
        }
    }

    private void sendPrice(Map params) {

        RequestParams requestParams = new RequestParams(params);

        if (picDialog != null) {

            if (picDialog.getImagePathList().size() > 0) {
                File[] files = new File[picDialog.getImagePathList().size()];
                for (int i = 0; i < picDialog.getImagePathList().size(); i++) {
                    files[i] = new File(picDialog.getImagePathList().get(i));
                }
                try {
                    requestParams.put("UpLoadFiles", files);
                } catch (FileNotFoundException e) {
                    Log.e(TAG, e.getMessage(), e);
                    Toast.makeText(this, getString(R.string.upload_image_error), Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        AsyncHttpResponseHandler asyncHttpResponseHandler = new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                super.onStart();
                loading();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.getMessage(), throwable);
                Toast.makeText(AddBiddingActivity.this, getString(R.string.no_data_error), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddBiddingActivity.this, getString(R.string.tips_success_operate), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            tipsOutput(recode, msg);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage(), e);
                        Toast.makeText(AddBiddingActivity.this, getString(R.string.RECODE_ERROR_OTHERS), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                disLoading();
            }

        };
        asyncHttpResponseHandler.setCharset("UTF-8");
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(18000);
        client.post(this, Constant.DEALER_ADD_PRICE, requestParams, asyncHttpResponseHandler);

    }

    private boolean trimParams(Map params) {

        params.put("customName", loginUser.getCustomName());
        params.put("customCode", loginUser.getCustomCode());
        params.put("biddingCode", "");
        if (!validateAndPutValue(params, "biddingTitle", topic_edit.getText().toString(), "请输入主题")) {
            return false;
        }
        params.put("consumerCode", "");
        params.put("consumerName", "");
        if (!validateAndPutValue(params, "biddingDeadline", (String) expdate_txt.getTag(R.string.TAG_KEY_A), "请选择截止日期")) {
            return false;
        }
        if (!validateAndPutValue(params, "expectDeliveryDate", (String) expdate_txt.getTag(R.string.TAG_KEY_B), "请选择交收日期")) {
            return false;
        }
        if (!validateAndPutValue(params, "deliveryGoodsMode", (String) delivery_txt.getTag(R.string.TAG_KEY_A), "请选择交收方式")) {
            return false;
        }
        if (!validateAndPutValue(params, "deliveryGoodsPlace", (String) delivery_txt.getTag(R.string.TAG_KEY_B), "请选择交收地址")) {
            return false;
        }
        params.put("taxBillType", tax_txt.getTag().toString());
        params.put("memo", other_edit.getText().toString());
        params.put("commissionRate", "");
        params.put("returnState", "");
        params.put("customerService", "");
        if (!validateAndPutValue(params, "depositPaymentVouchers", certificate_edit.getText().toString(), "请输入保证金付款凭证")) {
            return false;
        }
        params.put("productCode", product_name_edit.getTag().toString());
        if (!validateAndPutValue(params, "nameType", product_name_edit.getText().toString(), "请输入名称型号")) {
            return false;
        }
        params.put("brand", product_brand_edit.getText().toString());
        if (!validateAndPutValue(params, "productBigCategory", (String) product_category_txt.getTag(), "请选择所属分类")) {
            return false;
        }
        params.put("productMiddleCategory", "");
        params.put("collectCategory", "");
        if (!validateAndPutValue(params, "requiredQuantity", product_num_edit.getText().toString(), "请输入数量")) {
            return false;
        }
        if (!validateAndPutValue(params, "unit", product_unit_edit.getText().toString(), "请输入单位")) {
            return false;
        }
        params.put("memo2", product_comment_edit.getText().toString());
        params.put("checkStatus", "");
        params.put("intentPrice", product_unit_price_edit.getText().toString());

        float price = 0;
        try {
            price = Float.parseFloat(product_num_edit.getText().toString()) * Float.parseFloat(product_unit_price_edit.getText().toString());
        } catch (Exception e) {
        }
        params.put("price", price + "");

        return true;
    }

    private boolean validateAndPutValue(Map params, String key, String value, String tips) {

        params.put(key, value);

        boolean flag = true;
        if (value == null || "".equals(value.trim())) {
            flag = false;
            Toast.makeText(this, tips, Toast.LENGTH_SHORT).show();
        }
        return flag;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product:

                break;
            case R.id.product_serach:
                showSearchDialog(product_name_edit.getText().toString());
                break;
            case R.id.tax:
                showFaPiaoDialog();
                break;
            case R.id.expdate:
                showTimerDialog();
                break;
            case R.id.delivery:
                showReceiveDialog();
                break;
            case R.id.payment:
                showZhifuDialog();
                break;
            case R.id.product_category:
                showFenleiDialog();
                break;
            case R.id.product_pic_add:
                showPicDialog();
                break;
            case R.id.sb_enable:
                sb_enable.setSelected(!sb_enable.isSelected());
                break;
            default:
                break;

        }
    }

    /**
     * Created by meisl on 2015/9/4.
     */
    class CompressImageTask extends AsyncTask<String, Void, Bitmap> {

        private String TAG = "com.lessomall.bidding.fragment.CommonBiddingFragment.CompressImageTask";

        private ProgressDialog loadingDialog;

        private String path;
        private PicDialog picDialog;
        private LinearLayout product_pic;
        private ImageView product_pic_add;

        public CompressImageTask(PicDialog picDialog, LinearLayout product_pic, ImageView product_pic_add) {
            this.picDialog = picDialog;
            this.product_pic = product_pic;
            this.product_pic_add = product_pic_add;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            path = params[0];

            Bitmap bitmap = null;
            try {
                if (picDialog.getType() == 1) {
                    path = PictureUtil.compressImage(path, picDialog.getOutPutMediaFile().getAbsolutePath());
                    bitmap = BitmapFactory.decodeFile(path);
                } else {
                    path = PictureUtil.compressImage(path);
                    bitmap = BitmapFactory.decodeFile(path);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (bitmap != null) {
                picDialog.getImagePathList().add(path);

                final ImageView imageView = new ImageView(AddBiddingActivity.this);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.product_pic_add_width),
                        getResources().getDimensionPixelSize(R.dimen.product_pic_add_height));
                layoutParams.topMargin = getResources().getDimensionPixelSize(R.dimen.interval_D);
                layoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.interval_D);
                layoutParams.rightMargin = getResources().getDimensionPixelSize(R.dimen.interval_B);
                imageView.setLayoutParams(layoutParams);
                imageView.setAdjustViewBounds(true);
                imageView.setClickable(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageBitmap(bitmap);
                imageView.setTag(path);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int index = 0;
                        String[] pathArr = new String[picDialog.getImagePathList().size()];
                        for (int i = 0; i < picDialog.getImagePathList().size(); i++) {
                            pathArr[i] = "file:///" + picDialog.getImagePathList().get(i);
                            if (picDialog.getImagePathList().get(i).equals(imageView.getTag())) {
                                index = i;
                            }
                        }

                        Intent intent = new Intent(AddBiddingActivity.this, ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, pathArr);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, index);

                        startActivity(intent, false);

                    }
                });

                product_pic.addView(imageView, product_pic.getChildCount() - 1);

                if (picDialog.getImagePathList().size() >= Constant.IMG_MAX_COUNT) {
                    product_pic_add.setVisibility(View.GONE);
                }
            }
            disLoading();
        }


        /**
         * 加载对话框(显示)
         */
        public void loading() {
            if (loadingDialog == null) {
                loadingDialog = new ProgressDialog(AddBiddingActivity.this);
                loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                loadingDialog.setMessage(getString(R.string.image_loading));
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
    }

}
