package a1_2020;

public class Card {
	private String name;
	private String colour;
	private int value;
	
	public Card (String name, String colour, int value)
	{
		this.name = name;
		this.colour = colour;
		this.value = value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getColour()
	{
		return colour;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return getName();
	}
}
