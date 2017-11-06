package com.pheasant.shutterapp.features.shutter.manage.profile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-06-06.
 */

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        return view;
    }
}
