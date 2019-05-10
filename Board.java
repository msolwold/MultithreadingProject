import java.util.*;
import java.util.Collections.*;

/**
 * Board Class
 *
 * createBoard
 * printBoard
 */

public class Board {

  private boolean debug = false;

  private Character[] characters;
  private Carrot[] carrotArr;
  private Mountain mountain;

  private ArrayList<ArrayList> board;
  private HashMap<Integer, int[]> openPositions = new HashMap();
  private HashMap<Integer, int[]> characterPositions = new HashMap();


  public Board(Character[] characters, Carrot[] carrotArr, Mountain mtn, boolean debug){
    this.characters = characters;
    this.carrotArr = carrotArr;
    this.mountain = mtn;

    this.debug = debug;

    createBoard();

  }

  /**
   * Method is used for winner to set variables
   * @param c      Character winning player
   * @param coords int[] where the player hit the mountain
   */
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
    if (debug) System.out.println("The valid moves are: " + validMoves.toString());

    if (c.isWinner()) return true;

    int[] location = c.getLocation();
    if (debug) c.toStringL();

    Collections.shuffle(validMoves);

    if (debug) System.out.println("The case we want is " + validMoves.get(0));


    switch(validMoves.get(0)){

      case 1:
        if (debug) System.out.println("Top Left...");
        int[] newCoords = {location[0]-1,location[1]-1};

        if (this.board.get(newCoords[0]).get(newCoords[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords[0]).get(newCoords[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords[0]).get(newCoords[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords[0]).get(newCoords[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords[0]).set(newCoords[1], c.getId());

        this.openPositions.remove(newCoords[0]+newCoords[1]);
        this.openPositions.put(location[0]*location[1],location);
        break;

      case 2:
        if (debug) System.out.println("Top...");
        int[] newCoords2 = {location[0]-1,location[1]-1};

        if (this.board.get(newCoords2[0]).get(newCoords2[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords2[0]).get(newCoords2[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords2[0]).get(newCoords2[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords2[0]).get(newCoords2[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords2[0]).set(newCoords2[1], c.getId());

        this.openPositions.remove(newCoords2[0]+newCoords2[1]);
        this.openPositions.put(location[0]*location[1],location);
      break;

      case 3:
        if (debug) System.out.println("Top Right...");
        int[] newCoords3 = {location[0]+1,location[1]-1};

        if (this.board.get(newCoords3[0]).get(newCoords3[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords3[0]).get(newCoords3[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords3[0]).get(newCoords3[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords3[0]).get(newCoords3[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords3[0]).set(newCoords3[1], c.getId());

        this.openPositions.remove(newCoords3[0]+newCoords3[1]);
        this.openPositions.put(location[0]*location[1],location);
      break;

      case 4:
        if (debug) System.out.println("Middle Left...");
        int[] newCoords4 = {location[0]-1,location[1]};

        if (this.board.get(newCoords4[0]).get(newCoords4[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords4[0]).get(newCoords4[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords4[0]).get(newCoords4[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords4[0]).get(newCoords4[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords4[0]).set(newCoords4[1], c.getId());

        this.openPositions.remove(newCoords4[0]+newCoords4[1]);
        this.openPositions.put(location[0]*location[1],location);
      break;

      case 6:
        if (debug) System.out.println("Middle Right...");
        int[] newCoords6 = {location[0]+1,location[1]};

        if (this.board.get(newCoords6[0]).get(newCoords6[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords6[0]).get(newCoords6[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords6[0]).get(newCoords6[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords6[0]).get(newCoords6[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords6[0]).set(newCoords6[1], c.getId());

        this.openPositions.remove(newCoords6[0]+newCoords6[1]);
        this.openPositions.put(location[0]*location[1],location);
      break;

      case 7:
        if (debug) System.out.println("Bottom Left...");
        int[] newCoords7 = {location[0]-1,location[1]+1};

        if (this.board.get(newCoords7[0]).get(newCoords7[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords7[0]).get(newCoords7[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords7[0]).get(newCoords7[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords7[0]).get(newCoords7[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords7[0]).set(newCoords7[1], c.getId());

        this.openPositions.remove(newCoords7[0]+newCoords7[1]);
        this.openPositions.put(location[0]*location[1],location);
        break;

      case 8:
        if (debug) System.out.println("Bottom...");
        int[] newCoords8 = {location[0],location[1]+1};

        if (this.board.get(newCoords8[0]).get(newCoords8[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords8[0]).get(newCoords8[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords8[0]).get(newCoords8[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords8[0]).get(newCoords8[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords8[0]).set(newCoords8[1], c.getId());

        this.openPositions.remove(newCoords8[0]+newCoords8[1]);
        this.openPositions.put(location[0]*location[1],location);
      break;

      case 9:
        if (debug) System.out.println("Bottom Right...");
        int[] newCoords9 = {location[0]+1,location[1]+1};

        if (this.board.get(newCoords9[0]).get(newCoords9[1]) instanceof Carrot){
          Carrot carrot = Carrot.class.cast(this.board.get(newCoords9[0]).get(newCoords9[1]));
          c.setCarrot(carrot);
        }

        else if (c.getNameChar() == "marvin" && this.board.get(newCoords9[0]).get(newCoords9[1])
              instanceof Character){
          Character deadChar = Character.class.cast(this.board.get(newCoords9[0]).get(newCoords9[1]));
          if (deadChar.hasCarrot()) c.setCarrot(deadChar.kill());
        }

        this.board.get(newCoords9[0]).set(newCoords9[1], c.getId());

        this.openPositions.remove(newCoords9[0]+newCoords9[1]);
        this.openPositions.put(location[0]*location[1],location);
      break;
    }
    return false;
  }

  /**
   * Tests possible moves for the Character to make
   *
   * @param  c Character player being tested
   * @return   ArrayList<Integer> list of valid moves
   */
  public ArrayList<Integer> testMoves(Character c){

    if (debug) System.out.println("Testing for Moves...");

    ArrayList<Integer> moves = new ArrayList();
    int[] location = c.getLocation();

    if (c.getNameChar() == "marvin"){
      if (debug) System.out.println("We have Marvin looking for a move...");
      for (int i = -1, k = 1; i<2; i++){
        for (int j = -1; j < 2; j++, k++){

          int x = location[0]+=i;
          int y = location[1]+=j;
          if (debug) System.out.println("x: "+x+" y: "+y);


          try {
            if (c.hasCarrot() && this.board.get(x).get(y) instanceof Mountain){
              if (debug) System.out.println("Marvin is climbing the mountain...");
              int[] coords = {x,y};
              win(c, coords);
              break;
            }
            else if (this.board.get(x).get(y) == " " ||this.board.get(x).get(y) instanceof Carrot || this.board.get(x).get(y) instanceof Character
                    && Character.class.cast(this.board.get(x).get(y)).getNameChar() != c.getNameChar()){
              moves.add(k);
            }
          }
          catch (Exception e) {

          }
        }
      }
    }
    else {
      for (int i = -1, k = 1; i<2; i++){
        for (int j = -1; j < 2; j++, k++){

          int x = location[0]+=i;
          int y = location[1]+=j;
          if (debug) System.out.println("x: "+x+" y: "+y);


          try {
            if (c.hasCarrot() && this.board.get(x).get(y) instanceof Mountain){
              if (debug) System.out.println(c.toString()+" is climbing the mountain...");
              int[] coords = {x,y};
              win(c, coords);
              break;
            }
            else if (this.board.get(x).get(y) == " " ||this.board.get(x).get(y) instanceof Carrot){
              moves.add(k);
            }
          }
          catch (Exception e) {

          }
        }
      }
    }
    if (debug) System.out.println("The list of moves is: "+moves.toString());

    return moves;
  }

  /**
   * Moves the Mountain to a random location
   *
   * Every 3 Rounds this will be used
   */
  public void moveMountain(){

    if (debug) System.out.println("Moving Mountains...");


    Object[] s = this.openPositions.keySet().toArray();

    if (debug) System.out.println("List of open Spaces: "+Arrays.toString(s));

    int x = (int)(Math.random()*s.length);

    int[] newCoords = openPositions.get((int) s[x]);
    int[] oldCoords = this.mountain.getLocation();

    this.board.get(newCoords[0]).set(newCoords[1], this.mountain);
    this.board.get(oldCoords[0]).set(oldCoords[1], " ");
    this.mountain.setLocation(newCoords[0], newCoords[1]);

    this.openPositions.remove(s[x]);
    this.openPositions.put(oldCoords[0]+oldCoords[1], oldCoords);
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
        if (board.get(i).get(j) != " ") {
          if (board.get(i).get(j) instanceof Character){
            printBoard += Character.class.cast(board.get(i).get(j)).toString()+"|";
          }
          if (board.get(i).get(j) instanceof Mountain){
            printBoard += Mountain.class.cast(board.get(i).get(j)).toString()+"|";
          }
          if (board.get(i).get(j) instanceof Carrot){
            printBoard += Carrot.class.cast(board.get(i).get(j)).toString()+"|";
          }
        }
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

    System.out.println(Arrays.toString(deck));

    shuffle(deck);

    System.out.println(Arrays.toString(deck));

    // Place Characters
    int i;
    for(i = 0; i < this.characters.length; i++){
      System.out.println(deck[i]);
      int row = deck[i] / 5;
      int column = (deck[i] % 5);
      locations.get(row).set(column, this.characters[i]);
      this.characters[i].setLocation(column, row);
      open.remove(column*row);
      int[] c = {column,row};
      this.characterPositions.put(i, c);
    }

    // Place Carrots
    for (int j = 0; j < 2; i++,j++){
      System.out.println(deck[i]);
      int row = deck[i] / 5;
      int column = (deck[i] % 5);
      locations.get(row).set(column, this.carrotArr[j]);
      this.carrotArr[j].setLocation(column, row);
      open.remove(column*row);
    }

    // Place Mountain
    int row = deck[i] / 5;
    int column = (deck[i] % 5);
    locations.get(row).set(column, mountain);
    this.mountain.setLocation(column, row);
    open.remove(column*row);


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
