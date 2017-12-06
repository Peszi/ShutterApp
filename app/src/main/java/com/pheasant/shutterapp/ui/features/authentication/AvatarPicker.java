package com.pheasant.shutterapp.ui.features.authentication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-12-06.
 */

public class AvatarPicker implements DialogInterface.OnClickListener, View.OnClickListener {

    private String[] avatarsList;
    private TypedArray avatarsIcons;

    private AlertDialog avatarDialog;
    private ImageView pickerIcon;
    private TextView pickerTitle;

    private int avatarID = -1;

    public AvatarPicker(Context context, View view) {
        // Arrays
        this.avatarsList = context.getResources().getStringArray(R.array.register_avatar_list);
        this.avatarsIcons = context.getResources().obtainTypedArray(R.array.avatars);
        // Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        this.avatarDialog = builder.setSingleChoiceItems(avatarsList, -1, this).setTitle(R.string.register_picker).create();
        // UI Elements
        this.pickerIcon = (ImageView) view.findViewById(R.id.register_avatar_icon);
        this.pickerTitle = (TextView) view.findViewById(R.id.register_picker_title);
        view.findViewById(R.id.register_avatar).setOnClickListener(this);
        view.findViewById(R.id.register_picker).setOnClickListener(this);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        this.avatarID = which;
        this.pickerIcon.setImageResource(avatarsIcons.getResourceId(which + 1, 0));
        this.pickerTitle.setText(avatarsList[which]);
        dialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        this.avatarDialog.show();
    }

    public int getAvatarID() {
        return this.avatarID;
    }
}
