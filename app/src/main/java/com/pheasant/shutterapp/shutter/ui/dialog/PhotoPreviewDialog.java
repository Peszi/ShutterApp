package com.pheasant.shutterapp.shutter.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.util.DialogUtil;

/**
 * Created by Peszi on 2017-12-03.
 */

public class PhotoPreviewDialog implements View.OnClickListener {

    private ImageView photoView;

    private Dialog dialog;

    public PhotoPreviewDialog(Context context) {
        this.dialog = DialogUtil.prepare(context, R.layout.layout_dialog_photo_preview);
//        this.dialog.findViewById(R.id.recipients_close_btn).setOnClickListener(this);
//        this.dialog.findViewById(R.id.recipients_send_btn).setOnClickListener(this);
        this.photoView = (ImageView) this.dialog.findViewById(R.id.dialog_photo);
    }

    public void showDialog(Bitmap photoBitmap) {
        if (this.photoView != null)
            this.photoView.setImageBitmap(photoBitmap);
        this.dialog.show();
    }

    @Override
    public void onClick(View v) {

    }
}
