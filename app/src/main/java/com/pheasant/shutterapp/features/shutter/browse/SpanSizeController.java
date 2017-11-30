package com.pheasant.shutterapp.features.shutter.browse;

import android.support.v7.widget.GridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 27.06.2017.
 */

public class SpanSizeController extends GridLayoutManager.SpanSizeLookup {

    @Override
    public int getSpanSize(int position) {
        if (position <= 1) { return 6; }
        return (position < 3 ? 3 : 3);
    }
}
