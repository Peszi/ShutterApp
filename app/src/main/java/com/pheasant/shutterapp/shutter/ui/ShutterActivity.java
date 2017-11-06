package com.pheasant.shutterapp.shutter.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.shutter.ui.util.BundlePacker;

public class ShutterActivity extends AppCompatActivity implements ShutterInterface {

    private ShutterAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_shutter);
        this.pagerAdapter = new ShutterAdapter(this.getSupportFragmentManager(), this.getWindow().getDecorView(), BundlePacker.prepareBundle(this.getIntent()), this);
    }

    @Override
    public void onBackPressed() {
        this.pagerAdapter.onBack();
    }

    @Override
    public void logoutUser() {
        this.finish();
    }
}