package com.pheasant.shutterapp.features.shutter.browse;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.io.PhotoFileManager;
import com.pheasant.shutterapp.shutter.ui.util.Avatar;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.Util;

public class PreviewActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_preview);
        Util.setupFont(this, this.getWindow().getDecorView(), Util.FONT_PATH_THIN);

        Bundle bundle = this.getIntent().getExtras();
        final int userPhotoId = bundle.getInt(IntentKey.USER_PHOTO);
        final String userName = bundle.getString(IntentKey.USER_NAME);
        final int userAvatar = bundle.getInt(IntentKey.USER_AVATAR);
        final String photoCreatedAt = bundle.getString(IntentKey.USER_PHOTO_TIME_LEFT);

        final ImageView photoPreview = (ImageView) this.findViewById(R.id.preview_image);
        this.setBitmap(photoPreview, userPhotoId);
        ((TextView) this.findViewById(R.id.preview_name)).setText(userName);
        ((ImageView) this.findViewById(R.id.preview_avatar)).setImageResource(Avatar.getAvatar(userAvatar));
        ((TextView) this.findViewById(R.id.preview_photo_expire)).setText(photoCreatedAt + " left");

        this.findViewById(R.id.preview_back_button).setOnClickListener(this);
    }

    private void setBitmap(ImageView photoPreview, int photoId) {
//        final Bitmap userPhoto = PhotoFileManager.loadPhotoFile(photoId, this);
//        if (userPhoto != null)
//            photoPreview.setImageBitmap(userPhoto);
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
