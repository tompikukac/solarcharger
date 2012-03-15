package com.szlab.solarcharger;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class HallOfShameActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hall_of_fame);
		setListAdapter(new ArrayAdapter(this,
				R.layout.hall_list_item, getResources().getStringArray(R.array.HallOfShameList)));
	}
}