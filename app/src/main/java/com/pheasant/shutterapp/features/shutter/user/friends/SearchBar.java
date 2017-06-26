package com.pheasant.shutterapp.features.shutter.user.friends;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pheasant.shutterapp.R;

/**
 * Created by Peszi on 2017-06-09.
 */

public class SearchBar implements TextWatcher, View.OnClickListener, TextView.OnEditorActionListener {

    private ImageView searchImage;
    private EditText keywordInput;
    private ImageButton clearButton;

    private InputMethodManager inputManager;
    private SearchListener searchListener;

    public SearchBar(Context context, View searchBar) {
        this.inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.searchImage = (ImageView) searchBar.findViewById(R.id.search_icon);
        this.keywordInput = (EditText) searchBar.findViewById(R.id.search_keyword);
        this.keywordInput.addTextChangedListener(this);
        this.keywordInput.setOnEditorActionListener(this);
        this.clearButton = (ImageButton) searchBar.findViewById(R.id.search_clear);
        this.clearButton.setOnClickListener(this);
    }

    public void setSearchListener(SearchListener searchListener) {
        this.searchListener = searchListener;
    }

    public void setIconToFriends() {
        this.searchImage.setImageResource(R.drawable.img_friends);
    }

    public void setIconToSearch() {
        this.searchImage.setImageResource(R.drawable.img_search);
    }

    public void clearKeyword() {
        this.keywordInput.removeTextChangedListener(this);
        this.keywordInput.setText("");
        this.keywordInput.addTextChangedListener(this);
        this.onTextChanged("", 0, 0, 0);
    }

    private void hideKeyboard() {
        this.inputManager.hideSoftInputFromWindow(this.keywordInput.getWindowToken(), 0);
    }

    public String getText() {
        return this.keywordInput.getText().toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (this.searchListener != null) {
            if (s.length() > 0) {
                this.clearButton.setVisibility(View.VISIBLE);
            } else {
                this.clearButton.setVisibility(View.GONE);
                this.hideKeyboard();
            }
            this.searchListener.onType(s.toString());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int action, KeyEvent event) {
        if (action == EditorInfo.IME_ACTION_DONE)
            this.hideKeyboard();
        return false;
    }

    @Override
    public void onClick(View v) {
        this.clearKeyword();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public interface SearchListener {
        void onType(String keyword);
    }
}
