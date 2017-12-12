package algorithm;

import java.util.ArrayList;
import java.util.List;

import tree.Row;

public class InfoGain extends Algorithm{
	
	private double entropy=0;
	private List<Row> rows;
	
	public double calculateEntropy(List<Row> table)
	{
		rows = table;
		
		if(rows.size()==0) return 0;
		
		int ONE = 1;
		int ZERO = 0;

		List<Row> zeroRows = new ArrayList<Row>();
		List<Row> oneRows = new ArrayList<Row>();
		

		for(int i=0;i<rows.size();i++)
		{
			Row current = rows.get(i);
			int ClassCol = current.getRowList().size()-1;

			if(current.getCell(ClassCol).getValue()==ONE)
			{
				oneRows.add(current);
			}else if(current.getCell(ClassCol).getValue()==ZERO)
			{
				zeroRows.add(current);
			}
		}
			
		int count0 = zeroRows.size();
		int count1 = oneRows.size();
		
		entropy = entropyMath(count0,count1);
		
		return entropy;
		
	}
	
	
	private double entropyMath(int count0, int count1)
	{
		int local_count0 = count0;
		int local_count1 = count1;
		
		double local_ratio0 = (double)local_count0/(local_count0+local_count1);
		double local_ratio1 = 1 - local_ratio0;
		return -local_ratio1*Math.log(local_ratio1)/Math.log(2)-local_ratio0*Math.log(local_ratio0)/Math.log(2);
	}
	
	public double calculateInfoGain(double currentEntropy,
			ArrayList<Double> entropiesOfSubsets,
			ArrayList<Integer> sizesOfSubsets, double totalExamples) {

		double gain = currentEntropy;
		for (int j = 0; j < entropiesOfSubsets.size(); j++)
			gain -= (sizesOfSubsets.get(j) / totalExamples)
			* entropiesOfSubsets.get(j);
		return gain;
	}

}
