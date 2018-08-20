/**
 * Name: Jiaqi Fan
 * ID: A12584051
 * Login: cs12sju
 */
package hw8;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * class line counter that use hash table
 * to count the % of same wording in each file
 * @author Jiaqi Fan
 * @version 1.0
 * @since 5/23/2016
 */
public class LineCounter {
	/**
	 * method that print static on console
	 * @param filename the first file name 
	 * @param compareFileName the second file name
	 * @param percentage the percentage of similiar
	 */
	public static void printToConsole(String filename, String compareFileName, int percentage) {
		if(!filename.isEmpty()) //if file name is not empty print
			System.out.println("\n"+filename+":");
		if(!compareFileName.isEmpty()) //the second file name is not empty
			//print out the comparsion
			System.out.println(percentage+"% of lines are also in "+compareFileName);
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//check for argument passed in
		if(args.length<1) {
			System.err.println("Invalid number of arguments passed");
			return;
		}
		//get the length of the arguments
		int numArgs = args.length;
		//create the hash table array based on how many argvs
		HashTable[] tableList = new HashTable[numArgs];
		List<String>[] stringList = new List[numArgs];
		//Pre processing: Read every file and create a HashTable
		for(int i=0; i<numArgs; i++) {
			tableList[i] = new HashTable(16); //Initialize all the table
			stringList[i] = new ArrayList<>();
			File file = new File(args[i]); //create a file for all argvs
			String line;
			//use buffer reader to read all the lines from file
			try (FileReader fr = new FileReader(file);
				 BufferedReader br = new BufferedReader(fr);
			){
				//add the lines in file to the hash table
				while ((line = br.readLine()) != null) {
					tableList[i].insert(line);
					stringList[i].add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//Find similarities across files
		for(int i=0; i<numArgs; i++) {
			boolean flag = true;
			for (int j = 0; j <numArgs ; j++) {
				if(i != j){
					int percent = 0; //the percent of similarity
					Iterator<String> iterator = stringList[i].iterator();
					String val;
					//compare each hash table
					while (iterator.hasNext()){
						val = iterator.next();
						if(tableList[j].lookup(val)){
							percent++;
						}
					}
					//calculate the percentage
					percent = (int) ((double) percent / stringList[i].size() * 100);
					//print it on the console
					printToConsole(flag ? args[i] : "", args[j], percent);
					flag = false;
				}
			}
		}
	}
}
