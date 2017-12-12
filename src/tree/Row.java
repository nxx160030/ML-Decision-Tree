package tree;

import java.util.ArrayList;
import java.util.List;

public class Row {
	
	private List<Cell> cellList = new ArrayList<Cell>();
	
	public Row(String headers,String line)
	{
		String COMMA = ",";
		String[] attribute = headers.split(COMMA);
		String[] values = line.split(COMMA);
		
		for(int i=0; i<attribute.length; i++)
		{
			Cell cell = new Cell(attribute[i], Integer.parseInt(values[i]));
			cellList.add(cell);
		}
	}
	
	public List<Cell> getRowList()
	{
		return cellList;
	}
	
	
	public Cell getCell(int index)
	{
		return cellList.get(index);
	}

}
