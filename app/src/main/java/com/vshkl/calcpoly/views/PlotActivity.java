package com.vshkl.calcpoly.views;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
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

public class PlotActivity extends AppCompatActivity {

    private final static int LINE_THICKNESS = 8;

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


    /**
     * Class extending AsyncTask for executing points calculatins not in UI thread,
     * An {@link AsyncTask} subclass
     */
    private class CalculatePoints extends AsyncTask<double[], Integer, double[]> {
        private final SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(PlotActivity.this);
        private final int max = Integer.parseInt(preferences.getString(
                getString(R.string.pref_max_key), getString(R.string.pref_max_default)));
        private final double step = Double.parseDouble(preferences.getString(
                getString(R.string.pref_step_key), getString(R.string.pref_step_default)));
        private final int size = (int)(max/step);

        @Override
        protected double[] doInBackground(double[]... params) {
            Callback callback = new Callback(params[0][0], params[0][1], params[0][1], params[0][1]);
            CPolynomialCalculator cpoly = new CPolynomialCalculator();
            cpoly.setCallback(callback);

            return calculatePoints(cpoly, size, step);
        }

        @Override
        protected void onPostExecute(double[] points) {
            GraphView graph = (GraphView) findViewById(R.id.graph);
            DataPoint[] dataPoints = new DataPoint[size+1];
            for (int i = 0; i <= size; i++) {
                dataPoints[i] = new DataPoint(i, points[i]);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
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

            series.setColor(getResources().getColor(R.color.accent_material_light));
            series.setThickness(LINE_THICKNESS);

            graph.addSeries(series);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setMaxX(size);
            graph.getViewport().setMaxY(points[size]);
            graph.getViewport().setScalable(true);
            graph.getGridLabelRenderer()
                    .setGridColor(getResources().getColor(R.color.primary_material_dark));
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
            graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
        }
    }
}
