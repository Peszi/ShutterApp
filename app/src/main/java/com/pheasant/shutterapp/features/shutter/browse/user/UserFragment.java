package com.pheasant.shutterapp.features.shutter.browse.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.ShutterFragment;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-05-18.
 */

public class UserFragment extends ShutterFragment {

    private UserPagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_THIN);
        this.pagerAdapter = new UserPagerAdapter(this.getFragmentManager(), view, this.getArguments());
        return view;
    }

    @Override
    public void onShow() {
        this.pagerAdapter.onShow();
    }

}
