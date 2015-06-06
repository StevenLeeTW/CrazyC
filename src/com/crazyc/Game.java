package com.crazyc;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity {
	private Bitmap [] cards;
	private Bitmap cardBack, ccw, cw;
	private Player user,  com1, com2, com3;
	private Rule rule;
	ImageView c1Card1, c1Card2,c1Card3,c1Card4,c1Card5,c1Card6,
	c2Card1,c2Card2,c2Card3,c2Card4,c2Card5,c2Card6,
	c3Card1, c3Card2, c3Card3, c3Card4, c3Card5, c3Card6;
	TextView scoreView, c1Score, c2Score, c3Score,users,c1s,c2s,c3s;
	ImageView [] pCardArr ;
	Context context= Game.this;
	int topCard, round, uTotal, c1Total, c2Total, c3Total, whichEffect=-1;
	boolean effect_reverse = true;
	boolean stg;
	static String name;
	Bundle bundleData = new Bundle() ;
	Animation AmP1Card = new TranslateAnimation( 0, 230, 0, -150 );//動畫移動距離
	Animation AmP2Card = new TranslateAnimation( 0, 140, 0, -150 );//動畫移動距離
	Animation AmP3Card = new TranslateAnimation( 0, 66, 0, -150 );//動畫移動距離
	Animation AmP4Card = new TranslateAnimation( 0, 0, 0, -150 );//動畫移動距離
	Animation AmP5Card = new TranslateAnimation( 0, -80, 0, -150 );//動畫移動距離
	Animation AmP6Card = new TranslateAnimation( 0, -165, 0, -150 );//動畫移動距離
	Animation AmC1Card = new TranslateAnimation( 0, 450, 0, 25 );//動畫移動距離
	Animation AmC2Card = new TranslateAnimation( 0, 0, 0, 150 );//動畫移動距離
	Animation AmC3Card = new TranslateAnimation( 0, -450, 0, 25 );//動畫移動距離
	Animation Amscore = new TranslateAnimation(0,0,0,-80);//score動畫

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_game);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		//cards = new Bitmap[76];
		bundleData = getIntent().getExtras();
		rIndex = bundleData.getInt("r");
		//loading();
		stg = true;
		//dialog for entering name or start game
		if(rIndex == 0)
			inputName();
		else
			startGame();
		//temp cards
		cards = new Bitmap[70];
		round = 0;		
		uTotal = bundleData.getInt("user");
		c1Total = bundleData.getInt("com1");
		c2Total = bundleData.getInt("com2");
		c3Total = bundleData.getInt("com3");
		
		scoreView = (TextView)findViewById(R.id.userScore);
		c1Score = (TextView)findViewById(R.id.c1score);
		c2Score = (TextView)findViewById(R.id.c2score);
		c3Score = (TextView)findViewById(R.id.c3score);
		users = (TextView)findViewById(R.id.users);
		c1s = (TextView)findViewById(R.id.c1s);
		c2s = (TextView)findViewById(R.id.c2s);
		c3s = (TextView)findViewById(R.id.c3s);
		
		
		scoreView.setText("1000");
		c1Score.setText("1000");
		c2Score.setText("1000");
		c3Score.setText("1000");
		
		cardsInit();
		
		user = new Player();
		com1 = new Player();
		com2 = new Player();
		com3 = new Player();		
		rule = new Rule(user , com1 , com2 , com3);	
			
		ImageButton pass = (ImageButton)findViewById(R.id.pass);
		pass.setOnClickListener(new ButtonListener());
	}
	
	//放大
	public void enlargebm(ImageView imgView /*,int posiotn*/)
	{
		Matrix matrix = new Matrix();
		float scaleWidth = 2;
		float scaleHeight = 2;
		matrix.postScale(scaleWidth, scaleHeight);
		
	//	imgView.setDrawingCacheEnabled(true);
		Bitmap bmp = null ;
		//temp 54, need to put the new
		if(effect_reverse ){
			bmp = ccw;
		}
		else {
			bmp = cw;
			}
		//else if(sign == 2){
		//	bmp = attacka;
		//}
		
		int w = bmp.getWidth();
		int h = bmp.getHeight();
		Bitmap newbm = Bitmap.createBitmap(bmp, 0, 0, w, h, matrix, true);
		imgView.setImageBitmap(newbm);
//		imgView.destroyDrawingCache();
	}

	
	//telling which mode should be used(enlarge/reduce)
	public void startGame()
	{
		Builder dialog = new AlertDialog.Builder(this);
		DialogInterface.OnClickListener go;
		dialog.setTitle("GO!!!!!!!");
		go = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				for(int i = 0; i < pCardArr.length; i++)
				{
					pCardArr[i].setImageBitmap(cards[user.hand[i]]);
					playCard(cards[user.hand[i]], pCardArr[i], i);
				}
			}
		};
		dialog.setNeutralButton("Ok", go);
		dialog.setCancelable(false);
		dialog.show();	
	}
	public void inputName()
	{
		LayoutInflater inflater = LayoutInflater.from(Game.this);
		View v = inflater.inflate(R.layout.entername, null);
		Builder dialog = new AlertDialog.Builder(this);
		DialogInterface.OnClickListener okbtn, renamebtn;
		dialog.setTitle("Enter your name");
		dialog.setView(v);
		final EditText et = (EditText)v.findViewById(R.id.editname);
		InputFilter[] filters = {new InputFilter.LengthFilter(8)};  
		et.setFilters(filters);
		okbtn = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				name = et.getText().toString();
				if(name.equals(""))
					name = "Player";
				for(int i = 0; i < pCardArr.length; i++)
				{
					pCardArr[i].setImageBitmap(cards[user.hand[i]]);
					playCard(cards[user.hand[i]], pCardArr[i], i);
				}
			}
		};
		renamebtn= new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				et.setText("");
			}
		};
		
		dialog.setNegativeButton("Ok", okbtn);
		dialog.setPositiveButton("Rename", renamebtn);
		dialog.setCancelable(false);
		dialog.show();
		
	}
	
	//若符合出牌規則 則帶入UI圖片及圖片參數出牌 
	public void playCard(Bitmap c, ImageView pCard, int i)
	{
		pCard.setOnClickListener(new ButtonListener(c, pCard, i));			
	}
	
	class ButtonListener implements OnClickListener
	{
		ImageView playedCard  = (ImageView)findViewById(R.id.playedCard);
		Bitmap change;
		ImageView pCard;
		int index;
		int tempHand;

		public ButtonListener()
		{
			//first constructor
		}
		public ButtonListener(Bitmap change,  ImageView pCard, int index)
		{
			this.change = change;
			this.pCard = pCard;
			this.index = index;	
		}
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			//玩家按下pass
			if(arg0.getId() == R.id.pass)
			{
				if(user.pointer < 5)
				{
					dealCardUI();
		
				}
				if(Rule.deck.currentDeck <=5)
				{
					Rule.deck.recycleMerge();
				}
				comRound();
				scoreView.setText(""+user.scoreBox(-10));
				ScoreAnim(0,-10);
			}
			else 
			{
				switch(pCard.getId()){
				case R.id.pCard1:
					amCtrl(0, AmP1Card);
					break;
				case R.id.pCard2:
					amCtrl(1, AmP2Card);
					break;
				case R.id.pCard3:
					amCtrl(2, AmP3Card);; 
					break;
				case R.id.pCard4:
					amCtrl(3, AmP4Card); 
					break;
				case R.id.pCard5:
					amCtrl(4, AmP5Card); 
					break;
				case R.id.pCard6:
					amCtrl(5, AmP6Card);
					break;
				}
				updateScore();					
				new CountDownTimer(870,290){
					int timerrun=1;
					@Override
					public void onFinish() {
						
					}
					
					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
						if(timerrun==2)
						{						
							playedCard.setImageBitmap(change);
							tempHand = user.hand[index];	
							updateScore();	
							//UI手牌整理						
							for(int i=0; i <= user.pointer; i++)
							{
								pCardArr[i].setImageBitmap(cards[user.hand[i]]);
								playCard(cards[user.hand[i]], pCardArr[i], i);
							}
							pCardArr[user.pointer+1].setVisibility(4);	
							
						}
						timerrun++;
						updateScore();
					}
				}.start(); 	
				//game over or not
				isOver();		
				//com round		    
				comRound();
			}
		}		
	}
	
	
	//for user
	public void dealCardUI()
	{
		Rule.deck.deal(user);
		pCardArr[user.pointer].setImageBitmap(cards[user.hand[user.pointer]]);
		pCardArr[user.pointer].setVisibility(0);					
		playCard(cards[user.hand[user.pointer]], pCardArr[user.pointer], user.pointer);
	}
	public void userUnclickable(ImageView [] imgarr)
	{
		for(int i = 0; i<imgarr.length; i++)
		{
			imgarr[i].setClickable(false);
		}
	}
	
	// 玩家出牌限制
	public void restrict()
	{
		if(Rule.deck.recyclePointer > -1)
		{
			topCard = Rule.deck.recycleArr[Rule.deck.recyclePointer];
			String tcSuit = Deck.suit(topCard);
			int tcPoint = Deck.point(topCard);
			
			for(int i = 0; i <= user.pointer; i++)
			{
				if(((Deck.point(user.hand[i])== tcPoint || 
						Deck.suit(user.hand[i]).equals(tcSuit))&& tcPoint < 40)||
						(tcPoint > 39 && user.hand[i]<58) ||
						(user.hand[i] > 39 && user.hand[i] < 58)
						||((user.hand[i]>57&&user.hand[i]<62) && tcPoint<48 &&tcPoint >39)
						||(user.hand[i]>61 && user.hand[i] < 68)
						||((user.hand[i] > 67 && user.hand[i]< 70) && tcPoint<48 &&tcPoint >39))
				{
					pCardArr[i].setClickable(true);
				}
				else
				{
					pCardArr[i].setClickable(false);
				}
			}
		}
	}
	
	//動畫顯示控制
	public void amCtrl(int i, Animation am)
	{		
		if(Rule.deck.recyclePointer>-1 )
		{
			topCard = Rule.deck.recycleArr[Rule.deck.recyclePointer];
			String topSuit = Deck.suit(topCard);
			int topPoint = Deck.point(topCard);
			
			if(((Deck.point(user.hand[i])== topPoint || 
					Deck.suit(user.hand[i]).equals(topSuit))&& topPoint <40)||
					topPoint > 39)
			{
				playCardAnim(pCardArr[i], am, i);

			}		
			//以下的幾個else if為效果卡"特效顯示"及效果實現
			else if(user.hand[i] > 39 && user.hand[i] < 44)
			{
				if(effect_reverse == true){
					com3.scoreBox(-50);
					ScoreAnim(3,-50);
				}
				else{
					com1.scoreBox(-50);
				ScoreAnim(1,-50);
				}
				playCardAnim(pCardArr[i], am, i);	
				
				updateScore();
			}
			else if(user.hand[i] > 43 && user.hand[i] < 48)
			{
				if(effect_reverse == true ){
					com3.scoreBox(-80);
				ScoreAnim(3,-80);
				}
				else{
					com1.scoreBox(-80);
					ScoreAnim(1,-80);
					}
				playCardAnim(pCardArr[i], am, i);
				updateScore();
			}
			else if(user.hand[i] > 47 && user.hand[i] < 52)
			{
				com1.scoreBox(-35);
				com2.scoreBox(-35);
				com3.scoreBox(-35);
				ScoreAnim(4,-35);
				playCardAnim(pCardArr[i], am, i);
				updateScore();

			}
			else if(user.hand[i] > 51 && user.hand[i] < 54)
			{
				com1.scoreBox(-65);
				com2.scoreBox(-65);
				com3.scoreBox(-65);
				ScoreAnim(4,-65);
				playCardAnim(pCardArr[i], am, i);
				updateScore();

			}
			else if(user.hand[i] > 53 && user.hand[i] < 58)
			{
				effect_reverse = !effect_reverse;
				playCardAnim(pCardArr[i], am, i );
			}
			else if(user.hand[i] > 57 && user.hand[i] < 62 )
			{
				if(topPoint > 39 && topPoint < 44){
					user.scoreBox(50);
					ScoreAnim(0,+50);
				}
				else if(topPoint > 43 && topPoint < 48){
					user.scoreBox(80);
					ScoreAnim(0,+80);
				}
				updateScore();
				playCardAnim(pCardArr[i], am, i );
			}
			else if(user.hand[i] > 61 && user.hand[i]< 66)
			{
				playCardAnim(pCardArr[i], am, i );
			}
			else if(user.hand[i] > 65 && user.hand[i] < 68)
			{
				user.scoreBox(100);
				ScoreAnim(0,+100);
				playCardAnim(pCardArr[i], am, i );
				updateScore();
			}
			else if(user.hand[i] > 67 && user.hand[i] < 70 )
			{
				if(effect_reverse == true)
				{
					if(topPoint > 39 && topPoint < 44)
					{
						user.scoreBox(50);
						ScoreAnim(0,+50);
						com1.scoreBox(-50);
						ScoreAnim(1,-50);
					}
					else if(topPoint > 43 && topPoint < 48)
					{
						user.scoreBox(80);
						ScoreAnim(0,+80);
						com1.scoreBox(-80);
						ScoreAnim(1,-80);
					}
				}
				else
				{
					if(topPoint > 39 && topPoint < 44)
					{
						user.scoreBox(50);
						ScoreAnim(0,+50);
						com3.scoreBox(-50);
						ScoreAnim(3,-50);
					}
					else if(topPoint > 43 && topPoint < 48)
					{
						user.scoreBox(80);
						ScoreAnim(0,+80);
						com3.scoreBox(-80);
						ScoreAnim(3,-80);
					}
				}
				updateScore();
				playCardAnim(pCardArr[i], am, i );
			}
		}
		else if(stg)
		{
			if(user.hand[i] > 39 && user.hand[i] < 44)
			{
				if(effect_reverse == true){
					com3.scoreBox(-50);
				ScoreAnim(3,-50);
				}
				else {
					com1.scoreBox(-50);
					ScoreAnim(1,-50);
				}
				playCardAnim(pCardArr[i], am, i);		
				updateScore();
			}
			else if(user.hand[i] > 43 && user.hand[i] < 48)
			{
				if(effect_reverse == true ){
					com3.scoreBox(-80);
					ScoreAnim(3,-80);
				}
				else{
					com1.scoreBox(-80);
					ScoreAnim(1,-80);
					}
				playCardAnim(pCardArr[i], am, i);
				updateScore();
			}
			else if(user.hand[i] > 47 && user.hand[i] < 52)
			{
				com1.scoreBox(-35);
				com2.scoreBox(-35);
				com3.scoreBox(-35);
				ScoreAnim(4,-35);
				playCardAnim(pCardArr[i], am, i);
				updateScore();

			}
			else if(user.hand[i] > 51 && user.hand[i] < 54)
			{
				com1.scoreBox(-65);
				com2.scoreBox(-65);
				com3.scoreBox(-65);
				ScoreAnim(4,-65);
				playCardAnim(pCardArr[i], am, i);
				updateScore();

			}
			else if(user.hand[i] > 53 && user.hand[i] < 58)
			{
				effect_reverse = !effect_reverse;
				playCardAnim(pCardArr[i], am, i );
			}
			else if(user.hand[i] > 61 && user.hand[i]< 66)
			{
				playCardAnim(pCardArr[i], am, i );
			}
			else if(user.hand[i] > 65 && user.hand[i] < 68)
			{
				user.scoreBox(100);
				ScoreAnim(0,+100);
				playCardAnim(pCardArr[i], am, i );
				updateScore();
			}
			else 
			{
				playCardAnim(pCardArr[i], am, i );
			}
			
			stg = false;			
		}
	}	
	
	//將玩家出牌動畫寫成method
	public void playCardAnim(ImageView pCardA,Animation a, int i)
	{
		if(user.hand[i] > 53 && user.hand[i] < 58)
		{
			//改
			eCardToast(1);
		}
		else if(user.hand[i] > 39 && user.hand[i] < 44){
			eCardToast(2);
		}
		else if(user.hand[i] > 43 && user.hand[i] < 48){
			eCardToast(2);
		}
		else if(user.hand[i] > 47 && user.hand[i] < 52){//MINE
			eCardToast(3);
		}
		else if(user.hand[i] > 51 && user.hand[i] < 54){//MINES
			eCardToast(3);
		}
		else if(user.hand[i] > 57 && user.hand[i] < 62){//deffense
			eCardToast(4);
		}
		else if(user.hand[i] > 65 && user.hand[i] < 68){//fourleaf
			eCardToast(5);
		}
		else if(user.hand[i] > 67 && user.hand[i] < 70){//reflect
			eCardToast(6);
		}
		
		pCardA.clearAnimation();
		a.setDuration(300);
		pCardA.startAnimation(a);
		
		user.play(i);
	}
	
	public void eCardToast(final int a)//A為效果牌判斷變數  1迴轉2單體攻擊3群體攻擊
	{
		final ImageView imgView = new ImageView(context);
		final Toast toast = Toast.makeText(getApplicationContext(),
				"", Toast.LENGTH_SHORT);
		new CountDownTimer(500, 250){

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				toast.cancel();
			}

			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				 if(a == 1){//reverse

					toast.setGravity(Gravity.CENTER, 0, 0);
					enlargebm(imgView);
					LinearLayout toastView = new LinearLayout(context);
					toastView.addView(imgView);
					toast.setView(toastView);
					toast.show();
					}
					else if(a == 2){//單體
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = new LinearLayout(context);
						imgView.setImageResource(R.drawable.attacksin);
						toastView.addView(imgView);
						toast.setView(toastView);
						toast.show();
					}
					
					else if(a == 3){//群體
						
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = new LinearLayout(context);
						imgView.setImageResource(R.drawable.attackall);
						toastView.addView(imgView);
						toast.setView(toastView);
						toast.show();
					}
					else if(a == 4){//阻擋
						
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = new LinearLayout(context);
						imgView.setImageResource(R.drawable.defenseeff);
						toastView.addView(imgView);
						toast.setView(toastView);
						toast.show();
					}
					else if(a == 5){//幸運草
						
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = new LinearLayout(context);
						imgView.setImageResource(R.drawable.fourleafeff);
						toastView.addView(imgView);
						toast.setView(toastView);
						toast.show();
					}
					else if(a == 6){//反射
						
						toast.setGravity(Gravity.CENTER, 0, 0);
						LinearLayout toastView = new LinearLayout(context);
						imgView.setImageResource(R.drawable.reflecteff);
						toastView.addView(imgView);
						toast.setView(toastView);
						toast.show();
					}
			}
			
		}.start();
		
		
	}
	
	//玩家"迴轉"效果要在此修改
	public void comRound()
	{
		final ImageView t1 = (ImageView)findViewById(R.id.test1);
		final ImageView t2 = (ImageView)findViewById(R.id.test2);
		final ImageView t3 = (ImageView)findViewById(R.id.test3);
		final ImageView[] c1CardArr  = {c1Card1, c1Card2, c1Card3, c1Card4, c1Card5, c1Card6};
		final ImageView[] c2CardArr  = {c2Card1, c2Card2, c2Card3, c2Card4, c2Card5, c2Card6};
		final ImageView[] c3CardArr  = {c3Card1, c3Card2, c3Card3, c3Card4, c3Card5, c3Card6};

		new CountDownTimer(3000,600){
			int timerrun=1;
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				//完成一次後要執行的寫在這
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				//counterclockwise = true
				if(effect_reverse == true)
				{
					if(timerrun==2){
						ComPlayedCard(com3, t3, c3CardArr, c3Score, com2, com1, user);
						//若效果卡為迴轉(54)
						// make user's hand cards unclickable after user played any card
						if(whichEffect ==54)
						{
							whichEffect = -1;
					 		effect_reverse = false;
							restrict();
							timerrun = 6;							
						}
					}
					else if(timerrun==3){
						ComPlayedCard(com2, t2, c2CardArr, c2Score,com1, user, com3);
						//若效果卡為迴轉(54)
						if(whichEffect==54)
						{
							whichEffect = -1;
							effect_reverse = false;
							comRoundForC2Rev();
							restrict();
							timerrun = 6;
						}
					}
					else if(timerrun==4){
						ComPlayedCard(com1, t1, c1CardArr, c1Score, user, com3, com2);
						if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = false;
							comRoundForC1Rev();	
							restrict();
							timerrun = 6;
						}
					}
				}
				//clockwise = false
				else if(effect_reverse == false)
				{
					if(timerrun==2){
						ComPlayedCard(com1, t1, c1CardArr, c1Score, com2, com3, user);
						//效果卡
						if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = true;
							restrict();
							timerrun = 6;
						}
					}
					else if(timerrun==3){
						ComPlayedCard(com2, t2, c2CardArr, c2Score,com3, user, com1);
						if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = true;
							comRoundForC2Rev();
							restrict();
							timerrun = 6;
						}
					}
					else if(timerrun==4){
						ComPlayedCard(com3, t3, c3CardArr, c3Score, user, com1, com2);
						if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = true;
							comRoundForC3Rev();
							restrict();
							timerrun = 6;
						}
					}
				}
				timerrun++;
				restrict();
			}
		}.start();
		
		if(user.pointer == 0)
			dealCardUI();
	}
	
	public void comRoundForC1Rev()
	{
		final ImageView t1 = (ImageView)findViewById(R.id.test1);
		final ImageView t2 = (ImageView)findViewById(R.id.test2);
		final ImageView t3 = (ImageView)findViewById(R.id.test3);
		final ImageView[] c1CardArr  = {c1Card1, c1Card2, c1Card3, c1Card4, c1Card5, c1Card6};
		final ImageView[] c2CardArr  = {c2Card1, c2Card2, c2Card3, c2Card4, c2Card5, c2Card6};
		final ImageView[] c3CardArr  = {c3Card1, c3Card2, c3Card3, c3Card4, c3Card5, c3Card6};
		
		new CountDownTimer(2400,600)
		{
			int timerrun = 1;

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if(effect_reverse == false)
				{
					if(timerrun == 2)
					{
						ComPlayedCard(com2, t2, c2CardArr, c2Score,com3, user, com1);
						//效果卡
					if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = true;
							comRoundForC2Rev();
							timerrun = 5;
						}
					}
					else if(timerrun == 3)
					{
						ComPlayedCard(com3, t3, c3CardArr, c3Score, user, com1, com2);
						//效果卡
						if(whichEffect == 54)
						{
							whichEffect = -1 ;
							effect_reverse = true;
							comRoundForC3Rev();
							timerrun = 5;
						}
					}
				}
				else if(effect_reverse == true)
				{
					timerrun = 5;
				}
				timerrun++;
				restrict();
			}
		}.start();
		
		if(user.pointer == 0)
			dealCardUI();
	}
	
	//complaycard is the main function that computer plays.
	public void comRoundForC2Rev()
	{
		final ImageView t1 = (ImageView)findViewById(R.id.test1);
		final ImageView t2 = (ImageView)findViewById(R.id.test2);
		final ImageView t3 = (ImageView)findViewById(R.id.test3);
		final ImageView[] c1CardArr  = {c1Card1, c1Card2, c1Card3, c1Card4, c1Card5, c1Card6};
		final ImageView[] c2CardArr  = {c2Card1, c2Card2, c2Card3, c2Card4, c2Card5, c2Card6};
		final ImageView[] c3CardArr  = {c3Card1, c3Card2, c3Card3, c3Card4, c3Card5, c3Card6};
		
		new CountDownTimer(1800,600)
		{
			int timerrun=1;
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if(effect_reverse == false)
				{
					if(timerrun == 2)
					{
						ComPlayedCard(com3, t3, c3CardArr, c3Score, user, com1, com2);
						//效果卡						
						if(whichEffect==54)
						{
							whichEffect = -1;
							effect_reverse = true;
							comRoundForC3Rev();
							timerrun = 4;
						}
					}			
				}
				else if(effect_reverse == true)
				{
					if(timerrun == 2)
					{
						ComPlayedCard(com1, t1, c1CardArr, c1Score, user, com3, com2);
						//效果卡
						if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = false;
							comRoundForC1Rev();
							timerrun = 4;
						}
					}
				}
				
				timerrun++;
				restrict();
			}			
		}.start();
	}
	
	public void comRoundForC3Rev()
	{
		final ImageView t1 = (ImageView)findViewById(R.id.test1);
		final ImageView t2 = (ImageView)findViewById(R.id.test2);
		final ImageView t3 = (ImageView)findViewById(R.id.test3);
		final ImageView[] c1CardArr  = {c1Card1, c1Card2, c1Card3, c1Card4, c1Card5, c1Card6};
		final ImageView[] c2CardArr  = {c2Card1, c2Card2, c2Card3, c2Card4, c2Card5, c2Card6};
		final ImageView[] c3CardArr  = {c3Card1, c3Card2, c3Card3, c3Card4, c3Card5, c3Card6};
		
		new CountDownTimer(2400,600)
		{
			int timerrun = 1;

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				if(effect_reverse == true)
				{
					if(timerrun == 2)
					{
						ComPlayedCard(com2, t2, c2CardArr, c2Score,com1, user, com3);						
						if(whichEffect == 54)
						{
							whichEffect = -1;
							effect_reverse = false;
							comRoundForC2Rev();
							timerrun = 5;
						}

					}
					else if(timerrun == 3)
					{
						ComPlayedCard(com1, t1, c1CardArr, c1Score, user, com3, com2);
						if(whichEffect == 54)
						{
							whichEffect = -1 ;
							effect_reverse = false;
							comRoundForC1Rev();
							timerrun = 5;
						}
					}
				}
				else if(effect_reverse == false)
				{
					timerrun = 5;
				}
				timerrun++;
				restrict();
			}
		}.start();
		
		if(user.pointer == 0)
			dealCardUI();
	}
	
	//電腦"迴轉"效果在此判斷
	//處理電腦出牌,分數控制以及手牌增加減少UI
	public void ComPlayedCard(Player com, final ImageView playedCard, ImageView [] cNcardN, TextView s 
			,Player next, Player opposite, Player last)
	{	
		whichEffect = -1;
		//手牌若只有一張牌  就必須抽牌
		if(com.pointer==0)
		{
			Rule.deck.deal(com);			
			cNcardN[1].setVisibility(0);

		}
	
		final int tempAI = rule.easyAI(com, next, opposite, last);	
		
		//若有牌可出
		if(tempAI >-1)
		{
			switch(s.getId()){
			case R.id.c1score:
				cNcardN[com.pointer+1].setVisibility(4);
				cNcardN[com.pointer].clearAnimation();
				AmC1Card.setDuration(300);
				findViewById(R.id.comCard1).startAnimation(AmC1Card); 
				new CountDownTimer(870,290)
				{
					int timerrun=1;
					@Override
					public void onFinish() {}
					@Override
					public void onTick(long millisUntilFinished) 
					{
						// TODO Auto-generated method stub
						if(timerrun==2){
						playedCard.setImageBitmap(cards[tempAI]);
						} 
						timerrun++;
					}
				}.start(); 
				break;
			case R.id.c2score:
				cNcardN[com.pointer+1].setVisibility(4);
				cNcardN[com.pointer].clearAnimation();
				AmC2Card.setDuration(300);
				findViewById(R.id.com2Card4).startAnimation(AmC2Card); 
				new CountDownTimer(870,290)
				{
					int timerrun=1;
		        	@Override
					public void onFinish() {}
		            @Override
					public void onTick(long millisUntilFinished) {
		            	// TODO Auto-generated method stub
		            	if(timerrun==2){
		            		playedCard.setImageBitmap(cards[tempAI]);
					    } 
		            	timerrun++;
		            }	        
				}.start(); 
				break;
			case R.id.c3score:
				cNcardN[com.pointer+1].setVisibility(4);
				cNcardN[com.pointer].clearAnimation();
				AmC3Card.setDuration(300);
				findViewById(R.id.com3Card1).startAnimation(AmC3Card); 
				new CountDownTimer(870,290)
				{
		        	int timerrun=1;
		            @Override
					public void onFinish() {}
		            @Override
					public void onTick(long millisUntilFinished) {
		                // TODO Auto-generated method stub
					    if(timerrun==2){
							playedCard.setImageBitmap(cards[tempAI]);
					    } 
					    timerrun++;
		            }	        
				}.start(); 
				break;
			}
			
			if(tempAI > 39 && tempAI < 44){
				eCardToast(2);
				if(com == com1 && next == user){
					ScoreAnim(0,-50);
					}	
					else if(com == com1 && next ==com2){
					ScoreAnim(2,-50);
					}	
					else if(com == com2 && next ==com1){
					ScoreAnim(1,-50);
					}	
					else if(com == com2 && next ==com3){
					ScoreAnim(3,-50);
					}	
					else if(com == com3 && next ==com2){
					ScoreAnim(2,-50);
					}	
					else if(com == com3 && next ==user){
					ScoreAnim(0,-50);
					}	
			}
			if(tempAI > 43 && tempAI < 48){
				eCardToast(2);
				if(com == com1 && next == user){
					ScoreAnim(0,-80);
					}	
					else if(com == com1 && next ==com2){
					ScoreAnim(2,-80);
					}	
					else if(com == com2 && next ==com1){
					ScoreAnim(1,-80);
					}	
					else if(com == com2 && next ==com3){
					ScoreAnim(3,-80);
					}	
					else if(com == com3 && next ==com2){
					ScoreAnim(2,-80);
					}	
					else if(com == com3 && next ==user){
					ScoreAnim(0,-80);
					}	
			}
			else if(tempAI > 47 && tempAI <52){
				eCardToast(3);
				if(com == com1 ){
					ScoreAnim(5,-35);
					}	
					else if(com == com2 ){
					ScoreAnim(6,-35);
					}	
					else if(com == com3 ){
					ScoreAnim(7,-35);
					}	
			}
			else if(tempAI > 51 && tempAI <54){
				eCardToast(3);
				if(com == com1 ){
					ScoreAnim(5,-65);
					}	
					else if(com == com2 ){
					ScoreAnim(6,-65);
					}	
					else if(com == com3 ){
					ScoreAnim(7,-65);
					}	
			}
			else if(tempAI > 53 && tempAI < 58)//回選
			{
				eCardToast(1);
			}
			else if(tempAI > 57 && tempAI <62){//阻擋
				eCardToast(4);
				if(topCard> 39 && topCard < 44)
				{
					if(com == com1 ){
						ScoreAnim(1,+50);
						}	
						else if(com == com2 ){
						ScoreAnim(2,+50);
						}	
						else if(com == com3 ){
						ScoreAnim(3,+50);
						}	
				}
				else if(topCard > 43 && topCard < 48)
				{
					if(com == com1 ){
						ScoreAnim(1,+80);
						}	
						else if(com == com2 ){
						ScoreAnim(2,+80);
						}	
						else if(com == com3 ){
						ScoreAnim(3,+80);
						}	
				}
				else if(topCard > 47 && topCard < 52)
				{
					if(com == com1 ){
						ScoreAnim(1,+35);
						}	
						else if(com == com2 ){
						ScoreAnim(2,+35);
						}	
						else if(com == com3 ){
						ScoreAnim(3,+35);
						}	
				}
				else if(topCard > 51 && topCard < 54)
				{
					if(com == com1 ){
						ScoreAnim(1,+65);
						}	
						else if(com == com2 ){
						ScoreAnim(2,+65);
						}	
						else if(com == com3 ){
						ScoreAnim(3,+65);
						}	
				}
			}
		else if(tempAI > 65 && tempAI <68){//fourleaf
			eCardToast(5);
			if(com == com1 ){
				ScoreAnim(1,+100);
				}	
				else if(com == com2 ){
				ScoreAnim(2,+100);
				}	
				else if(com == com3 ){
				ScoreAnim(3,+100);
				}
			}
		else if(tempAI > 67 && tempAI <70){//反射
			eCardToast(6);
			if(topCard > 39 && topCard < 44)
			{
				if(com == com1 && opposite == user){
					ScoreAnim(0,-50);
					ScoreAnim(1,+50);
					}	
					else if(com == com1 && opposite == com2){
					ScoreAnim(2,-50);
					ScoreAnim(1,+50);
						}	
					else if(com == com2 && opposite == com1){
					ScoreAnim(2,+50);
					ScoreAnim(1,-50);
					}	
					else if(com == com2 && opposite == com3){
					ScoreAnim(2,+50);
					ScoreAnim(3,-50);
					}	
					else if(com == com3 && opposite == com2){
					ScoreAnim(3,+50);
					ScoreAnim(2,-50);
					}	
					else if(com == com3 && opposite == user){
					ScoreAnim(3,+50);
					ScoreAnim(0,-50);
					}
			}
			else if(topCard > 43 && topCard < 48)
			{
				if(com == com1 && opposite == user){
					ScoreAnim(0,-80);
					ScoreAnim(1,+80);
					}	
					else if(com == com1 && opposite == com2){
					ScoreAnim(2,-80);
					ScoreAnim(1,+80);
						}	
					else if(com == com2 && opposite == com1){
					ScoreAnim(2,+80);
					ScoreAnim(1,-80);
					}	
					else if(com == com2 && opposite == com3){
					ScoreAnim(2,+80);
					ScoreAnim(3,-80);
					}	
					else if(com == com3 && opposite == com2){
					ScoreAnim(3,+80);
					ScoreAnim(2,-80);
					}	
					else if(com == com3 && opposite == user){
					ScoreAnim(3,+80);
					ScoreAnim(0,-80);
					}
			}
			else if(topCard > 47 && topCard < 52)
			{
				if(com == com1 && opposite == user){
					ScoreAnim(0,-35);
					ScoreAnim(1,+35);
					}	
					else if(com == com1 && opposite == com2){
					ScoreAnim(2,-35);
					ScoreAnim(1,+35);
						}	
					else if(com == com2 && opposite == com1){
					ScoreAnim(2,+35);
					ScoreAnim(1,-35);
					}	
					else if(com == com2 && opposite == com3){
					ScoreAnim(2,+35);
					ScoreAnim(3,-35);
					}	
					else if(com == com3 && opposite == com2){
					ScoreAnim(3,+35);
					ScoreAnim(2,-35);
					}	
					else if(com == com3 && opposite == user){
					ScoreAnim(3,+35);
					ScoreAnim(0,-35);
					}
			}
			else if(topCard > 51 && topCard < 54)
			{
				if(com == com1 && opposite == user){
					ScoreAnim(0,-65);
					ScoreAnim(1,+65);
					}	
					else if(com == com1 && opposite == com2){
					ScoreAnim(2,-65);
					ScoreAnim(1,+65);
						}	
					else if(com == com2 && opposite == com1){
					ScoreAnim(2,+65);
					ScoreAnim(1,-65);
					}	
					else if(com == com2 && opposite == com3){
					ScoreAnim(2,+65);
					ScoreAnim(3,-65);
					}	
					else if(com == com3 && opposite == com2){
					ScoreAnim(3,+65);
					ScoreAnim(2,-65);
					}	
					else if(com == com3 && opposite == user){
					ScoreAnim(3,+65);
					ScoreAnim(0,-65);
					}
		
			}
		}
		}
		//若pass 則扣分（寫在AI裡） 若手牌小於六張 則需抽牌
		else
		{			
			//抽牌部分UI
			if(com.pointer < 5)
			{
				Rule.deck.deal(com);
				cNcardN[com.pointer].setVisibility(0);
				playedCard.setImageBitmap(cardBack);
				if(com == com1){
					ScoreAnim(1,-10);
					}	
					else if(com == com2){
					ScoreAnim(2,-10);
					}	
					else if(com == com3){
					ScoreAnim(3,-10);
					}
			}				
		}
		
		if(Rule.deck.currentDeck <=5)
		{
			Rule.deck.recycleMerge();
		}
		isOver();
		
		int tempNum=tempAI;
		if(tempNum>53 && tempNum <58)
			tempNum = 54;
		
		else if(tempNum > 61 && tempNum<66)
		{
			tempNum = 61;
		}

		whichEffect = tempNum;
		if(whichEffect == 61)
		{
			Rule.deck.deal(com);
			cNcardN[com.pointer].setVisibility(0);
			playedCard.setImageBitmap(cardBack);
		}
		
		updateScore();

	}
	
	public void cardsInit()
	{
		pCardArr = new ImageView[6];
		pCardArr[0] = (ImageView)findViewById(R.id.pCard1);
		pCardArr[1] = (ImageView)findViewById(R.id.pCard2);
		pCardArr[2] = (ImageView)findViewById(R.id.pCard3);
		pCardArr[3] = (ImageView)findViewById(R.id.pCard4);
		pCardArr[4] = (ImageView)findViewById(R.id.pCard5);
		pCardArr[5] = (ImageView)findViewById(R.id.pCard6);	
		c1Card1 = (ImageView)findViewById(R.id.comCard1);
		c1Card2 = (ImageView)findViewById(R.id.comCard2);
		c1Card3 = (ImageView)findViewById(R.id.comCard3);
		c1Card4 = (ImageView)findViewById(R.id.comCard4);
		c1Card5 = (ImageView)findViewById(R.id.comCard5);
		c1Card6 = (ImageView)findViewById(R.id.comCard6);
		c2Card1 = (ImageView)findViewById(R.id.com2Card4);
		c2Card2 = (ImageView)findViewById(R.id.com2Card3);
		c2Card3 = (ImageView)findViewById(R.id.com2Card2);
		c2Card4 = (ImageView)findViewById(R.id.com2Card1);
		c2Card5 = (ImageView)findViewById(R.id.com2Card5);
		c2Card6 = (ImageView)findViewById(R.id.com2Card6);
		c3Card1 = (ImageView)findViewById(R.id.com3Card1);
		c3Card2 = (ImageView)findViewById(R.id.com3Card2);
		c3Card3 = (ImageView)findViewById(R.id.com3Card3);
		c3Card4 = (ImageView)findViewById(R.id.com3Card4);
		c3Card5 = (ImageView)findViewById(R.id.com3Card5);
		c3Card6 = (ImageView)findViewById(R.id.com3Card6);		
	
		//Activity使用bitmap陣列來儲存牌  Rule裡面使用int陣列來對應要出的牌
		cardBack = BitmapFactory.decodeResource(getResources(),R.drawable.cardback);
		cards[0] =BitmapFactory.decodeResource(getResources(), R.drawable.green0);
		cards[4] =BitmapFactory.decodeResource(getResources(), R.drawable.green1);
		cards[8] =BitmapFactory.decodeResource(getResources(), R.drawable.green2);
		cards[12] =BitmapFactory.decodeResource(getResources(), R.drawable.green3);
		cards[16] =BitmapFactory.decodeResource(getResources(), R.drawable.green4);
		cards[20] =BitmapFactory.decodeResource(getResources(), R.drawable.green5);
		cards[24] =BitmapFactory.decodeResource(getResources(), R.drawable.green6);
		cards[28] =BitmapFactory.decodeResource(getResources(), R.drawable.green7);
		cards[32] =BitmapFactory.decodeResource(getResources(), R.drawable.green8);
		cards[36] =BitmapFactory.decodeResource(getResources(), R.drawable.green9);
		cards[1] =BitmapFactory.decodeResource(getResources(), R.drawable.red0);
		cards[5] =BitmapFactory.decodeResource(getResources(), R.drawable.red1);
		cards[9] =BitmapFactory.decodeResource(getResources(), R.drawable.red2);
		cards[13] =BitmapFactory.decodeResource(getResources(), R.drawable.red3);
		cards[17] =BitmapFactory.decodeResource(getResources(), R.drawable.red4);
		cards[21] =BitmapFactory.decodeResource(getResources(), R.drawable.red5);
		cards[25] =BitmapFactory.decodeResource(getResources(), R.drawable.red6);
		cards[29] =BitmapFactory.decodeResource(getResources(), R.drawable.red7);
		cards[33] =BitmapFactory.decodeResource(getResources(), R.drawable.red8);
		cards[37] =BitmapFactory.decodeResource(getResources(), R.drawable.red9);	
		cards[2] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow0);
		cards[6] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow1);
		cards[10] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow2);
		cards[14] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow3);
		cards[18] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow4);
		cards[22] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow5);
		cards[26] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow6);
		cards[30] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow7);
		cards[34] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow8);
		cards[38] =BitmapFactory.decodeResource(getResources(), R.drawable.yellow9);
		cards[3] =BitmapFactory.decodeResource(getResources(), R.drawable.blue0);
		cards[7] =BitmapFactory.decodeResource(getResources(), R.drawable.blue1);
		cards[11] =BitmapFactory.decodeResource(getResources(), R.drawable.blue2);
		cards[15] =BitmapFactory.decodeResource(getResources(), R.drawable.blue3);
		cards[19] =BitmapFactory.decodeResource(getResources(), R.drawable.blue4);
		cards[23] =BitmapFactory.decodeResource(getResources(), R.drawable.blue5);
		cards[27] =BitmapFactory.decodeResource(getResources(), R.drawable.blue6);
		cards[31] =BitmapFactory.decodeResource(getResources(), R.drawable.blue7);
		cards[35] =BitmapFactory.decodeResource(getResources(), R.drawable.blue8);
		cards[39] =BitmapFactory.decodeResource(getResources(), R.drawable.blue9);
		cards[40] =BitmapFactory.decodeResource(getResources(), R.drawable.knife);
		cards[41] =cards[40];
		cards[42] =cards[40];
		cards[43] =cards[40];
		cards[44]=BitmapFactory.decodeResource(getResources(), R.drawable.sword);
		cards[45]=cards[44];
		cards[46]=cards[44];
		cards[47]=cards[44];
		cards[48] =BitmapFactory.decodeResource(getResources(), R.drawable.mine);
		cards[49]=cards[48];
		cards[50]=cards[48];
		cards[51]=cards[48];
		cards[52]=BitmapFactory.decodeResource(getResources(), R.drawable.mines);
		cards[53] =cards[52];
		cards[54] = BitmapFactory.decodeResource(getResources(), R.drawable.reverse);
		cards[55]= cards[54];
		cards[56]= cards[54];
		cards[57]= cards[54];
		cards[58]= BitmapFactory.decodeResource(getResources(), R.drawable.guard);
		cards[59]= cards[58];
		cards[60]= cards[58];
		cards[61]= cards[58];
		cards[62]= BitmapFactory.decodeResource(getResources(), R.drawable.exchange);
		cards[63]= cards[62];
		cards[64]= cards[62];
		cards[65]= cards[62];	
		cards[66]= BitmapFactory.decodeResource(getResources(), R.drawable.colorchange);
		cards[67]= cards[66];
		cards[68]= BitmapFactory.decodeResource(getResources(), R.drawable.strikeback);
		cards[69]= cards[68];
		ccw = BitmapFactory.decodeResource(getResources(), R.drawable.ccw);
		cw = BitmapFactory.decodeResource(getResources(), R.drawable.cw);
	}
	
	int rIndex ;
	public void endDialog()
	{
		DialogInterface.OnClickListener goOn, exit;
		int finRound [] = {1, 2, 3, 4};
		Builder finDialog = new AlertDialog.Builder(this);
		
		if(rIndex == 3)
			finDialog.setTitle("最終回結束!");		
		else
			finDialog.setTitle("第"+finRound[rIndex]+"回結束!");
		
		finDialog.setMessage("前往積分排名!!");
		
		intent = new Intent();
		
		goOn = new DialogInterface.OnClickListener() 
		{	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				intent.setClass(Game.this, EndOfGame.class);			
				bundleData.putInt("user", uTotal);
				bundleData.putInt("com1", c1Total);
				bundleData.putInt("com2", c2Total);
				bundleData.putInt("com3", c3Total);
				bundleData.putInt("r", rIndex+1);
				intent.putExtras(bundleData);				
				startActivity(intent);
				finish();
			}
		};
		exit = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				intent.setClass(Game.this, MainMenu.class);
				startActivity(intent);
				Game.this.finish();
			}
		};
		
		finDialog.setNegativeButton("繼續", goOn);
		finDialog.setPositiveButton("退出", exit);
		finDialog.setCancelable(false);
		finDialog.show();
	}
	Intent intent;
	public void exitDialog()
	{
		DialogInterface.OnClickListener yes, no;
		Builder finDialog = new AlertDialog.Builder(this);
		
		
		finDialog.setTitle("確定要結束遊戲嗎？");		
		
		intent = new Intent();
		
		yes = new DialogInterface.OnClickListener() 
		{	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub				
				Intent intent = new Intent();
				intent.setClass(Game.this, MainMenu.class);
				startActivity(intent);
				finish();
			}
		};
		no = new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		};
		
		finDialog.setNegativeButton("確定", yes);
		finDialog.setPositiveButton("取消", no);
		finDialog.setCancelable(false);
		finDialog.show();
	}
	int countdown;
	public void isOver()
	{
		//�ݭn�ǰe���Ʀ�End��������ܱƦW
		//�nToggle ��n�^�X����  ���U�T�w  �i�J���Z�p�⭶��
		//
		round++;
		if(round%4 == 0)
			countdown++;
		if(round == 20)
		{
			uTotal += user.score;
			c1Total += com1.score;
			c2Total += com2.score;
			c3Total += com3.score;
			updateCD(countdown);
			endDialog();
		}
		else
			updateCD(countdown);
	}
	private Handler endHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			uTotal += user.score;
			c1Total += com1.score;
			c2Total += com2.score;
			c3Total += com3.score;
			updateCD(countdown);
			endDialog();
		}
	};
/*	private Handler handlerECard = new Handler(){
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			
		}
	};*/
	
	
	public void updateCD(int cd)
	{
		int tempcd = 15-cd;
		int d1 = tempcd/10;
		int d2 = tempcd%10;
		TextView dd1 = (TextView)findViewById(R.id.cddigit1);
		TextView dd2 = (TextView)findViewById(R.id.cddigit2);
		numImg();
		dd1.setBackgroundResource(numArr[d1]);
		dd2.setBackgroundResource(numArr[d2]);
	}
	
	public void updateScore()
	{
		c1Score.setText(""+com1.scoreBox(0));
		c2Score.setText(""+com2.scoreBox(0));
		c3Score.setText(""+com3.scoreBox(0));
		scoreView.setText(""+user.scoreBox(0));		
	}
	
	public void ScoreAnim(final int player,final int score){
		if(score>0){
			users.setTextColor(Color.parseColor("#00ff00"));
			c1s.setTextColor(Color.parseColor("#00ff00"));
			c2s.setTextColor(Color.parseColor("#00ff00"));
			c3s.setTextColor(Color.parseColor("#00ff00"));
		}
		else{
			users.setTextColor(Color.parseColor("#ff0000"));
			c1s.setTextColor(Color.parseColor("#ff0000"));
			c2s.setTextColor(Color.parseColor("#ff0000"));
			c3s.setTextColor(Color.parseColor("#ff0000"));
		}
		Amscore.setDuration(500);
		new CountDownTimer(460,230){

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if(player == 0 ){
					users.setVisibility(View.INVISIBLE);
				}
				else if(player == 1 ){
					c1s.setVisibility(View.INVISIBLE);
				}
				else if(player == 2 ){
				c2s.setVisibility(View.INVISIBLE);
				}
				else if(player == 3 ){
					c3s.setVisibility(View.INVISIBLE);
				}
				else if(player == 4 ){
					c3s.setVisibility(View.INVISIBLE);
					c1s.setVisibility(View.INVISIBLE);
					c2s.setVisibility(View.INVISIBLE);
				}
				else if(player == 5 ){
					c3s.setVisibility(View.INVISIBLE);
					c2s.setVisibility(View.INVISIBLE);
					users.setVisibility(View.INVISIBLE);
				}
				else if(player == 6 ){
					c3s.setVisibility(View.INVISIBLE);
					c1s.setVisibility(View.INVISIBLE);
					users.setVisibility(View.INVISIBLE);
				}
				else if(player == 7 ){
					c1s.setVisibility(View.INVISIBLE);
					c2s.setVisibility(View.INVISIBLE);
					users.setVisibility(View.INVISIBLE);
				}
			}

			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				if(player == 0 ){
					users.setText(""+score);
					users.setVisibility(View.VISIBLE);
					users.startAnimation(Amscore);
				}
				else if(player == 1 ){
					c1s.setText(""+score);
					c1s.setVisibility(View.VISIBLE);
					c1s.startAnimation(Amscore);
				}
				else if(player == 2 ){
					c2s.setText(""+score);
					c2s.setVisibility(View.VISIBLE);
					c2s.startAnimation(Amscore);
				}
				else if(player == 3 ){
					c3s.setText(""+score);
					c3s.setVisibility(View.VISIBLE);
					c3s.startAnimation(Amscore);
				}
				else if(player == 4 ){
					c1s.setText(""+score);
					c2s.setText(""+score);
					c3s.setText(""+score);
					c1s.setVisibility(View.VISIBLE);
					c2s.setVisibility(View.VISIBLE);
					c3s.setVisibility(View.VISIBLE);
					c1s.startAnimation(Amscore);
					c2s.startAnimation(Amscore);
					c3s.startAnimation(Amscore);
				}
				else if(player == 5 ){
					users.setText(""+score);
					c2s.setText(""+score);
					c3s.setText(""+score);
					users.setVisibility(View.VISIBLE);
					c2s.setVisibility(View.VISIBLE);
					c3s.setVisibility(View.VISIBLE);
					users.startAnimation(Amscore);
					c2s.startAnimation(Amscore);
					c3s.startAnimation(Amscore);
				}
				else if(player == 6 ){
					c1s.setText(""+score);
					users.setText(""+score);
					c3s.setText(""+score);
					c1s.setVisibility(View.VISIBLE);
					users.setVisibility(View.VISIBLE);
					c3s.setVisibility(View.VISIBLE);
					c1s.startAnimation(Amscore);
					users.startAnimation(Amscore);
					c3s.startAnimation(Amscore);
				}
				else if(player == 7 ){
					c1s.setText(""+score);
					c2s.setText(""+score);
					users.setText(""+score);
					c1s.setVisibility(View.VISIBLE);
					c2s.setVisibility(View.VISIBLE);
					users.setVisibility(View.VISIBLE);
					c1s.startAnimation(Amscore);
					c2s.startAnimation(Amscore);
					users.startAnimation(Amscore);
				}
			}
			
		}.start();
	}

	/*public void loading(){
		//loading
		final ProgressDialog myDialog =  new ProgressDialog(this,R.style.CustomDialogStyle);
		myDialog.show();
	       new Thread()
	       { 
	         public void run()
	         { 
	           try
	           { 
	             sleep(3000);
	           }
	           catch (Exception e)
	           {
	             e.printStackTrace();
	           }
	           finally
	           {
	             myDialog.dismiss();    
	        		//dialog for entering name or start game
	        		if(rIndex == 0){
	        			inputName();}
	        		else{
	        			startGame();}
	           }
	         }
	       }.start(); //開始執行執行緒
	       
	 
	}*/

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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{   
		if (keyCode == KeyEvent.KEYCODE_BACK) 
		{   
			exitDialog();
		}   
		return false;   
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		return true;
	}

}
