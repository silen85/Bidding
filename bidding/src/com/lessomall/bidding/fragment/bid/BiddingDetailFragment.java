package com.lessomall.bidding.fragment.bid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.fragment.CommonBiddingFragment;
import com.lessomall.bidding.model.Bidding;

/**
 * Created by meisl on 2015/8/28.
 */
public class BiddingDetailFragment extends CommonBiddingFragment {

    private Bidding bidding;

    private LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_bidding_detail, null);

        initView();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initData();

    }

    private void initView() {


        switch (getBidding().getBiddingStatusId()) {
            case STATUS_BIDDING_1:

                break;
            case STATUS_BIDDING_2:

                break;
            case STATUS_BIDDING_3:

                break;
            case STATUS_BIDDING_4:

                break;
            case STATUS_BIDDING_5:

                break;
            case STATUS_BIDDING_6:

                break;
            case STATUS_BIDDING_7:

                break;
            default:
                break;
        }


    }

    private void initData() {

        topic_edit.setText(getBidding().getBiddingTitle());
        product_name_edit.setText(getBidding().getNameType());
        product_brand_edit.setText(getBidding().getBrand());
        product_category_txt.setText(getBidding().getProductBigCategory());
        product_num_edit.setText(getBidding().getRequiredQuantity());
        product_unit_edit.setText(getBidding().getUnit());
        product_unit_price_edit.setText(getBidding().getIntentPrice() + " 元");
        product_comment_edit.setText(getBidding().getMemo2());
        tax_txt.setText(getActivity().getResources().getString(R.string.bidding_tax) + "：" + getBidding().getTaxBillType());
        expdate_txt.setText(getActivity().getResources().getString(R.string.bidding_expdate) + "：" + getBidding().getBiddingDeadline());
        delivery_txt.setText(getActivity().getResources().getString(R.string.bidding_delivery) + "：" + getBidding().getDeliveryGoodsMode() + "；地址：" + getBidding().getDeliveryGoodsPlace());
        payment_txt.setText(getActivity().getResources().getString(R.string.bidding_payment) + "：" + getBidding().getPaymentMode());
        certificate_edit.setText(getBidding().getDepositPaymentVouchers());
        other_edit.setText(getBidding().getMemo());

        switch (getBidding().getBiddingStatusId()) {
            case STATUS_BIDDING_1:

                break;
            case STATUS_BIDDING_2:

                break;
            case STATUS_BIDDING_3:

                break;
            case STATUS_BIDDING_4:

                break;
            case STATUS_BIDDING_5:

                break;
            case STATUS_BIDDING_6:

                break;
            case STATUS_BIDDING_7:

                break;
            default:
                break;
        }

    }

    public Bidding getBidding() {
        return bidding;
    }

    public void setBidding(Bidding bidding) {
        this.bidding = bidding;
    }

}
