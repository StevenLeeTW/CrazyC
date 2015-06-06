package com.crazyc;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HelpActivity extends Activity {

	ArrayAdapter<String> adapter;
	Intent intent;
	ListView list;
	Bitmap [] cards;
	ImageView showCard ;
	String [] effectDesArr;
	TextView description;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		intent = new Intent();
        mainHelp();
	}
	
	public void mainHelp()
	{
		setContentView(R.layout.help);
		currentViewId = R.layout.help;
		ImageView rulebtn = (ImageView)findViewById(R.id.temprule);
		ImageView cardEffectbtn = (ImageView)findViewById(R.id.tempcardeffect);	
		rulebtn.setOnClickListener(new btnListener());
		cardEffectbtn.setOnClickListener(new btnListener());
	}
	
	class btnListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId() == R.id.temprule)
			{
				currentViewId = R.layout.rule_des;
				setContentView(R.layout.rule_des);
			}
			else if(v.getId() == R.id.tempcardeffect)
			{
				currentViewId = R.layout.activity_help;
				setContentView(R.layout.activity_help);
				showCardEffect();

			}
		}
		
	}
	
	int currentViewId;
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{   
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{   
			if(currentViewId==R.layout.rule_des 
					|| currentViewId == R.layout.activity_help)
			{  
				mainHelp();
			}
			else
			{
				intent.setClass(HelpActivity.this, MainMenu.class);
				startActivity(intent);
				finish();
			}
		}   
		return false;   
	}
	
	public void showCardEffect()
	{
		//回轉
		String item[] = {"Knife", "Sword", "Defense", "Reflect", "Landmine", "Big Bang" ,
				"Reverse", "4 Leaf Clover", "Exchange"};
		showCard = (ImageView)findViewById(R.id.cardimgdes);
		description = (TextView)findViewById(R.id.carddescription);
		effectDesArr = new String [9];
		effectDesArr[0] = "17世紀歐洲盜賊常用的短刀，盜賊伊薩雷茲殺害其夥伴後，在一次搶劫事件被王國護衛兵擊斃，這把短刀遺落在東城門市集，從此流入民間，相傳使用這把短刀，會遭到親人與朋友的背叛。效果：造成下一家50點傷害";
		effectDesArr[1] = "古代的偉大劍士德瑞爾配戴的巨型劍，德瑞爾曾用這把巨劍挑戰各路劍術高手，最後獲得劍神的稱號，晚年因遇不到對手而歸隱山林。德瑞爾將其畢生劍術奧義藏於劍柄中，與這把巨劍隱沒於世。效果：造成下一家80點傷害";
		effectDesArr[2] = "命運天使泰勒莉亞的拒命之手。抵抗命運之人，若心中的意念感動泰勒莉亞會受到天使的庇護，扭轉一切既定於因果之上的事物。效果：被上家攻擊時，可抵消傷害";
		effectDesArr[3] = "以大氣層反射宇宙射線為發想，象徵反彈對手的效果卡能力。若被對手攻擊時，可反射傷害給對方，自身不受到傷害";
		effectDesArr[4] = "瘋狂科學家貝魯克發明的生化地雷，一枚的爆炸威力可以毀掉3/4座足球場，內含17種化學毒素和4種文獻未記載的病毒。傳聞貝魯克將這項技術以天價賣給美國軍方，但因為是試做的半成品，在佈雷時要非常小心。效果：除了自己以外的玩家受到35點傷害";
		effectDesArr[5] = "科學家貝魯克在其實驗室底下埋藏大量的生化地雷，第二次世界大戰結束後，俄國曾入侵貝魯克在太平洋上的實驗室總部。為了不讓技術公諸於世，貝魯克最後決定引爆生化地雷，與入侵者同歸於盡。效果：除了自己以外的玩家受到65點傷害";
		effectDesArr[6] = "民間軼聞流傳，森林中矮精靈常用的魔法，他們時常使用這個魔法戲弄誤入矮精靈居所的旅人，使其方向顛倒迷失於森林之中。效果：改變遊戲行進方向（順時針/逆時針）";
		effectDesArr[7] = "花圃或草叢中可見的幸運草，通常當人們無意尋找時才容易發現它們，是個平凡卻罕見的存在，當人們發現它們的時候會感到驚喜與幸福。效果：對自己增加100點分數";
		effectDesArr[8] = "幸運天使斯普力歐的易換之手。可以讓貧困的人選擇捨棄身上一件不幸的事物，換取幸福。效果：可跟下一家隨機交換手上的牌";

		cards = new Bitmap[9];
		cards[0] = BitmapFactory.decodeResource(getResources(), R.drawable.knife);
		cards[1] = BitmapFactory.decodeResource(getResources(), R.drawable.sword);
		cards[2] = BitmapFactory.decodeResource(getResources(), R.drawable.guard);
		cards[3] = BitmapFactory.decodeResource(getResources(), R.drawable.strikeback);
		cards[4] = BitmapFactory.decodeResource(getResources(), R.drawable.mine);
		cards[5] = BitmapFactory.decodeResource(getResources(), R.drawable.mines);
		cards[6] = BitmapFactory.decodeResource(getResources(), R.drawable.reverse);
		cards[7] = BitmapFactory.decodeResource(getResources(), R.drawable.colorchange);
		cards[8] = BitmapFactory.decodeResource(getResources(), R.drawable.exchange);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,item);
		list = (ListView)findViewById(R.id.cardlistview);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				showCard.setImageBitmap(cards[arg2]);
				description.setText(effectDesArr[arg2]);
			}
			
		});
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}

}
