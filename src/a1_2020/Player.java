package a1_2020;

import java.util.ArrayList;
public class Player {
	private String name;
	private int score = 0;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player()
	{
	
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setScore(int newScore)
	{
		score = newScore;
	}
	
	public int getScore()
	{	
		return score;
	}
	
	public ArrayList<Card> getHand()
	{
		return hand;
	}
	
	public void pickupCard(Card aCard)
	{
		hand.add(aCard);
	}
	
	public Card playCard(int cardIndex)
	{
		Card toPlay = hand.get(cardIndex);
		hand.remove(cardIndex);
		return toPlay;
	}
	
	public void clearHand()
	{
		hand.clear();
	}
	
	public int calculateValueOfHand()
	{
		int value = 0;
		
		for (int i = 0; i < hand.size(); i++)
		{
			value += hand.get(i).getValue();
		}
		
		return value;
	}
	
}
