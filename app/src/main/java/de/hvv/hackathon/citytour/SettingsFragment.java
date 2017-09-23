package de.hvv.hackathon.citytour;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!preferences.contains("edited")) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("edited", true);
            editor.apply();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout v = (LinearLayout) super.onCreateView(inflater, container, savedInstanceState);

        Button btn = new Button(getActivity().getApplicationContext());
        btn.setText("Next");

        v.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MainActivityBottomNavigation.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
