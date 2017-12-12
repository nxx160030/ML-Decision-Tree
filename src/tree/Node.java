package tree;

import java.util.ArrayList;
import java.util.List;

public class Node {	
	
	private List<Node> children;
	private double entropy;
	private int classification;
	private String attribute;
	private Node leftChild;
	private Node rightChild;
	private int index;
	private List<Row> rows;
	
	public Node(){
 
		children = null;
		leftChild = null;
		rightChild = null;
		entropy = 0;
		classification = 0;
		attribute = new String("");
		index = 0;
		rows = new ArrayList<Row>();
	}
	
	public void setEntropy(double value)
	{
		entropy = value;
	}
	
	public double getEntropy()
	{
		return entropy;
	}
	
	public void setAttribute(String value)
	{
		attribute = value;
	}
	
	public String getAttribute()
	{
		return attribute;
	}

	
	public void setChildren(Node leftChild, Node rightChild)
	{
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		children = new ArrayList<Node>();
		if(this.leftChild!=null)
		{
			children.add(this.leftChild);
		}
		if(this.rightChild!=null)
		{
			children.add(this.rightChild);
		}
	}
	
	public void setClassification(int value)
	{
		classification = value;
	}
	
	public List<Node> getChildren()
	{
		if(leftChild==null&&rightChild==null) return null;
		return children;
	}
	
	public Node getChildren(int index)
	{
		return children.get(index);
	}
	
	public Node getLeftChild()
	{
		if(this.leftChild!=null)
		{
			return this.leftChild;		
		}
		return null;
	}
	
	
	public Node getRightChild()
	{
		if(this.rightChild!=null)
		{
			return this.rightChild;		
		}
		return null;
	}
	
	public int getClassification()
	{
		return classification;
	}
	
	
	public void setIndex(int value)
	{
		index = value;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public void setRows(List<Row> list)
	{
		rows = list;
	}
	
	public List<Row> getRows()
	{
		return rows;
	}
	
}
