package com.lessomall.bidding.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
public class BaseFragment extends Fragment implements View.OnClickListener {

    protected TimeChooserDialog timerDialog;
    protected int timeType = 1;
    protected String sBeginDate, sEndDate;

    protected EditText product_name_edit;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout topic = (LinearLayout) view.findViewById(R.id.topic);
        topic.setOnClickListener(this);

        LinearLayout product = (LinearLayout) view.findViewById(R.id.product);
        product.setOnClickListener(this);

        product_name_edit = (EditText) view.findViewById(R.id.product_name_edit);

        ImageView product_serach = (ImageView) view.findViewById(R.id.product_serach);
        product_serach.setOnClickListener(this);

        LinearLayout product_category = (LinearLayout) view.findViewById(R.id.product_category);
        product_category.setOnClickListener(this);

        ImageView product_pic_upload = (ImageView) view.findViewById(R.id.product_pic_upload);
        product_pic_upload.setOnClickListener(this);

        LinearLayout tax = (LinearLayout) view.findViewById(R.id.tax);
        tax.setOnClickListener(this);

        LinearLayout expdate = (LinearLayout) view.findViewById(R.id.expdate);
        expdate.setOnClickListener(this);

        LinearLayout delivery = (LinearLayout) view.findViewById(R.id.delivery);
        delivery.setOnClickListener(this);

        LinearLayout payment = (LinearLayout) view.findViewById(R.id.payment);
        payment.setOnClickListener(this);

        LinearLayout certificate = (LinearLayout) view.findViewById(R.id.certificate);
        certificate.setOnClickListener(this);

        LinearLayout other = (LinearLayout) view.findViewById(R.id.other);
        other.setOnClickListener(this);

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
}
