/**
 * Create thread character will occupy
 * Track if they are holding carrot
 * Track if the character has been killed by marvin
 *
 * @param thread Thread thread for the character object
 * @param name String name of character {"bugs", "marvin", "tweety", "taz"}
 * @param num int Number associated with character
 * @param dead Boolean if the character is alive
 * @param holding Boolean if the character is holding the carrot
 */

public class Character extends Thread {

  Game game;

  private String name;
  private String id;
  boolean dead = false;
  boolean holding = false;
  private int i = 1;

  public Character(String name, String n, Game g){
    this.name = name;
    this.id = n;

    game = g;
  }

  public void run(){

    game.play(this.id, this.i++);

  }

}
