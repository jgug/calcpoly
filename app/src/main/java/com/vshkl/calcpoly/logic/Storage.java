package com.vshkl.calcpoly.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Storage {

    static final String FILENAME = "settings";
    static final String PREFERENCE_NAME = "Settings";
    static final String[] COEFFICIENTS_NAMES = {"C0", "C1", "C2", "C3"};
    static final int SIZE = 4;
    static final String TAG_WRITE_ERR = "Error on write";

    /**
     * Stores data as a string to file
     * @param context application context
     * @param array an array to store
     */
    public static void storeData(Context context, double[] array) {
        FileOutputStream fos;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            StringBuilder stringBuilder = new StringBuilder();
            for (double val : array) {
                stringBuilder.append(val).append(",");
            }
            fos.write(stringBuilder.toString().getBytes());
            Log.v("STORAGE", stringBuilder.toString());
            fos.close();
        } catch (IOException e) {
            Log.e(TAG_WRITE_ERR, e.toString());
        }
    }

    /**
     * Restore data from file
     * @param context application context
     * @return array of doubles
     */
    public static double[] restoreData(Context context) {
        FileInputStream fis;
        String str = null;

        try {
            fis = context.openFileInput(FILENAME);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[] array = new double[SIZE];
        String[] strArray = str.split(",");
        Log.v("RESORE", strArray[0] + " " + strArray[1] + " " + strArray[2] + " " + strArray[3]);
        for (int i = 0; i < SIZE; i++) {
            array[i] = Double.parseDouble(strArray[i]);
        }

        return array;
    }

    /**
     * Check if stored file exists.
     * @param context application context
     * @return boolean. Return true if file exists and false if not.
     */
    public static boolean hasFile(Context context) {
        FileInputStream fis;

        try {
            if ((fis = context.openFileInput(FILENAME)) != null) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
