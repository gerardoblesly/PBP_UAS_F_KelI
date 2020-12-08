package com.gerardoleonel.projectuts_eventorganizer.UnitTest;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gerardoleonel.projectuts_eventorganizer.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import static android.widget.Toast.LENGTH_SHORT;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private MaterialButton btnRegister;
    private TextInputEditText username, email, password, noHP, address;
    private RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnRegister = findViewById(R.id.btnRegister);
        username = findViewById(R.id.txUsername);
        email = findViewById(R.id.txEmail);
        password = findViewById(R.id.txPassword);
        noHP = findViewById(R.id.txNomorHp);
        address = findViewById(R.id.txAddress);
        presenter = new RegisterPresenter(this, new RegisterService());
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onRegisterClicked();
            }
        });
    }

    @Override
    public String getUsername() {
        return username.getText().toString();
    }

    @Override
    public void showUsernameError(String message) {
        username.setError(message);
    }

    @Override
    public String getEmail() {
        return email.getText().toString();
    }

    @Override
    public void showEmailError(String message) {
        email.setError(message);
    }

    @Override
    public String getPassword() {
        return password.getText().toString();
    }

    @Override
    public void showPasswordError(String message) {
        password.setError(message);
    }

    @Override
    public String getNoHp() {
        return noHP.getText().toString();
    }

    @Override
    public void showNoHpError(String message) {
        noHP.setError(message);
    }

    @Override
    public String getAddress() {
        return address.getText().toString();
    }

    @Override
    public void showAddressError(String message) {
        address.setError(message);
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showRegisterError(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }
    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, LENGTH_SHORT).show();
    }

}
