/**
 * TODO
 * Make class for board space that draws the image and
 * contains a method that swaps labels from one panel to another
 * Maybe put the images in the Character classes?
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.util.*;

import java.io.*;
import java.io.IOException;

import javax.imageio.*;
import java.awt.image.BufferedImage;

public class VisualGameBoard {


  private int winner = 0;

  private JFrame mainFrame = new JFrame("Looney Tunes Game Board");
  private BoardPanel[][] boardArr = new BoardPanel[5][5];
  private BoardPanel[][] round2Board = new BoardPanel[2][10];
  private JLabel[] imgArr = new JLabel[22];
  private JPanel[] winnerSquares = new JPanel[2];
  private JPanel samBox;

  public VisualGameBoard(){
    getImages();
  }

  public void updateMountainTile(int[] x, int[] y){
    boardArr[y[1]][y[0]].remove(imgArr[10]);
    boardArr[y[1]][y[0]].revalidate();
    boardArr[y[1]][y[0]].repaint();
    boardArr[x[1]][x[0]].revalidate();
    boardArr[x[1]][x[0]].repaint();
    boardArr[x[1]][x[0]].add(imgArr[10]);
  }

  public void setWinner(Character c) {
    int[] location = c.getLocation();
    boardArr[location[1]][location[0]].clearPanel();
    this.winnerSquares[this.winner].revalidate();
    this.winnerSquares[this.winner].repaint();
    this.winnerSquares[this.winner].add(imgArr[c.getID()+12]);
    this.winnerSquares[this.winner].setOpaque(false);
    this.winner++;

  }

  public void updateCharacterTiles(int[] x, int[] y, Character c, boolean carrot, boolean kill){

    if (c.getNameChar() == "bugs"){
      BoardPanel p = boardArr[y[1]][y[0]];
      p.swapPanel(boardArr[x[1]][x[0]], c, carrot);
    }

    if (c.getNameChar() == "marvin"){
      BoardPanel p = boardArr[y[1]][y[0]];
      p.swapPanelMarvin(boardArr[x[1]][x[0]], c, carrot, kill);
    }

    if (c.getNameChar() == "taz"){
      BoardPanel p = boardArr[y[1]][y[0]];
      p.swapPanel(boardArr[x[1]][x[0]], c, carrot);
    }

    if (c.getNameChar() == "tweety"){
      BoardPanel p = boardArr[y[1]][y[0]];
      p.swapPanel(boardArr[x[1]][x[0]], c, carrot);
    }

  }

  public void setRoundTwo(Character c, Character t){

    BoardPanel cPanel = round2Board[0][0];
    BoardPanel tPanel = round2Board[1][0];
    System.out.println(c.getID());
    System.out.println(t.getID());
    cPanel.setRoundTwo(c.getID(), this.imgArr[c.getID()+16]);
    tPanel.setRoundTwo(t.getID(), this.imgArr[t.getID()+16]);
  }

  public void moveRoundTwo(int id, int index){
    round2Board[id][index-1].moveRoundTwo(round2Board[id][index]);
  }

  public void freezeChar(int id, int index) {
    round2Board[id][index].freeze();
  }

  public void unFreezeChar(int id, int index){
    round2Board[id][index].unFreeze();
  }

  private void getImages(){
    for (int i = 0; i < this.imgArr.length; i++){
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
        this.imgArr[i] = new JLabel(ic);
      }
    }
  }

  public void turnBlue() {
    this.samBox.setBackground(new Color(0,0,255));
  }

  public void turnWhite() {
    this.samBox.setBackground(new Color(255,255,255));
  }

  public void createFrame(ArrayList<ArrayList> board) {


    this.mainFrame.setSize(890,792);

    FrameBackground container = new FrameBackground();
    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
    container.setPreferredSize(new Dimension(890,770));

    JPanel round1Container = new FrameBackground();
    round1Container.setLayout(new BoxLayout(round1Container, BoxLayout.Y_AXIS));
    round1Container.setPreferredSize(new Dimension(750,770));

    JPanel round2Container = new JPanel();
    round2Container.setLayout(new BoxLayout(round2Container, BoxLayout.Y_AXIS));
    round2Container.setPreferredSize(new Dimension(140,770));

    JPanel round2Winner = new JPanel();
    round2Winner.setPreferredSize(new Dimension(140,70));
    round2Winner.add(imgArr[20]);
    round2Winner.setBackground(new Color(255,255,255));
    round2Winner.setOpaque(true);
    this.samBox = round2Winner;

    JPanel round2Game = new JPanel();
    round2Game.setPreferredSize(new Dimension(140,700));
    round2Game.setOpaque(false);
    round2Game.setLayout(new GridLayout(10,2));

    JPanel header = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    header.setPreferredSize(new Dimension(750,188));
    header.setOpaque(false);

    JPanel headerContainerL = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    headerContainerL.setPreferredSize(new Dimension(365,188));
    headerContainerL.add(imgArr[21]);
    headerContainerL.setOpaque(false);

    JPanel headerContainerR = new JPanel(new FlowLayout(FlowLayout.TRAILING));
    headerContainerR.setPreferredSize(new Dimension(365,188));
    headerContainerR.setOpaque(false);

    JPanel winnerOne = new JPanel();
    winnerOne.setPreferredSize(new Dimension(175,188));
    winnerOne.setOpaque(false);

    JPanel winnerTwo = new JPanel();
    winnerTwo.setPreferredSize(new Dimension(175,188));
    winnerTwo.setOpaque(false);

    JPanel main = new JPanel();
    main.setPreferredSize(new Dimension(562,562));
    main.setOpaque(false);
    main.setLayout(new GridLayout(5,5));

    JPanel footer = new JPanel();
    footer.setPreferredSize(new Dimension(750,20));
    footer.setBorder(BorderFactory.createLineBorder(Color.black));
    footer.setOpaque(false);

    this.mainFrame.getContentPane().add(container);

    container.add(round1Container);
    container.add(round2Container);

    round1Container.add(header);
    round1Container.add(main);
    round1Container.add(footer);

    round2Container.add(round2Winner);
    round2Container.add(round2Game);

    header.add(headerContainerL);
    header.add(headerContainerR);


    /* TODO
    JButton startButton = new JButton("Start");
    MyActionListener actionListener = new MyActionListener(this, startButton);
    startButton.addActionListener(actionListener);

    headerContainerL.add(startButton);
    */

    this.winnerSquares[0] = winnerOne;
    this.winnerSquares[1] = winnerTwo;
    headerContainerR.add(winnerOne);
    headerContainerR.add(winnerTwo);

    for (int i = 0; i < this.boardArr.length; i++){
      for (int j = 0; j < this.boardArr[i].length ;j++) {
        BoardPanel p = new BoardPanel();
        System.out.println(i);
        System.out.println(j);
        p.setOpaque(false);
        p.setBorder(BorderFactory.createLineBorder(Color.black));
        p.setPreferredSize(new Dimension(112,112));
        if (board.get(i).get(j) instanceof Character) {
          Character c = Character.class.cast(board.get(i).get(j));
          if (c.getNameChar() == "bugs"){
            p.add(imgArr[0]);
            p.set(imgArr[0], 0);
          }
          if (c.getNameChar() == "marvin"){
            p.add(imgArr[1]);
            p.set(imgArr[1], 1);
          }
          if (c.getNameChar() == "taz"){
            p.add(imgArr[2]);
            p.set(imgArr[2], 2);
          }
          if (c.getNameChar() == "tweety"){
            p.add(imgArr[3]);
            p.set(imgArr[3], 3);
          }
        }
        else if (board.get(i).get(j) instanceof Mountain) {
          p.add(imgArr[10]);
          p.set(imgArr[10], 10);
        }

        else if (board.get(i).get(j) instanceof Carrot) {
          if (Carrot.class.cast(board.get(i).get(j)).getID() == 0){
            p.add(imgArr[8]);
            p.set(imgArr[8], 8);
          }
          else {
            p.add(imgArr[9]);
            p.set(imgArr[9], 9);
          }
        }
        else{
        }
        this.boardArr[i][j] = p;
        main.add(p);
      }
    }

    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 2; j++){
        BoardPanel p = new BoardPanel();
        p.setOpaque(true);
        p.setBorder(BorderFactory.createLineBorder(Color.black));
        p.setPreferredSize(new Dimension(70,70));

        this.round2Board[j][9-i] = p;
        round2Game.add(p);
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

  class BoardPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JLabel img;
    private int index;
    private boolean recentlyKilled;

    public void set(JLabel img, int index){
      this.img = img;
      this.index = index;
    }

    public void clearPanel(){
      this.remove(this.img);
      this.revalidate();
      this.repaint();
    }

    public void freeze(){
      this.setBackground(new Color(0,0,225));
    }

    public void unFreeze(){
      this.setBackground(new Color(255,255,255));
    }

    public void setRoundTwo(int id, JLabel img) {
      this.img = img;
      this.index = id;
      this.revalidate();
      this.repaint();
      this.add(img);
    }

    public void moveRoundTwo(BoardPanel destination){
      this.remove(this.img);
      this.revalidate();
      this.repaint();

      destination.revalidate();
      destination.repaint();
      destination.setRoundTwo(this.index,this.img);
      this.img = null;
      this.index = 0;
    }


    public void swapPanel(BoardPanel destination, Character c, boolean carrot){

      this.remove(this.img);
      this.revalidate();
      this.repaint();

      if (carrot) {
        destination.remove(imgArr[8+c.getCarrot().getID()]);
        destination.revalidate();
        destination.repaint();
        this.img = imgArr[this.index+4];
        destination.add(this.img);
        destination.set(this.img, this.index);
      }

      else {
        destination.revalidate();
        destination.repaint();
        destination.add(this.img);
        destination.set(this.img, this.index);
      }

      this.img = null;

    }

    public void swapPanelMarvin(BoardPanel destination, Character c, boolean carrot, boolean kill){

      if (kill){

        Character k = c.getKilled();
        int killedIndex = k.getID();

        if (this.recentlyKilled) {
          this.remove(imgArr[11]);
          this.recentlyKilled = false;
        }

        else this.remove(this.img);

        this.revalidate();
        this.repaint();

        destination.recentlyKilled = true;

        if (k.hasCarrot()) destination.remove(imgArr[killedIndex+4]);
        else destination.remove(imgArr[killedIndex]);
        destination.revalidate();
        destination.repaint();
        if (c.hasCarrot()){
           this.img = imgArr[5];
           destination.add(this.img);
           destination.set(this.img, this.index);
         }
        else {
          destination.add(imgArr[11]);
          destination.set(this.img, this.index);
        }

      }

      else {

        if (this.recentlyKilled) {
          this.remove(imgArr[11]);
          this.recentlyKilled = false;
        }

        else this.remove(this.img);
        this.revalidate();
        this.repaint();

        if (carrot) {
          destination.remove(imgArr[8+c.getCarrot().getID()]);
          destination.revalidate();
          destination.repaint();
          this.img = imgArr[this.index+4];
          destination.add(this.img);
          destination.set(this.img, this.index);
        }

        else {
          destination.revalidate();
          destination.repaint();
          destination.add(this.img);
          destination.set(this.img, this.index);
        }

      }
      this.set(null, -1);
    }

  }
}
