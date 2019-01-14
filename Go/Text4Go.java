/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class Players implements Player{
	BufferedReader console;
	
	Players(){
		console = new BufferedReader(new InputStreamReader(System.in));
	}
	
	public Move getMoves(Board b){
		System.out.println("Enter the x position: ");
		String str = "";
		int x = 0, y = 0;
		try{
			str = console.readLine();
			x = Integer.parseInt(str);
		}
		catch(Exception e){
		}
		System.out.println("Enter the y position: ");
		
		try{
			str = console.readLine();
			y = Integer.parseInt(str);
		}catch(Exception e){
		}
		Move m = new Move(x, y);
		return m;
	}

	@Override
	public Move getMove(Board b) {
		// TODO Auto-generated method stub
		return null;
	}
}

// This is a class which implements the Player interface, and it takes the moves from the user from standard input
// An example of how to implement the Player interface
public class Text4Go implements GameSetup{
	Board myBoard;
	Player player1, player2;
	int winner;
	
	public Text4Go(Player p1, Player p2){
		player1 = p1;
		player2 = p2;
		winner = None;
		myBoard = new Board();
	}
	
	public void animateGame(){
		Move moves = null;
		while(true){
			while(true){
				moves = player1.getMove(new Board());
				if(myBoard.toMove(moves.xLR, moves.yUD)) break;
				System.out.println("Illegal Move by Player 1 at " + moves.xLR + "" + moves.yUD);
			}
			
			System.out.println("Player 1 moved at " + moves.xLR + "" + moves.yUD);
					
			if (myBoard.blackWin()){
				System.out.println("Player 1 wins the game !");
				winner = Black;
				break;
			}
			
			while(true){
				moves = player2.getMove(new Board());
				if(myBoard.toMove(moves.xLR, moves.yUD)) break;
				System.out.println("Illegal Move by player 2 at "+moves.xLR + " " + moves.yUD);
			}
			
			System.out.println("Player 2 moved at " + moves.xLR + ", " + moves.yUD);
		   		    
		    if (myBoard.whiteWin()){
		    	System.out.println("Player 2 wins the game !");
				winner = White;
				break;
		    }
		   
		}
	}
			
	    // Returns the winner of the game
	    public int getResult(){
		return winner;
	    }
	    
	    public static void main(String args[]){
	    	Player p1 = null;
	    	Player p2 = null;
	    	System.out.println("Player 1 vs Player 2");
	    	
	    	Text4Go t = new Text4Go(p1, p2);
	    	t.animateGame();
	    }
}
