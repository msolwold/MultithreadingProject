import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * Tracks rounds and contains the locks that create synchronization
 * Contains the checker for winner
 * Allows 1 thread to access moveCharacter at a time
 *
 */

public class Game {

  /////////////////////
  // Local Variables //
  /////////////////////

  private boolean debug = false;
  private int gameID = (int) (Math.random() * 100);

  // Lock used for enforcing synchronization
  ReentrantLock reeLock = new ReentrantLock();

  // boolean for tracking winner
  private boolean winner = false;

  // Tracks rounds for mountain movement
  // Will force mountain to move every 3 rounds of gameplay
  private int mountainTrigger = 15;
  private int roundCounter = 1;

  // board and character containers
  private Board board;
  private Character[] characters;

  // used to maintain smooth gameplay
  private int time = 3000;

  /**
   * Game constructor
   * @param debug boolean debug variable, true indicated enhanced outputs
   */
  public Game(boolean debug) {
    this.debug = debug;
  }

  /**
   * Sets board and characters the game will use
   * @param board Board board object
   * @param c     Character[] array of character objects
   */
  public void setBoard(Board board, Character[] c) {
    this.board = board;
    this.characters = c;
  }

  /**
   * Called if a character is killed.
   * Decrements time to maintain smooth gameplay
   * lowers bound for rounds to maintain mountain movements
   */
  public void characterKilled() {
    this.time -= 1000;
    this.mountainTrigger -= 4;

  }

  /**
   * Called if a winner is found. Forces game to end by killing threads
   */
  private void winnerFound() {
    this.board.printBoard();
    for (int i = 0; i < this.characters.length; i++) {
      if (!characters[i].isDead())
        characters[i].endGame();
    }
  }

  /**
   * Play method that runs the game.
   * Uses a Reentrant lock to ensure only one thread enters at a time
   * @param c Character character object that is requesting to play
   */
  public void play(Character c) {

    //Attempts to lock the thread
    boolean tryReeLock = this.reeLock.tryLock();

    // If it succeeds it will be allowed in to get properly locked and ran
    if (tryReeLock && !c.isDead()) {

      try {

        // Lock the thread
        this.reeLock.lock();

        // Checks round number. Every 3 it will move the mountain
        if (this.roundCounter >= this.mountainTrigger) {
          this.board.moveMountain();
          this.roundCounter = 1;
        }

        try {

          // Character is moved after some outputs
          this.roundCounter++;
          System.out.println(c.toString() + " is up!");
          System.out.println(this.board.printBoard());
          System.out.println("Game: " + this.gameID);
          Thread.sleep(500);

          this.winner = this.board.moveCharacter(c);

          // Checks if character who recently played is the winner
          if (this.winner)
            winnerFound();
        }

        // Catches exception
        catch (InterruptedException e) {
          if (this.debug)
            System.out.println("Thread interruption: " + e);

          // Unlocks the thread allowing another thread to enter
        } finally {
          this.reeLock.unlock();
        }
      }

      finally {

        this.reeLock.unlock();

        try {

          // Puts the thread to sleep allowing for other threads to enter without competition
          // This is what creates the appearance of "turns"
          // this.time is dependent on # of active threads
          Thread.sleep(this.time);
        } catch (InterruptedException e) {
          if (this.debug)
            System.out.println("Thread Interruption: " + e);
        }
      }
    }
  }
}
