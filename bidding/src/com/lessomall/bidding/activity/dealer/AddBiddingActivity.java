package com.lessomall.bidding.activity.dealer;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.ui.addbidding.FaPiaoDialog;
import com.lessomall.bidding.ui.addbidding.FenleiDialog;
import com.lessomall.bidding.ui.addbidding.PicDialog;
import com.lessomall.bidding.ui.addbidding.ReceiveDialog;
import com.lessomall.bidding.ui.addbidding.SearchDialog;
import com.lessomall.bidding.ui.addbidding.ZhifuDialog;

/**
 * Created by meisl on 2015/8/10.
 */
public class AddBiddingActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "com.lessomall.bidding.activity.dealer.AddBiddingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dealer_addbidding);

        Constant.CATEGORY_CACHE_LEVEL1 = new String[]{"1000000000-机械五金", "2000000000-五金工具五金五金工具五五金工具五五金工具五工具五金工具", "2000000000-五金工具", "2000000000-五金工具", "2000000000-五金工具", "2000000000-五金工具", "2000000000-五金工具"};

        initTitle();

        initView();

        initBottom();

        initData();

    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void initView() {

        LinearLayout topic = (LinearLayout) findViewById(R.id.topic);
        topic.setOnClickListener(this);

        LinearLayout product = (LinearLayout) findViewById(R.id.product);
        product.setOnClickListener(this);

        LinearLayout tax = (LinearLayout) findViewById(R.id.tax);
        tax.setOnClickListener(this);

        LinearLayout expdate = (LinearLayout) findViewById(R.id.expdate);
        expdate.setOnClickListener(this);

        LinearLayout delivery = (LinearLayout) findViewById(R.id.delivery);
        delivery.setOnClickListener(this);

        LinearLayout payment = (LinearLayout) findViewById(R.id.payment);
        payment.setOnClickListener(this);

        LinearLayout certificate = (LinearLayout) findViewById(R.id.certificate);
        certificate.setOnClickListener(this);

        LinearLayout other = (LinearLayout) findViewById(R.id.other);
        other.setOnClickListener(this);

        LinearLayout product_category = (LinearLayout) findViewById(R.id.product_category);
        product_category.setOnClickListener(this);

        ImageView product_pic_upload = (ImageView) findViewById(R.id.product_pic_upload);
        product_pic_upload.setOnClickListener(this);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initBottom() {

    }


    private void showFaPiaoDialog(int type) {

        FaPiaoDialog faPiaoDialog = new FaPiaoDialog(AddBiddingActivity.this, type);
        faPiaoDialog.getWindow().setGravity(Gravity.BOTTOM);
        faPiaoDialog.setCanceledOnTouchOutside(true);
        faPiaoDialog.setClickListenerInterface(new FaPiaoDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        faPiaoDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        faPiaoDialog.show();

    }

    private void showZhifuDialog(int type) {

        ZhifuDialog zhifuDialog = new ZhifuDialog(AddBiddingActivity.this, type);
        zhifuDialog.getWindow().setGravity(Gravity.BOTTOM);
        zhifuDialog.setCanceledOnTouchOutside(true);
        zhifuDialog.setClickListenerInterface(new ZhifuDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        zhifuDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        zhifuDialog.show();

    }

    private void showFenleiDialog(String code) {

        FenleiDialog fenleiDialog = new FenleiDialog(AddBiddingActivity.this, code, Constant.CATEGORY_CACHE_LEVEL1);
        fenleiDialog.getWindow().setGravity(Gravity.BOTTOM);
        fenleiDialog.setCanceledOnTouchOutside(true);
        fenleiDialog.setClickListenerInterface(new FenleiDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        fenleiDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        fenleiDialog.show();


    }

    private void showPicDialog(int type) {

        PicDialog picDialog = new PicDialog(AddBiddingActivity.this, type);
        picDialog.getWindow().setGravity(Gravity.BOTTOM);
        picDialog.setCanceledOnTouchOutside(true);
        picDialog.setClickListenerInterface(new PicDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        picDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        picDialog.show();

    }

    private void showReceiveDialog(int type) {

        ReceiveDialog receiveDialog = new ReceiveDialog(AddBiddingActivity.this, type);
        receiveDialog.getWindow().setGravity(Gravity.BOTTOM);
        receiveDialog.setCanceledOnTouchOutside(true);
        receiveDialog.setClickListenerInterface(new ReceiveDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        receiveDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        receiveDialog.show();

    }

    private void showSearchDialog(String txt) {

        SearchDialog searchDialog = new SearchDialog(AddBiddingActivity.this, txt);
        searchDialog.getWindow().setGravity(Gravity.BOTTOM);
        searchDialog.setCanceledOnTouchOutside(true);
        searchDialog.setClickListenerInterface(new SearchDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        searchDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        searchDialog.show();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.topic:

                break;
            case R.id.product:

                break;
            case R.id.tax:
                showFaPiaoDialog(0);
                break;
            case R.id.expdate:
                showTimerDialog();
                break;
            case R.id.delivery:
                showReceiveDialog(0);
                break;
            case R.id.payment:
                showZhifuDialog(0);
                break;
            case R.id.certificate:

                break;
            case R.id.other:

                break;
            case R.id.product_category:
                showFenleiDialog("2000000000");
                break;
            case R.id.product_pic_upload:
                showPicDialog(0);
                break;
            default:
                break;

        }
    }

    private class Bidding {

        private String toptic;
        private String productName;
        private String productBrand;
        private String productCategoryCode;
        private String productCategoryName;
        private int productNum;
        private String productUnit;
        private String productUnitPrice;
        private String productComment;
        private String[] productPicPaths;
        private String tax;
        private String expDate;
        private String deliverDate;
        private String deliverType;
        private String deliverAddress;
        private String payment;
        private String certificate;
        private String other;


    }

}
