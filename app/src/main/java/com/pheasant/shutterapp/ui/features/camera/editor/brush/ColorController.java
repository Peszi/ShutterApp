package com.pheasant.shutterapp.ui.features.camera.editor.brush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Peszi on 2017-05-11.
 */

public class ColorController extends SurfaceView implements View.OnTouchListener {

    private final int PALETTE_LENGTH = 12;
    private final int PALETTE_COLOR_CUTOFF = 2;

    private final float OUTER_BACKGROUND_SIZE = 0.95f;
    private final float INNER_BACKGROUND_SIZE = 0.45f;

    private final float PALETTE_SIZE = 0.85f;
    private final float COLOR_SIZE = 0.30f;

    private final float BLACK_COLOR_OFFSET = 0.25f;

    private int colorAngle;
    private int[] colorPalette;
    private Paint colorPaint;
    private int pickedColor;

    private int canvasRadius;

    private OnChangeListener onChangeListener;

    public ColorController(Context context, AttributeSet set) {
        super(context, set);
        this.setOnTouchListener(this);
        this.setupPalette();
    }

    private void setupPalette() {
        this.colorAngle = 360 / this.PALETTE_LENGTH;
        this.colorPaint = new Paint();
        this.colorPaint.setAntiAlias(true);
        this.fillPalette();
        this.invalidate();
    }

    private void fillPalette() {
        this.colorPalette = new int[this.PALETTE_LENGTH];
        for (int i = 0; i < this.colorPalette.length; i++)
            this.colorPalette[i] = ColorUtils.HSLToColor(new float[] {i * this.colorAngle, 0.85f, 0.5f});
    }

    public void setColor(int pickedColor) {
        this.pickedColor = pickedColor;
    }

    public void setOnColorPickedListener(OnChangeListener onChangeListener) {
        this.onChangeListener = onChangeListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final float x = (event.getX() - v.getWidth()/2) / this.canvasRadius;
        final float y = (event.getY() - v.getHeight()/2) / this.canvasRadius;
        float distance = (float) (Math.sqrt(x * x + y * y));
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (distance > 1.0f)
                this.onChangeListener.onPickingClose();
        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                distance -= this.BLACK_COLOR_OFFSET;
                distance = Math.max(0.0f, distance);
                distance = Math.min(1.0f, distance);
                final float angle = this.getAngleFromXY(x, y) - this.colorAngle / 2;
                this.pickedColor = ColorUtils.HSLToColor(new float[]{angle, 1.0f, distance});
                this.onChangeListener.onColorPicked(this.pickedColor);
                this.invalidate();
            }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(canvas.getWidth()/2, canvas.getHeight()/2);
        this.canvasRadius = canvas.getWidth()/2;

        // outer background and colors palette
        canvas.drawOval(this.getCircleSize(this.OUTER_BACKGROUND_SIZE), this.getPaint(Color.WHITE));
        final RectF shape = this.getCircleSize(this.PALETTE_SIZE);
        for (int i = 0; i < this.colorPalette.length; i++)
            canvas.drawArc(shape, i * this.colorAngle + this.PALETTE_COLOR_CUTOFF, this.colorAngle - this.PALETTE_COLOR_CUTOFF, true, this.getPaint(this.colorPalette[i]));

        // inner background and picked color
        canvas.drawOval(this.getCircleSize(this.INNER_BACKGROUND_SIZE), this.getPaint(Color.WHITE));
        canvas.drawOval(this.getCircleSize(this.COLOR_SIZE), this.getPaint(this.pickedColor));
    }

    private RectF getCircleSize(float canvasAmount) {
        final float size = (int) (this.canvasRadius * 2 * canvasAmount);
        return new RectF(-size / 2, -size / 2, size / 2, size / 2);
    }

    private Paint getPaint(int color) {
        this.colorPaint.setColor(color);
        return this.colorPaint;
    }

    private float getAngleFromXY(float x, float y) {
        float angle = (float) Math.toDegrees(Math.atan2(y, x));
        if (angle < 0)
            angle = 360 + angle;
        return angle;
    }

    public interface OnChangeListener {
        void onColorPicked(int color);
        void onPickingClose();
    }
}