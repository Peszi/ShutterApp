package com.pheasant.shutterapp.features.shutter.browse.editor.editors;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.browse.editor.utils.DrawListener;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-05-09.
 */

public class MessageEditor implements TextView.OnEditorActionListener {

    private EditText editText;
    private Bitmap editTextBitmap;
    private int editTextBitmapPosition;
    private boolean editTextMoving;
    private boolean disableEditing;

    private InputMethodManager inputManager;
    private DrawListener drawListener;

    public MessageEditor(Context context, View view, DrawListener drawListener) {
        Util.setupFont(context, view.findViewById(R.id.editor_text_message), Util.FONT_PATH_LIGHT);
        this.inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.drawListener = drawListener;
        this.setupEditText(view);
    }

    private void setupEditText(View view) {
        this.editText = (EditText) view.findViewById(R.id.editor_text_message);
        this.editText.setDrawingCacheEnabled(true);
        this.editText.setOnEditorActionListener(this);
    }

    public void disableEditing(boolean disableEditing) {
        this.disableEditing = disableEditing;
        if (this.disableEditing)
            this.editorHide();
    }

    public void init(int messageStartPosition) {
        this.editText.setText("");
        this.editText.setVisibility(View.GONE);
        this.editTextMoving = false;
        this.editTextBitmapPosition = messageStartPosition;
    }

    public void clear() {
        if (this.editTextBitmap != null) {
            this.editTextBitmap.recycle();
            this.editTextBitmap = null;
        }
    }

    public void onTouch(View view, MotionEvent event) {
        if (!this.disableEditing) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.editTextMoving = this.isOneOfTheBarSidesTouched(view, (int) event.getX(), (int) event.getY());
                if (!this.isEditorVisible() && (this.editTextBitmap == null || this.isBarTouched(event.getX(), event.getY())) && !this.editTextMoving)
                    this.editorShow();
                else if (view != this.editText)
                    this.editorHide();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE && this.editTextMoving) {
                this.moveBar((int) event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                this.editTextMoving = false;
            }
        }
    }

    public void drawMessage(Canvas canvas) {
        if (this.editTextBitmap != null && !this.editTextBitmap.isRecycled())
            canvas.drawBitmap(this.editTextBitmap, 0, this.editTextBitmapPosition - this.editTextBitmap.getHeight() / 2, new Paint());
    }

    private void editorShow() {
        if (this.editTextBitmap != null)
            this.editTextBitmap.recycle();
        if (this.drawListener != null)
            this.drawListener.drawRequest();
        this.editText.setVisibility(View.VISIBLE);
        this.showSoftKeyboard(this.editText);
    }

    private void editorHide() {
        this.editText.setVisibility(View.GONE);
        this.hideSoftKeyboard(this.editText);
        if (this.editText.getText().length() > 0)
            this.editTextBitmap = this.editText.getDrawingCache();
        else if (this.editTextBitmap != null) {
            this.editTextBitmap.recycle();
            this.editTextBitmap = null;
        }
        if (this.drawListener != null)
            this.drawListener.drawRequest();
    }

    private void moveBar(int height) {
        this.editTextBitmapPosition = height;
        if (this.drawListener != null)
            this.drawListener.drawRequest();
    }

    private boolean isOneOfTheBarSidesTouched(View view, int x, int y) {
        final int MOVE_SIZE = view.getWidth()/6;
        return (x < MOVE_SIZE || x > (view.getWidth() - MOVE_SIZE)) && this.isBarTouched(x, y) ? true : false;
    }

    private boolean isBarTouched(float x, float y) {
        if (this.editTextBitmap != null) {
            final Rect barRect = new Rect(0, -this.editTextBitmap.getHeight()/2, this.editTextBitmap.getWidth(), this.editTextBitmap.getHeight()/2);
            barRect.offset(0, this.editTextBitmapPosition);
            return barRect.contains((int) x,(int) y);
        }
        return false;
    }

    private boolean isEditorVisible() {
        return this.editText.getVisibility() == View.VISIBLE ? true : false;
    }

    private void showSoftKeyboard(View view){
        if(view.requestFocus())
            this.inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideSoftKeyboard(View view){
        this.inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            this.editorHide();
            return true;
        }
        return false;
    }

}
