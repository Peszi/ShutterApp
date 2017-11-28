package com.pheasant.shutterapp.shutter.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.api.data.FriendData;
import com.pheasant.shutterapp.shutter.api.listeners.FriendsListListener;
import com.pheasant.shutterapp.shutter.ui.shared.DialogUtil;
import com.pheasant.shutterapp.utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peszi on 2017-05-18.
 */

public class RecipientsDialog implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private Dialog dialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    private RecipientsAdapter recipientsAdapter;

    private RecipientsDialogListener recipientsListener;

    public RecipientsDialog(Context context) {
        this.dialog = DialogUtil.prepare(context, R.layout.layout_dialog_recipients);
        this.dialog.findViewById(R.id.recipients_close_btn).setOnClickListener(this);
        this.dialog.findViewById(R.id.recipients_send_btn).setOnClickListener(this);
        this.swipeRefreshLayout = (SwipeRefreshLayout) this.dialog.findViewById(R.id.recipients_list_refresh);
        this.swipeRefreshLayout.setOnRefreshListener(this);
        this.recipientsAdapter = new RecipientsAdapter(context);
        final ToggleButton checkAllBtn = (ToggleButton) this.dialog.findViewById(R.id.recipients_all_btn);
        this.recipientsAdapter.setCheckAllButton(checkAllBtn);
        final ListView listView = (ListView) this.dialog.findViewById(R.id.recipients_list);
        listView.setAdapter(this.recipientsAdapter);
    }

    public void setRecipientsDialogListener(RecipientsDialogListener recipientsListener) {
        this.recipientsListener = recipientsListener;
    }

    public void showDialog() {
        this.dialog.show();
    }

    public void updateRecipients(ArrayList<FriendData> friendsData) {
        this.swipeRefreshLayout.setRefreshing(false);
        this.recipientsAdapter.updateList(friendsData);
    }

    @Override
    public void onRefresh() {
        if (this.recipientsListener != null)
            this.recipientsListener.onRecipientsRefresh();
    }

    @Override
    public void onClick(View v) {
        final List<Integer> recipients = this.recipientsAdapter.getCheckedIds();
        if (v.getId() == R.id.recipients_send_btn) {
            if (!recipients.isEmpty()) {
                if (this.recipientsListener != null)
                    this.recipientsListener.onRecipientsPicked(recipients);
                this.dialog.hide();
            } else {
                Toast.makeText(this.dialog.getContext(), "You need to check someone first!", Toast.LENGTH_LONG).show();
            }
        } else {
            this.dialog.hide();
        }
    }


    public interface RecipientsDialogListener {
        void onRecipientsPicked(List<Integer> recipients);
        void onRecipientsRefresh();
    }
}
