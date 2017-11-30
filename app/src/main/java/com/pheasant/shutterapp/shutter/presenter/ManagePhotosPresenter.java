package com.pheasant.shutterapp.shutter.presenter;

import com.pheasant.shutterapp.shutter.api.ShutterDataManager;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsPhotosListListener;
import com.pheasant.shutterapp.shutter.ui.features.BrowseFragment;
import com.pheasant.shutterapp.shutter.ui.interfaces.BrowsePhotosView;
import com.pheasant.shutterapp.shutter.ui.listeners.BrowsePhotosViewListener;

/**
 * Created by Peszi on 2017-11-30.
 */

public class ManagePhotosPresenter implements BrowsePhotosViewListener, FriendsPhotosListListener {

    private ShutterDataManager shutterDataManager;
    private BrowsePhotosView browsePhotosView;

    public void setShutterDataManager(ShutterDataManager shutterDataManager) {
        this.shutterDataManager = shutterDataManager;
        this.shutterDataManager.registerFriendsPhotosListListener(this);
    }

    public void setView(BrowsePhotosView browsePhotosView) {
        this.browsePhotosView = browsePhotosView;
    }

    @Override
    public void onPageShowEvent() {
        this.shutterDataManager.downloadFriendsPhotos();
    }

    @Override
    public void onRefreshEvent() {
        this.shutterDataManager.downloadFriendsPhotos();
    }

    // Photos List Callback

    @Override
    public void onFriendsPhotosListDownloaded(int changesCount) {
        this.browsePhotosView.refreshSetRefreshing(false);
    }
}
