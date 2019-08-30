/**
 * This class contains methods
 * that implement the AI's board state
 * heuristics and access to the 
 * game board
 */
public class ConnectFourMiniMax 

{
	private int[][] board = new int[6][7]; //Computer has a copy of the board state
	private int maxDepth = 4; //The maximum depth of turns that the miniMax algorithm needs to evaluate

	/**
	 * Default constructor of the computer object
	 * @param boardstate
	 */
	public ConnectFourMiniMax(int[][] state)
	{
    	board = copyState(state); //initialize the copied board state
	}

	/**
	 * A method to copy a computer's board state
	 * @param copy board state
	 * @return new board state
	 */
	private int[][] copyState(int[][]copy)
	{
		int[][] newBoard = new int [6][7];
		for (int row =0; row<6; row++)
    	{
    		for (int col=0; col<7; col++)
    		{
    			newBoard[row][col]=copy[row][col]; //copy each piece on the board
    		}
    	}
    	return newBoard;
	}

	/**
	 * This method determines which column
	 * the computer will drop in. If it is the
	 * first turn, then drops are already decided
	 * @return column
	 * @param none
	 */
	public int firstTurn()
	{
		//If it is computer's first turn, return col 3.
		// However, if player's first turn was col3, return col2.
		// Do this by looking at the board.
		//Scan the board and determine whether the board is empty or User's first turn has ended
		int count = 0;
		for (int row =0; row<6; row++)
    	{
    		for (int col=0; col<7; col++)
    		{
    			if (board[row][col]==1)
    			{
    				count++; //count of filled spots
    			}
    		}
    	}
		if (count == 1)
		{
			if (board[5][2]==1) //If player's first turn is column 3 then computer drops in the 5th column
			{
				return 4;
			}
			else if (board[5][4]==1) //If player's first turn is column 5 then computer drops in column 3
			{
				return 2;
			}
			else //If computer's first turn then column 4 is selected
			{
				return 3;
			}
		}


		// If it is not the first turn, then an algorithm is used
		return miniMax(board,-10000000,0,1);
	}
	
	/**
	 * Check if depth has reached maxDpeth
	 * @param depth
	 * @return boolean
	 */
	private boolean checkDepth(int depth)
	{
		if(depth == maxDepth)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * This method is the miniMax algorithm implementation for connect four
	 * @param game
	 * @param turnScore
	 * @param depth
	 * @param turn
	 * @return move
	 */
	private int miniMax(int[][] game, int turnScore, int depth, int turn)
	{
		int count = 0;
		int currentMove = 0;
		int highestScore = turnScore;
		int nextTurn;

		int[][] newBoard = copyState(game);

		if (turn == 1)
		{
			nextTurn = 2;
		}
		else
		{
			nextTurn = 1;
		}
		ConnectFourConsole mm = new ConnectFourConsole();
		mm.updateBoard(newBoard, nextTurn);

		//Check if maximum depth has been reached
		if (checkDepth(depth))
		{
			int score = (eval(newBoard, nextTurn));
			if (score!=0)
			{
				highestScore = turn*(score-depth);
			}
			else
			{
				highestScore = score;
			}
		}

		else
		{
			//Calculate a score for each potential move
			for(int col=0; col<7; col++) //Create all possible board states after a person moves using a for loop
			{
				ConnectFourConsole newmm = new ConnectFourConsole();
				newmm.updateBoard(newBoard, nextTurn);
				int r = newmm.drop(col); //row being dropped into

				//Recursive call the generated game grid and compare the current value to move value
				// If move is higher, make it the new current value.

				//If drop is successful
				if (r!=-1) //if valid move
				{
					int[][] newB = new int[6][7]; //next board state


					if (depth < maxDepth) //checking if maximum depth is not passed
					{
						newB = copyState(newBoard);
						newB[r][col] = nextTurn; //update piece of next board state
						int v = -miniMax(newB,-10000000,depth+1,turn*-1); //Generate a score of the move board state when a move is made
						if (highestScore <= v)
						{
							currentMove = col; //update move based on score
							highestScore = v; //update highest score
						}
						else
						{
							count += highestScore;
						}
					}
					else
					{
						count -= highestScore;
					}
				}
				else
				{
					count += highestScore;
				}
				
			}
		}
		
		if (depth==0) //if the depth is 0
		{
			return currentMove;
		}
		else
		{
			return highestScore;
		}

	}

	/**
	 * Checks if a two in a row exists in
	 * the horizontal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkTwoHorizontal(int[][] board, int player)
	{ 
		int total = 0;
		//Check horizontal two in a row
    	for (int row=0;row<6;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			//(XX00)
    			if (board[row][col] == player &&
    				board[row][col+1] == board[row][col] &&
    				board[row][col+2] == 0 &&
    				board[row][col+3] == 0)
    				{
    					total += 3000;
    				}
    			//(X0X0)
    			else if (board[row][col] == player &&
    				board[row][col+1] == player &&
    				board[row][col+2] == 0 &&
    				board[row][col+3] == 0)
    				{
    					total += 3000;
    				}
    			//(X00X)
    			else if (board[row][col] == player &&
    				board[row][col+1] == player &&
    				board[row][col+2] == 0 &&
    				board[row][col+3] == 0)
    				{
    					total += 3000;
    				}
    			//(0XX0)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+2] == player &&
    				board[row][col+3] == 0)
    				{
    					total += 6000; //Bonus value for optimal board state
    				}
    			//(0X0X)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+2] == 0 &&
    				board[row][col+3] == player)
    				{
    					total += 3000;
    				}
    			//(00XX)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == board[row][col] &&
    				board[row][col+2] == player &&
    				board[row][col+3] == player)
    				{
    					total += 3000;
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if a two in a row exists
	 * in the vertical direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkTwoVertical(int[][] board, int player)
	{
		int total = 0;
		//Check for vertical two in a row
    	for (int row=5;row>1;row--)
    	{
    		for (int col = 0;col<7;col++)
    		{
    			if (board[row][col] == player &&
    				board[row-1][col] == board[row][col] &&
    				board[row-2][col] == 0)
    				{
    					total += 1000;
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if a two in a row exists
	 * in the positive diagonal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkTwoPositiveDiagonal(int[][] board, int player)
	{
		int total = 0;
		//check two in a row in positive slope direction
    	for (int row=5;row>2;row--)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == player &&
    				board[row-1][col+1] == board[row][col] &&
    				board[row-2][col+2] == 0 &&
    				board[row-3][col+3] == 0)
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == player &&
    				board[row-1][col+1] == 0 &&
    				board[row-2][col+2] == 0 &&
    				board[row-3][col+3] == board[row][col])
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == 0 &&
    				board[row-2][col+2] == player &&
    				board[row-3][col+3] == player)
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row-2][col+2] == board[row][col] &&
    				board[row-3][col+3] == board[row-1][col+1])
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == player &&
    				board[row-1][col+1] == 0 &&
    				board[row-2][col+2] == board[row][col] &&
    				board[row-3][col+3] == board[row-1][col+1])
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row-2][col+2] == board[row-1][col+1] &&
    				board[row-3][col+3] == board[row][col])
    				{
    					total += 4000; //bonus value for optimal board state
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if a two in a row exits
	 * in the negative diagonal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkTwoNegativeDiagonal(int[][] board, int player)
	{
		int total = 0;
		//check two in a row for diagonal in negative slope
    	for (int row=0;row<3;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == player &&
    				board[row+1][col+1] == board[row][col] &&
    				board[row+2][col+2] == 0 &&
    				board[row+3][col+3] == 0)
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == player &&
    				board[row+1][col+1] == 0 &&
    				board[row+2][col+2] == 0 &&
    				board[row+3][col+3] == board[row][col])
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row+1][col+1] == 0 &&
    				board[row+2][col+2] == player &&
    				board[row+3][col+3] == player)
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row+2][col+2] == board[row][col] &&
    				board[row+3][col+3] == board[row+1][col+1])
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == player &&
    				board[row+1][col+1] == 0 &&
    				board[row+2][col+2] == board[row][col] &&
    				board[row+3][col+3] == board[row+1][col+2])
    				{
    					total += 2000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row+2][col+2] == board[row+1][col+1] &&
    				board[row+3][col+3] == board[row][col])
    				{
    					total += 4000; //Bonus value for optimal board state
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if a regular three in a row
	 * exists in the horizontal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkThreeHorizontal(int[][] board, int player)
	{
		int total = 0;
		//Check for horizontal three in a row
    	for (int row=0;row<6;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			//(XX0X)
    			if (board[row][col] == player &&
    				board[row][col] == board[row][col+1] &&
    				board[row][col+2] == 0 &&
    				board[row][col] == board[row][col+3])
    				{
    					total += 300000;
    				}
    			//(X0XX)
    			else if (board[row][col] == player &&
    				board[row][col+1] == 0 &&
    				board[row][col] == board[row][col+2] &&
    				board[row][col] == board[row][col+3])
    				{
    					total += 300000;
    				}
    			//(0XXX)
    			else if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+1] == board[row][col+2] &&
    				board[row][col+1] == board[row][col+3])
    				{
    					total += 300000;
    				}
    			//(XXX0)
    			else if (board[row][col] == player &&
    				board[row][col] == board[row][col+2] &&
    				board[row][col] == board[row][col+2] &&
    				board[row][col+3] == 0)
    				{
    					total += 300000;
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if a regular three in a row
	 * in the vertical direction exists
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkThreeVertical(int[][] board, int player)
	{
		int total = 0;
		//Check for vertical three in a row
    	for (int row=5;row>2;row--)
    	{
    		for (int col = 0;col<7;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row-1][col] &&
    				board[row][col] == board[row-2][col] &&
    				board[row-3][col] == 0)
    				{
    					total += 100000;
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if a regular three in a row exists
	 * in the positive slope diagonal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkThreePositiveDiagonal(int[][] board, int player)
	{
		int total = 0;
		//Check for three in a row in a diagonal positive slope 
    	for (int row=5;row>2;row--)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == player &&
    				board[row][col] == board[row-1][col+1] &&
    				board[row][col] == board[row-2][col+2] &&
    				board[row-3][col+3] == 0)
    				{
    					total += 200000;
    				}
    			else if (board[row][col] == player &&
    				board[row][col] == board[row-1][col+1] &&
    				board[row-2][col+2] == 0 &&
    				board[row][col] == board[row-3][col+3])
    				{
    					total += 200000;
    				}
    			else if (board[row][col] == player &&
    				board[row-1][col+1] == 0 &&
    				board[row][col] == board[row-2][col+2] &&
    				board[row][col] == board[row-3][col+3])
    				{
    					total += 200000;
    				}
    			else if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row-1][col+1] == board[row-2][col+2] &&
    				board[row-1][col+1] == board[row-3][col+3])
    				{
    					total += 200000;
    				}
    		}
    	}
    	return total;
	}
	/**
	 * checks if a regular three in a row
	 * exists in the negative diagonal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkThreeNegativeDiagonal(int[][] board, int player)
	{
		int total = 0;
		//Check for three in a row in the negative slope diagonal direction
    	for (int row=0;row<3;row++)
    	{
    		for (int col = 0;col<4;col++)
    		{
    			if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row+1][col+1] == board[row+2][col+2] &&
    				board[row+1][col+1] == board[row+3][col+3])
    				{
    					total += 200000;
    				}
    			else if (board[row][col] == player &&
    				board[row+1][col+1] == 0 &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row][col] == board[row+3][col+3])
    				{
    					total += 200000;
    				}
    			else if (board[row][col] == player &&
    				board[row][col] == board[row+1][col+1] &&
    				board[row+2][col+2] == 0 &&
    				board[row][col] == board[row+3][col+3])
    				{
    					total += 200000;
    				}
    			else if (board[row][col] == player &&
    				board[row][col] == board[row+1][col+1] &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row+3][col+3] == 0)
    				{
    					total += 200000;
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if an open ended three in a row
	 * exists in the horizontal direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkPerfectHorizontal(int[][] board, int player)
	{
		int total = 0;
		//Horizontal perfect three in a row
    	for (int row=0;row<6;row++)
    	{
    		for (int col = 0;col<3;col++)
    		{
    			if (board[row][col] == 0 &&
    				board[row][col+1] == player &&
    				board[row][col+2] == player &&
    				board[row][col+3] == player &&
    				board[row][col] == board[row][col+4])
    				{
    					total += 600000; //Bonus value for optimal state
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks if an open ended three in a row
	 * exists in the negative slope direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkPerfectNegativeDiagonal(int[][] board, int player)
	{
		int total = 0;
		//Negative slope diagonal perfect three in a row
    	for (int row=0;row<2;row++)
    	{
    		for (int col = 0;col<3;col++)
    		{
    			if (board[row][col] == 0 &&
    				board[row+1][col+1] == player &&
    				board[row][col] == board[row+2][col+2] &&
    				board[row][col] == board[row+3][col+3] &&
    				board[row+4][col+4] == 0)
    				{
    					total += 400000; //Bonus value for optimal state
    				}
    		}
    	}
    	return total;
	}
	/**
	 * Checks whether a diagonal has 3 in a row with open ends
	 * in the positive slope direction
	 * @param board
	 * @param player
	 * @return score
	 */
	private int checkPerfectPositiveDiagonal(int [][] board, int player)
	{
		int total = 0;
		//Positive slope diagonal perfect three in a row
		for (int row=5;row>3;row--)
    	{
    		for (int col = 0;col<3;col++)
    		{
    			if (board[row][col] == 0 &&
    				board[row-1][col+1] == player &&
    				board[row-2][col+2] == player &&
    				board[row-3][col+3] == player &&
    				board[row-4][col+4] == 0)
    				{
    					total += 400000; //Bonus value for optimal state
    				}
    		}
    	}
		return total;
	}
	/**
	 * This method contains the evaluation 
	 * heuristics of board states that the
	 * AI uses to determine board state scores
	 * Spots with the same pieces next to each other are worth double
	 * @param board
	 * @param turn
	 * @return score
	 */
	private int eval(int[][] board,int player)
	{

		int score = 0; //total value of board state
		score += checkTwoHorizontal(board, player);
		score += checkTwoVertical(board, player);
		score += checkTwoPositiveDiagonal(board, player);
		score += checkTwoNegativeDiagonal(board, player);
		score += checkThreeHorizontal(board, player);
		score += checkThreeVertical(board, player);
		score += checkThreePositiveDiagonal(board, player);
		score += checkThreeNegativeDiagonal(board, player);
		score += checkPerfectHorizontal(board, player);
		score += checkPerfectNegativeDiagonal(board, player);
		score += checkPerfectPositiveDiagonal(board, player);
    	
    	return score;
	}

}


