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
  ReentrantLock firstRoundLock = new ReentrantLock();
  ReentrantLock secondRoundLock = new ReentrantLock();

  // boolean for tracking winner
  private boolean winner = false;

  // Tracks rounds for mountain movement
  // Will force mountain to move every 3 rounds of gameplay
  private int mountainTrigger = 15;
  private int roundCounter = 1;
  private int activeCharacters = 4;
  private int winners = 0;

  // board and character containers
  private Board board;
  private VisualGameBoard vb;
  private Character[] characters;
  private Character[] winningCharacters = new Character[2];

  private Character sam;


  // used to maintain smooth gameplay
  private int time = 4000;

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

  public void givegBoard(VisualGameBoard vb){
    this.vb = vb;
  }

  /**
   * Called if a character is killed.
   * Decrements time to maintain smooth gameplay
   * lowers bound for rounds to maintain mountain movements
   */
  public void characterKilled() {
    this.time -= 1000;
    this.mountainTrigger -= 4;
    this.activeCharacters -= 1;

    if (this.activeCharacters == 2) endRound();
    if (this.activeCharacters == 1) endGame();
  }

  /**
   * Called if a winner is found. Forces game to end by killing threads
   */
  private void winnerFound(Character c) {

    System.out.println(c.getNameChar() + " is one of our winners!");

    this.board.printBoard();

    if (winners > 0){
      this.winningCharacters[this.winners] = c;
      for (int i = 0; i < this.characters.length; i++) {
        if (!characters[i].isDead() && !characters[i].isWinner())
          characters[i].endGame();
      }
      setupSecondRound();
    }
    else {
      this.time -= 1000;
      this.winningCharacters[this.winners] = c;
      this.winners++;
    }
  }

  private void endGame() {
    System.out.println("Marvin Killed Everyone!");
    for (int i = 0; i < this.characters.length; i++) {
      if (!characters[i].isDead())
        characters[i].endGame();
    }
  }

  public void endRound(){

    if (this.activeCharacters > 2){
      for (int i = 0, j = 0; i < this.characters.length; i++){
        if (!this.characters[i].hasCarrot()){
          this.characters[i].endGame();
        }
        else {
          this.characters[i].setWinner();
          this.characters[i].interrupt();
          this.winningCharacters[j++] = this.characters[i];
        }
      }
    }
    else {
        for (int i = 0, j = 0; i < this.characters.length; i++){
          if (!this.characters[i].isDead()) {
            this.characters[i].setWinner();
            this.characters[i].interrupt();
            this.winningCharacters[j++] = this.characters[i];
          }
        }
    }
    setupSecondRound();
  }

  private void setupSecondRound(){

    System.out.println("=============== STARTING SECOND ROUND ===================");

    board.createSecondRoundBoard(this.winningCharacters[0], this.winningCharacters[1]);
    this.sam = new Character(5, "sam", "s", this);
    this.roundCounter = 1;
    this.winner = false;
    this.winningCharacters[0].interrupt();
    this.winningCharacters[1].interrupt();
    this.sam.start();
  }

  private void endRoundTwo(Character c){
    System.out.println("The Winner is " + c.getNameChar());
    for (int i = 0; i < this.winningCharacters.length; i++) {
      winningCharacters[i].endGame();
    }
    this.sam.endGame();
  }

  /**
   * Play method that runs the game.
   * Uses a Reentrant lock to ensure only one thread enters at a time
   * @param c Character character object that is requesting to play
   */
  public void playFirstRound(Character c) {


    //Attempts to lock the thread
    boolean tryLock = this.firstRoundLock.tryLock();

    // If it succeeds it will be allowed in to get properly locked and ran
    if (tryLock && !c.isDead()) {

      try {

        // Lock the thread
        this.firstRoundLock.lock();

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
            winnerFound(c);
        }

        // Catches exception
        catch (InterruptedException e) {
          if (this.debug)
            System.out.println("Thread interruption: " + e);
        }
      }
      finally {
        this.firstRoundLock.unlock();
        this.firstRoundLock.unlock();

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

  public void playSecondRound(Character c) {
    //Attempts to lock the thread
    boolean tryLock = this.secondRoundLock.tryLock();

    if (tryLock){

      try {
        this.secondRoundLock.lock();

        Thread.sleep(500);

        if (c.getNameChar() == "sam"){
          if (roundCounter % 2 == 0) {

            if (c.lockOn()){
               winningCharacters[0].setFrozen();
               vb.freezeChar(winningCharacters[0].getSecondRoundID(), winningCharacters[0].getSecondRoundIndex());
             }
            if (c.lockOn()) {
              winningCharacters[1].setFrozen();
              vb.freezeChar(winningCharacters[1].getSecondRoundID(), winningCharacters[1].getSecondRoundIndex());
            }
          }
          this.roundCounter++;
        }
        else {
          try {
            Thread.sleep(50);

            if (c.frozen()) {
              System.out.println(c.getNameChar() + " is Frozen and can't move!");
              c.setFrozen();
              vb.unFreezeChar(c.getSecondRoundID(), c.getSecondRoundIndex());
            }

            else {

              System.out.println(c.toString() + " is up!");

              this.winner = this.board.moveCharacterRoundTwo(c);

              if (this.winner){
                System.out.println(c.getNameChar() + " is the winner!");
                endRoundTwo(c);
              }
            }

          }
          catch (InterruptedException e) {
              System.out.println("Thread interruption: " + e);
          }
        }
      }
      catch (InterruptedException e) {
          System.out.println("Thread interruption: " + e);
      }
      finally {

        this.secondRoundLock.unlock();
        this.secondRoundLock.unlock();

        try {
          Thread.sleep(1500);
        } catch (InterruptedException e) {
            System.out.println("Thread Interruption: " + e);
        }
      }
    }
  }
}
