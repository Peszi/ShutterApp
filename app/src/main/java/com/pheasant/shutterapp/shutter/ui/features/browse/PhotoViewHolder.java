package com.pheasant.shutterapp.shutter.ui.features.browse;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-11-30.
 */

public class PhotoViewHolder extends RecyclerView.ViewHolder {

    public ImageView thumbnail;
    public TextView title;

    public PhotoViewHolder(View view) {
        super(view);
        this.thumbnail = (ImageView) view.findViewById(R.id.card_image);
        this.title = (TextView) view.findViewById(R.id.card_text);
    }
}
