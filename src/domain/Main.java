package domain;

import java.util.ArrayList;
import java.util.Collections;

import operation.GameEngine;

public class Main {

	public static void main(String[] args) {
//		Card blueCard = new Card("Blue", 2);
//		Card greenCard = new Card("Green", 4);
//		
//		System.out.println(greenCard.toString());
//		
//		System.out.println(blueCard.matchCard(greenCard));
		
		
//		ArrayList<Integer> i = new ArrayList<Integer>();
//		
//		i.add(1);
//		i.add(4);
//		i.add(3);
//		i.add(4);
//		i.add(4);
//		i.add(3);
//		
//		System.out.println(Collections.frequency(i, Collections.max(i)));
//		
//		
//		System.out.println(formatCardName("green nine").length());
		
		
//		Deck gameDeck = new Deck();
//		Deck playDeck = new Deck();
//		playDeck.createCards();
//		playDeck.shuffle();
//		
//		gameDeck.checkDeckSize(playDeck);
//		
//		
//		System.out.println("Helo");
		
		GameEngine ge = new GameEngine();
		ge.currentPlayer = new Player("Tommy");
		System.out.println(ge.currentPlayer.printHand());

		ge.gameDeck.createCards();
		ge.gameDeck.shuffle();
		
		ge.inPlayDeck.addCardToDeck(new ActionCard("Blue", 10));
//		ge.gameDeck.addCardToDeck(new ActionCard("Purple", 9));
		System.out.println(ge.handleActionCard());
		System.out.println(ge.currentPlayer.printHand());
		System.out.println(ge.inPlayDeck.peek());
	}
	
	static String formatCardName(String input) {
		String[] arr = input.split(" ");	
		return String.join(" ", capitalised(arr[0]), capitalised(arr[1]));
	}
	
	static String capitalised(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1);
	}

}
