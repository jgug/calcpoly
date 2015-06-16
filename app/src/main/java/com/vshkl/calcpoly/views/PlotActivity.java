package com.vshkl.calcpoly.views;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.vshkl.calcpoly.R;
import com.vshkl.calcpoly.logic.Callback;
import com.vshkl.core.CPolynomialCalculator;

public class PlotActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Bundle bundle = this.getIntent().getExtras();
        double[] coefficients = bundle.getDoubleArray("coefficients");

        CalculatePoints calculatePoints = new CalculatePoints();
        calculatePoints.execute(coefficients);
    }

    /**
     * Method for calculating point for plot
     * @param cpoly CPolynomialCalculator object
     * @return double[]
     */
    public double[] calculatePoints(CPolynomialCalculator cpoly) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int max = Integer.parseInt(preferences.getString(
                getString(R.string.pref_max_key), getString(R.string.pref_max_default)));
        double step = Double.parseDouble(preferences.getString(
                getString(R.string.pref_step_key), getString(R.string.pref_step_default)));
        int size = (int)(max/step);
        Log.v("SIZE", String.valueOf(size));
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = cpoly.calculate(i*step);
        }
        return array;
    }



    private class CalculatePoints extends AsyncTask<double[], Integer, double[]> {

        @Override
        protected double[] doInBackground(double[]... params) {
            Callback callback = new Callback(params[0][0], params[0][1], params[0][1], params[0][1]);
            CPolynomialCalculator cpoly = new CPolynomialCalculator();
            cpoly.setCallback(callback);
            double[] points = calculatePoints(cpoly);

            return points;
        }

        @Override
        protected void onPostExecute(double[] points) {
            SharedPreferences preferences =
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
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

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPointInterface) {
                    Toast.makeText(getApplicationContext(),
                            "Point " + dataPointInterface, Toast.LENGTH_SHORT
                    ).show();
                }
            });

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMaxX(max);
            graph.getViewport().setMaxY(points[max - 1]);
            graph.getViewport().setScalable(true);
            graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
            graph.addSeries(series);
        }
    }
}
