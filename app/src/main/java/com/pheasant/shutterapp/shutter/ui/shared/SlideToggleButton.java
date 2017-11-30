package com.pheasant.shutterapp.shutter.ui.shared;

import android.animation.Animator;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Peszi on 2017-05-09.
 */
// TODO messed up
public class SlideToggleButton implements View.OnClickListener, Animator.AnimatorListener {

    public static final int RIGHT = 1;
    public static final int LEFT = -1;

    private ImageView imageButton;
    private int buttonOnResource;
    private int buttonOffResource;
    private int imageSlideDirection;
    private boolean isToggled;
    private boolean isShowed;
    private boolean showBack;

    private View.OnClickListener onClickListener;

    public SlideToggleButton(View view, int buttonId, int buttonResource, int imageSlideDirection) {
        this(view, buttonId, buttonResource, buttonResource, imageSlideDirection);
    }

    public SlideToggleButton(View view, int buttonId, int buttonOnResource, int buttonOffResource, int imageSlideDirection) {
        this.imageButton = (ImageView) view.findViewById(buttonId);
        this.buttonOnResource = buttonOnResource;
        this.buttonOffResource = buttonOffResource;
        this.imageButton.setOnClickListener(this);
        this.imageButton.animate().setListener(this);
        this.imageSlideDirection = imageSlideDirection;
        this.init();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void init() {
        this.isToggled = false;
        this.isShowed = true;
        this.imageButton.setImageResource(this.buttonOnResource);
        this.show();
    }

    public void hide() {
        this.isShowed = false;
        this.showBack = false;
        this.imageButton.animate().translationX(this.imageButton.getWidth() * imageSlideDirection);
    }

    public void hideAndShow() {
        this.isShowed = false;
        this.showBack = true;
        this.imageButton.animate().translationX(this.imageButton.getWidth() * imageSlideDirection);
    }

    public void show() {
        this.isShowed = false;
        this.showBack = false;
        this.imageButton.animate().translationX(0);
    }

    private void changeImage() {
        if (this.isToggled)
            this.imageButton.setImageResource(this.buttonOffResource);
        else
            this.imageButton.setImageResource(this.buttonOnResource);
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        if (this.showBack) {
            this.showBack = false;
            this.show();
            this.changeImage();
        } else {
            this.isShowed = true;
        }
    }

    @Override
    public void onClick(View v) {
        if (this.isShowed) {
            this.hideAndShow();
            this.isToggled = !this.isToggled;
            this.onClickListener.onClick(v);
        }
    }

    public boolean isToggled() {
        return this.isToggled;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}