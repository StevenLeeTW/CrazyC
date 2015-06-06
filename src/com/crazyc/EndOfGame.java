package com.crazyc;


import com.crazyc.R;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class EndOfGame extends Activity {
	
	Bundle bundleData = new Bundle();
	int userS, com1S, com2S, com3S, r, lastClickCount;
	Intent intent;
	TextView no1, no2, no3, no4;
	
	AnimationDrawable scoreAnim[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_end_of_game);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		numImg();
		TextView [] no1digit = {(TextView)findViewById(R.id.no11),(TextView)findViewById(R.id.no12),
				(TextView)findViewById(R.id.no13),(TextView)findViewById(R.id.no14)};
		TextView [] no2digit = {(TextView)findViewById(R.id.no21),(TextView)findViewById(R.id.no22),
				(TextView)findViewById(R.id.no23),(TextView)findViewById(R.id.no24)};
		TextView [] no3digit = {(TextView)findViewById(R.id.no31),(TextView)findViewById(R.id.no32),
				(TextView)findViewById(R.id.no33),(TextView)findViewById(R.id.no34)};
		TextView [] no4digit = {(TextView)findViewById(R.id.no41),(TextView)findViewById(R.id.no42),
				(TextView)findViewById(R.id.no43),(TextView)findViewById(R.id.no44)};

		intent = new Intent();
		no1 = (TextView)findViewById(R.id.no1);
		no2 = (TextView)findViewById(R.id.no2);
		no3 = (TextView)findViewById(R.id.no3);
		no4 = (TextView)findViewById(R.id.no4);

		scoreAnim = new AnimationDrawable [16];
		
		for(int i = 0; i < no1digit.length; i++)
		{
			scoreAnim[i] = (AnimationDrawable)no1digit[i].getBackground();
			scoreAnim[i+4] = (AnimationDrawable)no2digit[i].getBackground();
			scoreAnim[i+8] = (AnimationDrawable)no3digit[i].getBackground();
			scoreAnim[i+12] = (AnimationDrawable)no4digit[i].getBackground();
			
			if(scoreAnim[i].isRunning())
			{
				scoreAnim[i].stop();
				scoreAnim[i+4].stop();
				scoreAnim[i+8].stop();
				scoreAnim[i+12].stop();
			}
			else
			{
				scoreAnim[i].start();
				scoreAnim[i+4].start();
				scoreAnim[i+8].start();
				scoreAnim[i+12].start();
			}
		}
		
		
	/*	TextView isCont = (TextView)findViewById(R.id.askcontinue);
		Button y = (Button)findViewById(R.id.yesbtn);
		Button n = (Button)findViewById(R.id.nobtn);		*/
		bundleData = getIntent().getExtras();
		userS = bundleData.getInt("user");
		com1S = bundleData.getInt("com1");
		com2S = bundleData.getInt("com2");
		com3S = bundleData.getInt("com3");
		r = bundleData.getInt("r");
		if(r == 4)
		{
			title = "遊戲結束, 是否重新遊戲?";
			bundleData.putInt("user", 0);
			bundleData.putInt("com1", 0);
			bundleData.putInt("com2", 0);
			bundleData.putInt("com3", 0);
			bundleData.putInt("r", 0);
		}
		else if(r == 3)
		{
			title = "前往最終決戰!?";
			bundleData.putInt("user", userS);
			bundleData.putInt("com1", com1S);
			bundleData.putInt("com2", com2S);
			bundleData.putInt("com3", com3S);
			bundleData.putInt("r", r);
		}
		else
		{
			title = "是否前往下一回?";
			bundleData.putInt("user", userS);
			bundleData.putInt("com1", com1S);
			bundleData.putInt("com2", com2S);
			bundleData.putInt("com3", com3S);
			bundleData.putInt("r", r);
		}
		
		handler1.sendMessageDelayed(new Message(),3000);
		handler2.sendMessageDelayed(new Message(),5500);
	}
	
	
	String title;
	private Handler handler2 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			showDialog();
		}		
	};
	public void showDialog()
	{
		DialogInterface.OnClickListener yes, no;
		Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title);
		yes = new DialogInterface.OnClickListener() 
		{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				intent.setClass(EndOfGame.this, Game.class);
				intent.putExtras(bundleData);
				startActivity(intent);
				lastClickCount++;
				finish();
			}
			
		};
		no = new DialogInterface.OnClickListener() 
		{

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				intent.setClass(EndOfGame.this, MainMenu.class);
				startActivity(intent);
				finish();
			}
			
		};
		
		dialog.setNegativeButton("繼續", yes);
		dialog.setPositiveButton("退出", no);
		dialog.setCancelable(false);
		dialog.show();
	}
	public void showScoreAndRank()
	{
		int arr1[] = {userS, com1S, com2S, com3S};
		String userName = Game.name;
		int nameLength = Game.name.length();
		for(int i = 0; i < 8-nameLength; i++)
		{
			userName += " ";
						
		}
		
		String arr2[] = {userName, "com1    ", "com2    ", "com3    "};
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(arr1[j] < arr1[j+1])
				{
					int temp1 = arr1[j];
					arr1[j] = arr1[j+1];
					arr1[j+1] = temp1;
					String temp2 = arr2[j];
					arr2[j] = arr2[j+1];
					arr2[j+1] = temp2;
				}
			}
		}	
		
		no1.setText(arr2[0]);
		no2.setText(arr2[1]);
		no3.setText(arr2[2]);
		no4.setText(arr2[3]);
		TextView [] no1digit = {(TextView)findViewById(R.id.no11),(TextView)findViewById(R.id.no12),
				(TextView)findViewById(R.id.no13),(TextView)findViewById(R.id.no14)};
		TextView [] no2digit = {(TextView)findViewById(R.id.no21),(TextView)findViewById(R.id.no22),
				(TextView)findViewById(R.id.no23),(TextView)findViewById(R.id.no24)};
		TextView [] no3digit = {(TextView)findViewById(R.id.no31),(TextView)findViewById(R.id.no32),
				(TextView)findViewById(R.id.no33),(TextView)findViewById(R.id.no34)};
		TextView [] no4digit = {(TextView)findViewById(R.id.no41),(TextView)findViewById(R.id.no42),
				(TextView)findViewById(R.id.no43),(TextView)findViewById(R.id.no44)};

		for(int i = 0; i < 4;i++)
		{
			int [] arr = new int [4];
			arr[0] = arr1[i]/1000;
			arr[1] = (arr1[i]/100)%10;
			arr[2] = (arr1[i]/10)%10;
			arr[3] = arr1[i]%10;
			
			for(int j = 0; j < 4; j++)
			{
				switch(i){
				case 0:
					no1digit[j].setBackgroundResource(numArr[arr[j]]);
					break;
				case 1:
					no2digit[j].setBackgroundResource(numArr[arr[j]]);
					break;
				case 2:
					no3digit[j].setBackgroundResource(numArr[arr[j]]);
					break;
				case 3:
					no4digit[j].setBackgroundResource(numArr[arr[j]]);
					break;
				}
			}
		}
	}
	 

	int [] numArr = new int[10];
	public void numImg()
	{
		numArr[0] = R.drawable.num0;
		numArr[1] = R.drawable.num1;
		numArr[2] = R.drawable.num2;
		numArr[3] = R.drawable.num3;
		numArr[4] = R.drawable.num4;
		numArr[5] = R.drawable.num5;
		numArr[6] = R.drawable.num6;
		numArr[7] = R.drawable.num7;
		numArr[8] = R.drawable.num8;
		numArr[9] = R.drawable.num9;
	}

	private Handler handler1 = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			for(int i = 0; i< scoreAnim.length; i++)
			{
				scoreAnim[i].stop();
			}
			showScoreAndRank();			
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_end_of_game, menu);
		return true;
	}

}
