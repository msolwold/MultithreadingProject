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

  private ConcurrentLinkedQueue<Character> q;
  private ReentrantLock lock = new ReentrantLock();
  private boolean leaderChange = false;

  private boolean winner = false;
  private Character winningPlayer;

  private int round = 1;

  private Board board;


  public Game(ConcurrentLinkedQueue<Character> q){
    this.q = q;
  }

  public void setBoard(Board board){
    this.board = board;
  }

  public void play(Character c){

    this.q.peek().setLeader();

    while (!winner){

      System.out.println(this.board.printBoard());

      Character currChar = this.q.poll();

      if (currChar.isLeader && !this.leaderChange) this.rounds++;
      this.leaderChange = false;

      if (this.rounds / 3 == 0) this.board.moveCarrots();

      System.out.println("It is " + currChar.getNameChar() + "'s turn to move'");

      try
      {
        Thread.sleep(1000);

        this.winner = this.board.moveCharacter(currChar);

        if (this.winner){
           this.winningPlayer = currChar;
           while (!this.q.isEmpty()){
             this.q.poll.interrupt();
           }
        }

        if (!currChar.isDead() && !this.winner) this.q.add(currChar);
        else{
          if (currChar.isLeader) {
            this.q.peek().setLeader;
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
