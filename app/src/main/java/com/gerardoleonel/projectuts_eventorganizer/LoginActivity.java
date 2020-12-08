package com.gerardoleonel.projectuts_eventorganizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.gerardoleonel.projectuts_eventorganizer.databinding.ActivityLoginBinding;
import com.gerardoleonel.projectuts_eventorganizer.db.AppDatabase;
import com.gerardoleonel.projectuts_eventorganizer.db.entity.User;
import com.gerardoleonel.projectuts_eventorganizer.pref.SharedPref;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private AppDatabase db;
    private SharedPref pref;
    private String CHANNEL_ID = "Channel 1";
    FirebaseAuth firebaseAuth;

    private String email, password;
    private static final String TAG = "EmailPassword";

    private static final int LOGIN_SUCCESS = 1;
    private static final int LOGIN_FAILED = 2;
    private static final int EMPTY_FIELD = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        db = AppDatabase.getInstance(this);
        firebaseAuth = FirebaseAuth.getInstance();
        pref = new SharedPref(this);
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLogin();
            }
        });
        binding.tvGotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        binding.setUser(new User());
    }

    private void doLogin() {
        User user = binding.getUser();
        if(isValid(user))
        {
            signInWithFirebase();
//            new LoginTask().execute();
        }
        else
        {
            makeToast("All field must be filled");
        }

    }

    private boolean isValid(User user) {
        if(user.getEmail() == null || user.getEmail().equals("")) return false;
        if(user.getPassword() == null || user.getPassword().equals("")) return false;
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    private void signInWithFirebase(){

        email = binding.getUser().getEmail();
        password = binding.getUser().getPassword();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "Authentication Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            User userThis = binding.getUser();
                            if (user.isEmailVerified())
                            {
                                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
                                createNotificationChannel();
                                addNotification();
                                finish();
                            }
                            else{
                                //gagal login
                                Toast.makeText(LoginActivity.this, "Silahkan verifikasi email dahulu",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // ...
                        }

                        // ...
                    }
                });
    }

//    private class LoginTask extends AsyncTask<URL, Integer, Long>
//    {
//        @Override
//        protected Long doInBackground(URL... urls) {
//            User user = binding.getUser();
//            long flagMessage = LOGIN_SUCCESS;
////            if(isValid(user)) {
//                User loggedInUser = db.getUserDao().findUserById(user.getUsername(), user.getPassword());
////                if(loggedInUser != null) {
////                    flagMessage = LOGIN_SUCCESS;
//                    pref.createSession(loggedInUser);
////                }
////                else {
////                    flagMessage = LOGIN_FAILED;
////                }
////            }
////            else flagMessage = EMPTY_FIELD;
//            return flagMessage;
//        }
//
//        @Override
//        protected void onPostExecute(Long aLong) {
//            super.onPostExecute(aLong);
////            if(aLong == LOGIN_SUCCESS) {
////                makeToast(getResources().getString(R.string.text_login_success));
////                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
////                createNotificationChannel();
////                addNotification();
////                finish();
////            }
////            else if(aLong == LOGIN_FAILED) {
////                makeToast(getResources().getString(R.string.text_user_is_invalid));
////            }
////            else if(aLong == EMPTY_FIELD) {
////                makeToast(getResources().getString(R.string.text_field_empty));
////            }
//        }
//    }

    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Holla!")
                .setContentText("Welcome to Filography!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);


        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    private void makeToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}