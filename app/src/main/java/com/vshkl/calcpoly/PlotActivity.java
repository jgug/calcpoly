package com.vshkl.calcpoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class PlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        Bundle bundle = this.getIntent().getExtras();
        double[] points = bundle.getDoubleArray("points");

        GraphView graph = (GraphView) findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[100];
        for (int i = 0; i < 100; i++) {
            dataPoints[i] = new DataPoint(i, points[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxX(100);
        graph.getViewport().setMaxY(points[99]);
        graph.addSeries(series);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plot, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
