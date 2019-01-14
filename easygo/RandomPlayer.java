import java.util.LinkedList;

/**
   This is a player which makes its move randomly - it randomly selects one amongst the several 
   legal moves available.
   @author Software Corner Team, Techkriti 2005, IIT Kanpur.
   @version 1.0
 */
public class RandomPlayer implements Player, Constants{
	/**
	   Given a board, generate a valid move for it.
	   @param b The board for which the move has to be made
	   @return a valid move, chosen randomly amongst the several valid moves available.
	 */
	public Move getMove(Board b){
		LinkedList possibleMoves = b.generatePossibleMoves();
		int numMoves = possibleMoves.size();
		int move = (int)(numMoves * Math.random());
		return (Move)possibleMoves.get(move);
	}
}
