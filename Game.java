/**
 *
 * Class that will track the game movements
 * Creates all necessary objects for the game
 * Will hold 2d array and make movements
 *
 */

public class Game {

  public synchronized void play(String n, int i){

    System.out.println(n+" is in the play method");
    try
    {
      for (int j = 0; j < 5; j++){
      Thread.sleep(500);
      System.out.println(n + " says hello "+ i++ +" \n");
    }
    catch (Exception e)
    {
        System.out.println("Thread  interrupted.");
    }
    finally {

    }

  }
}
