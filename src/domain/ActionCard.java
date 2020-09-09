/**
 * 
 */
package domain;

/**
 * @author Zac
 *
 */
public class ActionCard extends Card implements Actionable {

	public ActionCard(String colour, int value) {
		super(colour, value);
		setAction(true);
	}

	@Override
	public int cardPenalty() {
		return 3;
	}

	@Override
	public void penaltyMessage() {
		System.out.println("Wham Card Active!  Penalty: pick up 3 cards");		
	}

}
