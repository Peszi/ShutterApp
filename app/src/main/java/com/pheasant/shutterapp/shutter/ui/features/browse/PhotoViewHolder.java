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

    public PhotoViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.card_image);
        this.title = (TextView) view.findViewById(R.id.card_text);
        this.progressBar = (ProgressBar) view.findViewById(R.id.card_progress);
    }

    public void setupData(PhotoData photoData) {
        this.title.setText("ID " + photoData.getImageId());
        this.thumbnail.setImageResource(R.drawable.image_exist);
        this.progressBar.setVisibility(View.VISIBLE);
    }

    public void setupPhoto(Bitmap bitmap) {
        this.progressBar.setVisibility(View.GONE);
        this.thumbnail.setImageBitmap(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight()/2 - bitmap.getWidth()/2, bitmap.getWidth(), bitmap.getWidth()));
        bitmap.recycle();
    }
}
