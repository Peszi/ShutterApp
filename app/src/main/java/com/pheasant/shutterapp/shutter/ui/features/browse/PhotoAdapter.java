package com.pheasant.shutterapp.shutter.ui.features.browse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

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

    public void updateList(ArrayList<PhotoData> photosList) {
        this.photosList = photosList;
        this.notifyDataSetChanged();
        // TODO update changes
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
        if (photoData != null) {
            holder.setupData(photoData);
            Log.d("RESPONSE", "ADAPTER POS " + position + " IMAGE ID " + photoData.getImageId());
            this.adapterListener.getPhoto(photoData.getImageId());
        }
    }

    @Override
    public int getItemCount() {
        return this.photosList.size();
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
        void getPhoto(int photoId);
    }
}
