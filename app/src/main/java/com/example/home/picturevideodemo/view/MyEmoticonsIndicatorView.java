package com.example.home.picturevideodemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.home.picturevideodemo.R;

import java.util.ArrayList;
import java.util.Iterator;

import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

public class MyEmoticonsIndicatorView extends LinearLayout {
    protected Context mContext;
    protected ArrayList<ImageView> mImageViews;
    protected Drawable mDrawableSelect;
    protected Drawable mDrawableNomal;
    protected LayoutParams mLeftLayoutParams;

    @SuppressLint("WrongConstant")
    public MyEmoticonsIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.setOrientation(0);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.EmoticonsIndicatorView, 0, 0);

        try {
            this.mDrawableSelect = a.getDrawable(R.styleable.EmoticonsIndicatorView_bmpSelect);
            this.mDrawableNomal = a.getDrawable(R.styleable.EmoticonsIndicatorView_bmpNomal);
        } finally {
            a.recycle();
        }

        if (this.mDrawableNomal == null) {
            this.mDrawableNomal = this.getResources().getDrawable(R.mipmap.indicator_point_nomal);
        }

        if (this.mDrawableSelect == null) {
            this.mDrawableSelect = this.getResources().getDrawable(R.mipmap.indicator_point_select);
        }

        this.mLeftLayoutParams = new LayoutParams(-2, -2);
        this.mLeftLayoutParams.leftMargin = EmoticonsKeyboardUtils.dip2px(context, 4.0F);
    }

    public void playTo(int position, PageSetEntity pageSetEntity) {
        if (this.checkPageSetEntity(pageSetEntity)) {
            this.updateIndicatorCount(pageSetEntity.getPageCount());
            Iterator i$ = this.mImageViews.iterator();

            while (i$.hasNext()) {
                ImageView iv = (ImageView) i$.next();
                iv.setImageDrawable(this.mDrawableNomal);
            }

            ((ImageView) this.mImageViews.get(position)).setImageDrawable(this.mDrawableSelect);
        }
    }

    public void playBy(int startPosition, int nextPosition, PageSetEntity pageSetEntity) {
        if (this.checkPageSetEntity(pageSetEntity)) {
            this.updateIndicatorCount(pageSetEntity.getPageCount());
            if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
                nextPosition = 0;
                startPosition = 0;
            }

            if (startPosition < 0) {
                nextPosition = 0;
                startPosition = 0;
            }

            ImageView imageViewStrat = (ImageView) this.mImageViews.get(startPosition);
            ImageView imageViewNext = (ImageView) this.mImageViews.get(nextPosition);
            imageViewStrat.setImageDrawable(this.mDrawableNomal);
            imageViewNext.setImageDrawable(this.mDrawableSelect);
        }
    }

    @SuppressLint("WrongConstant")
    protected boolean checkPageSetEntity(PageSetEntity pageSetEntity) {
        if (pageSetEntity != null && pageSetEntity.isShowIndicator()) {
            this.setVisibility(0);
            return true;
        } else {
            this.setVisibility(8);
            return false;
        }
    }

    @SuppressLint("WrongConstant")
    protected void updateIndicatorCount(int count) {
        if (this.mImageViews == null) {
            this.mImageViews = new ArrayList();
        }

        int i;
        if (count > this.mImageViews.size()) {
            for (i = this.mImageViews.size(); i < count; ++i) {
                ImageView imageView = new ImageView(this.mContext);
                imageView.setImageDrawable(i == 0 ? this.mDrawableSelect : this.mDrawableNomal);
                this.addView(imageView, this.mLeftLayoutParams);
                this.mImageViews.add(imageView);
            }
        }

        for (i = 0; i < this.mImageViews.size(); ++i) {
            if (i >= count) {
                ((ImageView) this.mImageViews.get(i)).setVisibility(8);
            } else {
                ((ImageView) this.mImageViews.get(i)).setVisibility(0);
            }
        }

    }
}
