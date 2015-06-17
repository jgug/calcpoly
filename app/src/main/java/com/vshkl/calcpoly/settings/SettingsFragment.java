package com.vshkl.calcpoly.settings;


import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceFragment;

import com.vshkl.calcpoly.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.pref);
    }
}
