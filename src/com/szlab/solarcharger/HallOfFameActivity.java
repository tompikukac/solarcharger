package com.szlab.solarcharger;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class HallOfFameActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hall_of_fame);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.hall_list_item, getResources().getStringArray(R.array.HallOfFameList));
		setListAdapter(adapter);
	}
}