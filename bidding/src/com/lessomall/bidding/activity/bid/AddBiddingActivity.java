package com.lessomall.bidding.activity.bid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    private String TAG = "com.lessomall.bidding.activity.bid.AddBiddingActivity";

    private EditText product_name_edit;

    protected LinearLayout topic, product, product_category, product_pic, tax, expdate, delivery, payment, certificate, other;

    protected TextView biddingid, biddingstatus, product_category_txt, tax_txt, expdate_txt, delivery_txt, payment_txt;

    private ImageView sb_enable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_addbidding);

        Constant.CATEGORY_CACHE_LEVEL1 = new String[]{"1000000000-机械五金", "2000000000-五金工具五金五金工具五五金工具五五金工具五工具五金工具", "2000000000-五金工具", "2000000000-五金工具", "2000000000-五金工具", "2000000000-五金工具", "2000000000-五金工具"};

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

        LinearLayout topic = (LinearLayout) findViewById(R.id.topic);
        topic.setOnClickListener(this);

        LinearLayout product = (LinearLayout) findViewById(R.id.product);
        product.setOnClickListener(this);

        product_name_edit = (EditText) findViewById(R.id.product_name_edit);

        ImageView product_serach = (ImageView) findViewById(R.id.product_serach);
        product_serach.setOnClickListener(this);

        LinearLayout product_category = (LinearLayout) findViewById(R.id.product_category);
        product_category.setOnClickListener(this);

        ImageView product_pic_add = (ImageView) findViewById(R.id.product_pic_add);
        product_pic_add.setOnClickListener(this);

        tax = (LinearLayout) findViewById(R.id.tax);
        tax.setOnClickListener(this);

        tax_txt = (TextView) tax.findViewById(R.id.tax_txt);

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

        sb_enable = (ImageView) findViewById(R.id.sb_enable);
        sb_enable.setSelected(true);
        sb_enable.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }


    private void showFaPiaoDialog() {

        FaPiaoDialog faPiaoDialog = new FaPiaoDialog(AddBiddingActivity.this);
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

    private void showZhifuDialog() {

        ZhifuDialog zhifuDialog = new ZhifuDialog(AddBiddingActivity.this);
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

    private void showFenleiDialog() {

        FenleiDialog fenleiDialog = new FenleiDialog(AddBiddingActivity.this, Constant.CATEGORY_CACHE_LEVEL1);
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

    private void showPicDialog() {

        PicDialog picDialog = new PicDialog(AddBiddingActivity.this);
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

    private void showReceiveDialog() {

        ReceiveDialog receiveDialog = new ReceiveDialog(AddBiddingActivity.this);
        receiveDialog.getWindow().setGravity(Gravity.BOTTOM);
        receiveDialog.setCanceledOnTouchOutside(true);
        receiveDialog.setOnCancelListener(receiveDialog);
        receiveDialog.setClickListenerInterface(new ReceiveDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

            }
        });
        receiveDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        receiveDialog.show();

    }

    private void showSearchDialog(String txt) {

        SearchDialog searchDialog = new SearchDialog(AddBiddingActivity.this, txt, loginUser.getSessionid());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.topic:

                break;
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
            case R.id.certificate:

                break;
            case R.id.other:

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

}
