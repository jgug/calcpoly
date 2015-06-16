package com.vshkl.calcpoly;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class PlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = this.getIntent().getExtras();
        double[] points = bundle.getDoubleArray("points");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int max = Integer.parseInt(preferences.getString(
                getString(R.string.pref_max_key), getString(R.string.pref_max_default)));

        GraphView graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[max];
        for (int i = 0; i < max; i++) {
            dataPoints[i] = new DataPoint(i, points[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(max);
        graph.getViewport().setMaxY(points[max-1]);
        graph.addSeries(series);
    }
}
