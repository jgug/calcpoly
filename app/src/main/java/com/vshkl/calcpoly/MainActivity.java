package com.vshkl.calcpoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vshkl.calcpoly.logic.Callback;
import com.vshkl.calcpoly.logic.Storage;
import com.vshkl.core.CPolynomialCalculator;

public class MainActivity extends AppCompatActivity {

    int MAXVALUE = 100;
    int STEP = 1;
    int SIZE = 4;

    double[] coefficients;

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

        if (Storage.hasFile(getApplicationContext())) {
            fillEditTexts(Storage.restoreData(getApplicationContext()));
        }

        final CPolynomialCalculator cpoly = new CPolynomialCalculator();

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coefficients = getCoefficients();

                Callback callback = new Callback(
                        coefficients[0], coefficients[1], coefficients[2], coefficients[3]);
                cpoly.setCallback(callback);
                double results[] = calculatePoints(MAXVALUE, STEP, cpoly);

                Bundle bundle = new Bundle();
                bundle.putDoubleArray("points", results);
                bundle.putInt("size", MAXVALUE);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Storage.storeData(getApplicationContext(), coefficients);
    }

    /**
     * Method for calculating point for plot
     * @param maxValue max value to calculate
     * @param step calculation step
     * @param cpoly CPolynomialCalculator object
     * @return double[]
     */
    public double[] calculatePoints(int maxValue, int step, CPolynomialCalculator cpoly) {
        double[] array = new double[maxValue];
        for (int i = 0; i < maxValue; i += step) {
            array[i] = cpoly.calculate(i);
        }
        return array;
    }

    /**
     * Returns data from all EditTexts as double array
     * @return array of doubles
     */
    public double[] getCoefficients() {
        coefficients = new double[SIZE];
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
