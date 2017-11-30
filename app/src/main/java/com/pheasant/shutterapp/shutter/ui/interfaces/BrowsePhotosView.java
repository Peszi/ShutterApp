package com.pheasant.shutterapp.shutter.ui.interfaces;

/**
 * Created by Peszi on 2017-11-30.
 */

public interface BrowsePhotosView {
    void listScrollToPosition(int position);
    void updatePhotosList(ArrayList<PhotoData> photosList);
    void refreshSetRefreshing(boolean show);
}
