package main;

import java.util.List;

import tree.TreeBuilder;
import tree.TreeTester;
import tree.TreeValidator;
import tree.Node;
import tree.Row;
import util.FileOp;

public class Main {

	private static String PRUN_YES = "yes";
	
	public static String INFO_GAIN = "infoGain";
	public static String IMPURITY = "varImp";
	
	public static String warningMsg = "parameters should be like:"
			+ "\n <L> <K> <training-set> <validation-set> <test-set> <to-print>"
			+"\n L: integer (used in the post-pruning algorithm)"
			+"\n K: integer (used in the post-pruning algorithm)"
			+"\n to-print:{yes,no}";

	public static void main(String[] args) throws Exception {
		
		
		int L=0, K=0;
		String trngSetFile="";
		String vldSetFile="";
		String tstSetFile="";
		String toPrint="";
		try {		
			// readin commands and analysis
			L = Integer.parseInt(args[0]);
			K = Integer.parseInt(args[1]);
				
			trngSetFile = args[2];
			vldSetFile = args[3];
			tstSetFile = args[4];
				
			toPrint = args[5];
		} catch (Exception e) {
			
			System.out.println(warningMsg);
		}				

		
//		int L=23;
//		int K=45;
//		
//		// create tables with file names
//		String trngSetFile = "./training_set.csv";
//		String tstSetFile = "./test_set.csv";
//		String vldSetFile = "./validation_set.csv";
//		String toPrint = "yes";
		
		List<Row>  trainingTable_infogain = FileOp.openFile(trngSetFile);
		List<Row>  trainingTable_varimp = FileOp.openFile(trngSetFile);
		List<Row> testTable = FileOp.openFile(tstSetFile);
		List<Row> validateTable = FileOp.openFile(vldSetFile);
		
		// using tranningSet to create two decision trees
		TreeBuilder treeBuilder = new TreeBuilder();
		
		Node tree_infogain = treeBuilder.buildTree(trainingTable_infogain,new Node(),INFO_GAIN);		
		Node tree_varimp = treeBuilder.buildTree(trainingTable_varimp,new Node(),IMPURITY);	
		
		if(toPrint.equalsIgnoreCase(PRUN_YES))
		{
			System.out.print("Now printing tree using " + INFO_GAIN);
			System.out.print(treeBuilder.printTree(tree_infogain, 0));	
			System.out.println("\n");

			System.out.print("Now printing tree using " + IMPURITY);
			System.out.print(treeBuilder.printTree(tree_varimp, 0));
			System.out.println("\n");
		}

		
		// post prun the trees
		TreeValidator treeValidator = new TreeValidator();
		Node prun_infogain = treeValidator.postPrunTree(trngSetFile,L,K,validateTable,INFO_GAIN);
		Node prun_varimp = treeValidator.postPrunTree(trngSetFile,L,K,validateTable,IMPURITY);
		if(toPrint.equalsIgnoreCase(PRUN_YES))
		{
			System.out.print("Now printing tree using " + INFO_GAIN + " after post prun");
			System.out.print(treeBuilder.printTree(prun_infogain, 0));	
			System.out.println("\n");

			System.out.print("Now printing tree using " + IMPURITY + " after post prun");
			System.out.print(treeBuilder.printTree(prun_varimp, 0));
			System.out.println("\n");
		}
		
		// test the accuracy of two trees
		TreeTester treeTester = new TreeTester();
		double accurancy_infogain = treeTester.accuracyTester(tree_infogain, testTable);
		double accurancy_varimp = treeTester.accuracyTester(tree_varimp, testTable);
		System.out.println("The accurancy of " + INFO_GAIN + " is " + accurancy_infogain*100 + "%");
		System.out.println("The accurancy of " + IMPURITY + " is " + accurancy_varimp*100 + "%");
		System.out.println("\n");
		
		accurancy_infogain = treeTester.accuracyTester(prun_infogain, testTable);
		accurancy_varimp = treeTester.accuracyTester(prun_varimp, testTable);
		System.out.println("After Post Prun the accurancy of " + INFO_GAIN + " is " 
				+ accurancy_infogain*100 + "%");
		System.out.println("After Post Prun the accurancy of " + IMPURITY + " is " 
				+ accurancy_varimp*100 + "%");	
		System.out.println("\n");
	}
}
