package a1_2020;

import java.util.Stack;
import java.util.Scanner;
import java.util.Collections;

public class GameEngine {
	private static int WIN_SCORE = 300;
	private static int STARTING_CARD_COUNT = 5;
	private Stack<Card> gameDeck = new Stack<Card>();
	private Stack<Card> inPlayDeck = new Stack<Card>();
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
		Card tempTopCard = null;
		String chosenCardName = null;
		Card chosenCard = null;
		int playerCardIndex = 0;

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
			if (gameDeck.size() == 0) {
				tempTopCard = inPlayDeck.pop();
				for (int i = 0; i < inPlayDeck.size(); i++) {
					gameDeck.add(inPlayDeck.pop());
				}
				shuffle();
				inPlayDeck.add(tempTopCard);
			}

			// show the top card and current player
			System.out.println("Top Card in Play: " + inPlayDeck.peek());
			System.out.println("Current Player: " + currentPlayer.getName());

			// handle Wham! card first if needed
			if (inPlayDeck.peek().getName().contains("Wham!")) {
				System.out.println("Wham Card Active!  Penalty: pick up 3 cards");
				// Make sure we can pick up cards
				for (int i = 0; i < 3; i++) {
					if (gameDeck.size() == 0) {
						tempTopCard = inPlayDeck.pop();
						for (int j = 0; j < inPlayDeck.size(); j++) {
							gameDeck.add(inPlayDeck.pop());
						}
						shuffle();
						inPlayDeck.add(tempTopCard);
					}
					currentPlayer.pickupCard(gameDeck.pop());
				}
				if (gameDeck.size() == 0) {
					tempTopCard = inPlayDeck.pop();
					for (int j = 0; j < inPlayDeck.size(); j++) {
						gameDeck.add(inPlayDeck.pop());
					}
					shuffle();
					inPlayDeck.add(tempTopCard);
				}
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
							if (currentPlayer.getHand().get(i).getValue() == (inPlayDeck.peek().getValue()) || currentPlayer.getHand().get(i).getColour().equals(inPlayDeck.peek().getColour())) {
								System.out.println("Unable to pickup card: you have at least one valid card to play in your hand.");
								i = currentPlayer.getHand().size();
								errorOccurred = true;
							}
						}
						if (!errorOccurred) {
							// make sure there are cards to pick up.
							if (gameDeck.size() == 0) {
								tempTopCard = inPlayDeck.pop();
								for (int j = 0; j < inPlayDeck.size(); j++) {
									gameDeck.add(inPlayDeck.pop());
								}
								shuffle();
								inPlayDeck.add(tempTopCard);
							}

							// pick up card and end turn.
							currentPlayer.pickupCard(gameDeck.pop());

							validChoice = true;
						}
					}
					else {
						// player has selected a card.  Search hand for their selection.
						for (int i = 0; i < currentPlayer.getHand().size(); i++) {
							if (currentPlayer.getHand().get(i).getName().equals(chosenCardName)) {
								chosenCard = currentPlayer.getHand().get(i);
								playerCardIndex = i;
								i = currentPlayer.getHand().size();
							}
						}
						if (chosenCard == null) {
							System.out.println("Unable to locate card.  Please make sure you match the card name listed");
						}
						else {
							// card has been found in the player's hand.
							if (chosenCard.getValue() == inPlayDeck.peek().getValue() || chosenCard.getColour() == inPlayDeck.peek().getColour()) {
								//	valid choice to play card
								inPlayDeck.add(currentPlayer.playCard(playerCardIndex));
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

	private void shuffle() {
		Collections.shuffle(gameDeck);
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
		addCardToDeck(new Card("Blue Zero", "Blue", 0));
		addCardToDeck(new Card ("Blue One", "Blue", 1));
		addCardToDeck(new Card ("Blue Two", "Blue", 2));
		addCardToDeck(new Card ("Blue Three", "Blue", 3));
		addCardToDeck(new Card ("Blue Four", "Blue", 4));
		addCardToDeck(new Card ("Blue Five", "Blue", 5));
		addCardToDeck(new Card ("Blue Six", "Blue", 6));
		addCardToDeck(new Card ("Blue Seven", "Blue", 7));
		addCardToDeck(new Card ("Blue Eight", "Blue", 8));
		addCardToDeck(new Card ("Blue Nine", "Blue", 9));
		addCardToDeck(new Card ("Blue One", "Blue", 1));
		addCardToDeck(new Card ("Blue Two", "Blue", 2));
		addCardToDeck(new Card ("Blue Three", "Blue", 3));
		addCardToDeck(new Card ("Blue Four", "Blue", 4));
		addCardToDeck(new Card ("Blue Five", "Blue", 5));
		addCardToDeck(new Card ("Blue Six", "Blue", 6));
		addCardToDeck(new Card ("Blue Seven", "Blue", 7));
		addCardToDeck(new Card ("Blue Eight", "Blue", 8));
		addCardToDeck(new Card ("Blue Nine", "Blue", 9));
		addCardToDeck(new Card("Green Zero", "Green", 0));
		addCardToDeck(new Card ("Green One", "Green", 1));
		addCardToDeck(new Card ("Green Two", "Green", 2));
		addCardToDeck(new Card ("Green Three", "Green", 3));
		addCardToDeck(new Card ("Green Four", "Green", 4));
		addCardToDeck(new Card ("Green Five", "Green", 5));
		addCardToDeck(new Card ("Green Six", "Green", 6));
		addCardToDeck(new Card ("Green Seven", "Green", 7));
		addCardToDeck(new Card ("Green Eight", "Green", 8));
		addCardToDeck(new Card ("Green Nine", "Green", 9));
		addCardToDeck(new Card ("Green One", "Green", 1));
		addCardToDeck(new Card ("Green Two", "Green", 2));
		addCardToDeck(new Card ("Green Three", "Green", 3));
		addCardToDeck(new Card ("Green Four", "Green", 4));
		addCardToDeck(new Card ("Green Five", "Green", 5));
		addCardToDeck(new Card ("Green Six", "Green", 6));
		addCardToDeck(new Card ("Green Seven", "Green", 7));
		addCardToDeck(new Card ("Green Eight", "Green", 8));
		addCardToDeck(new Card ("Green Nine", "Green", 9));
		addCardToDeck(new Card ("Purple Zero", "Purple", 0));
		addCardToDeck(new Card ("Purple One", "Purple", 1));
		addCardToDeck(new Card ("Purple Two", "Purple", 2));
		addCardToDeck(new Card ("Purple Three", "Purple", 3));
		addCardToDeck(new Card ("Purple Four", "Purple", 4));
		addCardToDeck(new Card ("Purple Five", "Purple", 5));
		addCardToDeck(new Card ("Purple Six", "Purple", 6));
		addCardToDeck(new Card ("Purple Seven", "Purple", 7));
		addCardToDeck(new Card ("Purple Eight", "Purple", 8));
		addCardToDeck(new Card ("Purple Nine", "Purple", 9));
		addCardToDeck(new Card ("Purple One", "Purple", 1));
		addCardToDeck(new Card ("Purple Two", "Purple", 2));
		addCardToDeck(new Card ("Purple Three", "Purple", 3));
		addCardToDeck(new Card ("Purple Four", "Purple", 4));
		addCardToDeck(new Card ("Purple Five", "Purple", 5));
		addCardToDeck(new Card ("Purple Six", "Purple", 6));
		addCardToDeck(new Card ("Purple Seven", "Purple", 7));
		addCardToDeck(new Card ("Purple Eight", "Purple", 8));
		addCardToDeck(new Card ("Purple Nine", "Purple", 9));
		addCardToDeck(new Card ("Purple Zero", "Purple", 0));
		addCardToDeck(new Card ("Orange One", "Orange", 1));
		addCardToDeck(new Card ("Orange Two", "Orange", 2));
		addCardToDeck(new Card ("Orange Three", "Orange", 3));
		addCardToDeck(new Card ("Orange Four", "Orange", 4));
		addCardToDeck(new Card ("Orange Five", "Orange", 5));
		addCardToDeck(new Card ("Orange Six", "Orange", 6));
		addCardToDeck(new Card ("Orange Seven", "Orange", 7));
		addCardToDeck(new Card ("Orange Eight", "Orange", 8));
		addCardToDeck(new Card ("Orange Nine", "Orange", 9));
		addCardToDeck(new Card ("Orange One", "Orange", 1));
		addCardToDeck(new Card ("Orange Two", "Orange", 2));
		addCardToDeck(new Card ("Orange Three", "Orange", 3));
		addCardToDeck(new Card ("Orange Four", "Orange", 4));
		addCardToDeck(new Card ("Orange Five", "Orange", 5));
		addCardToDeck(new Card ("Orange Six", "Orange", 6));
		addCardToDeck(new Card ("Orange Seven", "Orange", 7));
		addCardToDeck(new Card ("Orange Eight", "Orange", 8));
		addCardToDeck(new Card ("Orange Nine", "Orange", 9));
		// action cards
		addCardToDeck(new Card ("Blue Wham!", "Blue", 10));
		addCardToDeck(new Card ("Blue Wham!", "Blue", 10));
		addCardToDeck(new Card ("Green Wham!", "Green", 10));
		addCardToDeck(new Card ("Green Wham!", "Green", 10));
		addCardToDeck(new Card ("Purple Wham!", "Purple", 10));
		addCardToDeck(new Card ("Purple Wham!", "Purple", 10));
		addCardToDeck(new Card ("Orange Wham!", "Orange", 10));
		addCardToDeck(new Card ("Orange Wham!", "Orange", 10));
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

}
