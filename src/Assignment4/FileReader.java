/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Aria Pahlavan and Jett Anderson
 * eID: ap44342, jra2995
 * @version 1.00 2016-03-01
 */
package Assignment4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Assignment4
 * Created by Aria Pahlavan on Mar 2016.
 */
public class FileReader {

    public ArrayList<String> readDictionaryFile(String filename) {
        // Allows for method-wide access of file and scanner so as to throw no errors and allow for file closing

        //Opening file and scanning the contents
        File file = new File(filename);

        // Instantiate scanner to null reference to allow for closing compatibility with try-catch block
        Scanner scan = null;
        ArrayList<String> list = new ArrayList<String>();
        try {
            scan = new Scanner(file);

            //if a line does not start with an '*', the first 5 letters
            //are copied into the list.
            while ( scan.hasNextLine() ) {
                String line = scan.nextLine();
                if ( line.charAt(0) != '*' ) {
                    String word = line.substring(0, 5);
                    list.add(word);
                }
            }
        } catch (FileNotFoundException fnfe) {
            //File error handling
            System.err.println("Error - File " + file + " not found.");
        } finally {
            //In case an error occurs, close the scanner safely.
            if ( scan != null ) {
                scan.close();
            }
        }

        return list;
    }

    public ArrayList<String> readTestFile(String filename) {
        //Opening file and scanning the contents
        File file = new File(filename);

        // Instantiate scanner to null reference to allow for closing compatibility with try-catch block
        Scanner scan = null;
        ArrayList<String> lines = new ArrayList<String>();


        try {
            scan = new Scanner(file);

            while ( scan.hasNext() ) {


                // Adds lines to the input test case String array if possible
                while ( scan.hasNextLine() ) {
                    String line = scan.nextLine();
                    lines.add(line);
                }


//                StringBuffer word = new StringBuffer(scan.next());
//                word.append(" ");
//                word.append(scan.next());
//
//                if ( word.toString().matches("[a-z]{5}( +)?(\t+)?[a-z]{5}") && !scan.hasNext("[^\\n]"))
//                    lines.add(word.toString());
//                else System.err.println("Error - Invalid Input found: " + word.toString());


            }
        } catch (FileNotFoundException fnfe) {
            // File name was not correct or not visible from its current position in file system hierarchy
            System.err.println("Error - File " + filename + " was not found.");
            System.exit(0);
        } finally {
            //Close the scanner safely.
            if ( scan != null ) {
                scan.close();
            }
        }

        return lines;
    }

}
