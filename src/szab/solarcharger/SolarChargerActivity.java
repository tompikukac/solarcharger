package szab.solarcharger;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.hardware.SensorEventListener;

public class SolarChargerActivity extends Activity {
	/** Called when the activity is first created. */

	SensorManager mSensorManager;
	Sensor myLightSensor;
	private TextView textLightSensorData;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		myLightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
		textLightSensorData = (TextView)findViewById(R.id.textView1);

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
			if(event.sensor.getType()==Sensor.TYPE_LIGHT){
			     textLightSensorData.setText("Light Sensor Date:"
			       + String.valueOf(event.values[0]));
			    }

		}
	};

}
