import java.util.Scanner;
/**
 * This class contains the methods necessary
 * for playing a game of connect four with 
 * an AI. All methods regarding game states
 * are implemented here.
 */

public class ConnectFourConsole 
{
	private int[][] board; //The board state
	private boolean isAIfirst; //Variable determining whether the AI went first or not
	private int turn; //The player's turn counter
	
	/**
	 * Default constructor for a console game object
	 */
	public ConnectFourConsole()
	{
		board = new int [6][7]; //6x7 board is standard rule for connect four
		for(int row = 0; row < 6; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				board[row][col] = 0; //Initialize the board with 0's to represent empty spots
			}
		}
		
	}
	
	/**
	 * This method initializes the turn counter.
	 * If the AI is going first then the counter starts at 2
	 * @param none
	 * @return void
	 */
	public void setFirstTurn()
	{
		turn = 1; //Turn is set to 1
	}
	
	/**
	 * Prints the board state.
	 * '.' represents an empty spot
	 * 'X' represents a player's piece
	 * 'O' represents a computer's piece
	 * @param none
	 * @return void
	 */
	public void printBoard()
	{
		for(int row = 0; row < 6; row++) //for every row
		{
			//Each loop checks whether the AI went first and the turn counter to determine the appropriate piece to print
			for(int col = 0; col < 7; col++) //for every column
			{
				if(board[row][col] == 1 && isAIfirst) 
				{
					System.out.print("O");
					System.out.print(" ");
				}
				else if(board[row][col] == 1 && !isAIfirst)
				{
					System.out.print("X");
					System.out.print(" ");
				}
				else if(board[row][col] == 2 && !isAIfirst)
				{
					System.out.print("O");
					System.out.print(" ");
				}
				else if(board[row][col] == 2 && isAIfirst)
				{
					System.out.print("X");
					System.out.print(" ");
				}
				else
				{
					System.out.print(".");
					System.out.print(" ");
				}
			}
			System.out.print("\n"); //print rows on a new line
		}
	}
	
	/**
	 * Copies the board state
	 * @param none
	 * @return board
	 */
	public int[][] copyBoard()
	{
		int[][] boardCopy = new int[6][7]; //new board constructed
		for(int row = 0; row < 6; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				boardCopy[row][col] = board[row][col]; //pieces are copied properly
			}
		}
		return boardCopy;
	}
	
	/**
	 * The board is updated as well as the turn counter
	 * This method will be used primarily by the AI
	 * to determine future board states
	 * @param a new board state
	 * @param turn counter
	 * @return void
	 */
	public void updateBoard(int[][] newBoard, int t)
	{
		board = new int[6][7];
		for(int row = 0; row < 6; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				board[row][col] = newBoard[row][col]; //update to a board state given 
			}
		}
		turn = t; //update turn counter
	}
	
	/**
	 * This method asks whether the user wants
	 * to have the first turn. After the user
	 * responds, the isAIfirst variable is updated
	 * @param none
	 * @return void
	 */
	public void askPlayer()
	{
		
		boolean isValid = false;
		String firstTurn = " ";
		Scanner input = new Scanner(System.in);
		System.out.println("Would you like to have the first turn? (y/n): ");
		while(!isValid) //Keep asking for user input until valid input is detected
		{
			
			firstTurn = input.next();
			
			if(firstTurn.equals("y"))
			{
				isValid = true;
			}
			else if(firstTurn.equals("n"))
			{
				isValid = true;
			}
			else
			{
				System.out.println("Invalid input, please try again (y/n): ");
			}
		}
		
		if(firstTurn.equals("y")) {isAIfirst = false;} //Human is going first
		else {isAIfirst = true;} //Computer is going first
	}
	
	/**
	 * Update a spot in the board with
	 * the turn value if the spot is empty
	 * @param row
	 * @param col
	 * @return void
	 */
	public void setBoardPos(int row, int col)
	{
		if(board[row][col] == 0)
		{
			board[row][col] = turn;
		}
	}
	
	/**
	 * Checks if a winner is detected
	 * on the current board state
	 * @param none
	 * @return boolean
	 */
	public boolean win()
	{
		boolean win = false;
		
		//Check for horizontal win condition 
		for(int row = 0; row < 6; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				if((board[row][col] != 0) &&
						board[row][col] == board[row][col+1] &&
						board[row][col] == board[row][col+2] &&
						board[row][col] == board[row][col+3])
				{
					win = true;
				}
			}
		}
		
		//Check for vertical win condition
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 7; col++)
			{
				if((board[row][col] != 0) &&
						board[row][col] == board[row+1][col] &&
						board[row][col] == board[row+2][col] &&
						board[row][col] == board[row+3][col])
				{
					win = true;
				}
			}
		}
		
		//Check for negative slope diagonal win condition
		for(int row = 5; row > 2; row--)
		{
			for(int col = 0; col < 4; col++)
			{
				if((board[row][col] != 0) &&
						board[row][col] == board[row-1][col+1] &&
						board[row][col] == board[row-2][col+2] &&
						board[row][col] == board[row-3][col+3])
				{
					win = true;
				}
			}
		}
		
		//Check for positive slope diagonal win condition
		for(int row = 0; row < 3; row++)
		{
			for(int col = 0; col < 4; col++)
			{
				if((board[row][col] != 0) &&
						board[row][col] == board[row+1][col+1] &&
						board[row][col] == board[row+2][col+2] &&
						board[row][col] == board[row+3][col+3])
				{
					win = true;
				}
			}
		}
		return win; //return whether there is a winner or not
	}
	
	/**
	 * This method drops a piece and returns the row
	 * it was dropped. If the piece can not be 
	 * dropped, -1 is returned
	 * @param col
	 * @return row position or -1 if invalid
	 */
	public int drop(int col)
	{
		if(win()) //Check if there is a winner
		{
			return -1;
		}
		int row = 5; //bottom most row of the board 
		for(; row >= 0 && board[row][col] != 0; row--) //update the row counter if the spot is non empty
		{
		}
		if(row == -1) //The row counter will reach -1 if the top most row is full
		{
			return -1;
		}
		else
		{
			setBoardPos(row, col); //fill the spot with the appropriate piece
			if(turn == 1) {turn = 2;} //update turn counter
			else {turn = 1;}
			return row; //row that has been filled is returned
		}
	}
	
	/**
	 * This method checks whether the game is full or not
	 * @param none
	 * @return boolean
	 */
	public boolean full()
	{
		boolean full = true;
		for(int row = 5; row >= 0; row--)
		{
			for(int col = 0; col < 7; col++)
			{
				if(board[row][col] == 0)
				{
					full = false; //as soon as a spot is not empty, function returns false
				}
			}
		}
		return full; //No empty spots found
	}
	
	/**
	 * This method asks for a users input so that
	 * a piece many be dropped in a column
	 * @param none
	 * @return column position
	 */
	public int parseInput()
	{
		String raw = " ";
		int in = -1;
		boolean isValid = false;
		Scanner g = new Scanner(System.in);
		System.out.println("Please enter a column to drop: ");
		while(!isValid) //Keeps asking for a valid user input
		{
			
			raw = g.nextLine();
			
			if(raw.length() == 1) //input should be of length 1 
			{
				in = Character.getNumericValue(raw.charAt(0));
			}
			else
			{
				System.out.println("Invalid input, please enter 1 integer only: ");
				continue;
			}
			if(in<=35 && in>=10) //integer should not be an alphabet
			{
				System.out.println("Invalid input, please enter a valid integer: ");
				continue;
			}
			if(in<=7 && in>0) //column must be 1-7 inclusive
			{
				isValid = true;
			}
			else
			{
				System.out.println("Invalid input, please enter a column 1-7 to drop in: ");
			}
		}
		
		return in;
	}
	
	/**
	 * The main function that runs the game
	 * It implements the AI that the user
	 * will play against. Users have the option to
	 * decide whether they will go first or not
	 * @param not used
	 * @return void
	 */
	public static void main(String[] args)
	{
		
		ConnectFourConsole newGame = new ConnectFourConsole(); //new game object
		newGame.askPlayer(); //ask whether user starts
		newGame.setFirstTurn(); //initialize the turn counter
		
		if(newGame.isAIfirst) //if the AI is first, then it will make a move first
		{
			System.out.println("Computer is making a move...");
			ConnectFourMiniMax comp = new ConnectFourMiniMax(newGame.copyBoard());
			int c = comp.firstTurn(); //The AI's selected column to drop in
			newGame.drop(c); //Drop the piece
			newGame.printBoard(); //print board
		}
		while(!newGame.win() && !newGame.full()) //Game runs as long as there are no winners or board is full
		{
			int move = newGame.parseInput(); //get user's input to drop a piece
			if(newGame.drop(move-1) == -1) {System.out.print("Column is full, "); continue;} //If invalid drop, error message and start loop again
			newGame.printBoard(); //print board
			System.out.println("Computer is making a move...");
			ConnectFourMiniMax comp = new ConnectFourMiniMax(newGame.copyBoard()); 
			int c = comp.firstTurn();
			newGame.drop(c);
			newGame.printBoard();
		}
		if(newGame.turn == 1 && !newGame.isAIfirst) {System.out.println("Winner is Computer");} //Print winner based on counter and whether AI went first
		else if(newGame.turn == 1 && newGame.isAIfirst){System.out.println("Winner is Player");}
		else if(newGame.turn == 2 && !newGame.isAIfirst){System.out.println("Winner is Player");}
		else if(newGame.turn == 2 && newGame.isAIfirst){System.out.println("Winner is Computer");}
		
		else {System.out.println("Board is full, game is tied");} //Game is tied if no winner is declared
	}
}
