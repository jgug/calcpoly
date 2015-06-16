package com.vshkl.calcpoly.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.vshkl.calcpoly.R;

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
        double step = Double.parseDouble(preferences.getString(
                getString(R.string.pref_step_key), getString(R.string.pref_step_default)));
        int size = (int)(max/step);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[size];
        for (int i = 0; i < size; i++) {
            dataPoints[i] = new DataPoint(i, points[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(max);
        graph.getViewport().setMaxY(points[max-1]);
        graph.addSeries(series);
    }
}
