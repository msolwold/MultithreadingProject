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

    ConcurrentLinkedQueue<Character> q = new ConcurrentLinkedQueue<Character>();
    Game g1 = new Game(q);

    //////////////////////////////
    // Create character Threads //
    //////////////////////////////
    Character marvin = new Character("marvin", "m", g1, q);
    Character bugs = new Character("bugs", "b", g1, q);
    Character taz = new Character("taz", "d", g1, q);
    Character tweety = new Character("tweety", "t", g1, q);

    ////////////////////////////////////////////////
    // Create Carrot, Mountain, and Board Objects //
    ////////////////////////////////////////////////
    Carrot c1 = new Carrot(1);
    Carrot c2 = new Carrot(2);
    Carrot[] carrotArr = {c1, c2};

    Mountain mountain = new Mountain();

    Character[] charArr = {marvin, bugs, taz, tweety};
    Board board = new Board(charArr, carrotArr, mountain);

    g1.setBoard(board);

    ///////////////////
    // Start Threads //
    ///////////////////
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
