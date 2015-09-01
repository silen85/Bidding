package com.lessomall.bidding.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.ui.TimeChooserDialog;
import com.lessomall.bidding.ui.addbidding.FaPiaoDialog;
import com.lessomall.bidding.ui.addbidding.FenleiDialog;
import com.lessomall.bidding.ui.addbidding.PicDialog;
import com.lessomall.bidding.ui.addbidding.ReceiveDialog;
import com.lessomall.bidding.ui.addbidding.SearchDialog;
import com.lessomall.bidding.ui.addbidding.ZhifuDialog;

/**
 * Created by meisl on 2015/8/28.
 */
public class CommonBiddingFragment extends Fragment implements View.OnClickListener {

    //竞价单:1:未提交 2:待审核 3:竞价中 4:已审核 5:已确认报价 6:已发货 7:已收货
    //报价单:1:待报价 2:已报价 3:被退回 4:竞价达成待发货 5:已发货 6:已确认收货

    protected final static String STATUS_BIDDING_1 = "1", STATUS_BIDDING_2 = "2", STATUS_BIDDING_3 = "3", STATUS_BIDDING_4 = "4", STATUS_BIDDING_5 = "5",
            STATUS_BIDDING_6 = "6", STATUS_BIDDING_7 = "7";

    protected final static String STATUS_QUOTE_1 = "1", STATUS_QUOTE_2 = "2", STATUS_QUOTE_3 = "3", STATUS_QUOTE_4 = "4", STATUS_QUOTE_5 = "5",
            STATUS_QUOTE_6 = "6";

    protected LinearLayout topic, product, product_category, product_pic, tax, expdate, delivery, payment, certificate, other;

    protected RelativeLayout rule_layout;

    protected ImageView product_serach, product_pic_add, sb_enable;

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected String sBeginDate, sEndDate;

    protected TextView biddingid, biddingstatus, product_category_txt, tax_txt, expdate_txt, delivery_txt, payment_txt;
    protected EditText topic_edit, product_brand_edit, product_name_edit, product_num_edit, product_unit_edit, product_unit_price_edit, product_comment_edit, certificate_edit, other_edit;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        biddingid = (TextView) view.findViewById(R.id.biddingid);

        biddingstatus = (TextView) view.findViewById(R.id.biddingstatus);

        topic = (LinearLayout) view.findViewById(R.id.topic);
        topic_edit = (EditText) view.findViewById(R.id.topic_edit);

        product = (LinearLayout) view.findViewById(R.id.product);
        product.setOnClickListener(this);

        product_name_edit = (EditText) view.findViewById(R.id.product_name_edit);

        product_serach = (ImageView) view.findViewById(R.id.product_serach);
        product_serach.setOnClickListener(this);

        product_brand_edit = (EditText) view.findViewById(R.id.product_brand_edit);

        product_category = (LinearLayout) view.findViewById(R.id.product_category);
        product_category.setOnClickListener(this);

        product_category_txt = (TextView) view.findViewById(R.id.product_category_txt);

        product_num_edit = (EditText) view.findViewById(R.id.product_num_edit);
        product_unit_edit = (EditText) view.findViewById(R.id.product_unit_edit);
        product_unit_price_edit = (EditText) view.findViewById(R.id.product_unit_price_edit);
        product_comment_edit = (EditText) view.findViewById(R.id.product_comment_edit);

        product_pic = (LinearLayout) view.findViewById(R.id.product_pic);

        product_pic_add = (ImageView) view.findViewById(R.id.product_pic_add);
        product_pic_add.setOnClickListener(this);

        tax = (LinearLayout) view.findViewById(R.id.tax);
        tax.setOnClickListener(this);

        tax_txt = (TextView) view.findViewById(R.id.tax_txt);

        expdate = (LinearLayout) view.findViewById(R.id.expdate);
        expdate.setOnClickListener(this);

        expdate_txt = (TextView) view.findViewById(R.id.expdate_txt);

        delivery = (LinearLayout) view.findViewById(R.id.delivery);
        delivery.setOnClickListener(this);

        delivery_txt = (TextView) view.findViewById(R.id.delivery_txt);

        payment = (LinearLayout) view.findViewById(R.id.payment);
        payment.setOnClickListener(this);

        payment_txt = (TextView) view.findViewById(R.id.payment_txt);

        certificate = (LinearLayout) view.findViewById(R.id.certificate);
        certificate.setOnClickListener(this);

        certificate_edit = (EditText) view.findViewById(R.id.certificate_edit);

        other = (LinearLayout) view.findViewById(R.id.other);
        other.setOnClickListener(this);

        other_edit = (EditText) view.findViewById(R.id.other_edit);

        rule_layout = (RelativeLayout) view.findViewById(R.id.rule_layout);

        sb_enable = (ImageView) view.findViewById(R.id.sb_enable);
        sb_enable.setSelected(true);
        sb_enable.setOnClickListener(this);

    }

    protected void showTimerDialog() {

        timerDialog = new TimeChooserDialog(getActivity(), timeType, sBeginDate, sEndDate);
        timerDialog.getWindow().setGravity(Gravity.BOTTOM);
        timerDialog.setCanceledOnTouchOutside(true);
        timerDialog.setClickListenerInterface(new TimeChooserDialog.ClickListenerInterface() {
            @Override
            public void doFinish() {

                timeType = timerDialog.getType();
                sBeginDate = timerDialog.getsBeaginDate();
                sEndDate = timerDialog.getsEndDate();

                expdate_txt.setText(getString(R.string.bidding_expdate) + "：" + sBeginDate + "  -  " + sEndDate);

            }
        });
        timerDialog.getWindow().setWindowAnimations(R.style.DIALOG);  //添加动画
        timerDialog.show();

    }

    private void showFaPiaoDialog(int type) {

        FaPiaoDialog faPiaoDialog = new FaPiaoDialog(getActivity(), type);
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

        ZhifuDialog zhifuDialog = new ZhifuDialog(getActivity(), type);
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

        FenleiDialog fenleiDialog = new FenleiDialog(getActivity(), code, Constant.CATEGORY_CACHE_LEVEL1);
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

        PicDialog picDialog = new PicDialog(getActivity(), type);
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

        ReceiveDialog receiveDialog = new ReceiveDialog(getActivity(), type);
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

        SearchDialog searchDialog = new SearchDialog(getActivity(), txt);
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

    protected void disableInput() {

        topic_edit.setFocusable(false);
        product_name_edit.setFocusable(false);
        product_serach.setVisibility(View.GONE);
        product_brand_edit.setFocusable(false);
        product_category.setOnClickListener(null);
        product_num_edit.setFocusable(false);
        product_unit_edit.setFocusable(false);
        product_unit_price_edit.setFocusable(false);
        product_comment_edit.setFocusable(false);
        product_pic_add.setVisibility(View.GONE);

        tax.setOnClickListener(null);
        expdate.setOnClickListener(null);
        payment.setOnClickListener(null);
        delivery.setOnClickListener(null);

        certificate_edit.setFocusable(false);
        other_edit.setFocusable(false);

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
            case R.id.product_pic_add:
                showPicDialog(0);
                break;
            case R.id.sb_enable:
                sb_enable.setSelected(!sb_enable.isSelected());
                break;
            default:
                break;

        }
    }
}
