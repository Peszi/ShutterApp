package com.pheasant.shutterapp.shutter.ui.features.browse;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView thumbnail;
    private TextView title;
    private ProgressBar progressBar;
    private ImageView corruptedIcon;

    private int photoId;

    private PhotoViewListener viewListener;

    public PhotoViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.card_photo);
        this.title = (TextView) view.findViewById(R.id.card_panel_text);
        this.progressBar = (ProgressBar) view.findViewById(R.id.card_progress);
        this.corruptedIcon = (ImageView) view.findViewById(R.id.card_corrupted);
    }

    public void setListener(PhotoViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void setupData(PhotoData photoData) {
        this.photoId = photoData.getImageId();
        this.title.setText(photoData.getCreatorName());
        this.thumbnail.setImageResource(R.drawable.back_blur_default);
        this.thumbnail.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (this.viewListener != null)
            this.viewListener.onStartPreview(this.photoId);
    }

    public interface PhotoViewListener {
        void onStartPreview(int photoId);
    }
}
