package com.lessomall.bidding.fragment.bid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.ImagePagerActivity;
import com.lessomall.bidding.fragment.CommonBiddingFragment;
import com.lessomall.bidding.model.Bidding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by meisl on 2015/8/28.
 */
public class BiddingDetailFragment extends CommonBiddingFragment {

    private Bidding bidding;

    private LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = (LinearLayout) inflater.inflate(R.layout.fragment_bidding_detail, null);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        initView();

        initData();

    }

    private void initView() {

        //竞价单:1:未提交 2:待审核 3:竞价中 4:已审核 5:已确认报价 6:已发货 7:已收货
        switch (getBidding().getBiddingStatusId()) {
            case STATUS_BIDDING_1:

                break;
            case STATUS_BIDDING_2:
                topic_edit.setEnabled(false);
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

        biddingid.setText(getBidding().getBiddingCode());
        biddingstatus.setText(getBidding().getBiddingStatusName());

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

        final String[] urls = getBidding().getPictureURL().split(",");
        if (urls != null && urls.length > 0) {

            for (int i = 0; i < urls.length; i++) {

                if (!"".equals(urls[i].trim())) {

                    ImageSize imageSize = new ImageSize(getActivity().getResources().getDimensionPixelSize(R.dimen.product_pic_add_width),
                            getActivity().getResources().getDimensionPixelSize(R.dimen.product_pic_add_height));

                    final ImageView imageView = new ImageView(getActivity());

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getActivity().getResources().getDimensionPixelSize(R.dimen.product_pic_add_width),
                            getActivity().getResources().getDimensionPixelSize(R.dimen.product_pic_add_height));

                    layoutParams.topMargin = getActivity().getResources().getDimensionPixelSize(R.dimen.interval_D);
                    layoutParams.bottomMargin = getActivity().getResources().getDimensionPixelSize(R.dimen.interval_D);
                    layoutParams.rightMargin = getActivity().getResources().getDimensionPixelSize(R.dimen.interval_B);

                    imageView.setLayoutParams(layoutParams);
                    imageView.setAdjustViewBounds(true);
                    imageView.setClickable(true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    ImageLoader.getInstance().loadImage(urls[i], imageSize, new ImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String s, View view) {
                        }

                        @Override
                        public void onLoadingFailed(String s, View view, FailReason failReason) {
                        }

                        @Override
                        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                            if (bitmap != null) {
                                product_pic.addView(imageView, 0);
                                imageView.setImageBitmap(bitmap);
                                imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String[] webUrl = new String[urls.length];
                                        for (int i = 0; i < urls.length; i++) {
                                            webUrl[i] = urls[i];
                                        }

                                        Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, webUrl);
                                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                                        getActivity().startActivity(intent);

                                    }
                                });
                            }
                        }

                        @Override
                        public void onLoadingCancelled(String s, View view) {
                        }
                    });

                }
            }
        }

        //竞价单:1:未提交 2:待审核 3:竞价中 4:已审核 5:已确认报价 6:已发货 7:已收货
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
