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
  private boolean held = false;
  private Character holder;
  private int[] location = new int[2];

  /**
   * Constructor
   * @param id int value the object will use as an ID
   */
  public Carrot(int id) {
    this.id = id;
  }

  /**
   * prints initial
   * @return String single character representing object
   */
  public String toString() {
    return "  c  ";
  }

  /**
   * Method to check if carrot is held
   * @return boolean true if being held
   */
  public boolean isHeld(){
    return this.held;
  }

  /**
   * returns ID of the object
   * @return int ID of object
   */
  public int getID(){
    return this.id;
  }

  /**
   * returns the character that is currently holding the carrot
   * @return Character character object that is holding the carrot
   */
  public Character getHolder() {
    return this.holder;
  }

  /**
   * Method that returns the location of the carrot
   * If the carrot is held by a character then it will return that characters location
   * @return int[] coordinates of carrot
   */
  public int[] getLocation() {
    if (this.held){
      return this.holder.getLocation();
    }
    return this.location;
  }

  /**
   * Used when carrot is being picked up
   * @param c Character character that is picking the carrot up
   */
  public void setHolder(Character c) {
    this.holder = c;
    this.held = true;
  }

  /**
   * Set the coordinates of the carrot
   * @param x int x coordinate
   * @param y int y coordinate
   */
  public void setLocation(int x, int y) {
    this.location[0] = x;
    this.location[1] = y;
  }

}
