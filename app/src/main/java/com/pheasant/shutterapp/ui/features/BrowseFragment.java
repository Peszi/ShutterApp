package com.pheasant.shutterapp.ui.features;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.api.ShutterDataManager;
import com.pheasant.shutterapp.api.data.PhotoData;
import com.pheasant.shutterapp.presenter.ManagePhotosPresenter;
import com.pheasant.shutterapp.ui.dialog.PhotoPreviewDialog;
import com.pheasant.shutterapp.ui.features.browse.PhotoAdapter;
import com.pheasant.shutterapp.ui.features.browse.PhotoViewHolder;
import com.pheasant.shutterapp.ui.interfaces.BrowsePhotosView;
import com.pheasant.shutterapp.ui.shared.NotifiableFragment;
import com.pheasant.shutterapp.util.Util;

import java.util.ArrayList;

/**
 * Created by Peszi on 2017-04-24.
 */

public class BrowseFragment extends NotifiableFragment implements BrowsePhotosView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private PhotoPreviewDialog previewDialog;
    private RecyclerView photosView;

    private LinearLayoutManager layoutManager;
    private PhotoAdapter photoAdapter;

    private ManagePhotosPresenter photosPresenter;

    public BrowseFragment() {
        this.photosPresenter = new ManagePhotosPresenter();
        this.photosPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_photos_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.previewDialog = new PhotoPreviewDialog(this.getContext());

        this.refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.browse_photos_refresh);
        this.refreshLayout.setOnRefreshListener(this);

        this.layoutManager = new LinearLayoutManager(this.getActivity());

        this.photoAdapter = new PhotoAdapter(this.getContext());
        this.photoAdapter.setAdapterListener(this.photosPresenter);

        this.photosView = (RecyclerView) view.findViewById(R.id.browse_photos);
        this.photosView.setAdapter(this.photoAdapter);
        this.photosView.setLayoutManager(this.layoutManager);
        this.photosView.setHasFixedSize(true);
        this.photosView.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    public void setShutterDataManager(ShutterDataManager shuterDataManager) {
        this.photosPresenter.setShutterDataManager(shuterDataManager);
    }

    @Override
    public void onShow() {
        this.photosPresenter.onPageShowEvent();
    }

    @Override
    public void onRefresh() {
        this.photosPresenter.onRefreshEvent();
    }

    // View Interface

    @Override
    public void listScrollToPosition(int position) {
        this.photosView.smoothScrollToPosition(position);
    }

    @Override
    public void showPhotoDialog(Bitmap photoBitmap) {
        this.previewDialog.showDialog(photoBitmap);
    }

    @Override
    public void updatePhotosList(ArrayList<PhotoData> photosList) {
        this.photoAdapter.updateList(photosList);
    }

    @Override
    public void setRefreshing(boolean show) {
        this.refreshLayout.setRefreshing(show);
    }

    @Override
    public void updateThumbnail(int photoId, Bitmap bitmap) {
        final int adapterPosition = this.photoAdapter.getAdapterPositionByPhotoId(photoId);
        if (adapterPosition >= 0) {
            final PhotoViewHolder photoViewHolder = (PhotoViewHolder) this.photosView.findViewHolderForAdapterPosition(adapterPosition);
            if (photoViewHolder != null)
                photoViewHolder.setupPhoto(bitmap);
        }
    }

}