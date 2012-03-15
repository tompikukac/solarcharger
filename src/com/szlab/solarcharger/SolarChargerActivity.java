package com.szlab.solarcharger;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SolarChargerActivity extends SolarChargerBaseActivity {
	private static final String TAG = "SolarCharger";
	/** Called when the activity is first created. */

	SensorManager mSensorManager;
	Sensor myLightSensor;
	VUMeter vuMeter;
	BatteryLevel batteryLevel;
	SharedPreferences preferences;
	RelativeLayout centerMsg;
	private int[] BG_ID = { R.id.view_bg_1, R.id.view_bg_2, R.id.view_bg_3 };
	PowerManager.WakeLock wl;
	private final int LIGHT_SENSOR_TRESHOLD = 400;
	private RefreshHandler mRefreshHandler = new RefreshHandler();
	boolean isInChargingState = false;
	boolean canCharge = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		myLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		// textLightSensorData = (TextView) findViewById(R.id.textView1);
		vuMeter = (VUMeter) findViewById(R.id.VUComponent);
		batteryLevel = (BatteryLevel) findViewById(R.id.BatteryComponent);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		centerMsg = (RelativeLayout) findViewById(R.id.centerMessage);
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

		mRefreshHandler.start();
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(lightSensorEventListener);
		if (wl.isHeld()) {
			wl.release();
		}
		batteryLevel.unregisterBatteryReceiver();
		mRefreshHandler.quit();
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
				// textLightSensorData.setText("Light Sensor Date:" +
				// String.valueOf(event.values[0]));
				//Log.d(TAG, String.format("sensor: %s", event.values[0]));
				vuMeter.setValue(event.values[0], 1000);
				canCharge = event.values[0] > LIGHT_SENSOR_TRESHOLD;
			}
		}
	};

	class RefreshHandler extends Handler {
		private boolean isQuit = false;

		@Override
		public void handleMessage(Message msg) {
			if (!isQuit) {
				SolarChargerActivity.this.updateUI();
				this.removeMessages(0);
				sendMessageDelayed(obtainMessage(0), 3000);
			}
		}

		public void start() {
			isQuit = false;
			sendEmptyMessage(0);
		}
		
		public void quit() {
			isQuit = true;
		}
	};

	private void updateUI() {
		//Log.d(TAG, String.format("updateUI: %s, %s", canCharge ,isInChargingState));
		if (canCharge && !isInChargingState) {
			setChargingState(true);
		} else if (!canCharge && isInChargingState) {
			setChargingState(false);
		}
	}

	private void setChargingState(boolean isCharging) {
		isInChargingState = isCharging;
		// if (lastStateChange +3000< System.currentTimeMillis()) {
		if (isCharging) {
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			v.vibrate(120);
			Toast.makeText(getBaseContext(), R.string.str_FeedbackTextCharging, Toast.LENGTH_LONG).show();
		} else {

		}
		centerMsg.setVisibility(isCharging ? View.GONE : View.VISIBLE);
		batteryLevel.setChargingState(isCharging);
		// }
	}
}
