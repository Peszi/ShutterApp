package com.pheasant.shutterapp.ui.shared;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-11-22.
 */

public class LoadingToggleButton extends LinearLayout implements View.OnClickListener {

    private TextView buttonTitle;
    private ProgressBar buttonLoading;

    private OnToggleClickListener inviteListener;

    private String titleOn, titleOff;
    private int colorOn, colorOff;
    private int backgroundOn, backgroundOff;

    private int state;

    public LoadingToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.button_loading_toggle, this);
        this.getAttributes(context, attrs);
        this.buttonTitle = (TextView) this.findViewById(R.id.button_loading_toggle_title);
        this.buttonLoading = (ProgressBar) this.findViewById(R.id.button_loading_toggle_progress);
        this.setButtonState(0);
        this.setOnClickListener(this);
    }

    public void setListener(OnToggleClickListener inviteListener) {
        this.inviteListener = inviteListener;
    }

    public void setButtonState(int state) {
        this.state = state;
        if (this.state == 0) {
            this.buttonTitle.setText(this.titleOn);
            this.buttonTitle.setTextColor(this.colorOn);
            this.setProgressBarColor(this.colorOn);
            this.setBackgroundResource(this.backgroundOn);
        } else {
            this.buttonTitle.setText(this.titleOff);
            this.buttonTitle.setTextColor(this.colorOff);
            this.setProgressBarColor(this.colorOff);
            this.setBackgroundResource(this.backgroundOff);
        }
        this.buttonTitle.setVisibility(VISIBLE);
        this.buttonLoading.setVisibility(GONE);
    }

    @Override
    public void onClick(View v) {
        if (this.state < 2) {
            this.setupLoadingState();
            if (this.inviteListener != null)
                this.inviteListener.onClickEvent(this.state, v);
            this.state = 2;
        }
    }

    private void setupLoadingState() {
        this.buttonTitle.setVisibility(GONE);
        this.buttonLoading.setVisibility(VISIBLE);
    }

    private void setProgressBarColor(int color) {
        this.buttonLoading.getIndeterminateDrawable().setColorFilter(color, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    private void getAttributes(Context context, AttributeSet attributeSet) {
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.LoadingToggleButton, 0, 0);
        this.titleOn = attributes.getString(R.styleable.LoadingToggleButton_titleOn);
        this.titleOff = attributes.getString(R.styleable.LoadingToggleButton_titleOff);
        this.colorOn = attributes.getColor(R.styleable.LoadingToggleButton_colorOn, Color.RED);
        this.colorOff = attributes.getColor(R.styleable.LoadingToggleButton_colorOff, Color.RED);
        this.backgroundOn = attributes.getResourceId(R.styleable.LoadingToggleButton_backgroundSrcOn, 0);
        this.backgroundOff = attributes.getResourceId(R.styleable.LoadingToggleButton_backgroundSrcOff, 0);
        attributes.recycle();
    }

    public interface OnToggleClickListener {
        void onClickEvent(int state, View view);
    }
}