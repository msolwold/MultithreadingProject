import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 *
 * Class that will track the game movements
 * Creates all necessary objects for the game
 * Will hold 2d array and make movements
 *
 */

public class Game {

  private boolean debug = false;

  private ConcurrentLinkedQueue<Character> q;
  private ReentrantLock lock = new ReentrantLock();
  private boolean leaderChange = false;
  private boolean mountainMoved = false;

  private boolean winner = false;
  private Character winningPlayer;

  private int round = 1;

  private Board board;


  public Game(ConcurrentLinkedQueue<Character> q, boolean debug){
    this.q = q;
    this.debug = debug;
  }

  public void setBoard(Board board){
    this.board = board;
  }

  public synchronized void play(Character c){

    if (debug) System.out.println("------- Starting Game -------");

    this.q.peek().setLeader();

    while (!winner){

      if (debug) System.out.println("It is round number: " + this.round);

      if (debug) System.out.println("Print out board: \n\n");
      System.out.println(this.board.printBoard());

      Character currChar = this.q.poll();

      if (currChar.isLeader() && !this.leaderChange){
        if (this.mountainMoved) this.mountainMoved = false;
        this.round++;
      }
      
      this.leaderChange = false;

      if (this.round % 3 == 0 && !this.mountainMoved){
        this.board.moveMountain();
        this.mountainMoved = true;
      }

      System.out.println("It is " + currChar.getNameChar() + "'s turn to move");
      if (debug) currChar.toStringL();

      try
      {
        Thread.sleep(1000);

        this.winner = this.board.moveCharacter(currChar);

        if (this.winner){
          if (debug) System.out.println("We have a winner (Game Class Line ~65)"+currChar.toString());
           this.winningPlayer = currChar;
           while (!this.q.isEmpty()){
             this.q.poll().interrupt();
           }
        }

        if (!currChar.isDead() && !this.winner) this.q.add(currChar);
        else{
          if (currChar.isLeader()) {
            this.q.peek().setLeader();
            this.leaderChange = true;
          }
        }
      }
      catch (Exception e)
      {
        System.out.println("Thread Interrupted: "+e);
      }
    }
  }
}
