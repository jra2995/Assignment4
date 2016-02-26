package Assignment4;

import java.util.ArrayList;

public class Dictionary {
	private ArrayList<String> dictionary;
	
	public Dictionary(ArrayList<String> list) {
		dictionary = list;
	}
	
	public boolean search(String word){
		return dictionary.contains(word);
	}
	
	public ArrayList<String> getDictionary(){
		return dictionary;
	}
	
	public void setDictionary(ArrayList<String> newDictionary){
		dictionary = newDictionary;
	}
}
