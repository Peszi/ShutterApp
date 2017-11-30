package com.pheasant.shutterapp.shutter.ui.features;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.browse.utils.PhotosAdapter;
import com.pheasant.shutterapp.shutter.api.ShutterDataManager;
import com.pheasant.shutterapp.shutter.presenter.ManagePhotosPresenter;
import com.pheasant.shutterapp.shutter.ui.features.browse.PhotoAdapter;
import com.pheasant.shutterapp.shutter.ui.interfaces.BrowsePhotosView;
import com.pheasant.shutterapp.shutter.ui.shared.NotifiableFragment;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-04-24.
 */

public class BrowseFragment extends NotifiableFragment implements BrowsePhotosView, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView photosView;

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

//        this.layoutManager = new GridLayoutManager(this.getContext(), 6, LinearLayoutManager.VERTICAL, false);
//        this.photosView = (RecyclerView) view.findViewById(R.id.browse_photos);
//        this.photosView.setHasFixedSize(false);
//        this.photosView.setLayoutManager(this.layoutManager);
//
//        this.photosAdapter = new PhotoAdapter(this.getContext(), this.getArguments());
//        this.layoutManager.setSpanSizeLookup(this.photosAdapter.getSpanSizeLookup());
//        this.photosView.setAdapter(this.photosAdapter);
//        this.photosView.setItemAnimator(new DefaultItemAnimator());
//        this.photosView.setOnTouchListener(this);

        this.refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.browse_photos_refresh);
        this.refreshLayout.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());

        this.photoAdapter = new PhotoAdapter(this.getContext());

        this.photosView = (RecyclerView) view.findViewById(R.id.browse_photos);
        this.photosView.setHasFixedSize(true);
        this.photosView.setLayoutManager(layoutManager);
        this.photosView.setItemAnimator(new DefaultItemAnimator());
        this.photosView.setAdapter(this.photoAdapter);

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
    public void refreshSetRefreshing(boolean show) {
        this.refreshLayout.setRefreshing(show);
    }
}