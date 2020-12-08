package com.gerardoleonel.projectuts_eventorganizer.fragment.profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.AnnouncementAPI;
import com.gerardoleonel.projectuts_eventorganizer.api.EventAPI;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Announcement;
import com.gerardoleonel.projectuts_eventorganizer.events.EditEvent;
import com.gerardoleonel.projectuts_eventorganizer.fragment.history.AdapterHistory;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.android.volley.Request.Method.DELETE;

public class AdapterAnnouncement extends RecyclerView.Adapter<AdapterAnnouncement.AnnouncementViewHolder> {

    private List<Announcement> listAnnouncement;
    private Context context;
    private int idAnnounce;
    private AdapterAnnouncement.deleteItemListener mListener;

    public AdapterAnnouncement(List<Announcement> listAnnouncement, Context context, AdapterAnnouncement.deleteItemListener mListener) {
        this.listAnnouncement = listAnnouncement;
        this.context = context;
        this.mListener = mListener;
    }

    public interface deleteItemListener {
        void deleteItem( Boolean delete);
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_announcement, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        final Announcement announcement = listAnnouncement.get(position);
        holder.announceTitle.setText(announcement.getJudulPengumuman());
        holder.announceDescription.setText(announcement.getIsiPengumuman());

        holder.btnEditAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putSerializable("announce", announcement);
                Intent intent = new Intent(view.getContext(), EditAnnounce.class);
                intent.putExtras(data);
                view.getContext().startActivity(intent);
                ((ToDoList)view.getContext()).finish();
            }
        });

        holder.btnDeleteAnnounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idAnnounce = announcement.getId();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure want to delete this to do list? ");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteAnnounce();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listAnnouncement.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder{

        public AnnouncementViewHolder(@NonNull View itemView)
        {
            super(itemView);
            announceTitle = itemView.findViewById(R.id.tvAnnounceTitle);
            announceDescription = itemView.findViewById(R.id.tvAnnounceDescription);
            btnEditAnnounce = itemView.findViewById(R.id.btnEditAnnounce);
            btnDeleteAnnounce = itemView.findViewById(R.id.btnDeleteAnnounce);

        }
        TextView announceTitle, announceDescription;
        MaterialButton btnEditAnnounce, btnDeleteAnnounce;

    }

    public void deleteAnnounce(){
        RequestQueue queue = Volley.newRequestQueue(context);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Menghapus data...");
        progressDialog.setProgressStyle(android.app.ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(DELETE, AnnouncementAPI.URL_DELETE_ANNOUNCEMENT + idAnnounce, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                    mListener.deleteItem(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }


}
