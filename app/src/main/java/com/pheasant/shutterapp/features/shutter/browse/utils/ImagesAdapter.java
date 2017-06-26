package com.pheasant.shutterapp.features.shutter.browse.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.network.request.data.PhotoData;
import com.pheasant.shutterapp.shared.Avatar;
import com.pheasant.shutterapp.utils.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public class ImagesAdapter extends BaseAdapter {

    private List<Thumbnail> thumbnailsList;
    private LayoutInflater mInflater;
    private Context context;

    public ImagesAdapter(Context context) {
        this.thumbnailsList = new ArrayList<>();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void fetchImagesList(List<PhotoData> imagesList) {
//        for (PhotoData photoData : imagesList) {
//            Thumbnail item = new Thumbnail(photoData.getImageId(), photoData.getDate(), photoData.getUserData());
//            if (!this.isContaining(item))
//                thumbnailsList.add(0, item);
//        }
//        this.notifyDataSetChanged();
    }

    public void fetchPhoto(GridView gridView, Bitmap photoData, int photoId) {
        final int itemPosition = this.getIndexByPhotoId(photoId);
        if (itemPosition >= 0) {
            ImagesAdapter.Thumbnail thumbnail = this.getItem(itemPosition);
            thumbnail.bitmap = photoData;
            this.setThumbnailsPhoto(gridView.getChildAt(itemPosition), thumbnail);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = this.prepareThumbnailView(view, parent);
        this.setThumbnailsData(view, this.getItem(position));
        return view;
    }

    private View prepareThumbnailView(View view, ViewGroup parent) {
        if (view == null) {
            view = mInflater.inflate(R.layout.layout_browse_thumbnail, parent, false);
            view.setTag(R.id.friend_photo, view.findViewById(R.id.friend_photo));
            view.setTag(R.id.friend_avatar, view.findViewById(R.id.friend_avatar));
            view.setTag(R.id.friend_name, view.findViewById(R.id.friend_name));
            view.setTag(R.id.friend_photo_expire_time, view.findViewById(R.id.friend_photo_expire_time));
        }
        Util.setupFont(context, view, Util.FONT_PATH_THIN);
        view.findViewById(R.id.friend_bar).setVisibility(View.INVISIBLE);
        return view;
    }

    private void setThumbnailsData(View view, Thumbnail thumbnail) {
        if (thumbnail != null) {
            final ImageView thumbnailAvatar = (ImageView) view.getTag(R.id.friend_avatar);
            final TextView thumbnailUserName = (TextView) view.getTag(R.id.friend_name);
            final TextView thumbnailExpireTime = (TextView) view.getTag(R.id.friend_photo_expire_time);
            thumbnailAvatar.setImageResource(Avatar.getAvatar(thumbnail.avatar));
            thumbnailUserName.setText(thumbnail.name);
            thumbnailExpireTime.setText(thumbnail.getTimeLeft());
            this.setThumbnailsPhoto(view, thumbnail);
        }
    }

    private void setThumbnailsPhoto(View view, Thumbnail thumbnail) {
        if (view != null) {
            final ImageView thumbnailPhoto = (ImageView) view.getTag(R.id.friend_photo);
            ThumbnailAnimation animation = new ThumbnailAnimation(context, view.findViewById(R.id.friend_bar));
            if (thumbnail.bitmap != null) {
                thumbnailPhoto.setImageBitmap(thumbnail.bitmap);
                thumbnailPhoto.startAnimation(animation.getAnimation());
            }
        }
    }

    @Override
    public int getCount() {
        return thumbnailsList.size();
    }

    @Override
    public Thumbnail getItem(int i) {
        return thumbnailsList.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private int getIndexByPhotoId(int photoId) {
        for (int i = 0; i < this.thumbnailsList.size(); i++)
            if (thumbnailsList.get(i).photoId == photoId)
                return i;
        return -1;
    }

    private boolean isContaining(Thumbnail currentItem) {
        for (Thumbnail item : this.thumbnailsList)
            if (item.photoId == currentItem.photoId)
                return true;
        return false;
    }

    public class Thumbnail {

        public final int photoId;
        public Date createdAt;
        public Bitmap bitmap = null;

        public String name;
        public int avatar;

        Thumbnail(int photoId, Date createdAt, UserData userData) {
            this.photoId = photoId;
            this.createdAt = createdAt;
            if (userData != null) {
                this.name = userData.getName();
                this.avatar = userData.getAvatar();
            }
        }

        public String getTimeLeft() {
//            if (this.createdAt != null)
//                return PhotoData.getLiveTime(this.createdAt);
            return "";
        }

        public String getTime() {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
            return simpleDateFormat.format(this.createdAt);
        }
    }
}