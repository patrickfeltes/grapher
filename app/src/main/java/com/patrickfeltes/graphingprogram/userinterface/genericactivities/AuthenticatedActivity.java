package com.patrickfeltes.graphingprogram.userinterface.genericactivities;

// idea for this abstract class comes from Ben Pankow:
// https://github.com/benpankow/pipeline-messenger/blob/master/app/src/main/java/com/benpankow/pipeline/activity/base/AuthenticatedActivity.java


import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.patrickfeltes.graphingprogram.userinterface.auth.LoginActivity;

public abstract class AuthenticatedActivity extends BaseActivity {

    protected FirebaseAuth.AuthStateListener createAuthStateListener() {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();

                // don't let not logged in users be on authenticated activities
                if (currentUser == null) {
                    Intent loginIntent = new Intent(AuthenticatedActivity.this, LoginActivity.class);
                    AuthenticatedActivity.this.startActivity(loginIntent);
                }
            }
        };
    }



}
