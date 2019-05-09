import java.util.*;

/**
 *
 * Mountain object - must be holding a carrot to land on top of this
 * First character to land on it wins
 * Will move randomly every 3 rounds of the game
 *
 * @param climbed Boolean variable that tracks if it has been climbed - Signals a winner
 * @param winner String name of character on top of the mountain
 * @param location location of mountain object
 */


public class Mountain {

  private boolean climbed = false;
  private Character winner;
  private int[] location = new int[2];

  public Mountain(){

  }

  public String toString(){
    return "  M  ";
  }

  public Character getWinner(){
    return this.winner;
  }

  public int[] getLocation(){
    return this.location;
  }

  public void setWinner(Character c){
    this.winner = c;
    this.climbed = true;
  }

  public void setLocation(int x, int y){
    this.location[0] = x;
    this.location[1] = y;
  }

  public boolean isClimbed(){
    return this.climbed;
  }

}
