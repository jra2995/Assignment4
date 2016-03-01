package Assignment4;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Create a word ladder solver object
    	Assignment4Interface wordLadderSolver;
    	try{
    		wordLadderSolver = new WordLadderSolver(args[0]);
    		File input = new File(args[1]);
    		ArrayList<String> lines = new ArrayList<String>();
    		Scanner scan;
    		try{
    			scan = new Scanner(input);
				while(scan.hasNextLine()){
					String line = scan.nextLine();
					lines.add(line);
				}
    		}
    		catch(FileNotFoundException fnfe){
    			System.err.println("File " + args[1] + " was not found.");
    			System.exit(0);
    		}
    		
    		for(int i = 0; i < lines.size(); i++){
    			if(!lines.get(i).matches("[a-z]{5} [a-z]{5}")){
    				System.err.println("Error - Invalid input. Input must be in the form: " + 
    						"[5-letter lowercase word] [5-letter lowercase word].");
    			}
    			else{
    				String[] words = lines.get(i).split(" ");
    				String startWord = words[0];
    				String endWord = words[1];
    				ArrayList<String> result = wordLadderSolver.computeLadder(startWord, endWord);
    				boolean correct = wordLadderSolver.validateResult(startWord, endWord, result);
    				if(correct){
    					for(int j = 0; j < result.size(); j++){
    						System.out.println(result.get(j));
    						
    					}
    					System.out.println("*****");
    				}
    				else{
    					throw new NoSuchLadderException("A word ladder does not exist for the words " + 
    							startWord + " and " + endWord + ".\n*****");
    				}
    			}
    		}
    		//ArrayList<String> result = wordLadderSolver.computeLadder("stone", "money");
    		//System.out.println(result);
            //boolean correct = wordLadderSolver.validateResult("money", "honey", result);
    	}
        catch (ArrayIndexOutOfBoundsException a){
        	System.err.println("One of the command line file arguments is missing.\nClosing program.");
        	System.exit(0);
        }
    	catch (NoSuchLadderException e) 
        {
            e.printStackTrace();
        }
    }
}
