/**
 * Name: Jiaqi Fan
 * ID: A12584051
 * Login: cs12sju
 */
package hw8;

import static org.junit.Assert.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.junit.*;
/**
 * class for a hash table tester 
 * @author Jiaqi Fan
 * @version 1.0
 * @since 5/23/2016
 */
public class HashTableTester {
	private HashTable hashTable;
	private HashTable hashTable2;
	private String test = "test";
    
    @Before
    public void setUp() {
    	hashTable = new HashTable(13); //make a size 13 table
    	hashTable2 = new HashTable(13, test);
    }
    
    /**
     * test for insert method with null pointer exception
     * for both ctors hashtable
     */
    @Test (expected = NullPointerException.class)
	public void testInsertNPE() {
    hashTable.insert(null); //add null
    fail("can not insert null");
    hashTable2.insert(null);
    fail("can not insert null");
    }
    
    /**
     * test for insert method
     * for both ctors hastable
     */
    @Test
    public void testInsert(){
    	for(int i=0;i<100;i++){
    		// add 100 numbers
    		assertTrue(hashTable.insert(""+i));
    		assertTrue(hashTable2.insert(""+i));
    	}
		for(int i=0;i<100;i++){
			// add 100 repeat numbers
			assertFalse(hashTable.insert(""+i));
			assertFalse(hashTable2.insert(""+i));
		}
    }
    
    /**
     * test for delete method with null pointer exception
     * for both ctors hashtable
     */
    @Test (expected = NullPointerException.class)
	public void testDeleteNPE() {
    hashTable.delete(null); //delete null
    fail("can not delete null");
    hashTable2.delete(null);
    fail("can not delete null");
	}
    
    /**
     * test for delete method
     * for both ctors hastable
     */
    @Test
    public void testDelete(){
    	for(int i=0;i<100;i++){
    		//delete something that is not in the list
    		assertFalse(hashTable.delete(""+i));
    		assertFalse(hashTable2.delete(""+i));
    	}
    	//add 1- 100 to the list
		for(int i=0;i<100;i++){
			hashTable.insert(""+i);
			hashTable2.insert(""+i);
		}
		for(int i=0;i<100;i++){
			//we could delete it
			assertTrue(hashTable.delete(""+i));
			assertTrue(hashTable2.delete(""+i));
		}
    }
    
    /**
     * test for look up method with null pointer exception
     * for both ctors hashtable
     */
    @Test (expected = NullPointerException.class)
	public void testLookupNPE() {
    hashTable.lookup(null); //loop up a null
    fail("can not insert null"); //fail
    hashTable2.lookup(null);
    fail("can not insert null");
	}
    
    /**
     * test for look up method
     * for both ctors hastable
     */
    @Test
    public void testLoopUp(){
    	for(int i=0;i<100;i++){
    		//loop up for something that is not in the list
    		assertFalse(hashTable.lookup(""+i));
    		assertFalse(hashTable2.lookup(""+i));
    	}
    	
		for(int i=0;i<100;i++){
			//add 1-100 to the list
			hashTable.insert(""+i);
			hashTable2.insert(""+i);
		}
		for(int i=0;i<100;i++){
			//we could lookup it
			assertTrue(hashTable.lookup(""+i));
			assertTrue(hashTable.lookup(""+i));
		}
    }
    /**
     * test get size
     * for both ctor hastable
     */
    @Test
    public void testGetSize(){
    	for(int i = 0; i < 100; i ++){
    	// add 100 element to the list
    	hashTable.insert(""+i);
    	hashTable2.insert(""+i);
    	}
    	int test = 100;
    	//size should be 100
    	assertEquals(test, hashTable.getSize());
    	assertEquals(test, hashTable2.getSize());
    }
    /**
     * test for print table
     */
    @Test
    public void testPrintTable(){
    	//insert and print
    	hashTable.insert("i");
    	hashTable.insert("think");
    	hashTable.insert("this");
    	hashTable.insert("test");
    	hashTable.insert("is");
    	hashTable.insert("cool");
    	hashTable.printTable();
    	System.out.println(); //insert a new line
    	//insert and print
    	hashTable2.insert("i");
    	hashTable2.insert("love");
    	hashTable2.insert("cse");
    	hashTable2.insert("12");
    	hashTable2.insert("very");
    	hashTable2.insert("much");
    	hashTable2.printTable();
    }
    
}
