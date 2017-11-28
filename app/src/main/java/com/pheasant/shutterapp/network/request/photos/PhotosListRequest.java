package com.pheasant.shutterapp.network.request.photos;

import com.pheasant.shutterapp.shutter.api.data.PhotoData;
import com.pheasant.shutterapp.network.request.util.BaseRequest;
import com.pheasant.shutterapp.network.request.util.Request;
import com.pheasant.shutterapp.network.request.util.RequestMethod;

import java.util.List;

/**
 * Created by Peszi on 2017-05-05.
 */

public class PhotosListRequest extends Request {

    private List<PhotoData> imagesList;

    public PhotosListRequest(final String apikey) {
        this.setOutputData(BaseRequest.TYPE_JSON);
        this.setAddress("images");
        this.setAuthorization(apikey);
        this.setMethod(RequestMethod.GET);
    }

    @Override
    protected void onSuccess(Object result) {
//        this.imagesList = new ArrayList<>();
//        try {
//            JSONObject jsonResult = new JSONObject((String) result);
//            if (!jsonResult.getBoolean("error")) {
//                JSONArray imagesList = jsonResult.getJSONArray("images");
//                for (int i = 0; i < imagesList.length(); i++) {
//                    final JSONObject imageObject = (JSONObject) imagesList.get(i);
//                    final PhotoData photoData = new PhotoData(imageObject.getInt("id"), imageObject.getInt("creator_id"), imageObject.getString("created_at"));
//                    this.imagesList.add(photoData);
//                }
//                this.getResultListener().onResult(Request.RESULT_OK);
//            }
//        } catch (JSONException e) {
            this.getResultListener().onResult(Request.RESULT_ERR);
//            e.printStackTrace();
//        }
    }

    public List<PhotoData> getImagesData() {
        return this.imagesList;
    }
}
