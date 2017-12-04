package com.pheasant.shutterapp.ui.listeners;

import android.content.Context;

/**
 * Created by Peszi on 2017-11-27.
 */

public interface CameraManageViewListener {
    void setupView(Context context);
    void onPageShow();
    boolean onBackBtn();
}
