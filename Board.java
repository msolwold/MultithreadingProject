import java.util.*;
import java.util.Collections.*;

/**
 * Board class
 *
 * Full control over movement on the board.
 * Has functions that print board as well as creates it
 *
 * moveCharacter returns true or false indicating a winner
 */

public class Board {

  /////////////////////
  // Local Variables //
  /////////////////////

  private boolean debug = false;

  // Board pieces
  private Character[] characters;
  private Carrot[] carrotArr;
  private Mountain mountain;

  // Current character being moved
  private Character c;

  // Container for board pieces
  private ArrayList<ArrayList> board;

  /**
   * Creates the board object
   * @param characters Character[] array of character objects for tracking purposes
   * @param carrotArr  Carrot[] Array of carrots
   * @param mtn        Mountain mountain object
   * @param debug      boolean debug option set in GameDriver
   */
  public Board(Character[] characters, Carrot[] carrotArr, Mountain mtn, boolean debug) {
    this.characters = characters;
    this.carrotArr = carrotArr;
    this.mountain = mtn;

    this.debug = debug;

    createBoard();
  }

  ////////////////////////
  // Movement Functions //
  ////////////////////////

  /**
   * Move character object pseudorandomly
   * @param c Character object being moved
   */
  public boolean moveCharacter(Character c) {

    if (this.debug) {
      System.out.println("-----------------");
      for (int x = 0; x < this.characters.length; x++) {
        System.out.println(
            this.characters[x].getNameChar() + " is at postion " + Arrays.toString(this.characters[x].getLocation()));
      }

      for (int x = 0; x < 2; x++) {
        System.out.println(
            this.carrotArr[x].toString() + " is at postion " + Arrays.toString(this.carrotArr[x].getLocation()));
      }

      System.out.println(this.mountain.toString() + " is at postition " + Arrays.toString(this.mountain.getLocation()));
      System.out.println("-----------------");
    }

    this.c = c;

    ArrayList<Integer> validMoves = testMoves();
    if (c.isWinner())
      return true;

    if (this.debug)
      System.out.println("The valid moves are: " + validMoves.toString());

    if (this.c.isWinner())
      return true;

    int[] location = this.c.getLocation();
    if (this.debug)
      this.c.toStringL();

    Collections.shuffle(validMoves);

    if (this.debug)
      System.out.println("The case we want is " + validMoves.get(0));

    switch (validMoves.get(0)) {

    case 1:

      if (this.debug)
        System.out.println("Top Left...");

      setPosition(new int[] { location[0] - 1, location[1] - 1 }, location);

      break;

    case 2:
      if (this.debug)
        System.out.println("Top...");

      setPosition(new int[] { location[0], location[1] - 1 }, location);
      break;

    case 3:

      if (this.debug)
        System.out.println("Top Right...");

      setPosition(new int[] { location[0] + 1, location[1] - 1 }, location);

      break;

    case 4:

      if (this.debug)
        System.out.println("Middle Left...");

      setPosition(new int[] { location[0] - 1, location[1] }, location);

      break;

    case 6:

      if (this.debug)
        System.out.println("Middle Right...");

      setPosition(new int[] { location[0] + 1, location[1] }, location);

      break;

    case 7:

      if (this.debug)
        System.out.println("Bottom Left...");

      setPosition(new int[] { location[0] - 1, location[1] + 1 }, location);

      break;

    case 8:

      if (this.debug)
        System.out.println("Bottom...");

      setPosition(new int[] { location[0], location[1] + 1 }, location);

      break;

    case 9:

      if (this.debug)
        System.out.println("Bottom Right...");

      setPosition(new int[] { location[0] + 1, location[1] + 1 }, location);

      break;
    }
    return false;
  }

  /**
   * Tests possible moves for the Character to make
   *
   * @return   ArrayList<Integer> list of valid moves
   */
  public ArrayList<Integer> testMoves() {

    if (this.debug)
      System.out.println("Testing for Moves...");

    ArrayList<Integer> moves = new ArrayList();
    int[] location = this.c.getLocation();

    if (this.c.getNameChar() == "marvin") {
      if (this.debug)
        System.out.println("We have Marvin looking for a move...");
      for (int i = -1, k = 1; i < 2; i++) {
        for (int j = -1; j < 2; j++, k++) {

          //if (this.debug) System.out.println("i: " + i + " j: " + j + " k: " + k);

          int x = location[0] + j;
          int y = location[1] + i;
          if (this.debug)
            System.out.println("x: " + x + " y: " + y);

          try {
            if (debug)
              System.out.println("[" + x + ", " + y + "] is " + this.board.get(y).get(x));

            if (this.c.hasCarrot() && this.board.get(y).get(x) instanceof Mountain) {
              if (this.debug)
                System.out.println("Marvin is climbing the mountain...");
              System.out.println(c.getNameChar() + " has won!");
              c.setWinner();
              break;
            }

            else if (this.board.get(y).get(x) instanceof Character
                && !(Character.class.cast(this.board.get(y).get(x)).getNameChar().equals(this.c.getNameChar()))
                && Character.class.cast(this.board.get(y).get(x)).hasCarrot()) {
              ArrayList<Integer> al = new ArrayList();
              al.add(k);
              return al;
            }

            else if (this.board.get(y).get(x) == " " || this.board.get(y).get(x) instanceof Carrot
                || this.board.get(y).get(x) instanceof Character
                    && !(Character.class.cast(this.board.get(y).get(x)).getNameChar().equals(this.c.getNameChar()))) {

              if (this.debug)
                System.out.println("Adding " + k + " to the list because [" + x + ", " + y + "] is "
                    + this.board.get(y).get(x).toString());

              moves.add(k);
            }
          } catch (Exception e) {
            //System.out.println("Tested valid move and caught: "+e);
          }
        }
      }
    } else {
      for (int i = -1, k = 1; i < 2; i++) {
        for (int j = -1; j < 2; j++, k++) {

          //if (this.debug) System.out.println("i: " + i + " j: " + j + " k: " + k);

          int x = location[0] + j;
          int y = location[1] + i;
          if (this.debug)
            System.out.println("x: " + x + " y: " + y);

          try {
            if (debug)
              System.out.println("[" + x + ", " + y + "] is " + this.board.get(y).get(x));

            if (this.c.hasCarrot() && this.board.get(y).get(x) instanceof Mountain) {
              if (this.debug)
                System.out.println(this.c.toString() + " is climbing the mountain...");
              System.out.println(c.getNameChar() + " has won!");
              c.setWinner();
              c.interrupt();
            } else if (this.board.get(y).get(x) == " "
                || this.board.get(y).get(x) instanceof Carrot && !c.hasCarrot()) {

              if (this.debug)
                System.out.println(
                    "Adding " + k + " to the list because [" + x + ", " + y + "] is " + this.board.get(y).get(x));

              moves.add(k);
            }
          } catch (Exception e) {
            //System.out.println("Tested valid move and caught: "+e);
          }
        }
      }
    }

    return moves;
  }

  /**
   * Sets the position of the character
   * @param newCoords int[] coordinates of where the character is moving to
   * @param location  int[] coordinates of where the character was
   */
  private void setPosition(int[] newCoords, int[] location) {

    if (this.board.get(newCoords[1]).get(newCoords[0]) instanceof Carrot) {
      Carrot carrot = Carrot.class.cast(this.board.get(newCoords[1]).get(newCoords[0]));

      if (this.debug)
        System.out.println(this.c.getNameChar() + " found " + carrot.toString());

      this.c.setCarrot(carrot);
      if (this.debug) System.out.println(c.toString() + " picked up Carrot " + carrot.getID());
    }

    else if (this.c.getNameChar() == "marvin" && this.board.get(newCoords[1]).get(newCoords[0]) instanceof Character) {
      Character deadChar = Character.class.cast(this.board.get(newCoords[1]).get(newCoords[0]));
      if (deadChar.hasCarrot())
        this.c.setCarrot(deadChar.kill());
        if (this.debug) System.out.println(c.toString() + " stole Carrot " + c.getCarrot().getID());
      else
        deadChar.kill();
    }

    if (this.debug)
      System.out.println(
          "Moving the Character from " + Arrays.toString(location) + " to " + Arrays.toString(newCoords) + "\n");

    if (this.debug) {
      System.out.println("Before: [");
      for (int i = 0; i < this.board.size(); i++) {
        for (int j = 0; j < this.board.get(i).size(); j++) {
          System.out.print(this.board.get(i).get(j) + ", ");
        }
      }
      System.out.println("]");
    }

    this.board.get(newCoords[1]).set(newCoords[0], this.c);

    this.board.get(location[1]).set(location[0], " ");
    this.c.setLocation(newCoords[0], newCoords[1]);

    if (this.debug) {
      System.out.println("After: [");
      for (int i = 0; i < this.board.size(); i++) {
        for (int j = 0; j < this.board.get(i).size(); j++) {
          System.out.print(this.board.get(i).get(j) + ", ");
        }
      }
      System.out.println("]");
    }
  }

  /**
   * Moves the Mountain to a random location
   *
   * Every 3 Rounds this will be used
   */
  public void moveMountain() {

    if (this.debug)
      System.out.println("Moving Mountains...");

    ArrayList<int[]> openPositions = new ArrayList();
    for (int i = 0; i < this.board.size(); i++) {
      for (int j = 0; j < this.board.get(i).size(); j++) {
        if (this.board.get(i).get(j) == " ")
          openPositions.add(new int[] { j, i });
      }
    }

    if (this.debug) {
      System.out.println("Valid Moves are: ");
      for (int i = 0; i < openPositions.size(); i++) {
        System.out.print(Arrays.toString(openPositions.get(i)) + ", ");
      }
    }

    Collections.shuffle(openPositions);

    this.board.get(openPositions.get(0)[1]).set(openPositions.get(0)[0], this.mountain);
    this.board.get(this.mountain.getLocation()[1]).set(this.mountain.getLocation()[0], " ");

    if (this.debug)
      System.out.println("Moving mountain from " + Arrays.toString(this.mountain.getLocation()) + " to "
          + Arrays.toString(openPositions.get(0)));

    this.mountain.setLocation(openPositions.get(0)[0], openPositions.get(0)[1]);
  }

  //////////////////////////////////////////////////////
  // Helper Functions                                 //
  // Print Board, Create Board, Shuffle for placement //
  //////////////////////////////////////////////////////

  /**
   * Prints the current board with locations of objects
   * @return String - Formatted output showing locations of objects
   */
  public String printBoard() {

    if (this.debug)
      System.out.println("Printing Board...");

    String printBoard = "/";

    for (int i = 0; i < this.board.size() - 1; i++) {
      printBoard += "- - - ";
    }
    printBoard += "- - -\\\n";

    for (int i = 0; i < this.board.size(); i++) {
      printBoard += "|";

      for (int j = 0; j < this.board.get(i).size(); j++) {

        if (board.get(i).get(j) != " ") {
          if (board.get(i).get(j) instanceof Character) {
            printBoard += Character.class.cast(this.board.get(i).get(j)).toString() + "|";
          }
          if (board.get(i).get(j) instanceof Mountain) {
            printBoard += Mountain.class.cast(this.board.get(i).get(j)).toString() + "|";
          }
          if (board.get(i).get(j) instanceof Carrot) {
            printBoard += Carrot.class.cast(this.board.get(i).get(j)).toString() + "|";
          }
        } else
          printBoard += "     |";
      }

      if (i < this.board.get(i).size() - 1) {
        printBoard += "\n|";
        for (int j = 0; j < this.board.get(i).size() - 1; j++) {
          printBoard += "- - - ";
        }
        printBoard += "- - -|\n";
      }
    }
    printBoard += "\n\\";
    for (int j = 0; j < this.board.get(j).size() - 1; j++) {
      printBoard += "- - - ";
    }
    printBoard += "- - -/";
    return printBoard;
  }

  /**
   * Creates the board and openPosition Data Structures
   */
  private void createBoard() {

    int[] deck = new int[25];
    ArrayList<ArrayList> locations = new ArrayList<ArrayList>();

    for (int i = 0; i < 5; i++) {
      ArrayList<Object> arr = new ArrayList<Object>();
      for (int j = 0; j < 5; j++) {
        arr.add(" ");
      }
      locations.add(arr);
    }

    for (int i = 0; i < 25; i++) {
      deck[i] = i;
    }

    if (this.debug)
      System.out.println(Arrays.toString(deck));

    shuffle(deck);

    if (this.debug)
      System.out.println(Arrays.toString(deck));

    // Place Characters
    int i;
    for (i = 0; i < this.characters.length; i++) {
      if (this.debug)
        System.out.println("Deck value is: " + deck[i]);
      int row = (deck[i] / 5);
      int column = (deck[i] % 5);
      if (this.debug)
        System.out.println("row: " + row + " column " + column + " deck[i] " + deck[i]);
      locations.get(row).set(column, this.characters[i]);
      this.characters[i].setLocation(column, row);
    }

    if (this.debug) {
      for (int j = 0; j < this.characters.length; j++) {
        System.out.println(
            this.characters[j].getNameChar() + " is at postion " + Arrays.toString(this.characters[j].getLocation()));
      }
    }

    // Place Carrots
    for (int j = 0; j < 2; i++, j++) {
      System.out.println(deck[i]);
      int row = deck[i] / 5;
      int column = (deck[i] % 5);
      locations.get(row).set(column, this.carrotArr[j]);
      this.carrotArr[j].setLocation(column, row);
    }

    if (this.debug) {
      for (int j = 0; j < 2; j++) {
        System.out.println(
            this.carrotArr[j].toString() + " is at postion " + Arrays.toString(this.carrotArr[j].getLocation()));
      }
    }

    // Place Mountain
    int row = deck[i] / 5;
    int column = (deck[i] % 5);
    locations.get(row).set(column, mountain);
    this.mountain.setLocation(column, row);

    if (this.debug)
      System.out.println(this.mountain.toString() + " is at postition " + Arrays.toString(this.mountain.getLocation()));

    this.board = locations;
  }

  /**
   * Shuffles the deck
   * Used for creating randomness when placing objects
   * @param deck int[] Array containing integers 0-24
   */
  private void shuffle(int[] deck) {

    Random rand = new Random();

    for (int i = 0; i < deck.length; i++) {

      int placeholder = deck[i];
      int randomNumber = Math.abs(rand.nextInt() % deck.length);

      deck[i] = deck[randomNumber];
      deck[randomNumber] = placeholder;

    }
  }
}
