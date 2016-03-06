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

import static junit.framework.TestCase.assertEquals;

public class InvalidInputTest {
    //path to the input files
    private static final String DICTIONARY_PATH = "Junit Tests/A4words.dat";
    private static final String INVALID_TEST_PATH = "Junit Tests/JunitInvalidTest.txt";


    @Test
    /**
     * This method will test the program against invalid inputs to ensure
     * the smoothness of error-handling of the program.
     *
     * @throws NoSuchLadderException
     */
    public void testInvalidInputTest() throws NoSuchLadderException {
        // Testing the word ladder program using a test file

        // Create a file opener object
        FileReader fileReader = new FileReader();

        // Create a word ladder solver object
        WordLadderSolver wordLadderSolver = new WordLadderSolver(fileReader.readDictionaryFile(DICTIONARY_PATH));

        //getting the list of words to be matched
        ArrayList<String> lines = fileReader.readTestFile(INVALID_TEST_PATH);


        // Cycles through input test cases and make sure they are all invalid
        for ( int i = 0; i < lines.size(); i++ ) {

            //Testing our input pattern
            if ( !lines.get(i).matches("[a-z]{5}(((( )|(\\t))(( +)?(\\t+)?|(\\t+)?( +)?)+))[a-z]{5}") ) {
                if ( !lines.get(i).equals("") ) {
                    System.err.println("Error - Invalid Input: " + lines.get(i) + "\nInput must be in the form: " +
                            "\"[5-letter lowercase word] [5-letter lowercase word]\".\n");
                }
            } else {
                //If the test reaches this point, there is a bug in our code.
                assertEquals("NOT", "REACHED");
            }

        }

    }

}
