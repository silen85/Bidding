package com.lessomall.bidding.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.LoginActivity;
import com.lessomall.bidding.activity.OtherActivity;

/**
 * Created by meisl on 2015/6/9.
 */
public class GuideFragment extends Fragment implements View.OnClickListener {

    int pageSize = 4;
    int pageIndex = 1;

    private View view;

    private int verticalMinDistance = 180;
    private int minVelocity = 0;
    private GestureDetector mGestureDetector;

    private RelativeLayout layoutContent;

    private ImageView content_no1;
    private ImageView content_no2;
    private ImageView content_no3;
    private ImageView content_no4;

    private ImageView aroundImg_no1;
    private ImageView aroundImg_no3;
    private ImageView aroundImg_no4;

    private ImageView title1;
    private ImageView title2;
    private ImageView title3;
    private LinearLayout title4;
    private ImageButton btn_title4;

    private ImageView dot1;
    private ImageView dot2;
    private ImageView dot3;
    private ImageView dot4;

    ImageView[] dots;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_guide, null);

        initView();

        dots = new ImageView[]{dot1, dot2, dot3, dot4};

        initGesture();
        layoutContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                return true;
            }
        });

        btn_title4.setOnClickListener(this);

        return view;
    }

    private void initView() {

        layoutContent = (RelativeLayout) view.findViewById(R.id.layoutContent);

        content_no1 = (ImageView) view.findViewById(R.id.content_no1);
        content_no2 = (ImageView) view.findViewById(R.id.content_no2);
        content_no3 = (ImageView) view.findViewById(R.id.content_no3);
        content_no4 = (ImageView) view.findViewById(R.id.content_no4);

        aroundImg_no1 = (ImageView) view.findViewById(R.id.aroundImg_no1);
        aroundImg_no3 = (ImageView) view.findViewById(R.id.aroundImg_no3);
        aroundImg_no4 = (ImageView) view.findViewById(R.id.aroundImg_no4);

        title1 = (ImageView) view.findViewById(R.id.title1);
        title2 = (ImageView) view.findViewById(R.id.title2);
        title3 = (ImageView) view.findViewById(R.id.title3);
        title4 = (LinearLayout) view.findViewById(R.id.title4);
        btn_title4 = (ImageButton) view.findViewById(R.id.btn_title4);

        dot1 = (ImageView) view.findViewById(R.id.dot1);
        dot2 = (ImageView) view.findViewById(R.id.dot2);
        dot3 = (ImageView) view.findViewById(R.id.dot3);
        dot4 = (ImageView) view.findViewById(R.id.dot4);


    }

    private void step(int pageIndex, boolean flag) {

        switch (pageIndex) {
            case 1:
                if (!flag) {
                    stepTwo_b();
                    dots[pageIndex - 1].setImageResource(R.mipmap.star);
                    dots[pageIndex].setImageResource(R.mipmap.dot);
                }
                break;
            case 2:
                if (flag) {
                    stepTwo();
                    dots[pageIndex - 2].setImageResource(R.mipmap.dot);
                    dots[pageIndex - 1].setImageResource(R.mipmap.star);
                } else {
                    stepThree_b();
                    dots[pageIndex - 1].setImageResource(R.mipmap.star);
                    dots[pageIndex].setImageResource(R.mipmap.dot);
                }
                break;
            case 3:
                if (flag) {
                    stepThree();
                    dots[pageIndex - 2].setImageResource(R.mipmap.dot);
                    dots[pageIndex - 1].setImageResource(R.mipmap.star);
                } else {
                    stepFour_b();
                    dots[pageIndex - 1].setImageResource(R.mipmap.star);
                    dots[pageIndex].setImageResource(R.mipmap.dot);
                }
                break;
            case 4:
                if (flag) {
                    stepFour();
                    dots[pageIndex - 2].setImageResource(R.mipmap.dot);
                    dots[pageIndex - 1].setImageResource(R.mipmap.star);
                }
                break;
            default:
                return;
        }

    }

    private void stepTwo() {

        startAlphaOut(content_no1);
        startAlphaOut(aroundImg_no1);
        startAlphaOut(title1);

        startAlphaIn(content_no2);
        startAlphaIn(title2);

    }

    private void stepTwo_b() {

        startAlphaOut(content_no2);
        startAlphaOut(title2);

        startAlphaIn(aroundImg_no1);
        startAlphaIn(content_no1);
        startAlphaIn(title1);

    }

    private void stepThree() {

        startAlphaOut(content_no2);
        startAlphaOut(title2);

        startAlphaIn(aroundImg_no3);
        startAlphaIn(content_no3);
        startAlphaIn(title3);

    }

    private void stepThree_b() {

        startAlphaOut(content_no3);
        startAlphaOut(aroundImg_no3);
        startAlphaOut(title3);

        startAlphaIn(content_no2);
        startAlphaIn(title2);

    }

    private void stepFour() {

        startAlphaOut(content_no3);
        startAlphaOut(aroundImg_no3);
        startAlphaOut(title3);

        startAlphaIn(content_no4);
        startAlphaIn(aroundImg_no4);
        startAlphaIn(title4);
        startAlphaIn(btn_title4);

        title4.setVisibility(View.VISIBLE);

    }

    private void stepFour_b() {

        startAlphaOut(content_no4);
        startAlphaOut(aroundImg_no4);
        startAlphaOut(title4);
        startAlphaOut(btn_title4);

        title4.setVisibility(View.INVISIBLE);

        startAlphaIn(content_no3);
        startAlphaIn(aroundImg_no3);
        startAlphaIn(title3);

    }

    private void startAlphaIn(Object target) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "alpha", 0f, 1.0f);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.setDuration(480);
        objectAnimator.setRepeatCount(0);
        objectAnimator.start();
    }

    private void startAlphaOut(Object target) {

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(target, "alpha", 1.0f, 0f);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.setDuration(480);
        objectAnimator.setRepeatCount(0);
        objectAnimator.start();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_title4) {

            if (getActivity() instanceof OtherActivity) {
                ((OtherActivity) getActivity()).backToList();
            } else {
                Intent it = new Intent();
                it.setClass(getActivity(), LoginActivity.class);
                startActivity(it);
                getActivity().overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
                getActivity().finish();
            }
        }

    }

    private void initGesture() {
        mGestureDetector = new GestureDetector(getActivity(), new MyOnGestureListener());
    }

    class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            if (e1.getX() - e2.getX() > verticalMinDistance
                    && Math.abs(velocityX) > minVelocity) {

                if (pageIndex < pageSize) {
                    pageIndex++;
                    step(pageIndex, true);
                }
            } else if (e2.getX() - e1.getX() > verticalMinDistance
                    && Math.abs(velocityX) > minVelocity) {

                if (pageIndex > 1) {
                    pageIndex--;
                    step(pageIndex, false);
                }
            }
            return false;
        }
    }

}
