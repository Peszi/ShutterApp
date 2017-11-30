package com.pheasant.shutterapp.shutter.ui.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-05-25.
 */

public class DialogUtil {

    public static Dialog prepare(Context context, int layoutId) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(layoutId);
        dialog.setCancelable(true);
        Util.setupFont(context, dialog.getWindow().getDecorView(), Util.FONT_PATH_LIGHT);
        return dialog;
    }
}
