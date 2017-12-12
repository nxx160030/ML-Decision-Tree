package tree;

public class Cell {
	
	private String attribute;
	private int value;
	
	public Cell(String attr, int val)
	{
		attribute = attr;
		value = val;
		
	}
	
	public String getAttribute()
	{
		return attribute;
	}
	
	public int getValue()
	{
		return value;
	}

}
