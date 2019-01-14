import java.awt.*;
import java.applet.*;
import java.awt.event.*;

/**This class implements an applet based game for the Game of Go.
   The moves are to be made using a mouse.
   <p>
   There are a few bugs in this class. After a game ends, the board does not 
   refresh properly. It is better to restart a game after the end of a game. This 
   should not be a serious impediment for the participants when they test their
   strategies using this class. The bugs will be fixed in the later versions of
   the game.
*/
public class GraphicGame extends Applet implements Constants, MouseListener{
	private int XSTART=100;
	private int YSTART=100;
	private int LENGTH=30;
	private int RADIUS=10;	

	private static final int MACHINE = 1;
	private static final int HUMAN = 2;

	private Board go;
	private int player;
	private int whoMovesNext;
	private boolean gameStarted;
	private boolean reinit=false;	
	private boolean repaint;
	private TextField msg=new TextField(250);
	private Player machinePlayer;
	private Move prevMove = null;
	static GraphicGame game;

	/**Constructor for the class*/
	GraphicGame(){
		init();
		start();
		whoMovesNext = HUMAN;
	}

	/**Initializes the applet*/
	public void init(){
		addMouseListener(this);
		setBackground(new Color(234,215,12));
		setLayout(null);
		add(msg);
	}

	/**Called whenever a new game of go is started*/
	public void start(){
		repaint=true;
		go=new Board();

		/*Default player is RandomPlayer*/
		machinePlayer = new RandomPlayer();

		player=BLACK;
		gameStarted=false;
		msg.setBounds(XSTART,YSTART + SIZE * LENGTH,(SIZE + 1) * LENGTH,LENGTH);	
		msg.setEditable(false);
		msg.setText("Click anywhere to start");
	}
	
	public void setMachinePlayer(Player p){
		machinePlayer = p;
	}
	
	/**This resents the applet window, by calling the start() method*/
	public void reset(){
		start();
		repaint();
	}

	/**Sets whether the window is to be repainted or not - for example, 
	   after a window has lost focus or has been deiconified, 
	   the window has to be repainted*/
	public void setRepaint(boolean b){
		repaint=b;
	}

	/**Redraws the board, together with the pieces*/
	public void paint(Graphics g){
		if(repaint){
			makeNextMove();
			renderBoard(g);
			repaint=false;
		}
		else updateBoard(g);
	}

	/**renders the board and the players' moves*/
	public void updateBoard(Graphics g){
		renderBoard(g);
		drawPlayerMoves(g);
		makeNextMove();
	}
	
	private Move makeMachineMove(){
		return machinePlayer.getMove(new Board(go));
	}

	/**If the machine has to move next, it gets the move by calling appropriate function*/
	private void makeNextMove(){
		if(whoMovesNext==MACHINE && gameStarted){
			whoMovesNext = HUMAN;
			prevMove = makeMachineMove();
			go.makeMove(prevMove.xPos, prevMove.yPos);
			String str;
			if(go.getCurrentPlayer()==BLACK) str="Player 1(Black)";
				else str="Player 2(White)";
			if(go.isPlayerWin(go.otherPlayer())){
				str += " loses the game.";
				gameStarted=false;
				reinit=true;
			}
			else str += "to move";
			msg.setText(str);
			if(!repaint) repaint();
		}
	}

	private void renderBoard(Graphics g){
		for(int i=0;i<SIZE;i ++ ){
			for(int j=0;j<SIZE;j ++){
				g.drawLine(XSTART,YSTART + i * LENGTH,XSTART + (SIZE-1) * LENGTH,YSTART + i * LENGTH);
				g.drawLine(XSTART + i * LENGTH,YSTART,XSTART + i * LENGTH,YSTART + (SIZE-1) * LENGTH);
			}
		}
	}
	
	private void drawPlayerMoves(Graphics g){
		int arr[][] = go.getBoardArray();
		for(int i=0;i<SIZE;i++){
			for(int j=0;j<SIZE;j++){
				if(arr[i][j] == BLACK)
					colorMove(g,Color.black,Color.black,i,j);
				else if(arr[i][j] == WHITE)
					colorMove(g,Color.white,Color.black,i,j);
			}
		}
	}
	
	private void colorMove(Graphics g,Color color1,Color color2,int i,int j){
		g.setColor(color1);
		g.fillOval(XSTART + i * LENGTH-RADIUS,YSTART + j * LENGTH-RADIUS,2 * RADIUS,2 * RADIUS);
		g.setColor(color2);
		g.drawOval(XSTART + i * LENGTH-RADIUS,YSTART + j * LENGTH-RADIUS,2 * RADIUS,2 * RADIUS);
	}
	
	/**Changes the message in the text field*/
	public void revertMessage(){
		if(go.getCurrentPlayer()==BLACK) msg.setText("Player 1(Black) to move");
		else msg.setText("Player 2(White) to move");
	}
	
	/**This method is used to capture the human player's move*/
	public void mouseClicked(MouseEvent me){
		String str="nothing";
		if(whoMovesNext == HUMAN){
			if(!gameStarted) gameStarted=true;
			if(reinit){
				reset();
				reinit=false;
			}
			int x=me.getX();
			int y=me.getY();
			int i=(x-XSTART + RADIUS)/LENGTH;
			int j=(y-YSTART + RADIUS)/LENGTH;
			if(go.getCurrentPlayer()==BLACK) str="Player 2(White)"; else str="Player 1(Black)";
			if(i>=0 && i<LENGTH && j>=0 && j<LENGTH){
				try{
					if (!go.makeMove(i, j)) throw new IllegalArgumentException();
					prevMove = new Move(i,j);
					whoMovesNext = MACHINE;
					if(go.isPlayerWin(go.otherPlayer())){
						str+=" loses. Click anywhere to restart";
						gameStarted=false;
						reinit=true;
					}
					else str+="to move";
					repaint();
				}catch(IllegalArgumentException e){
					str="Illegal Move--";
					if(go.getCurrentPlayer()==BLACK) str+=" Player 1(Black) to move";
					else str+=" Player 2(White) to move";
				}
			}
			else str="Illegal Move";
		}
		else if(gameStarted==false){
			if(go.getMoves()==0){
				makeMachineMove();
				gameStarted=true;
				repaint();
			}
			return;
		}
		if(!str.equals("nothing")) msg.setText(str);
	}

	public void mouseReleased(MouseEvent me){
	}

	public void mouseEntered(MouseEvent me){
	}

	public void mouseExited(MouseEvent me){
	}

	public void mousePressed(MouseEvent me){
	}

	/**The main function. It creates a frame window an embeds the applet in the frame
	   so that the game can be run as a stand alone program
	   @param args The argument can be used to specify which player class
	   should the machine use for making the move - e.g "java GraphicGame NaivePlayer" will
	   make the machine use NaivePlayer class for making its move. If no argument is specified,
	   then RandomPlayer will be used.
	*/
	public static void main(String args[]){
		Frame frame=new Frame("Game of go -- by Software Corner Team, Techkriti 2005, IIT Kanpur");

		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
			public void windowDeiconified(WindowEvent we){
				GraphicGame.game.setRepaint(true);
				GraphicGame.game.repaint();
			}
			public void windowActivated(WindowEvent we){
				windowDeiconified(we);
			}
		});

		game=new GraphicGame();
		if(args.length == 0){
			System.out.println("No arguments specified - using RandomPlayer for machine");
		}
		else{
			try{
				Class cl = Class.forName(args[0]);
				Player p = (Player)cl.newInstance();
				game.setMachinePlayer(p);
			}catch(Exception e){
				System.out.println("Error loading player class - "+e);
				System.exit(-1);
			}
		}
		frame.setSize(500,500);
		frame.setVisible(true);
		frame.add(game);
		frame.setResizable(true);
		frame.show();
	}
}
