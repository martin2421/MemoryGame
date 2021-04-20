import javax.swing.JButton;

public class Cell extends JButton {

 // Debug
 private static final String TAG = "Cell: ";
 
 // Cell types
 private static final int MAX_RANGE = 26;
 private static final int MIN_RANGE = 0;
 private static final int EMPTY_CELL = 25;
 
 private boolean isSelected = false;
 private boolean isMatched = false;
 private int type = EMPTY_CELL;

 public Cell(int aType) {
  super();
  type = aType;
 }

 /**
  * This method gets the type of the cell
  * 
  * @return an int value that represents a specific 
  * card, an empty cell place or a card that is currently hidden.
  */
 public int getType() {

  return type;
 }

 /**
  * Sets the type of this cell. The range is between MIN_RANGE
  * and MAX_RANGE
  * @param aType is a valid integer value.  An invalid value
  * means something is wrong with the caller, and therefore the
  * program will stop with an error.
  */
 public void setType(int aType) {
  if (aType > MAX_RANGE || aType < MIN_RANGE){
   error("setType(int) reported \"Invalid type code\"", true);
  }
  type = aType;
 }

 /**
  * This method checks if 2 cells are of the same type
  * 
  * @param other is the second Cell we want to be compared to
  * @return true if the given Cell shares the same image,
  * false if the Cells are not related.
  */
 public boolean sameType(Cell other) {

  if (other == null) {
   error("sameType(Cell) received null", false);
   return false;
  }

  if (this.getType() == other.getType()) {
   return true;
  } else {
   return false;
  }
 }

 /**
  * This method checks if the type of this cell is empty (blank cell)
  * 
  * @return true if this cell is considered empty, false
  * if this cell has not yet been paired with another cell
  */
 public boolean isEmpty() {
  if (this.type != EMPTY_CELL) {
   return false;
  }
  return true;
 }

 /**
  * This method set the cell to selected
  * @param selected tells this cell that it is currently under selection
  * by a player
  */
 public void setSelected(boolean selected) {

  isSelected = selected;
 }

 /**
  * This method sets the cell to matched
  * 
  * @param matched tells this cell that it has been matched with its
  * sister cell by the player.
  */
 public void setMatched(boolean matched) {

  isMatched = matched;
 }

 /**
  * This method checks if a cell is selected
  * 
  * @return true if the user is currently selecting this cell,
  * false if the cell has not been selected
  */
 public boolean isSelected() {

  if (isSelected == true) {
   return true;
  }

  return false;
 }

 /**
  * This method checks if a cell is matched
  * 
  * @return true if the cell was previously paired with its sister cell,
  * false if it has yet to be paired by the player
  */
 public boolean isMatched() {

  if (isMatched == true) {
   return true;
  } else {
   return false;
  }
 }
 
 ////////////////////////////////////////////////////////////////////////////
 // Static methods
 ////////////////////////////////////////////////////////////////////////////

 /**
  * Error reporting.
  */
 private static void error( String message, boolean crash ){
  System.err.println( TAG + message );
  if (crash) System.exit(-1);
 }

}
