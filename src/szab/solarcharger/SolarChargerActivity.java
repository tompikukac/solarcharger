package szab.solarcharger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.SensorEventListener;

public class SolarChargerActivity extends SolarChargerBaseActivity {
	private static final String TAG = "SolarCharger";
	/** Called when the activity is first created. */

	SensorManager mSensorManager;
	Sensor myLightSensor;
	private TextView textLightSensorData;
	VUMeter vuMeter;
	BatteryLevel batteryLevel;
	SharedPreferences preferences;
	TextView stateText;
	private int[] BG_ID = { R.id.view_bg_1, R.id.view_bg_2, R.id.view_bg_3 };
	PowerManager.WakeLock wl;
	private final int LIGHT_SENSOR_TRESHOLD = 400; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		myLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		//textLightSensorData = (TextView) findViewById(R.id.textView1);
		vuMeter = (VUMeter) findViewById(R.id.VUComponent);
		batteryLevel = (BatteryLevel) findViewById(R.id.BatteryComponent);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		stateText= (TextView) findViewById(R.id.FeedbackTextDark);
		Toast.makeText(getBaseContext(), R.string.hint_tap_vu_hide, Toast.LENGTH_LONG).show();
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(lightSensorEventListener, myLightSensor, SensorManager.SENSOR_DELAY_NORMAL);

		String aaa = preferences.getString("listPref", "");
		int myNum = 0;

		try {
			myNum = Integer.parseInt(aaa);
		} catch (NumberFormatException nfe) {
			System.out.println("Could not parse " + nfe);
		}
		switchBG(myNum);

		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Solar charger wake lock");
		if (preferences.getBoolean("checkboxKeepAwake", true)) {

			wl.acquire();
		}

		batteryLevel.registerBatteryReceiver();
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(lightSensorEventListener);
		if (wl.isHeld()) {
			wl.release();
		}
		batteryLevel.unregisterBatteryReceiver();
	}

	private void switchBG(int idx) {
		// int resId = getResources().getIdentifier(aaa, "id",
		// getPackageName());
		for (int i = 0; i < BG_ID.length; i++) {
			ImageView bg = (ImageView) findViewById(BG_ID[i]);
			if (idx == i) {
				bg.setVisibility(View.VISIBLE);
			} else {
				bg.setVisibility(View.GONE);
			}

		}
		// Toast.makeText(getBaseContext(), "VALAMI " + idx,
		// Toast.LENGTH_LONG).show();

	}

	SensorEventListener lightSensorEventListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
				//textLightSensorData.setText("Light Sensor Date:" + String.valueOf(event.values[0]));
				vuMeter.setValue(event.values[0], 1000);
				setChargingState(event.values[0] > LIGHT_SENSOR_TRESHOLD);
			}
		}
	};
	
	private void setChargingState(boolean isCharging)
	{
		if(isCharging) {
			stateText.setText(R.string.str_FeedbackTextCharging);
		} else {
			stateText.setText(R.string.str_FeedbackTextDark);
		}
		batteryLevel.setChargingState(isCharging);
	}
}
