import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * A Memory game were the player is shown a set of card, and then has to
 * remember the location of each pairs in the least amount of tries.
 * 
 * @author Yasser Ghamlouch <a
 *         href="mailto:yasman8@gmail.com">yasman8@gmail.com</a>
 * 
 */
public class Memory extends JFrame {

 ///////////////////////////////////////////////////////////////////////////
 // Constants
 ///////////////////////////////////////////////////////////////////////////

 private static final long serialVersionUID = 1L;

 ///////////////////////////////////////////////////////////////////////////
 // Instance variables
 ///////////////////////////////////////////////////////////////////////////

 // Logic
 private Board mBoard;
 // GUI components
 private JButton mRetryButton;
 private JButton mNewButton;
 private JSplitPane mSplitPane;

 ///////////////////////////////////////////////////////////////////////////
 // Constructor
 ///////////////////////////////////////////////////////////////////////////

 /**
  * Creates a Frame to start and display the game to the user.
  */
 public Memory() {

  super();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setBackground(Color.WHITE);
  setTitle("Memory Game - Breaking Bad");

  mBoard = new Board();
  add(mBoard, BorderLayout.CENTER);

  mSplitPane = new JSplitPane();
  add(mSplitPane, BorderLayout.SOUTH);

  mRetryButton = new JButton("Retry");
  mRetryButton.setFocusPainted(false);
  mRetryButton.addMouseListener(btnMouseListener);
  mSplitPane.setLeftComponent(mRetryButton);

  mNewButton = new JButton("New Game");
  mNewButton.setFocusPainted(false);
  mNewButton.addMouseListener(btnMouseListener);
  mSplitPane.setRightComponent(mNewButton);

  pack();
  setLocationRelativeTo(null);
  setResizable(true);
  setVisible(true);

 }

 ///////////////////////////////////////////////////////////////////////////
 // Listeners
 ///////////////////////////////////////////////////////////////////////////

 private MouseListener btnMouseListener = new MouseAdapter() {
  public void mouseClicked(MouseEvent e) {
   if (e.getClickCount() == 1 && e.getComponent() == mRetryButton) {
    mBoard.reInit();
   } else if (e.getClickCount() == 1 && e.getComponent() == mNewButton) {
    mBoard.init();
   }
  }
 };

 ///////////////////////////////////////////////////////////////////////////
 // Static methods
 ///////////////////////////////////////////////////////////////////////////

 /**
  * Starts the game. You need to have a running windows server/system to use
  * this application. It is not compatible with CLI.
  * 
  * @param args
  *            - Parameters are ignored.
  */
 public static void main(String[] args) {
  new Memory();
 }
}
