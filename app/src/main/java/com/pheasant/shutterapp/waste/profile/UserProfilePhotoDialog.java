package com.pheasant.shutterapp.waste.profile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.shared.DialogUtil;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-05-25.
 */

public class UserProfilePhotoDialog implements View.OnClickListener {

    private Dialog dialog;

    public UserProfilePhotoDialog(Context context) {
        this.dialog = DialogUtil.prepare(context, R.layout.layout_dialog_user_photo);
        this.dialog.findViewById(R.id.user_photo_close_btn).setOnClickListener(this);;

        Util.setupFont(context, this.dialog.getWindow().getDecorView(), Util.FONT_PATH_THIN);
        Util.setupFont(context, this.dialog.findViewById(R.id.profile_container), Util.FONT_PATH_LIGHT);
    }

    public void showDialog(Bitmap bitmap) {
        ((ImageView) this.dialog.findViewById(R.id.user_photo_data)).setImageBitmap(bitmap);
        this.dialog.show();
    }

    @Override
    public void onClick(View v) {
        this.dialog.dismiss();
    }
}
