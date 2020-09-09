/**
 * 
 */
package domain;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Zac
 *
 */
public class PlayerManager {
	
	private int turn = 0;
	
	private ArrayList<Player> players;
//	private Scanner userInput = new Scanner(System.in);
//	private Player currentPlayer;
	private Player winner;
	private Player champion;

	public PlayerManager() {
		players = new ArrayList<Player>();
	}
	
	private ArrayList<Player> getAllPlayers() {
		return players;
	}
	
	public Player getPlayer(int index) {
		return getAllPlayers().get(index);
	}
	
	public int size() {
		return getAllPlayers().size();
	}
	
	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getChampion() {
		return champion;
	}

	public void setChampion(Player champion) {
		this.champion = champion;
	}

	public void createPlayer(int numOfPlayer, Scanner userInput) {
		System.out.println(String.format("Creating %d players ...", numOfPlayer));
		for (int i = 0; i < numOfPlayer; i++) {
			System.out.println(String.format("\nEnter Player %d name: ", i + 1));
			String name = userInput.nextLine();
			if (players.add(new Player(name))) {
				System.out.println(String.format("Player %s created with starting score = %d", getPlayer(i).getName(), getPlayer(i).getScore()));
			}
		}
	}
	
//	public Player getTopPlayer() {
//		Player topPlayer = getPlayer(0);
//		int score = topPlayer.getScore();
//
//		for (Player player : getAllPlayers()) {
//			if (player.getScore() > score) {
//				score = player.getScore();
//				topPlayer = player;
//			}
//		}
//		return topPlayer;
//	}
	
	public boolean hasWinningPlayer(int winScore) {
		for (Player player : getAllPlayers()) {
			if (player.getScore() >= winScore) {
				setChampion(player);
				return true;
			}
		}
		setChampion(null);
		return false;
	}
	
	public Player changePlayer() {
		turn++;
		if (turn == size()) turn = 0;
//		turn = turn < size() - 1 ? turn + 1 : 0;
//		currentPlayer = getPlayer(turn);
		return getPlayer(turn);
	}
	
	public void printPlayersScore() {
		for (Player player : getAllPlayers()) {
			System.out.println(player);
		}
	}
	
	public void clearHand() {
		for (Player player : getAllPlayers()) {
			player.clearHand();
		}
	}
	
	public void deal(int cardCount, Deck deck) {
		clearHand();
		for (int i = 0; i < cardCount; i++) {
			for (Player player : getAllPlayers()) {
				player.pickupCard(deck.pop());
			}			
		}
	}
	
	private int getLoserHandValue() {
		int loserValue = 0;
		for (Player player : getAllPlayers()) {
			int handValue = player.calculateValueOfHand();
			System.out.println(String.format("Remaining hand value for %s: %d", player.getName(), handValue));			
			loserValue += handValue;
		}
		return loserValue;
	}
	
	public void scoreGame() {

		// add score to winning player's hand
		getWinner().setScore(getWinner().getScore() + getLoserHandValue());

		System.out.println("\nScores at end of round:");
		printPlayersScore();
	}
}
