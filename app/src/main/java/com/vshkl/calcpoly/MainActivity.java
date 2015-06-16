package com.vshkl.calcpoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vshkl.calcpoly.logic.Callback;
import com.vshkl.core.CPolynomialCalculator;

public class MainActivity extends AppCompatActivity {

    int MAXVALUE = 100;
    int STEP = 1;

    static {
        System.loadLibrary("CPoly");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editTextC0 = (EditText)findViewById(R.id.te_c0);
        final EditText editTextC1 = (EditText)findViewById(R.id.te_c1);
        final EditText editTextC2 = (EditText)findViewById(R.id.te_c2);
        final EditText editTextC3 = (EditText)findViewById(R.id.te_c3);
        final Button buttonShow = (Button)findViewById(R.id.buttonShow);

        final CPolynomialCalculator cpoly = new CPolynomialCalculator();

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Callback callback = new Callback(
                        Double.parseDouble(editTextC0.getText().toString()),
                        Double.parseDouble(editTextC1.getText().toString()),
                        Double.parseDouble(editTextC2.getText().toString()),
                        Double.parseDouble(editTextC3.getText().toString())
                );
                cpoly.setCallback(callback);
                double results[] = calculatePoints(MAXVALUE, STEP, cpoly);

                for (double i : results) {
                    Log.v("Point: ",String.valueOf(i));
                }

                Bundle bundle = new Bundle();
                bundle.putDoubleArray("points", results);

                Intent intent = new Intent(getApplicationContext(), PlotActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Method for calculating point for plot
     * @param maxValue max value to calculate
     * @param step calculation step
     * @param cp CPolynomialCalculator object
     * @return double[]
     */
    public double[] calculatePoints(int maxValue, int step, CPolynomialCalculator cp) {
        double[] array = new double[maxValue];
        for (int i = 0; i < maxValue; i += step) {
            array[i] = cp.calculate(i);
        }
        return array;
    }
}
