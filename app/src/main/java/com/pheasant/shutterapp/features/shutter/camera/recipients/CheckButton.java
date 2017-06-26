package com.pheasant.shutterapp.features.shutter.camera.recipients;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-05-19.
 */

public class CheckButton extends ToggleButton implements View.OnClickListener {

    private final String ON_TEXT = "uncheck";
    private final String OFF_TEXT = "check";

    private ChangeListener changeListener;

    public CheckButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTextOn(this.ON_TEXT);
        this.setTextOff(this.OFF_TEXT);
        this.setOnClickListener(this);
    }

    public void setChangeListener(ChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    @Override
    public void onClick(View v) {
        final ToggleButton button = ((ToggleButton) v);
        if (button.getText() == button.getTextOff()) {
            this.setTextColor(Color.BLACK);
            this.setBackgroundResource(R.drawable.all_dark_set_btn_back);
        } else {
            this.setTextColor(Color.WHITE);
            this.setBackgroundResource(R.drawable.all_dark_reset_btn_back);
        }
        this.changeListener.onStateChanged();
    }

    public interface ChangeListener {
        void onStateChanged();
    }
}
