/**
 * 
 */
package whambam;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Zac
 *
 */
public class PlayerManager {

	private int turn = 0;

	private ArrayList<Player> players;
	private Scanner userInput = new Scanner(System.in);
	private ArrayList<Player> winners = new ArrayList<Player>();
	private ArrayList<Player> champions = new ArrayList<Player>();
	private ArrayList<Player> losers = new ArrayList<Player>();

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

	private ArrayList<Player> getLosers() {
		return losers;
	}

	public boolean addLoser(Player loser) {
		return this.getLosers().add(loser);
	}

	public void addLosersExcept(Player exception) {
		getLosers().addAll(getAllPlayers());
		getLosers().remove(exception);
	}

	public void clearLosers() {
		getLosers().clear();
	}

	private ArrayList<Player> getWinners() {
		return winners;
	}

	public boolean addWinner(Player winner) {
		return getWinners().add(winner);
	}

	public void addWinnersExcept(Player exception) {
		getWinners().addAll(getAllPlayers());
		getWinners().remove(exception);
	}

	public void clearWinners() {
		getWinners().clear();
	}

	private ArrayList<Player> getChampions() {
		return champions;
	}

	public boolean addChampion(Player champion) {
		return this.champions.add(champion);
	}

	public String printChampions() {
		String output = "";
		for (Player player : getChampions()) {
			output += String.format("Congratulations %s. You WIN! \n", player.getName());
		}
		return output;
	}

	private float getLoserHandValue() {
		float totalLoserValue = 0;
		for (Player loser : getLosers()) {
			int loserValue = loser.calculateValueOfHand();
			System.out.println(String.format("Remaining hand value for %s: %d", loser.getName(), loserValue));
			totalLoserValue += loserValue;
		}
		return totalLoserValue;
	}

	public void scoreGame() {
		int scorePerPerson = Math.round(getLoserHandValue() / getWinners().size());
		for (Player winner : getWinners()) {
			// add score to winning player's hand
			winner.setScore(winner.getScore() + scorePerPerson);
		}

		System.out.println("\nScores at end of round:");
		printPlayersScore();

		clearWinners();
		clearLosers();
	}

	public boolean hasChampion(int winScore) {
		for (Player player : getAllPlayers()) {
			if (player.getScore() >= winScore) {
				addChampion(player);
			}
		}
		return getChampions().size() > 0 ? true : false; // return true if there are champions
	}

	public void createPlayers(int numOfPlayer) {
		System.out.println(String.format("Creating %d players ...", numOfPlayer));
		for (int i = 0; i < numOfPlayer; i++) {
			System.out.println(String.format("\nEnter Player %d name: ", i + 1));
			String name = userInput.nextLine();
			if (players.add(new Player(name))) {
				System.out.println(String.format("Player %s created with starting score = %d", 
				getPlayer(i).getName(), getPlayer(i).getScore()));
			}
		}
	}

	public void deal(int cardCount, Deck deck) {
		System.out.println("Dealing cards...");
		clearHand();
		for (int i = 0; i < cardCount; i++) {
			for (Player player : getAllPlayers()) {
				player.pickupCard(deck.pop());
			}
		}
		String output = "";
		for (Player player : getAllPlayers()) {
			output += String.format("%s has %d cards \n", player.getName(), player.getHandSize());
		}
		System.out.print(output);
	}

	public Player changePlayer() {
		turn++;
		if (turn == size())
			turn = 0;
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
}
