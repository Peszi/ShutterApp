package com.pheasant.shutterapp.features.shutter.browse.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.download.FriendsPhotosDownloader;
import com.pheasant.shutterapp.network.request.data.PhotoData;
import com.pheasant.shutterapp.network.request.photos.FriendsPhotosRequest;
import com.pheasant.shutterapp.network.request.util.OnRequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.shared.views.SquareImageView;
import com.pheasant.shutterapp.utils.IntentKey;
import com.pheasant.shutterapp.utils.TimeStamp;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.Calendar.DAY_OF_WEEK;

/**
 * Created by Peszi on 2017-06-21.
 */

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnRequestResultListener, FriendsPhotosDownloader.OnDownloadListener {

    private FriendsPhotosRequest photosRequest;
    private FriendsPhotosDownloader photosDownloader;

    private LayoutInflater layoutInflater;
    private Context context;
    private String apiKey;

    private List<PhotoData> photosList = new ArrayList<>();
    private Map<Integer, String> spacersList = new HashMap<>();

    public PhotosAdapter(Context context, Bundle bundle) {
        this.context = context;
        this.apiKey = bundle.getString(IntentKey.USER_API_KEY);
        this.layoutInflater = LayoutInflater.from(context);
        this.photosRequest = new FriendsPhotosRequest(this.apiKey);
        this.photosRequest.setOnRequestResultListener(this);
        this.photosDownloader = new FriendsPhotosDownloader(context, this.apiKey);
        this.photosDownloader.setOnDownloadListener(this);
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {

        public SquareImageView photoData;
        public TextView photoCreator;
        public TextView photoTime;

        public PhotoViewHolder(View view) {
            super(view);
            this.photoData = (SquareImageView) view.findViewById(R.id.photo_image);
            this.photoCreator = (TextView) view.findViewById(R.id.photo_creator);
            this.photoTime = (TextView) view.findViewById(R.id.photo_time);
        }
    }

    public static class SpacerViewHolder extends RecyclerView.ViewHolder {

        public TextView spacerTitle;

        public SpacerViewHolder(View view) {
            super(view);
            this.spacerTitle = (TextView) view.findViewById(R.id.spacer_title);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (this.photosList.get(position) != null)
            return 0;
        return 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseView = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                baseView = this.layoutInflater.inflate(R.layout.layout_photo, parent, false);
                viewHolder = new PhotoViewHolder(baseView);
                break;
            default:
                baseView = this.layoutInflater.inflate(R.layout.layout_photo_title, parent, false);
                viewHolder = new SpacerViewHolder(baseView);
                break;
        }
        if (viewHolder != null)
            Util.setupFont(this.context, baseView, Util.FONT_PATH_LIGHT);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // setup image
        switch (holder.getItemViewType()) {
            case 0:
                PhotoViewHolder photoHolder = (PhotoViewHolder) holder;
                PhotoData photoData = this.photosList.get(position);
                if (this.photosDownloader.canGetImage(photoData.getImageId())) {
                    photoHolder.photoData.setImageBitmap(this.photosDownloader.getPhotoThumbnail(photoData.getImageId()));
                    ThumbnailAnimation animation = new ThumbnailAnimation(this.context, photoHolder.itemView);
                    photoHolder.photoData.startAnimation(animation.getAnimation());
                } else {
                    photoHolder.photoData.setImageResource(R.drawable.image_exist);
                }
                // setup data
                photoHolder.photoCreator.setText("by " + photoData.getCreatorName());
                photoHolder.photoTime.setText(TimeStamp.getLiveTime(photoData.getDate()));
                break;
            default:
                SpacerViewHolder spacerHolder = (SpacerViewHolder) holder;
                spacerHolder.spacerTitle.setText(this.spacersList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return this.photosList.size();
    }

    public void reload() {
        this.photosRequest.execute();
    }

    // On friends photos list downloaded
    @Override
    public void onResult(int resultCode) {
        this.photosList.clear();
        if (resultCode == Request.RESULT_OK) {
            int lastDay = -1;
            for (PhotoData photoData : this.photosRequest.getPhotosList()) {
                Calendar calendar = this.toCalendar(photoData.getDate());

                int dayOfWeek = calendar.get(DAY_OF_WEEK);
                if (lastDay != dayOfWeek) {
                    lastDay = dayOfWeek;
                    String day = calendar.getDisplayName(Calendar.DAY_OF_WEEK ,Calendar.LONG, Locale.getDefault());
                    if (DateUtils.isToday(photoData.getDate().getTime()))
                        day = "Today";
                    this.photosList.add(null);
                    this.spacersList.put(this.photosList.size()-1, day);

                }
                this.photosList.add(photoData);
            }
        }
        this.photosDownloader.clearImagesFolder(this.photosRequest.getPhotosList());
        this.notifyDataSetChanged();
    }

    public Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    // On photo data downloaded
    @Override
    public void onDownloaded(int photoId) {
        this.notifyItemChanged(this.getItemPosition(photoId));
    }

    private int getItemPosition(int photoId) {
        for (int i = 0; i < this.photosList.size(); i++)
            if (this.photosList.get(i) != null && this.photosList.get(i).getImageId() == photoId)
                return i;
        return 0;
    }
}
