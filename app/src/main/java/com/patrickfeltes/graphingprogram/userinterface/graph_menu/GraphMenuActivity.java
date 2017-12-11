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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.FirebaseUtilities;
import com.patrickfeltes.graphingprogram.database.objects.GraphInfo;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.AuthenticatedActivity;

import java.util.ArrayList;
import java.util.List;

public class GraphMenuActivity extends AuthenticatedActivity {

    private List<GraphInfo> graphNames;

    public static final int ACTIVITY_RESULT_CODE = 0;

    private GraphInfoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_menu);
        graphNames = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.rv_graph_names);
        String UID = FirebaseAuth.getInstance().getUid();
        adapter = new GraphInfoAdapter(graphNames);
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
                if (dataSnapshot.exists()) {
                    graphNames.addAll(dataSnapshot.getValue(genericTypeIndicator));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        Button addGraph = findViewById(R.id.b_add_graph);
        addGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewGraphActivity.class);
                ((Activity)(view.getContext())).startActivityForResult(intent, ACTIVITY_RESULT_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (ACTIVITY_RESULT_CODE) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newGraphName = data.getStringExtra("graphName");
                    FirebaseUtilities.createNewGraph(FirebaseAuth.getInstance().getUid(), newGraphName, graphNames, adapter);
                }
                break;
            }
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
                FirebaseAuth.getInstance().signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
