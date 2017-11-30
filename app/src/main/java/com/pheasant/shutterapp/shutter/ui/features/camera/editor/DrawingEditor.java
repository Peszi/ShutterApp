package com.pheasant.shutterapp.shutter.ui.features.camera.editor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.features.camera.editor.brush.BrushEditorDialog;
import com.pheasant.shutterapp.shutter.ui.features.camera.editor.utils.DrawListener;
import com.pheasant.shutterapp.shutter.ui.shared.SlideToggleButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-10.
 */

public class DrawingEditor implements View.OnClickListener, BrushEditorDialog.BrushChangeListener {

    public static final int[] BUSH_SIZES = {25, 50, 75, 100};

    private final int DEFAULT_COLOR = Color.WHITE;
    private final int DEFAULT_SIZE = this.BUSH_SIZES[0];

    private List<Drawing> drawingsBuffer;
    private Drawing currentDrawing;
    private Paint drawingPaint;
    private int drawingColor;
    private int drawingSize;

    private boolean enableDrawing;

    private BrushEditorDialog colorsDialog;
    private Context context;
    private View drawingPanel;
    private DrawListener drawListener;

    private float startX, startY;

    // TODO rewrite drawing
    public DrawingEditor(Context context, View view, DrawListener drawListener) {
        this.context = context;
        this.setupButtons(view);
        this.setupDrawing();
        this.drawListener = drawListener;
        this.colorsDialog = new BrushEditorDialog();
    }

    private void setupButtons(View view) {
        this.drawingPanel = view.findViewById(R.id.editor_draw_panel);
        new SlideToggleButton(view, R.id.editor_draw_clear_button, R.drawable.editor_draw_clear, SlideToggleButton.RIGHT).setOnClickListener(this);
        new SlideToggleButton(view, R.id.editor_draw_brush_button, R.drawable.editor_draw_color, SlideToggleButton.RIGHT).setOnClickListener(this);
        new SlideToggleButton(view, R.id.editor_draw_undo_button, R.drawable.editor_draw_undo, SlideToggleButton.RIGHT).setOnClickListener(this);
    }

    private void setupDrawing() {
        this.drawingsBuffer = new ArrayList<>();
        this.drawingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.drawingPaint.setStyle(Paint.Style.STROKE);
        this.drawingPaint.setStrokeJoin(Paint.Join.ROUND);
        this.drawingPaint.setStrokeCap(Paint.Cap.ROUND);
        this.init();
    }

    public void init() {
        this.drawingsBuffer.clear();
        this.drawingColor = this.DEFAULT_COLOR;
        this.drawingSize = this.DEFAULT_SIZE;
    }

    public void startDrawing(boolean enableDrawing) {
        this.enableDrawing = enableDrawing;
        if (this.enableDrawing) {
            this.drawingPanel.setVisibility(View.VISIBLE); // updateList buttons
        } else {
            this.drawingPanel.setVisibility(View.GONE);
        }
    }

    private void cleanDrawing() {
        this.drawingsBuffer.clear();
        this.drawListener.invalidateSurface();
    }

    private void customizeBrush() {
        this.colorsDialog.showDialog(this.context, this.drawingColor, this.drawingSize, this);
    }

    private void undoDrawing() {
        if (this.drawingsBuffer.size() > 0)
            this.drawingsBuffer.remove(this.drawingsBuffer.size()-1);
        this.drawListener.invalidateSurface();
    }

    public void onTouch(View v, MotionEvent event) {
        if (this.enableDrawing) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.startX = event.getX();
                this.startY = event.getY();
                this.currentDrawing = new Drawing(new Path(), this.drawingColor, this.drawingSize);
                this.currentDrawing.getPath().moveTo(event.getX(), event.getY());
                this.drawingsBuffer.add(this.currentDrawing);
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float dx = Math.abs(event.getX() - this.startX);
                float dy = Math.abs(event.getY() - this.startY);
                if (dx > 3 || dy > 3) {
                    float cX = (event.getX() + this.startX) / 2;
                    float cY = (event.getY() + this.startY) / 2;
                    this.currentDrawing.getPath().quadTo(this.startX, this.startY, cX, cY);
                    this.drawListener.invalidateSurface();
                    this.startX = event.getX();
                    this.startY = event.getY();
                }
            }
        }
    }

    public void drawDrawings(Canvas canvas) {
        for (Drawing drawing : this.drawingsBuffer) {
            this.drawingPaint.setColor(drawing.getBrushColor());
            this.drawingPaint.setStrokeWidth(drawing.getBrushSize());
            canvas.drawPath(drawing.getPath(), this.drawingPaint);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editor_draw_clear_button: this.cleanDrawing(); break;
            case R.id.editor_draw_brush_button: this.customizeBrush(); break;
            case R.id.editor_draw_undo_button: this.undoDrawing(); break;
        }
    }

    @Override
    public void onColorChanged(int color) {
        this.drawingColor = color;
    }

    @Override
    public void onSizeChanged(int size) {
        this.drawingSize = size;
    }

    private class Drawing {

        private Path drawingPath;
        private int brushColor;
        private int brushSize;

        public Drawing(Path drawingPath, int brushColor, int brushSize) {
            this.drawingPath = drawingPath;
            this.brushColor = brushColor;
            this.brushSize = brushSize;
        }

        private Path getPath() {
            return this.drawingPath;
        }

        private int getBrushColor() {
            return this.brushColor;
        }

        private int getBrushSize() {
            return this.brushSize;
        }
    }
}
