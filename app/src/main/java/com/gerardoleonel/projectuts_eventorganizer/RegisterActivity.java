package com.gerardoleonel.projectuts_eventorganizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gerardoleonel.projectuts_eventorganizer.api.CustomerAPI;
import com.gerardoleonel.projectuts_eventorganizer.api.EventAPI;
import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivityRegisterBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.AppDatabase;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.User;
import com.gerardoleonel.projectuts_eventorganizer.events.CreateEvent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.Request.Method.POST;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
//    private AppDatabase db;

    //    private static final int REGISTER_SUCCESS = 1;
    //    private static final int REGISTER_FAILED = 2;
    //    private static final int EMPTY_FIELD = 3;
    //    private static final int NOT_UNIQUE = 4;
    //    private static final int PASSWORD_SHORT = 5;
    //    private static final int EMAIL_PATTERN = 6;
    //    long UniqueCheck = -1;
    private String email, password;
    private static final String TAG = "EmailPassword";
    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
//        db = AppDatabase.getInstance(this);
        firebaseAuth = FirebaseAuth.getInstance();
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRegister();
            }
        });
        binding.tvGotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
        binding.setUser(new User());
    }

    private void doRegister() {
//        new checkUnique().execute();
//        new RegisterTask().execute();
        User user = binding.getUser();
        if(isValid(user))
        {
            signUpWithFirebase();
        }
        else
        {
            makeToast("All field must be filled!");
        }
    }
    private void signUpWithFirebase(){
        email = binding.getUser().getEmail();
        password = binding.getUser().getPassword();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(RegisterActivity.this, "Authentication Success",
                                    Toast.LENGTH_SHORT).show();
//                            FirebaseUser userfb = fb.getCurrentUser();
                            System.out.println("email yang resigtr 222: "+firebaseAuth.getCurrentUser().getEmail());
                            sendEmailVerification();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification(){
        FirebaseUser userfb = firebaseAuth.getCurrentUser();
        userfb.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,
                                    "Verification email sent to " + userfb.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                            createCustomer();
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(RegisterActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private boolean isValid(User user) {
        if(user.getUsername() == null || user.getUsername().equals("")) return false;
        if(user.getPassword() == null || user.getPassword().equals("")) return false;
        if(user.getAlamat() == null || user.getAlamat().equals("")) return false;
        if(user.getNoHP() == null || user.getNoHP().equals("")) return false;
        if(user.getEmail() == null || user.getEmail().equals("")) return false;
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) { // supaya keyboard bisa nutup saat menekan UI diluar keyboard
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void createCustomer()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading....");
        progressDialog.setTitle("Register on Progress...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(POST, CustomerAPI.URL_ADD_CUSTOMER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    Log.d(TAG, "onResponse: ");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                progressDialog.dismiss();
                Log.d(TAG, "This one error response: " +error.getMessage());
//                Toast.makeText(CreateEvent.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                User user = binding.getUser();
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username", user.getUsername());
                params.put("password", user.getPassword());
                params.put("email", user.getEmail());
                params.put("phone_number", user.getNoHP());
                params.put("address", user.getAlamat());

                return params;
            }
        };
        queue.add(stringRequest);
    }

//    private class RegisterTask extends AsyncTask<URL, Integer, Long>
//    {
//        @Override
//        protected Long doInBackground(URL... urls) {
//            User user = binding.getUser();
//            long flagMessage = -1;
//            if(isValid(user))
//            {
//                if(UniqueCheck==0)
//                {
//                    flagMessage = NOT_UNIQUE;
//                }
//                else if(user.getPassword().length() < 6)
//                {
//                    flagMessage = PASSWORD_SHORT;
//                }
//                else if(!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches())
//                {
//                    flagMessage = EMAIL_PATTERN;
//                }
//                else {
//                    try {
//                        db.getUserDao().createUser(user);
//                        flagMessage = REGISTER_SUCCESS;
//                    } catch (Exception e) {
//                        flagMessage = REGISTER_FAILED;
//                    }
//                }
//            }
//            else
//            {
//                flagMessage = EMPTY_FIELD;
//            }
//            return flagMessage;
//        }
//
//        @Override
//        protected void onPostExecute(Long aLong) {
//            super.onPostExecute(aLong);
//            if(aLong == REGISTER_SUCCESS) {
//                makeToast(getResources().getString(R.string.text_register_success));
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                finish();
//            }
//            else if(aLong == REGISTER_FAILED) {
//                makeToast(getResources().getString(R.string.text_something_error));
//            }
//            else if(aLong == EMPTY_FIELD) {
//                makeToast(getResources().getString(R.string.text_field_empty));
//            }
//            else if(aLong == PASSWORD_SHORT) {
//                makeToast(getResources().getString(R.string.text_password_short));
//            }
//            else if(aLong == NOT_UNIQUE) {
//                makeToast(getResources().getString(R.string.text_not_unique));
//            }
//            else if(aLong == EMAIL_PATTERN){
//                makeToast(getResources().getString(R.string.text_email_pattern));
//            }
//        }
//    }

//    private class checkUnique extends AsyncTask<URL, Integer, Long>
//    {
//        @Override
//        protected Long doInBackground(URL... urls)
//        {
//            User user = binding.getUser();
//            List<User> ListCheck = new ArrayList<>();
//            ListCheck = db.getUserDao().UserUnique(user.getUsername());
//
//            if(ListCheck.size()>0) {
//                UniqueCheck = 0;
//            }else
//            {
//                UniqueCheck = 1;
//            }
//            return UniqueCheck;
//        }
//    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}