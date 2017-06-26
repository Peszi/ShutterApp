package com.pheasant.shutterapp.features.shutter.browse.editor.editors;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Peszi on 2017-05-10.
 */

public class PhotoEditor {

    private Bitmap photoBitmap;
    private Paint photoPaint;
    private Rect photoSize;

    public PhotoEditor() {
        this.photoPaint = new Paint();
    }

    public void init(Bitmap photo) {
        this.photoBitmap = photo;
        this.photoSize = new Rect(0, 0, photo.getWidth(), photo.getHeight());
    }

    public void drawPhoto(Canvas canvas) {
        if (this.photoBitmap != null)
            canvas.drawBitmap(this.photoBitmap, this.photoSize, new Rect(0, 0, canvas.getWidth(), canvas.getHeight()), this.photoPaint);
    }

    public Bitmap getPhotoBitmap() {
        return this.photoBitmap;
    }
}
