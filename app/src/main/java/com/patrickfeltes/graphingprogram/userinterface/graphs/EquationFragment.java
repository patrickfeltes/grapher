package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.database.objects.EquationList;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;
import com.patrickfeltes.graphingprogram.recyclerview.EquationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Equation fragment handles user input of equations for a graph.
 */
public class EquationFragment extends Fragment {

    private String graphKey;
    private RecyclerView recyclerView;
    private EquationAdapter adapter;

    public EquationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equation_layout, container, false);
        graphKey = getArguments().getString(ExtraKeys.GRAPH_KEY);

        setUpRecyclerView(view);
        fillRecyclerView();

        Button addEquations = view.findViewById(R.id.b_add_equations);
        addEquations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addEquation();
            }
        });

        return view;
    }

    /**
     * Set up recycler view creates the adapter and sets all necessary attributes for the view
     * @param view The view that RecyclerView belongs to
     */
    private void setUpRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.rv_equation_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // adds dividers between each item in the RecyclerView
        // source: https://stackoverflow.com/questions/31242812/how-can-a-divider-line-be-added-in-an-android-recyclerview
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );
        recyclerView.addItemDecoration(mDividerItemDecoration);
    }

    /**
     * Grabs the equations for this graph from Firebase and puts them into the RecyclerView
     */
    private void fillRecyclerView() {
        FirebaseRoutes.getGraphRoute(graphKey).addValueEventListener(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> equations = dataSnapshot.getValue(EquationList.class)
                            .equations;
                    if (equations == null) {
                        equations = new ArrayList<>();
                    }
                    adapter = new EquationAdapter(equations, graphKey);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
