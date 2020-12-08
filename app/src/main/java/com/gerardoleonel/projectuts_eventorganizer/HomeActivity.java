package com.gerardoleonel.projectuts_eventorganizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivityHomeBinding;
import com.gerardoleonel.projectuts_eventorganizer.fragment.bottommenu.BottomMenuFragment;
import com.gerardoleonel.projectuts_eventorganizer.fragment.home.HomeFragment;
import com.gerardoleonel.projectuts_eventorganizer.pref.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private SharedPref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        loadFragment(new HomeFragment());
        pref = new SharedPref(this);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_menu:
                        showBottomMenu();
                        break;
                }
                return true;
            }
        });

        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String mag = "Successful";
                        if(!task.isSuccessful())
                        {
                            mag = "Failed";
                        }
//                        Toast.makeText(HomeActivity.this, mag, Toast.LENGTH_SHORT).show();
                    }
                });
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String CHANNEL_ID = "Channel 1";
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void loadFragment(Fragment fragment) {
        if(fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment) // menampung container
                    .commit();
        }
    }

    //https://medium.com/@kosta.palash/using-bottomsheetdialogfragment-with-material-design-guideline-f9814c39b9fc
    private void showBottomMenu() {
        IMenuClickHandler menuHandler = new IMenuClickHandler() {
            @Override
            public void onMenuClick(Fragment fragment) {
                loadFragment(fragment);
            }
        };
        BottomMenuFragment fragment = new BottomMenuFragment(menuHandler);
        fragment.show(getSupportFragmentManager(), "Bottom Menu");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.change_theme) {
            pref.switchAppTheme();
            AppCompatDelegate.setDefaultNightMode(pref.getAppTheme());
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            finish();
        }
        return false;
    }
}