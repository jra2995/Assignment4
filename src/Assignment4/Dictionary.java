/**
 * Classes to model a word ladder solver
 * Solves EE422C Programming Assignment #4
 * @author Jett Anderson and Aria Pahlavan
 * eid: jra2995, ap44342
 * @version 1.00 2016-03-01
 */

package Assignment4;

import java.util.ArrayList;

public class Dictionary {
	/**
	 * Represents all word lengths in the dictionary
	 */
	public static final int WORD_SIZE = 5;
	
	/**
	 * Holds the words of the dictionary as a list of String nodes
	 */
	private ArrayList<String> nodes;
	
	/**
	 * Represents a difference of one letter between any two words
	 * Adjacency list form of the edges for this "dictionary" graph
	 */
	private ArrayList<ArrayList<String>> edges;
	
	/**
	 * Initializes a new dictionary based on the given list, also making edges between the individual nodes
	 * @param list the list of words to become the nodes of this dictionary graph object
	 */
	public Dictionary(ArrayList<String> list) {
		nodes = list;
		edges = new ArrayList<>();
		makeEdges();
	}

	public ArrayList<String> getNodes() {
		return nodes;
	}

	/**
	 * Searches the dictionary nodes for a particular word and returns if the dictionary contains it
	 * @param word the word to be looked for in the dictionary
	 * @return true if the word is in the dictionary's nodes, false otherwise
	 */
	public boolean search(String word){
		return nodes.contains(word);
	}
	
	/**
	 * Accesses the list of node words in this dictionary and returns it
	 * @return the list of word nodes of this dictionary
	 */
	public ArrayList<String> getDictionary(){
		return nodes;
	}
	
	/**
	 * Accesses the list of adjacency lists for each node and returns it 
	 * @return the list of adjacency lists for each node
	 */
	public ArrayList<ArrayList<String>> getEdges(){
		return edges;
	}
	
	/**
	 * Returns the adjacency list (list of outgoing edges) of a particular word, where the edges
	 * are defined as differences of only one letter in the word
	 * @param word the word to get the outgoing edges list from
	 * @return the particular word's adjacency list
	 */
	public ArrayList<String> getEdgesFrom(String word){
		// Checks for a word being part of the nodes of this dictionary, and then gets the appropriate adjacency list
		// if available
		if(search(word)){
			int index = nodes.indexOf(word);
			return edges.get(index);
		}
		else{
			return null;
		}
	}
	
	/**
	 * Returns if there exists an edge in the dictionary graph between String one and String two,
	 * so if they differ by one letter, in other words
	 * @param one the first string to serve as the node getting outgoing edges from
	 * @param two the word that might be in the adjacency list of one
	 * @return true if there is an edge between the two, false otherwise
	 */
	public boolean isEdge(String one, String two){
		ArrayList<String> adjacencyList = getEdgesFrom(one);
		if(adjacencyList.contains(two)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * sets the node list of the dictionary to whatever list of words the input parameter has
	 * @param newDictionary the new list of words for the dictionary to hold as nodes
	 */
	public void setDictionary(ArrayList<String> newDictionary){
		nodes = newDictionary;
	}
	
	/**
	 * Makes edges between each node by comparing one word/node at a time with every other word/node (O(n^2) operation 
	 * unfortunately) and adding a word to an adjacency list if the two words differ by only one letter
	 */
	private void makeEdges(){
		// Standard pairwise iteration scheme with nested loops to compare one word at a time with every other word
		for(int i = 0; i < nodes.size(); i++){
			ArrayList<String> adjacencyList = new ArrayList<String>();
			for(int j = 0; j < nodes.size(); j++){
				// If the nodes being compared aren't the same word, determine if the words are one letter apart
				// and add an edge if appropriate to the first node's adjacency list
				if(!(nodes.get(i).equals(nodes.get(j)))){
					if(differByOneLetter(nodes.get(i), nodes.get(j))){
						adjacencyList.add(nodes.get(j));
					}
				}
			}
			edges.add(adjacencyList);
		}
	}
	
	/**
	 * Compares two words (assumed to be in the dictionary, but can be used for other purposes
	 * and returns a boolean representing whether the words differ by only one letter or not
	 * @param one the first string to be compared with
	 * @param two the second string to be compared with
	 * @return true if the strings differ by only one letter, false otherwise
	 */
	private boolean differByOneLetter(String one, String two){
		int distance = 0;
		
		// Compares characters one at a time, determining the number of letters different in the two words
    	for(int i = 0; i < WORD_SIZE; i++){
    		if(one.charAt(i) != two.charAt(i)){
    			distance++;
    		}
    	}
    	// Based on if the words differ by only one letter, return the desired boolean value
    	if(distance == 1){
    		return true;
    	}
    	else{
    		return false;
    	}
	}
}
