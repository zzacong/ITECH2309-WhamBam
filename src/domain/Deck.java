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
public class Deck extends Stack<Card> {
//	private Stack<Card> deck = new Stack<Card>();
	
	public void addCardToDeck(Card card) {
		this.add(card);
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
	public void checkDeckSize(Deck otherDeck) {
		Card tempTopCard = null;
		if (this.isEmpty()) {
			tempTopCard = otherDeck.pop();
			this.addAll(otherDeck);
			otherDeck.clear();
			this.shuffle();
			otherDeck.add(tempTopCard);
		}		
	}
	
	public String getAllCards() {
		String output = "";
		for (Card card : this) {
			output += card.toString() + "\n";
		}
		return output;
	}
	
	@Override
	public String toString() {
		return String.format("Deck size: %d\nCards:\n%s", this.size(), getAllCards());
	}
	
}
