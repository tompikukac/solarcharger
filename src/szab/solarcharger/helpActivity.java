package szab.solarcharger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class helpActivity  extends SolarChargerBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);

	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		
		//menu.removeItem();
		menu.findItem(R.id.menuHelp).setEnabled(false);
		return super.onPrepareOptionsMenu(menu);
	};
	
}
