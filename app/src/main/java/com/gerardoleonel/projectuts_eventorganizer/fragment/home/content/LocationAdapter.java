package com.gerardoleonel.projectuts_eventorganizer.fragment.home.content;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gerardoleonel.projectuts_eventorganizer.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {

    private Context context;
    private List<Location> locations;

    public LocationAdapter(Context context, List<Location> locations) {
        this.context = context;
        this.locations = locations;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName,mDesc;
        ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.name);
            mDesc = itemView.findViewById(R.id.desc);
            mImage = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_list_item,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Location location = locations.get(position);

        holder.mName.setText(location.getName());
        holder.mDesc.setText(location.getDesc());
        holder.mImage.setImageResource(location.getImage());


    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
