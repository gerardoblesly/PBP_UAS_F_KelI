package com.gerardoleonel.projectuts_eventorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivitySecondSplashBinding;
import com.gerardoleonel.projectuts_eventorganizer.pref.SharedPref;

public class SecondSplashActivity extends AppCompatActivity {

    private ActivitySecondSplashBinding binding;
    private Handler handler = new Handler();
    private SharedPref pref;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_second_splash);
        textView = findViewById(R.id.textSplashWelcome);
        pref = new SharedPref(this);
        textView.setText(pref.getSessionUsername());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SecondSplashActivity.this, HomeActivity.class));
                finish();
            }
        },3000);
    }
}