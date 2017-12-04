package com.pheasant.shutterapp.ui.util;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-05-08.
 */

public class Avatar {

    private static int[] AVATAR_IMAGES = new int[] {R.drawable.avatar_m0, R.drawable.avatar_m1, R.drawable.avatar_m2,
                                                   R.drawable.avatar_f0, R.drawable.avatar_f1, R.drawable.avatar_f2, R.drawable.avatar_default};

    public static int getAvatar(int avatarIndex) {
        return AVATAR_IMAGES[avatarIndex];
    }
}
