package com.pheasant.shutterapp.features.shutter.camera.recipients;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.request.data.UserData;
import com.pheasant.shutterapp.utils.Util;

import java.util.List;

/**
 * Created by Peszi on 2017-05-18.
 */

public class RecipientsDialog implements View.OnClickListener {

    private Dialog dialog;
    private RecipientsAdapter recipientsAdapter;

    private ActionListener actionListener;

    public RecipientsDialog(Context context) {
        this.dialog = new Dialog(context);
        this.dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.layout_dialog_recipients);
        this.dialog.setCancelable(true);
        this.dialog.findViewById(R.id.recipients_close_btn).setOnClickListener(this);
        this.dialog.findViewById(R.id.recipients_send_btn).setOnClickListener(this);
        this.recipientsAdapter = new RecipientsAdapter(context, (ListView) this.dialog.findViewById(R.id.recipients_list), (ToggleButton) this.dialog.findViewById(R.id.recipients_all_btn));
        Util.setupFont(context, this.dialog.getWindow().getDecorView(), Util.FONT_PATH_LIGHT);
        Util.setupFont(context, this.dialog.findViewById(R.id.recipients_title), Util.FONT_PATH_THIN);
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void showDialog(List<UserData> friendsData) {
        this.recipientsAdapter.prepare(friendsData);
        this.dialog.show();
    }

    @Override
    public void onClick(View v) {
        List<Integer> recipients = this.recipientsAdapter.getCheckedIds();
        if (v.getId() == R.id.recipients_send_btn) {
            if (!recipients.isEmpty()) {
                if (this.actionListener != null)
                    this.actionListener.onAccept(recipients);
                this.dialog.hide();
            } else {
                Toast.makeText(this.dialog.getContext(), "You need to check someone first!", Toast.LENGTH_LONG).show();
            }
        } else {
            this.dialog.hide();
        }
    }


    public interface ActionListener {
        void onAccept(List<Integer> recipients);
    }
}
