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
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class CreateAnnounce extends AppCompatActivity {

    TextInputEditText txTitle, txDesc;
    Button btnCancel, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announce);

        txTitle = findViewById(R.id.txtAnnounceTitle);
        txDesc = findViewById(R.id.txtAnnounceDesc);

        btnCancel = findViewById(R.id.btnCancelAnnounce);
        btnCreate = findViewById(R.id.btnCreateAnnounce);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAnnounce.this, ToDoList.class));
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAnnounce();
                finish();
            }
        });
    }

    public void createAnnounce()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Adding Event...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan requesSt menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, AnnouncementAPI.URL_ADD_ANNOUNCEMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan status dari response
                    startActivity(new Intent(CreateAnnounce.this, ToDoList.class));
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(CreateAnnounce.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(CreateAnnounce.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String>  params = new HashMap<String, String>();

                params.put("email", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                params.put("judul_pengumuman", txTitle.getText().toString());
                params.put("isi_pengumuman", txDesc.getText().toString());

                return params;
            }
        };
        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

}