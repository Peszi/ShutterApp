package com.pheasant.shutterapp.waste.profile;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.download.user.UserData;
import com.pheasant.shutterapp.network.download.user.UserDataListener;
import com.pheasant.shutterapp.network.download.user.UserPhoto;
import com.pheasant.shutterapp.network.download.user.UserPhotosRequester;
import com.pheasant.shutterapp.utils.DialogUtil;
import com.pheasant.shutterapp.utils.Util;

import java.util.List;

/**
 * Created by Peszi on 2017-05-18.
 */

public class UserProfileDialog implements View.OnClickListener, UserDataListener, AdapterView.OnItemClickListener {

    private Dialog dialog;
    private ImageView userAvatar;
    private TextView userName;
    private TextView userEmail;
    private TextView userCreatedAt;

    private ProfilePhotosAdapter photosAdapter;
    private UserProfilePhotoDialog photoDialog;

    private UserPhotosRequester photosRequester;

    public UserProfileDialog() {}

    public void setupView(Context context) {
        this.dialog = DialogUtil.prepare(context, R.layout.layout_dialog_profile);
        this.dialog.findViewById(R.id.profile_close_btn).setOnClickListener(this);
        this.dialog.findViewById(R.id.profile_refresh_btn).setOnClickListener(this);
        this.userAvatar = (ImageView) this.dialog.findViewById(R.id.profile_user_avatar);
        this.userName = (TextView) this.dialog.findViewById(R.id.profile_user_name);
        this.userEmail = (TextView) this.dialog.findViewById(R.id.profile_user_email);
        this.userCreatedAt = (TextView) this.dialog.findViewById(R.id.profile_user_with_us);

        this.photosAdapter = new ProfilePhotosAdapter(context, (GridView) this.dialog.findViewById(R.id.profile_photos));
        this.photosAdapter.getPhotosGrid().setOnItemClickListener(this);

        Util.setupFont(context, this.dialog.getWindow().getDecorView(), Util.FONT_PATH_THIN);
        Util.setupFont(context, this.dialog.findViewById(R.id.profile_container), Util.FONT_PATH_LIGHT);

        this.photoDialog = new UserProfilePhotoDialog(context);
    }

    public void setUserPhotosRequester(UserPhotosRequester photosRequester) {
        this.photosRequester = photosRequester;
    }

    public void showDialog(UserData userData) {
        this.photosRequester.reloadPhotosList();
        if (userData != null) {
            this.userName.setText(userData.getName());
            this.userEmail.setText(userData.getEmail());
            this.userCreatedAt.setText("since " + userData.getCreated().split(" ")[0]);
        }
        this.dialog.show();
        this.photosAdapter.init();
    }

    @Override
    public void onListLoaded(List<UserPhoto> userPhotos) {
        Log.d("RESPONSE", "USER PHOTOS LOADED");
        this.photosAdapter.addPhotos(userPhotos);
        this.showPhotosCount();
    }

    @Override
    public void onPhotoLoaded(int photoId, Bitmap bitmap) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_close_btn: this.dialog.hide(); break;
            case R.id.profile_refresh_btn: this.photosRequester.reloadPhotosList(); break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.photoDialog.showDialog(this.photosAdapter.getItem(position).getBitmap());
    }

    private void showPhotosCount() {
        final int count = this.photosAdapter.getCount();
        ((TextView) this.dialog.findViewById(R.id.profile_photos_count)).setText("(" + count + ")");
    }
}
