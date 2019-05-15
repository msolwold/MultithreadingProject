import java.util.concurrent.*;
import java.util.*;

/**
 * Sets game up. Creates objects and calls builder methods
 * Starts 4 threads and waits.
 *
 * @param debug boolean debugging option that will print out more detailed moves/decisionss
 *
 */

public class GameDriver {

  public static void main(String[] args) {

    // Toggle for enhanced outputs
    boolean debug = false;

    Game g1 = new Game(debug);

    //////////////////////////////
    // Create character Threads //
    //////////////////////////////

    if (debug)
      System.out.println("Creating Threads...");
    Character marvin = new Character("marvin", "m", g1, debug);
    Character bugs = new Character("bugs", "b", g1, debug);
    Character taz = new Character("taz", "d", g1, debug);
    Character tweety = new Character("tweety", "t", g1, debug);

    ////////////////////////////////////////////////
    // Create Carrot, Mountain, and Board Objects //
    ////////////////////////////////////////////////

    if (debug)
      System.out.println("Creating Carrots...");
    Carrot c1 = new Carrot(1);
    Carrot c2 = new Carrot(2);
    Carrot[] carrotArr = { c1, c2 };

    if (debug)
      System.out.println("Creating Mountains...");
    Mountain mountain = new Mountain();

    Character[] charArr = { marvin, bugs, taz, tweety };
    if (debug)
      System.out.println("Creating Board...");
    Board board = new Board(charArr, carrotArr, mountain, debug);

    g1.setBoard(board, charArr);
    System.out.println(board.printBoard());

    ///////////////////
    // Start Threads //
    ///////////////////

    if (debug)
      System.out.println("Starting Threads...");
    if (debug)
      System.out.println("------- Starting Game -------");
    marvin.start();
    bugs.start();
    taz.start();
    tweety.start();

    /////////////////////////////////
    //Wait for threads to complete //
    /////////////////////////////////

    try {
      marvin.join();
      bugs.join();
      taz.join();
      tweety.join();
    } catch (Exception e) {
      System.out.println("Interrupted");
    }
  }
}
