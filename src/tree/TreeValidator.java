package tree;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import util.FileOp;

public class TreeValidator {

	private Node bestTree;
	private int l,k;
	private List<Row> validateTable;
	private List<Row> trainingTable;
	private int nodeCount;
	
	public Node postPrunTree(String fileName, int L, int K, List<Row> vldTable, String algorithm) throws IOException
	{
		String trainingFile = fileName;	
		trainingTable = FileOp.openFile(trainingFile);
		TreeBuilder treeBuilder = new TreeBuilder();	
		Node postPrunTree = treeBuilder.buildTree(trainingTable,new Node(),algorithm);	

		bestTree = postPrunTree;
		validateTable = vldTable;
		l = L;
		k = K;
		TreeTester treeTester = new TreeTester();
		double bestAccuracy = treeTester.accuracyTester(bestTree, validateTable);
		
		for(int i=1;i<l+1;i++)
		{
			// create a new tree everytime
			trainingTable = FileOp.openFile(trainingFile);
			Node newTree = new TreeBuilder().buildTree(trainingTable, new Node(), algorithm);
			Random random = new Random();			
			int m = Math.max(1,random.nextInt(k));
			for(int j=1; j<m+1; j++)
			{
				nodeCount = 0;
				int n = orderTreeNodes(newTree,1);
				int p = Math.max(1,random.nextInt(n));
				prunTree(newTree,p);			
			}
			double newTreeAccurancy = treeTester.accuracyTester(newTree, validateTable);
			if(bestAccuracy<newTreeAccurancy)
			{
				bestAccuracy = newTreeAccurancy;
				bestTree = newTree;
			}			
		}	
		return bestTree;
	}
	
	private int orderTreeNodes(Node tree, int idx)
	{
		Node rootNode = tree;
		int index = idx;
		this.nodeCount++;
		
		if(rootNode.getChildren()!=null)
		{
			rootNode.setIndex(index++);
			if(rootNode.getLeftChild()!=null)
				orderTreeNodes(rootNode.getLeftChild(), index);
			if(rootNode.getRightChild()!=null)
				orderTreeNodes(rootNode.getRightChild(), index);
		}
		if(nodeCount==1) return 1;
		return nodeCount-1;
	}
	
	private void prunTree(Node node, int nodeNum)
	{
		Node rootNode = node;
		int index = nodeNum;
		
		int currentIdx = rootNode.getIndex();
		if(currentIdx==index)
		{
			// found the node and cut children
			rootNode.setChildren(null, null);
			// set classification info
			List<Row> table = rootNode.getRows();

			int countOne = 0;
			for(int i=0;i<table.size();i++)
			{
				int columnClass = table.get(i).getRowList().size()-1;
				countOne += table.get(i).getCell(columnClass).getValue();	
			}
			if(countOne>=(table.size()-countOne))
			{
				rootNode.setClassification(1);
			}
			else
			{
				rootNode.setClassification(0);
			}				
		}
		else 
		{
			if(rootNode.getLeftChild()!=null)
				prunTree(rootNode.getLeftChild(),index);
			if(rootNode.getRightChild()!=null)
				prunTree(rootNode.getRightChild(),index);
		}
	}

}
