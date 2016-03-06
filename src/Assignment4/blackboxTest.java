/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 *
 * @author Aria Pahlavan and Jett Anderson
 * eID: ap44342, jra2995
 * @version 1.00 2016-03-01
 */
package Assignment4;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;

/**
 * Project4_WordLetters
 * Created by Aria Pahlavan on Mar 2016.
 */
public class blackboxTest {

    //path to the input files
    private static final String DICTIONARY_PATH = "Junit Tests/A4words.dat";
    private static final String TEST_PATH = "Junit Tests/JunitTest.txt";

    @Test
    /**
     * This method is only used for testing the program against basic cases
     * to ensure the test and analyze the algorithm used.
     *
     * @throws NoSuchLadderException
     */
    public void testWordLadderSolver() throws NoSuchLadderException {
        // Testing the word ladder program using a test file

        String[] actualResult = {"heads", "heals", "teals", "tells", "tolls", "toils", "tails"};

        // Create a file opener object
        FileReader fileReader = new FileReader();

        // Create a word ladder solver object
        WordLadderSolver wordLadderSolver = new WordLadderSolver(fileReader.readDictionaryFile(DICTIONARY_PATH));

        //getting the list of words to be matched
        ArrayList<String> lines = fileReader.readTestFile(TEST_PATH);


        // Cycles through input test cases and checks for valid input to run a word ladder solver on
        for ( int i = 0; i < lines.size(); i++ ) {
            // Input has to be of form [5-letter lowercase word] [5-letter lowercase word] only, with
            // one space in the middle and nothing after it

            if(!lines.get(i).matches("[a-z]{5}(((( )|(\\t))(( +)?(\\t+)?|(\\t+)?( +)?)+))[a-z]{5}")){
            }
            else {
                Scanner scanner = new Scanner(lines.get(i));

                String startWord = scanner.next();
                String endWord = scanner.next();


                // Actually computes the ladder if possible from the given start word and end word and validates
                // the expectedResult
                ArrayList<String> expectedResult = wordLadderSolver.computeLadder(startWord, endWord);
                boolean correct = wordLadderSolver.validateResult(startWord, endWord, expectedResult);

                // Prints the resulting word ladder, one word per line if it is a correct word ladder
                if ( correct ) {
                    for ( int j = 0; j < expectedResult.size(); j++ ) {
                        assertEquals(expectedResult.get(i), actualResult[i]);

                    }
                }

            }

        }

    }



}