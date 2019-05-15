import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Create thread character will occupy
 * Track if they are holding carrot
 * Track if the character has been killed by marvin
 *
 * @param thread Thread thread for the character object
 * @param name String name of character {"bugs", "marvin", "tweety", "taz"}
 * @param id String letter associated with character {"b","m","t", "d"}
 * @param dead Boolean if the character is alive
 * @param holding Boolean if the character is holding the carrot
 * @param location int[] x, y coordinates in array
 *
 * @constructor Character(name, abbv., game object, queue)
 *
 * @getters -
 * getNameChar returns the name of the Character
 * getLocation returns int[] with x, y coordinates
 * getCarrot returns the carrot the character is holding
 *
 * @setters -
 * setLocation set location in int[] with x, y coordinates
 *
 * hasCarrot - boolean
 *
 * run()
 * adds Character to queue then executes game
 */

public class Character extends Thread {

  //////////////////////
  // Local Variables  //
  //////////////////////

  private boolean debug;

  private Game game;

  // Indentifiers
  private String name;
  private String initial;
  private String id;

  // Variables used to track state
  private boolean dead = false;
  private boolean winner = false;
  private AtomicBoolean alive = new AtomicBoolean(false);
  private boolean holding = false;
  private Carrot carrot;

  // Coordinates of the character
  private int[] location;

  /**
   * Creates character object with thread attributes
   * @param name  String name of character
   * @param n     String initial for character to use
   * @param g     Game game object
   * @param debug boolean debug option true produced enhanced outputs
   */
  public Character(String name, String n, Game g, boolean debug) {

    this.debug = debug;

    this.name = name;
    this.initial = n;
    this.id = "  " + n + "  ";
    this.game = g;

    this.alive.set(true);

    this.location = new int[2];
  }

  /**
   * Returns the name of the character
   * @return String name of character
   */
  public String getNameChar() {
    return this.name;
  }

  /**
   * Returns the location of the character
   * @return int[] coordinates of the character
   */
  public int[] getLocation() {
    return this.location;
  }

  /**
   * returns the carrot object the character is holding
   * @return Carrot carrot character is holding
   */
  public Carrot getCarrot(){
    return this.carrot;
  }

  /**
   * prints the ID of the character
   * @return String ID of the character
   */
  public String toString() {
    return this.id;
  }

  /**
   * Used for debugging
   * enhanced output
   */
  public void toStringL() {
    System.out.println(this.name + " is at position " + Arrays.toString(this.location));
  }

  /**
   * Sets this character as the winner. Updates the ID to reflect being on the mountain
   */
  public void setWinner() {
    this.winner = true;
    this.id = " M(" + this.initial + ") ";
  }

  /**
   * Sets location of character
   * @param x int x coordinate
   * @param y int y coordinate
   */
  public void setLocation(int x, int y) {
    this.location[0] = x;
    this.location[1] = y;
  }

  /**
   * Sets the carrot object of the character
   * @param c Carrot carrot object character picked up
   *
   * Has added functionality that updates the holder of the carrot class
   */
  public void setCarrot(Carrot c) {
    this.carrot = c;
    this.holding = true;
    this.id = " " + this.initial + "(c)";
    c.setHolder(this);
  }

  /**
   * Used when marvin kills the character
   * @return Carrot returns the carrot object the character was holding if there is one
   */
  public Carrot kill() {

    System.out.println(this.name + " is being killed!");

    this.dead = true;
    this.interrupt();
    this.alive.set(false);
    this.game.characterKilled();

    if (this.hasCarrot()) {
      this.holding = false;
      return this.carrot;
    }

    else
      return null;
  }

  /**
   * Ends the game, kills this thread
   */
  public void endGame() {
    this.interrupt();
    this.alive.set(false);
    System.out.println("Removing " + this.name + "...");
  }

  /**
   * returns the current state of the character
   * @return boolean true of dead false if not
   */
  public boolean isDead() {
    return this.dead;
  }

  /**
   * Returns if the character is the winner
   * @return boolean true for winner, false if not
   */
  public boolean isWinner() {
    return this.winner;
  }

  /**
   * method that returns state of character
   * @return boolean true if character is holding carrot
   */
  public boolean hasCarrot() {
    return this.holding;
  }

  /**
   * Overrides thread.run method.
   * Runs until the thread is marked dead
   *
   * Attempts to call play method, when it is done it will wait for 1000ms and then loop
   */
  public void run() {

    while (alive.get()) {

      try {

        game.play(this);
        Thread.sleep(1000);

      } catch (Exception e) {

        if (this.debug)
          System.out.println("Thread Interrupted...");

      }
    }
  }
}
