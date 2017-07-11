package com.pheasant.shutterapp.features.shutter.editor.brush;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.editor.editors.DrawingEditor;

/**
 * Created by Peszi on 2017-05-16.
 */

public class SizeController implements View.OnClickListener {

    private final String BRUSH_PREFIX = "Size: ";

    private TextView brushSize;
    private int sizeIndex;

    private OnChangeListener onChangeListener;

    public SizeController(Dialog dialog) {
        this.brushSize = (TextView) dialog.findViewById(R.id.brush_size);
        dialog.findViewById(R.id.brush_size_inc).setOnClickListener(this);
        dialog.findViewById(R.id.brush_size_dec).setOnClickListener(this);
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    public void setSize(int size) {
        for (int i = 0; i < DrawingEditor.BUSH_SIZES.length; i++)
            if (DrawingEditor.BUSH_SIZES[i] == size)
                this.sizeIndex = i;
        this.setupSize();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.brush_size_inc: this.changeUp(); break;
            case R.id.brush_size_dec: this.changeDown(); break;
        }
    }

    private void setupSize() {
        final int brushSize = DrawingEditor.BUSH_SIZES[this.sizeIndex];
        this.brushSize.setText(BRUSH_PREFIX + brushSize);
        if (this.onChangeListener != null)
            this.onChangeListener.onSizePicked(brushSize);
    }

    private void changeUp() {
        this.sizeIndex++;
        if (this.sizeIndex >= DrawingEditor.BUSH_SIZES.length)
            this.sizeIndex = 0;
        this.setupSize();
    }

    private void changeDown() {
        this.sizeIndex--;
        if (this.sizeIndex < 0)
            this.sizeIndex = DrawingEditor.BUSH_SIZES.length - 1;
        this.setupSize();
    }

    public interface OnChangeListener {
        void onSizePicked(int size);
    }
}
