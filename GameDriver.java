import java.util.concurrent.*;
import java.util.*;

/**
 * Will hold outputs that introduce the game and will start the game
 *
 * @param debug boolean debugging option that will print out more detailed moves/decisionss
 *
 */


public class GameDriver{

  public static void main(String[] args){

    boolean debug = true;

    ConcurrentLinkedQueue<Character> q = new ConcurrentLinkedQueue<Character>();
    Game g1 = new Game(q, debug);

    //////////////////////////////
    // Create character Threads //
    //////////////////////////////

    if (debug) System.out.println("Creating Threads...");
    Character marvin = new Character("marvin", "m", g1, q);
    Character bugs = new Character("bugs", "b", g1, q);
    Character taz = new Character("taz", "d", g1, q);
    Character tweety = new Character("tweety", "t", g1, q);

    ////////////////////////////////////////////////
    // Create Carrot, Mountain, and Board Objects //
    ////////////////////////////////////////////////

    if (debug) System.out.println("Creating Carrots...");
    Carrot c1 = new Carrot(1);
    Carrot c2 = new Carrot(2);
    Carrot[] carrotArr = {c1, c2};

    if (debug) System.out.println("Creating Mountains...");
    Mountain mountain = new Mountain();

    Character[] charArr = {marvin, bugs, taz, tweety};
    if (debug) System.out.println("Creating Board...");
    Board board = new Board(charArr, carrotArr, mountain, debug);

    g1.setBoard(board);

    ///////////////////
    // Start Threads //
    ///////////////////

    if (debug) System.out.println("Starting Threads...");
    marvin.start();
    bugs.start();
    taz.start();
    tweety.start();

    try {
      marvin.join();
      bugs.join();
      taz.join();
      tweety.join();
    }
    catch (Exception e) {
      System.out.println("Interrupted");
    }
  }
}
