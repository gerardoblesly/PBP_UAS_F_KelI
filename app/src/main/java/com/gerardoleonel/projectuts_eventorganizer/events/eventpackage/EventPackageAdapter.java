package com.gerardoleonel.projectuts_eventorganizer.events.eventpackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.databinding.PackageTypeItemLayoutBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventPackage;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;
import com.gerardoleonel.projectuts_eventorganizer.events.eventplace.EventPlaceActivity;

import java.util.ArrayList;
import java.util.List;


public class EventPackageAdapter extends RecyclerView.Adapter<EventPackageAdapter.EventViewHolder> {

    private List<EventPackage> events;
    private Context context;
    private EventType selectedEventType;

    public EventPackageAdapter(Context context, EventType eventType) {
        this.context = context;
        this.events = new ArrayList<>();
        this.selectedEventType = eventType;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PackageTypeItemLayoutBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.package_type_item_layout, parent, false);
        return new EventViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventPackage eventPackage = events.get(position);
        holder.onBind(eventPackage);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateData(List<EventPackage> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder
    {
        private PackageTypeItemLayoutBinding itemBinding;

        public EventViewHolder(PackageTypeItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void onBind(final EventPackage eventPackage) {
            itemBinding.setEventPackage(eventPackage);
            itemBinding.packageCv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventPlaceActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("eventType", selectedEventType);
                    bundle.putSerializable("eventPackage", eventPackage);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}



