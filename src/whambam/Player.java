package whambam;

import java.util.ArrayList;
public class Player {
	private String name;
	private int score = 0;
	private ArrayList<Card> hand = new ArrayList<Card>();
	private Card chosenCard;
	
	public Player(String name) {
		this.setName(name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore() {	
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public Card getChosenCard() {
		return chosenCard;
	}

	public void setChosenCard(Card chosenCard) {
		this.chosenCard = chosenCard;
	}
	
	private ArrayList<Card> getHand() {
		return hand;
	}
	
	public boolean isEmptyHand() {
		return getHand().isEmpty();
	}
	
	public int getHandSize() {
		return getHand().size();
	}
	
	public String printHand() {
		return String.format("Cards in hand: \n%s", getHand().toString());
	}
	
	public void clearHand() {
		getHand().clear();
	}
	
	public void pickupCard(Card aCard) {
		getHand().add(aCard);
	}
	
	public Card playCard(Card chosenCard) {
		getHand().remove(chosenCard);
		return chosenCard;
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
			if (handCard.getName().equalsIgnoreCase(card)) {
				setChosenCard(handCard);
				return true;				
			}
		}
		System.out.println("Unable to locate card.  Please make sure you match the card name listed");
		return false;
	}
	
	public boolean cardInHand(Card card) {
		for (Card handCard : getHand()) {
			if (handCard.match(card)) {
				setChosenCard(handCard);
				return true;				
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %d", getName(), getScore());
	}
	
}
