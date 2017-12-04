package com.pheasant.shutterapp.ui.features.browse;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.data.PhotoData;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView thumbnailPhoto;
    private TextView userName;
    private TextView photoTimestamp;
    private ImageButton imageButton;

    private ProgressBar progressBar;
    private ImageView corruptedIcon;

    private int photoId;

    private PhotoViewListener viewListener;

    public PhotoViewHolder(View view) {
        super(view);
        // Photo data
        this.thumbnailPhoto = (ImageView) view.findViewById(R.id.card_photo);
        this.userName = (TextView) view.findViewById(R.id.card_panel_user_name);
        this.photoTimestamp = (TextView) view.findViewById(R.id.card_panel_timestamp);
        this.imageButton = (ImageButton) view.findViewById(R.id.card_panel_delete);
        // UI ctrl
        this.progressBar = (ProgressBar) view.findViewById(R.id.card_progress);
        this.corruptedIcon = (ImageView) view.findViewById(R.id.card_corrupted);
    }

    public void setListener(PhotoViewListener viewListener) {
        this.viewListener = viewListener;
    }

    public void setupData(PhotoData photoData) {
        this.photoId = photoData.getImageId();
        this.thumbnailPhoto.setImageResource(R.drawable.back_blur_default);
        this.thumbnailPhoto.setOnClickListener(this);
        this.userName.setText(photoData.getCreatorName());
        this.photoTimestamp.setText(photoData.getLiveTime());
        if (photoData.isMe()) {
            this.imageButton.setVisibility(View.VISIBLE);
            this.imageButton.setOnClickListener(this);
        } else { this.imageButton.setVisibility(View.GONE); }
        this.progressBar.setVisibility(View.VISIBLE);
        this.corruptedIcon.setVisibility(View.GONE);
    }

    public void setupPhoto(Bitmap bitmap) {
        this.progressBar.setVisibility(View.GONE);
        if (bitmap != null) {
            this.thumbnailPhoto.setImageBitmap(bitmap);
        } else {
            this.corruptedIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (this.viewListener != null) {
            switch (v.getId()) {
                case R.id.card_photo: this.viewListener.onStartPreview(this.photoId); break;
                case R.id.card_panel_delete: this.viewListener.onPhotoRemove(this.photoId); break;
            }
        }
    }

    public interface PhotoViewListener {
        void onStartPreview(int photoId);
        void onPhotoRemove(int photoId);
    }
}
