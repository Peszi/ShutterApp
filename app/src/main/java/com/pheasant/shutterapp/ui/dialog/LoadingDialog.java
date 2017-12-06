package com.pheasant.shutterapp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.ui.util.DialogUtil;
import com.pheasant.shutterapp.util.Util;

/**
 * Created by Peszi on 2017-04-22.
 */

public class LoadingDialog implements View.OnClickListener, Runnable {

    private final int TIMEOUT = 5000;

    private Dialog dialog;
    private AnimationDrawable rocketAnimation;
    private TextView loadingMessage;
    private Button cancelButton;

    private int loadingMessageId;

    private Handler timeoutHandler;

    private LoadingDialogListener listener;

    public LoadingDialog(Context context, int loadingMessageId) {
        this.loadingMessageId = loadingMessageId;
        this.timeoutHandler = new Handler();
        this.dialog = DialogUtil.setupDialogWoStyle(context, R.layout.layout_dialog_loading);
        this.dialog.setCancelable(false);
        this.setupUI();
        Util.setupFont(context, this.dialog.getWindow().getDecorView(), Util.FONT_PATH_LIGHT);
    }

    private void setupUI() {
        final ImageView loadingImage = (ImageView) this.dialog.findViewById(R.id.loading_image);
        loadingImage.setBackgroundResource(R.drawable.logo_animation);
        this.rocketAnimation = (AnimationDrawable) loadingImage.getBackground();
        this.rocketAnimation.start();
        this.loadingMessage = (TextView) this.dialog.findViewById(R.id.loading_message);
        this.cancelButton = (Button) this.dialog.findViewById(R.id.loading_cancel_btn);
        this.cancelButton.setOnClickListener(this);
        this.setupLoadingMessage();
    }

    private void setupLoadingMessage() {
        this.loadingMessage.setText(this.loadingMessageId);
        this.cancelButton.setVisibility(View.INVISIBLE);
    }

    private void setupTimeoutMessage(int messageId) {
        this.timeoutHandler.removeCallbacksAndMessages(null);
        this.loadingMessage.setText(messageId);
        this.cancelButton.setText(R.string.loading_dialog_button_cancel);
        this.cancelButton.setVisibility(View.VISIBLE);
    }

    public void setupErrorMessage(String errorMessage) {
        this.timeoutHandler.removeCallbacksAndMessages(null);
        this.loadingMessage.setText(errorMessage);
        this.cancelButton.setText(R.string.loading_dialog_button_close);
        this.cancelButton.setVisibility(View.VISIBLE);
    }

    public void setListener(LoadingDialogListener listener) {
        this.listener = listener;
    }

    public void showDialog() {
        this.dialog.show();
        this.timeoutHandler.removeCallbacksAndMessages(null);
        this.timeoutHandler.postDelayed(this, this.TIMEOUT);
        this.setupLoadingMessage();
    }

    /* On Timeout */
    @Override
    public void run() {
        this.setupTimeoutMessage(R.string.loading_dialog_delay);
    }

    /* On Request Result */
    public void onSuccess() {
        this.dialog.hide();
    }

    public void onFail(String message) {
        this.setupErrorMessage(message);
    }

    @Override
    public void onClick(View v) {
        if (this.listener != null)
            this.listener.onLoadingCanceled();
        this.dialog.dismiss();
    }

    public interface LoadingDialogListener {
        void onLoadingCanceled();
    }
}