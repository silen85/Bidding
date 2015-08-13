package com.lessomall.bidding.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lessomall.bidding.R;


public class SplashView extends LinearLayout {

	public SplashView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public SplashView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	private void initView(Context context) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.activity_splash, this);

		ImageView splash_progress = (ImageView) findViewById(R.id.splash_progress);
		Animation roteAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.roat);
		LinearInterpolator lir = new LinearInterpolator();
		roteAnimation.setInterpolator(lir);
		splash_progress.startAnimation(roteAnimation);

	}

}
