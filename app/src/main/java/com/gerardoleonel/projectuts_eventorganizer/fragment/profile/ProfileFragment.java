package com.gerardoleonel.projectuts_eventorganizer.fragment.profile;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.R;
import com.gerardoleonel.projectuts_eventorganizer.api.AnnouncementAPI;
import com.gerardoleonel.projectuts_eventorganizer.api.CustomerAPI;
import com.gerardoleonel.projectuts_eventorganizer.databinding.FragmentProfileBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.AppDatabase;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.Announcement;
import com.gerardoleonel.projectuts_eventorganizer.pref.SharedPref;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class ProfileFragment extends Fragment {

    public static final int CAMERA_FROM_CODE = 101;
    public static final int CAMERA_REQUEST_CODE = 102;
    private FragmentProfileBinding binding;
    private static SharedPref pref;
    private static AppDatabase db;
    private static String foto_profil;
    private FirebaseAuth mAuth;
    private String username, email, phone, address;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.fragment_profile, container, false);
        getUser();

        mAuth = FirebaseAuth.getInstance();
        pref = new SharedPref(getActivity());

        binding.btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),EditProfile.class));
            }
        });

        binding.btnChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        binding.btnToDoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ToDoList.class));
            }
        });


        db = AppDatabase.getInstance(getActivity());

        if(pref.isChanged()==true)
        {
            foto_profil = pref.getSessionImage();
            byte[] bit = Base64.getDecoder().decode(foto_profil);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bit, 0, bit.length);
            binding.userAvatar.setImageBitmap(bitmap);
        }
        return binding.getRoot();
    }

    private void askCameraPermissions ()
    {
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_FROM_CODE);;
        }else{
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == CAMERA_FROM_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(getActivity(), "Camera Permission is Required to Use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST_CODE);
    }

//    private static class LoginTask extends AsyncTask<String, Integer, Boolean> {
//        @Override
//        protected Boolean doInBackground(String... x) {
//
//            User user = db.getUserDao().findUserByIdTok(pref.getSessionUsername());
//
//            //TODO: ini buat set string/image user didatabse, buat updatenya dia
//            user.setUserAvatar(foto_profil);
//            try
//            {
//                db.getUserDao().updateUser(user);
//            } catch (Exception E) {
//                System.out.println("Error edit DB " + E.getMessage());
//            }
//            return true;
//        }
//
//        protected void onPostExecute(Boolean b)
//        {
//
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CAMERA_REQUEST_CODE) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            binding.userAvatar.setImageBitmap(image);

            //converting bitmap to bytearray source: https://stackoverflow.com/questions/7620401/how-to-convert-byte-array-to-bitmap
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
            byte[] bitmapdata = blob.toByteArray();

            foto_profil = Base64.getEncoder().encodeToString(bitmapdata); // conver byte foto jadi string source: https://www.toptip.ca/2019/04/java-convert-byte-array-to-string-then.html
//            new LoginTask().execute();

//            Toast.makeText(this.getContext(), "Save data "+ foto_profil, Toast.LENGTH_SHORT).show();
            pref.imageCreated(foto_profil);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getUser() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
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
                            username = jsonObject.optString("username");
                            email = jsonObject.optString("email");
                            phone = jsonObject.optString("phone_number");
                            address = jsonObject.optString("address");

                            binding.profileUsername.setText(username);
                            binding.profileEmail.setText(email);
                            binding.profilePhone.setText(phone);
                            binding.profileAddress.setText(address);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getActivity(), response.optString("message"), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

}
