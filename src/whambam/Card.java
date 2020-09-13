package whambam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Card {

	public static final ArrayList<String> COLOURS = new ArrayList<String>(
			Arrays.asList("Blue", "Green", "Purple", "Orange", "White"));
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
			put(15, "Wham Bam!");
		}
	};

	private String name;
	private String colour;
	private int value;

	public Card(String colour, int value) {
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

	public boolean match(Card otherCard) {
		return matchValue(otherCard) || matchColour(otherCard) || getValue() == 15;
	}

	private boolean matchValue(Card otherCard) {
		return this.getValue() == otherCard.getValue();
	}

	private boolean matchColour(Card otherCard) {
		return this.getColour().equals(otherCard.getColour());
	}

	@Override
	public String toString() {
		return getName();
	}

}
