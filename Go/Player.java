/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;

// An interface which defines how a player is to be implemented.
public interface Player extends GameSetup{
		
	public Move getMove(Board b);
}
