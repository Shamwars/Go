/** This class encapsulates a move of the game.
	@author Software Corner Team, Techkriti 2005, IIT Kanpur.
	@version 1.0
 */
public class Move{
	/**The x coordinate of the move*/
	public int xPos;
	
	/**The y coordinate of the move*/
	public int yPos;

	/**Default constructor for this class
	   initializes both the coordinates to 0
	*/
	public Move(){
		xPos = 0;
		yPos = 0;
	}

	/**Constructor for this class*/
	public Move(int x, int y){
		xPos = x;
		yPos = y;
	}
}
