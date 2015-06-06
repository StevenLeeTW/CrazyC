package com.crazyc;

//�Ĩ�n����ӨM�w�ӭt ���w��W�P�Ƭ�6 ����
public class Rule {
	int cards[];
	static Deck  deck;
	private static Game game;
	static Player user ;
	static Player com1 ;
	static Player com2 ;
	static Player com3 ;
	
	public Rule(Player u, Player c1, Player c2, Player c3)
	{
		//cards�}�C����Activity�̪�Bitmap�}�C
		//cards = new int[76];
		//temp cards
		cards = new int[70];
		Rule.user = u;
		Rule.com1 = c1;
		Rule.com2 = c2;
		Rule.com3 = c3;
		
		for(int i = 0; i < cards.length; i++)
		{
			cards[i] = i;
		}
		deck = new Deck(cards);	
	
		
		for(int i = 0; i < 6; i++)
		{
			deck.deal(user);
			deck.deal(com1);
			deck.deal(com2);
			deck.deal(com3);
		}
		
	}
	public int HardAI(Player com, Player next, Player opposite, Player last)
	{
		int cardIndex = -1;
		int top = deck.recycleArr[deck.recyclePointer];
		String topSuit = Deck.suit(top); 
		int topPoints = Deck.point(top);
		
		for(int i =0 ; i< com.hand.length; i++)
		{
			if(next.equals(user))
			{
				if(com.hand[i] > 39 && com.hand[i] <58)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i] > 61 && com.hand[i] < 66)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i] > 57 && com.hand[i] < 62 && topPoints >39 && topPoints<54)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i]>65 && com.hand[i] < 68)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i]>67 && com.hand[i] < 70 && topPoints >39 && topPoints<54)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if((Deck.suit(com.hand[i]).equals(topSuit)&& topPoints < 40)|| 
						(!Deck.suit(com.hand[i]).equals(topSuit)&& com.hand[i] < 40&&topPoints > 39 ))
				{
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if((Deck.point(com.hand[i]) == topPoints && topPoints < 40) || 
						(Deck.point(com.hand[i]) != topPoints &&com.hand[i] < 40&& topPoints > 39))
				{
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(i == com.hand.length-1)
				{		
					com.scoreBox(-10);
					cardIndex = -1;
					/*if(com == com1){
					game.ScoreAnim(1,-10);
					}	
					else if(com == com2){
					game.ScoreAnim(2,-10);
					}	
					else if(com == com3){
					game.ScoreAnim(3,-10);
					}	*/
				}
			}			
			else
			{
				if((Deck.suit(com.hand[i]).equals(topSuit)&& topPoints < 40)|| 
						(!Deck.suit(com.hand[i]).equals(topSuit)&& com.hand[i] < 40&&topPoints > 39 ))
				{
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if((Deck.point(com.hand[i]) == topPoints && topPoints < 40) || 
						(Deck.point(com.hand[i]) != topPoints &&com.hand[i] < 40&& topPoints > 39))
				{
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				//�Ȯɳ]�p���������q
				else if(com.hand[i] > 39 && com.hand[i] <58)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i] > 57 && com.hand[i] < 62 && topPoints >39 && topPoints<54)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i] > 61 && com.hand[i] < 66)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i]>65 && com.hand[i] < 68)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				else if(com.hand[i]>67 && com.hand[i] < 70 && topPoints >39 && topPoints<54)
				{
					effectCard(com.hand[i],com, next, opposite, last, topPoints);
					cardIndex = com.hand[i];
					com.play(i);
					break;
				}
				//for迴圈最後一次
				else if(i == com.hand.length-1)
				{		
					com.scoreBox(-10);
					cardIndex = -1;
				/*	if(com == com1){
					game.ScoreAnim(1,-10);
					}	
					else if(com == com2){
					game.ScoreAnim(2,-10);
					}	
					else if(com == com3){
					game.ScoreAnim(3,-10);
					}*/
				}
			}			
		}
		return cardIndex;
	}

	//�ѳW�h���O����C���D��  comAI ��k�Φb���q���X�P�ɩI�s�A�P�_�X�P
	//��T�̰Ѽ� ���U�@�a   ��a  �W�@�a
	public int easyAI(Player com, Player next, Player opposite, Player last)
	{
		int cardIndex = -1;
		int top = deck.recycleArr[deck.recyclePointer];
		String topSuit = Deck.suit(top); 
		int topPoints = Deck.point(top);
		
		//com�X�P�W�h
		for(int i = 0; i < com.hand.length; i++)
		{
			//�i�Ҽ{�֥[�ĪG  �Ϋe�̧P�_�Y�I�ƪ��^�Ǥj��40�B�ۦP �h���Q����
			if((Deck.suit(com.hand[i]).equals(topSuit)&& topPoints < 40)|| 
					(!Deck.suit(com.hand[i]).equals(topSuit)&& com.hand[i] < 40&&topPoints > 39 ))
			{
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			else if((Deck.point(com.hand[i]) == topPoints && topPoints < 40) || 
					(Deck.point(com.hand[i]) != topPoints &&com.hand[i] < 40&& topPoints > 39))
			{
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			//�Ȯɳ]�p���������q
			else if(com.hand[i] > 39 && com.hand[i] <58)
			{
				effectCard(com.hand[i],com, next, opposite, last, topPoints);
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			else if(com.hand[i] > 57 && com.hand[i] < 62 && topPoints >39 && topPoints<54)
			{
				effectCard(com.hand[i],com, next, opposite, last, topPoints);
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			else if(com.hand[i] > 61 && com.hand[i] < 66)
			{
				effectCard(com.hand[i],com, next, opposite, last, topPoints);
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			else if(com.hand[i]>65 && com.hand[i] < 68)
			{
				effectCard(com.hand[i],com, next, opposite, last, topPoints);
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			else if(com.hand[i]>67 && com.hand[i] < 70 && topPoints >39 && topPoints<54)
			{
				effectCard(com.hand[i],com, next, opposite, last, topPoints);
				cardIndex = com.hand[i];
				com.play(i);
				break;
			}
			//for迴圈最後一次
			else if(i == com.hand.length-1)
			{		
				com.scoreBox(-10);
				cardIndex = -1;
				/*if(com == com1){
				game.ScoreAnim(1,-10);
				}	
				else if(com == com2){
				game.ScoreAnim(2,-10);
				}	
				else if(com == com3){
				game.ScoreAnim(3,-10);
				}*/
			}
		}
		return cardIndex;
	}

	public static void effectCard(int ci,Player com, Player next,
			Player opposite , Player last,int top)//ci => cardIndex
	{
		if(ci < 44)
		{
			next.scoreBox(-50);
			/*if(com == com1 && next == user){
			game.ScoreAnim(0,-50);
			}	
			else if(com == com1 && next ==com2){
			game.ScoreAnim(2,-50);
			}	
			else if(com == com2 && next ==com1){
			game.ScoreAnim(1,-50);
			}	
			else if(com == com2 && next ==com3){
			game.ScoreAnim(3,-50);
			}	
			else if(com == com3 && next ==com2){
			game.ScoreAnim(2,-50);
			}	
			else if(com == com3 && next ==user){
			game.ScoreAnim(2,-50);
			}	*/
		}
		else if(ci < 48)
		{
			next.scoreBox(-80);
			/*if(com == com1 && next == user){
			game.ScoreAnim(0,-80);
			}	
			else if(com == com1 && next ==com2){
			game.ScoreAnim(2,-80);
			}	
			else if(com == com2 && next ==com1){
			game.ScoreAnim(1,-80);
			}	
			else if(com == com2 && next ==com3){
			game.ScoreAnim(3,-80);
			}	
			else if(com == com3 && next ==com2){
			game.ScoreAnim(2,-80);
			}	
			else if(com == com3 && next ==user){
			game.ScoreAnim(2,-80);
			}	*/
			
		}
		//之後修改為防禦卡
		else if(ci < 52)
		{
			next.scoreBox(-35);
			opposite.scoreBox(-35);
			last.scoreBox(-35);
			/*if(com == com1 ){
			game.ScoreAnim(5,-35);
			}	
			else if(com == com2 ){
			game.ScoreAnim(6,-35);
			}	
			else if(com == com3 ){
			game.ScoreAnim(7,-35);
			}	*/
		}
		else if( ci < 54)
		{
			next.scoreBox(-65);
			opposite.scoreBox(-65);
			last.scoreBox(-65);
			/*if(com == com1 ){
			game.ScoreAnim(5,-65);
			}	
			else if(com == com2 ){
			game.ScoreAnim(6,-65);
			}	
			else if(com == com3 ){
			game.ScoreAnim(7,-65);
			}	*/
		}
		//reverse
		else if(ci < 58)
		{
			//迴轉效果由Game.java控制
		}
		else if(ci < 62)
		{
			if(top > 39 && top < 44)
			{
				com.scoreBox(50);
				/*if(com == com1 ){
					game.ScoreAnim(1,+50);
					}	
					else if(com == com2 ){
					game.ScoreAnim(2,+50);
					}	
					else if(com == com3 ){
					game.ScoreAnim(3,+50);
					}	*/
			}
			else if(top > 43 && top < 48)
			{
				com.scoreBox(80);
				/*if(com == com1 ){
					game.ScoreAnim(1,+80);
					}	
					else if(com == com2 ){
					game.ScoreAnim(2,+80);
					}	
					else if(com == com3 ){
					game.ScoreAnim(3,+80);
					}	*/
			}
			else if(top > 47 && top < 52)
			{
				com.scoreBox(35);
				/*if(com == com1 ){
					game.ScoreAnim(1,+35);
					}	
					else if(com == com2 ){
					game.ScoreAnim(2,+35);
					}	
					else if(com == com3 ){
					game.ScoreAnim(3,+35);
					}	*/
			}
			else if(top > 51 && top < 54)
			{
				com.scoreBox(65);
				/*if(com == com1 ){
					game.ScoreAnim(1,+65);
					}	
					else if(com == com2 ){
					game.ScoreAnim(2,+65);
					}	
					else if(com == com3 ){
					game.ScoreAnim(3,+65);
					}	*/
			}
		}
		else if(ci < 66)
		{
			//由Game.java控制
		}
		else if(ci < 68)
		{
			com.scoreBox(100);
			/*if(com == com1 ){
				game.ScoreAnim(1,+100);
				}	
				else if(com == com2 ){
				game.ScoreAnim(2,+100);
				}	
				else if(com == com3 ){
				game.ScoreAnim(3,+100);
				}	*/
		}
		else if(ci < 70)
		{
			if(top > 39 && top < 44)
			{
				com.scoreBox(50);
				last.scoreBox(-50);
				/*if(com == com1 && last == user){
					game.ScoreAnim(0,-50);
					game.ScoreAnim(1,+50);
					}	
					else if(com == com1 && last == com2){
						game.ScoreAnim(2,-50);
						game.ScoreAnim(1,+50);
						}	
					else if(com == com2 && last ==com1){
					game.ScoreAnim(2,+50);
					game.ScoreAnim(1,-50);
					}	
					else if(com == com2 && last ==com3){
					game.ScoreAnim(2,+50);
					game.ScoreAnim(3,-50);
					}	
					else if(com == com3 && last ==com2){
					game.ScoreAnim(3,+50);
					game.ScoreAnim(2,-50);
					}	
					else if(com == com3 && last ==user){
					game.ScoreAnim(3,+50);
					game.ScoreAnim(0,-50);
					}*/
			}
			else if(top > 43 && top < 48)
			{
				com.scoreBox(80);
				last.scoreBox(-80);
				/*if(com == com1 && last == user){
					game.ScoreAnim(0,-80);
					game.ScoreAnim(1,+80);
					}	
					else if(com == com1 && last == com2){
						game.ScoreAnim(2,-80);
						game.ScoreAnim(1,+80);
						}	
					else if(com == com2 && last ==com1){
					game.ScoreAnim(2,+80);
					game.ScoreAnim(1,-80);
					}	
					else if(com == com2 && last ==com3){
					game.ScoreAnim(2,+80);
					game.ScoreAnim(3,-80);
					}	
					else if(com == com3 && last ==com2){
					game.ScoreAnim(3,+80);
					game.ScoreAnim(2,-80);
					}	
					else if(com == com3 && last ==user){
					game.ScoreAnim(3,+80);
					game.ScoreAnim(0,-80);
					}*/
			}
			else if(top > 47 && top < 52)
			{
				com.scoreBox(35);
				last.scoreBox(-35);
				/*if(com == com1 && last == user){
					game.ScoreAnim(0,-35);
					game.ScoreAnim(1,+35);
					}	
					else if(com == com1 && last == com2){
						game.ScoreAnim(2,-35);
						game.ScoreAnim(1,+35);
						}	
					else if(com == com2 && last ==com1){
					game.ScoreAnim(2,+35);
					game.ScoreAnim(1,-35);
					}	
					else if(com == com2 && last ==com3){
					game.ScoreAnim(2,+35);
					game.ScoreAnim(3,-35);
					}	
					else if(com == com3 && last ==com2){
					game.ScoreAnim(3,+35);
					game.ScoreAnim(2,-35);
					}	
					else if(com == com3 && last ==user){
					game.ScoreAnim(3,+35);
					game.ScoreAnim(0,-35);
					}*/
			}
			else if(top > 51 && top < 54)
			{
				com.scoreBox(65);
				last.scoreBox(-65);
				/*if(com == com1 && last == user){
					game.ScoreAnim(0,-65);
					game.ScoreAnim(1,+65);
					}	
					else if(com == com1 && last == com2){
						game.ScoreAnim(2,-65);
						game.ScoreAnim(1,+65);
						}	
					else if(com == com2 && last ==com1){
					game.ScoreAnim(2,+65);
					game.ScoreAnim(1,-65);
					}	
					else if(com == com2 && last ==com3){
					game.ScoreAnim(2,+65);
					game.ScoreAnim(3,-65);
					}	
					else if(com == com3 && last ==com2){
					game.ScoreAnim(3,+65);
					game.ScoreAnim(2,-65);
					}	
					else if(com == com3 && last ==user){
					game.ScoreAnim(3,+65);
					game.ScoreAnim(0,-65);
					}*/
			}
		}
	}
}
