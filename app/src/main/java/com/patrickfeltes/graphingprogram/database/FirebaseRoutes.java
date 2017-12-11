package com.patrickfeltes.graphingprogram.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Utility functions to get locations in database tree.
 */
public class FirebaseRoutes {

    public static DatabaseReference getGraphRoute(String graphKey) {
        return FirebaseDatabase.getInstance().getReference("graphs").child(graphKey);
    }

    public static DatabaseReference getUserRoute(String UID) {
        return FirebaseDatabase.getInstance().getReference("users").child(UID);
    }

    public static DatabaseReference getGraphInfoForUser(String UID) {
        return FirebaseDatabase.getInstance().getReference("graphs-info").child(UID);
    }

    public static DatabaseReference getGraphEquationsRoute (String graphKey) {
        return FirebaseDatabase.getInstance().getReference("graphs").child(graphKey).child("equations");
    }

}
