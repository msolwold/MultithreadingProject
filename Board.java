import java.util.*;

/**
 * Board Class
 *
 * createBoard
 * printBoard
 */

public class Board {

  private Character[] characters;
  private Carrot[] carrotArr;
  private Mountain mountain;

  private ArrayList<ArrayList> board;
  private HashMap<Integer, int[]> openPositions;
  private HashMap<Integer, int[]> characterPositions;
  private ArrayList<Carrot> looseCarrots;


  public Board(Character[] characters, Carrot[] carrotArr, Mountain mtn){
    this.characters = characters;
    this.carrotArr = carrotArr;
    this.mountain = mtn;

    createBoard();

  }

  public void win(Character c, int[] coords){
    System.out.println(c.getNameChar() + " has won!");
    c.setWinner();
    this.board.get(coords[0]).set(coords[1], c.getId());
  }

  /**
   * Move character object pseudorandomly
   * @param c Character object being moved
   */
  public boolean moveCharacter(Character c){

    ArrayList<Integer> validMoves = testMoves(c);
    if (c.isWinner()) return true;

    int[] location = c.getLocation();

    validMoves.shuffle();

    switch(validMoves.get(0)){
      case 1:
        if (this.board.get(location[0]-1).get(location[1]-1) instanceof Carrot){
          Object obj = this.board.get(location[0]-1).get(location[1]-1).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]-1).set(location[1]-1, c.getId());
        break;

      case 2:
        if (this.board.get(location[0]).get(location[1]-1) instanceof Carrot){
          Object obj = this.board.get(location[0]).get(location[1]-1).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]).set(location[1]-1, c.getId());
        break;

      case 3:
        if (this.board.get(location[0]+1).get(location[1]-1) instanceof Carrot){
          Object obj = this.board.get(location[0]+1).get(location[1]-1).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]+1).set(location[1]-1, c.getId());
        break;

      case 4:
        if (this.board.get(location[0]-1).get(location[1]) instanceof Carrot){
          Object obj = this.board.get(location[0]-1).get(location[1]).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]-1).set(location[1], c.getId());
        break;

      case 6:
        if (this.board.get(location[0]+1).get(location[1]) instanceof Carrot){
          Object obj = this.board.get(location[0]+1).get(location[1]).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]+1).set(location[1], c.getId());
        break;

      case 7:
        if (this.board.get(location[0]-1).get(location[1]+1) instanceof Carrot){
          Object obj = this.board.get(location[0]-1).get(location[1]+1).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]-1).set(location[1]+1, c.getId());
        break;

      case 8:
        if (this.board.get(location[0]).get(location[1]+1) instanceof Carrot){
          Object obj = this.board.get(location[0]).get(location[1]+1).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]).set(location[1]+1, c.getId());
        break;

      case 9:
        if (this.board.get(location[0]+1).get(location[1]+1) instanceof Carrot){
          Object obj = this.board.get(location[0]+1).get(location[1]+1).getClass();
          if (obj instanceof Carrot) carrot = (Carrot) obj;
          c.setCarrot(carrot);
          looseCarrots.remove(obj);
        }
        else this.board.get(location[0]+1).set(location[1]+1, c.getId());
        break;
    }

  }

  public ArrayList<Integer> testMoves(Character c){
    ArrayList<Integer> moves = new ArrayList();
    int[] location = c.getLocation();
    if (c.getNameChar() == "marvin"){
      for (int i = -1, k = 1; i<2; i++){
        for (int j = -1; j < 2; j++, k++){

          int x = location[0]+=i;
          int y = location[1]+=j;

          try {
            if (c.hasCarrot() && this.board.get(x).get(y) instanceof Mountain){
              int[] coords = {x,y};
              win(c, coords);
              break;
            }
            else if (this.board.get(x).get(y) == " " ||this.board.get(x).get(y) instanceof Carrot || this.board.get(x).get(y) instanceof Character
                    && this.board.get(x).get(y).getNameChar() !=c.getNameChar()){
              validMoves.add(k);
            }
          }
          catch (Exception e) {

          }
        }
      }
    }
    else {
      if (c.hasCarrot() && this.board.get(x).get(y) instanceof Mountain){
        int[] coords = {x,y};
        win(c, coords);
        break;
      }
      else if (this.board.get(x).get(y) == " " ||this.board.get(x).get(y) instanceof Carrot){
        validMoves.add(k);
      }

    }
    return validMoves;
  }

  /**
   * Moves the carrots to random locations
   *
   * Every 3 Rounds this will be used
   */
  public void moveCarrots(){
    ArrayList<Carrots> moveableCarrots = this.looseCarrots;
    int[] s = this.openPositions.keySet().toArray();

    for(int i = 0; i < moveableCarrots.size(); i++){
      Carrot tempCarr = moveableCarrots.get(i);
      this.board.get(this.openPositions.get(s[0])).set(s[1], tempCarr);
      int[] oldLocation = tempCarr.getLocation();
      this.board.get(oldLocation[0]).set(oldLocation[1], " ");
      tempCarr.setLocation(x, y);
    }
  }

  /**
   * Prints the current board with locations of objects
   * @return String - Formatted output showing locations of objects
   */
  public String printBoard(){

    String printBoard = "/";

    for (int i = 0;i < this.board.size()-1;i++) {
      printBoard += "- - - ";
    }
    printBoard += "- - -\\\n";

    for (int i = 0;i<this.board.size();i++) {
      printBoard += "|";

      for (int j = 0;j<this.board.get(i).size();j++) {
        if (board.get(i).get(j) != " ") printBoard += board.get(i).get(j).toString()+"|";
        else printBoard += "     |";
      }

      if (i < this.board.get(i).size()-1){
        printBoard += "\n|";
        for (int j = 0;j < this.board.get(i).size()-1;j++) {
          printBoard += "- - - ";
        }
        printBoard += "- - -|\n";
      }
    }
    printBoard += "\n\\";
    for (int j = 0;j < this.board.get(j).size()-1;j++) {
      printBoard += "- - - ";
    }
    printBoard += "- - -/";
    return printBoard;
  }

  /**
   * Creates the board and openPosition Data Structures
   */
  private void createBoard(){

    int[] deck = new int[25];
    ArrayList<ArrayList> locations = new ArrayList<ArrayList>();
    HashMap<Integer, int[]> open = new HashMap<>();

    for(int i =0; i < 5; i++){
      ArrayList<Object> arr = new ArrayList<Object>();
      for (int j = 0; j < 5; j++){
        arr.add(" ");

        int[] c = {i,j};
        open.put(i+j, c);
      }
      locations.add(arr);
    }

    for(int i =0; i < 25; i++){
      deck[i] = i;
    }

    shuffle(deck);

    // Place Characters
    int i;
    for(i = 0; i < this.characters.length; i++){
      int row = deck[i] / 5;
      int column = 5-(deck[i] % 5);
      locations.get(row).set(column, this.characters[i]);
      this.characters[i].setLocation(column, row);
      open.remove(column+row);
      int[] c = {column,row};
      this.characterPositions.put(i, c);
    }

    // Place Carrots
    for (int j = 0; j < 2; i++,j++){
      int row = deck[i] / 5;
      int column = 5-(deck[i] % 5);
      locations.get(row).set(column, this.carrotArr[j]);
      this.carrotArr[j].setLocation(column, row);
      open.remove(column+row);
      this.looseCarrots.add(this.carrotArr[j]);
    }

    // Place Mountain
    int row = deck[i] / 5;
    int column = 5-(deck[i] % 5);
    locations.get(row).set(column, mountain);
    this.mountain.setLocation(column, row);
    open.remove(column+row);


    this.board = locations;
    this.openPositions = open;
  }

  /**
   * Shuffles the deck
   * Used for creating randomness when placing objects
   * @param deck int[] Array containing integers 0-24
   */
  private void shuffle(int[] deck){

    Random rand = new Random();

    for(int i = 0 ; i < deck.length; i++){

      int placeholder = deck[i];
      int randomNumber = Math.abs(rand.nextInt() % deck.length);

      deck[i] = deck[randomNumber];
      deck[randomNumber] = placeholder;

    }
  }
}
