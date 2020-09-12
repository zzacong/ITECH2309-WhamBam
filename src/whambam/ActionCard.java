/**
 * 
 */
package whambam;

/**
 * @author Zac
 *
 */
public class ActionCard extends Card implements Actionable {
	
	public ActionCard(String colour, int value) {
		super(colour, value);
	}

	@Override
	public int cardPenalty(Card card) {
		switch (card.getValue()) {
		case 15:
			return 9;
		case 10:
			return 3;
		default:
			return card.getValue();
		}
			
	}

}
