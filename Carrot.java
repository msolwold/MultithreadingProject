/**
 *
 * Carrot object that is needed to be held in order to win the game
 * There will be at most 2 carrots
 *
 * Track if the carrot has been picked up
 * Track who is holding the carrot
 *
 * @param id id of the Carrot
 * @param held Boolean if the carrot is picked up
 * @param holder String name of character that is holding the carrot
 * @param location location of carrot object
 */

public class Carrot {

  private int id;
  private Character holder;
  private boolean held;
  private int[] location = new int[2];

  public Carrot(int id){
    this.id = id;
  }

  public String toString(){
    return "  c  ";
  }

  public Character getHolder(){
    return this.holder;
  }

  public int[] getLocation(){
    return this.location;
  }

  public void setHolder(Character c){
    this.holder = c;
    this.held = true;
  }

  public void setLocation(int x, int y){
    this.location[0] = x;
    this.location[1] = y;
  }

}
