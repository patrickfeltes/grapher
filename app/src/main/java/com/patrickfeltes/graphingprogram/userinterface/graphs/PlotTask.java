package com.patrickfeltes.graphingprogram.userinterface.graphs;

import android.graphics.Color;
import android.os.AsyncTask;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.patrickfeltes.graphingprogram.parser.Parser;
import com.patrickfeltes.graphingprogram.parser.Tokenizer;
import com.patrickfeltes.graphingprogram.parser.ast.Node;
import com.patrickfeltes.graphingprogram.parser.exceptions.InvalidExpressionException;

import java.util.HashMap;
import java.util.Random;

/**
 * PlotTask evaluates an equation over a set of points and plots it on the passed GraphView.
 */
public class PlotTask extends AsyncTask<GraphViewInfoBundle, Void, LineGraphSeries<DataPoint>> {

    private GraphView graphView;

    public PlotTask(GraphView graphView) {
        this.graphView = graphView;
    }

    @Override
    protected LineGraphSeries<DataPoint> doInBackground(GraphViewInfoBundle... graphInformations) {
        GraphViewInfoBundle graphInformation = graphInformations[0];
        LineGraphSeries<DataPoint> series = null;
        try {
            Node equation = new Parser(new Tokenizer(graphInformation.getEquation())).parse();
            double partitionLength = (graphInformation.getMaxX() - graphInformation.getMinX()) / graphInformation.getPartitions();
            DataPoint[] points = new DataPoint[graphInformation.getPartitions()];
            HashMap<String, Double> map = new HashMap<>();
            double x = graphInformation.getMinX();
            for (int i = 0; i < graphInformation.getPartitions(); i++) {
                map.put("x", x);
                points[i] = new DataPoint(x, equation.evaluate(map));
                x += partitionLength;
            }
            series = new LineGraphSeries<>(points);
        } catch(InvalidExpressionException e) {
            e.printStackTrace();
        }

        return series;
    }

    @Override
    protected void onPostExecute(LineGraphSeries<DataPoint> points) {
        super.onPostExecute(points);
        Random random = new Random();
        points.setColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        graphView.addSeries(points);
    }
}
