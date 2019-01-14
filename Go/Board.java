package GoGame;

//Class to models a board of the game
public class Board implements GameSetup {
	// The array needed to create the board
		int board[][];
		int move;
		int curPlayer;
		int colour;
		// These are used while checking the validity of a move		
		static int EMPTY = 0;
		static int SAFE = 0;
		static int CHECKING = 0;
		
		public Board() {
			// Size for the board set at 9x9 array
			board = new int[Size][Size];
			for(int i = 0; i < Size; i++)
				for(int j = 0; j < Size; j++)
					board[i][j] = None;
			curPlayer = Black;
			move = 0;
		}
		
		public Board(Board myBoard) {
			// TODO Auto-generated constructor stub
		}

		// Create a copy of existing board
		public Board createCopy() {
			Board otherBoard = new Board();
			
			for (int i = 0; i < Size; i++) {
				for (int j = 0; j < Size; j++) {
					otherBoard.board[i][j] = this.board[i][j];
				}
			}	
			return otherBoard;
		}
		// This method checks if the move being taken is legal or not
		public boolean isLegal(int[][] board, int xLR, int yUD, int colour){
			if(xLR < 0 || yUD < 0 || xLR >= Size || yUD >= Size || board[xLR][yUD] != None) 
				return false;
			board[xLR][yUD] = curPlayer;
			if(isPlayerWin(curPlayer)){
				board[xLR][yUD] = None;
				return true;
			}
			int temp[][] = new int[Size][Size];
			for(int i = 0; i < Size; i++)
				for(int j = 0; j < Size; j++)
					temp[i][j] = EMPTY;
			boolean legal = isSafe(xLR, yUD, temp, curPlayer);
			board[xLR][yUD] = None;
			return legal;
		}
		
		private boolean isPlayerWin(int player) {
			// TODO Auto-generated method stub
			return playerWin(player);
		}

		private boolean playerWin(int player) {
			// TODO Auto-generated method stub
			int opponent = (player == Black)? Black : White;
			int[][] temp = new int[Size][Size];
			for(int i = 0; i < Size; i++)
				for(int j = 0; j < Size; j++)
					temp[i][j] = EMPTY;
			for(int i = 0; i < Size; i++){
				for(int j = 0; j < Size; j++){
					if(board[i][j] != opponent) continue;
					if(temp[i][j] == SAFE) continue;
					boolean safe = isSafe(i, j, temp, opponent);
					if(!safe) return true;
				}
			}
			return false;
		}
		
		public boolean blackWin(){
			return playerWin(Black);
		}
		
		public boolean whiteWin(){
			return playerWin(White);
		}
        // Checks if the piece at coordinates (x,y) is safe or not
		private boolean isSafe(int xLR, int yUD, int[][] temp, int player) {
			for(int i = xLR - 1; i <= xLR +1; i++){
				for(int j = yUD - 1; j <= yUD +1; j++){
					if(i - xLR == j - yUD || i - xLR == yUD - j) continue;
					if(board[i][j] == None){
						temp[xLR][yUD] = SAFE;
						return true;
					}
					else if(board[i][j] == player && temp[i][j] == SAFE){
						temp[xLR][yUD] = SAFE;
						return true;
					}
				}
			}
			return false;
		}
		// Check take a board state and a board coordinate and return whether that board position is captured or not
		private static boolean isCaptured(int[][] board, int xLR, int yUD) {
		    boolean result = !isNotCaptured(board, xLR, yUD);
		    emptyBoard(board);
		    return result;
		}

		private static void emptyBoard(int[][] board) {
			// TODO Auto-generated method stub
			for (int i = 0; i < Size; i++) {
		        for (int j = 0; j < Size; j++) {
		            if (board[i][j] == CHECKING)
		                board[i][j] = Black;
		        }
		    }
		}

		private static boolean isNotCaptured(int[][] board, int xLR, int yUD) {
		    int value = board[xLR][yUD];
		    if (!(value == Black || value == CHECKING))
		        return true;

		    int top = yUD < Size - 1 ? board[xLR][yUD + 1] : CHECKING;
		    int bottom = yUD > 0 - 1 ? board[xLR][yUD - 1] : CHECKING;
		    int left = xLR > 0 ? board[xLR - 1][yUD] : CHECKING;
		    int right = xLR < Size - 1 ? board[xLR + 1][yUD] : CHECKING;

		    if (top == EMPTY || right == EMPTY || left == EMPTY || bottom == EMPTY)
		        return true;

		    board[xLR][yUD] = CHECKING;

		    return (top == Black && isNotCaptured(board, xLR, yUD + 1))
		            || (bottom == Black && isNotCaptured(board, xLR, yUD - 1))
		            || (left == Black && isNotCaptured(board, xLR - 1, yUD))
		            || (right == Black && isNotCaptured(board, xLR + 1, yUD));
		}

		public boolean toMove(int xLR, int yUD){
			if(isLegal(board, xLR,yUD, yUD)){
				board[xLR][yUD] = curPlayer;
				curPlayer = (curPlayer == White) ? Black : White ;
				move++;
				return true;
			}
			else return false;
		}

}
