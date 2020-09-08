package domain;

import java.util.ArrayList;
public class Player {
	private String name;
	private int score = 0;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	public Player(String name) {
		this.setName(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setScore(int newScore) {
		score = newScore;
	}
	
	public int getScore() {	
		return score;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %d", getName(), getScore());
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void pickupCard(Card aCard) {
		hand.add(aCard);
	}
	
	public Card playCard(Card chosenCard) {
		hand.remove(chosenCard);
		return chosenCard;
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	public int calculateValueOfHand() {
		int value = 0;
		for (Card card : getHand()) {
			value += card.getValue();
		}
		return value;
	}
	
	public boolean cardInHand(String card) {
		for (Card handCard : getHand()) {
			if (handCard.getName().equals(card)) 
				return true;
		}
		return false;
	}
	
	public Card checkHand(String card) {
		ArrayList<Card> hand = getHand();
		for (int i = 0; i < hand.size(); i++) {
			Card handCard = hand.get(i);
			if (handCard.getName().equals(card)) {
				return handCard;
			}
		}
		System.out.println("Unable to locate card.  Please make sure you match the card name listed");
		return null;
	}
	
}
