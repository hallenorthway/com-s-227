package hangmanui;
import hw2.Game;
import hw2.HideableChar;
import hw2.PhraseSelector;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Text-based user interface for HangmanGame.
 */
public class TextUIMain
{

  /**
   * Entry point for execution.
   * @param args
   *   not used
   */
  public static void main(String[] args) throws FileNotFoundException
  {
    PhraseSelector selector = new PhraseSelector("words.txt");
    new TextUIMain().runUI(selector);
  }

  /**
   * Starts the main loop for the user interface.  
   * @param selector
   *   the PhraseSelector to be used
   * @throws FileNotFoundException
   *   if the file referred to in the PhraseSelector cannot be opened
   */
  public void runUI(PhraseSelector selector) throws FileNotFoundException
  {
    Scanner scanner = new Scanner(System.in);
    boolean playAgain = true;
    Random rand = new Random();
    
    // keep going until user decides to quit
    while (playAgain)
    {
      // create a new game instance
      String secretWord = selector.selectWord(rand).toUpperCase();
      Game game = new Game(secretWord);
      
      // play until it's over
      while (!game.gameOver())
      {
        System.out.println();
        HideableChar[] chars = game.getDisplayedWord();
        printDisplayedWord(chars);
        System.out.println("Your guesses so far: " + game.lettersGuessed());
        System.out.print("Enter your guess: ");
        String input = scanner.nextLine();
        
        // convert to lower case
        char guess = input.trim().toUpperCase().charAt(0);
        boolean good = game.guessLetter(guess);
        if (good)
        {
          System.out.println("Good guess!");
        }
        else
        {
          System.out.println("Nope, that makes " + game.numWrongGuesses() + " wrong guesses");
        }
      }

      if (game.won())
      {
        System.out.println("Congratulations, you won!");
      }
      else
      {
        System.out.println("Sorry, you lost!");
      }

      System.out.println();
      System.out.print("Want to play again (y/n)? ");
      String answer = scanner.nextLine();
      playAgain = answer.trim().toLowerCase().startsWith("y");
    }
  }
  
  private void printDisplayedWord(HideableChar[] chars)
  {
    System.out.print("The word: ");
    for (int i = 0; i < chars.length; ++i)
    {
      if (chars[i].isHidden())
      {
        System.out.print(" ");
      }
      else
      {
        System.out.print(chars[i].getDisplayedChar());
      }
    }
    System.out.println();
    
    System.out.print("          ");

    for (int i = 0; i < chars.length; ++i)
    {
      if (chars[i].isHidden())
      {
        System.out.print("^");
      }
      else
      {
        System.out.print(" ");
      }
    }
    System.out.println();

  }
}
