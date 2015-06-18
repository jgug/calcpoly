package com.vshkl.calcpoly.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.vshkl.calcpoly.R;
import com.vshkl.calcpoly.logic.Storage;
import com.vshkl.calcpoly.settings.SettigsActivity;

import butterknife.ButterKnife;
import butterknife.InjectViews;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final static int SIZE = 4;
    private final static String EXTRA_NAME = "coefficients";
    private final static String MESSAGE_EMPTY = "Please, fill in all fields";

    static {
        System.loadLibrary("CPoly");
    }

    @InjectViews({R.id.editTextC0, R.id.editTextC1, R.id.editTextC2, R.id.editTextC3})
    EditText[] editTexts;

    @OnClick(R.id.buttonShow)
    public void show() {
        if (isAllFilledIn()) {
            double[] coefficients = getCoefficients();

            Bundle bundle = new Bundle();
            bundle.putDoubleArray(EXTRA_NAME, coefficients);

            Intent intent = new Intent(getApplicationContext(), PlotActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, MESSAGE_EMPTY, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
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
     * Returns data from all EditTexts as double array
     * @return array of doubles
     */
    public double[] getCoefficients() {
        double[] coefficients = new double[SIZE];
        int i = 0;
        for (EditText editText : editTexts) {
            coefficients[i] = Double.parseDouble(editText.getText().toString());
            i++;
        }
        return coefficients;
    }

    /**
     * Fill with values from storage
     * @param array an array of values to fill
     */
    public void fillEditTexts(double[] array) {
        int i = 0;
        for (EditText editText : editTexts) {
            editText.setText(String.valueOf(array[i]));
            i++;
        }
    }

    /**
     * Check if every EditText filled in
     * @return boolean. True if all fields are filled in. False if not
     */
    public boolean isAllFilledIn() {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() == 0) {
                return false;
            }
        }
        return true;
    }
}
