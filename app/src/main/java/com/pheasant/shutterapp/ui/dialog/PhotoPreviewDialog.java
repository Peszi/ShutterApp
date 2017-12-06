package com.pheasant.shutterapp.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.ui.util.DialogUtil;

/**
 * Created by Peszi on 2017-12-03.
 */

public class PhotoPreviewDialog implements View.OnClickListener {

    private ImageView photoView;
    private Dialog dialog;

    public PhotoPreviewDialog(Context context) {
        this.dialog = DialogUtil.setupDialog(context, R.layout.layout_dialog_photo_preview);
        this.photoView = (ImageView) this.dialog.findViewById(R.id.dialog_preview_photo);
        this.dialog.findViewById(R.id.dialog_preview_btn_close).setOnClickListener(this);
    }

    public void showDialog(Bitmap photoBitmap) {
        if (this.photoView != null)
            this.photoView.setImageBitmap(photoBitmap);
        this.dialog.show();
    }

    @Override
    public void onClick(View v) {
        this.dialog.hide();
    }
}
