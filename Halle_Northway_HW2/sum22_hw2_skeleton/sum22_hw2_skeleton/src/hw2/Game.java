package hw2;

/**
 * The class Game is responsible for keeping track of the
 * secret word or phrase, the letters that have been guessed,
 * the number of wrong guesses, and whether the game is won or 
 * lost. Part of the state of a Game object is an array of
 * HideableChar.
 * 
 * @author Halle N
 */
public class Game {
	
	/**
	 * The maximum number of wrong guesses.
	 */
	private int maxWrongGuesses;
	
	/**
	 * Wrong guesses made so far by the player.
	 */
	private int wrongGuessesCount;

	/**
	 * Characters guessed by the player.
	 */
	private String stringGuessedLetters = "";
	
	/**
	 * The word to be guessed by the player.
	 */
	private String secretWord = "";
	
	/**
	 * A HideableChar array containing characters of secret word, each with a hidden state.
	 */
	private HideableChar[] secretWordArray;
	
	/**
	 * Default value for the maximum number of wrong guesses.
	 */
	public static final int DEFAULT_MAX_WRONG_GUESSES = 7;

	/**
	 * Constructs a hangman game using the given word as the
	 * secret word and the default maximum number of wrong guesses.
	 * 
	 * @param word
	 *    the secret word
	 */
	public Game(String word) {
		secretWord = word;
		maxWrongGuesses = DEFAULT_MAX_WRONG_GUESSES;
		
		secretWordArray = new HideableChar[secretWord.length()];
		
		for (int i = 0; i < secretWord.length(); i++) {
			secretWordArray[i] = new HideableChar(secretWord.charAt(i));
		}
		
	}

	/**
	 * Constructs a hangman game using the given word as the
	 * secret word and the given value as the maximum number of wrong
	 * guesses.
	 *
	 * @param word
	 *    the given word
	 * @param maxGuesses
	 *    the max number of wrong guesses
	 */
	public Game(String word, int maxGuesses) {
		secretWord = word;
		maxWrongGuesses = maxGuesses;
		
		secretWordArray = new HideableChar[secretWord.length()];
		
		for (int i = 0; i < secretWord.length(); i++) {
			secretWordArray[i] = new HideableChar(secretWord.charAt(i));
		}
	}

	/**
	 * Returns the maximum number of wrong guesses for this game.
	 * 
	 * @return
	 *    the max number of wrong guesses
	 */
	public int getMaxGuesses() {
		return maxWrongGuesses;
	}

	/**
	 * Determines whether this game is over.
	 * 
	 * @return
	 *    true if the player has won or has run out of guesses, false
	 *    otherwise
	 */
	public boolean gameOver() {
		 if (won() == true || wrongGuessesCount >= maxWrongGuesses) {
		 		return true;
		 } else {
				return false;
		 }
	}

	/**
	 * Returns the number of wrong guesses made so far by the player.
	 * 
	 * @return
	 *    the number of wrong guesses made
	 */
	public int numWrongGuesses() {
		return wrongGuessesCount;
	}

	/**
	 * Determines whether the player has guessed all the letters in
	 * the secret word.
	 * 
	 * @return
	 *    true if the player has won, false otherwise
	 */
	public boolean won() {
		
		if (wrongGuessesCount > maxWrongGuesses) {
			return false;
		}
		
		for (int i = 0; i < secretWord.length(); i++) {
			if (secretWordArray[i].isHidden() == true) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns a string containing all the letters guessed so far
	 * by the player, without duplicates.
	 * 
	 * @return
	 *    the letters guessed by the player so far
	 */
	public String lettersGuessed() {
		return stringGuessedLetters;
	}

	/**
	 * Returns a sequence of HideableChar representing the secret
	 * word or phrase.
	 * 
	 * @return
	 *    the displayed form of the secret word
	 */
	public HideableChar[] getDisplayedWord() {
		return secretWordArray;
	}

	/**
	 * Returns the complete secret word or phrase as a string.
	 *
	 * @return
	 *    the secret word or phrase
	 */
	public String getSecretWord() { 
		return secretWord;
	}

	/**
	 * Invoked by the player to guess a letter.
	 * 
	 * @param ch
	 *    the letter to check
	 * @return
	 *    true if the letter has not been guessed already and occurs in
	 *    the secret word, false otherwise
	 */
	public boolean guessLetter(char ch) {
		char guessedCharacter = ch;
		
		// if game is over, do nothing
		if (gameOver() == true) {
			return false;
		}
		
		// if letter has been guessed already, increase wrong guess count
		if (stringGuessedLetters.indexOf(guessedCharacter) >= 0) {
			
			stringGuessedLetters = stringGuessedLetters + guessedCharacter;
			wrongGuessesCount++;
			
			return false;
		}
		// else if letter is not in secret word, increase wrong guess count
		else if (secretWord.indexOf(guessedCharacter) < 0) {
			
			stringGuessedLetters = stringGuessedLetters + guessedCharacter;
			wrongGuessesCount++;
			
			return false;
		}
		// else if letter is in secret word, change occurrences of letter in secret word to not hidden
		else if (secretWord.indexOf(guessedCharacter) >= 0 && Character.isAlphabetic(guessedCharacter)) {
			
			stringGuessedLetters = stringGuessedLetters + guessedCharacter;
			
			for (int i = 0; i < secretWord.length(); i++) {
				
				if (secretWordArray[i].matches(guessedCharacter)) {
					secretWordArray[i].unHide();
				}
			}
			
			return true;
		}
		
		return false;
	}
}
