package com.crazyc;

import java.util.HashMap;
import java.util.Map;

import com.crazyc.R;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageButton;

public class OptionControl extends Activity {
    public static MediaPlayer music;
    public static SoundPool soundPool;     
    public static boolean musicSt = true; //音乐开关
    public static boolean soundSt = true ; //音效开关
    public static Context context;
    public static final int[] musicId = {R.raw.mafia};
    public static Map<Integer,Integer> soundMap; //音效资源id与加载过后的音源id的映射关系表
	//Bundle bundleData = new Bundle();
    ImageButton turnOn, turnOff,turnEasy,turnHard;
	CheckBox cb ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_option_control);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);	

		turnOn = (ImageButton)findViewById(R.id.bturnon);
		turnOff = (ImageButton)findViewById(R.id.bturnoff);
		turnEasy = (ImageButton)findViewById(R.id.easymode);
		turnHard = (ImageButton)findViewById(R.id.hardmode);
		turnOn.setOnClickListener(new ButtonListener());
		turnOff.setOnClickListener(new ButtonListener());
		turnEasy.setOnClickListener(new ButtonListener());
		turnHard.setOnClickListener(new ButtonListener());
	}
	

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{   
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{   
			Intent intent = new Intent(); 
			intent.setClass(OptionControl.this, MainMenu.class);
			startActivity(intent);
		}   
		return super.onKeyDown(keyCode, event);   
	}
	
	class ButtonListener implements OnClickListener
	{

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if(arg0.getId()== R.id.bturnon)		
			{
				turnOn.setBackgroundResource(R.drawable.on_clicked);
				turnOff.setBackgroundResource(R.drawable.off);
				init(OptionControl.this);
				startMusic();
				arg0.setClickable(false);
				turnOff.setClickable(true);
			}
			else if(arg0.getId()== R.id.bturnoff)
			{
				turnOff.setBackgroundResource(R.drawable.off_clicked);
				turnOn.setBackgroundResource(R.drawable.on);
				pauseMusic();
				arg0.setClickable(false);
				music.release();
				turnOn.setClickable(true);
			}
			else if(arg0.getId()== R.id.easymode)
			{
				turnEasy.setBackgroundResource(R.drawable.easy_clicked);
				turnHard.setBackgroundResource(R.drawable.hard);

			}
			else if(arg0.getId()== R.id.hardmode)
			{
				turnHard.setBackgroundResource(R.drawable.hard_clicked);
				turnEasy.setBackgroundResource(R.drawable.easy);

			}
			
		}		
	}
	
    public static void init(Context c)
    {
        context = c;
 
        initMusic();
         
        initSound();
    }
     
    //初始化音效播放器
    
    public static void initSound()
    {
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,100);
         
        soundMap = new HashMap<Integer,Integer>();
        soundMap.put(R.raw.playcardsound, soundPool.load(context, R.raw.playcardsound, 1));
    }
     
    //初始化音乐播放器
    public static void initMusic()
    {
    	//修改背景音 因為我們只有一首
    	//int r = new Random().nextInt(musicId.length);
        music = MediaPlayer.create(context,musicId[0]);
        music.setLooping(true);
    }
     
    /**
     * 播放音效
     * @param resId 音效资源id
     */
    public static void playSound(int resId)
    {
        if(soundSt == false)
            return;
         
        Integer soundId = soundMap.get(resId);
        if(soundId != null)
            soundPool.play(soundId, 1, 1, 1, 0, 1);
    }
 
    /**
     * 暂停音乐
     */
    public static void pauseMusic()
    {
        if(music.isPlaying())
            music.pause();
    }
     
    /**
     * 播放音乐
     */
    public static void startMusic()
    {
        if(musicSt)
            music.start();
    }
     
    /**
     * 切换一首音乐并播放
     */
  /*  public static void changeAndPlayMusic()
    {
        if(music != null)
            music.release();
        initMusic();
        startMusic();
    }*/
     
    /**
     * 获得音乐开关状态
     * @return
     */
    public static boolean isMusicSt() {
        return musicSt;
    }
     
    /**
     * 设置音乐开关
     * @param musicSt
     */
    public static void setMusicSt(boolean musicSt) {
        OptionControl.musicSt = musicSt;
        if(musicSt)
            music.start();
        else
            music.stop();
    }
 
    /**
     * 获得音效开关状态
     * @return
     */
    public static boolean isSoundSt() {
        return soundSt;
    }
 
    /**
     * 设置音效开关
     * @param soundSt
     */
    public static void setSoundSt(boolean soundSt) {
        OptionControl.soundSt = soundSt;
    }
     
    /**
     * 发出‘邦’的声音
     */
    public static void boom()
    {
        playSound(R.raw.playcardsound);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.option_control, menu);
		return true;
	}

}
