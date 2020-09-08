/**
 * 
 */
package domain;

import java.util.Collections;
import java.util.Stack;

/**
 * @author Zac
 *
 */
public class Deck {
	
	private Stack<Card> deck;
	
	public Deck() {
		deck = new Stack<Card>();
	}
	
	private Stack<Card> getDeck() {
		return deck ;
	}
	
	public Card peek() {
		return getDeck().peek();
	}
	
	public Card pop() {
		return getDeck().pop();
	}
	
	public void addCardToDeck(Card card) {
		getDeck().add(card);
	}
	
	public void addAll(Deck otherDeck) {
		getDeck().addAll(otherDeck.getDeck());
	}
	
	public void clear() {
		getDeck().clear();
	}
	
	public void shuffle() {
		Collections.shuffle(getDeck());
	}
	
	public void checkDeckSize(Deck deck) {
		Stack<Card> otherDeck = deck.getDeck();
		Card tempTopCard = null;
		if (getDeck().isEmpty()) {
			tempTopCard = otherDeck.pop();
			getDeck().addAll(otherDeck);
			otherDeck.clear();
			this.shuffle();
			otherDeck.add(tempTopCard);
		}		
	}
	
	public int size() {
		return getDeck().size();
	}
	
	public String getAllCards() {
		String output = "";
		for (Card card : getDeck()) {
			output += card.toString() + "\n";
		}
		return output;
	}
	
	public void createCards() {
		boolean hasZero = false;
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 11; i++) {
				if (i == 0 && hasZero) {
					continue;
				}
				if (i == 0) {
					hasZero = true;
				}
				addCardToDeck(new Card("Blue", i));
				addCardToDeck(new Card("Green", i));
				addCardToDeck(new Card("Purple", i));
				addCardToDeck(new Card("Orange", i));
			}
		}
		System.out.println(toString());
	}
	
	@Override
	public String toString() {
		return String.format("Deck size: %d\nCards:\n%s", size(), getAllCards());
	}
	
}
