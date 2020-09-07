package operation;

import java.util.Stack;

import domain.Card;
import domain.Deck;
import domain.Player;

import java.util.Scanner;
import java.util.Collections;

public class GameEngine {
	private static int WIN_SCORE = 300;
	private static int STARTING_CARD_COUNT = 5;
	private Deck gameDeck = new Deck();
	private Deck inPlayDeck = new Deck();
	private Player player1 = new Player();
	private Player player2 = new Player();
	private Player currentPlayer = null;
	private Scanner userInput = new Scanner(System.in);

	public GameEngine() {

	}

	public void play() {
		System.out.println("Welcome to Wham Bam!");
		createPlayers();

		do {
			System.out.println("*** Starting new round ***");
			newRound();
			System.out.println("*** Round OVER ***");
			
			scoreGame();
		}
		while (player1.getScore() < WIN_SCORE && player2.getScore() < WIN_SCORE);

		// Current player is losing player.
		if (currentPlayer.equals(player1)) {
			System.out.println("Congratulations " + player2.getName() + ".  You WIN!");
		}
		else {
			System.out.println("Congratulations " + player1.getName() + ".  You WIN!");
		}
	
	}

	private void newRound() {
		String chosenCardName = null;
		Card chosenCard = null;

		boolean roundComplete = false;
		boolean validChoice = false;
		boolean errorOccurred = false;

		createCards();
		shuffle();
		currentPlayer = selectStartingPlayer();
		gameDeck.clear();
		createCards();
		shuffle();
		deal();
		inPlayDeck.add(gameDeck.pop());

		while (!roundComplete) {
			// if the deck is empty, replenish it
			checkGameDeckSize();

			// show the top card and current player
			System.out.println("Top Card in Play: " + inPlayDeck.peek());
			System.out.println("Current Player: " + currentPlayer.getName());

			// handle Wham! card first if needed
			if (inPlayDeck.peek().getName().contains("Wham!")) {
				System.out.println("Wham Card Active!  Penalty: pick up 3 cards");
				// Make sure we can pick up cards
				for (int i = 0; i < 3; i++) {
					checkGameDeckSize();
					currentPlayer.pickupCard(gameDeck.pop());
				}
				checkGameDeckSize();
				inPlayDeck.add(gameDeck.pop());
			}
			else {
				// General number card on top.
				validChoice = false;
				errorOccurred = false;
				chosenCard = null;

				while (!validChoice) {
					System.out.println("Cards in hand: ");
					System.out.println(currentPlayer.getHand().toString());
					System.out.println("Select a card to play (N to pick up from deck): ");

					chosenCardName = userInput.nextLine();

					if (chosenCardName.equals("N")) {
						// choosing to pick up.  Needs to not have a valid card to play in hand.
						for (int i = 0; i < currentPlayer.getHand().size(); i++) {
							if (currentPlayer.getHand().get(i).matchCard(inPlayDeck.peek())) {
								System.out.println("Unable to pickup card: you have at least one valid card to play in your hand.");
								errorOccurred = true;
								break;
							}
						}
						if (!errorOccurred) {
							// make sure there are cards to pick up.
							checkGameDeckSize();

							// pick up card and end turn.
							currentPlayer.pickupCard(gameDeck.pop());

							validChoice = true;
						}
					}
					else {
						// player has selected a card.  Search hand for their selection.
						chosenCard = currentPlayer.checkHand(chosenCardName);
						if (chosenCard != null) {
							// card has been found in the player's hand.
							if (chosenCard.matchCard(inPlayDeck.peek())) {
								//	valid choice to play card
								inPlayDeck.add(currentPlayer.playCard(chosenCard));
								validChoice = true;
							}
							else {
								System.out.println("Invalid card selection.  Must match colour or number of top card in deck.  Please retry.");
							}
						}
					}
				}
			}

			// see if player has emptied hand - if so, the round is over
			if (currentPlayer.getHand().size() == 0) {
				roundComplete = true;

			}

			// switch players for next turn, or so losing player is current player
			if (currentPlayer.equals(player1)) {
				currentPlayer = player2;
			}
			else {
				currentPlayer = player1;
			}
		}
	}

	// Delegate
	private void shuffle() {
		gameDeck.shuffle();
	}

	private void deal() {
		player1.clearHand();
		player2.clearHand();

		for (int i = 0; i < STARTING_CARD_COUNT; i++) {
			player1.pickupCard(gameDeck.pop());
			player2.pickupCard(gameDeck.pop());
		}
	}

	private Player selectStartingPlayer() {
		boolean startingPlayerSelected = false;
		Card p1card;
		Card p2card;
		Player startingPlayer = null;

		while (!startingPlayerSelected) {
			p1card = gameDeck.pop();
			p2card = gameDeck.pop();

			if (p1card.getValue() > p2card.getValue()) {
				startingPlayer = player1;
				startingPlayerSelected = true;
			}
			else if (p2card.getValue() > p1card.getValue()) {
				startingPlayer = player2;
				startingPlayerSelected = true;
			}
		}
		return startingPlayer;
	}

	private void scoreGame() {
		// currentPlayer is losing player
		int handValue = currentPlayer.calculateValueOfHand();
		System.out.println("Remaining hand value for " + currentPlayer.getName() + ": " + handValue);

		// add score to winning player's hand
		if (currentPlayer.equals(player1)) {
			player2.setScore(player2.getScore() + handValue);
		}
		else {
			player1.setScore(player1.getScore() + handValue);
		}

		System.out.println("Scores at end of round:");
		System.out.println(player1.getName() + ": " + player1.getScore());
		System.out.println(player2.getName() + ": " + player2.getScore());
	}

	private void createCards() {
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
		System.out.println(gameDeck.toString());
	}

	private void addCardToDeck(Card theCard) {
		gameDeck.add(theCard);
	}

	private void createPlayers() {
		System.out.println("Enter Player 1 name: ");
		player1.setName(userInput.nextLine());
		System.out.println("Enter Player 2 name: ");
		player2.setName(userInput.nextLine());		
	}
	
	// Extract
	private void checkGameDeckSize() {
		gameDeck.checkDeckSize(inPlayDeck);
	}

}
