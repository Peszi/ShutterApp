package com.pheasant.shutterapp.features.shutter;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.pheasant.shutterapp.R;
import com.pheasant.shutterapp.network.download.user.UserData;
import com.pheasant.shutterapp.utils.IntentKey;

@SuppressWarnings("deprecation")
public class ShutterActivity extends AppCompatActivity {

    private ShutterPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_shutter);
        this.pagerAdapter = new ShutterPagerAdapter(this.getSupportFragmentManager(), this.getWindow().getDecorView(), this.getUserData());
    }

    @Override
    public void onBackPressed() {
        this.pagerAdapter.onBack();
    }

    private Bundle getUserData() {
        Bundle bundle = new Bundle();
        bundle.putString(IntentKey.USER_API_KEY, ((UserData) this.getIntent().getSerializableExtra(IntentKey.USER_DATA)).getApiKey());
        return bundle;
    }
}