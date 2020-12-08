package com.gerardoleonel.projectuts_eventorganizer.events.eventtype;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.gerardoleonel.projectuts_eventorganizer.HomeActivity;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivityPickEventBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.AppDatabase;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventTypeActivity extends AppCompatActivity {

    private ActivityPickEventBinding binding;
    private AppDatabase db;
    private EventTypeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pick_event);
        db = AppDatabase.getInstance(this);
        initAdapter();
        showData();

        binding.btnBackAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EventTypeActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void initAdapter() {
        adapter = new EventTypeAdapter(this, new ArrayList());
        binding.rvEvents.setLayoutManager(new GridLayoutManager(this, 2));
        binding.rvEvents.setAdapter(adapter);
    }

    private void showData() {

        List<EventType> events = new ArrayList<>();
        events.add(new EventType(UUID.randomUUID().toString(), "https://www.nymetroparents.com/columnpic2/stress-free-birthdays.jpg", "Birthday"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://lh3.googleusercontent.com/proxy/1_CZE9K1bGyHbodMausV3nhEB6IFjxLCakMM1CGLs7LwthogqnfUc5AZ16YPEIRdvfZsA5sJIHyEx4dMoqigVqaMSmGnzbnhGc8069J0Qr6NahSbTsRTQZifNM4o99bAz6OAHxpQaKE", "Pre-Wedding"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://familybuildersok.org/wp-content/uploads/2019/02/happy-family.jpg", "Family"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://i.insider.com/5d24ab4d21a86114000e6df5?width=1100&format=jpeg&auto=webp", "Graduation"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://www.weddingforward.com/wp-content/uploads/2017/05/wedding-proposal-ideas-man-propose-a-woman-at-the-beach-featured.jpg", "Proposal"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://i.insider.com/5e2762f7ab49fd1ed25a9dc4?width=1100&format=jpeg&auto=webp", "Wedding"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://i0.wp.com/thismamaloveslife.com/wp-content/uploads/2017/11/CandiceMarkMaternity-54-1.jpg?fit=2048%2C1365&ssl=1", "Maternity"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://4.bp.blogspot.com/-YE7mbxambnE/WgfKpEaD8JI/AAAAAAAAAk8/lHqM8-cMEYgbzrCYe7s-fMbUq-NmcJVHgCLcBGAs/s1600/bayi-tersenyum-digitografi.jpg", "Newborn"));
        events.add(new EventType(UUID.randomUUID().toString(), "https://www.eccireland.ie/wp-content/uploads/2013/06/HOlidays.jpg", "Holiday"));

        adapter.updateData(events);
    }
}