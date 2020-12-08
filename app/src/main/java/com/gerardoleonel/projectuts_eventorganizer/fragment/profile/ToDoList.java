package com.gerardoleonel.projectuts_eventorganizer.fragment.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.AnnouncementAPI;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Announcement;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ToDoList extends AppCompatActivity {


    private RecyclerView recyclerView;
    private List<Announcement> listAnnouncement;
    private AdapterAnnouncement adapter;
    private FirebaseAuth mAuth;
    Button btnAddToDoList, btnBackToDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        mAuth = FirebaseAuth.getInstance();
        setAdapter();
        getAnnouncement();

        btnBackToDoList = findViewById(R.id.btnBackToDoList);
        btnBackToDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnAddToDoList = findViewById(R.id.btnAddToDoList);
        btnAddToDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ToDoList.this, CreateAnnounce.class));
                finish();
            }
        });

    }

    public void setAdapter(){
        setTitle("Profile");
        listAnnouncement = new ArrayList<Announcement>();
        recyclerView = findViewById(R.id.rvAnnouncement);
        adapter = new AdapterAnnouncement(listAnnouncement, this, new AdapterAnnouncement.deleteItemListener() {
            @Override
            public void deleteItem(Boolean delete) {
                if(delete)
                {
                    getAnnouncement();
                }
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public void getAnnouncement() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Getting your todo list....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, AnnouncementAPI.URL_SELECT_ANNOUNCEMENT,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    if (!listAnnouncement.isEmpty())
                        listAnnouncement.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        if(mAuth.getCurrentUser().getEmail().equalsIgnoreCase(jsonObject.optString("email")))
                        {
                            String idPengumuman = jsonObject.optString("id");
                            String judulPengumuman = jsonObject.optString("judul_pengumuman");
                            String isiPengumuman = jsonObject.optString("isi_pengumuman");

                            Announcement announcement = new Announcement(Integer.parseInt(idPengumuman),judulPengumuman, isiPengumuman);

                            listAnnouncement.add(announcement);
                        }
                    }
                    System.out.println("sizenya ada : " +listAnnouncement.size());
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(ToDoList.this, response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ToDoList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }
}