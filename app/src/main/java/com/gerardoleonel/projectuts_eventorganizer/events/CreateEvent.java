package com.gerardoleonel.projectuts_eventorganizer.events;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.HomeActivity;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.EventAPI;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Event;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class CreateEvent extends AppCompatActivity {

    private String selectedEventType, selectedEventPlace, selectedEventPackage;
    private Button btnCreate, btnCancel;
    private Event event;
    TextInputEditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        description = findViewById(R.id.txtDescription);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.eventType, android.R.layout.simple_spinner_item);
        final AutoCompleteTextView eventTypeDropDown = findViewById(R.id.txtEventType);
        eventTypeDropDown.setText(selectedEventType);
        eventTypeDropDown.setAdapter(adapter);
        eventTypeDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEventType = eventTypeDropDown.getEditableText().toString();
            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.eventPlace, android.R.layout.simple_spinner_item);
        final AutoCompleteTextView eventPlaceDropDown = findViewById(R.id.txtEventPlace);
        eventPlaceDropDown.setText(selectedEventPlace);
        eventPlaceDropDown.setAdapter(adapter2);
        eventPlaceDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEventPlace = eventPlaceDropDown.getEditableText().toString();
            }
        });

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
                R.array.eventPackage, android.R.layout.simple_spinner_item);
        final AutoCompleteTextView eventPackageDropDown = findViewById(R.id.txtEventPackage);
        eventPackageDropDown.setText(selectedEventPackage);
        eventPackageDropDown.setAdapter(adapter3);
        eventPackageDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedEventPackage = eventPackageDropDown.getEditableText().toString();
            }
        });

        btnCreate = findViewById(R.id.btnCreate);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateEvent.this, HomeActivity.class));
                finish();
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createEvent();
            }
        });

    }

    public void createEvent()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Adding Event...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan requesSt menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(POST, EventAPI.URL_ADD_EVENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);
                    //obj.getString("message") digunakan untuk mengambil pesan status dari response
                    startActivity(new Intent(CreateEvent.this, HomeActivity.class));
                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
                    Toast.makeText(CreateEvent.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Toast.makeText(CreateEvent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("eventType", selectedEventType);
                params.put("eventPlace", selectedEventPlace);
                params.put("eventPackage", selectedEventPackage);
                params.put("eventDescription", description.getText().toString());

                return params;
            }
        };
        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }
}