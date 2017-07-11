package com.pheasant.shutterapp.features.shutter.browse.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.browse.ImageLoader;
import com.pheasant.shutterapp.features.shutter.browse.OnItemListener;
import com.pheasant.shutterapp.features.shutter.browse.SpanSizeController;
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

public class PhotosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnRequestResultListener, FriendsPhotosDownloader.OnDownloadListener, OnItemListener {

    private FriendsPhotosRequest photosRequest;
    private FriendsPhotosDownloader photosDownloader;

    private LayoutInflater layoutInflater;
    private Context context;
    private String apiKey;

    private List<PhotoData> photosList = new ArrayList<>();
    private Map<Integer, String> spacersList = new HashMap<>();
    private SpanSizeController sizeController;


    public PhotosAdapter(Context context, Bundle bundle) {
        this.context = context;
        this.apiKey = bundle.getString(IntentKey.USER_API_KEY);
        this.layoutInflater = LayoutInflater.from(context);
        this.photosRequest = new FriendsPhotosRequest(this.apiKey);
        this.photosRequest.setOnRequestResultListener(this);
        this.photosDownloader = new FriendsPhotosDownloader(context, this.apiKey);
        this.photosDownloader.setOnDownloadListener(this);
        this.sizeController = new SpanSizeController();
    }

    @Override
    public void onClick(int imageId) {
        Toast.makeText(this.context.getApplicationContext(), "Image id:" + imageId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLongClick(int imageId) {
        Toast.makeText(this.context.getApplicationContext(), "force reloading...", Toast.LENGTH_LONG).show();
        this.photosDownloader.forceGetImage(imageId);
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
                viewHolder = new PhotoViewHolder(baseView, this);
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
                photoHolder.setupData(photoData, this.photosDownloader);
                break;
            default:
                SpacerViewHolder spacerHolder = (SpacerViewHolder) holder;
//                spacerHolder.spacerTitle.setText(this.spacersList.get(position));
                spacerHolder.spacerTitle.setText("Today");
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
            this.sizeController.getPhotosDays().clear();
//            this.photosList.add(null);
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
                    this.sizeController.getPhotosDays().add(this.photosList.size()-1);

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

    public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
        return this.sizeController;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener, ImageLoader.OnLoaderListener {

        private int imageId;
        private OnItemListener onItemListener;

        public SquareImageView photoData;
        public TextView photoCreator;
        public TextView photoTime;

        public PhotoViewHolder(View view, OnItemListener onItemListener) {
            super(view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
            this.onItemListener = onItemListener;
            this.photoData = (SquareImageView) view.findViewById(R.id.photo_image);
            this.photoCreator = (TextView) view.findViewById(R.id.photo_creator);
            this.photoTime = (TextView) view.findViewById(R.id.photo_time);
        }

        public void setupData(PhotoData photoData, FriendsPhotosDownloader friendsPhotosDownloader) {
            this.imageId = photoData.getImageId();

//            if (friendsPhotosDownloader.canGetImage(this.imageId)) {
//                this.photoData.setImageBitmap(friendsPhotosDownloader.getPhotoThumbnail(this.imageId));

//                ThumbnailAnimation animation = new ThumbnailAnimation(this.context, photoHolder.itemView);
//                this.photoData.startAnimation(animation.getAnimation());
//            } else {
//
//            }
            this.photoData.setImageResource(R.drawable.image_exist);
            this.photoCreator.setText(photoData.getCreatorName());
            this.photoTime.setText(TimeStamp.getLiveTime(photoData.getDate()));
            ImageLoader imageLoader = new ImageLoader(friendsPhotosDownloader, this.imageId);
            imageLoader.setOnLoaderListener(this);
            imageLoader.execute();
        }

        @Override
        public void onClick(View view) {
            this.onItemListener.onClick(this.imageId);
        }

        @Override
        public boolean onLongClick(View view) {
            this.onItemListener.onLongClick(this.imageId);
            return false;
        }

        @Override
        public void onLoad(Bitmap bitmap, int imageId) {
            if (this.imageId == imageId)
                this.photoData.setImageBitmap(bitmap);
        }

        @Override
        public void onErr(int imageId) { // TODO on image loading err
            if (this.imageId == imageId)
                this.photoData.setImageResource(R.drawable.image_exist);
        }
    }

    public static class SpacerViewHolder extends RecyclerView.ViewHolder {

        public TextView spacerTitle;

        public SpacerViewHolder(View view) {
            super(view);
            this.spacerTitle = (TextView) view.findViewById(R.id.spacer_title);
        }
    }

}
