package com.patrickfeltes.graphingprogram.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Utility functions to get locations in database tree.
 */
public class FirebaseRoutes {

    public static final String GRAPH_ROUTE = "graphs";
    public static final String USER_ROUTE = "users";
    public static final String GRAPH_INFO_ROUTE = "graphs-info";

    public static final String EQUATION_KEY = "equations";
    public static final String USERNAME_KEY = "username";

    public static DatabaseReference getGraphRoute(String graphKey) {
        return FirebaseDatabase.getInstance().getReference(GRAPH_ROUTE).child(graphKey);
    }

    public static DatabaseReference getUserRoute(String UID) {
        return FirebaseDatabase.getInstance().getReference(USER_ROUTE).child(UID);
    }

    public static DatabaseReference getGraphInfoForUser(String UID) {
        return FirebaseDatabase.getInstance().getReference(GRAPH_INFO_ROUTE).child(UID);
    }

    public static DatabaseReference getGraphEquationsRoute (String graphKey) {
        return FirebaseDatabase.getInstance().getReference(GRAPH_ROUTE).child(graphKey)
                .child(EQUATION_KEY);
    }

}
