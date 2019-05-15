import java.util.concurrent.*;
import java.util.*;

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

  private boolean debug;

  private Game game;
  private ConcurrentLinkedQueue<Character> q;

  private String name;
  private String initial;
  private String id;

  private boolean dead = false;
  private boolean winner = false;
  private boolean leader = false;

  private boolean holding = false;
  private Carrot carrot;

  private int[] location;

  public Character(String name, String n, Game g, ConcurrentLinkedQueue<Character> q, boolean debug){

    this.debug = debug;

    this.name = name;
    this.initial = n;
    this.id = "  "+n+"  ";
    this.game = g;
    this.q = q;

    this.location = new int[2];
  }

  public String getNameChar(){
    return this.name;
  }

  public int[] getLocation(){
    return this.location;
  }

  public String toString(){
    return this.id;
  }

  public void toStringL(){
    System.out.println(this.name + " is at position "+ Arrays.toString(this.location));
  }

  public void setWinner(){
    this.winner = true;
    this.id = " M("+this.initial+") ";
  }

  public void setLocation(int x, int y){
    this.location[0] = x;
    this.location[1] = y;
  }

  public void setCarrot(Carrot c){
    this.carrot = c;
    this.holding = true;
    this.id = " "+this.initial+"(c)";
  }

  public void setLeader(){
    this.leader = true;
  }

  public Carrot kill(){

    if (this.debug) System.out.println(this.name + " is being killed!");

    this.dead = true;
    if (this.hasCarrot()){
      this.holding = false;
      return this.carrot;
    }
    else return null;
  }

  public boolean isDead(){
    return this.dead;
  }

  public boolean isWinner(){
    return this.winner;
  }

  public boolean isLeader(){
    return this.leader;
  }

  public boolean hasCarrot(){
    return this.holding;
  }

  public void run(){

    q.add(this);

    synchronized(this){

      try {
        game.play(this);
      }
      catch (Exception e) {

      }
    }
  }

}
