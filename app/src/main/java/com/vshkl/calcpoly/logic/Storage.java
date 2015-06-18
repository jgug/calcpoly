package com.vshkl.calcpoly.logic;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Storage {

    static final String FILENAME = "settings";
    static final int SIZE = 4;

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
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        String[] strArray = str != null ? str.split(",") : new String[]{"0", "0", "0", "0"};
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
        try {
            if (context.openFileInput(FILENAME) != null) {
                return true;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

}
