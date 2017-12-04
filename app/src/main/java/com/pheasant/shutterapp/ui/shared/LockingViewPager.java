package com.pheasant.shutterapp.ui.shared;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.pheasant.shutterapp.ui.interfaces.ViewPagerInterface;

/**
 * Created by Peszi on 2017-05-09.
 */

public class LockingViewPager extends ViewPager implements ViewPagerInterface {

    public LockingViewPager(Context context) {
        super(context);
    }

    public LockingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isEnabled() && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isEnabled() && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return isEnabled() && super.canScrollHorizontally(direction);
    }

    @Override
    public void enablePager(boolean enable) {
        this.setEnabled(enable);
    }
}