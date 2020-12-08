package com.gerardoleonel.projectuts_eventorganizer.events.eventplace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardoleonel.projectuts_eventorganizer.ConclusionActivity;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.PackageTypeItemLayoutBinding;
import com.gerardoleonel.projectuts_eventorganizer.databinding.PlaceTypeItemLayoutBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPlace;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;
import com.gerardoleonel.projectuts_eventorganizer.events.eventpackage.EventPackageAdapter;

import java.util.ArrayList;
import java.util.List;

public class EventPlaceAdapter extends RecyclerView.Adapter<EventPlaceAdapter.PlaceViewHolder> {

    private List<EventPlace> events;
    private Context context;
    private EventType selectedType;
    private EventPackage selectedPackage;

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        PlaceTypeItemLayoutBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.place_type_item_layout,parent,false);
        return new PlaceViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position)
    {
        EventPlace eventPlace = events.get(position);
        holder.onBind(eventPlace);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateData(List<EventPlace> events)
    {
        this.events = events;
        notifyDataSetChanged();
    }

    public EventPlaceAdapter(Context context, EventType eventType, EventPackage eventPackage)
    {
        this.context = context;
        this.events = new ArrayList<>();
        this.selectedType = eventType;
        this.selectedPackage = eventPackage;
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder
    {
        private PlaceTypeItemLayoutBinding itemBinding;

        public PlaceViewHolder(PlaceTypeItemLayoutBinding itemBinding)
        {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void onBind(final EventPlace eventPlace)
        {
            itemBinding.setEventPlace(eventPlace);
            itemBinding.placeCv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, ConclusionActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("eventType", selectedType);
                    bundle.putSerializable("eventPackage", selectedPackage);
                    bundle.putSerializable("eventPlace", eventPlace);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
