import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

 private static final String TAG = "Board: ";

 // Constants
 private static final int WIDTH = 20;
 private static final int MAX_CARDS = 24;
 private static final int MIN_CARDS = 1;
 private static final int ROWS = 4;
 private static final int COLUMNS = 6;
 private static final int PAIRS = 12;

 private static final int MAX_SELECTED_CARDS = 2;
 private static final int FIRST = 0;
 private static final int SECOND = 1;
 private static final int VISIBLE_DELAY = 2000;
 private static final int PEEK_DELAY = 2000;

 // Type of Cards
 private static final int EMPTY_CELL = 0;
 private static final int HIDDEN_CARD = 26;
 private static final int EMPTY_CARD = 25;

 // Card image file properties
 private static final String SUFFIX = ".jpg";
 private static final String PREFIX = "img-";
 private static final String FOLDER = "/images/";
 private static final String HIDDEN_IMAGE = FOLDER + PREFIX + "26" + SUFFIX;
 private static final String EMPTY_IMAGE = FOLDER + PREFIX + "25" + SUFFIX;

 private static ArrayList<Cell> chosenCards = new ArrayList<Cell>();
 private static int failedAttempts = 0;
 private static int selectedCards = 0;

 private Cell[][] board = null;
 private String[] cardStorage = initCardStorage();
 private Cell[] cardChecker = new Cell[MAX_SELECTED_CARDS];

 public Board() {
  super();

  setBackground(Color.WHITE);
  setBorder(BorderFactory.createEmptyBorder(WIDTH, WIDTH, WIDTH, WIDTH));
  setLayout(new GridLayout(ROWS, COLUMNS));

  board = new Cell[ROWS][COLUMNS];

  for (int row = 0; row < ROWS; row++) {
   for (int column = 0; column < COLUMNS; column++) {
    board[row][column] = new Cell(EMPTY_CELL);
    board[row][column].addActionListener(this);
    add(board[row][column]);
   }
  }

  init();
 }

 public void init() {

  resetMatchedImages();
  resetBoardParam();
  peek();
  cardStorage = initCardStorage();
  setImages();

 }

 public void reInit() {

  resetMatchedImages();
  resetBoardParam();
  peek();
  setImages();

 }

 /**
  * This method checks if the board is solved or not.
  * 
  * @return true if the board is solved, false if there remains cards that
  *         have to be matched
  */
 public boolean isSolved() {

  // No check for null, the method can't be called out of
  // an instance, and the constructor initialize the board

  for (int row = 0; row < ROWS; row++) {
   for (int column = 0; column < COLUMNS; column++) {
    if (!board[row][column].isEmpty()) {
     return false;
    }
   } // column loop
  } // row loop

  return true;
 }

 /**
  * This method adds a selected card to the chosen card list
  * 
  * @param aCard
  *            is the card to be added to the list
  */

 private void addToChose(Cell aCard) {

  if (aCard != null) {
   if (!chosenCards.contains(aCard)) {
    chosenCards.add(aCard);
   }
  } else {
   error("addToChose( Cell ) received null.", true);
  }

 }

 /**
  * This method is the action performed when a card is clicked it represents
  * the main user interface of the game
  * 
  * @param e an ActionEvent
  *           
  */
 public void actionPerformed(ActionEvent e) {

  if (e == null) {
   error("actionPermormed(ActionEvent) received null", false);
   return;
  }

  // Flush out cases where we don't care
  if (!(e.getSource() instanceof Cell)) {
   return;
  }

  if (!isCardValid((Cell) e.getSource())) {
   return;
  }

  // Proceed with cases we want to cover

  ++selectedCards;

  if (selectedCards <= MAX_SELECTED_CARDS) {
   Point gridLoc = getCellLocation((Cell) e.getSource());
   setCardToVisible(gridLoc.x, gridLoc.y);
   cardChecker[selectedCards - 1] = getCellAtLoc(gridLoc);
   addToChose(getCellAtLoc(gridLoc));
  }

  if (selectedCards == MAX_SELECTED_CARDS) {

   if (!sameCellPosition(cardChecker[FIRST].getLocation(),
     cardChecker[SECOND].getLocation())) {

    setSelectedCards(cardChecker[FIRST], cardChecker[SECOND]);
   } else {
    --selectedCards;
   }
  } // if selectedCards == MAX
 }

 ////////////////////////////////////////////////////////////////////////////
 // Utils Methods
 ////////////////////////////////////////////////////////////////////////////

 // This method returns the location of a Cell object on the board
 private Cell getCellAtLoc(Point point) {
  if (point == null) {
   error("getCellAtLoc( Point ) received null", true);
   return null;
  }

  return board[point.x][point.y];
 }

 // This method sets a card to visible at a certain location
 private void setCardToVisible(int x, int y) {

  board[x][y].setSelected(true);
  showCardImages();
 }

 // This method delays the setCards method, so the user can peek at the cards
 // before the board resets them
 private void peek() {

  Action showImagesAction = new AbstractAction() {

   public void actionPerformed(ActionEvent e) {
    showCardImages();
   }
  };

  Timer timer = new Timer(PEEK_DELAY, showImagesAction);
  timer.setRepeats(false);
  timer.start();
 }

 // This method sets the images on the board
 private void setImages() {

  ImageIcon anImage;

  for (int row = 0; row < ROWS; row++) {
   for (int column = 0; column < COLUMNS; column++) {

    URL file = getClass().getResource(
      FOLDER
        + PREFIX
        + cardStorage[column
          + (COLUMNS * row)]
        + SUFFIX);

    if (file == null) {
     System.err.println(TAG
       + "setImages() reported error \"File not found\".");
     System.exit(-1);
    }

    anImage = new ImageIcon(file);

    board[row][column].setIcon(anImage);

   } // column loop
  } // row loop
 }

 // This method shows a specific image at a certain location
 private void showImage(int x, int y) {

  URL file = getClass().getResource(
    FOLDER + PREFIX
      + cardStorage[y + (COLUMNS * x)]
      + SUFFIX);

  if (file == null) {
   System.err.println(TAG
     + "showImage(int, int) reported error \"File not found\".");
   System.exit(-1);
  }

  ImageIcon anImage = new ImageIcon(file);
  board[x][y].setIcon(anImage);

 }

 // This method sets all the images on the board
 private void showCardImages() {

  // For each card on the board
  for (int row = 0; row < ROWS; row++) {
   for (int column = 0; column < COLUMNS; column++) {

    // Is card selected ?
    if (!board[row][column].isSelected()) {

     // If selected, verify if the card was matched by the user
     if (board[row][column].isMatched()) {
      // It was matched, empty the card slot
      board[row][column].setIcon(new ImageIcon(getClass()
        .getResource(EMPTY_IMAGE)));
      board[row][column].setType(EMPTY_CARD);
     } else {
      // It was not, put the "hidden card" image
      board[row][column].setIcon(new ImageIcon(getClass()
        .getResource(HIDDEN_IMAGE)));
      board[row][column].setType(HIDDEN_CARD);
     }

    } else {
     // The card was not selected
     showImage(row, column);

     String type = cardStorage[column
       + (COLUMNS * row)];
     int parsedType = Integer.parseInt(type);

     board[row][column].setType(parsedType);

    } // Is card selected?
   } // inner loop - columns
  } // outer loop - rows
 }

 // This method generates a random image, i.e. a random integer representing
 // the type of the image

 private String generateRandomImageFilename(int max, int min) {

  Random random = new Random();
  Integer aNumber = (min + random.nextInt(max));

  if (aNumber > 0 && aNumber < 10) {
   return "0" + aNumber;
  } else {
   return aNumber.toString();
  }
 }

 // This method creates an array of string holding the indices of 24 random
 // images grouped in pairs.

 private String[] initCardStorage() {

  String[] cardStorage = new String[MAX_CARDS];
  String[] firstPair = new String[PAIRS];
  String[] secondPair = new String[PAIRS];

  firstPair = randomListWithoutRep();

  for (int i = 0; i < PAIRS; i++) {
   cardStorage[i] = firstPair[i];
  }

  Collections.shuffle(Arrays.asList(firstPair));

  for (int j = 0; j < PAIRS; j++) {
   secondPair[j] = firstPair[j];
  }

  for (int k = PAIRS; k < MAX_CARDS; k++) {
   cardStorage[k] = secondPair[k - PAIRS];
  }

  return cardStorage;
 }

 // this method is to generate a list of PAIRS images (types)
 // without repetition

 private String[] randomListWithoutRep() {

  String[] generatedArray = new String[PAIRS];
  ArrayList<String> generated = new ArrayList<String>();

  for (int i = 0; i < PAIRS; i++) {
   while (true) {
    String next = generateRandomImageFilename(MAX_CARDS,
      MIN_CARDS);

    if (!generated.contains(next)) {
     generated.add(next);
     generatedArray[i] = generated.get(i);
     break; // breaks back to "for" loop
    }
   } // inner loop - for every random card, ensure its not already
    // existing
  } // outer loop - we want PAIRS different pairs

  return generatedArray;
 }

 // This method gets the location of a cell on the board and returns that
 // specific point
 private Point getCellLocation(Cell aCell) {

  if (aCell == null) {
   error("getCellLocation(Cell) received null", true);
   return null;
  }

  Point p = new Point();

  for (int column = 0; column < ROWS; column++) {

   for (int row = 0; row < COLUMNS; row++) {

    if (board[column][row] == aCell) {
     p.setLocation(column, row);
     return p;
    }
   } // row for
  } // column for
  return null;
 }

 // This methods checks if 2 cards are the same
 private boolean sameCellPosition(Point firstCell, Point secondCell) {

  if (firstCell == null || secondCell == null) {
   if (secondCell == firstCell) {
    // They're equal if both are null
    return true;
   }

   if (firstCell == null) {
    error("sameCellPosition(Point, Point) received (null, ??)",
      true);
   }
   if (secondCell == null) {
    error("sameCellPosition(Point, Point) received (??, null)",
      true);
   }

   return false;
  }

  if (firstCell.equals(secondCell)) {
   return true;
  }
  return false;
 }

 // This method check if any 2 selected cards are the same so it replaces
 // them with a blank cell or if they're different it flips them back,
 // it also check if the board is solved
 private void setSelectedCards(Cell firstCell, Cell secondCell) {

  if (firstCell == null || secondCell == null) {

   if (firstCell == null) {
    error("setSelectedCards(Cell, Cell) received (null, ??)", true);
   }
   if (secondCell == null) {
    error("setSelectedCards(Cell, Cell) received (??, null)", true);
   }
   return;
  }

  if (firstCell.sameType(secondCell)) {

   firstCell.setMatched(true);
   secondCell.setMatched(true);
   firstCell.setSelected(false);
   secondCell.setSelected(false);
   showImage(getCellLocation(secondCell).x,
     getCellLocation(secondCell).y);
   peek();
   finalMessage();
  } else {

   firstCell.setMatched(false);
   secondCell.setMatched(false);
   firstCell.setSelected(false);
   secondCell.setSelected(false);
   showImage(getCellLocation(secondCell).x,
     getCellLocation(secondCell).y);
   peek();
   failedAttempts++;
  }
  resetSelectedCards();
 }

 // This method checks if a selected card is valid, the user isn't allowed to
 // select blank cells again
 private boolean isCardValid(Cell aCard) {

  if (aCard == null) {
   error("isCardValid(Cell) received null", false);
   return false;
  }

  if (!aCard.isEmpty()) {
   return true;
  } else {
   return false;
  }

 }

 // This method displays the results when the game is solved
 private void finalMessage() {

  @SuppressWarnings("serial")
  // Anonymous class are not to be serialized
  Action showImagesAction = new AbstractAction() {

   public void actionPerformed(ActionEvent e) {
    if (isSolved()) {

     Float numeralScore = (((float) failedAttempts) / ((float) MAX_CARDS)) * 100;
     String textualScore = numeralScore.toString();

     JOptionPane.showMessageDialog(null,
       "Solved!! Your results:\n" + " Failed Attempts: "
         + failedAttempts
         + "\n Error percentage : " + textualScore
         + " %", "RESULTS",
       JOptionPane.INFORMATION_MESSAGE);
    } // if solved
   } // actionPerformed()
  }; // class implementation

  Timer timer = new Timer(VISIBLE_DELAY, showImagesAction);
  timer.setRepeats(false);
  timer.start();

 }

 // this method resets all the matched images, used in the replay method and
 // new game
 private void resetMatchedImages() {
  for (int row = 0; row < ROWS; row++) {
   for (int column = 0; column < COLUMNS; column++) {
    if (board[row][column].isMatched()) {
     board[row][column].setMatched(false);
    } // if
   } // for column
  } // for row
 }

 ////////////////////////////////////////////////////////////////////////////
 // Static methods
 ////////////////////////////////////////////////////////////////////////////

 /**
  * Error reporting.
  */
 private static void error(String message, boolean crash) {
  System.err.println(TAG + message);
  if (crash) {
   System.exit(-1);
  }
 }

 // This method resets the number of selected cards to 0 after 2 cards have
 // been chosen and checked
 private static void resetSelectedCards() {
  selectedCards = 0;
 }

 // This method resets the number of matched pairs on the board
 private static void resetNumMatchedCards() {
 }

 // This method resets the number of failed attempts
 private static void resetFailedAttempts() {
  failedAttempts = 0;
 }

 // This method resets the parameters of the board
 // used when replaying or when starting a new game
 private static void resetBoardParam() {

  resetFailedAttempts();
  resetNumMatchedCards();
 }

}
