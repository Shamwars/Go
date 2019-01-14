import java.util.LinkedList;

/**This class models a board of Go. 
   The board is represented a a two dimensional array. Each location on
   the board can be occupied by either NONE, BLACK or WHITE. 

   A large number of functions are provided in this class, which must be
   suffiecient to program your player. For programming your player, you
   have to implement an instance of the interface Player. 

@author Software Corner team, Techkriti -2005, IIT Kanpur
@version 1.0
*/
public class Board implements Constants{
	/**This is the array corresponding to the board*/
	protected int board[][];

	/**The number of moves so far in the game*/
	protected int moves;

	/**The current player of the game*/
	protected int currentPlayer;

	/**All these constants are used while checking the validity of a move*/
	protected static final int UNCHECKED = 0;
	protected static final int SAFE = 1;
	protected static final int UNSAFE = 2;
	protected static final int CHECKING = 3;
	
	/**This constructor is for initializing the board*/
	public Board(){
		board = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				board[i][j] = NONE;
		currentPlayer = BLACK;
		moves = 0;
	}

	/**A copy constructor. It makes a copy of the given board.
	 @param b the Board whose copy is to be made
	*/
	public Board(Board b){
		this.board = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				this.board[i][j] = b.board[i][j];
		this.currentPlayer = b.currentPlayer;
		this.moves = b.moves;
	}

	/**Initializes the board with the given array and the number of Moves
	   @param arr the array which represents the board
	   @param numMoves the number of moves
	*/
	public Board(int arr[][], int numMoves){
		board = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				board[i][j] = arr[i][j];
		moves = numMoves;
		currentPlayer = (moves %2 == 0) ? BLACK : WHITE; 
	}

	/**This method checks if the move being taken is valid or not
	   @param x The x coordinate of the move
	   @param y The y coordinate of the move
	   @return true if the move to be made at coordinate x and y is valid, false otherwise
	 */
	protected boolean isValid(int x, int y){
		if (x < 0 || y < 0 || x >= SIZE || y >= SIZE || board[x][y] != NONE) return false;
		board[x][y] = currentPlayer;
		if (isPlayerWin(currentPlayer)){
			board[x][y] = NONE;
			return true;
		}
		int temp[][] = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				temp[i][j] = UNCHECKED;
		boolean valid = isSafe(x, y, temp, currentPlayer);
		board[x][y] = NONE;
		return valid;
	}

	/**Checks if the piece at coordinates (x,y) is safe or not
	   @param x the x coordinate of the move
	   @param y the y coordinate of the move
	   @param player The player for whom the validity of this move is being checked
	   @return true if this move is safe or not
	 */
	protected boolean isSafe(int x, int y, int [][]temp, int player){
		for(int i = x - 1; i <= x + 1; i++){
			for(int j = y - 1; j <= y + 1; j++){
				if (i - x == j - y || i - x == y - j) continue;
				if (i < 0 || j < 0 || i >= SIZE || j >= SIZE) continue;
				if (board[i][j] == NONE){
					temp[x][y] = SAFE;
					return true;
				}
				else if (board[i][j] == player && temp[i][j] == SAFE){
					temp[x][y] = SAFE;
					return true;
				}
				else if (board[i][j] == player && temp[i][j] == UNCHECKED){
					temp[i][j] = CHECKING;
					boolean safe = isSafe(i, j, temp, player);
					if (safe){
						temp[x][y] = SAFE;
						return true;
					}
				}
			}
		}
		return false;
	}

	/**Returns the player who will move next in the game
	   @return The player to move
	 */
	public int getCurrentPlayer(){
		return currentPlayer;
	}

	/**Returns the player who is not the current player
	 @return the player who is not the current player
	*/
	public int otherPlayer(){
		return (currentPlayer == WHITE) ? BLACK : WHITE;
	}

	/**Returns the current number of moves in this game
	 @return the current number of moves in this game
	*/
	public int getMoves(){
		return moves;
	}

	/**Checks if this game has ended in a draw or not
	   @return true if the game has ended in draw, false otherwise
	 */
	public boolean gameDraw(){
		return (moves == SIZE*SIZE-1);
	}

	/**Checks if a player has won in this game or not
	   @param player The player for whom the victory is to be checked
	   @return true if player is victorius, false otherwise
	*/
	protected boolean playerWin(int player){
		int opponent = (player == BLACK) ? WHITE : BLACK ;
		int [][]temp = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				temp[i][j] = UNCHECKED;
		
		for(int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				if (board[i][j] != opponent) continue;
				if(temp[i][j] == SAFE) continue;
				boolean safe = isSafe(i, j, temp, opponent);
				if (!safe) return true;
			}
		}
		
		return false;
	}


	/**Checks whether the player with the black pieces has won or not
	   @return true if the player with black pieces has won in the game, false otherwise
	 */	
	public boolean blackWin(){
		return playerWin(BLACK);
	}
	
	/**Checks whether the player with the white pieces has won or not
	   @return true if the player with the white pieces has won the game, false otherwise
	 */
	public boolean whiteWin(){
		return playerWin(WHITE);
	}

	/**Returns true if the game has been won by the player indicated in the argument. Same as playerWin() 
	 function
	*/
	public boolean isPlayerWin(int player){
		return playerWin(player);
	}

	/**Places a piece of the current player at the indicated position of the board, if the move is valid
	   @param x the x - coordinate of the desired move
	   @param y the y - coordinate of the desired move
	   @return true if the move is valid, false otherwise
	*/
	public boolean makeMove(int x, int y){
		if (isValid(x,y)){
			board[x][y] = currentPlayer;
			currentPlayer = (currentPlayer == WHITE) ? BLACK:WHITE;
			moves ++;
			return true;
		}
		else return false;
	}

	/**This method returns a copy of the array corresponding to the board
	   @return a copy of the board array
	 */
	public int[][] getBoardArray(){
		int temp[][] = new int[SIZE][SIZE];
		for(int i = 0; i < SIZE; i++)
			for(int j = 0; j < SIZE; j++)
				temp[i][j] = board[i][j];
		return temp;
	}

	/**This method returns a copy of this board. Same as the Board(Board b) constructor*/
	public Board getBoardCopy(){
		return new Board(this);
	}
	
	/**This method generates all possible valid moves for the current player
	   in a linked list. This is a useful function which can be used by a player 
	   program when it is generating a move for the game.
	   @return The list of all possible valid moves in a linked list
	*/
	public LinkedList generatePossibleMoves(){
		LinkedList possibleMoves = new LinkedList();
		for (int i = 0; i < SIZE; i++)
			for (int j = 0; j < SIZE; j++)
				if (isValid(i,j)) possibleMoves.add(new Move(i,j));
		return possibleMoves;
	}
}
