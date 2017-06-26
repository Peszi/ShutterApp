package com.pheasant.shutterapp.features.shutter.browse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.browse.utils.ImagesAdapter;
import com.pheasant.shutterapp.features.shutter.browse.utils.PhotosAdapter;
import com.pheasant.shutterapp.features.shutter.camera.CameraFragment;
import com.pheasant.shutterapp.features.shutter.ShutterFragment;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.photos.PhotoUploadRequest;
import com.pheasant.shutterapp.network.request.util.OnRequestResultListener;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-04-24.
 */

public class PhotosFragment extends ShutterFragment implements AdapterView.OnItemClickListener, View.OnClickListener, CameraFragment.CameraActionListener {

//    private ImagesAdapter imagesAdapter;
//    private GridView gridView;

    //private TextView title;
//      this.imagesAdapter = new ImagesAdapter(this.getContext());
//        this.gridView = (GridView) view.findViewById(R.id.browse_images);
//        this.gridView.setAdapter(this.imagesAdapter);
//        this.gridView.setOnItemClickListener(this);

    private RecyclerView photosList;
    private PhotosAdapter photosAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_photos_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.photosList = (RecyclerView) view.findViewById(R.id.browse_photos);
        this.photosList.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(this.getContext());
        this.photosList.setLayoutManager(this.layoutManager);


        this.photosAdapter = new PhotosAdapter(this.getContext(), this.getArguments());
        this.photosList.setAdapter(this.photosAdapter);
        this.photosList.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onShow() {
        this.photosAdapter.reload();
        this.scrollToTheTop();
    }

    private void scrollToTheTop() {
        if (this.photosList != null)
            this.photosList.smoothScrollToPosition(0);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        this.startPhotoPreview(this.imagesAdapter.getItem(position));
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.browse_profile_btn && this.dataListener != null) {
//            this.profileDialog.showDialog(this.dataListener.getUserData());
//        }
    }

    private void startPhotoPreview(ImagesAdapter.Thumbnail thumbnail) {
        if (thumbnail != null && thumbnail.bitmap != null) {
            Intent intent = new Intent(this.getActivity(), PreviewActivity.class);
            intent.putExtra(IntentKey.USER_PHOTO, thumbnail.photoId);
            intent.putExtra(IntentKey.USER_AVATAR, thumbnail.avatar);
            intent.putExtra(IntentKey.USER_NAME, thumbnail.name);
            intent.putExtra(IntentKey.USER_PHOTO_TIME_LEFT, thumbnail.getTimeLeft());
            this.startActivity(intent);
        }
    }

    @Override
    public void onPhotoDone(Bitmap bitmap, List<Integer> recipients) {
        PhotoUploadRequest uploadRequest = new PhotoUploadRequest(bitmap, recipients, this.getArguments().getString(IntentKey.USER_API_KEY));
        uploadRequest.setOnRequestResultListener(new OnRequestResultListener() {
            @Override
            public void onResult(int resultCode) {

            }
        });
        uploadRequest.execute();
    }

    @Override
    public List<UserData> getFriendsData() {
        List<UserData> userDatas = new ArrayList<>();
        UserData userData = new UserData();
        userData.setId(1);
        userData.setName("Peszi");
        userData.setAvatar(1);
        userDatas.add(userData);
        return userDatas;
    }
}