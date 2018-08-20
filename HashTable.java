/**
 * Name: Jiaqi Fan
 * ID: A12584051
 * Login: cs12sju
 */
package hw8;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**
 * class for a hash table of link list that 
 * store the lines from the files.
 * @author Jiaqi Fan
 * @version 1.0
 * @since 5/23/2016
 */
public class HashTable implements IHashTable{
	
	private int nelems;  //Number of element stored in the hash table
	private int expand;  //Number of times that the table has been expanded
	private int collision;  //Number of collisions since last expansion
	private String statsFileName;     //FilePath for the file to write statistics upon every rehash
	private boolean printStats = false;   //Boolean to decide whether to write statistics to file or not after rehashing
    private int capacity; //the capacity of the table
	private LinkedList<LinkedList<String>> nodes; //the linklist for the collision
    private List<String> items;
    private int longest; //keep track of the longest chaining
	
	/**
	 * Constructor for hash table
	 * @param Initial size of the hash table
	 */
	public HashTable(int size) {
		//Initialize the hash table
        this.capacity = size;//set the cap equal to size
		nodes = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
        	//loop thought the linklist and make nodes
            nodes.add(new LinkedList<>());
        }
        items = new ArrayList<>(capacity);
    }
	
	/**
	 * Constructor for hash table
	 * @param Initial size of the hash table
	 * @param File path to write statistics
	 */
	public HashTable(int size, String fileName){
        this.capacity = size; //initialize the table
        nodes = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            nodes.add(new LinkedList<>());
        }
        items = new ArrayList<>(capacity);

        printStats = true; //Set printStats to true
        statsFileName = fileName; //Set the file name
	}

	/** Insert the string value into the hash table
	 * 
	 * @param value value to insert
	 * @throws NullPointerException if value is null
	 * @return true if the value was inserted, false if the value was already present
	 */
	@Override
	public boolean insert(String value) {
        return insertHelper(value, false);
	}

	/**
	 * private helper method for insert
	 * @param value the value to insert
	 * @param rehash to check rehash table
	 * @return true for inserted otherwise false
	 */
    private boolean insertHelper(String value, boolean rehash) {
        if(lookup(value)){ //check for repeat value
            return false;
        }
        //check for rehash and expand condition
        if(!rehash && nelems >= capacity * 2 / 3 - 1){
            expend();
            rehash();
        }
        LinkedList<String> tempnode = nodes.get(hash(value));
        boolean insert = tempnode.add(value);
        if(insert){//if node is added
            nelems++;
            if(!rehash){
                items.add(value);
            }
            //check condition for collision
            if(tempnode.size() > 1){
                collision++;
                longest = Math.max(longest, tempnode.size()); //record longest
            }
        }
        return insert;
    }

	/** Delete the given value from the hash table
	 * 
	 * @param value value to delete
	 * @throws NullPointerException if value is null
	 * @return true if the value was deleted, false if the value was not found
	 */
	public boolean delete(String value) {
        if(value == null){
            throw new NullPointerException();
        }
        LinkedList<String> tempnode = nodes.get(hash(value));
        boolean remove = tempnode.remove(value);
        if(remove){
            nelems--;
            items.remove(value);
            if(!tempnode.isEmpty()){
                collision--;
                longest = findLongest();
            }
        }
        return remove;
	}

	/**
	 * private helper method for find the longest chaining
	 * @return 
	 */
    private int findLongest() {
        int chain = 0;
        for (int i = 0; i < nodes.size(); i++) {
        	//find the longest chaining
            chain = Math.max(chain, nodes.get(i).size());
        }
        return chain; //return the length
    }

	/** Check if the given value is present in the hash table
	 * 
	 * @param value value to look up
	 * @throws NullPointerException if value is null
	 * @return true if the value was found, false if the value was not found
	 */
    @Override
	public boolean lookup(String value) {
        if(value == null){//check for value is null or not
            throw new NullPointerException();
        }
        return nodes.get(hash(value)).contains(value);
	}

	/** Print the contents of the hash table to the given print stream. Print nothing if table is empty
	 * 
	 * Example output for this function:
	 * 
	 * 0:
	 * 1:
	 * 2: marina, fifty
	 * 3: table
	 * 4:
	 * 
	 * @param out the output stream
	 */
	@Override
	public void printTable() {
		//loop through the table and print everything
        for (int i = 0; i < nodes.size(); i++) {
            System.out.println(i + ": " + listStr(nodes.get(i)));
        }
    }
	/**
	 * private helper method for print table method
	 * @param list the linklist of the chaining
	 * @return the string of  each item in linkedlist
	 */
    private String listStr(LinkedList<String> list) {
    	//create a empty string builder to hold the chaing
        StringBuilder builder = new StringBuilder();
        //check for chaining 
        boolean checker = false;
        for (String temp : list) {
            if (checker) { //condition to add a comma or not
            	builder.append(",");
            }else {
                checker = true;
            }
            builder.append(temp);
        }
        return builder.toString();
    }

	/**
	 * Return the number of elements currently stored in the hashtable
	 * @return nelems
	 */
    @Override
	public int getSize() {
        return nelems;
	}
    
    /**
     * private helper method to enlarge 
     * the HashTable when load factor 
     * goes over the threshold
     */
    private void expend(){
	   //number of expansion increase
       expand++;
       //copy the elements
       for (int i = 0; i < capacity; i++) {
    	   nodes.add(new LinkedList<>());
            }
       capacity += capacity; //set the new cap
       }

    /**
     * private helper method to rehash
     * the items into the table after expansion
     */
    private void rehash(){
        //print stats when it is true
        if(printStats){
            printStatistics();
        }
        //reset everything
        nelems = 0;
        collision = 0;
        longest = 0;
        //clear out the table
        for (int i = 0; i < capacity / 2; i++) {
            nodes.get(i).clear();
        }
        //reinsert items
        for (String item : items) {
            insertHelper(item, true);
        }
    }

    /**
     * private helper method 
     * to print the statistics after each expansion.
     * This method will be called only if printStats=true
     */
    private void printStatistics(){
        File file = new File(statsFileName);
        if(!file.exists()){
            try { //if file does not exist create a new one
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try(FileWriter writer = new FileWriter(file, true);){
        	//print the stats with correct format
            writer.write(String.format("%d resizes, load factor %.2f, %d collisions, %d longest chain \n",
                    expand, (float) nelems/capacity/2, collision, longest));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * private hash function calculate method
     * @param value of the item needed to be inserted
     * @return the index of the position in table
     */
    private int hash(String value){
        return Math.abs(value.hashCode() % capacity);
    }

}
