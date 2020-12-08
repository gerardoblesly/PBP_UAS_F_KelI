package com.gerardoleonel.projectuts_eventorganizer.events.eventplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivityEventPlaceBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.AppDatabase;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPlace;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventPlaceActivity extends AppCompatActivity {

    private ActivityEventPlaceBinding binding;
    private EventPlaceAdapter adapter;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event_place);
        db = AppDatabase.getInstance(this);
        EventType selectedEventType = (EventType) getIntent().getSerializableExtra("eventType");
        EventPackage selectedEventPackage = (EventPackage) getIntent().getSerializableExtra("eventPackage");
        initAdapter(selectedEventPackage, selectedEventType);
        showData();
    }

    private void initAdapter(EventPackage selectedEventPackage, EventType selectedEventType)
    {
        adapter = new EventPlaceAdapter(this, selectedEventType, selectedEventPackage);
        binding.placeRv.setLayoutManager(new LinearLayoutManager(this));
        binding.placeRv.setAdapter(adapter);
    }

    private void showData()
    {
        List<EventPlace> events = new ArrayList<>();
        events.add(new EventPlace(UUID.randomUUID().toString(),"Italy","https://cdn.cnn.com/cnnnext/dam/assets/170606121206-italy---travel-destination---shutterstock-517522957.jpg","Beautiful Place to Go!"));
        adapter.updateData(events);
    }

}