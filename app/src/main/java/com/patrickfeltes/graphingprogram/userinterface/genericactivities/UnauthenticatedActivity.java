package com.patrickfeltes.graphingprogram.userinterface.genericactivities;

// idea for this abstract class comes from Ben Pankow:
// https://github.com/benpankow/pipeline-messenger/blob/master/app/src/main/java/com/benpankow/pipeline/activity/base/UnauthenticatedActivity.java

import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.patrickfeltes.graphingprogram.userinterface.graph_menu.GraphMenuActivity;

/**
 * An UnauthenticatedActivity does not require a user to be logged in
 */
public abstract class UnauthenticatedActivity extends BaseActivity {

    protected FirebaseAuth.AuthStateListener createAuthStateListener() {
        return new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                // don't let logged in users be on unauthenticated pages
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(UnauthenticatedActivity.this, GraphMenuActivity.class);
                    UnauthenticatedActivity.this.startActivity(intent);
                }
            }
        };
    }

}
