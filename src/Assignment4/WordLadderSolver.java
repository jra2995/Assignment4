/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Jett Anderson and Aria Pahlavan
 * eid: jra2995, ap44342
 * @version 1.00 2016-03-01
 */

package Assignment4;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class WordLadderSolver implements Assignment4Interface{
    // Member fields:
	/**
	 * the dictionary graph containing the words as nodes and edges between words as differences of one letter
	 * between words
	 */
	private Dictionary dictionary;
    
	// Constructors:
	/**
	 * Custom constructor - Makes a new WordLadderSolver capable of solving a word ladder given a dictionary file name as input to
	 * initialize the dictionary. Dictionary will only consist of Dictionary.WORD_SIZE letter words, in this case
	 *
	 * @param list
	 * @throws FileNotFoundException
	 *         if an error occurs while attempting to
	 *         open the dictionary file.
	 */
	public WordLadderSolver(ArrayList<String> list) {

		//initializing dictionary
		list.trimToSize();
		dictionary = new Dictionary(list);

	}
	
	/**
	 * Returns the dictionary object for this particular wordLadderSolver object
	 * @return the dictionary object for this particular wordLadderSolver object
	 */
	public Dictionary getDictionary(){
		return dictionary;
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
    	ladder = makeLadder(startWord, endWord, ladder);
    	ladder = correctWordLadder(ladder);
    	
    	// If no ladder can be made, i.e. ladder size is 0, throw the no such ladder exception
    	if(ladder.size() == 0 || (ladder.size() == 1 && !startWord.equals(endWord))){
    		throw new NoSuchLadderException("A word ladder does not exist for the words " + 
					startWord + " and " + endWord + ".\n");
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
    		if(!dictionary.isEdge(wordLadder.get(i), wordLadder.get(i + 1))){
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
     * @param currentLadder the trace of the current ladder so we can keep track of what words were already fitted into
     * the word ladder
     * @return an empty ArrayList if no word ladder can be made, otherwise returns the ArrayList of strings representing
     * the word ladder
     */
    private ArrayList<String> makeLadder(String startWord, String endWord, ArrayList<String> currentLadder){
    	// Sets up the word ladder as a blank list
    	ArrayList<String> ladder = new ArrayList<String>();
    	
    	// If the start word and end word are the same, return a list with the word in it
    	if(startWord.equals(endWord)){
    		ladder.add(startWord);
    		return ladder;
    	}u
    	
    	// Otherwise we are going to add the startWord anyway to the list to begin our recursion
    	ladder.add(startWord);
    	
    	// If the start word and end word are off by one letter only, we return a list with the start word and end word
    	if(dictionary.isEdge(startWord, endWord)){
    		ladder.add(endWord);
    		return ladder;
    	}
    	
    	// Gets all edges from the startWord and sorts them by proximity (fewest letters away from) the endWord
    	ArrayList<String> tempSolutions = dictionary.getEdgesFrom(startWord);
    	tempSolutions = sortByDifferencesAscendingOrder(tempSolutions, endWord);
    	
    	// If any of the edges are connected to words that are one letter away from the end word, 
    	// add the connecting word and end word to the ladder and return the ladder (because we've found a solution)
    	for(int i = 0; i < tempSolutions.size(); i++){
    		if(dictionary.isEdge(tempSolutions.get(i), endWord)){
    			ladder.add(tempSolutions.get(i));
    			ladder.add(endWord);
    			return ladder;
    		}
    	}
    	
    	// Cycles through edges one by one looking for words that change letters
    	for(int i = 0; i < tempSolutions.size(); i++){
    		int posChanged = getPositionChanged(tempSolutions.get(i), startWord);
    		
    		// If the word to test is not in the current word ladder for solutions, add it to the DFS word ladder
    		if(!currentLadder.contains(tempSolutions.get(i))){
    			// Recurses using the given word after adding the word to the in process ladder
    			currentLadder.addAll(ladder);
    			ArrayList<String> tempLadder = makeLadder(tempSolutions.get(i), endWord, currentLadder);
    			
    			// If the ladder has returned from recursion with the last word being the end word, we have a correct 
    			// ladder that we need to start adding the pieces together with
    			if(tempLadder.get(tempLadder.size() - 1).equals(endWord)){
    				ladder.addAll(tempLadder);
    				return ladder;
    			}
    		}
    	}
    	
    	// Return the whole ladder at the end of recursion
    	return ladder;
    }
    
    /**
     * Checks the first letter position changed between two words and returns the index of that position
     * @param one the first word to check against
     * @param two the second word to check against
     * @return the integer index position that is changed between the two words
     */
    private int getPositionChanged(String one, String two){
    	for(int i = 0; i < Dictionary.WORD_SIZE; i++){
    		if(one.charAt(i) != two.charAt(i)){
    			return i;
    		}
    	}
    	return -1;
    }
    
    /**
     * Sorts an ArrayList in ascending order starting with the word that is closest (has the fewest letters different)
     * to the string endWord to the word that is the farthest, or most letters different from the string endWord and 
     * returns the sorted list
     * @param list the ArrayList to be sorted in ascending order
     * @param endWord the endWord to compare the words in list to
     * @return the sorted list in ascending order with respect to differences from endWord
     */
    private ArrayList<String> sortByDifferencesAscendingOrder(ArrayList<String> list, String endWord){
    	// Creates a list of integer distances that correspond to the distances of the words in list from endWord
    	ArrayList<Integer> distances = new ArrayList<Integer>();
    	for(int i = 0; i < list.size(); i++){
    		int tempDistance = 0;
    		
    		// Counts the number of letters that are different from endWord
    		for(int j = 0; j < Dictionary.WORD_SIZE; j++){
    			if(list.get(i).charAt(j) != endWord.charAt(j)){
    				tempDistance++;
    			}
    		}
    		distances.add(tempDistance);
    	}
    	
    	// Standard bubble sort that sorts both the String list and distances list in O(n^2) time
    	for(int i = 0; i < distances.size(); i++){
    		for(int j = i; j < distances.size(); j++){
    			
    			// Sorts in ascending order by using < test, and sorts both integer and String lists by swapping elements
    			// as appropriate
    			if(distances.get(j) < distances.get(i)){
    				int swapped = distances.get(i);
    				distances.set(i, distances.get(j));
    				distances.set(j, swapped);
    				String switched = list.get(i);
    				list.set(i, list.get(j));
    				list.set(j, switched);
    			}
    		}
    	}
    	
    	// Returns the sorted list by this point
    	return list;
    }
    
    /**
     * Corrects the word ladder by removing any words from the ladder that are cycles of three
     * For example, tolls, tills, and tells are a cycle of three because they all have the same position changed between
     * the three words in sequence, so if a cycle of three comes up in a word ladder, the word in the middle is 
     * redundant and needs to be removed, or can be removed.
     * @param ladder the word ladder to be checked and corrected, if necessary
     * @return the corrected word ladder
     */
    private ArrayList<String> correctWordLadder(ArrayList<String> ladder){
    	// Cycles through the entire list to check for cycles of three
    	for(int i = 0; i < ladder.size(); i++){
    		// If there are at least three words in the word ladder, we have the potential for unnecessary words
    		if(i + 1 < ladder.size() - 1){
    			if(i + 2 < ladder.size() - 1){
    				// If the position between all three words in a row is the same, remove the middle one
    				// since it is unnecessary and correct the loop index to account for the removed element from the list
    				if(getPositionChanged(ladder.get(i), ladder.get(i + 1)) == getPositionChanged(ladder.get(i + 1), 
    						ladder.get(i + 2))){
    					ladder.remove(i + 1);
    					i--;
    				}
    			}
    		}
    	}
    	return ladder;
    }
}
