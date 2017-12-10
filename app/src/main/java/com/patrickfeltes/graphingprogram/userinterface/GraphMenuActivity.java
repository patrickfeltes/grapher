package com.patrickfeltes.graphingprogram.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.AuthenticatedActivity;

import java.util.ArrayList;
import java.util.List;

public class GraphMenuActivity extends AuthenticatedActivity {

    private List<GraphInfo> graphNames;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_menu);
        graphNames = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_graph_names);
        String UID = FirebaseAuth.getInstance().getUid();
        final GraphInfoAdapter adapter = new GraphInfoAdapter(graphNames);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // adds dividers between each item in the RecyclerView
        // source: https://stackoverflow.com/questions/31242812/how-can-a-divider-line-be-added-in-an-android-recyclerview
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);

        final GenericTypeIndicator<List<GraphInfo>> genericTypeIndicator =new GenericTypeIndicator<List<GraphInfo>>(){};
        FirebaseDatabase.getInstance().getReference("graphs-info").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                graphNames.addAll(dataSnapshot.getValue(genericTypeIndicator));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        Button addGraph = findViewById(R.id.b_add_graph);
        addGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addGraph();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.graph_menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
