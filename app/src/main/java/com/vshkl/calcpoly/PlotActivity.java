package com.vshkl.calcpoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;

public class PlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        Bundle bundle = this.getIntent().getExtras();
        double[] points = bundle.getDoubleArray("points");
        int size = bundle.getInt("size");

        GraphView graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            dataPoints[i] = new DataPoint(i, points[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(size);
        graph.getViewport().setMaxY(points[size-1]);
        graph.addSeries(series);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
