package Assignment4;

import java.util.ArrayList;

public class Dictionary {
	private static final int ALPHABET_SIZE = 26;
	private static final char LOWERCASE_A = 'a';
	private static final int WORD_SIZE = 5;
	private ArrayList<String> nodes;
	private ArrayList<ArrayList<String>> edges = new ArrayList<ArrayList<String>>();
	
	public Dictionary(ArrayList<String> list) {
		nodes = list;
		makeEdges();
	}
	
	public boolean search(String word){
		return nodes.contains(word);
	}
	
	public ArrayList<String> getDictionary(){
		return nodes;
	}
	
	public ArrayList<ArrayList<String>> getEdges(){
		return edges;
	}
	
	public ArrayList<String> getEdgesFrom(String word){
		if(search(word)){
			int index = nodes.indexOf(word);
			return edges.get(index);
		}
		else{
			return null;
		}
	}
	
	public void setDictionary(ArrayList<String> newDictionary){
		nodes = newDictionary;
	}
	
	private void makeEdges(){
		for(int i = 0; i < nodes.size(); i++){
			ArrayList<String> adjacencyList = new ArrayList<String>();
			for(int j = 0; j < nodes.size(); j++){
				if(!(nodes.get(i).equals(nodes.get(j)))){
					if(differByOneLetter(nodes.get(i), nodes.get(j))){
						adjacencyList.add(nodes.get(j));
					}
				}
			}
			edges.add(adjacencyList);
		}
	}
	
	private static boolean differByOneLetter(String one, String two){
		for(int index = 0; index < WORD_SIZE; index++){
			for(char c = LOWERCASE_A; c < ALPHABET_SIZE; c++){
				String regex = one.substring(0, index) + c + one.substring(index + 1, WORD_SIZE);
				if(regex.length() == 6){
					regex = regex.substring(0, 5);
				}
				if(two.matches(regex)){
					return true;
				}
			}
		}
		return false;
	}
}
