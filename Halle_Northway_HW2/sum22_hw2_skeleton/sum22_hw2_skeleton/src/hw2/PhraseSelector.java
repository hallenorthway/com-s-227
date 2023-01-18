package hw2;
/**
 * The class PhraseSelector is responsible for choosing a
 * random line from a text file.
 *
 * @author Halle N
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class PhraseSelector {
	
	private File file;
	
	/**
	 * Constructs a PhraseSelector that will select words from
	 * the given file. This constructor may throw a FileNotFoundException
	 * if the file cannot be opened.
	 * 
	 * @param givenFilename
	 *    the given file
	 * @throws FileNotFoundException
	 */
	public PhraseSelector(String givenFilename) throws FileNotFoundException {
		
		file = new File(givenFilename);
	}

	/**
	 * Returns a word or phrase selected at random from this PhraseSelector's
	 * file, using the given source of randomness. This method may throw
	 * FileNotFoundException if the file cannot be opened.
	 * 
	 * @param rand
	 *    the given source of randomness
	 * @return
	 *    the randomly selected line from the file
	 * @throws FileNotFoundException
	 */
	public String selectWord(Random rand) throws FileNotFoundException {
		
		Scanner fileScnr = new Scanner(file);
		fileScnr.useDelimiter(",,");
	    ArrayList<String> wordList = new ArrayList<String>();
	    int size = 0;
	    
	    while (fileScnr.hasNextLine()) {
			String nextWord = fileScnr.nextLine();
			size += 1;
			wordList.add(nextWord + ","); // add ',' for delimiter
	    }
	    
	    int n = rand.nextInt(size);
	    
	    String secretWord = wordList.get(n);
	    
	    // remove ',' from array list to get secret word by itself
	    secretWord = secretWord.substring(0, secretWord.length() - 1);
	    	    
	    fileScnr.close();
	    
		return secretWord;
	    
	}
}
