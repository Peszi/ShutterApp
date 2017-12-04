package com.pheasant.shutterapp.ui.shared;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-11-23.
 */

public class LoadingButton extends LinearLayout implements View.OnClickListener {

    private TextView buttonText;
    private ProgressBar buttonLoading;

    private OnClickListener buttonListener;

    private boolean state;

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.button_loading, this);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0);
        final String title = attributes.getString(R.styleable.LoadingButton_android_text);
        final int color = attributes.getColor(R.styleable.LoadingButton_android_color, 0);
        this.buttonText = (TextView) this.findViewById(R.id.button_loading_title);
        this.buttonText.setText(title);
        this.buttonText.setTextColor(color);
        this.buttonLoading = (ProgressBar) this.findViewById(R.id.button_loading_progress);
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
        this.buttonText.setVisibility(VISIBLE);
        this.buttonLoading.setVisibility(GONE);
    }

    private void setLoadingState() {
        this.buttonText.setVisibility(GONE);
        this.buttonLoading.setVisibility(VISIBLE);
    }
}
