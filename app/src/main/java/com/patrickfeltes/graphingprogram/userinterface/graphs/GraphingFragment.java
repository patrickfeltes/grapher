package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.patrickfeltes.graphingprogram.ExtraKeys;
import com.patrickfeltes.graphingprogram.R;
import com.patrickfeltes.graphingprogram.asynctasks.GraphViewInfoBundle;
import com.patrickfeltes.graphingprogram.asynctasks.PlotTask;
import com.patrickfeltes.graphingprogram.database.objects.EquationList;
import com.patrickfeltes.graphingprogram.database.FirebaseRoutes;

import java.util.ArrayList;
import java.util.List;

/**
 * GraphingFragment handles all of the plotting of graphs in the GraphingActivity
 */
public class GraphingFragment extends Fragment {

    private final double minX = -10;
    private final double maxX = 10;
    private final double minY = -10;
    private final double maxY = 10;
    private final int numberOfPartitions = 500;

    private GraphView graphView;
    private String graphKey;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graphing_layout, container, false);

        graphView = view.findViewById(R.id.graph);
        graphKey = getArguments().getString(ExtraKeys.GRAPH_KEY);

        fillGraph();

        // set bounds
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(minY);
        graphView.getViewport().setMaxY(maxY);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(minX);
        graphView.getViewport().setMaxX(maxX);

        // allow zooming and scrolling
        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScalableY(true);

        return view;
    }

    /**
     * Fills the GraphView with the equations from Firebase
     */
    private void fillGraph() {
        FirebaseRoutes.getGraphRoute(graphKey).addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    plotEquations(dataSnapshot.getValue(EquationList.class).equations);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Takes a list of equations and attempts to plot them
     * @param equations The equations to plot
     */
    private void plotEquations(List<String> equations) {
        if (equations != null) {
            for (String equation : equations) {
                // if equation isn't blank, create a plot task to evaluate it
                if (equation.trim().length() != 0) {
                    new PlotTask(graphView).execute(new GraphViewInfoBundle(minX, maxX,
                            numberOfPartitions, equation));
                }
            }
        }
    }
}
