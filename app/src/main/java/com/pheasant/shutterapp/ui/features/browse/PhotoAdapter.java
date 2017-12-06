package com.pheasant.shutterapp.ui.features.browse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.data.PhotoData;
import com.pheasant.shutterapp.util.Util;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> implements PhotoViewHolder.PhotoViewListener {

    private ArrayList<PhotoData> photosList;

    private PhotoAdapterListener adapterListener;

    private LayoutInflater layoutInflater;

    public PhotoAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.photosList = new ArrayList<>();
    }

    public void setAdapterListener(PhotoAdapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public void updateList(ArrayList<PhotoData> newPhotosList) {
        boolean dataChanged = false;
        if (newPhotosList.size() != this.photosList.size()) // TODO data set compare
            dataChanged = true;
        this.photosList.clear();
        this.photosList.addAll(newPhotosList);
        if (dataChanged)
            this.notifyDataSetChanged();
    }

    public int getAdapterPositionByPhotoId(int photoId) {
        for (int i = 0; i < this.photosList.size(); i++)
            if (this.photosList.get(i).getImageId() == photoId)
                return i;
        return -1;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View baseView = this.layoutInflater.inflate(R.layout.layout_browse_card, parent, false);
        Util.setupFont(parent.getContext(), baseView, Util.FONT_PATH_LIGHT);
        return new PhotoViewHolder(baseView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        final PhotoData photoData = this.photosList.get(position);
//        Log.d("RESPONSE", "[ui] BIND " + position + " PHOTO " + photoData.getImageId());
        if (photoData != null) {
            holder.setListener(this);
            holder.setupData(photoData);
            this.adapterListener.getThumbnail(photoData.getImageId());
        }
    }

    @Override
    public int getItemCount() {
        return this.photosList.size();
    }

    @Override
    public void onStartPreviewEvent(int photoId) {
        this.adapterListener.getPhoto(photoId);
    }

    @Override
    public void onPhotoRemoveEvent(int photoId) {
        this.adapterListener.removePhoto(photoId);
    }

    @Override
    public void onThumbnailReloadEvent(int photoId) {
        this.adapterListener.getThumbnail(photoId);
    }

    @Override
    public void onPhotoIdShowEvent(int photoId) {
        Toast.makeText(this.layoutInflater.getContext(), "PHOTO ID: " + photoId, Toast.LENGTH_LONG).show();
        this.adapterListener.getThumbnail(photoId);
    }

    public ArrayList<Integer> getPhotoIds(int firstCard, int lastCard) {
        ArrayList<Integer> photoIds = new ArrayList<>();
        for (int i = firstCard; i <= lastCard; i++) {
            final PhotoData photoData = this.photosList.get(i);
            if (photoData != null)
                photoIds.add(photoData.getImageId());
        }
        return photoIds;
    }

    public interface PhotoAdapterListener {
        void getThumbnail(int photoId);
        void getPhoto(int photoId);
        void removePhoto(int photoId);
    }
}
