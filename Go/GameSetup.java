/**
 * Implement the game Go
 * @author Yassin Ting
 * @student ID 2014359
 * @version 1.0
 *
*/

package GoGame;

//Interface that includes setup for use in subsequent classes
//Size of the board which is 9 x 9
//Black player
//White player
//None player at the board
public interface GameSetup {
	public int Size = 9;
	public int Black = 1;
	public int White = 2;
	public int None = 0;
}
