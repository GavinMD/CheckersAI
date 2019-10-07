import java.util.Scanner;

import javax.swing.JFrame;

public class Game extends JFrame{
	
	private static Scanner kb = new Scanner(System.in);
	
	Checker[][] board;
	int turn;
	boolean finish = false;
	Board b;

	
	public Game(){

	}

	//takes a char array and generates the board of checker objects
	Game(char[][] c){
		turn = 0;
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
		
		play();
	}
	
	//technically the game loop
	public void play(){
		System.out.println("make a move");
		
		makeMove(kb.nextInt(),kb.nextInt());
	}
	
	
	
}
