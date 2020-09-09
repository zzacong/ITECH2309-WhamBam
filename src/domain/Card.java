package domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Card {
	
//	public static final ArrayList<String> COLOURS = new ArrayList<String>(Arrays.asList("Blue", "Green", "Purple", "Orange", "White"));
	public static final ArrayList<String> COLOURS = new ArrayList<String>(Arrays.asList("Blue", "Green", "Purple", "Orange"));
	private static final Map<Integer, String> VALUENAME = new HashMap<Integer, String>() {
		{
			put(0, "Zero");
			put(1, "One");
			put(2, "Two");
			put(3, "Three");
			put(4, "Four");
			put(5, "Five");
			put(6, "Six");		
			put(7, "Seven");
			put(8, "Eight");
			put(9, "Nine");
			put(10, "Wham!");
//			put(15, "Wham Bam!");
		}
	};	

	private String name;
	private String colour;
	private int value;
	private boolean isAction = false;
	
	public Card (String colour, int value) {
		this.setColour(colour);
		this.setValue(value);
		this.setName(colour + " " + VALUENAME.get(value));
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
	
	public boolean match(Card otherCard) {
		return matchValue(otherCard) || matchColour(otherCard);
	}
	
	private boolean matchValue(Card otherCard) {
		return this.getValue() == otherCard.getValue();
	}
	
	private boolean matchColour(Card otherCard) {
		return this.getColour().equals(otherCard.getColour());
	}
	
	public static String formatCardName(String input) {
		String[] arr = input.split(" ");	
		return String.join(" ", capitalised(arr[0]), capitalised(arr[1]));
	}
	
	private static String capitalised(String input) {
		return input.substring(0,1).toUpperCase() + input.substring(1);
	}
	
}
