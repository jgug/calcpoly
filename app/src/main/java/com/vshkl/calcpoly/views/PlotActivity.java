package com.vshkl.calcpoly.views;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlotActivity extends AppCompatActivity {

    private final static int LINE_THICKNESS = 8;
    private final static String EXTRA_NAME = "coefficients";
    private final static String BUNDLE_ARRAY = "bundle_array";

    private double step;
    private int size;

    double[] points;

    @InjectView(R.id.graph) GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        ButterKnife.inject(this);

        Bundle bundle = this.getIntent().getExtras();
        double[] coefficients = bundle.getDoubleArray(EXTRA_NAME);

        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int max = Integer.parseInt(preferences.getString(
                getString(R.string.pref_max_key), getString(R.string.pref_max_default)));
        step = Double.parseDouble(preferences.getString(
                getString(R.string.pref_step_key), getString(R.string.pref_step_default)));
        size = (int)(max/step);

        if (savedInstanceState == null) {
            points = new double[size+1];
            CalculatePoints calculatePoints = new CalculatePoints();
            calculatePoints.execute(coefficients);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDoubleArray(BUNDLE_ARRAY, points);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        points = savedInstanceState.getDoubleArray(BUNDLE_ARRAY);
        plot(points, size, step);
    }

    /**
     * Method for calculating point for plot
     * @param cpoly CPolynomialCalculator object
     * @param size size of resulting array
     * @param step calculation step
     * @return double[]
     */
    public double[] calculatePoints(CPolynomialCalculator cpoly, int size, double step) {
        double[] array = new double[size+1];
        for (int i = 0; i <= size; i++) {
            array[i] = cpoly.calculate(i*step);
        }
        return array;
    }

    public void plot(double[] points, int size, final double step) {
        DataPoint[] dataPoints = new DataPoint[size+1];
        for (int i = 0; i <= size; i++) {
            dataPoints[i] = new DataPoint(i, points[i]);
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);

        series.setColor(getResources().getColor(R.color.accent_material_light));
        series.setThickness(LINE_THICKNESS);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPointInterface) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("x = ").append((int) (dataPointInterface.getX() * step))
                        .append("  P(x) = ").append((int) dataPointInterface.getY());

                Toast.makeText(getApplicationContext(),
                        stringBuilder.toString(), Toast.LENGTH_SHORT
                ).show();
            }
        });

        graph.getGridLabelRenderer()
                .setGridColor(getResources().getColor(R.color.primary_material_dark));
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxX(size);
        graph.getViewport().setMaxY(series.getHighestValueY());
        graph.getViewport().setScalable(true);
        graph.addSeries(series);
    }

    /**
     * Class extending AsyncTask for executing points calculatins not in UI thread,
     * An {@link AsyncTask} subclass
     */
    private class CalculatePoints extends AsyncTask<double[], Void, double[]> {
        @Override
        protected double[] doInBackground(double[]... params) {
            Callback callback =
                    new Callback(params[0][0], params[0][1], params[0][2], params[0][3]);
            CPolynomialCalculator cpoly = new CPolynomialCalculator();
            cpoly.setCallback(callback);
            points = calculatePoints(cpoly, size, step);
            return points;
        }

        @Override
        protected void onPostExecute(double[] points) {
            plot(points, size, step);
        }
    }
}
