/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Aria Pahlavan and Jett Anderson
 * eID: ap44342, jra2995
 * @version 1.00 2016-03-01
 */

package Assignment4;

import java.util.ArrayList;
import java.util.Scanner;

public class A4Driver
{
    public static void main(String[] args)
    {

	    Scanner scanner = null;


    	// Try to create a WordLadderSolver and run through input test cases
    	try{

		    // Create a file opener object
		    FileReader fileReader = new FileReader();

		    // Create a word ladder solver object
		    WordLadderSolver wordLadderSolver = new WordLadderSolver(fileReader.readDictionaryFile(args[0]));

		    //getting the list of words to be matched
		    ArrayList<String> lines = fileReader.readTestFile(args[1]);


    		// Cycles through input test cases and checks for valid input to run a word ladder solver on
    		for(int i = 0; i < lines.size(); i++){
    			// Input has to be of form [5-letter lowercase word] [5-letter lowercase word] only, with 
    			// one space in the middle and nothing after it
    			if(!lines.get(i).matches("[a-z]{5}(((( )|(\\t))(( +)?(\\t+)?|(\\t+)?( +)?)+))[a-z]{5}")){
				    if ( !lines.get(i).equals("") ) {
					    System.err.println("Error - Invalid Input: " + lines.get(i) + "\nInput must be in the form: " +
							    "\"[5-letter lowercase word] [5-letter lowercase word]\".\n");
				    }
    			}
    			else{
				    scanner = new Scanner(lines.get(i));

				    String startWord = scanner.next();
				    String endWord = scanner.next();
    				
    				// Checks to see if the 5-letter lowercase words are actually dictionary entries
    				if(!((WordLadderSolver)wordLadderSolver).getDictionary().search(startWord) || !((WordLadderSolver)wordLadderSolver).getDictionary().search(endWord)){
    					System.err.println("Error - at least one of " + startWord + " or " + endWord + 
    							" is not a " + Dictionary.WORD_SIZE + "-letter word dictionary entry.");
    				}
    				else{
    					// Actually computes the ladder if possible from the given start word and end word and validates
        				// the result
        				ArrayList<String> result = null;
        				boolean correct = false;
        				try{
        					result = wordLadderSolver.computeLadder(startWord, endWord);
        					correct = wordLadderSolver.validateResult(startWord, endWord, result);
        				}
        				catch(NoSuchLadderException nsle){
        					// If we get here, no ladder exists, so we print an error message saying the 
        					// word ladder doesn't exist
        					Throwable error = nsle;
        					System.err.println(error.getMessage());
        					correct = false;
        				}
        				
        				// Prints the resulting word ladder, one word per line if it is a correct word ladder
        				if(correct){
        					System.out.println("The following word ladder exists between " + startWord + " and " + 
        							endWord + " (" + ( result.size() -1 ) + "-Step Solution):");
        					for(int j = 0; j < result.size(); j++){
        						System.out.println(result.get(j));
        						
        					}
        					System.out.println("*****\n");
        				}
    				}
    			}
    		}
    	}
        catch (ArrayIndexOutOfBoundsException a){
        	// If we get here, one of the command line arguments for the filenames is missing from the run
        	// configuration or entered incorrectly from command line
        	System.err.println("One of the command line file arguments is missing.\nClosing program.");
        	if(scanner != null){
        		scanner.close();

        	}
        }
    	finally{
    		// Closes scanner as part of exiting the program
    		if(scanner != null){
    			scanner.close();
    		}
    	}
    }
}
