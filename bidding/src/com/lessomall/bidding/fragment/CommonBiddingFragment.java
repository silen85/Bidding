package com.lessomall.bidding.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import com.lessomall.bidding.ui.TimeChooserDialog;
import com.lessomall.bidding.ui.bidding.FaPiaoDialog;
import com.lessomall.bidding.ui.bidding.FenleiDialog;
import com.lessomall.bidding.ui.bidding.PicDialog;
import com.lessomall.bidding.ui.bidding.ReceiveDialog;
import com.lessomall.bidding.ui.bidding.SearchDialog;
import com.lessomall.bidding.ui.bidding.ZhifuDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by meisl on 2015/8/28.
 */
public class CommonBiddingFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {

    private String TAG = "com.lessomall.bidding.fragment.CommonBiddingFragment";

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

    protected PicDialog picDialog;

    protected TextView biddingid, biddingstatus, product_category_txt, tax_txt, expdate_txt, delivery_txt, payment_txt;
    protected EditText topic_edit, product_brand_edit, product_name_edit, product_num_edit, product_unit_edit, product_unit_price_edit, product_comment_edit, certificate_edit, other_edit;

    protected List<String> imagePathList = new ArrayList(Constant.IMG_MAX_COUNT);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        biddingid = (TextView) view.findViewById(R.id.biddingid);

        biddingstatus = (TextView) view.findViewById(R.id.biddingstatus);

        topic = (LinearLayout) view.findViewById(R.id.topic);
        topic_edit = (EditText) view.findViewById(R.id.topic_edit);
        topic_edit.setOnFocusChangeListener(this);

        product = (LinearLayout) view.findViewById(R.id.product);
        product.setOnClickListener(this);

        product_name_edit = (EditText) view.findViewById(R.id.product_name_edit);
        product_name_edit.setOnFocusChangeListener(this);

        product_serach = (ImageView) view.findViewById(R.id.product_serach);
        product_serach.setOnClickListener(this);

        product_brand_edit = (EditText) view.findViewById(R.id.product_brand_edit);
        product_brand_edit.setOnFocusChangeListener(this);

        product_category = (LinearLayout) view.findViewById(R.id.product_category);
        product_category.setOnClickListener(this);

        product_category_txt = (TextView) view.findViewById(R.id.product_category_txt);

        product_num_edit = (EditText) view.findViewById(R.id.product_num_edit);
        product_unit_edit = (EditText) view.findViewById(R.id.product_unit_edit);
        product_unit_price_edit = (EditText) view.findViewById(R.id.product_unit_price_edit);
        product_comment_edit = (EditText) view.findViewById(R.id.product_comment_edit);

        product_num_edit.setOnFocusChangeListener(this);
        product_unit_edit.setOnFocusChangeListener(this);
        product_unit_price_edit.setOnFocusChangeListener(this);
        product_comment_edit.setOnFocusChangeListener(this);

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
        certificate_edit = (EditText) view.findViewById(R.id.certificate_edit);
        certificate_edit.setOnFocusChangeListener(this);

        other = (LinearLayout) view.findViewById(R.id.other);
        other_edit = (EditText) view.findViewById(R.id.other_edit);
        other_edit.setOnFocusChangeListener(this);

        rule_layout = (RelativeLayout) view.findViewById(R.id.rule_layout);

        sb_enable = (ImageView) view.findViewById(R.id.sb_enable);
        sb_enable.setSelected(true);
        sb_enable.setOnClickListener(this);


        ((BaseActivity) getActivity()).setCameraCallback(new BaseActivity.CameraCallback() {
            @Override
            public void callback(String path) {
                if (path != null && imagePathList.size() < Constant.IMG_MAX_COUNT) {
                    new CompressImageTask(picDialog, product_pic, product_pic_add).execute(path);
                }
            }

            @Override
            public void callback(Uri uri) {
                if (uri != null) {
                    String path = getRealPathFromUri(uri);
                    if (path != null && imagePathList.size() < Constant.IMG_MAX_COUNT) {
                        new CompressImageTask(picDialog, product_pic, product_pic_add).execute(path);
                    }
                }
            }
        });

    }


    public String getRealPathFromUri(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void showSearchDialog(String txt) {

        final SearchDialog searchDialog = new SearchDialog(getActivity(), txt, ((BaseActivity) getActivity()).loginUser.getSessionid());
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

        searchDialog.show();

    }

    private void showFenleiDialog() {


        final FenleiDialog fenleiDialog = new FenleiDialog(getActivity(), (String) product_category_txt.getTag(), Constant.CATEGORY_CACHE_LEVEL1);
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

        fenleiDialog.show();


    }

    private void showPicDialog() {

        if (picDialog == null) {
            picDialog = new PicDialog(getActivity());
            picDialog.getWindow().setGravity(Gravity.BOTTOM);
            picDialog.setCanceledOnTouchOutside(true);
            picDialog.getWindow().setWindowAnimations(R.style.DIALOG);
        }
        picDialog.show();

    }

    private void showFaPiaoDialog() {


        final FaPiaoDialog faPiaoDialog = new FaPiaoDialog(getActivity(), (String) tax_txt.getTag());
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

        faPiaoDialog.show();

    }

    protected void showTimerDialog() {

        if (sBeginDate == null || "".equals(sBeginDate.trim()) || sEndDate == null || "".equals(sEndDate.trim())) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            if (sBeginDate == null || "".equals(sBeginDate.trim())) {
                calendar.add(Calendar.DAY_OF_MONTH, 15);
                sBeginDate = Constant.DATE_FORMAT_1.format(calendar.getTime());
            }

            if (sEndDate == null || "".equals(sEndDate.trim())) {
                calendar.add(Calendar.DAY_OF_MONTH, 30);
                sEndDate = Constant.DATE_FORMAT_1.format(calendar.getTime());
            }

        }

        if (timerDialog == null) {
            timerDialog = new TimeChooserDialog(getActivity(), timeType, sBeginDate, sEndDate);
            timerDialog.getWindow().setGravity(Gravity.BOTTOM);
            timerDialog.setCanceledOnTouchOutside(true);
            timerDialog.setClickListenerInterface(new TimeChooserDialog.ClickListenerInterface() {
                @Override
                public void doFinish() {

                    timeType = timerDialog.getType();
                    sBeginDate = timerDialog.getsBeginDate();
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


        final ReceiveDialog receiveDialog = new ReceiveDialog(getActivity(), (String) delivery_txt.getTag(R.string.TAG_KEY_A), (String) delivery_txt.getTag(R.string.TAG_KEY_B));
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

        receiveDialog.show();

    }

    private void showZhifuDialog() {


        final ZhifuDialog zhifuDialog = new ZhifuDialog(getActivity(), (String) payment_txt.getTag());
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

        zhifuDialog.show();

    }


    protected void disableInput() {

        topic_edit.setEnabled(false);
        topic_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_name_edit.setEnabled(false);
        product_name_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_serach.setVisibility(View.GONE);
        product_brand_edit.setEnabled(false);
        product_brand_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_category.setOnClickListener(null);
        product_num_edit.setEnabled(false);
        product_num_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_unit_edit.setEnabled(false);
        product_unit_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_unit_price_edit.setEnabled(false);
        product_unit_price_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_comment_edit.setEnabled(false);
        product_comment_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        product_pic_add.setVisibility(View.GONE);

        tax.setOnClickListener(null);
        expdate.setOnClickListener(null);
        payment.setOnClickListener(null);
        delivery.setOnClickListener(null);

        certificate_edit.setEnabled(false);
        certificate_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));
        other_edit.setEnabled(false);
        other_edit.setTextColor(getResources().getColor(R.color.BASE_TEXT_COLOR));

    }

    protected void enableInput() {

        topic_edit.setEnabled(true);
        product_name_edit.setEnabled(true);
        product_serach.setVisibility(View.VISIBLE);
        product_brand_edit.setEnabled(true);
        product_category.setOnClickListener(this);
        product_num_edit.setEnabled(true);
        product_unit_edit.setEnabled(true);
        product_unit_price_edit.setEnabled(true);
        product_comment_edit.setEnabled(true);

        if (imagePathList.size() < Constant.IMG_MAX_COUNT) {
            product_pic_add.setVisibility(View.VISIBLE);
        } else {
            product_pic_add.setVisibility(View.GONE);
        }

        tax.setOnClickListener(this);
        expdate.setOnClickListener(this);
        payment.setOnClickListener(this);
        delivery.setOnClickListener(this);

        certificate_edit.setEnabled(true);
        other_edit.setEnabled(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.product:

                break;
            case R.id.product_serach:
                product.setSelected(true);
                showSearchDialog(product_name_edit.getText().toString());
                break;
            case R.id.tax:
                product.setSelected(false);
                showFaPiaoDialog();
                break;
            case R.id.expdate:
                product.setSelected(false);
                showTimerDialog();
                break;
            case R.id.delivery:
                product.setSelected(false);
                showReceiveDialog();
                break;
            case R.id.payment:
                product.setSelected(false);
                showZhifuDialog();
                break;
            case R.id.product_category:
                product.setSelected(true);
                showFenleiDialog();
                break;
            case R.id.product_pic_add:
                product.setSelected(true);
                showPicDialog();
                break;
            case R.id.sb_enable:
                product.setSelected(false);
                sb_enable.setSelected(!sb_enable.isSelected());
                break;
            default:
                break;

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()) {
            case R.id.product_name_edit:
            case R.id.product_brand_edit:
            case R.id.product_num_edit:
            case R.id.product_unit_edit:
            case R.id.product_unit_price_edit:
            case R.id.product_comment_edit:
                if (hasFocus)
                    product.setSelected(true);
                break;
            case R.id.topic_edit:
            case R.id.certificate_edit:
            case R.id.other_edit:
                if (hasFocus)
                    product.setSelected(false);
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
                    path = PictureUtil.compressImage(path, ((BaseActivity) getActivity()).getOutPutMediaFile().getAbsolutePath());
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

                imagePathList.add(path);

                final ImageView imageView = new ImageView(getActivity());

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
                        String[] pathArr = new String[imagePathList.size()];
                        for (int i = 0; i < imagePathList.size(); i++) {
                            if (imagePathList.get(i).indexOf("http") >= 0) {
                                pathArr[i] = imagePathList.get(i);
                            } else {
                                pathArr[i] = "file:///" + imagePathList.get(i);
                            }
                            if (imagePathList.get(i).equals(imageView.getTag())) {
                                index = i;
                            }
                        }

                        Intent intent = new Intent(getActivity(), ImagePagerActivity.class);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, pathArr);
                        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, index);

                        ((BaseActivity) getActivity()).startActivity(intent, false);

                    }
                });

                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View v) {

                        final String tag = (String) v.getTag();

                        ((BaseActivity) getActivity()).confirm("确定删除这张图片？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                imagePathList.remove(tag);
                                product_pic.removeView(v);

                                if (imagePathList.size() < Constant.IMG_MAX_COUNT) {
                                    product_pic_add.setVisibility(View.VISIBLE);
                                }

                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        return true;
                    }
                });

                product_pic.addView(imageView, product_pic.getChildCount() - 1);

                if (imagePathList.size() >= Constant.IMG_MAX_COUNT) {
                    product_pic_add.setVisibility(View.GONE);
                }

                Toast.makeText(getActivity(), getString(R.string.photo_finish_tips), Toast.LENGTH_SHORT).show();
            }
            disLoading();
        }


        /**
         * 加载对话框(显示)
         */
        public void loading() {
            if (loadingDialog == null) {
                loadingDialog = new ProgressDialog(getActivity());
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
