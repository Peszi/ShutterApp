package com.pheasant.shutterapp.features.shutter.browse.editor.brush;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-05-11.
 */

public class BrushEditorDialog implements ColorController.OnChangeListener, SizeController.OnChangeListener {

    private Dialog dialog;
    private BrushChangeListener brushChangeListener;

    public void showDialog(Context context, int initColor, int initSize, BrushChangeListener brushChangeListener) {
        this.brushChangeListener = brushChangeListener;
        this.setupDialog(context);
        this.setupSurface(initColor);
        this.setupSizeController(initSize);
    }

    private void setupDialog(Context context) {
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        this.dialog.setCancelable(true);
        this.dialog.setContentView(R.layout.layout_dialog_colors);
        this.dialog.show();
        Util.setupFont(context, this.dialog.getWindow().getDecorView(), Util.FONT_PATH_LIGHT);
    }

    private void setupSurface(int initColor) {
        ColorController colorController = ((ColorController) this.dialog.findViewById(R.id.colors_picker));
        colorController.setOnColorPickedListener(this);
        colorController.setColor(initColor);
    }

    private void setupSizeController(int initSize) {
        SizeController sizeController = new SizeController(this.dialog);
        sizeController.setOnChangeListener(this);
        sizeController.setSize(initSize);
    }

    @Override
    public void onColorPicked(int color) {
        this.brushChangeListener.onColorChanged(color);
    }

    @Override
    public void onSizePicked(int size) {
        this.brushChangeListener.onSizeChanged(size);
    }

    @Override
    public void onPickingClose() {
        this.dialog.hide();
    }

    public interface BrushChangeListener {
        void onColorChanged(int color);
        void onSizeChanged(int color);
    }
}
