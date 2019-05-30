import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MyActionListener implements ActionListener {

  private final VisualGameBoard v;
  private final JButton startButton;

  public MyActionListener(VisualGameBoard v, JButton b){
    this.v = v;
    this.startButton = b;
  }

  public void actionPerformed(ActionEvent e) {
    this.startButton.revalidate();
    this.startButton.repaint();
    GameDriver.startGame();
  }

}
