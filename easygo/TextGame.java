import java.io.BufferedReader;
import java.io.InputStreamReader;

/**This is a class which implements the Player interface, and it takes the moves from the user from standard input
   <p>An example of how to implement the Player interface
   @author Software Corner Team, Techkriti 2005.
   @version 1.0
*/
class HumanPlayer implements Player{
    private BufferedReader console;

    /**Constructor of the class*/
    HumanPlayer(){
	console = new BufferedReader(new InputStreamReader(System.in));
    }

    /**Reads a move from the standard input, and return it.
       @param b the board corresponding to which the move has to be made
    */
    public Move getMove(Board b){
	System.out.print("Enter the x position : ");
	String str = "";
	int x = 0, y = 0;
	try{
	    str = console.readLine();
	    x = Integer.parseInt(str);

	}catch(Exception e){}

	System.out.print("Enter the y position : ");

	try{
	    str = console.readLine();
	    y = Integer.parseInt(str);
	}catch(Exception e){}

	Move m = new Move(x, y);
	return m;
    }
}

/**This class implements a console based game for go. This can also be used
   to test a player program.
   @author Software Corner Team, Techkriti 2005, IIT Kanpur
   @version 1.0
*/
public class TextGame implements Constants{
    private Board goBoard;
    private Player player1, player2;
    private int winner;

    /**Constructor for the class
       @param p1 the first player
       @param p2 the first player
    */
    public TextGame(Player p1, Player p2){
	player1 = p1;
	player2 = p2;
	goBoard = new Board();
	winner = NONE;
    }

    /**Simulates the game between the two players*/
    public void simulateGame(){
	Move move = null;
	while(true){
	    while(true){
		move = player1.getMove(new Board(goBoard));
		if (goBoard.makeMove(move.xPos, move.yPos)) break;
		System.out.println("Illegal Move by player 1 at "+move.xPos + " " + move.yPos);
	    }

	    System.out.println("Player 1 moved at " + move.xPos + ", " + move.yPos+" on move "+goBoard.getMoves());
	    printBoard();

	    if (goBoard.blackWin()){
		System.out.println("Player 1 wins the game !");
		winner = BLACK;
		break;
	    }

	    while(true){
		move = player2.getMove(new Board(goBoard));
		if (goBoard.makeMove(move.xPos, move.yPos)) break;
		System.out.println("Illegal Move by player 2 at "+move.xPos + " " + move.yPos);
	    }

	    System.out.println("Player 2 moved at " + move.xPos + ", " + move.yPos+" oon move "+ goBoard.getMoves());
	    printBoard();

	    if (goBoard.whiteWin()){
		System.out.println("Player 2 wins the game !");
		winner = WHITE;
		break;
	    }

	    if (goBoard.gameDraw()){
		System.out.println("The game ends in a draw !");
		break;
	    }
	}
    }

    /**Prints the board in a neat and formatted manner*/
    private void printBoard(){
	System.out.println("");
	int [][]board = goBoard.getBoardArray();
	for(int j = 0; j < SIZE; j++){
	    for (int i = 0; i < SIZE; i++){
		if (board[i][j] == WHITE) System.out.print("W ");
		else if (board[i][j] == BLACK) System.out.print("B ");
		else System.out.print(". ");
	    }
	    System.out.print("\n");
	}
	System.out.println("");
    }

    /**Returns the winner of the game*/
    public int getResult(){
	return winner;
    }

    /**
	   This can be used for playing/simulating a text based game between two programmed players.
	   The players to be played can be specified as arguments to the program - for example, if your
	   player is the class MyPlayer, then you can play against it by typing "java MyPlayer HumanPlayer"
	   on the command prompt.
	   @param args the player class names. If one argument is missing, then HumanPlayer is the default second player.
	   If both the arguments are missing, then HumanPlayer and RandomPlayer are used.
	 */
    public static void main(String args[]){
	Player p1 = null, p2 = null;
	if(args.length == 0){
	    /*No arguments given, use default players */
	    System.out.println("No player name specified - using RandomPlayer and HumanPlayer");
	    p1 = new RandomPlayer();
	    p2 = new HumanPlayer();
	}
	else if(args.length == 1){
	    /*Only one argument specified*/
	    Class cl = null;;
		try{
			cl = Class.forName(args[0]);
			p1 = (Player)cl.newInstance();
	    }catch(Exception e){
			System.out.println("Error loading class - "+e);
			System.exit(-1);
	    }
	    System.out.println("Second player not specified - using HumanPlayer");
	    p2 = new HumanPlayer();
	}
	else{
	    Class cl1 = null, cl2 = null;
	    try{
			cl1 = Class.forName(args[0]);
			p1 = (Player)cl1.newInstance();
	    }catch(Exception e){
			System.out.println("Error loading class - "+e);
			System.exit(-1);
	    }
	    try{
			cl2 = Class.forName(args[1]);
			p2 = (Player)cl2.newInstance();
	    }catch(Exception e){
			System.out.println("Error loading class - "+e);
			System.exit(-1);
	    }
	}

	TextGame g = new TextGame(p1, p2);
	g.simulateGame();
    }
}
