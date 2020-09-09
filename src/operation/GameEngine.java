package operation;

import domain.Actionable;
import domain.Card;
import domain.Deck;
import domain.Player;
import domain.PlayerManager;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class GameEngine {
	private static int WIN_SCORE = 20;
	private static int STARTING_CARD_COUNT = 5;
	private Deck gameDeck = new Deck();
	private Deck inPlayDeck = new Deck();
	private PlayerManager playerM = new PlayerManager();
	private Player currentPlayer = null;
	private Scanner userInput = new Scanner(System.in);

	public GameEngine() {

	}

	public void play() {
		System.out.println("Welcome to Wham Bam!\n");
		createPlayers();

		do {
			newRound();
			scoreGame();
		}
		while (!playerM.hasWinningPlayer(WIN_SCORE));
		
		System.out.println(String.format("\nCongratulations %s. You WIN!", playerM.getChampion().getName()));
	
	}

	private void newRound() {
		System.out.println("\n\n*** Starting new round ***");
		boolean roundComplete = false;
		createCards();
		currentPlayer = selectStartingPlayer();
		createCards();
		deal();
		inPlayDeck.addCardToDeck(gameDeck.pop());

		while (!roundComplete) {
			// if the deck is empty, replenish it
			checkGameDeckSize();

			// show the top card and current player
			System.out.println("\nTop Card in Play: " + inPlayDeck.peek());
			System.out.println("Current Player: " + currentPlayer.getName());
			
			// handle Wham! card first if needed
			if (inPlayDeck.peek() instanceof Actionable) {
				handleActionCard();
			}
			else {
				// General number card on top.
				handleNumberCard();
			}

			// see if player has emptied hand - if so, the round is over
			if (currentPlayer.isEmptyHand()) {
				playerM.setWinner(currentPlayer);
				roundComplete = true;
			}
			// switch players for next turn, or so losing player is current player
			currentPlayer = playerM.changePlayer();
		}

		System.out.println("\n*** Round OVER ***\n\n");
	}

	private void handleActionCard() {
		Actionable actionC = (Actionable) inPlayDeck.peek();
		actionC.penaltyMessage();
		// Make sure we can pick up cards
		for (int i = 0; i < actionC.cardPenalty(); i++) {
			System.out.println("pick 1 card");
//			checkGameDeckSize();
			currentPlayer.pickupCard(pickFromGameDeck());
		}
//		checkGameDeckSize();
		inPlayDeck.addCardToDeck(pickFromGameDeck());
	}
	
	private void handleNumberCard() {
		String chosenCardName = null;
		Card chosenCard = null;
		boolean validChoice = false;

		while (!validChoice) {
			System.out.println("Cards in hand: ");
			System.out.println(currentPlayer.printHand());
			System.out.println("Select a card to play (N to pick up from deck): ");

			chosenCardName = userInput.nextLine();

			if (chosenCardName.equalsIgnoreCase("N")) {
				// choosing to pick up.  Needs to not have a valid card to play in hand.
				if (currentPlayer.cardInHand(inPlayDeck.peek())) {
					System.out.println("Unable to pickup card: you have at least one valid card to play in your hand.");
				}
				else {
					// make sure there are cards to pick up.
//					checkGameDeckSize();
					// pick up card and end turn.
					currentPlayer.pickupCard(pickFromGameDeck());
					validChoice = true;
				}
			}
			else {
				// player has selected a card.  Search hand for their selection.
				if (currentPlayer.cardInHand(chosenCardName)) {
					chosenCard = currentPlayer.getChosenCard();
					if (chosenCard.match(inPlayDeck.peek())) {
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
	

	private Player selectStartingPlayer() {		
//		boolean startingPlayerSelected = false;
		Player startingPlayer = null;
		
		while (startingPlayer == null) {
			ArrayList<Integer> playerValues = new ArrayList<Integer>();
			for (int i = 0; i < playerM.size(); i++) {
				playerValues.add(gameDeck.pop().getValue());
			}
			System.out.println(playerValues.toString());
			int highestValue = Collections.max(playerValues);
			if (Collections.frequency(playerValues, highestValue) <= 1) {
				startingPlayer = playerM.getPlayer(playerValues.indexOf(highestValue));
//				startingPlayerSelected = true;
			}
		}
		return startingPlayer;
	}
	
	private void createPlayers() {
		System.out.println("Enter number of player: ");
		int numOfPlayer = obtainValidInt();
		playerM.createPlayer(numOfPlayer, userInput);
	}
	
	private void createCards() {
		inPlayDeck.clear();
		gameDeck.clear();
		gameDeck.createCards();
		gameDeck.shuffle();
	}
	
	// Delegate
	private void deal() {
		playerM.deal(STARTING_CARD_COUNT, gameDeck);
	}

	private void scoreGame() {
		playerM.scoreGame();
	}

	// Extract
	private void checkGameDeckSize() {
		gameDeck.checkDeckSize(inPlayDeck);
	}
	
	private Card pickFromGameDeck() {
		checkGameDeckSize();
		return gameDeck.pop();
	}
	
	private int obtainValidInt() {
		int num = 0;
		while(outOfRange(num)) {
			try {
				num = Integer.parseInt(userInput.nextLine());
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid input.  Please enter a number between 2 - 4: ");
			}
			if (outOfRange(num)) {
				System.out.println("Input out of range.  Please enter a number between 2 - 4: ");
			}
		}
		return num;
	}
	
	private boolean outOfRange(int num) {
		return (num < 2 || num > 4);
	}
	
	

}
