package szab.solarcharger;

import java.util.prefs.Preferences;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SolarChargerBaseActivity extends Activity {

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.optionsmenu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menuHelp:
			Intent myIntent = new Intent(this, helpActivity.class);
			this.startActivity(myIntent);
			return true;
		case R.id.menuSettings:
			Intent settingsActivity = new Intent(getBaseContext(), settingsActivity.class);
			startActivity(settingsActivity);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
