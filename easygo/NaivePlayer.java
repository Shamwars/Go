import java.util.LinkedList;

/**
   This class models a naive player, which used heuristic evaluation 
   function to decide its move. Note that this is a relatively weak player. 
   It is intelligent enough to beat random player, but it can be very easily
   defeated by Human players.
   @author Software Corner Team, Techkriti 2005, IIT Kanpur.
   @version 1.0
 */
public class NaivePlayer implements Player, Constants{
	/**
	   Given the board array arr, it evaluates how good this board is
	   with respect to the given evaluation function
	   @param arr the board array
	   @param the given player
	   @return the score of this board with respect to the given player
	 */
	private int evaluateBoard(int [][]arr, int player){
		//this represents the net mobility score
		int otherPlayer = (player == WHITE) ? BLACK : WHITE;
		int mobility = 0;
		for(int i = 0; i < SIZE; i++){
			for(int j = 0; j < SIZE; j++){
				if (arr[i][j] == player){
					//more mobility for central positions
					mobility += (10 - 4 * Math.abs(SIZE / 2 - i));
					mobility += (10 - 4 * Math.abs(SIZE / 2 - j));
					//now look at the adjacent positions
					if (i != 0 && arr[i - 1][j] == otherPlayer) mobility -= 3;
					if (j != 0 && arr[i][j - 1] == otherPlayer) mobility -= 3;
					if (i != SIZE - 1 && arr[i + 1][j] == otherPlayer) mobility -= 3;
					if (j != SIZE - 1 && arr[i][j + 1] == otherPlayer) mobility -= 3;
				}
				else if(arr[i][j] == otherPlayer){
					if (i != 0 && arr[i - 1][j] == player) mobility += 5;
					if (j != 0 && arr[i][j - 1] == player) mobility += 5;
					if (i != SIZE - 1 && arr[i + 1][j] == player) mobility += 5;
					if (j != SIZE - 1 && arr[i][j + 1] == player) mobility += 5;
				}
			}
		}
		return mobility;
	}

	/**Given the board b, make a valid move for 
	   this board situation
	   @param b the board for which a move has to be made
	*/
	public Move getMove(Board b){
		//get all the possible valid moves, check them one by one
		LinkedList l = b.generatePossibleMoves();
		//get the board array
		int [][]board = b.getBoardArray();
		int numMoves = l.size();
		int player = b.getCurrentPlayer();
		int maxScore = -1000;
		Move bestMove = null;
		for(int i = 0; i < numMoves; i++){
			Board temp = new Board(b);
			Move m = (Move) l.get(i);
			temp.makeMove(m.xPos, m.yPos);

			//if there is any immediate win move
			//then return that move immediately
			if(temp.isPlayerWin(player)) return m;
			int score = evaluateBoard(temp.getBoardArray(), player);
			if (score > maxScore){
				maxScore = score;
				bestMove = m;
			}
		}
		return bestMove;
	}
}
