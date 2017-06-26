package com.pheasant.shutterapp.features.shutter.browse.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-05-08.
 */

public class ThumbnailAnimation implements Animation.AnimationListener {

    private Animation animation;
    private View barView;

    public ThumbnailAnimation(Context context, View barView) {
        this.animation = AnimationUtils.loadAnimation(context, R.anim.pop_up);
        this.animation.setAnimationListener(this);
        this.barView = barView;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        this.barView.setVisibility(View.VISIBLE);
    }

    public Animation getAnimation() {
        return this.animation;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
