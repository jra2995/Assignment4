/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Jett Anderson and Aria Pahlavan
 * eid: jra2995, ap44342
 * @version 1.00 2016-03-01
 */

package Assignment4;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Declare a word ladder solver object
    	Assignment4Interface wordLadderSolver;
    	
    	// Instantiate scanner to null reference to allow for closing compatibility with try-catch block
    	Scanner scan = null;
    	
    	// Try to create a WordLadderSolver and run through input test cases
    	try{
    		// Set up the new WordLadderSolver with the dictionary filename input from command line
    		wordLadderSolver = new WordLadderSolver(args[0]);
    		
    		// Set up the file with the input file name input from command line
    		File input = new File(args[1]);
    		ArrayList<String> lines = new ArrayList<String>();
    		
    		// Tries to set up a scanner to read in the input test cases from a file
    		try{
    			// Adds lines to the input test case String array if possible
    			scan = new Scanner(input);
				while(scan.hasNextLine()){
					String line = scan.nextLine();
					lines.add(line);
				}
    		}
    		catch(FileNotFoundException fnfe){
    			// File name was not correct or not visible from its current position in file system hierarchy
    			System.err.println("File " + args[1] + " was not found.");
    			System.exit(0);
    		}
    		
    		// Cycles through input test cases and checks for valid input to run a word ladder solver on
    		for(int i = 0; i < lines.size(); i++){
    			// Input has to be of form [5-letter lowercase word] [5-letter lowercase word] only, with 
    			// one space in the middle and nothing after it
    			if(!lines.get(i).matches("[a-z]{5} [a-z]{5}")){
    				System.err.println("Error - Invalid input. Input must be in the form: " + 
    						"\"[5-letter lowercase word] [5-letter lowercase word]\".\n");
    			}
    			else{
    				// Splits line around the space to get start word and end word
    				String[] words = lines.get(i).split(" ");
    				String startWord = words[0];
    				String endWord = words[1];
    				
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
        							endWord + ":");
        					for(int j = 0; j < result.size(); j++){
        						System.out.println(result.get(j));
        						
        					}
        					System.out.println("*****\n");
        				}
    				}
    			}
    		}
    		//ArrayList<String> result = wordLadderSolver.computeLadder("stone", "money");
    		//System.out.println(result);
            //boolean correct = wordLadderSolver.validateResult("money", "honey", result);
    	}
        catch (ArrayIndexOutOfBoundsException a){
        	// If we get here, one of the command line arguments for the filenames is missing from the run
        	// configuration or entered incorrectly from command line
        	System.err.println("One of the command line file arguments is missing.\nClosing program.");
        	if(scan != null){
        		scan.close();
        		
        	}
        }
    	finally{
    		// Closes scanner as part of exiting the program
    		if(scan != null){
    			scan.close();
    		}
    	}
    }
}
