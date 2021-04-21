import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game extends JFrame {

 private Board board;
 private JButton retryButton;
 private JButton newButton;
 private JSplitPane splitPane;

 public Game() {

  // Calls JFrame to create Frame
  super();

  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  setBackground(Color.WHITE);
  setTitle("Memory Game - Breaking Bad");

  board = new Board();
  add(board, BorderLayout.CENTER);

  splitPane = new JSplitPane();
  add(splitPane, BorderLayout.SOUTH);

  retryButton = new JButton("Retry");
  retryButton.setFocusPainted(false);
  retryButton.addMouseListener(listener);
  splitPane.setLeftComponent(retryButton);

  newButton = new JButton("New Game");
  newButton.setFocusPainted(false);
  newButton.addMouseListener(listener);
  splitPane.setRightComponent(newButton);

  pack();
  setLocationRelativeTo(null);
  setResizable(true);
  setVisible(true);
 }

 private MouseListener listener = new MouseAdapter() {
  public void mouseClicked(MouseEvent e) {
   if (e.getClickCount() == 1 && e.getComponent() == retryButton) {
    board.reInit();
   } else if (e.getClickCount() == 1 && e.getComponent() == newButton) {
    board.init();
   } else if (e.getClickCount() == 3 && e.getComponent() == newButton) {                
       JOptionPane.showMessageDialog(null, "I AM THE DANGER MF\n    ~ Los Pollos Hermanos", "Easter Egg", JOptionPane.WARNING_MESSAGE);
   }
  }
 };

 public static void main(String[] args) {
  new Game();
 }
}
