package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tree.Row;


public class FileOp {

	public static List<Row> openFile(String fileName) throws IOException
	{

		String file = fileName;
		BufferedReader br = new BufferedReader(new FileReader(file));	
		String headers = br.readLine();	
		List<Row> array = new ArrayList<Row>();
		String current = br.readLine();
		
		while(current!=null)
		{	
			Row line = new Row(headers,current);
			array.add(line);
			current = br.readLine();
		}
		
		br.close();
		return array;		
	}
}
