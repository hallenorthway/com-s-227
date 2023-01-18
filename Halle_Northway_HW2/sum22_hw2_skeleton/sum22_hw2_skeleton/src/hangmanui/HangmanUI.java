package hangmanui;
import hw2.Game;
import hw2.HideableChar;
import hw2.PhraseSelector;
//import hw2.PhraseSelectorAlt;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Graphical user interface for HangmanGame.
 */
public class HangmanUI extends JPanel
{ 
  /**
   * Width of each panel.
   */
  private static final int PANEL_WIDTH = 800;
  
  /**
   * Height of the canvas for drawing the stick figure.
   */
  private static final int IMAGE_HEIGHT = 550;
  
  /**
   * Height of the canvas for drawing the word.
   */
  private static final int TEXT_HEIGHT = 100;
  
  /**
   * Height of the panel for the letter buttons.
   */
  private static final int LETTER_PANEL_HEIGHT = 100;

  /**
   * Width of box for each letter.
   */
  private static final int LETTER_WIDTH = 30;
  
  /**
   * Height of box for each letter.
   */
  private static final int LETTER_HEIGHT = 40;
  
  /**
   * Font size for letters.
   */
  private static final int FONT_SIZE = 32;
  
  /**
   * Center to center separation between letters.
   */
  private static final int SEPARATION = 36; 
  
  /**
   * Custom panel for drawing the displayed word.
   */
  private WordCanvas wordCanvas;
  
  /**
   * Custom panel for drawing the stick figure.
   */
  private GameCanvas gameCanvas;
  
  /**
   * Panel for the letter buttons.
   */
  private JPanel letters;
  
  /**
   * Buttons for the letters of the alphabet.
   */
  private JButton[] buttons;
  
  /**
   * Button for starting another round.
   */
  private JButton playAgainButton;
  
  /**
   * The phrase selector.
   */
  private final PhraseSelector selector;
  
  /**
   * Current instance of the game.
   */
  private Game game;
  
  /**
   * Random number generator for deciding how to draw the stick figure.
   */
  private final Random internalRand;

  /**
   * Random number generator for selecting words or phrases from file.
   */
  private final Random rand;
  
  /**
   * State variable indicating whether the figure should be drawn with
   * a bow or a hat.
   */
  private boolean drawHat;
  
  /**
   * State variable indicating whether the figure should be drawn wearing
   * a skirt.
   */
  private boolean drawSkirt;
  
  /**
   * Main entry point for starting up an instance of this 
   * user interface.
   * @param selector
   *   the PhraseSelector to be used
   */
  public static void start(final PhraseSelector selector, final Random rand)
  {
    Runnable r = new Runnable()
    {
      public void run()
      {
        createAndShow(selector, rand);
      }
    };
    SwingUtilities.invokeLater(r);
  }
  
  /**
   * Creates the enclosing frame and starts up the UI machinery.
   * @param selector
   *   the PhraseSelector to be used
   */
  private static void createAndShow(PhraseSelector selector, Random rand)
  {    
    // create the frame
    JFrame frame = new JFrame("CS227 Hangman");

    try
    {   
      // create an instance of our JPanel subclass and 
      // add it to the frame
      frame.getContentPane().add(new HangmanUI(selector, rand));

      // use the preferred sizes
      frame.pack();

      // we want to shut down the application if the 
      // "close" button is pressed on the frame
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // make the frame visible and start the UI machinery
      frame.setVisible(true);
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
      JOptionPane.showMessageDialog(frame, "Unable to open file: " + e.getMessage());
      frame.dispose();
    }
  }

  
  /**
   * Constructor sets up Swing components and initializes the first game.
   * @param selector
   *   the PhraseSelector to be used 
   * @throws FileNotFoundException
   *   if the file referred to by the PhraseSelector cannot be opened 
   */
  private HangmanUI(PhraseSelector selector, Random rand) throws FileNotFoundException
  {
    this.selector = selector;
    this.rand = rand;
    internalRand = new Random();

    // set up the first game
    String secretWord = selector.selectWord(rand).toUpperCase();
    game = new Game(secretWord);
    
    // hat or bow?
    drawHat = internalRand.nextInt(2) == 0; 
    drawSkirt = internalRand.nextInt(2) == 0;

    //
    // Rest of constructor creates and lays out the Swing components
    //
    
    // panel for drawing the secret word
    wordCanvas = new WordCanvas();
    wordCanvas.setPreferredSize(new Dimension(PANEL_WIDTH, TEXT_HEIGHT));
    wordCanvas.setMinimumSize(new Dimension(PANEL_WIDTH, TEXT_HEIGHT));
    
    // button for playing again, initially invisible
    playAgainButton = new JButton();
    playAgainButton.setFont(new Font("Serif", Font.PLAIN, 20));
    playAgainButton.setText("Click here to play again");
    playAgainButton.addActionListener(new PlayAgainHandler());
    playAgainButton.setVisible(false);
    playAgainButton.setEnabled(false);
    wordCanvas.add(playAgainButton);

    // panel for drawing the stick figure
    gameCanvas = new GameCanvas();
    gameCanvas.setPreferredSize(new Dimension(PANEL_WIDTH, IMAGE_HEIGHT));
    
    // panel for 26 buttons for the letters
    letters = new JPanel(new GridLayout(2, 13));
    letters.setPreferredSize(new Dimension(PANEL_WIDTH, LETTER_PANEL_HEIGHT));
    buttons = new JButton[26];
    ActionListener listener = new ButtonHandler();
    for (int i = 0; i < 26; ++i)
    {
      buttons[i] = new JButton(Character.toString((char)('A' + i)));
      buttons[i].setFont(new Font("Serif", Font.PLAIN, 20));
      buttons[i].addActionListener(listener);
      letters.add(buttons[i]);
    }
    
    // lay out the pieces vertically
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    this.add(gameCanvas);
    this.add(wordCanvas);
    this.add(letters);
    
  }
  

  /**
   * Handler for the letter buttons.
   */
  private class ButtonHandler implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      // do nothing if game is over
      if (!game.gameOver())
      {
        char ch = e.getActionCommand().charAt(0);
        int index = ch - 'A';
        buttons[index].setEnabled(false);
        game.guessLetter(ch);
        if (game.gameOver())
        {
          playAgainButton.setVisible(true);
          playAgainButton.setEnabled(true);
        }
        repaint();
      }
    }   
  }

  /**
   * Handler for the "play again" button.
   */
  private class PlayAgainHandler implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent e)
    {
      try
      {
        String word = selector.selectWord(rand).toUpperCase();
        game = new Game(word);
        drawHat = internalRand.nextInt(2) == 0;
        drawSkirt = internalRand.nextInt(2) == 0;
        playAgainButton.setVisible(false);
        playAgainButton.setEnabled(false);
        for (int i = 0; i < 26; ++i)
        {
          buttons[i].setEnabled(true);
        }
        repaint();
      }
      catch (FileNotFoundException fnfe)
      {
        // This should really never happen...
        JOptionPane.showMessageDialog(HangmanUI.this.getParent(), "Unable to open file: " + fnfe.getMessage());
      }
    }   
  }

  /**
   * 
   */
  private class LetterRectangle extends Rectangle
  {
    private String ch;
    
    public LetterRectangle(int width, int height, String ch)
    {
      super(width, height);
      this.ch = ch;
    }
    
    public String getChar()
    {
      return ch;
    }
  }
  
  /**
   * Custom panel for drawing the displayed form of the secret word.
   */
  private class WordCanvas extends JPanel
  {
    @Override
    public void paintComponent(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.setBackground(Color.WHITE);
      g2.clearRect(0, 0, getWidth(), getHeight());
      
      // get the displayed word
      HideableChar[] chars = game.getDisplayedWord();
      
      // create a rectangle for each character
      LetterRectangle[] letters = new LetterRectangle[chars.length];
      for (int i = 0; i < chars.length; ++i)
      {
        letters[i] = new LetterRectangle(LETTER_WIDTH, LETTER_HEIGHT, chars[i].getDisplayedChar());
      }
      
      // assign positions to try to center the word, keeping
      // a minimum left margin of one letter width
      int totalWidth = (letters.length - 1) * SEPARATION  + LETTER_WIDTH;
      int windowWidth = Math.max(getWidth(), totalWidth + 2 * LETTER_WIDTH);
      int leftMargin = (windowWidth - totalWidth) / 2;
      leftMargin = Math.max(leftMargin, LETTER_WIDTH); 

      // set character and x, y position for each rectangle
      for (int i = 0; i < letters.length; ++i)
      {
        int x = leftMargin + i * SEPARATION;
        int y = LETTER_PANEL_HEIGHT - LETTER_HEIGHT - 20;
        letters[i].setLocation(x, y);
      }
      
      // draw each rectangle
      Font f = new Font(Font.SANS_SERIF, Font.PLAIN, FONT_SIZE);
      g2.setFont(f);
      g2.setColor(Color.BLACK);

      FontMetrics fm = g2.getFontMetrics(f);
      int h = fm.getHeight();

      for (int i = 0; i < letters.length; ++i)
      {
        LetterRectangle r = letters[i];
        //g2.draw(r);
        //System.out.println("Draw " + r.x + " " + r.y);
        
        String text = r.getChar();
        System.out.println(text);
        if (text != null)
        {
          int w = fm.stringWidth(text);
          int x = r.x + LETTER_WIDTH / 2 - (w / 2);
          int y = r.y + LETTER_HEIGHT / 2 + (h / 2) - 2;
          g2.drawString(text, x, y);
        }
        else
        {
          //g2.fillRect(r.x + 1, r.y + 1, LETTER_WIDTH - 2, LETTER_HEIGHT - 2);
          // x, y is upper left corner of rectangle
          int bottom = r.y + r.height;
          int right = r.x + r.width;
          int left = r.x;
          int mid = bottom - r.height / 4;
          g2.drawLine(left, bottom, right, bottom);
          g2.drawLine(left, mid, left, bottom);
          g2.drawLine(right, mid, right, bottom);
        }
        
        
        
        
        
        
      }
      
      
//      // try to center it, (x, y) is bottom left corner of text
//      Font font = new Font(Font.MONOSPACED, Font.PLAIN, 40);
//      g2.setFont(font);     
//      FontMetrics metrics = g.getFontMetrics(font);
//      int height = metrics.getHeight();
//      int width = metrics.stringWidth(text);
//      int x = (getWidth() - width) / 2;
//      int y = (getHeight() + height) / 2; 
//      
//      // draw the text
//      g2.drawString(text, x, y);
    }
  }
  
  /**
   * Custom panel for drawing the stick figure.  This class is long and 
   * entirely tedious.
   */
  private class GameCanvas extends JPanel
  {
    private final int lineWidth = 8;
    
    // text position
    private final int textX = 50;
    private final int textY = 50;
    
    // scaffold
    private final int scaffoldX = 200;
    private final int scaffoldY = 75; // upper left corner
    private final int scaffoldHeight = 550;
    private final int scaffoldWidth = 400;
    private final int scaffoldThickness = 30;
    
    // body
    private final int center = 500;  // centerline of body
    private final int headTop = scaffoldY + 100;//150;
    private final int headHeight = 100;
    private final int headWidth = 80;
    private final int neckHeight = 50;
    private final int armWidth = 100;
    private final int armHeight = 50;
    private final int bodyHeight = 100;
    private final int legHeight = 100;
    private final int legWidth = 50;
    private final int eyeWidth = 16; // offset from centerline
    private final int eyeRadius = 4;
    private final int eyeHeight = 30; // offset from top of head
    private final int mouthWidth = 12; // from centerline
    private final int mouthHeight = 70; // from top of head
    
    @Override
    public void paintComponent(Graphics g)
    {
      Graphics2D g2 = (Graphics2D) g;
      g2.setBackground(Color.YELLOW);
      g2.clearRect(0, 0, getWidth(), getHeight());
      int wrongGuesses = game.numWrongGuesses();
      int guessesLeft = game.getMaxGuesses() - wrongGuesses;
      boolean dead = guessesLeft <= 0;
           
      
      g2.setPaint(Color.BLACK);
      g2.setStroke(new BasicStroke(lineWidth));
      
      drawMessage(g2, guessesLeft, dead);
      drawScaffold(g2);
      
      //
      // Draw the stick figure from the top down based on the number of wrong guesses
      //
      
      // head
      if (wrongGuesses >= 1)
      {
        drawHead(g2, dead);
        if (drawHat)
        {
          drawHat(g2);
        }
        else
        {
          drawBow(g2);
        }
      }

      // neck
      if (wrongGuesses >= 2)
      {
        int currentTop = headTop + headHeight;
        g2.drawLine(center, currentTop, center, currentTop + neckHeight);
       }
      
      // left arm
      if (wrongGuesses >= 3)
      {
        int currentTop = headTop + headHeight + neckHeight;
        g2.drawLine(center, currentTop, center - armWidth, currentTop + armHeight);
      }
      
      // right arm
      if (wrongGuesses >= 4)
      {     
        int currentTop = headTop + headHeight + neckHeight;
        g2.drawLine(center, currentTop, center + armWidth, currentTop + armHeight);
      }
      
      // torso
      if (wrongGuesses >= 5)
      {
        int currentTop = headTop + headHeight + neckHeight;
        g2.drawLine(center, currentTop, center, currentTop + bodyHeight);
      }
      
      // left leg
      if (wrongGuesses >= 6)
      {
        int currentTop = headTop + headHeight + neckHeight + bodyHeight;
        g2.drawLine(center, currentTop, center - legWidth, currentTop + legHeight);
      }
      
      // right leg
      if (wrongGuesses >= 7)
      {
        int currentTop = headTop + headHeight + neckHeight + bodyHeight;
        g2.drawLine(center, currentTop, center + legWidth, currentTop + legHeight);
      }      
      
      // do this last because it overlaps arms and legs
      if (drawSkirt && wrongGuesses >= 5)
      {
        drawSkirt(g2);
      }
      
    }
    
    /**
     * Helper method for drawing the head of the stick figure.
     * @param g2
     *   graphics context
     * @param dead
     *   true if the player has lost
     */
    private void drawHead(Graphics2D g2, boolean dead)
    {
      g2.drawOval(center - headWidth / 2, headTop, headWidth, headHeight);

      if (!dead)
      {
        // regular eyes
        g2.drawOval(center - eyeWidth - eyeRadius, headTop + eyeHeight - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);
        g2.drawOval(center + eyeWidth - eyeRadius, headTop + eyeHeight - eyeRadius, 2 * eyeRadius, 2 * eyeRadius);
      }
      else
      {
        // crosses for eyes
        g2.setStroke(new BasicStroke(lineWidth / 2));
        g2.drawLine(center - eyeWidth - eyeRadius, headTop + eyeHeight, center - eyeWidth + eyeRadius, headTop + eyeHeight);
        g2.drawLine(center - eyeWidth, headTop + eyeHeight - eyeRadius, center - eyeWidth, headTop + eyeHeight  + eyeRadius);
        g2.drawLine(center + eyeWidth - eyeRadius, headTop + eyeHeight, center + eyeWidth + eyeRadius, headTop + eyeHeight);
        g2.drawLine(center + eyeWidth, headTop + eyeHeight - eyeRadius, center + eyeWidth, headTop + eyeHeight  + eyeRadius);
        g2.setStroke(new BasicStroke(lineWidth));
      }

      // mouth
      g2.drawLine(center - mouthWidth, headTop + mouthHeight, center + mouthWidth, headTop + mouthHeight);
    }
    
    /**
     * Helper method for drawing a bow on the stick figure's head.
     * @param g2
     *   graphics context
     */
    private void drawBow(Graphics2D g2)
    {
      Paint savedColor = g2.getPaint();
      Stroke savedStroke = g2.getStroke();
      g2.setStroke(new BasicStroke(lineWidth / 2));

      // position relative to top of head
      int x = center + 5;
      int y = headTop; 
      int[] xPoints = {x, x + 16, x + 20, x + 10};
      int[] yPoints = {y + 10, y + 15, y + 5, y - 10};

      g2.setColor(Color.PINK);
      g2.fillPolygon(xPoints, yPoints, 4);
      g2.setPaint(savedColor);
      g2.drawPolygon(xPoints, yPoints, 4);
     
      x = x + 16;
      y = y + 15; 
      int[] xPoints2 = {x, x + 10, x + 20, x + 5};
      int[] yPoints2 = {y, y + 15, y, y - 8};

      g2.setColor(Color.PINK);
      g2.fillPolygon(xPoints2, yPoints2, 4);
      g2.setPaint(savedColor);
      g2.drawPolygon(xPoints2, yPoints2, 4);
      
      g2.setStroke(savedStroke);
    }
    
    /**
     * Helper method for drawing a hat.
     * @param g2
     *   graphics context
     */
    private void drawHat(Graphics2D g2)
    {
      int width = 60;
      int height = 60;
      int x = center - width / 2 - 5;
      int y = headTop - height / 2 - 5;
      
      // top of hat is a rounded rectangle filled out at bottom with a polygon
      g2.fillRoundRect(x, y, width, height, width / 2, height / 2);
      int[] xPoints = {x, x + width , x + width + 8, x - 4};
      int[] yPoints = {y + 20, y + 20, y + height - 9, y + height - 5};
      g2.fillPolygon(xPoints, yPoints, 4);

      // brim
      g2.drawLine(center - 70, headTop + height / 2 - 5, center + 60, headTop + height / 2 - 12);      
    }
    
    /**
     * Helper method for drawing a skirt over the torso.
     * @param g2
     *   graphics context
     */
    private void drawSkirt(Graphics2D g2)
    {
      int currentTop = headTop + headHeight + neckHeight;
      int dressWidthTop = 20;
      int dressWidthBottom = 35;
      int dressHeight = 120;
      int[] xPoints = {center - dressWidthTop, center - dressWidthBottom, 
          center + dressWidthBottom, center + dressWidthTop};
      int[] yPoints = {currentTop, currentTop + dressHeight, 
          currentTop + dressHeight, currentTop};
      Paint savedColor = g2.getPaint();
      g2.setColor(Color.PINK);
      g2.fillPolygon(xPoints, yPoints, 4);
      g2.setPaint(savedColor);
      g2.drawPolygon(xPoints, yPoints, 4);
    }
    
    /**
     * Helper method for displaying the number of guesses left.
     * @param g2
     *   graphics context
     * @param guessesLeft
     *   number of remaining guesses
     * @param dead
     *   true if the player has lost
     */
    private void drawMessage(Graphics2D g2, int guessesLeft, boolean dead)
    {
      g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 36));
      String msg;
      if (game.won())
      {
        msg = "You win!";
      }
      else if (!dead)
      {
        msg = "You have " + guessesLeft + " guesses left.";
      }
      else
      {
        msg = "You've lost! The word was " + game.getSecretWord();
      }
      g2.drawString(msg, textX, textY);
    }
    
    /**
     * Helper method for drawing the scaffold.
     * @param g2
     *   graphics context
     */
    private void drawScaffold(Graphics2D g2)
    {
      // save graphics state
      Paint savedColor = g2.getPaint();
      Stroke savedStroke = g2.getStroke();
      
      g2.setColor(new Color(87, 44, 0));  // dark brown
      g2.fillRect(scaffoldX, scaffoldY, scaffoldThickness, scaffoldHeight);
      g2.fillRect(scaffoldX, scaffoldY, scaffoldWidth, scaffoldThickness);
      g2.setStroke(new BasicStroke(scaffoldThickness));
      g2.drawLine(scaffoldX + scaffoldThickness, scaffoldY + scaffoldWidth / 3, scaffoldX + scaffoldWidth / 3, scaffoldY + scaffoldThickness);
      
      // draw the rope
      g2.setStroke(savedStroke);
      g2.drawLine(center, scaffoldY + scaffoldThickness, center, headTop);
      g2.setPaint(savedColor);
    }
  }  // end of GameCanvas class declaration
}
