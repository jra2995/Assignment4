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
	private Dictionary dictionary;
	private String inputFileName;
    // add a constructor for this object. HINT: it would be a good idea to set up the dictionary there
	public WordLadderSolver(String dictionaryName, String inputName){
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
		inputFileName = inputName;
	}
    // do not change signature of the method implemented from the interface
    @Override
    public ArrayList<String> computeLadder(String startWord, String endWord) throws NoSuchLadderException
    {
        // implement this method
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public boolean validateResult(String startWord, String endWord, ArrayList<String> wordLadder)
    {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    // add additional methods here
    
}
