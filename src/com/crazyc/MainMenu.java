package com.crazyc;


import com.crazyc.R;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainMenu extends Activity {
	static boolean enl;
	Animation Am1Hp = new TranslateAnimation( 0, 100, 0, 80 );//動畫移動距離
	Animation Am2Hp = new TranslateAnimation( 0, -100, 0, 80 );//動畫移動距離
	Animation Am3Hp = new TranslateAnimation( 0, 100, 0, -80 );//動畫移動距離
	Animation Am4Hp = new TranslateAnimation( 0, -100, 0, -80 );//動畫移動距離
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        //get the boolean value to decide enlarge or not
		mainMenu();		
	
		
	}
	int currentViewId;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{   
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{   
			if(currentViewId==R.layout.help )
			{
				mainMenu();
			}
			 
			else
			{
				
				finish();
			}
		}   
		return false;   
	}
//	@Override  
/*	protected void onPause()
	{		
		if(OptionControl.music!= null)  
		{  
			//保存當前播放位置
			//  position = mediaPlayer.getCurrentPosition();  
			OptionControl.music.stop();  
			OptionControl.music.release();
			super.onPause();       
		}  
		else
		{
			OptionControl.musicSt = true;
			super.onPause();
		}
		  
	}*/
	@Override
	protected void onDestroy()
	{
		if(OptionControl.music!=null)
		{
			OptionControl.music.release();
		}
		super.onDestroy();
	}
	

	public void mainMenu()
	{
		setContentView(R.layout.activity_main_menu);
		currentViewId = R.layout.activity_main_menu;
		ImageButton start = (ImageButton)findViewById(R.id.menuStart);
		ImageButton help = (ImageButton)findViewById(R.id.menuHelp);
		ImageButton option = (ImageButton)findViewById(R.id.option);
		final ImageView h1 = (ImageView)findViewById(R.id.hp_1);
		final ImageView h2 = (ImageView)findViewById(R.id.hp_2);
		final ImageView h3 = (ImageView)findViewById(R.id.hp_3);
		final ImageView h4 = (ImageView)findViewById(R.id.hp_4);
		new CountDownTimer(100000,2200){
			int timerrun=1;
			@Override
			public void onFinish() {
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if(timerrun%5==1){		
					h1.clearAnimation();
					Am1Hp.setDuration(2000);
					h2.clearAnimation();
					Am2Hp.setDuration(2000);
					h3.clearAnimation();
					Am3Hp.setDuration(2000);
					h4.clearAnimation();
					Am4Hp.setDuration(2000);
					//
					h1.startAnimation(Am1Hp); 
					h2.startAnimation(Am2Hp); 
					h3.startAnimation(Am3Hp);
					h4.startAnimation(Am4Hp);
				
				}
				if(timerrun%5==2){		
					h1.clearAnimation();
					Am1Hp.setDuration(1800);
					//
					h1.startAnimation(Am1Hp); 
				
				}
				if(timerrun%5==3){		
					h3.clearAnimation();
					Am3Hp.setDuration(1800);
					//
					h3.startAnimation(Am3Hp);
				
				}
				if(timerrun%5==4){		
					h2.clearAnimation();
					Am2Hp.setDuration(1800);
					//
					h2.startAnimation(Am2Hp); 
				
				}
				if(timerrun%5==0){		
					h4.clearAnimation();
					Am4Hp.setDuration(1800);
					//
					h4.startAnimation(Am4Hp);
				
				}
				timerrun++;
			}
		}.start(); 				

	/*	if(enl)
		{		
			int h = start.getLayoutParams().height;
			int w = start.getLayoutParams().width;
			start.setLayoutParams(new LinearLayout.LayoutParams(w*2,h*2 ));
			h = help.getLayoutParams().height;
			w = help.getLayoutParams().width;
			help.setLayoutParams(new LinearLayout.LayoutParams(w*2,h*2 ));
			h = option.getLayoutParams().height;
			w = option.getLayoutParams().width;
			option.setLayoutParams(new LinearLayout.LayoutParams(w*2,h*2 ));
		}*/
		final Bundle bundleData = new Bundle();
		
		start.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
	/*			bundleData.putInt("r", 0);
				bundleData.putInt("user", 0);
				bundleData.putInt("com1", 0);
				bundleData.putInt("com2", 0);
				bundleData.putInt("com3", 0);*/
				intent.setClass(MainMenu.this, Loading.class);
				intent.putExtras(bundleData);
			  	startActivity(intent);
			  	finish();
			}
		});
		help.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(MainMenu.this, HelpActivity.class);
				startActivity(intent);
				finish();
			}
		});
		option.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setClass(MainMenu.this, OptionControl.class);
				startActivity(intent);
				finish();
			}
		});		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		return true;
	}

}
