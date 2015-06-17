package com.vshkl.calcpoly.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vshkl.calcpoly.R;
import com.vshkl.calcpoly.logic.Storage;
import com.vshkl.calcpoly.settings.SettigsActivity;
import com.vshkl.core.CPolynomialCalculator;

public class MainActivity extends AppCompatActivity {

    int SIZE = 4;

    EditText editTextC0;
    EditText editTextC1;
    EditText editTextC2;
    EditText editTextC3;
    Button buttonShow;

    static {
        System.loadLibrary("CPoly");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextC0 = (EditText)findViewById(R.id.editTextC0);
        editTextC1 = (EditText)findViewById(R.id.editTextC1);
        editTextC2 = (EditText)findViewById(R.id.editTextC2);
        editTextC3 = (EditText)findViewById(R.id.editTextC3);
        buttonShow = (Button)findViewById(R.id.buttonShow);

        final CPolynomialCalculator cpoly = new CPolynomialCalculator();

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double[] coefficients = getCoefficients();

//                Callback callback = new Callback(
//                        coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
//                cpoly.setCallback(callback);
//                double results[] = calculatePoints(cpoly);

                Bundle bundle = new Bundle();
//                bundle.putDoubleArray("points", results);
                bundle.putDoubleArray("coefficients", coefficients);

                Intent intent = new Intent(getApplicationContext(), PlotActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettigsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Storage.hasFile(this)) {
            fillEditTexts(Storage.restoreData(this));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Storage.storeData(this, getCoefficients());
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

    /**
     * Returns data from all EditTexts as double array
     * @return array of doubles
     */
    public double[] getCoefficients() {
        double[] coefficients = new double[SIZE];
        coefficients[0] = Double.parseDouble(editTextC0.getText().toString());
        coefficients[1] = Double.parseDouble(editTextC1.getText().toString());
        coefficients[2] = Double.parseDouble(editTextC2.getText().toString());
        coefficients[3] = Double.parseDouble(editTextC3.getText().toString());
        return coefficients;
    }

    /**
     * Fill edit texts fields with values from storage if file exists
     * @param array an array of values to fill
     */
    public void fillEditTexts(double[] array) {
        editTextC0.setText(String.valueOf(array[0]));
        editTextC1.setText(String.valueOf(array[1]));
        editTextC2.setText(String.valueOf(array[2]));
        editTextC3.setText(String.valueOf(array[3]));
    }
}
