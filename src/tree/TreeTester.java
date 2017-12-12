package tree;

import java.util.List;

public class TreeTester {
	
	private Node rootNode;
	private List<Row> testTable;	
	
	public double accuracyTester(Node tree, List<Row> table)
	{
		rootNode = tree;
		testTable = table;
		
		int rowCount = testTable.size();
		int columnCount = testTable.get(0).getRowList().size();
		int match = 0;

		for(int i=0;i<rowCount;i++)
		{
			// not leaf, find next node
			while(rootNode.getChildren()!=null)
			{
				String treeAttr = rootNode.getAttribute();
				for(int j=0; j<columnCount-1;j++)
				{
					String currentAttr = testTable.get(i).getCell(j).getAttribute();
					if(treeAttr.equalsIgnoreCase(currentAttr))
					{
						int currentValue = testTable.get(i).getCell(j).getValue();
						if(currentValue==0)
						{
							if(rootNode.getLeftChild()!=null)
								// go to left child node
								rootNode = rootNode.getLeftChild();	
						}
						else
						{
							if(rootNode.getRightChild()!=null)
								// go to right child node
								rootNode = rootNode.getRightChild();	
						}
						// break j
						break;
					}		
				}
			}
			// arrive at leaf
			// find classification
			int classification = rootNode.getClassification();
			if(classification==testTable.get(i).getCell(columnCount-1).getValue())
				match++;
			// reset rootNode to top node for next row
			rootNode = tree;
		}
		return (double)match/(double)rowCount;
	}

}
