package com.pheasant.shutterapp.network.download;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pheasant.shutterapp.shutter.api.data.PhotoData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Peszi on 2017-05-06.
 */

public class PhotoFileManager {

    public static final String FILE_EXTENSION = ".png";
    public static final String PHOTO_PREFIX = "photo";
    public static final String THUMBNAIL_PREFIX = "thumbnail";

    private Context context;

    public PhotoFileManager(Context context) {
        this.context = context;
    }

    public void showAllPhotos() {
        for (String file : this.context.fileList())
            if (file.endsWith(FILE_EXTENSION))
                Log.d("RESPONSE", "FILE PHOTO " + file);
    }

    public void storePhoto(int id, Bitmap image) {
        this.storePhotoFile(PHOTO_PREFIX + id, image);
    }

    public void storeThumbnail(int id, Bitmap image) {
        this.storePhotoFile(THUMBNAIL_PREFIX + id, image);
    }

    private void storePhotoFile(String path, Bitmap image) {
        FileOutputStream outputStream;
        try {
            outputStream = this.context.openFileOutput(path + FILE_EXTENSION, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Bitmap loadPhoto(int id) {
        return this.loadPhotoFile(PHOTO_PREFIX + id);
    }

    protected Bitmap loadThumbnail(int id) {
        return this.loadPhotoFile(THUMBNAIL_PREFIX + id);
    }

    private Bitmap loadPhotoFile(String path) {
        Bitmap bitmap = null;
        FileInputStream inputStream;
        try {
            inputStream = this.context.openFileInput(path + FILE_EXTENSION);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private void deletePhotoFiles(int id) {
        this.deletePhoto(id);
        this.deleteThumbnail(id);
    }

    private void deletePhoto(int id) {
        if (this.isPhotoExist(id))
            this.deleteFile(PHOTO_PREFIX + id + FILE_EXTENSION);
    }

    private void deleteThumbnail(int id) {
        if (this.isThumbnailExist(id))
            this.deleteFile(THUMBNAIL_PREFIX + id + FILE_EXTENSION);
    }

    private void deleteFile(String path) {
        if (this.isFileExist(path))
            this.context.deleteFile(path);
    }

    public void clearAllPhotos() {
        for (String file : this.context.fileList())
            if (file.endsWith(FILE_EXTENSION))
                this.deleteFile(file);
    }

    protected void cleanUpPhotos(List<PhotoData> photoDataList) {
        for (String file : this.context.fileList())
            if (file.endsWith(FILE_EXTENSION)) {
                file = file.replace(FILE_EXTENSION, "");
                if (file.contains(PHOTO_PREFIX)) {
                    file = file.replace(PHOTO_PREFIX, "");
                    final int id = Integer.valueOf(file);
                    boolean toRemove = true;
                    for (PhotoData photoData : photoDataList)
                        if (photoData.getImageId() == id)
                            toRemove = false;
                    if (toRemove)
                        this.deletePhotoFiles(id);
                }
            }
    }

    protected boolean isPhotoExist(int id) {
        return this.isFileExist(PHOTO_PREFIX + id + FILE_EXTENSION);
    }

    protected boolean isThumbnailExist(int id) {
        return this.isFileExist(THUMBNAIL_PREFIX + id + FILE_EXTENSION);
    }

    private boolean isFileExist(String path) {
        for (String file : this.context.fileList())
            if (file.equalsIgnoreCase(path))
                return true;
        return false;
    }

    @SuppressLint("NewApi")
    public Bitmap prepareThumbnail(Bitmap photo) {
        Bitmap inputBitmap = Bitmap.createScaledBitmap(photo, photo.getWidth()/2, photo.getHeight()/2, false); photo.recycle();
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap, 0, inputBitmap.getHeight()/2 - inputBitmap.getWidth()/2, inputBitmap.getWidth(), inputBitmap.getWidth()); inputBitmap.recycle();
        return outputBitmap;
//        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

//        RenderScript rs = RenderScript.create(this.context);
//        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//        int radius = inputBitmap.getWidth()/27;
//        radius = Math.min(25, radius);
//        script.setRadius(radius);
//        for (int i = 0; i < 3; i++) {
//            final Allocation input = Allocation.createFromBitmap(rs, outputBitmap); //use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
//            final Allocation output = Allocation.createTyped(rs, input.getType());
//            script.setInput(input);
//            script.forEach(output);
//            output.copyTo(outputBitmap);
//        }
//        inputBitmap.recycle();
//        photo.recycle();
//        return outputBitmap;
    }

    public static Bitmap loadPhotoFile(int photoId, Context context) {
        Bitmap bitmap = null;
        FileInputStream inputStream;
        try {
            inputStream = context.openFileInput(PHOTO_PREFIX + photoId + FILE_EXTENSION);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public Context getContext() {
        return this.context;
    }
}
