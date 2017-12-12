package algorithm;

import java.util.List;

import tree.Row;

public class VarImp  extends Algorithm{
	
	private double entropy=0;
	private List<Row> rows;
	
	public double calculateEntropy(List<Row> table)
	{
		rows = table;
		if(rows == null) return 0;
		int[] count = new int[2];

		for(int i = 0; i < rows.size(); i++){
			
			int ClassColumn = rows.get(i).getRowList().size()-1;
			
			count[rows.get(i).getRowList().get(ClassColumn).getValue()]++;
		}
		
		double res = count[0] * count[1];
		entropy = res / (rows.size() * rows.size());
	
		return entropy;
	}
}
