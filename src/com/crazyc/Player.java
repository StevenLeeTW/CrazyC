package com.crazyc;

public class Player {
	
	public int hand [];
	
	//��V�ثe��P��;
	int pointer;
	int score ;

	public Player()
	{	
		hand = new int[6];
		pointer = -1;
		score = 1000;
	}
	public void play( int playCardIndex)
	{			
		Rule.deck.recycleArr[++Rule.deck.recyclePointer] = hand[playCardIndex];
				
		//做排組整理 若陣列中間的牌已經出了 就把後面的卡往前排
		if(pointer== playCardIndex)
			hand[pointer]=111;
		else
		{
			for(int i = playCardIndex; i< pointer ; i++)
			{
				hand[i] = hand[i+1];
			}
			hand[pointer] = 111;
		}
		pointer--;
		
	}
	public void addhandCard(int card)
	{
		if(pointer < 6)
			hand[++pointer] = card;
	}
	
	public int scoreBox(int plusOrMinus)
	{
		return score += plusOrMinus;		
	}
}
