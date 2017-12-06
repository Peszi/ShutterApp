package com.pheasant.shutterapp.ui.features.browse;

import android.graphics.Bitmap;
import android.os.Handler;
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

public class PhotoViewHolder extends RecyclerView.ViewHolder implements Runnable, View.OnClickListener, View.OnLongClickListener {

    private final int TIMEOUT = 3000;

    private ImageView thumbnailPhoto;
    private TextView userName;
    private TextView photoTimestamp;
    private ImageButton imageButton;

    private ProgressBar progressBar;
    private ImageView corruptedIcon;

    private int photoId;

    private Handler timeoutHandler;
    private PhotoViewListener viewListener;

    public PhotoViewHolder(View view) {
        super(view);
        this.timeoutHandler = new Handler();
        // Photo data
        this.thumbnailPhoto = (ImageView) view.findViewById(R.id.card_photo);
        this.thumbnailPhoto.setOnClickListener(this);
        this.thumbnailPhoto.setOnLongClickListener(this);
        this.userName = (TextView) view.findViewById(R.id.card_panel_user_name);
        this.photoTimestamp = (TextView) view.findViewById(R.id.card_panel_timestamp);
        this.imageButton = (ImageButton) view.findViewById(R.id.card_panel_delete);
        this.imageButton.setOnClickListener(this);
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
        this.userName.setText(photoData.getCreatorName());
        this.photoTimestamp.setText(photoData.getLiveTime());
        if (photoData.isMe()) { this.imageButton.setVisibility(View.VISIBLE); }
        else { this.imageButton.setVisibility(View.GONE); }
        this.setupLoadingState();
        this.timeoutHandler.postDelayed(this, this.TIMEOUT);
    }

    private void setupLoadingState() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.corruptedIcon.setVisibility(View.GONE);
    }

    public void setupPhoto(Bitmap bitmap) {
        this.progressBar.setVisibility(View.GONE);
        if (bitmap != null) {
            this.thumbnailPhoto.setImageBitmap(bitmap);
            this.timeoutHandler.removeCallbacksAndMessages(null);
        } else {
            this.corruptedIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (this.viewListener != null) {
            switch (v.getId()) {
                case R.id.card_photo: this.viewListener.onStartPreviewEvent(this.photoId); break;
                case R.id.card_panel_delete: this.viewListener.onPhotoRemoveEvent(this.photoId); break;
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        this.setupLoadingState();
        if (this.viewListener != null)
            this.viewListener.onPhotoIdShowEvent(this.photoId);
        return true;
    }

    @Override
    public void run() {
        if (this.viewListener != null)
            this.viewListener.onThumbnailReloadEvent(this.photoId);
    }

    public interface PhotoViewListener {
        void onStartPreviewEvent(int photoId);
        void onPhotoRemoveEvent(int photoId);
        void onThumbnailReloadEvent(int photoId);
        void onPhotoIdShowEvent(int photoId);
    }
}
