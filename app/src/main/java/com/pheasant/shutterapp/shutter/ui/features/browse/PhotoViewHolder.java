package com.pheasant.shutterapp.shutter.ui.features.browse;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.download.user.UserPhoto;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    private ImageView thumbnail;
    private TextView title;
    private ProgressBar progressBar;
    private ImageView corruptedIcon;

    public PhotoViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.card_image);
        this.title = (TextView) view.findViewById(R.id.card_text);
        this.progressBar = (ProgressBar) view.findViewById(R.id.card_progress);
        this.corruptedIcon = (ImageView) view.findViewById(R.id.card_corrupted);
    }

    public void setupData(PhotoData photoData) {
        this.title.setText("ID " + photoData.getImageId());
        this.thumbnail.setImageResource(R.drawable.back_blur_default);
        this.progressBar.setVisibility(View.VISIBLE);
        this.corruptedIcon.setVisibility(View.GONE);
    }

    public void setupPhoto(Bitmap bitmap) {
        this.progressBar.setVisibility(View.GONE);
        if (bitmap != null) {
            this.thumbnail.setImageBitmap(bitmap); //Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2 - bitmap.getWidth() / 2, bitmap.getWidth(), bitmap.getWidth())
//            bitmap.recycle();
        } else {
            this.corruptedIcon.setVisibility(View.VISIBLE);
        }
    }
}
