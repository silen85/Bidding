package com.lessomall.bidding.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.activity.ImagePagerActivity;
import com.lessomall.bidding.common.Tools;
import com.lessomall.bidding.model.Bidding;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.Date;
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

            TextView deadline = (TextView) view.findViewById(R.id.deadline);
            TextView delivery_date = (TextView) view.findViewById(R.id.delivery_date);
            TextView tax = (TextView) view.findViewById(R.id.tax);
            TextView other = (TextView) view.findViewById(R.id.other);
            TextView biddingstatus = (TextView) view.findViewById(R.id.biddingstatus);

            FrameLayout frame_picture = (FrameLayout) view.findViewById(R.id.frame_picture);
            ImageView pictures = (ImageView) view.findViewById(R.id.pictures);
            LinearLayout bg_pictures = (LinearLayout) view.findViewById(R.id.bg_pictures);
            TextView num_pictures = (TextView) view.findViewById(R.id.num_pictures);

            TextView topic = (TextView) view.findViewById(R.id.topic);
            TextView brand = (TextView) view.findViewById(R.id.brand);
            TextView num = (TextView) view.findViewById(R.id.num);
            TextView comment = (TextView) view.findViewById(R.id.comment);

            LinearLayout list_layout = (LinearLayout) view.findViewById(R.id.list_layout);
            ListView pricelist = (ListView) view.findViewById(R.id.pricelist);

            LinearLayout button_layout = (LinearLayout) view.findViewById(R.id.button_layout);
            Button button = (Button) view.findViewById(R.id.button);

            viewHolder1.deadline = deadline;
            viewHolder1.delivery_date = delivery_date;
            viewHolder1.tax = tax;
            viewHolder1.other = other;
            viewHolder1.biddingstatus = biddingstatus;
            viewHolder1.frame_picture = frame_picture;
            viewHolder1.pictures = pictures;
            viewHolder1.bg_pictures = bg_pictures;
            viewHolder1.num_pictures = num_pictures;
            viewHolder1.topic = topic;
            viewHolder1.brand = brand;
            viewHolder1.num = num;
            viewHolder1.comment = comment;
            viewHolder1.list_layout = list_layout;
            viewHolder1.pricelist = pricelist;
            viewHolder1.button_layout = button_layout;
            viewHolder1.button = button;


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

    private void writeData(final ViewHolder1 viewHolder1, Bidding bidding) {

        final String[] urls = bidding.getPictureURL().split(",");
        if (urls != null && urls.length > 0 && !"".equals(urls[0].trim())) {

            ImageSize imageSize = new ImageSize(viewHolder1.pictures.getWidth(), viewHolder1.pictures.getHeight());

            viewHolder1.pictures.setTag(urls[0]);
            ImageLoader.getInstance().loadImage(urls[0], imageSize, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {
                    viewHolder1.pictures.setImageResource(R.mipmap.bg_gray);
                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {
                    if (s.equals(viewHolder1.pictures.getTag())) {
                        viewHolder1.pictures.setImageResource(R.mipmap.pic_default);
                        //   viewHolder0.pictures.invalidate();
                    }
                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    if (s.equals(viewHolder1.pictures.getTag())) {
                        if (bitmap == null) {
                            viewHolder1.pictures.setImageResource(R.mipmap.pic_default);
                        } else {
                            viewHolder1.pictures.setImageBitmap(bitmap);
                            viewHolder1.bg_pictures.setVisibility(View.VISIBLE);
                            viewHolder1.num_pictures.setText(urls.length + "");

                            viewHolder1.frame_picture.setOnClickListener(new View.OnClickListener() {
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
                    if (s.equals(viewHolder1.pictures.getTag())) {
                        viewHolder1.pictures.setImageResource(R.mipmap.pic_default);
                        //    viewHolder0.pictures.invalidate();
                    }
                }
            });
        } else {
            viewHolder1.pictures.setImageResource(R.mipmap.pic_default);
            //    viewHolder0.pictures.invalidate();
        }

        String outputStr = "";

        if (bidding.getBiddingDeadline() != null && !"".equals(bidding.getBiddingDeadline().trim())) {
            outputStr = bidding.getBiddingDeadline() + "  【 还有" + Tools.daysBetween(new Date(), Tools.parseDate(bidding.getBiddingDeadline(), "yyyy-MM-dd")) + "天 】";
        }
        viewHolder1.deadline.setText(outputStr);

        outputStr = "";

        if (bidding.getExpectDeliveryDate() != null && !"".equals(bidding.getExpectDeliveryDate().trim())) {
            outputStr = bidding.getExpectDeliveryDate() + "  【 还有" + Tools.daysBetween(new Date(), Tools.parseDate(bidding.getExpectDeliveryDate(), "yyyy-MM-dd")) + "天 】";
        }
        viewHolder1.delivery_date.setText(outputStr);

        viewHolder1.tax.setText(bidding.getTaxBillType());
        viewHolder1.other.setText(bidding.getMemo());

        viewHolder1.biddingstatus.setText(bidding.getBiddingStatusName());
        viewHolder1.topic.setText(bidding.getBiddingTitle());
        viewHolder1.brand.setText("".equals(bidding.getBrand()) ? "" : (bidding.getBrand() + " ") + bidding.getNameType());
        viewHolder1.num.setText("".equals(bidding.getRequiredQuantity()) ? "数量未定" : (bidding.getRequiredQuantity() + " " + bidding.getUnit()));


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

        TextView deadline;
        TextView delivery_date;
        TextView tax;
        TextView other;
        TextView biddingstatus;

        FrameLayout frame_picture;
        ImageView pictures;
        LinearLayout bg_pictures;
        TextView num_pictures;

        TextView topic;
        TextView brand;
        TextView num;
        TextView comment;

        LinearLayout list_layout;
        ListView pricelist;

        LinearLayout button_layout;
        Button button;

    }

}
