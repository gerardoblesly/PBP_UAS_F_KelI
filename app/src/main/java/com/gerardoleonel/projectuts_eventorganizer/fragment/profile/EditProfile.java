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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.HomeActivity;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.CustomerAPI;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;
import static com.android.volley.Request.Method.PUT;

public class EditProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnCancel, btnEdit;
    private String id, username, phone, address;
    private TextInputEditText txUsername, txPhone, txAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getUser();

        mAuth = FirebaseAuth.getInstance();

        txUsername = findViewById(R.id.txtEditUsername);
        txPhone = findViewById(R.id.txtEditPhoneNumber);
        txAddress = findViewById(R.id.txtEditAddress);

        btnCancel = findViewById(R.id.btnCancelEditProfile);
        btnEdit = findViewById(R.id.btnEditProfileConfirm);

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
                editProfile();
                finish();
            }
        });
    }



    public void editProfile()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Mengubah data profile");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest stringRequest = new StringRequest(PUT, CustomerAPI.URL_UPDATE_CUSTOMER + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);

                    Toast.makeText(EditProfile.this, obj.getString("message"), Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(EditProfile.this, HomeActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();

                params.put("username", txUsername.getText().toString());
                params.put("phone_number", txPhone.getText().toString());
                params.put("address", txAddress.getText().toString());

                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    private void getUser() {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading...");
        progressDialog.setTitle("Getting user data");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        final JsonObjectRequest stringRequest = new JsonObjectRequest(GET, CustomerAPI.URL_SELECT_CUSTOMER,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                try {
                    JSONArray jsonArray = response.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        if(jsonObject.optString("email").equalsIgnoreCase(mAuth.getCurrentUser().getEmail()))
                        {
                            id = jsonObject.optString("id");
                            username = jsonObject.optString("username");
                            phone = jsonObject.optString("phone_number");
                            address = jsonObject.optString("address");

                            txUsername.setText(username);
                            txPhone.setText(phone);
                            txAddress.setText(address);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(EditProfile.this, response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

}