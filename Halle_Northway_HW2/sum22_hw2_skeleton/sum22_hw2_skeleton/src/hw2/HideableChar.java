package hw2;

/**
 * The class HideableChar is used to represent one letter in
 * a secret word or phrase. In addition to encapsulating a
 * character, it has a state of hidden or not hidden. This is
 * useful so that as letters are guessed, they can be un-hidden
 * to make them visible to a client.
 * 
 * @author Halle N
 */

public class HideableChar {
	
	/**
	 * Character in secret word.
	 */
	private char character;
	
	/**
	 * Hidden state of character.
	 */
	private boolean hiddenState;
		
	/**
	 * Constructs a HideableChar with the given character as data.
	 * 
	 * @param ch
	 *    the given character
	 */
	public HideableChar(char ch) {
		character = ch;
		hiddenState = Character.isAlphabetic(character);
	}

	/**
	 * Determines whether this object is currently in the hidden state.
	 * 
	 * @return
	 *    true if this object is hidden, false otherwise
	 */
	public boolean isHidden() {
		return hiddenState;
	}

	/**
	 * Sets this object's state to hidden.
	 */
	public void hide() {
		hiddenState = true;
	}

	/**
	 * Sets this object's state to not hidden.
	 */
	public void unHide() {
		hiddenState = false;
	}

	/**
	 * Determines whether the given character is equal to the character
	 * stored in this object.
	 * 
	 * @param ch
	 *    the given character to check
	 * @return
	 *    true if the given character is equal to this object's data
	 */
	public boolean matches(char ch) {
		if (character == ch) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns null if this object is in the hidden state, otherwise
	 * returns a one-character string consisting of the character stored
	 * in this object.
	 * 
	 * @return
	 *    string consisting of this object's character, or null if hidden
	 */
	public String getDisplayedChar() {
		if (hiddenState == true) {
			return null;
		} else {
			return String.valueOf(character);
		}
	}

	/**
	 * Returns a one-character string consisting of the character stored
	 * in this object (whether hidden or not).
	 * 
	 * @return
	 *    string consisting of this object's character
	 */
	public String getHiddenChar() {
		return String.valueOf(character);
	}
}
