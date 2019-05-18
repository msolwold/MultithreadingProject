/**
 * TODO
 * Make class for board space that draws the image and
 * contains a method that swaps labels from one panel to another
 * Maybe put the images in the Character classes?
 */


import javax.swing.*;
import java.awt.*;
import java.awt.GridLayout;
import java.util.*;

import java.io.*;
import java.io.IOException;

import javax.imageio.*;
import java.awt.image.BufferedImage;

public class VisualGameBoard {

  JFrame mainFrame = new JFrame("Looney Tunes Game Board");
  JPanel[][] boardArr = new JPanel[5][5];

  public VisualGameBoard(){

  }

  public void updateTiles(int[] x, int[] y){
  }

  public void createFrame(ArrayList<ArrayList> board) {


    this.mainFrame.setSize(750,792);

    FrameBackground container = new FrameBackground();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.setPreferredSize(new Dimension(750,770));

    JPanel header = new JPanel();
    header.setPreferredSize(new Dimension(750,188));
    header.setBorder(BorderFactory.createLineBorder(Color.black));
    header.setOpaque(false);

    JPanel main = new JPanel();
    main.setPreferredSize(new Dimension(562,562));
    main.setBorder(BorderFactory.createLineBorder(Color.black));
    main.setOpaque(false);
    main.setLayout(new GridLayout(5,5));

    JPanel footer = new JPanel();
    footer.setPreferredSize(new Dimension(750,20));
    footer.setBorder(BorderFactory.createLineBorder(Color.black));
    footer.setOpaque(false);

    this.mainFrame.getContentPane().add(container);

    container.add(header);
    container.add(main);
    container.add(footer);

    JLabel[] imgArr = new JLabel[7];

    for (int i = 0; i < imgArr.length; i++){
      BufferedImage img = null;
      String fileName = "./Assets/"+(i+1)+".png";
      System.out.println(fileName);
      try{
        img = ImageIO.read(new File("./Assets/", (i+1)+".png"));
      }
      catch (IOException e){
          e.printStackTrace();
      }
      if (img != null){
        ImageIcon ic = new ImageIcon(img);
        imgArr[i] = new JLabel(ic);
      }
    }

    boolean carrotPlaced = false;
    for (int i = 0; i < this.boardArr.length; i++){
      for (int j = 0; j < this.boardArr[i].length ;j++) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setBorder(BorderFactory.createLineBorder(Color.black));
        p.setPreferredSize(new Dimension(112,112));
        if (board.get(i).get(j) instanceof Character) {
          Character c = Character.class.cast(board.get(i).get(j));
          if (c.getNameChar() == "bugs"){
            p.add(imgArr[0]);
          }
          if (c.getNameChar() == "marvin"){
            p.add(imgArr[1]);
          }
          if (c.getNameChar() == "taz"){
            p.add(imgArr[2]);
          }
          if (c.getNameChar() == "tweety"){
            p.add(imgArr[3]);
          }
        }
        else if (board.get(i).get(j) instanceof Mountain) {
          p.add(imgArr[6]);
        }

        else if (board.get(i).get(j) instanceof Carrot) {
          if (!carrotPlaced){
            p.add(imgArr[4]);
            carrotPlaced = true;
          }
          else p.add(imgArr[5]);
        }
        else{
        }
        this.boardArr[i][j] = p;
      }
    }

    for (int i = 0; i < this.boardArr.length; i++){
      for (int j = 0; j < this.boardArr[i].length ;j++) {
        main.add(this.boardArr[i][j]);
      }
    }

    this.mainFrame.setVisible(true);
  }

  class FrameBackground extends JPanel {
    private static final long serialVersionUID = 1L;

    @Override
    public void paintComponent(Graphics g){

      BufferedImage img = null;
      try {
      img = ImageIO.read(new File("./Assets/Background.png"));
      }
      catch (IOException e){
        e.printStackTrace();
      }

      g.drawImage(img, 0, 0, null);
    }
  }
}
