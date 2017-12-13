package com.patrickfeltes.graphingprogram.database.objects;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;

import java.util.ArrayList;
import java.util.List;

public class UserHolder extends RecyclerView.ViewHolder {

    private TextView username;
    private String graphName;
    private String uid;

    private List<GraphInfo> graphs;

    private Context context;

    public UserHolder(View itemView, final String graphKey, final EditText input) {
        super(itemView);

        username = itemView.findViewById(R.id.tv_user);
        graphs = new ArrayList<>();
        context = itemView.getContext();

        final GenericTypeIndicator<List<GraphInfo>> genericTypeIndicator = new GenericTypeIndicator<List<GraphInfo>>(){};
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseRoutes.getGraphRoute(graphKey).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        graphName = dataSnapshot.getValue(Graph.class).name;
                        if (uid != null) {
                            FirebaseRoutes.getGraphInfoForUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    graphs.addAll(dataSnapshot.getValue(genericTypeIndicator));
                                    graphs.add(new GraphInfo(graphKey, graphName));
                                    FirebaseRoutes.getGraphInfoForUser(uid).setValue(graphs).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, graphName + " successfully shared.", Toast.LENGTH_SHORT).show();
                                            input.setText("");
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
            }
        });
    }

    public void bind(User user) {
        username.setText(user.username);
        this.uid = user.uid;
    }
}
