/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;

// The class that encapsulates a move of the game.
public class Move {
	// The x and y coordinate of the move
	int xLR;
	int yUD;
	
	public Move(){
		// Default constructor for this class initialises both the coordinates to 0
		xLR = 0;
		yUD = 0;
	}
	
	public Move(int x, int y){
		// Constructor for this class
		xLR = x;
		yUD = y;

	}

}
