package com.patrickfeltes.graphingprogram.userinterface.genericactivities;

// idea for this abstract class comes from Ben Pankow:
// https://github.com/benpankow/pipeline-messenger/blob/master/app/src/main/java/com/benpankow/pipeline/activity/base/BaseActivity.java

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

/**
 * BaseActivity is a super class for all Activities
 */
public abstract class BaseActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        listener = createAuthStateListener();
    }

    protected abstract FirebaseAuth.AuthStateListener createAuthStateListener();

    public FirebaseAuth getAuth() {
        return auth;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(listener);
    }
}