package hangmanui;

import hw2.PhraseSelector;

import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Sample main class for starting an instance of HangmanUI.
 */
public class UIMain
{
  /**
   * @param args
   *   not used
   */
  public static void main(String[] args) throws FileNotFoundException
  {
	//You can change the filename to read any other files for your testing.
    PhraseSelector selector = new PhraseSelector("words.txt");
    Random rand = new Random();
    HangmanUI.start(selector, rand);
  }

}
