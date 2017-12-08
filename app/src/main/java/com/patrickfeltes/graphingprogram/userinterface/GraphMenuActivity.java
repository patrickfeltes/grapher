package com.patrickfeltes.graphingprogram.userinterface;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.userinterface.genericactivities.AuthenticatedActivity;

import java.util.ArrayList;
import java.util.List;

public class GraphMenuActivity extends AuthenticatedActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_menu);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_graph_names);

        List<String> names = new ArrayList<>();
        names.add("Graph 1");
        final GraphInfoAdapter adapater = new GraphInfoAdapter(names);
        recyclerView.setAdapter(adapater);

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

        Button addGraph = findViewById(R.id.b_add_graph);
        addGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapater.addGraph();
            }
        });
    }
}
