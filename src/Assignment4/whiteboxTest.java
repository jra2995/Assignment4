package Assignment4;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Aria Pahlavan and Jett Anderson
 * eID: ap44342, jra2995
 * @version 1.00 2016-03-01
 */
public class whiteboxTest {
    //test dictionary file
    ArrayList<String> myDictionary = new ArrayList<>();

    //actual values of edges
    final String[] edges = { "scone", "shone" };

    //test field
    Dictionary dictionary;


    @Test
    /**
     * This method is only used to test the graph creator method and ensures
     * that all dictionary words are being stored asa a graph vertex and also
     * all of their edges are only off by one letter.
     */
    public void testGraph() {
        init();
        dictionary = new Dictionary(myDictionary);
        ArrayList<String> graph = dictionary.getNodes();


        //Testing vertices list
        for ( int i = 0; i < myDictionary.size(); i++ ) {
            assertEquals(graph.get(i), myDictionary.get(i));
        }

        //Testing edges
        ArrayList<String> firstEdge= dictionary.getEdgesFrom(graph.get(0));
        for ( int i = 0; i < edges.length; i++ ) {
            assertEquals(firstEdge.get(i), edges[i]);
        }

    }


    @Test
    /**
     * This method is only used to test the ladder maker method and
     * ensures that the most optimized ladder is computed.
     */
    public void testMAkeLadder() throws NoSuchLadderException {
        testGraph();

        WordLadderSolver wordLadderSolver = new WordLadderSolver(myDictionary);


        ArrayList<String> solution = wordLadderSolver.computeLadder("stone", "money");

        //Testing word ladder maker
        for (String ladderStep : solution)
            System.out.println(ladderStep);


    }

    private void init() {
        myDictionary.add("stone");
        myDictionary.add("seems");
        myDictionary.add("scone");
        myDictionary.add("morey");
        myDictionary.add("alone");
        myDictionary.add("honey");
        myDictionary.add("atoms");
        myDictionary.add("bogey");
        myDictionary.add("beady");
        myDictionary.add("money");
        myDictionary.add("conns");
        myDictionary.add("sunny");
        myDictionary.add("toner");
        myDictionary.add("shone");
        myDictionary.add("beads");
        myDictionary.add("creep");
        myDictionary.add("atone");
        myDictionary.add("smart");
        myDictionary.add("tones");
        myDictionary.add("mosey");
        myDictionary.add("angel");
        myDictionary.add("clons");
        myDictionary.add("coins");
        myDictionary.add("bones");
        myDictionary.add("cones");
        myDictionary.add("devil");
        myDictionary.add("coons");
        myDictionary.add("alane");
        myDictionary.add("clane");
        myDictionary.add("funny");
        myDictionary.add("zebra");
        myDictionary.add("flans");
        myDictionary.add("moons");
        myDictionary.add("atlas");
        myDictionary.add("child");
        myDictionary.add("dooms");
        myDictionary.add("clans");
        myDictionary.add("coney");
        myDictionary.add("buddy");
        myDictionary.add("heart");
        myDictionary.add("clone");
    }

}