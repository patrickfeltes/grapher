package com.patrickfeltes.graphingprogram.database;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.patrickfeltes.graphingprogram.database.objects.Graph;
import com.patrickfeltes.graphingprogram.database.objects.GraphInfo;
import com.patrickfeltes.graphingprogram.database.objects.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions for Firebase
 */
public class FirebaseUtilities {

    public static void addUserToDatabase(String UID, String username) {
        FirebaseRoutes.getUserRoute(UID).setValue(new User(username, UID));
    }

    public static void createNewGraph(final String UID, final String graphName,
                                      final List<GraphInfo> graphNames,
                                      final RecyclerView.Adapter adapter) {
        final DatabaseReference newLocation = FirebaseDatabase.getInstance()
                .getReference("graphs").push();
        newLocation.setValue(new Graph(new ArrayList<String>(), graphName))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String graphKey = newLocation.getKey();
                graphNames.add(new GraphInfo(graphKey, graphName));
                FirebaseRoutes.getGraphInfoForUser(UID).setValue(graphNames)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
