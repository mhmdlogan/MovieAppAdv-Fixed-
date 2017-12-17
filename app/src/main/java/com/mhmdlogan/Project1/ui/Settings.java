package com.mhmdlogan.Project1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mhmdlogan.Project1.R;

/**
 * Created by sahil on 28/04/16.
 * Settings panel activity for the app
 */
public class Settings extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.setting_app_bar);
        setSupportActionBar(toolbar);
        getFragmentManager().beginTransaction().replace(R.id.setting_conatainer, new MoviePreference()).commit();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Settings.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public static class MoviePreference extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_key)));
        }

        private void bindPreferenceSummaryToValue(Preference pref) {
            pref.setOnPreferenceChangeListener(this);

            onPreferenceChange(pref,
                    PreferenceManager.getDefaultSharedPreferences(pref.getContext())
                            .getString(pref.getKey(), ""));
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String valueOf = newValue.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(valueOf);
                if (prefIndex >= 0) {
                    preference.setSummary(listPreference.getEntries()[prefIndex]);
                }

            }

            return true;
        }
    }
}
