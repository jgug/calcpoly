package com.vshkl.calcpoly.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.vshkl.calcpoly.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref);
        System.out.println("Settings created!");
    }
}
