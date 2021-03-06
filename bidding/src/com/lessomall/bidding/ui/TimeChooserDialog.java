package com.lessomall.bidding.ui;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.MainActivity;
import com.lessomall.bidding.common.Constant;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by meisl on 2015/6/27.
 */
public class TimeChooserDialog extends Dialog {

    private String TAG = "com.lesso.data.ui.TimeChooserDialog";

    String[] txtWeek = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    private int type = 1;       //1:按日  2:按月
    private int dateType = 1;   //1:开始时间  2:结束时间

    private Calendar calendar = Calendar.getInstance();
    private String sBeginDate = "";
    private String sEndDate = "";
    private Date tempBeginDate;
    private Date tempEndDate;

    private DatePicker datePicker;

    private TextView start_day_txt, end_day_txt;

    private LinearLayout sdate_layout, datepicker_layout, edate_layout;

    private TextView byday, bymonth, datepicker_bdate, datepicker_edate, datepicker_finish;

    private Context context;

    private ClickListenerInterface clickListenerInterface;

    public TimeChooserDialog(Context context, int type, String sBeginDate, String sEndDate) {
        super(context);
        this.context = context;

        this.type = type;

        if (sBeginDate != null && !"".equals(sBeginDate.trim()))
            this.sBeginDate = sBeginDate;

        if (sEndDate != null && !"".equals(sEndDate.trim()))
            this.sEndDate = sEndDate;


        try {
            if (sBeginDate != null && !"".equals(sBeginDate.trim())) {
                tempBeginDate = Constant.DATE_FORMAT_1.parse(sBeginDate);
            } else {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.MONTH, -1);
                tempBeginDate = calendar.getTime();

                this.sBeginDate = Constant.DATE_FORMAT_1.format(tempBeginDate);

            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage(), e);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            tempBeginDate = calendar.getTime();

            this.sBeginDate = Constant.DATE_FORMAT_1.format(tempBeginDate);

        }

        try {
            if (sEndDate != null && !"".equals(sEndDate.trim())) {
                tempEndDate = Constant.DATE_FORMAT_1.parse(sEndDate);
            } else {
                tempEndDate = new Date();
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            tempEndDate = new Date();
        }

    }

    public interface ClickListenerInterface {
        void doFinish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        final LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_datepicker, null);

        start_day_txt = (TextView) dialog.findViewById(R.id.start_day_txt);
        end_day_txt = (TextView) dialog.findViewById(R.id.end_day_txt);
        if (context instanceof MainActivity) {
            start_day_txt.setText("开始时间");
            end_day_txt.setText("结束时间");
        } else {
            start_day_txt.setText("报价截止日期");
            end_day_txt.setText("期望交收日期");
        }


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        byday = (TextView) dialog.findViewById(R.id.byday);
        bymonth = (TextView) dialog.findViewById(R.id.bymonth);

        byday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 1;
                byday.setSelected(true);
                bymonth.setSelected(false);

                try {
                    View view2 = ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2);
                    view2.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                }

                calendar.setTime(tempBeginDate);
                datepicker_bdate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日  周" + txtWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);

                calendar.setTime(tempEndDate);
                datepicker_edate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日  周" + txtWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);


            }
        });

        bymonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = 2;
                byday.setSelected(false);
                bymonth.setSelected(true);

                try {
                    View view2 = ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2);
                    view2.setVisibility(View.GONE);
                } catch (Exception e) {
                }

                calendar.setTime(tempBeginDate);
                datepicker_bdate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");

                calendar.setTime(tempEndDate);
                datepicker_edate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");


            }
        });

        datepicker_finish = (TextView) dialog.findViewById(R.id.datepicker_finish);
        datepicker_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tempBeginDate == null) {
                    Toast.makeText(context, "请输入" + start_day_txt.getText().toString(), Toast.LENGTH_SHORT).show();
                } else if (tempEndDate == null) {
                    Toast.makeText(context, "请输入" + end_day_txt.getText().toString(), Toast.LENGTH_SHORT).show();
                } else {


                    if (type == 2) {

                        calendar.setTime(tempEndDate);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        calendar.add(Calendar.MONTH, 1);
                        calendar.add(Calendar.DAY_OF_MONTH, -day);
                        tempEndDate = calendar.getTime();

                        calendar.setTime(tempBeginDate);
                        calendar.add(Calendar.DAY_OF_MONTH, 1 - calendar.get(Calendar.DAY_OF_MONTH));
                        tempBeginDate = calendar.getTime();

                    }

                    sBeginDate = Constant.DATE_FORMAT_1.format(tempBeginDate);
                    sEndDate = Constant.DATE_FORMAT_1.format(tempEndDate);

                    if (sEndDate.compareTo(sBeginDate) >= 0) {
                        //Toast.makeText(context, sBeginDate + "-" + sEndDate, Toast.LENGTH_SHORT).show();
                        clickListenerInterface.doFinish();
                        TimeChooserDialog.this.dismiss();
                    } else {
                        Toast.makeText(context, end_day_txt.getText().toString() + "不能早于" + start_day_txt.getText().toString(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        sdate_layout = (LinearLayout) dialog.findViewById(R.id.sdate_layout);
        datepicker_layout = (LinearLayout) dialog.findViewById(R.id.datepicker_layout);
        edate_layout = (LinearLayout) dialog.findViewById(R.id.edate_layout);

        datepicker_bdate = (TextView) dialog.findViewById(R.id.datepicker_bdate);
        datepicker_edate = (TextView) dialog.findViewById(R.id.datepicker_edate);

        sdate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dateType == 2) {

                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(datepicker_layout, "Y", datepicker_layout.getY(), datepicker_layout.getY() - edate_layout.getHeight());
                    objectAnimator1.setInterpolator(new LinearInterpolator());
                    objectAnimator1.setDuration(300);
                    objectAnimator1.start();

                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(edate_layout, "Y", edate_layout.getY(), edate_layout.getY() + datepicker_layout.getHeight());
                    objectAnimator2.setInterpolator(new LinearInterpolator());
                    objectAnimator2.setDuration(300);
                    objectAnimator2.start();

                }

                dateType = 1;
                datepicker_bdate.setTextColor(context.getResources().getColor(R.color.MALL_C1));
                datepicker_edate.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));

                calendar.setTime(tempBeginDate);
                datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            }
        });


        edate_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dateType == 1) {

                    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(datepicker_layout, "Y", datepicker_layout.getY(), datepicker_layout.getY() + edate_layout.getHeight());
                    objectAnimator1.setInterpolator(new LinearInterpolator());
                    objectAnimator1.setDuration(300);
                    objectAnimator1.start();

                    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(edate_layout, "Y", edate_layout.getY(), edate_layout.getY() - datepicker_layout.getHeight());
                    objectAnimator2.setInterpolator(new LinearInterpolator());
                    objectAnimator2.setDuration(300);
                    objectAnimator2.start();

                }

                dateType = 2;
                datepicker_bdate.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));
                datepicker_edate.setTextColor(context.getResources().getColor(R.color.MALL_C1));

                calendar.setTime(tempEndDate);
                datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            }
        });

        if (dateType == 2) {
            datepicker_bdate.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));
            datepicker_edate.setTextColor(context.getResources().getColor(R.color.MALL_C1));
        } else {
            datepicker_bdate.setTextColor(context.getResources().getColor(R.color.MALL_C1));
            datepicker_edate.setTextColor(context.getResources().getColor(R.color.BASE_TEXT_COLOR));
        }

        datepicker_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdate_layout.performClick();
            }
        });

        datepicker_edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edate_layout.performClick();
            }
        });

        start_day_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sdate_layout.performClick();
            }
        });

        end_day_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edate_layout.performClick();
            }
        });

        /**
         *
         * 初始化开始时间控件
         */

        calendar.setTime(tempBeginDate);
        if (type == 2) {
            datepicker_bdate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
        } else {
            datepicker_bdate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日  周" + txtWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        }

        /**
         *
         * 初始化结束时间控件
         */

        calendar.setTime(tempEndDate);
        if (type == 2) {
            datepicker_edate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月");
        } else {
            datepicker_edate.setText(calendar.get(Calendar.YEAR) + "年" + (calendar.get(Calendar.MONTH) + 1) + "月" + calendar.get(Calendar.DAY_OF_MONTH) + "日  周" + txtWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
        }
        /**
         * 初始化datepicker控件
         */
        String date = (dateType == 2 ? sEndDate : sBeginDate);
        Date _date;
        try {
            if (date != null && !"".equals(date.trim())) {
                _date = Constant.DATE_FORMAT_1.parse(date);
            } else {
                _date = new Date();
            }
        } catch (ParseException e) {
            Log.e(TAG, e.getMessage());
            _date = new Date();
        }

        calendar.setTime(_date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePicker = (DatePicker) dialog.findViewById(R.id.datepicker);

        if (type == 2) {
            byday.setSelected(false);
            bymonth.setSelected(true);

            try {
                View view2 = ((ViewGroup) ((ViewGroup) TimeChooserDialog.this.datePicker.getChildAt(0)).getChildAt(0)).getChildAt(2);
                view2.setVisibility(View.GONE);
            } catch (Exception e) {
            }

        } else {
            byday.setSelected(true);
            bymonth.setSelected(false);
        }

        //    datePicker.setMaxDate(new Date().getTime());
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                calendar.set(i, i1, i2);
                if (type == 2) {
                    if (dateType == 2) {
                        tempEndDate = calendar.getTime();
                        datepicker_edate.setText(i + "年" + (i1 + 1) + "月");
                    } else {
                        tempBeginDate = calendar.getTime();
                        datepicker_bdate.setText(i + "年" + (i1 + 1) + "月");
                    }
                } else {
                    if (dateType == 2) {
                        tempEndDate = calendar.getTime();
                        datepicker_edate.setText(i + "年" + (i1 + 1) + "月" + i2 + "日  周" + txtWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                    } else {
                        tempBeginDate = calendar.getTime();
                        datepicker_bdate.setText(i + "年" + (i1 + 1) + "月" + i2 + "日  周" + txtWeek[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
                    }
                }
            }
        });
    }

    public int getType() {
        return type;
    }

    public String getsBeginDate() {
        return sBeginDate;
    }

    public String getsEndDate() {
        return sEndDate;
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

}
