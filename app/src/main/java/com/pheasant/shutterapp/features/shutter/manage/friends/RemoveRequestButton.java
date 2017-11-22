package com.pheasant.shutterapp.features.shutter.manage.friends;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.friends.RemoveFriendRequest;
import com.pheasant.shutterapp.network.request.util.RequestResultListener;
import com.pheasant.shutterapp.network.request.util.Request;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by Peszi on 2017-06-17.
 */

public class RemoveRequestButton extends android.support.v7.widget.AppCompatImageButton implements View.OnClickListener, DialogInterface.OnClickListener, RequestResultListener {

    private String name;
    private AlertDialog dialog;
    private RemoveFriendRequest removeRequest;
    private ReloadController reloadController;

    public RemoveRequestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnClickListener(this);
    }

    public void setup(ReloadController reloadController, String apiKey, int id, String name) {
        this.reloadController = reloadController;
        this.name = name;
        this.setupDialog();
        this.removeRequest = new RemoveFriendRequest(apiKey);
        this.removeRequest.setOnRequestResultListener(this);
    }

    private void setupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Removing " + this.name);
        builder.setMessage(R.string.friends_remove_dialog_message);
        builder.setPositiveButton(R.string.friends_remove_dialog_positive, this);
        builder.setNegativeButton(R.string.friends_remove_dialog_nagative, this);
        this.dialog = builder.create();
        this.dialog.getWindow().setBackgroundDrawableResource(R.drawable.all_input);
    }

    @Override
    public void onClick(View v) {
       if (this.dialog != null)
           this.dialog.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                this.removeRequest.execute(); break;
            case BUTTON_NEGATIVE:
                this.dialog.dismiss(); break;
        }
    }

    @Override
    public void onResult(int resultCode) {
        switch (resultCode) {
            case Request.RESULT_OK:
                this.reloadController.onReload();
                Toast.makeText(this.getContext().getApplicationContext(), "removing success ", Toast.LENGTH_LONG).show();
                break;
            case Request.RESULT_ERR:
                Toast.makeText(this.getContext().getApplicationContext(), "removing fail ", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
