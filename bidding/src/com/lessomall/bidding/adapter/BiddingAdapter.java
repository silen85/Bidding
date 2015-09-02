package com.lessomall.bidding.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.activity.ImagePagerActivity;
import com.lessomall.bidding.model.Bidding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by meisl on 2015/8/24.
 */
public class BiddingAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Bidding> list = new ArrayList();

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

        Bidding bidding = (Bidding) getItem(position);
        if ("0".equals(bidding.getOrderType())) {
            ViewHolder0 viewHolder0;
            if (view == null) {
                viewHolder0 = new ViewHolder0();
                view = layoutInflater.inflate(R.layout.item_bidding, null);
                view.setTag(viewHolder0);
            } else {
                viewHolder0 = (ViewHolder0) view.getTag();
            }

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

            viewHolder0.biddingid = biddingid;
            viewHolder0.biddingstatus = biddingstatus;
            viewHolder0.topic = topic;
            viewHolder0.brand = brand;
            viewHolder0.num = num;
            viewHolder0.date = date;
            viewHolder0.frame_picture = frame_picture;
            viewHolder0.pictures = pictures;
            viewHolder0.bg_pictures = bg_pictures;
            viewHolder0.num_pictures = num_pictures;

            writeData(viewHolder0, bidding);

        } else {
            ViewHolder1 viewHolder1;
            if (view == null) {
                viewHolder1 = new ViewHolder1();
                view = layoutInflater.inflate(R.layout.item_quote, null);
                view.setTag(viewHolder1);
            } else {
                viewHolder1 = (ViewHolder1) view.getTag();
            }


            writeData(viewHolder1, bidding);

        }

        return view;
    }

    private void writeData(final ViewHolder0 viewHolder0, Bidding bidding) {

        final String[] urls = bidding.getPictureURL().split(",");
        if (urls != null && urls.length > 0 && !"".equals(urls[0].trim())) {

            ImageSize imageSize = new ImageSize(viewHolder0.pictures.getWidth(), viewHolder0.pictures.getHeight());

            viewHolder0.pictures.setTag(urls[0]);
            ImageLoader.getInstance().loadImage(urls[0], imageSize, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    viewHolder0.pictures.setImageResource(R.mipmap.bg_gray);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    if (s.equals(viewHolder0.pictures.getTag())) {
                        viewHolder0.pictures.setImageResource(R.mipmap.pic_default);
                        //   viewHolder0.pictures.invalidate();
                    }
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (s.equals(viewHolder0.pictures.getTag())) {
                        if (bitmap == null) {
                            viewHolder0.pictures.setImageResource(R.mipmap.pic_default);
                        } else {
                            viewHolder0.pictures.setImageBitmap(bitmap);
                            viewHolder0.bg_pictures.setVisibility(View.VISIBLE);
                            viewHolder0.num_pictures.setText(urls.length + "");

                            viewHolder0.frame_picture.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    String[] webUrl = new String[urls.length];
                                    for (int i = 0; i < urls.length; i++) {
                                        webUrl[i] = urls[i];
                                    }

                                    Intent intent = new Intent(context, ImagePagerActivity.class);
                                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, webUrl);
                                    intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);

                                    ((BaseActivity) context).startActivity(intent, false);

                                }
                            });
                        }
                        //    viewHolder0.pictures.invalidate();
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                    if (s.equals(viewHolder0.pictures.getTag())) {
                        viewHolder0.pictures.setImageResource(R.mipmap.pic_default);
                        //    viewHolder0.pictures.invalidate();
                    }
                }
            });
        } else {
            viewHolder0.pictures.setImageResource(R.mipmap.pic_default);
            //    viewHolder0.pictures.invalidate();
        }

        viewHolder0.biddingid.setText(bidding.getBiddingCode());
        viewHolder0.biddingstatus.setText(bidding.getBiddingStatusName());
        viewHolder0.topic.setText(bidding.getBiddingTitle());
        viewHolder0.brand.setText("".equals(bidding.getBrand()) ? "" : (bidding.getBrand() + " ") + bidding.getNameType());
        viewHolder0.num.setText("".equals(bidding.getRequiredQuantity()) ? "数量未定" : (bidding.getRequiredQuantity() + " " + bidding.getUnit()));
        viewHolder0.date.setText(bidding.getBiddingDeadline() + "  -  " + bidding.getExpectDeliveryDate());

    }

    private void writeData(ViewHolder1 viewHolder1, Bidding bidding) {

    }

    static class ViewHolder0 {
        TextView biddingid;
        TextView biddingstatus;
        TextView topic;
        TextView brand;
        TextView num;
        TextView date;

        FrameLayout frame_picture;
        ImageView pictures;
        LinearLayout bg_pictures;
        TextView num_pictures;

    }

    static class ViewHolder1 {

    }

}
