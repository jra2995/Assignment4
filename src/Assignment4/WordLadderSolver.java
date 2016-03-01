/*
    ADD YOUR HEADER HERE
 */

package Assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// do not change class name or interface it implements
public class WordLadderSolver implements Assignment4Interface {
    // delcare class members here.
	private static final int WORD_SIZE = 5;
	private Dictionary dictionary;
    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there
	public WordLadderSolver(String dictionaryName){
		File file = new File(dictionaryName);
		Scanner scan = null;
		ArrayList<String> list = new ArrayList<String>();
		try{
			scan = new Scanner(file);
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				if(line.charAt(0) != '*'){
					String word = line.substring(0, 5);
					list.add(word);
				}
			}
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
			System.err.println("Error - File " + file + " not found.");
		}
		finally{
			if(scan != null){
				scan.close();
			}
		}
		dictionary = new Dictionary(list);
	}
    // do not change signature of the method implemented from the interface
    @Override
    public ArrayList<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException
    {
    	ArrayList<String> ladder = new ArrayList<String>();
    	ladder = makeLadder(startWord, endWord, 0);
    	
        // implement this method
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, ArrayList<String> wordLadder)
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
    private ArrayList<String> makeLadder(String startWord, String endWord, int positionChanged){
    	ArrayList<String> ladder = new ArrayList<String>();
    	if(startWord.equals(endWord)){
    		return ladder;
    	}
    	
    	ladder.add(startWord);
    	
    	ArrayList<String> tempSolutions = dictionary.getEdgesFrom(startWord);
    	tempSolutions = sortByDifferencesDescendingOrder(tempSolutions, endWord);
    	for(int i = 0; i < tempSolutions.size(); i++){
    		if(differByOne(tempSolutions.get(i), endWord)){
    			ladder.add(endWord);
    			return ladder;
    		}
    	}
    	
    	
    	for(int i = 0; i < tempSolutions.size(); i++){
    		int posChanged = getPositionChanged(tempSolutions.get(i), endWord);
    		if(posChanged != positionChanged){
    			ArrayList<String> tempLadder = makeLadder(tempSolutions.get(i), endWord, posChanged);
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
    	for(int i = 0; i < WORD_SIZE; i++){
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
    	for(int i = 0; i < WORD_SIZE; i++){
    		if(one.charAt(i) != two.charAt(i)){
    			return i;
    		}
    	}
    	return -1;
    }
    
    private ArrayList<String> sortByDifferencesDescendingOrder(ArrayList<String> list, String endWord){
    	ArrayList<Integer> distances = new ArrayList<Integer>();
    	for(int i = 0; i < list.size(); i++){
    		int tempDistance = 0;
    		for(int j = 0; j < WORD_SIZE; j++){
    			if(list.get(i).charAt(j) != endWord.charAt(j)){
    				tempDistance++;
    			}
    		}
    		distances.add(tempDistance);
    	}
    	for(int i = 0; i < distances.size(); i++){
    		for(int j = i; j < distances.size(); j++){
    			if(distances.get(j) > distances.get(i)){
    				int swapped = distances.get(i);
    				distances.set(i, distances.get(j));
    				distances.set(j, swapped);
    				String switched = distances.get(j) + list.get(i);
    				list.set(i, distances.get(i) + list.get(j));
    				list.set(j, switched);
    			}
    		}
    	}
    	return list;
    }
}
