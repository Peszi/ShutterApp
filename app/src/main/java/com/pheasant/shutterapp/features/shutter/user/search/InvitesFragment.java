package com.pheasant.shutterapp.features.shutter.user.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.features.shutter.ShutterFragment;
import com.pheasant.shutterapp.utils.Util;

/**
 * Created by Peszi on 2017-06-06.
 */

public class InvitesFragment extends ShutterFragment {

    private ListView usersList;
    private InvitesAdapter invitesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_invites_fragment, container, false);
        Util.setupFont(this.getActivity().getApplicationContext(), view, Util.FONT_PATH_LIGHT);

        this.usersList = (ListView) view.findViewById(R.id.invites_list);
        this.invitesAdapter = new InvitesAdapter(this.getContext(), this.getArguments());
        this.usersList.setAdapter(this.invitesAdapter);

        return view;
    }

    @Override
    public void onShow() {
        this.invitesAdapter.download();
    }


}