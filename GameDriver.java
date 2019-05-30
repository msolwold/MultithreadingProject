import java.util.concurrent.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Sets game up. Creates objects and calls builder methods
 * Starts 4 threads and waits.
 *
 * @param debug boolean debugging option that will print out more detailed moves/decisionss
 *
 *
 * TODO - Marvin kills everyone / has both carrots
 */

public class GameDriver {

  // Toggle for enhanced outputs
  public static boolean debug = false;

  public static Character[] charArr = new Character[4];

  public static void main(String[] args) {

    Game g1 = new Game(debug);


    //////////////////////////////
    // Create character Threads //
    //////////////////////////////

    if (debug)
      System.out.println("Creating Threads...");
    Character marvin = new Character(1, "marvin", "m", g1, debug);
    Character bugs = new Character(0, "bugs", "b", g1, debug);
    Character taz = new Character(2, "taz", "d", g1, debug);
    Character tweety = new Character(3, "tweety", "t", g1, debug);

    ////////////////////////////////////////////////
    // Create Carrot, Mountain, and Board Objects //
    ////////////////////////////////////////////////

    if (debug)
      System.out.println("Creating Carrots...");
    Carrot c1 = new Carrot(0);
    Carrot c2 = new Carrot(1);
    Carrot[] carrotArr = { c1, c2 };

    if (debug)
      System.out.println("Creating Mountains...");
    Mountain mountain = new Mountain();

    charArr[1] = marvin;
    charArr[0] = bugs;
    charArr[2] = taz;
    charArr[3] = tweety;

    if (debug)
      System.out.println("Creating Board...");
    Board board = new Board(g1, charArr, carrotArr, mountain, debug);

    g1.setBoard(board, charArr);
    System.out.println(board.printBoard());

    startGame();

  }


  public static void startGame(){


    if (debug)
      System.out.println("Starting Threads...");
    if (debug)
      System.out.println("------- Starting Game -------");

    ///////////////////
    // Start Threads //
    ///////////////////

    for (int i = 0; i < charArr.length; i++){
      charArr[i].start();
    }

    /////////////////////////////////
    //Wait for threads to complete //
    /////////////////////////////////

    for (int i = 0; i < charArr.length; i++){
      try {
        charArr[i].join();
      } catch (Exception e) {
        System.out.println("Interrupted");
      }
    }

  }
}
