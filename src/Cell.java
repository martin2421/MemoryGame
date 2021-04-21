import javax.swing.JButton;

public class Cell extends JButton {

 private static final int EMPTY_CELL = 25;
 private boolean isSelected = false;
 private boolean isMatched = false;
 private int type = EMPTY_CELL;

 public Cell(int typeB) {
  super();
  type = typeB;
 }

// This method is the getter for the type of the cell
 public int getType() {return type;}

// Sets the type of the cell
 public void setType(int typeB) {type = typeB;}

// This method checks if 2 cells are of the same type
 public boolean sameType(Cell other) {

  if (other == null) {return false;}

  if (this.getType() == other.getType()) {
    return true;
} else {
    return false;
  }
 }

// This method checks if the type of this cell is empty (blank cell)
 public boolean isEmpty() {
  if (this.type != EMPTY_CELL) {
   return false;
  }
  return true;
 }

// This method set the cell to selected
 public void setSelected(boolean selected) {isSelected = selected;}

// This method sets the cell to matched
 public void setMatched(boolean matched) {isMatched = matched;}

//  This method checks if a cell is selected
 public boolean isSelected() {

  if (isSelected == true) {
   return true;
  }

  return false;
 }

//  This method checks if a cell is matched
 public boolean isMatched() {
  if (isMatched == true) {
   return true;
  } else {
   return false;
  }
 }
}
