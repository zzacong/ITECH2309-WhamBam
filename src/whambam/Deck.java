/**
 * 
 */
package whambam;

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
		return deck;
	}
	
	public Card peek() {
		return getDeck().peek();
	}
	
	public Card pop() {
		return getDeck().pop();
	}
	
	public Card addCardToDeck(Card card) {
		getDeck().add(card);
		return card;
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
		Card tempTopCard = null;
		if (getDeck().isEmpty()) {
			System.out.println("Replenish Game Deck");
			tempTopCard = deck.pop();
			getDeck().addAll(deck.getDeck());
			deck.clear();
			shuffle();
			deck.addCardToDeck(tempTopCard);
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
		for (String colour : Card.COLOURS) {
			if (colour == "White") {
				for (int i = 0; i < 4; i++ ) {
					addCardToDeck(new ActionCard(colour, 15));					
				}
				continue;
			}
			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					addCardToDeck(new Card(colour, 0));	// Number card Zero			
				}
				for (int j = 1; j < 11; j++) {
					Card card;
					if (j <= 9) {
						card = new Card(colour, j); // Number card 1-9					
					}
					else {
						card = new ActionCard(colour, j); // Wham! card
					}
					addCardToDeck(card);
				}
				
			}
		}
	}
	
	
	@Override
	public String toString() {
		return String.format("Deck size: %d\nCards:\n%s", size(), getAllCards());
	}
	
}
