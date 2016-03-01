package Assignment4;

import java.util.ArrayList;

public class Assign4Driver
{
    public static void main(String[] args)
    {
        // Create a word ladder solver object
    	Assignment4Interface wordLadderSolver;
    	try{
    		wordLadderSolver = new WordLadderSolver(args[0]);
    		
    		ArrayList<String> result = wordLadderSolver.computeLadder("money", "honey");
            boolean correct = wordLadderSolver.validateResult("money", "honey", result);
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
