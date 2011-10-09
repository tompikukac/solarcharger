package szab.solarcharger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BatteryLevel extends RelativeLayout {

	private static final String TAG = "SolarCharger";

	private Context mContext;
	private AttributeSet mAttrs;
	private ImageView barFull;
	private ImageView barEmpty;
	private TextView percentText;
	BroadcastReceiver batteryLevelReceiver;

	public BatteryLevel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mAttrs = attrs;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.batterylevel, this);
		barFull = (ImageView) view.findViewById(R.id.batteryBarFull);
		barEmpty = (ImageView) view.findViewById(R.id.batteryBarEmpty);
		percentText = (TextView)view.findViewById(R.id.batteryPercentage);
		setLevelinPercent(65);
	}

	private void setLevelinPercent(int percent) {
		float percentF = new Float(percent)/100;
		barFull.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1f - percentF));
		barEmpty.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, percentF));
		percentText.setText(percent + "%");
	}

	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			int level = arg1.getIntExtra("level", 0);
			//Toast.makeText(mContext, "VALAMI " + level, Toast.LENGTH_LONG).show();
			setLevelinPercent(level);
		}
	};

	public void registerBatteryReceiver() {
		mContext.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}

	public void unregisterBatteryReceiver() {
		mContext.unregisterReceiver(this.mBatInfoReceiver);
	}
}
