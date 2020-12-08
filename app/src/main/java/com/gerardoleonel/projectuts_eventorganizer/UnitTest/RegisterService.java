package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gerardoleonel.projectuts_eventorganizer.LoginActivity;
import com.gerardoleonel.projectuts_eventorganizer.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterService {

    int cek=0;
    private static final String TAG = "EmailPassword";

    public void register(final RegisterView view, String username, String password, String email, String nohp, String address ) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            cek = 1;
                            view.startMainActivity();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                } else {
                    cek = 0;
                }
            }
        });
    }

    public Boolean getValid(final RegisterView view, String username, String email, String password, String nohp, String address){
        final Boolean[] bool = new Boolean[1];
        register(view, username, password, email, nohp, address) ;
        if (cek == 1 ){
            bool[0] = true;
        } else {
            bool[0] = false;
        }
        return bool[0];
    }

}
