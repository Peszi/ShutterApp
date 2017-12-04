package com.pheasant.shutterapp.api.util;

import org.json.JSONObject;

/**
 * Created by Peszi on 2017-12-04.
 */

public interface RequestListener {
    void onSuccess(JSONObject jsonResult);
}
