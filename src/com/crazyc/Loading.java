package com.crazyc;


import com.crazyc.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

public class Loading extends Activity {
	Bundle bundleData = new Bundle();
	boolean isEnlarge;
	ImageView progress;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loading);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		Animation anim = AnimationUtils.loadAnimation(this, R.layout.progressbar); 
		LinearInterpolator lir = new LinearInterpolator();  //動畫均速
	    anim.setInterpolator(lir);
		findViewById(R.id.progress).startAnimation(anim); 
		
		handler.sendMessageDelayed(new Message(), 3000);
	}
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Intent intent = new Intent();
			bundleData.putInt("r", 0);
			bundleData.putInt("user", 0);
			bundleData.putInt("com1", 0);
			bundleData.putInt("com2", 0);
			bundleData.putInt("com3", 0);
			intent.setClass(Loading.this, Game.class);
			intent.putExtras(bundleData);
			startActivity(intent);
			finish();
		}		
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_loading, menu);
		return true;
	}

}
