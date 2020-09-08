package operation;

import java.util.Stack;

import domain.Card;
import domain.Deck;
import domain.Player;
import domain.PlayerManager;

import java.util.Scanner;
import java.util.Collections;

public class GameEngine {
	private static int WIN_SCORE = 300;
	private static int STARTING_CARD_COUNT = 5;
	private Deck gameDeck = new Deck();
	private Deck inPlayDeck = new Deck();
	private PlayerManager playerM = new PlayerManager();
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
			System.out.println("\n\n*** Starting new round ***");
			newRound();
			System.out.println("*** Round OVER ***\n\n");
			
			scoreGame();
		}
		while (!playerM.hasWinningPlayer(WIN_SCORE));
		
		System.out.println("Congratulations " + playerM.getWinner().getName() + ".  You WIN!");
	
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
		inPlayDeck.addCardToDeck(gameDeck.pop());

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
				inPlayDeck.addCardToDeck(gameDeck.pop());
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
								inPlayDeck.addCardToDeck(currentPlayer.playCard(chosenCard));
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
			if (currentPlayer.getHand().isEmpty()) {
				playerM.setWinner(currentPlayer);
				roundComplete = true;

			}

			// switch players for next turn, or so losing player is current player
			currentPlayer = playerM.changePlayer();
		}
	}

	// Delegate
	private void shuffle() {
		gameDeck.shuffle();
	}
	
	// Delegate
	private void deal() {
		playerM.deal(STARTING_CARD_COUNT, gameDeck);
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
		playerM.scoreGame();
	}

	private void createCards() {
		gameDeck.createCards();
	}

	private void createPlayers() {
		System.out.println("Enter number of player: ");
		int numOfPlayer = userInput.nextInt();
		playerM.createPlayer(numOfPlayer);
	}
	
	// Extract
	private void checkGameDeckSize() {
		gameDeck.checkDeckSize(inPlayDeck);
	}

}
