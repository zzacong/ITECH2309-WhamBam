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
	private String name;

	public Deck(String name) {
		deck = new Stack<Card>();
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public void addAll(Deck otherDeck) {
		getDeck().addAll(otherDeck.getDeck());
	}

	public void shuffle() {
		// System.out.println("Top 10 cards before shuffle: \n" + getTopTenCards());
		System.out.println(String.format("Shuffling %s ...\n", getName()));
		Collections.shuffle(getDeck());
		// System.out.println("Top 10 cards after shuffle: \n" + getTopTenCards());
	}

	public int size() {
		return getDeck().size();
	}

	public void clear() {
		getDeck().clear();
	}

	public Card addCardToDeck(Card card) {
		getDeck().add(card);
		return card;
	}

	public void checkDeckSize(Deck deck) {
		Card tempTopCard = null;
		if (getDeck().isEmpty()) {
			System.out.println("\nReplenish " + getName());
			tempTopCard = deck.pop();
			getDeck().addAll(deck.getDeck());
			deck.clear();
			shuffle();
			deck.addCardToDeck(tempTopCard);
			System.out.println(String.format("Done replenishing. %s has %d cards", getName(), size()));
		}
	}

	public void createCards() {
		for (String colour : Card.COLOURS) {
			if (colour == "White") {
				for (int i = 0; i < 4; i++) {
					addCardToDeck(new ActionCard(colour, 15));
				}
				continue;
			}
			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					addCardToDeck(new Card(colour, 0)); // Number card Zero
				}
				for (int j = 1; j < 11; j++) {
					Card card;
					if (j <= 9) {
						card = new Card(colour, j); // Number card 1-9
					} else {
						card = new ActionCard(colour, j); // Wham! card
					}
					addCardToDeck(card);
				}
			}
		}
		System.out.println(String.format("%s - created and added %d cards", getName(), size()));
	}

	public String getTopTenCards() {
		String output = "";
		for (int i = 0; i < 10; i++) {
			output += getDeck().get(i).toString() + "\n";
		}
		return output;
	}

	@Override
	public String toString() {
		return String.format("%s size: %d", getName(), size());
	}

}
