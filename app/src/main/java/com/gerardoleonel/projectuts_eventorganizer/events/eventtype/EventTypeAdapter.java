package com.gerardoleonel.projectuts_eventorganizer.events.eventtype;

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
import com.gerardoleonel.projectuts_eventorganizer.databinding.EventTypeItemLayoutBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.EventType;
import com.gerardoleonel.projectuts_eventorganizer.events.eventpackage.EventPackageActivity;

import java.util.List;

public class EventTypeAdapter extends RecyclerView.Adapter<EventTypeAdapter.EventTypeViewHolder> {

    private List<EventType> events;
    private Context context;

    public EventTypeAdapter(Context context, List<EventType> events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EventTypeItemLayoutBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.event_type_item_layout, parent, false);
        return new EventTypeViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventTypeViewHolder holder, int position) {
        EventType eventType = events.get(position);
        holder.onBind(eventType);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void updateData(List<EventType> events) {
        this.events = events;
        notifyDataSetChanged();
    }

    class EventTypeViewHolder extends RecyclerView.ViewHolder {

        private EventTypeItemLayoutBinding itemBinding;

        public EventTypeViewHolder(EventTypeItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void onBind(final EventType eventType) {
            itemBinding.setEventType(eventType);
            itemBinding.cvEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, EventPackageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("eventType", eventType);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            Glide.with(context)
                    .load(eventType.getEventPhoto())
                    .into(itemBinding.eventPhoto);
        }
    }
}