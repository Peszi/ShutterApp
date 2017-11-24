package com.pheasant.shutterapp.shutter.ui.shared;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-11-23.
 */

public class LoadingImageButton extends LinearLayout implements View.OnClickListener {

    private ImageView buttonIcon;
    private ProgressBar buttonLoading;

    private OnClickListener buttonListener;

    private boolean state;

    public LoadingImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.button_image_loading, this);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingImageButton, 0, 0);
        final int src = attributes.getResourceId(R.styleable.LoadingImageButton_android_src, 0);
        final int color = attributes.getColor(R.styleable.LoadingImageButton_android_color, 0);
        this.buttonIcon = (ImageView) this.findViewById(R.id.button_image_loading_icon);
        this.buttonIcon.setImageResource(src);
        this.buttonLoading = (ProgressBar) this.findViewById(R.id.button_image_loading_progress);
        this.buttonLoading.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
        attributes.recycle();
        this.setOnClickListener(this);
    }

    public void setButtonListener(OnClickListener buttonListener) {
        this.buttonListener = buttonListener;
    }

    public void initButton() {
        this.state = false;
        this.setButtonState();
    }

    @Override
    public void onClick(View v) {
        if (!this.state) {
            this.setLoadingState();
            this.state = true;
        }
        if (this.buttonListener != null)
            this.buttonListener.onClick(v);
    }

    private void setButtonState() {
        this.buttonIcon.setVisibility(VISIBLE);
        this.buttonLoading.setVisibility(GONE);
    }

    private void setLoadingState() {
        this.buttonIcon.setVisibility(GONE);
        this.buttonLoading.setVisibility(VISIBLE);
    }
}