package com.inspector.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.inspector.R;

/**
 * Fragment para tela de Preferências
 * http://developer.android.com/guide/topics/ui/settings.html#Fragment
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //carregar preferencias de um xml
        addPreferencesFromResource(R.xml.preferences);
    }
}
