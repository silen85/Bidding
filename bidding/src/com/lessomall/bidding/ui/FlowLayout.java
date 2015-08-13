package com.lessomall.bidding.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by meisl on 2015/8/11.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        this(context, null);
        // TODO Auto-generated constructor stub
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        int width = getDefaultSize(getMeasuredWidth(), MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getMode(widthMeasureSpec)));
        int height = getPaddingTop() + getPaddingBottom();

        if (getChildCount() > 0) {
            int mCaculateWidth = 0;
            for (int i = 0; i < getChildCount(); i++) {

                View view = getChildAt(i);

                measureChild(view, widthMeasureSpec, heightMeasureSpec);

                int childWidth = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();

                MarginLayoutParams mlp = (MarginLayoutParams) view.getLayoutParams();
                int leftMargin = mlp.leftMargin;
                int topMargin = mlp.topMargin;
                int rightMargin = mlp.rightMargin;
                int bottomMargin = mlp.bottomMargin;

                if (i == 0) height += topMargin + childHeight + bottomMargin;

                mCaculateWidth += leftMargin + childWidth + rightMargin;
                if (width <= mCaculateWidth) {
                    mCaculateWidth = leftMargin + childWidth + rightMargin;
                    height += topMargin + childHeight + bottomMargin;
                }
            }
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub

        int width = getMeasuredWidth();
        if (getChildCount() > 0) {
            int mCaculateWidth = 0;
            int mCaculateHeight = 0;
            for (int i = 0; i < getChildCount(); i++) {

                View view = getChildAt(i);

                int childWidth = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();

                MarginLayoutParams mlp = (MarginLayoutParams) view.getLayoutParams();
                int leftMargin = mlp.leftMargin;
                int topMargin = mlp.topMargin;
                int rightMargin = mlp.rightMargin;
                int bottomMargin = mlp.bottomMargin;

                if (i == 0) mCaculateHeight += topMargin + childHeight + bottomMargin;

                mCaculateWidth += leftMargin + childWidth + rightMargin;
                if (width <= mCaculateWidth) {
                    mCaculateWidth = leftMargin + childWidth + rightMargin;
                    mCaculateHeight += topMargin + childHeight + bottomMargin;
                }

                view.layout(mCaculateWidth - childWidth - rightMargin, mCaculateHeight - bottomMargin - childHeight, mCaculateWidth - rightMargin, mCaculateHeight - bottomMargin);

            }
        }
    }
}
