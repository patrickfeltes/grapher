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

    private List<String> equations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graphing_layout, container, false);

        equations = new ArrayList<>();

        final GraphView graph = view.findViewById(R.id.graph);

        String graphKey = getArguments().getString(ExtraKeys.GRAPH_KEY);
        FirebaseRoutes.getGraphRoute(graphKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> equationsToAdd = dataSnapshot.getValue(EquationList.class).equations;
                    if (equationsToAdd != null) {
                        equations.addAll(dataSnapshot.getValue(EquationList.class).equations);
                        for (String equation : equations) {
                            if (equation.trim().length() != 0) {
                                new PlotTask(graph).execute(new GraphViewInfoBundle(minX, maxX, numberOfPartitions, equation));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // set bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(minY);
        graph.getViewport().setMaxY(maxY);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);

        // allow zooming and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        return view;
    }
}
