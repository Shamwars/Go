/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;

import java.util.Scanner;

// This class is used for simulating a game between two Players
public class MyGo implements GameSetup {
	
	private Stack leftStack;
	private Stack rightStack;
	private Stack upStack;
	private Stack downStack;
	private Board board;
	private Player Player;
	private int winner;
	
	
	public MyGo() {
		leftStack = new Stack(9);
		rightStack = new Stack(9);
		upStack = new Stack(9);
		downStack = new Stack(9);
		leftStack.push(9);
		leftStack.push(8);
		leftStack.push(7);
		leftStack.push(6);
		leftStack.push(5);
		leftStack.push(4);
		leftStack.push(3);
		leftStack.push(2);
		leftStack.push(1);
		
	}
	
	public static void main(String[]args){
		Player p1 = null, p2 = null;
		System.out.println("Player 1 vs Player 2");
	}
}
