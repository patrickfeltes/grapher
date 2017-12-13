package com.patrickfeltes.graphingprogram.recyclerview;

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
import com.patrickfeltes.graphingprogram.database.objects.GraphInfo;
import com.patrickfeltes.graphingprogram.database.objects.User;

import java.util.ArrayList;
import java.util.List;

public class UserHolder extends RecyclerView.ViewHolder {

    private TextView username;
    private String uid;
    private String graphKey;
    private String graphName;

    private EditText input;

    private Context context;

    public UserHolder(View itemView, String graphKey, String graphName, EditText input) {
        super(itemView);

        username = itemView.findViewById(R.id.tv_user);
        context = itemView.getContext();
        this.input = input;
        this.graphKey = graphKey;
        this.graphName = graphName;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareGraphWithUser();
            }
        });
    }

    public void bind(User user) {
        username.setText(user.username);
        this.uid = user.uid;
    }

    /**
     * Shares the current graph with a user
     */
    private void shareGraphWithUser() {
        final GenericTypeIndicator<List<GraphInfo>> genericTypeIndicator =
                new GenericTypeIndicator<List<GraphInfo>>(){};
        if (uid != null) {
            FirebaseRoutes.getGraphInfoForUser(uid).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // get all graphs for the user
                    List<GraphInfo> graphs = dataSnapshot.getValue(genericTypeIndicator);
                    if (graphs == null) {
                        graphs = new ArrayList<>();
                    }

                    // add this graph to the user and push it to the database
                    graphs.add(new GraphInfo(graphKey, graphName));
                    FirebaseRoutes.getGraphInfoForUser(uid).setValue(graphs).addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, graphName + " successfully shared.",
                                    Toast.LENGTH_SHORT).show();
                            input.setText("");
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });
        }
    }
}
