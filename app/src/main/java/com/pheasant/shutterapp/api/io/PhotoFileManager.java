package com.pheasant.shutterapp.api.io;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.pheasant.shutterapp.api.data.PhotoData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Peszi on 2017-05-06.
 */

public class PhotoFileManager implements Runnable {

    public static final String FILE_EXTENSION = ".png";
    public static final String PHOTO_PREFIX = "photo";
    public static final String THUMBNAIL_PREFIX = "thumbnail";

    private Bitmap photoBitmap;
    private String photoPath;

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
        this.photoBitmap = image;
        this.photoPath = PHOTO_PREFIX + id;
        this.startSavingThread();
    }

    public void storeThumbnail(int id, Bitmap image) {
        this.storePhotoFile(THUMBNAIL_PREFIX + id, image);
    }

    private void startSavingThread() {
        new Thread(this).start();
    }

    private void storePhotoFile(String path, Bitmap image) {
        FileOutputStream outputStream;
        try {
            outputStream = this.context.openFileOutput(path + FILE_EXTENSION, Context.MODE_PRIVATE);
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            image.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap loadPhoto(int id) {
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

    public boolean isPhotoExist(int id) {
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

    public Context getContext() {
        return this.context;
    }

    @Override
    public void run() {
        this.storePhotoFile(this.photoPath, this.photoBitmap);
    }
}
