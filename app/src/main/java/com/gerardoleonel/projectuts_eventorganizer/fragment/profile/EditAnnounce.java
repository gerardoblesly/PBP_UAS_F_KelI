package com.gerardoleonel.projectuts_eventorganizer.fragment.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.HomeActivity;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.AnnouncementAPI;
import com.gerardoleonel.projectuts_eventorganizer.api.EventAPI;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Announcement;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Event;
import com.gerardoleonel.projectuts_eventorganizer.events.EditEvent;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.PUT;

public class EditAnnounce extends AppCompatActivity
{
    private Button btnCancel, btnEdit;
    TextInputEditText txTitle, txDesc;
    private int idAnnounce;
    private Announcement announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_announce);

        Bundle bundle = getIntent().getExtras();
        announcement = (Announcement) bundle.getSerializable("announce");

        idAnnounce = announcement.getId();

        btnCancel = findViewById(R.id.btnCancelEditAnnounce);
        btnEdit = findViewById(R.id.btnEditAnnounceConfirm);

        txTitle = findViewById(R.id.txtEditJudulAnnounce);
        txDesc = findViewById(R.id.txtEditIsiAnnounce);

        txTitle.setText(announcement.getJudulPengumuman());
        txDesc.setText(announcement.getIsiPengumuman());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editAnnounce();
                finish();
            }
        });
    }

    public void editAnnounce()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("To do list being updated");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(PUT, AnnouncementAPI.URL_UPDATE_ANNOUNCEMENT + idAnnounce, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    Toast.makeText(EditAnnounce.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(EditAnnounce.this, ToDoList.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditAnnounce.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("judul_pengumuman", txTitle.getText().toString());
                params.put("isi_pengumuman", txDesc.getText().toString());

                return params;
            }
        };
        queue.add(stringRequest);
    }


}