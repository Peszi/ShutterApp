package com.pheasant.shutterapp.waste.profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.download.user.UserPhoto;
import com.pheasant.shutterapp.utils.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Peszi on 2017-05-23.
 */

public class ProfilePhotosAdapter extends ArrayAdapter<UserPhoto> implements UserPhoto.UserPhotoListener {

    private GridView photosGrid;
    private LayoutInflater layoutInflater;

    public ProfilePhotosAdapter(Context context, GridView photosGrid) {
        super(context, R.layout.layout_photo);
        this.layoutInflater = LayoutInflater.from(context);
        this.photosGrid = photosGrid;
        this.photosGrid.setAdapter(this);
    }

    public void init() {
        this.photosGrid.smoothScrollToPosition(0);
    }

    public void addPhotos(List<UserPhoto> userPhotos) {
        for (UserPhoto userPhoto : userPhotos)
            this.addPhoto(userPhoto);
        this.notifyDataSetChanged();
    }

    public void addPhoto(UserPhoto userPhoto) {
        int index = this.getIndex(userPhoto.getImageId());
        if (index < 0) {
            if (this.getCount() > 0 && this.isNewer(this.getItem(0), userPhoto)) // TODO compare time
                this.add(userPhoto);
            else
                this.insert(userPhoto, 0);
            userPhoto.setPhotoListener(this);
        } else {
            this.getItem(index).update(userPhoto);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.layout_photo, parent, false);
//            view.setTag(R.id.profile_photo, view.findViewById(R.id.profile_photo));
//            view.setTag(R.id.profile_photo_progress, view.findViewById(R.id.profile_photo_progress));
//            view.setTag(R.id.profile_photo_time, view.findViewById(R.id.profile_photo_time));
        }
        Util.setupFont(this.getContext(), view, Util.FONT_PATH_LIGHT);
        this.setupPhotoView(view, this.getItem(position));
        return view;
    }

    private void setupPhotoView(View view, UserPhoto userPhoto) {
//        if (userPhoto != null) {
//            final ImageView photo = (ImageView) view.getTag(R.id.profile_photo);
//            final TextView time = (TextView) view.getTag(R.id.profile_photo_time);
//            if (userPhoto.getBitmap() != null) {
//                photo.setImageBitmap(userPhoto.getBitmap());
//                time.setText(userPhoto.getExistTime());
//                time.setVisibility(View.VISIBLE);
//                view.setVisibility(View.VISIBLE);
//            } else {
//                photo.setImageResource(R.drawable.image_exist);
//                time.setVisibility(View.GONE);
//                view.setVisibility(View.GONE);
//            }
//            final ProgressBar progress = (ProgressBar) view.getTag(R.id.profile_photo_progress);
//            if (userPhoto.isLoading()) {
//                if (view != null) {
//                    Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.slow_pop_up);
//                    animation.start();
//                    view.setAnimation(animation);
//                }
//                //progress.setVisibility(View.VISIBLE);
//                view.setVisibility(View.VISIBLE);
//            } else {
//                progress.setVisibility(View.GONE);
//            }
//        }
    }

    @Override
    public void onLoading(int photoId) {}

    @Override
    public void onLoaded(int photoId, Bitmap bitmap) {
        this.notifyDataSetChanged();
    }

    private int getIndex(int photoId) {
        for (int i = 0; i < this.getCount(); i++) {
            if (this.getItem(i).getImageId() == photoId)
                return i;
        }
        return -1;
    }

    private boolean isNewer(UserPhoto userPhotoA, UserPhoto userPhotoB) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 2016-05-19 11:25:53
        try {
            Date dateA = format.parse(userPhotoA.getDate());
            Date dateB = format.parse(userPhotoB.getDate());
            return dateA.after(dateB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public GridView getPhotosGrid() {
        return this.photosGrid;
    }
}