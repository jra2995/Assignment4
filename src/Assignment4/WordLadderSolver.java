/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Jett Anderson and Aria Pahlavan
 * eid: jra2995, ap44342
 * @version 1.00 2016-03-01
 */

package Assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface {
    // declare class members here.
	/**
	 * the dictionary graph containing the words as nodes and edges between words as differences of one letter
	 * between words
	 */
	private Dictionary dictionary;
    
	// add a constructor for this object. HINT: it would be a good idea to set up the dictionary there
	/**
	 * Makes a new WordLadderSolver capable of solving a word ladder given a dictionary file name as input to 
	 * initialize the dictionary. Dictionary will only consist of Dictionary.WORD_SIZE letter words, in this case
	 * @param dictionaryName the file name for the dictionary's nodes to be initialized with 
	 */
	public WordLadderSolver(String dictionaryName){
		// Allows for method-wide access of file and scanner so as to throw no errors and allow for file closing
		// (It was giving me errors having scanner not initialized outside the try-catch
		File file = new File(dictionaryName);
		Scanner scan = null;
		ArrayList<String> list = new ArrayList<String>();
		
		// Initializes scanner and begins to read in the dictionary input from the file if possible
		try{
			scan = new Scanner(file);
			
			// Reads in the dictionary input from the file
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				
				// Makes sure that no *'d lines accidently get read in as words from the Stanford dictionary file
				if(line.charAt(0) != '*'){
					String word = line.substring(0, Dictionary.WORD_SIZE);
					list.add(word);
				}
			}
		}
		catch(FileNotFoundException fnfe){
			// If we get here, the dictionary filename was incorrect, or the file is not visible to the program hierarchy
			fnfe.printStackTrace();
			System.err.println("Error - File " + file + " not found.");
		}
		finally{
			// Closes the file scanner if it has been read from
			if(scan != null){
				scan.close();
			}
		}
		
		// Creates the new dictionary object based off of the input from the dictionary text file
		dictionary = new Dictionary(list);
	}
    // do not change signature of the method implemented from the interface
    @Override
    /**
     * Computes a word ladder for a given start word and end word, if possible
     * @param startWord the start word for the desired word ladder
     * @param endWord the end word for the desired word ladder
     * @returns an ArrayList representing the word ladder if possible
     * @throws NoSuchLadderException if no word ladder can be made between the two start and end words
     */
    public ArrayList<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException
    {
    	ArrayList<String> ladder = new ArrayList<String>();
    	ladder = makeLadder(startWord, endWord, 0, ladder);
    	ladder = correctWordLadder(ladder);
    	
    	// If no ladder can be made, i.e. ladder size is 0, throw the no such ladder exception
    	if(ladder.size() == 0){
    		throw new NoSuchLadderException("A word ladder does not exist for the words " + 
					startWord + " and " + endWord + ".\n*****");
    	}
    	return ladder;
    }

    @Override
    /**
     * Checks to make sure the wordLadder provided in the parameter list is a valid wordLadder from 
     * startWord to endWord
     * @param startWord the start word of the word ladder
     * @param endWord the end word of the word ladder
     * @param wordLadder the actual word ladder to be checked for correctness
     * @returns true if the wordLadder is valid, false otherwise
     */
    public boolean validateResult(String startWord, String endWord, ArrayList<String> wordLadder)
    {
    	// Checks to make sure start word of the wordLadder is the same as startWord
    	if(!wordLadder.get(0).equals(startWord)){
    		return false;
    	}
    	
    	// Checks to make sure end word of the wordLadder is the same as endWord
    	if(!wordLadder.get(wordLadder.size() - 1).equals(endWord)){
    		return false;
    	}
    	
    	// Makes sure there is a difference of only one letter between all adjacent words in the word ladder
    	for(int i = 0; i < wordLadder.size() - 1; i++){
    		if(!differByOne(wordLadder.get(i), wordLadder.get(i + 1))){
    			return false;
    		}
    	}
        
    	// If survived all the other checks, it is a valid word ladder
    	return true;
    }

    // add additional methods here
    /**
     * Constructs the word ladder from the start word, end word, position changed between the words, and the 
     * trace of the current ladder being worked on recursively by a type of DFS search that returns the first available
     * word ladder between the start word and the end word (NOT necessarily the shortest word ladder)
     * @param startWord the start word for the desired word ladder
     * @param endWord the end word for the desired word ladder
     * @param positionChanged the most recent position change between the intermediate words forming the word ladder
     * @param currentLadder the trace of the current ladder so we can keep track of what words were already fitted into
     * the word ladder
     * @return an empty ArrayList if no word ladder can be made, otherwise returns the ArrayList of strings representing
     * the word ladder
     */
    private ArrayList<String> makeLadder(String startWord, String endWord, int positionChanged, ArrayList<String> currentLadder){
    	// Sets up the word ladder as a blank list
    	ArrayList<String> ladder = new ArrayList<String>();
    	
    	// If the start word and end word are the same, return a list with the word in it
    	if(startWord.equals(endWord)){
    		ladder.add(startWord);
    		return ladder;
    	}
    	
    	// Otherwise we are going to add the startWord anyway to the list to begin our recursion
    	ladder.add(startWord);
    	
    	// If the start word and end word are off by one letter only, we return a list with the start word and end word
    	if(Dictionary.differByOneLetter(startWord, endWord)){
    		ladder.add(endWord);
    		return ladder;
    	}
    	
    	ArrayList<String> tempSolutions = dictionary.getEdgesFrom(startWord);
    	tempSolutions = sortByDifferencesAscendingOrder(tempSolutions, endWord);
    	for(int i = 0; i < tempSolutions.size(); i++){
    		if(differByOne(tempSolutions.get(i), endWord)){
    			ladder.add(tempSolutions.get(i));
    			ladder.add(endWord);
    			return ladder;
    		}
    	}
    	
    	for(int i = 0; i < tempSolutions.size(); i++){
    		int posChanged = getPositionChanged(tempSolutions.get(i), startWord);
    		if(!currentLadder.contains(tempSolutions.get(i))){
    			currentLadder.addAll(ladder);
    			ArrayList<String> tempLadder = makeLadder(tempSolutions.get(i), endWord, posChanged, currentLadder);
    			if(tempLadder.get(tempLadder.size() - 1).equals(endWord)){
    				ladder.addAll(tempLadder);
    				return ladder;
    			}
    		}
    	}
    	
    	return ladder;
    }
    
    private boolean differByOne(String one, String two){
    	int distance = 0;
    	for(int i = 0; i < Dictionary.WORD_SIZE; i++){
    		if(one.charAt(i) != two.charAt(i)){
    			distance++;
    		}
    	}
    	if(distance == 1){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    private int getPositionChanged(String one, String two){
    	for(int i = 0; i < Dictionary.WORD_SIZE; i++){
    		if(one.charAt(i) != two.charAt(i)){
    			return i;
    		}
    	}
    	return -1;
    }
    
    private ArrayList<String> sortByDifferencesAscendingOrder(ArrayList<String> list, String endWord){
    	ArrayList<Integer> distances = new ArrayList<Integer>();
    	for(int i = 0; i < list.size(); i++){
    		int tempDistance = 0;
    		for(int j = 0; j < Dictionary.WORD_SIZE; j++){
    			if(list.get(i).charAt(j) != endWord.charAt(j)){
    				tempDistance++;
    			}
    		}
    		distances.add(tempDistance);
    	}
    	for(int i = 0; i < distances.size(); i++){
    		for(int j = i; j < distances.size(); j++){
    			if(distances.get(j) < distances.get(i)){
    				int swapped = distances.get(i);
    				distances.set(i, distances.get(j));
    				distances.set(j, swapped);
    				String switched = list.get(i);
    				list.set(i, list.get(j));
    				list.set(j, switched);
    			}
    		}
    		//list.set(i, distances.get(i) + list.get(i));
    	}
    	return list;
    }
    
    private ArrayList<String> correctWordLadder(ArrayList<String> ladder){
    	for(int i = 0; i < ladder.size(); i++){
    		if(i + 1 < ladder.size() - 1){
    			if(i + 2 < ladder.size() - 1){
    				if(getPositionChanged(ladder.get(i), ladder.get(i + 1)) == getPositionChanged(ladder.get(i + 1), ladder.get(i + 2))){
    					ladder.remove(i + 1);
    					i--;
    				}
    			}
    		}
    	}
    	return ladder;
    }
}
