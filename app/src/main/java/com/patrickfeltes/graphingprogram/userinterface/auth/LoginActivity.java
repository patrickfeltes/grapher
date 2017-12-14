package com.patrickfeltes.graphingprogram.userinterface.auth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.UnauthenticatedActivity;

/**
 * LoginActivity is where users will login.
 */
public class LoginActivity extends UnauthenticatedActivity implements View.OnClickListener {

    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.et_email);
        passwordField = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.b_login);
        Button registerButton = findViewById(R.id.b_register);

        loginButton.setOnClickListener(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), CreateAccountActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Successfully Logged In " +
                            getAuth().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid login credentials",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
