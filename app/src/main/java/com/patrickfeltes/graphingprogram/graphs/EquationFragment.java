package com.patrickfeltes.graphingprogram.graphs;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.patrickfeltes.graphingprogram.R;

import java.util.ArrayList;
import java.util.List;


public class EquationFragment extends Fragment {

    private List<Equation> equations;

    public EquationFragment() {
        equations = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.equation_layout, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_equation_list);
        Button addEquations = (Button) view.findViewById(R.id.b_add_equations);

        final EquationAdapter adapter = new EquationAdapter(equations);
        recyclerView.setAdapter(adapter);

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

        addEquations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addEquation();
            }
        });

        return view;
    }

    public List<Equation> getEquationsList() {
        return equations;
    }
}
