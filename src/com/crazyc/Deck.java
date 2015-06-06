package com.crazyc;
import java.util.Random;

/*
 * �ĪG�d+���q�d�� 36+40 �� 76 �i
 * �ҥHdeckArr�j�p��76(�s����0~75)
 * �{�b�p�G�令��eCard�MnCard�}�C�X�b�@�_
 * �ͥX�@��standardCardArray(stdArr)
 * �Ӧ��}�Cindex�P�}�C���e���X�]���@�P
 * �s���e40(0~39)�����q�d
 * �s����36(40~75)���ĪG�d
 * 
 * */
import android.graphics.Bitmap;



public class Deck {
	Bitmap temp;
	int currentDeck;
	int recyclePointer;
	int [] deckArr ;
	int [] usedCard;
	int [] recycleArr;
	
	public Deck( int[] cards)
	{			
		//�ĪG�d��[�J�����ĪG�d
		//40 41 42 43 �U�a��10
		//44 45 46 47 �U�a��20
		//48 49 50 ���馩10
		//51 52 53 ���馩20
		deckArr = new int[70];	
		
		//�O��P��W���P
		recycleArr = new int [cards.length];
		
		//��l�ƵP��}�C
		for(int i =0; i < recycleArr.length; i++)
		{
			recycleArr[i] = 111;
		}
		
		currentDeck = cards.length -1;		
		deckArr =cards;
		shuffle(deckArr);
		recyclePointer = -1;
	}
	
	public static int point(int num)
	{
		if(num>39)
		{
			//�ĪG�d��k
			return num;
		}
		else
		{
			int p = num/4;
			return p;
		}
	}
	
	public static String suit(int num)
	{
		if(num >39)
		{
			return String.format("%d", num);
		}
		else
		{
			switch(num%4){
			case 0 : return "green";
			case 1 : return "red";
			case 2 : return "yellow";
			default : return "blue";			
			}		
		}
	}
	
	public void shuffle(int [] arr)
	{
		Random random = new Random();
		int c = currentDeck;
		int temp;
		for(int i =0; i <= c; i++)
		{
			int x = random.nextInt(c);
			temp = arr[i];
			arr[i] = arr[x];
			arr[x] = temp;
		}
	}
	
	public void deal(Player player)
	{
		player.addhandCard(deckArr[currentDeck--]);	
		deckArr[currentDeck+1] = 111;
	}
	
	public void recycleMerge()
	{
		int temp0 = recycleArr[recyclePointer];
		int temp = currentDeck;
		
		for(int i = currentDeck +1; i <= recyclePointer; i++)
		{
			deckArr[i] = recycleArr[i - (currentDeck +1)];
			temp++;
		}
		for(int i = 0; i < recycleArr.length; i ++)
			recycleArr[i] = 111;
		
		recycleArr[0] = temp0;
		recyclePointer = 0;
		currentDeck = temp;
		shuffle(Rule.deck.deckArr);
	}	
}
