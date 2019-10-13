import java.util.Scanner;
import java.util.LinkedList;
import javax.swing.JFrame;

public class Game extends JFrame{
	
	static final int
		EMPTY = 0,
		BLACK = 1,
		RED = 2,
		BKING = 3,
		RKING = 4;

	private static Scanner kb = new Scanner(System.in);
	
	Checker[][] board;
	int currentPlayer;
	boolean finish = false;
	Board b;

	
	public Game(){

	}

	//takes a char array and generates the board of checker objects
	Game(char[][] c){
		currentPlayer = 1;
		board = new Checker[8][8];
		int real = 1;
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				//creates a valid checker at initial checker position
				if(c[i][j] == 'r' || c[i][j] == 'w' || c[i][j] == 'p'){
					board[i][j] = new Checker(i,j,c[i][j], real);
					real++;
				}
				//creates a dummy 'checker' for board drawing for a non playable square
				else
					board[i][j] = new Checker(-1,-1,c[i][j], -1);
				board[i][j].data();
			}
		}
		b = new Board(board);
		
		add(b);
		setTitle("Checkers");
		setSize(808,830);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		
		play();
	}
	
	public Checker[][] getBoard(){
		return board;
	}
	
	public void makeMove(int start, int end){

		LinkedList<Move> moves = legalMoves(currentPlayer);

		//TODO DISPLAY LIST OF MOVES PLAYER CAN MAKE
		

		//lose check
		if (moves.equals(null)){
			System.out.println(currentPlayer + "loses");
			System.exit(0);}
		
		//valid move check
		if(start > 32 || start < 1 || end > 32 || end <1){
			System.out.println("invalid move");
			play();
		}
		
		System.out.println("making move " + start + " " + end);
		
		Checker temp = new Checker();
		int x = 0, y = 0, bp = 0;
		//loop through board to find pieces
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board.length; j++){
				//copy checker at start location
				if(board[i][j].getBoard_pos() == start){
					temp = board[i][j];
					//set space to empty
					bp = board[i][j].getBoard_pos();
					board[i][j] = new Checker(i,j,'p',bp);
				}
				//finds ending position and updates array index for new space
				if(board[i][j].getBoard_pos() == end){
					x = i;
					y = j;
				}
			}
		}		
		//updates board then redraws
		temp.setBoard_pos(end);
		board[x][y] = temp;
		b.update(board);
		
		currentPlayer++;
		if(currentPlayer > 2)
			currentPlayer = 1;
		play();
	}

	//creates linked list of legal moves for the give player. if there are jumps possible, only jumps are legal moves. 
	//if there are no legal moves then null is returned
	public LinkedList<Move> legalMoves(int player){
		int king;
		if(player == RED) {
			king = RKING;
		}else {
			king = BKING;
		}
		LinkedList<Move> list = new LinkedList<Move>();
		for(int row = 0; row<board.length; row++) {
			for(int col = 0; col<board[row].length; col++) {
				if(board[row][col].pieceType == player|| board[row][col].pieceType == king) {
					if(canJump(row, col, row-1, col-1, row-2, col-2)) {
						list.add(new Move(row, col, row-2,col-2));
					}else if(canJump(row, col, row-1, col+1, row-2, col+2)) {
						list.add(new Move(row, col, row-2, col-2));
					}else if(canJump(row, col, row + 1, col-1, row +2, col-2)) {
						list.add(new Move(row, col, row+2, col-2));
					}else if(canJump(row, col, row + 1, col + 1, row + 2, col +2)) {
						list.add(new Move(row, col, row+2, col+2));
					}
				}
			}
		}
		
		if(list.isEmpty()) {
			for(int row = 0; row<board.length; row++) {
				for(int col = 0; col<board[row].length; col++) {
					if(board[row][col].pieceType == player|| board[row][col].pieceType == king) {
						if(canMove(row, col, row-1, col-1, player)) {
							list.add(new Move(row, col, row-1,col-1));
						}else if(canMove(row, col, row-1, col+1, player)) {
							list.add(new Move(row, col, row-1, col-1));
						}else if(canMove(row, col, row + 1, col-1, player)) {
							list.add(new Move(row, col, row+1, col-1));
						}else if(canMove(row, col, row + 1, col + 1, player)) {
							list.add(new Move(row, col, row+1, col+1));
						}
					}
				}
			}
		}
		
		if(list.isEmpty()) {
			return null;
		}else {
			return list;
		}
	}
	
	
	//Creates a linked list of the legal jump moves
	public LinkedList<Move> jumpMoves(int player,  int row, int col){
		LinkedList<Move> jumps = new LinkedList<Move>();
		if(player != BLACK && player != RED) return null;
		
		int king = player + 2;
		if(board[row][col].pieceType == player|| board[row][col].pieceType == king) {
			if(canJump(row, col, row-1, col-1, row-2, col-2)) {
				jumps.add(new Move(row, col, row-2,col-2));
			}else if(canJump(row, col, row-1, col+1, row-2, col+2)) {
				jumps.add(new Move(row, col, row-1, col-2));
			}else if(canJump(row, col, row + 1, col-1, row +2, col-2)) {
				jumps.add(new Move(row, col, row+2, col-2));
			}else if(canJump(row, col, row + 1, col + 1, row + 2, col +2)) {
				jumps.add(new Move(row, col, row+2, col+2));
			}
		}
		return jumps;
		
		
	}
	
	
	//r1,c1 = starting position of player; r2,c2 = piece being jumped; r3,c3 = space jumped to
	public boolean canJump(int r1, int c1, int r2, int c2, int r3, int c3) {
		if(r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8) {
			return false; //jump is off the board
		}else if (board[r3][c3].pieceType != EMPTY) {
			return false; //no place to jump to
		}else if(currentPlayer == RED && (board[r1][c1].pieceType == RED || board[r1][c1].pieceType == RKING)) { //player is red and piece is red
			if((board[r2][c2].pieceType == BLACK || board[r2][c2].pieceType == BKING)) { //there is a black piece to jump
				if(board[r1][c1].pieceType == RKING) { //King can move forward or back
					return true;
				}else {
					return r1 > r3; //Single must move forward
				}
			}else {
				return false; //there is not a black piece to jump
			}
		}else if(currentPlayer == BLACK && (board[r1][c1].pieceType == BLACK || board[r1][c1].pieceType == BKING)) { //player is black and piece is black
			if((board[r2][c2].pieceType == RED || board[r2][c2].pieceType == RKING)) { //there is a red piece to jump
				if(board[r1][c1].pieceType == BKING) { //King can move forward or back
					return true;
				}else {
					return r1 < r3; //Single must move forward
				}
			}else {
				return false; //there is not a red piece to jump
			}	
		}
		return false;
	}
	
	public boolean canMove(int fr, int fc, int tr, int tc, int player) {
		if(player != BLACK && player != RED) return false; //The player is invalid
		if(tr < 0 || tr >= 8 || tc < 0 || tc >= 8) {
			return false; //jump is off the board
		}else if (board[tr][tc].pieceType != EMPTY) {
			return false; //no place to jump to
		}else if(player == RED && (board[fr][fc].pieceType == RED || board[fr][fc].pieceType == RKING)) { //player is red and piece is red
				if(board[fr][fc].pieceType == RKING && (tr == fr + 1 || tr == fr - 1) && (tc == fc + 1 || tc == fc - 1)) { //King can move forward or back
					return true;
				}else {
					return (tr == fr - 1 && (tc == fc+1 || tc==fc-1)); //Single must move forward
				}
		}else if(currentPlayer == BLACK && (board[fr][fc].pieceType == BLACK || board[fr][fc].pieceType == BKING)) { //player is black and piece is black
				if(board[fr][fc].pieceType == BKING && (tr == fr + 1 || tr == fr - 1) && (tc == fc + 1 || tc == fc - 1)) { //King can move forward or back
					return true;
				}else {
					return (tr == fr + 1 && (tc == fc+1 || tc==fc-1)); //Single must move forward
				}
			}else {
				return false; //there is not a red piece to jump
			}	
		
	}
	 
	//prints the current positions of the board to the console for debugging
	public void print() {
		for(int i = 0; i < board.length; i ++) {
			for(int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	



	
	//technically the board loop
	public void play(){
		System.out.println("make a move");
		
		makeMove(kb.nextInt(),kb.nextInt());
	}
	
	
	
}


