package com.lessomall.bidding.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.ImagePagerActivity;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.GetWebImageTask;
import com.lessomall.bidding.model.Bidding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meisl on 2015/8/24.
 */
public class BiddingAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Bidding> list = new ArrayList<Bidding>();

    public BiddingAdapter(Context context, List<Bidding> list) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int i) {
        return this.list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {

            Bidding bidding = (Bidding) getItem(position);

            if ("0".equals(bidding.getOrderType()))
                view = layoutInflater.inflate(R.layout.item_bidding, null);
            else
                view = layoutInflater.inflate(R.layout.item_quote, null);
        }

        this.writeData(view, position);

        return view;
    }

    private void writeData(View view, int position) {

        Bidding bidding = (Bidding) getItem(position);

        if ("1".equals(bidding.getOrderType())) {


        } else {

            TextView biddingid = (TextView) view.findViewById(R.id.biddingid);
            TextView biddingstatus = (TextView) view.findViewById(R.id.biddingstatus);
            TextView topic = (TextView) view.findViewById(R.id.topic);
            TextView brand = (TextView) view.findViewById(R.id.brand);
            TextView num = (TextView) view.findViewById(R.id.num);
            TextView date = (TextView) view.findViewById(R.id.date);

            FrameLayout frame_picture = (FrameLayout) view.findViewById(R.id.frame_picture);
            ImageView pictures = (ImageView) view.findViewById(R.id.pictures);
            LinearLayout bg_pictures = (LinearLayout) view.findViewById(R.id.bg_pictures);
            TextView num_pictures = (TextView) view.findViewById(R.id.num_pictures);

            if (bidding.getImageCache() != null) {
                pictures.setImageBitmap(bidding.getImageCache());
                pictures.invalidate();
            } else {
                final String[] urls = bidding.getPictureURL().split(",");
                if (urls != null && urls.length > 0) {
                    GetWebImageTask task = new GetWebImageTask(context, pictures);
                    task.setWebImageCacheI(bidding);
                    task.execute(Constant.PICTURE_URL + urls[0]);

                    bg_pictures.setVisibility(View.VISIBLE);
                    num_pictures.setText(urls.length + "");

                    frame_picture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String[] webUrl = new String[urls.length];
                            for (int i = 0; i < urls.length; i++) {
                                webUrl[i] = Constant.PICTURE_URL + urls[i];
                            }

                            Intent intent = new Intent(context, ImagePagerActivity.class);
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, webUrl);
                            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);
                            context.startActivity(intent);

                        }
                    });
                }
            }

            biddingid.setText(bidding.getBiddingCode());
            biddingstatus.setText(bidding.getBiddingStatusName());
            topic.setText(bidding.getBiddingTitle());
            brand.setText("".equals(bidding.getBrand()) ? "" : (bidding.getBrand() + " ") + bidding.getNameType());
            num.setText("".equals(bidding.getRequiredQuantity()) ? "数量未定" : (bidding.getRequiredQuantity() + " " + bidding.getUnit()));
            date.setText(bidding.getBiddingDeadline() + "  -  " + bidding.getExpectDeliveryDate());

        }

    }


}
