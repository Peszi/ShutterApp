package com.pheasant.shutterapp.shutter.ui.shared;

import android.support.v4.app.Fragment;

/**
 * Created by Peszi on 2017-11-06.
 */

public abstract class NotifiableFragment extends Fragment {
    public abstract void onShow();
    public boolean onBack() { return true; }
}
