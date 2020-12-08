package com.gerardoleonel.projectuts_eventorganizer.events.eventpackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivityEventPackageBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.AppDatabase;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventPackageActivity extends AppCompatActivity {

    private ActivityEventPackageBinding binding;
    private EventPackageAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_package);
        db = AppDatabase.getInstance(this);
        EventType selectedEventType = (EventType) getIntent().getSerializableExtra("eventType");
        initAdapter(selectedEventType);
        showData();
    }

    private void initAdapter(EventType selectedEventType) {
        adapter = new EventPackageAdapter(this, selectedEventType);
        binding.packageRv.setLayoutManager(new LinearLayoutManager(this));
        binding.packageRv.setAdapter(adapter);
    }

    private void showData() {
        List<EventPackage> events = new ArrayList<>();
        events.add(new EventPackage(UUID.randomUUID().toString(), "Regular", "Rp. 10.000.000", "Up to 20 edited photos"));
        events.add(new EventPackage(UUID.randomUUID().toString(), "Medium", "Rp. 25.000.000", "Up to 50 edited photos"));
        events.add(new EventPackage(UUID.randomUUID().toString(), "Flawless", "Rp. 50.000.000", "Up to 100 edited photos"));
        adapter.updateData(events);
    }
}