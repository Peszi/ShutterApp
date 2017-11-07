package com.pheasant.shutterapp.shutter.ui.features;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.manage.ManageAdapter;
import com.pheasant.shutterapp.shutter.ui.util.NotifiableFragment;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-05-18.
 */

public class ManageFragment extends NotifiableFragment {

    private ManageAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_user_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_THIN);
        this.pagerAdapter = new ManageAdapter(this.getFragmentManager(), view, this.getArguments());
        return view;
    }

    @Override
    public void onShow() {
        this.pagerAdapter.onShow();
    }
}
