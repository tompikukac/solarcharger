package szab.solarcharger;

import android.content.Context;
import android.graphics.Matrix;
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
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class VUMeter extends LinearLayout implements OnClickListener,
		AnimationListener {

	private Context mContext;
	private AttributeSet mAttrs;
	private ImageView pointer;
	private float prevAngle = -40f;
	private float nextValue = 0f;

	public VUMeter(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mAttrs = attrs;
		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.vumeter, this);
		pointer = (ImageView) view.findViewById(R.id.pointer);

		animateTo();
	}

	public void setValue(float values, int maxValue) {
		nextValue = values / maxValue;
		// animateToPercent(percent);
	}

	private void animateTo() {
		// percent = 0.80f;
		float percent = nextValue > 1f ? 1f : nextValue;
		float fromA = prevAngle;
		float rndDelta = (float)(0.5-Math.random())/2;
		percent = (percent*(1+rndDelta));
		percent = percent<0? 0 : percent;
		percent = percent > 1f ? 1f : percent;
		float toA = (-40f + (80f * percent));
		
		
		Log.d("tompi", "animateToPercent:" + percent + "|" + fromA + "|" + toA +"| "+ rndDelta);
		RotateAnimation anim = new RotateAnimation(fromA, toA,
				Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(0);
		anim.setDuration(200);
		anim.setFillAfter(true);
		anim.setFillBefore(true);
		anim.setAnimationListener(this);
		pointer.startAnimation(anim);
		prevAngle = toA;

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		animateTo();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

}
