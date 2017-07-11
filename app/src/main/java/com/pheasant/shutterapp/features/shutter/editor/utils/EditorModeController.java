package com.pheasant.shutterapp.features.shutter.editor.utils;

import android.view.View;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shared.views.SlideToggleButton;

/**
 * Created by Peszi on 2017-05-09.
 */

public class EditorModeController implements View.OnClickListener {

    private SlideToggleButton faceButton, drawButton;
    private ChangeModeListener changeModeListener;

    public EditorModeController(View view) {
        this.faceButton = new SlideToggleButton(view, R.id.editor_face_button, R.drawable.editor_face_button, R.drawable.editor_face_back_button, -1);
        this.drawButton = new SlideToggleButton(view, R.id.editor_draw_button, R.drawable.editor_draw_button, R.drawable.editor_draw_back_button, 1);
        this.faceButton.setOnClickListener(this);
        this.drawButton.setOnClickListener(this);
    }

    public void setChangeModeListener(ChangeModeListener changeModeListener) {
        this.changeModeListener = changeModeListener;
    }

    public void initButtons() {
        this.faceButton.init();
        this.drawButton.init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editor_face_button:
                if (this.faceButton.isToggled())
                    this.drawButton.hide();
                else
                    this.drawButton.show();
                break;
            case R.id.editor_draw_button:
                if (this.drawButton.isToggled())
                    this.faceButton.hide();
                else
                    this.faceButton.show();
                break;
        }
        if (this.faceButton.isToggled()) {
            this.changeModeListener.onFaceMode();
        } else if (this.drawButton.isToggled()) {
            this.changeModeListener.onDrawMode();
        } else {
            this.changeModeListener.onNoMode();
        }
    }

    public interface ChangeModeListener {
        void onFaceMode();
        void onDrawMode();
        void onNoMode();
    }
}
