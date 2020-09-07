package domain;

public class Main {

	public static void main(String[] args) {
		Card blueCard = new Card("Blue", 2);
		Card greenCard = new Card("Green", 4);
		
		System.out.println(greenCard.toString());
		
		System.out.println(blueCard.matchCard(greenCard));
		
	}

}
