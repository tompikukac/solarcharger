package com.szlab.solarcharger;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class settingsActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		// Get the custom preference
		/*Preference customPref = (Preference) findPreference("customPref");
		customPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {

			public boolean onPreferenceClick(Preference preference) {
				Toast.makeText(getBaseContext(), "The custom preference has been clicked", Toast.LENGTH_LONG).show();
				SharedPreferences customSharedPreference = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = customSharedPreference.edit();
				editor.putString("myCustomPref", "The preference has been clicked");
				editor.commit();
				return true;
			}

		});*/
	}

	boolean CheckboxPreference;
	String ListPreference;
	String editTextPreference;
	String ringtonePreference;
	String secondEditTextPreference;
	String customPref;

	private void getPrefs() {
		// Get the xml/preferences.xml preferences
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		CheckboxPreference = prefs.getBoolean("checkboxPref", true);
		ListPreference = prefs.getString("listPref", "nr1");
		//editTextPreference = prefs.getString("editTextPref", "Nothing has been entered");
		//ringtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
		//secondEditTextPreference = prefs.getString("SecondEditTextPref", "Nothing has been entered");
		// Get the custom preference
		//SharedPreferences mySharedPreferences = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
		//customPref = mySharedPreferences.getString("myCusomPref", "");
	}
}
