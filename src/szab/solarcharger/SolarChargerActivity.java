package szab.solarcharger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.hardware.SensorEventListener;

public class SolarChargerActivity extends Activity {
	private static final String TAG = "SolarCharger";
	/** Called when the activity is first created. */

	SensorManager mSensorManager;
	Sensor myLightSensor;
	private TextView textLightSensorData;
	VUMeter vuMeter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		myLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		textLightSensorData = (TextView) findViewById(R.id.textView1);
		vuMeter = (VUMeter) findViewById(R.id.VUComponent);
	}

	protected void onResume() {
		super.onResume();
		mSensorManager.registerListener(lightSensorEventListener,
				myLightSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	protected void onPause() {
		super.onPause();
		mSensorManager.unregisterListener(lightSensorEventListener);
	}

	SensorEventListener lightSensorEventListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
				textLightSensorData.setText("Light Sensor Date:"
						+ String.valueOf(event.values[0]));
			}
			vuMeter.setValue(event.values[0], 1000);
		}
	};

}
