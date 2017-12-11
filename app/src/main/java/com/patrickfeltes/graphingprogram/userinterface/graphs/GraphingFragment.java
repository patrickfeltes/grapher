package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.patrickfeltes.graphingprogram.R;

import java.util.ArrayList;
import java.util.List;

public class GraphingFragment extends Fragment {

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private int partitions;

    private List<String> equations;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graphing_layout, container, false);

        equations = new ArrayList<>();

        minX = -10;
        maxX = 10;
        minY = -10;
        maxY = 10;
        partitions = 100;

        // code from:
        // http://www.android-graphview.org/zooming-and-scrolling/
        final GraphView graph = view.findViewById(R.id.graph);

        String graphKey = getArguments().getString("graphKey");
        FirebaseDatabase.getInstance().getReference("graphs").child(graphKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> equationsToAdd = dataSnapshot.getValue(EquationList.class).equations;
                    if (equationsToAdd != null) {
                        equations.addAll(dataSnapshot.getValue(EquationList.class).equations);
                        for (String equation : equations) {
                            if (equation.trim().length() != 0) {
                                new EvaluatePointsAsyncTask(graph).execute(new GraphInformation(minX, maxX, partitions, equation));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        // set manual X bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(minY);
        graph.getViewport().setMaxY(maxY);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(minX);
        graph.getViewport().setMaxX(maxX);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        return view;
    }
}
