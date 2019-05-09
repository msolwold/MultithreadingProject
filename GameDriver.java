/**
 * Will hold outputs that introduce the game and will start the game
 *
 * @param debug boolean debugging option that will print out more detailed moves/decisionss
 *
 */


public class GameDriver{

  public static void main(String[] args){

    Game g1 = new Game();

    Character marvin = new Character("marvin", "m", g1);
    Character bugs = new Character("bugs", "b", g1);
    Character taz = new Character("taz", "d", g1);
    Character tweety = new Character("tweety", "t", g1);

    marvin.start();
    bugs.start();
    taz.start();
    tweety.start();

    try {
      marvin.join();
      bugs.join();
      taz.join();
      tweety.join();
    }
    catch (Exception e) {
      System.out.println("Interrupted");
    }


  }

}
