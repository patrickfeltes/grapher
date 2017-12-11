package com.patrickfeltes.graphingprogram.database;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.patrickfeltes.graphingprogram.Graph;
import com.patrickfeltes.graphingprogram.userinterface.GraphInfo;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtilities {

    public static void addUserToDatabase(String UID, String username) {
        DatabaseReference usersTree = FirebaseDatabase.getInstance().getReference("users");
        usersTree.child(UID).child("username").setValue(username);
    }

    public static void createNewGraph(final String UID, final String graphName, final List<GraphInfo> graphNames, final RecyclerView.Adapter adapter) {
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("graphs").push();
        ref.setValue(new Graph(new ArrayList<String>(), graphName)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                String graphKey = ref.getKey();
                graphNames.add(new GraphInfo(graphKey, graphName));
                FirebaseDatabase.getInstance().getReference("graphs-info").child(UID).setValue(graphNames).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

}
