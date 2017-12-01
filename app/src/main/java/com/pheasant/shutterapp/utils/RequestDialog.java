package com.pheasant.shutterapp.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-04-22.
 */

public class RequestDialog {

    private Dialog dialog;
    private Request request;

    private TextView loadingMessage;
    private AnimationDrawable rocketAnimation;
    private Button cancelButton;
    private Handler timeoutHandler;

    public void showDialog(Context context, String message, int timeout, Request request){
        // Custom dialog
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.layout_dialog_loading);
        // Layout
        this.prepareCancelButtonListener();
        this.prepareImageAnimation();
        this.setupLoadingMessage(message);
        this.prepareTimeoutAction(timeout);
        this.setupFonts(context);
        // Request
        this.request = request;
    }

    public void prepareErrorMessage(String message) {
        this.timeoutHandler.removeCallbacksAndMessages(null); // remove timeout callback
        RequestDialog.this.loadingMessage.setText(message);
        RequestDialog.this.cancelButton.setText("close");
        RequestDialog.this.cancelButton.setVisibility(View.VISIBLE);
        this.dialog.show();
    }

    /* DISMISS DIALOG */
    public void dismissDailog() {
        this.dialog.dismiss();
    }

    /* CANCELING LISTENER (Dismiss dialog) */
    private void prepareCancelButtonListener() {
        this.cancelButton = (Button) this.dialog.findViewById(R.id.loading_cancel);
        this.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestDialog.this.request.cancel();
                RequestDialog.this.dialog.dismiss();
            }
        });
    }

    /* SETUP ANIMATION */
    private void prepareImageAnimation() {
        final ImageView loadingImage = (ImageView) this.dialog.findViewById(R.id.loading_image);
        loadingImage.setBackgroundResource(R.drawable.logo_animation);
        this.rocketAnimation = (AnimationDrawable) loadingImage.getBackground();
        this.rocketAnimation.start();
    }

    /* SETUP MESSAGE */
    public void setupLoadingMessage(String message) {
        this.loadingMessage = (TextView) this.dialog.findViewById(R.id.loading_message);
        this.loadingMessage.setText(message + "...");
    }

    /* TIMEOUT ACTION */
    private void prepareTimeoutAction(final int timeout) {
        this.timeoutHandler = new Handler();
        this.timeoutHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                RequestDialog.this.loadingMessage.setText(R.string.loading_dialog_delay);
                RequestDialog.this.cancelButton.setVisibility(View.VISIBLE);
            }
        }, timeout);
        this.dialog.show();
    }

    /* CHANGE FONTS */
    private void setupFonts(Context context) {
        Util.setupFont(context.getAssets(), this.loadingMessage);
        Util.setupFont(context.getAssets(), this.cancelButton);
    }
}