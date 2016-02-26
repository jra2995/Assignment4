package Assignment4;

import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

public class Dictionary {
	private List<String> dictionary;
	
	public Dictionary(String filename) {
		File file = new File(filename);
		Scanner scan = null;
		try{
			scan = new Scanner(file);
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				if(line.charAt(0) != '*'){
					String word = line.substring(0, 5);
					dictionary.add(word);
				}
			}
		}
		catch(FileNotFoundException fnfe){
			fnfe.printStackTrace();
			System.err.println("Error - File " + file + " not found.");
		}
		finally{
			if(scan != null){
				scan.close();
			}
		}
	}
	
	public boolean search(String word){
		return dictionary.contains(word);
	}
	
	public List<String> getDictionary(){
		return dictionary;
	}
	
	public void setDictionary(List<String> newDictionary){
		dictionary = newDictionary;
	}
}
