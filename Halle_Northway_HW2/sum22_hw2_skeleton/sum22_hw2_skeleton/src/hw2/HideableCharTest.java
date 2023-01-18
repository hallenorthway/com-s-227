package hw2;
/**
 * JUnit test for the class HideableChar.
 * 
 * @author Halle N
 */
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

import org.junit.*;

public class HideableCharTest
{
	@Test
	public void testIsHidden()
	{
		HideableChar a = new HideableChar('a');
		assertTrue(a.isHidden());
		System.out.println("Expected: Constructing new HideableChar object that "
				+ "is alphabetic should return true.");
		System.out.println("Actual: " + a.isHidden());
		System.out.println("");
	}
		
	@Test
	public void testIsNotHidden()
	{
		HideableChar b = new HideableChar('1');
		assertFalse(b.isHidden());
		System.out.println("Expected: Constructing new HideableChar object that "
				+ "is not alphabetic should return false.");
		System.out.println("Actual: " + b.isHidden());
		System.out.println("");
	}
	
	@Test
	public void testHideNonAlphabetic()
	{
		HideableChar c = new HideableChar('1');
		c.hide();
		assertTrue(c.isHidden());
		System.out.println("Expected: Hiding a character that is not alphabetic "
				+ "should return true.");
		System.out.println("Actual: " + c.isHidden());
		System.out.println("");
	}
	
	@Test
	public void testUnHideAlphabetic()
	{
		HideableChar d = new HideableChar('a');
		d.unHide();
		assertFalse(d.isHidden());
		System.out.println("Expected: Unhiding a character that is alphabetic "
				+ "should return false.");
		System.out.println("Actual: " + d.isHidden());
		System.out.println("");
	}
	
	@Test
	public void testDoesNotMatchCharacter()
	{
		HideableChar e = new HideableChar('a');
		assertFalse(e.matches('b'));
		System.out.println("Expected: Checking if character 'a' "
				+ "matches given character 'b' should return false.");
		System.out.println("Actual: " + e.matches('b'));
		System.out.println("");
	}
	
	@Test
	public void testDoesMatchCharacter()
	{
		HideableChar f = new HideableChar('a');
		assertTrue(f.matches('a'));
		System.out.println("Expected: Checking if character 'a' "
				+ "matches given character 'a' should return true.");
		System.out.println("Actual: " + f.matches('a'));
		System.out.println("");
	}
	
	@Test
	public void displayHiddenChar()
	{
		HideableChar g = new HideableChar('a');
		assertNull(g.getDisplayedChar());
		System.out.println("Expected: Attempting to display character that is hidden "
				+ "should return null.");
		System.out.println("Actual: " + g.getDisplayedChar());
		System.out.println("");
	}
	
	@Test
	public void displayUnHiddenChar()
	{
		HideableChar h = new HideableChar('a');
		h.unHide();
		assertEquals("a", h.getDisplayedChar());
		System.out.println("Expected: Attempting to display character that is unhidden "
				+ "should return string consisting of character.");
		System.out.println("Actual: " + h.getDisplayedChar());
		System.out.println("");
	}
	
	@Test
	public void getHiddenChar()
	{
		HideableChar i = new HideableChar('a');
		i.hide();
		assertEquals("a", i.getHiddenChar());
		System.out.println("Expected: Getting character that is hidden "
				+ "should return string consisting of character.");
		System.out.println("Actual: " + i.getHiddenChar());
		System.out.println("");
	}
	
	@Test
	public void getUnHiddenChar()
	{
		HideableChar j = new HideableChar('a');
		j.unHide();
		assertEquals("a", j.getHiddenChar());
		System.out.println("Expected: Getting character that is unhidden "
				+ "should return string consisting of character.");
		System.out.println("Actual: " + j.getHiddenChar());
		System.out.println("");
	}
	
	@Test
	public void testDoesNotMatchCharacter1()
	{
		HideableChar k = new HideableChar('a');
		assertFalse(k.matches('%'));
		System.out.println("Expected: Checking if character 'a' "
				+ "matches given character '%' should return false.");
		System.out.println("Actual: " + k.matches('%'));
		System.out.println("");
	}
	
	@Test
	public void testDoesNotMatchCharacter2()
	{
		HideableChar l = new HideableChar('%');
		assertFalse(l.matches('#'));
		System.out.println("Expected: Checking if character '%' "
				+ "matches given character '#' should return false.");
		System.out.println("Actual: " + l.matches('#'));
		System.out.println("");
	}
	
	@Test
	public void testDoesNotMatchCharacter3()
	{
		HideableChar m = new HideableChar('a');
		assertFalse(m.matches('A'));
		System.out.println("Expected: Checking if character 'a' "
				+ "matches given character 'A' should return false.");
		System.out.println("Actual: " + m.matches('A'));
		System.out.println("");
	}

}
