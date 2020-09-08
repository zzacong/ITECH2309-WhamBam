package domain;

public class Card {
	
	private static final String[] VALUENAME = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Wham!"};

	private String name;
	private String colour;
	private int value;
	private boolean isAction = false;
	
	public Card (String colour, int value) {
		this.setColour(colour);
		this.setValue(value);
		this.setName(colour + " " + VALUENAME[value]);
	}
	
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}
	
	public String getColour() {
		return colour;
	}
	
	private void setColour(String colour) {
		this.colour = colour;
	}
	
	public int getValue() {
		return value;
	}
	
	private void setValue(int value) {
		this.value = value;
	}
	
	public boolean isAction() {
		return isAction;
	}

	protected void setAction(boolean isAction) {
		this.isAction = isAction;
	}

	public String toString() {
		return getName();
	}
	
	public boolean matchCard(Card otherCard) {
		return matchValue(otherCard) || matchColour(otherCard);
	}
	
	private boolean matchValue(Card otherCard) {
		return this.getValue() == otherCard.getValue();
	}
	
	private boolean matchColour(Card otherCard) {
		return this.getColour().equals(otherCard.getColour());
	}
	
	
}
