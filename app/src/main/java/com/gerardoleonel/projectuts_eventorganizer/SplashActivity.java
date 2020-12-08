package com.gerardoleonel.projectuts_eventorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivitySplashBinding;
import com.gerardoleonel.projectuts_eventorganizer.pref.SharedPref;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private Handler handler = new Handler();
    private SharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        pref = new SharedPref(this);
        AppCompatDelegate.setDefaultNightMode(pref.getAppTheme());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pref.isLogin()) {
                    startActivity(new Intent(SplashActivity.this, SecondSplashActivity.class));
                }
                else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                finish();
            }
        },1000);
    }

    private void navigate(Class destination) {

    }
}