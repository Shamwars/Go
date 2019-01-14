/**
   An interface which defines how a player is to be implemented.
   Any player program MUST implemented this interface. What you 
   have to do is just implement an instance of this class. Given a 
   board, the function getMove should return a valid move. The 
   different functions in Board class can be used for help, though
   you are free to do anything with your personal copy of Board.
   @author Software Corner Team, Techkriti 2005, IIT Kanpur.
   @version 1.0
 */
public interface Player extends Constants{
	/**Given the board b, make a valid move for this board situation. You
	   are allowed to do anything with your  <b> personal copy </b> of your
	   board. This function (that you will implement) should return
	   a valid move.
	*/
	public Move getMove(Board b);
}
