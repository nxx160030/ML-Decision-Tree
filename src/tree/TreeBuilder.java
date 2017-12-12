package tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import algorithm.InfoGain;
import algorithm.VarImp;

public class TreeBuilder {
	
	private String INFO_GAIN = "infoGain";
	private String IMPURITY = "varImp";
	

	// create a tree
	public Node buildTree(List<Row> table, Node node, String algorithm) throws IOException{

		 Node rootNode = node;
		 List<Row> rows = table;
		 int index = -1;
		 int columnClass = rows.get(0).getRowList().size()-1;
		 int rowCount = rows.size();
		 List<Row> leftRows = new ArrayList<Row>();
		 List<Row> rightRows = new ArrayList<Row>();
		 double maxGain = 0;
		 InfoGain infoGain = new InfoGain();
		 VarImp varImp = new VarImp();
		 rootNode.setRows(rows);

		 
		 if(algorithm.equalsIgnoreCase(INFO_GAIN))
		 {
			 rootNode.setEntropy(infoGain.calculateEntropy(rows));
		 }
		 else if(algorithm.equalsIgnoreCase(IMPURITY))
		 {
			 rootNode.setEntropy(varImp.calculateEntropy(rows));
		 }

		 for(int i = 0; i < columnClass; i++){  
			 List<Row> leftOnes = new ArrayList<Row>();
			 List<Row> rightOnes = new ArrayList<Row>();
			 for(int j = 0; j < rowCount; j++){
				 int value = rows.get(j).getCell(i).getValue();
				 if(value == 0){
					 leftOnes.add(rows.get(j));
				 }
				 else{
					 rightOnes.add(rows.get(j));
				 }
			 }	 
			 
			 ArrayList<Double> subEntropy = new ArrayList<>();
			 if(algorithm.equalsIgnoreCase(INFO_GAIN))
			 {
				 subEntropy.add(infoGain.calculateEntropy(leftOnes));
				 subEntropy.add(infoGain.calculateEntropy(rightOnes));
			 }
			 else if(algorithm.equalsIgnoreCase(IMPURITY))
			 {
				 subEntropy.add(varImp.calculateEntropy(leftOnes));
				 subEntropy.add(varImp.calculateEntropy(rightOnes));
			 }

			 ArrayList<Integer> subSize = new ArrayList<>();
			 subSize.add(leftOnes.size());
			 subSize.add(rightOnes.size());
			 double gain = infoGain.calculateInfoGain(rootNode.getEntropy(), subEntropy, subSize, rows.size());

			 if(gain > maxGain){
				 index = i;
				 rootNode.setAttribute(rows.get(0).getCell(i).getAttribute());
				 maxGain = gain;
				 leftRows = leftOnes;
				 rightRows = rightOnes;
			 }
		 }
		 if(index != -1){
			 for(int j = 0; j < leftRows.size(); j++){
				 leftRows.get(j).getRowList().remove(index);
			 }
			 for(int j = 0; j < rightRows.size(); j++){
				 rightRows.get(j).getRowList().remove(index);
			 }
			 Node leftChild = new Node();
			 Node rightChild = new Node();	 
 
			 rootNode.setChildren(buildTree(leftRows, leftChild,algorithm), buildTree(rightRows, rightChild,algorithm));
		 }
		 else{
			 Cell classificationCell = rows.get(0).getCell(rows.get(0).getRowList().size()-1);
			 int leafValue = classificationCell.getValue();
			 rootNode.setClassification(leafValue); 
			 return rootNode;
		 }
		 return rootNode;
	}

	public String printTree(Node rootNode, int level){
		StringBuilder stringBuilder = new StringBuilder();
		if(rootNode.getChildren() != null){
			for(int i = 0; i < rootNode.getChildren().size(); i++){
				stringBuilder.append("\n");
				for(int j = 0; j < level; j++){
					stringBuilder.append("| ");
				}
				stringBuilder.append(rootNode.getAttribute() + " = " + i + " : ");
				if(rootNode.getChildren() != null){
					stringBuilder.append(printTree(rootNode.getChildren(i), level + 1));
				}
			}
	   }
		else{
			stringBuilder.append(rootNode.getClassification());
		}
		return stringBuilder.toString();
	}	

}
