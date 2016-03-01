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
    	ladder = makeLadder(startWord, endWord, 0, ladder);
    	ladder = correctWordLadder(ladder);
    	return ladder;
        // implement this method
        //throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, ArrayList<String> wordLadder)
    {
    	if(!wordLadder.get(0).equals(startWord)){
    		return false;
    	}
    	
    	if(!wordLadder.get(wordLadder.size() - 1).equals(endWord)){
    		return false;
    	}
    	
    	for(int i = 0; i < wordLadder.size() - 1; i++){
    		if(!differByOne(wordLadder.get(i), wordLadder.get(i + 1))){
    			return false;
    		}
    	}
        
    	return true;
    }

    // add additional methods here
    private ArrayList<String> makeLadder(String startWord, String endWord, int positionChanged, ArrayList<String> currentLadder){
    	ArrayList<String> ladder = new ArrayList<String>();
    	if(startWord.equals(endWord)){
    		ladder.add(startWord);
    		return ladder;
    	}
    	
    	ladder.add(startWord);
    	
    	if(differByOne(startWord, endWord)){
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
    	
    	//int min = dictionary.getDictionary().size();
    	
    	//ArrayList<ArrayList<String>> tempPaths = new ArrayList<ArrayList<String>>();
    	for(int i = 0; i < tempSolutions.size(); i++){
    		int posChanged = getPositionChanged(tempSolutions.get(i), startWord);
    		if(/*posChanged != positionChanged &&*/ !currentLadder.contains(tempSolutions.get(i))){
    			currentLadder.addAll(ladder);
    			ArrayList<String> tempLadder = makeLadder(tempSolutions.get(i), endWord, posChanged, currentLadder);
    			//tempPaths.add(tempLadder);
    			if(tempLadder.get(tempLadder.size() - 1).equals(endWord)/* && tempLadder.size() == 2*/){
    				ladder.addAll(tempLadder);
    				return ladder;
    			}
    			//currentLadder.removeAll(ladder);
    		}
    	}
    	
    	/*for(int j = 0; j < tempPaths.size(); j++){
    		if(tempPaths.get(j).size() < min){
    			min = tempPaths.get(j).size();
    			ladder = tempPaths.get(j);
    		}
    	}*/
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
    
    private ArrayList<String> sortByDifferencesAscendingOrder(ArrayList<String> list, String endWord){
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
