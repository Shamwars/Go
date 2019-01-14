/**This class is used for simulating a game between two Players. It implements a 
   proper protocol for the game, and stores the outcomes.
   <p>
   Note that this class is still not complete. If it so happens that a particular
   player program enters into an infinite loop or recursion - then it will not be terminated, 
   even after its time limit is exceeded. However, it is sifficient for programmers to test thier own 
   players using this class. Rest of the work will be done by the time of the contest.
   @author Software Corner Team, Techkriti 2005, IIT Kanpur.
   @version 1.0
*/
public class Game implements Constants{
	private Board goBoard;
	private Player player1, player2;
	private long player1Time, player2Time;
	private boolean player1Erred, player2Erred;
	private int winner;
	private int numMoves;

	/**This is the time out limit for each player*/
	public static final long TIMEOUT = 60000;

	/**
	   A constructor for this class
	   @param p1
	   @param p2 The two players between which the tournament is to be held.
	 */
	public Game(Player p1, Player p2){
		player1 = p1;
		player2 = p2;
		goBoard = new Board();
		winner = NONE;
		numMoves = 0;
		player1Time = player2Time = 0;
		player1Erred = player2Erred = false;
	}

	/**Simulates the game between the two players*/
	public void simulateGame(){
		Move move = null;
		while(true){
	
			long start = System.currentTimeMillis();
			try{
				move = player1.getMove(new Board(goBoard));
			}catch(Exception e){
				//Error when player tried to make a move
				System.out.println(e);
				player1Erred = true;
				break;
			}
			long stop = System.currentTimeMillis();
			player1Time += (stop - start);

			if(player1Time > TIMEOUT){
				//player 1 timed out
				player1Erred = true;
				break;
			}

			boolean valid = goBoard.makeMove(move.xPos, move.yPos);
			if(!valid){
				//Invalid move by player 2
				System.out.println("Player 1 has made a wrong move !!!");
				player1Erred = true;
				break;
			}
			
			if (goBoard.blackWin()){
				winner = BLACK;
				break;
			}

			start = System.currentTimeMillis();
			try{
				move = player2.getMove(new Board(goBoard));
			}catch(Exception e){
				//Error when player tried to make a move
				System.out.println(e);
				player2Erred = true;
				break;
			}
			stop = System.currentTimeMillis();
			player2Time += (stop - start);
			
			if(player2Time > TIMEOUT){
				//player 2 timed out
				player2Erred = true;
				break;
			}

			valid = goBoard.makeMove(move.xPos, move.yPos);
			if(!valid){
				//Invalid move by player 2
				System.out.println("Player 2 has made a wrong move !!!");
				player2Erred = true;
				break;
			}
			
			if (goBoard.whiteWin()){
				winner = WHITE;
				break;
			}
			numMoves ++;
			if (goBoard.gameDraw()) break;
		}
	}
	
	/**This method is used to get the result of the ame
	  @return WHITE if white wins, BLACK if black wins, NONE if the game ends in a draw,
	  time out or exception thrown by one of the players' programs.
	*/
	public int getWinner(){
		return winner;
	}
	
	/**
	  Get the CPU time taken by player 1
	  @return the CPU time taken by player 1 in milliseconds
	 */
	public long getPlayer1Time(){
		return player1Time;
	}

	/**
	  Get the CPU time taken by player 2
	  @return the CPU time taken by player 2 in milliseconds
	 */
	public long getPlayer2Time(){
		return player2Time;
	}

	/**
	  Returns whether player 1 erred in the game. An error can be any of the
	  following : An exception, an invalid move or exceeding time limit
	  @return true if player 1 erred, false otherwise
	 */
	public boolean hasPlayer1Erred(){
		return player1Erred;
	}

	/**
	  Returns whether player 2 erred in the game. An error can be any of the
	  following : An exception, an invalid move or exceeding time limit
	  @return true if player 2 erred, false otherwise
	 */
	public boolean hasPlayer2Erred(){
		return player2Erred;
	}
	
	/**This can  be used for testing your own player. You can specify your own player
	   by typing the class name on the command prompt. e.g. If you want to test your player against 
	   RandomPlayer, where MyPlayer is your player class, type "java MyPlayer RandomPlayer" on the 
	   command prompt.
	   @param args The arguments - i.e. The player class names. If args are missing, default 
	   arguments will be used.
	 */
	public static void main(String args[]){
		Player p1 = null, p2 = null;
		if(args.length == 0){
			System.out.println("No player specified - using Naive Player and RandomPlayer");
			p1 = new NaivePlayer();
			p2 = new RandomPlayer();
		}
		else if(args.length == 1){
			try{
				Class cl = Class.forName(args[0]);
				p1 = (Player)cl.newInstance();
			}catch(Exception e){
				System.out.println("Error loading class - "+e);
				System.exit(-1);
			}
			System.out.println("No second player specified - using RandomPlayer");
		}
		else{
			try{
				Class cl = Class.forName(args[0]);
				p1 = (Player)cl.newInstance();
			}catch(Exception e){
				System.out.println("Error loading class - "+e);
				System.exit(-1);
			}
			try{
				Class cl = Class.forName(args[1]);
				p2 = (Player)cl.newInstance();
			}catch(Exception e){
				System.out.println("Error loading class - "+e);
				System.exit(-1);
			}
		}

		Game g = new Game(p1, p2);

		g.simulateGame();
		int winner = g.getWinner();
		if (winner == BLACK) System.out.println("Black wins");
		else if (winner == WHITE) System.out.println("White wins");
		else System.out.println("The game ends in a draw");
		System.out.println("\nThe game stats : ");
		System.out.println("No of moves = " + g.numMoves + "\nPlayer 1 time : " + g.player1Time + "\nPlayer 2 time : "+g.player2Time);
	}
}
