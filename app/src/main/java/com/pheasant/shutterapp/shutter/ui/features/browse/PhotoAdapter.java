package com.pheasant.shutterapp.shutter.ui.features.browse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoViewHolder> {

    private LayoutInflater layoutInflater;

    public PhotoAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
