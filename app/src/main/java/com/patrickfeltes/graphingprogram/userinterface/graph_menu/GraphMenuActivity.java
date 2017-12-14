package com.patrickfeltes.graphingprogram.userinterface.graph_menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseUtilities;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;
import com.patrickfeltes.graphingprogram.database.objects.GraphInfo;
import com.patrickfeltes.graphingprogram.recyclerview.GraphInfoAdapter;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.AuthenticatedActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphMenuActivity is the activity which holds a RecyclerView of graphs for the user to select
 * from.
 */
public class GraphMenuActivity extends AuthenticatedActivity {

    private List<GraphInfo> graphNames;

    public static final int ACTIVITY_RESULT_CODE = 0;

    private GraphInfoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_menu);

        setUpRecyclerView();

        Button addGraph = findViewById(R.id.b_add_graph);
        addGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewGraphActivity.class);
                ((Activity)(view.getContext())).startActivityForResult(intent,
                        ACTIVITY_RESULT_CODE);
            }
        });
    }

    private void setUpRecyclerView() {
        final RecyclerView recyclerView = findViewById(R.id.rv_graph_names);
        String UID = getAuth().getUid();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration decoration = new DividerItemDecoration(
                recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);

        final GenericTypeIndicator<List<GraphInfo>> genericTypeIndicator =
                new GenericTypeIndicator<List<GraphInfo>>(){};
        // set up listener to update the recycler view whenever something changes
        FirebaseRoutes.getGraphInfoForUser(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    graphNames = dataSnapshot.getValue(genericTypeIndicator);
                    if (graphNames == null) {
                        graphNames = new ArrayList<>();
                    }
                    adapter = new GraphInfoAdapter(graphNames);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // when a result comes bask from NewGraphActivity, add it to the database
        if (requestCode == ACTIVITY_RESULT_CODE && resultCode == Activity.RESULT_OK) {
            String newGraphName = data.getStringExtra(ExtraKeys.GRAPH_NAME);
            FirebaseUtilities.createNewGraph(getAuth().getUid(), newGraphName, graphNames, adapter);
        }
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
                getAuth().signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
