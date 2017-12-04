package com.pheasant.shutterapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.util.BundlePacker;

public class ShutterActivity extends AppCompatActivity implements ShutterInterface {

    private ShutterAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_shutter);
        final Bundle bundle = BundlePacker.prepareApiKeyBundle(this.getIntent());
        this.pagerAdapter = new ShutterAdapter(this.getSupportFragmentManager(), this.getApplicationContext(), this.getWindow().getDecorView(), bundle, this);
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