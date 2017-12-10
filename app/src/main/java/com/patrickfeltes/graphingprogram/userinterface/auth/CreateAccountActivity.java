package com.patrickfeltes.graphingprogram.userinterface.auth;

import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseUtilities;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.UnauthenticatedActivity;

public class CreateAccountActivity extends UnauthenticatedActivity implements View.OnClickListener{

    private EditText emailField;
    private EditText passwordField;
    private Button createAccountButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth = FirebaseAuth.getInstance();

        emailField = findViewById(R.id.et_email);
        passwordField = findViewById(R.id.et_password);
        createAccountButton = findViewById(R.id.b_create_account);

        createAccountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(CreateAccountActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                    // when a new user is created, add them to the database
                    String UID = task.getResult().getUser().getUid();
                    String username = emailField.getText().toString();
                    FirebaseUtilities.addUserToDatabase(UID, username);
                } else {
                    Toast.makeText(CreateAccountActivity.this, "Invalid username/password combo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}