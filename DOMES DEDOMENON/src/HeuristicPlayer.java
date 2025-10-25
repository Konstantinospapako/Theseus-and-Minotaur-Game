import java.util.ArrayList;

import java.util.Collections;
import java.util.Random;

public class HeuristicPlayer extends Player {

	//Class that enables Theseus to move strategically in order to win 
	
	ArrayList<Integer[]> path;
	int minotaurDistance = 0;
	int supplyDistance = 0;
	int up = 0, down = 0, right = 0, left = 0;
	
	//Empty constructor: Sets the values to zero
	public HeuristicPlayer()
	{
		super();
		
		path = new ArrayList<Integer[]>();
	}
	
	//Constructor with arg a Playerid,a name,a board, a score and coordinates plus the dimention of the path:
	//Sets the values of a new object equal to the given ones
	public HeuristicPlayer(int playerId,String name,Board board,int score,int x, int y, int dimention)
	{
		super(playerId,name,board,score,x,y);
		path = new ArrayList<Integer[]>(dimention);
		
	}
	
	
		
	/**
	 *  This function gets called inside the public double evaluate function
	 *  
	 * @param NearSupplies			a parameter which symbolizes the distance between Theseus and Supply
	 * @param OpponentDistance		a parameter which symbolizes the distance between Theseus and Minotaur
	 * @param previousMove			a parameter which symbolizes the previous move of the player
	 * @param player				a parameter which symbolizes the player (Theseus = 0, Minotaur = 1)
	 * @return						the actual value of the potential move
	 */
	public double goalFunction(double NearSupplies, double OpponentDistance, double previousMove,int player)
	{
		if(player == 0)
		{
			return NearSupplies * 4.4 - OpponentDistance * 5.6 - 0.35 * previousMove ;
		}
		else
		{
			return NearSupplies * 5.6 + OpponentDistance * 4.2 - previousMove * 0.35;
		}
	}
	

	
	/**
	 * In this function the parameters NearSupplies,OpponentDistance get values according to the 
	 * current board (distance from minotaur, supplies and existence of walls)
	 * 
	 * @param currentPos 	 	the current position of Theseus - smart player 
	 * @param dice		  		practically the direction we want to evaluate
	 * @param minotaurTile		Minotaur's TileId
	 * @param gameround			the round of the game
	 * @param playerId			a parameter which symbolizes the player (Theseus = 0, Minotaur = 1)
	 * @return 					the evaluation of the move/direction 
	 */
	public double evaluate(int TheseusTile, int dice, int MinotaurTile, int gameround,int player)
	{
		/* we define function f(NearSupplies, OpponentDistance) = NearSupplies * 4.4 - OpponentDistance * 5.6 as our goal function*/
		
		double NearSupplies = 0,OpponentDistance = 0, previousMove = 0;
		double evaluation = 0;		//the return parameter of this function
		
		if(player == 0)
		{
		/* This switch structure according to the dice it evaluates the respective direction */
		switch(dice)		
		{
			case 0:		//upwards
				if((TheseusTile+ board.getN())/(board.getN()) < (board.getN()) && board.gettiles(TheseusTile).getup() == false)		// if the move doesn't get out of bounds and there is no wall
				{
					if(board.hasSupply(TheseusTile + board.getN()))
					{
						NearSupplies += 1;
					}
					if(TheseusTile + board.getN() == MinotaurTile )
					{
						OpponentDistance += 3;
					}
					if((TheseusTile+ 2*board.getN())/(board.getN()) < (board.getN()) && board.gettiles(TheseusTile + board.getN()).getup() == false)
					{
						if(board.hasSupply(TheseusTile + 2*board.getN()))
						{
							NearSupplies += 0.5;
						}
						if(TheseusTile + 2*board.getN() == MinotaurTile )
						{
							OpponentDistance += 2;
						}
						if((TheseusTile + 3*board.getN())/(board.getN()) < (board.getN()) && board.gettiles(TheseusTile + 2*board.getN()).getup() == false)
						{
							if(board.hasSupply(TheseusTile + 2*board.getN()))
							{
								NearSupplies += 0.4;
							}
							if(TheseusTile + 2*board.getN() == MinotaurTile )
							{
								OpponentDistance += 0.3;
							}
							
						}
					}
				}
				else
				{							
					OpponentDistance = 2;		// If the move is invalid due to existence of wall we mark it negatively so as to not move towards this direction and lose the opportunity to move 
				}
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 2)
					{
						previousMove = 1;
					}
				}	
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
				System.out.println("up " + evaluation);
				break;
				
			case 1:
				if((TheseusTile + 1)%(board.getN()) < (board.getN()) && board.gettiles(TheseusTile).getright() == false)
				{
					if(board.hasSupply(TheseusTile + 1))
					{
						NearSupplies += 1;
					}
					if(TheseusTile + 1 == MinotaurTile )
					{
						OpponentDistance += 3;
					}
					if((TheseusTile + 2)%(board.getN()) < (board.getN()) && board.gettiles(TheseusTile + 1).getright() == false)
					{
						if(board.hasSupply(TheseusTile + 2))
						{
							NearSupplies += 0.5;
						}
						if(TheseusTile + 2 == MinotaurTile )
						{
							OpponentDistance += 2;
						}
						if((TheseusTile + 3) % (board.getN()) < (board.getN()) && board.gettiles(TheseusTile + 2).getright() == false)
						{
							if(board.hasSupply(TheseusTile + 3))
							{
								NearSupplies += 0.4;
							}
							if(TheseusTile + 3 == MinotaurTile )
							{
								OpponentDistance += 0.3;
							}
						}
					}
				}
				else
				{							
					OpponentDistance = 2;
				}
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 3)
					{
						previousMove = 1;
					}
				}
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
				System.out.println("right " + evaluation);
				break;
				
			case 2:
				if((TheseusTile - board.getN())/(board.getN()) >= 0 && board.gettiles(TheseusTile).getdown() == false)
				{
					if(board.hasSupply(TheseusTile - board.getN()))
					{
						NearSupplies += 1;
					}
					if(TheseusTile - board.getN() == MinotaurTile )
					{
						OpponentDistance += 3;
					}
					if((TheseusTile - 2*board.getN())/(board.getN()) >= 0 && board.gettiles(TheseusTile - board.getN()).getdown() == false)
					{
						if(board.hasSupply(TheseusTile - 2*board.getN()))
						{
							NearSupplies += 0.5;
						}
						if(TheseusTile - 2*board.getN() == MinotaurTile )
						{
							OpponentDistance += 2;
						}
						if((TheseusTile - 3*board.getN())/(board.getN()) >= 0 && board.gettiles(TheseusTile - 2*board.getN()).getdown() == false)
						{
							if(board.hasSupply(TheseusTile - 3*board.getN()))
							{
								NearSupplies += 0.4;
							}
							if(TheseusTile - 3*board.getN() == MinotaurTile )
							{
								OpponentDistance += 0.3;
							}
						}
					}
				}
				else
				{											 
					OpponentDistance = 2;
				}
				
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 0)
					{
						previousMove = 1;
					}
				}
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
				System.out.println("down " + evaluation);
				break;
				
			case 3:
				if((TheseusTile - 1)%(board.getN()) >= 0 && board.gettiles(TheseusTile).getleft() == false)
				{
					if(board.hasSupply(TheseusTile - 1))
					{
						NearSupplies += 1;
					}
					if((TheseusTile - 1)== MinotaurTile )
					{
						OpponentDistance += 3;
					}
					if((TheseusTile - 2)%(board.getN()) >= 0 && board.gettiles(TheseusTile - 1).getleft() == false)
					{
						if(board.hasSupply(TheseusTile - 2))
						{
							NearSupplies += 0.5;
						}
						if((TheseusTile - 2) == MinotaurTile)
						{
							OpponentDistance += 2;
						}
						if((TheseusTile - 3) % (board.getN()) >= 0 && board.gettiles(TheseusTile - 2).getleft() == false)
						{
							if(board.hasSupply(TheseusTile - 3))
							{
								NearSupplies += 0.4;
							}
							if((TheseusTile - 3) == MinotaurTile)
							{
								OpponentDistance += 0.3;
							}
						}
					}
				}
				else
				{							
					OpponentDistance = 2;
				}
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 1)
					{
						previousMove = 1;
					}
				}
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
				System.out.println("left " + evaluation);
				break;
		}
		return evaluation;
		}
		else
		{
			switch(dice)		
			{
				case 0:		//upwards
					if((MinotaurTile+ board.getN())/(board.getN()) < (board.getN()) && board.gettiles(MinotaurTile).getup() == false)		// if the move doesn't get out of bounds and there is no wall
					{
						if(MinotaurTile + board.getN() == TheseusTile )
						{
							OpponentDistance += 3;
						}
						if((MinotaurTile+ 2*board.getN())/(board.getN()) < (board.getN()) && board.gettiles(MinotaurTile + board.getN()).getup() == false)
						{
							if(MinotaurTile + 2*board.getN() == TheseusTile )
							{
								OpponentDistance += 2;
							}
							if((MinotaurTile + 3*board.getN())/(board.getN()) < (board.getN()) && board.gettiles(MinotaurTile + 2*board.getN()).getup() == false)
							{
								if(MinotaurTile + 2*board.getN() == TheseusTile )
								{
									OpponentDistance += 0.3;
								}
								
							}
						}
					}
					else
					{							
						NearSupplies = -2;		// If the move is invalid due to existence of wall we mark it negatively so as to not move towards this direction and lose the opportunity to move 
					}
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 2)
						{
							previousMove = 1;
						}
					}	
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
					System.out.println("up " + evaluation);
					break;
					
				case 1:
					if((MinotaurTile + 1)%(board.getN()) < (board.getN()) && board.gettiles(MinotaurTile).getright() == false)
					{
						if(MinotaurTile + 1 == TheseusTile )
						{
							OpponentDistance += 3;
						}
						if((MinotaurTile + 2)%(board.getN()) < (board.getN()) && board.gettiles(MinotaurTile + 1).getright() == false)
						{
							if(MinotaurTile + 2 == TheseusTile )
							{
								OpponentDistance += 2;
							}
							if((MinotaurTile + 3) % (board.getN()) < (board.getN()) && board.gettiles(MinotaurTile + 2).getright() == false)
							{
								if(MinotaurTile + 3 == TheseusTile )
								{
									OpponentDistance += 0.3;
								}
							}
						}
					}
					else
					{							
						NearSupplies = -2;
					}
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 3)
						{
							previousMove = 1;
						}
					}
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
					System.out.println("right " + evaluation);
					break;
					
				case 2:
					if((MinotaurTile - board.getN())/(board.getN()) >= 0 && board.gettiles(MinotaurTile).getdown() == false)
					{
						if(MinotaurTile - board.getN() == TheseusTile )
						{
							OpponentDistance += 3;
						}
						if((MinotaurTile - 2*board.getN())/(board.getN()) >= 0 && board.gettiles(MinotaurTile - board.getN()).getdown() == false)
						{
							if(MinotaurTile - 2*board.getN() == TheseusTile )
							{
								OpponentDistance += 2;
							}
							if((MinotaurTile - 3*board.getN())/(board.getN()) >= 0 && board.gettiles(MinotaurTile - 2*board.getN()).getdown() == false)
							{
								if(MinotaurTile - 3*board.getN() == TheseusTile )
								{
									OpponentDistance += 0.3;
								}
							}
						}
					}
					else
					{											 
						NearSupplies = -2;
					}
					
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 0)
						{
							previousMove = 1;
						}
					}
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
					System.out.println("down " + evaluation);
					break;
					
				case 3:
					if((MinotaurTile - 1)%(board.getN()) >= 0 && board.gettiles(MinotaurTile).getleft() == false)
					{
						if((MinotaurTile - 1)== TheseusTile )
						{
							OpponentDistance += 3;
						}
						if((MinotaurTile - 2)%(board.getN()) >= 0 && board.gettiles(MinotaurTile - 1).getleft() == false)
						{
							if((MinotaurTile - 2) == TheseusTile)
							{
								OpponentDistance += 2;
							}
							if((MinotaurTile - 3) % (board.getN()) >= 0 && board.gettiles(MinotaurTile - 2).getleft() == false)
							{
								if((MinotaurTile - 3) == TheseusTile)
								{
									OpponentDistance += 0.3;
								}
							}
						}
					}
					else
					{							
						NearSupplies = -2;
					}
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 1)
						{
							previousMove = 1;
						}
					}
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
					System.out.println("left " + evaluation);
					break;
			}
			return evaluation;
		}
	}
	
	
	//----------------------------------------------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * 
	 * @param currentPos		the current position of Theseus- smart player
	 * @param minotaurTile		Minotaur's TileId
	 * @param gameround			in order to take Theseu's move from previous round
	 * @return					optimum direction to move
	 */
	public int getNextMove(int TheseusTile, int MinotaurTile, int gameround, int player)
	{
		double[] evaluationArray = new double[4];
		int newTheseusTile = TheseusTile;
		double[] tempEvaluation = new double[4];							
		for(int i = 0; i < 4; i++)
		{
			/*The evaluationArray gets the evaluation of the moves accordingly to the direction.
			 * This happens because the indexes of the array(0,1,2 etc), are equal to the dice(i) at the evaluate function 
			 */
				evaluationArray[i] = evaluate(TheseusTile,i,MinotaurTile, gameround,player);		
				tempEvaluation[i] = evaluate(TheseusTile,i,MinotaurTile, gameround,player);	// temporarily saves the evaluation of each move, so as to bubble short him and get the max.
		}
		
		int a = 0;   														//This parameter will hold the optimum direction's value
																			// It will be the returned value of this function
		
		
		double temp;
		for(int i = 0; i < 4; i++)											// bubblesort of the tempEvaluation so as to take the max evaluation
		{
			for(int j = 4-1; j > i; j--)
			{
				if(tempEvaluation[j] < tempEvaluation[j-1])
				{
					temp = tempEvaluation[j];
					tempEvaluation[j] = tempEvaluation[j-1];
					tempEvaluation[j-1] = temp;
				}
			}
		}
		
		int [] moves = new int[4];
		int counter = 0;
	
		
		//if the max value of the sorted array before is zero this means we have to choose randomly between the zero-evaluated directions
		if(tempEvaluation[3] == 0)			
		{
			for(int k = 0; k < 4; k++)
			{
				if(evaluationArray[k] == 0)	/*because of the fact that the indexes of evaluationArray are the direction of the according moves
				 								 *  we go through this array with a for loop and if the value is 0 we keep at the array "moves" 
				 								 *	the index (and thus the direction that is rated with zero).
												 */
				{
					moves[counter++] = k;
				}
			}
			Random rand = new Random();			// then randomly we pick an index (which is defined by parameter counter) of the "moves" array 
			int choice = rand.nextInt(counter);
			a = moves[choice];					//and return the direction to move
								
		}
		
		
		
		else {									/* Else we check for what index of the evaluationArray the content is equal
		 											to the max value and we return the direction */
			for(int j = 0; j < 4; j++)
	        {
	        	if(evaluationArray[j] == tempEvaluation[3])
	        		a = j;			// registers "a" with the optimum direction to move
	        }
		}
				
		if(player == 0) {
		//This switch structure renews Theseu's TileId and keeps count of how many times Theseus moved towards each direction
        switch(a)			
        {
        	case 0: newTheseusTile += board.getN();
        			up++;
        		break;
        	case 1: newTheseusTile += 1;
        			right++;
        		break;
        	case 2: newTheseusTile -= board.getN();
        			down++;
        		break;
        	case 3: newTheseusTile -= 1;
        			left++;
        		break;
        }
        
        
      //checks if Theseus collected any supplies
        int s;										
        if(board.hasSupply(newTheseusTile))
        {
        	s = 1;
        }
        else s = 0;
        
       
		
		/*Those parameters are initialized in zero and according to the board are renewed by the actual distance from minotaur and supplies.
		 * If it's not meant to happen they will stay 0 and the statistics function will print 0
		 */
		supplyDistance = 0;					
		minotaurDistance = 0;
				
		
		/*In each if statement we check if there is any supply or the minotaur at a 3 tileId width and if there is no wall
		 *  towards this direction we renew the according parameters supply distance and minotaur distance
		 */
		if((board.hasSupply(newTheseusTile + board.getN()) && newTheseusTile + board.getN() < board.getN()*board.getN() && board.gettiles(newTheseusTile).getup() == false) ||
			(board.hasSupply(newTheseusTile - board.getN()) && newTheseusTile - board.getN() > 0 && board.gettiles(newTheseusTile).getdown() == false) ||
			(board.hasSupply(newTheseusTile + 1) && newTheseusTile + 1 < board.getN()*board.getN() && board.gettiles(newTheseusTile).getright() == false && (newTheseusTile + 1) % board.getN() != 0) || 
			(board.hasSupply(newTheseusTile - 1 ) && newTheseusTile - 1 > 0 && board.gettiles(newTheseusTile).getleft() == false && (newTheseusTile - 1) % board.getN() != board.getN() - 1))
		{
			supplyDistance = 1;
		}
					
		if(((newTheseusTile + board.getN() == MinotaurTile) && newTheseusTile + board.getN() < board.getN()*board.getN() && board.gettiles(newTheseusTile).getup() == false) || 
			((newTheseusTile - board.getN() == MinotaurTile) && newTheseusTile - board.getN() > 0 && board.gettiles(newTheseusTile).getdown() == false)|| 
			((newTheseusTile + 1 == MinotaurTile) && newTheseusTile + 1 < board.getN()*board.getN() && board.gettiles(newTheseusTile).getright() == false && (newTheseusTile + 1) % board.getN() != 0)||  
			((newTheseusTile - 1 == MinotaurTile) && newTheseusTile - 1 > 0 && board.gettiles(newTheseusTile).getleft() == false && (newTheseusTile - 1) % board.getN() != board.getN() - 1))
		{
			minotaurDistance = 1;
		}
					
					
		if((board.hasSupply(newTheseusTile + 2*board.getN()) && newTheseusTile + 2*board.getN() < board.getN()*board.getN() && board.gettiles(newTheseusTile + board.getN()).getup() == false && board.gettiles(newTheseusTile).getup() == false) ||
			(board.hasSupply(newTheseusTile - 2*board.getN()) && newTheseusTile - 2*board.getN() > 0 && board.gettiles(newTheseusTile - board.getN()).getdown() == false && board.gettiles(newTheseusTile).getdown() == false) || 
			(board.hasSupply(newTheseusTile + 2) && newTheseusTile + 2 < board.getN()*board.getN() && board.gettiles(newTheseusTile + 1).getright() == false && board.gettiles(newTheseusTile).getright() == false && (newTheseusTile + 2) % board.getN() != 0) ||
			(board.hasSupply(newTheseusTile - 2) && newTheseusTile - 2 > 0 && board.gettiles(newTheseusTile - 1).getleft() == false && board.gettiles(newTheseusTile).getleft() == false && (newTheseusTile - 2) % board.getN() != board.getN() - 1))
		{
			supplyDistance = 2;
		}
					
		if(((newTheseusTile + 2*board.getN() == MinotaurTile) && newTheseusTile + 2*board.getN() < board.getN()*board.getN() && board.gettiles(newTheseusTile + board.getN()).getup() == false && board.gettiles(newTheseusTile).getup() == false) ||
			((newTheseusTile - 2*board.getN() == MinotaurTile) && newTheseusTile - 2*board.getN() > 0 && board.gettiles(newTheseusTile - board.getN()).getdown() == false && board.gettiles(newTheseusTile).getdown() == false) || 
			((newTheseusTile + 2  == MinotaurTile) && newTheseusTile + 2 < board.getN()*board.getN() && board.gettiles(newTheseusTile + 1).getright() == false && board.gettiles(newTheseusTile).getright() == false && (newTheseusTile + 2) % board.getN() != 0) ||
			((newTheseusTile - 2 == MinotaurTile) && newTheseusTile - 2 > 0 && board.gettiles(newTheseusTile - 1).getleft() == false && board.gettiles(newTheseusTile).getleft() == false && (newTheseusTile - 2) % board.getN() != board.getN() - 1))
		{
			minotaurDistance = 2;
		}
					
					
		if((board.hasSupply(newTheseusTile + 3*board.getN()) && newTheseusTile + 3*board.getN() < board.getN()*board.getN() && board.gettiles(newTheseusTile + 2*board.getN()).getup() == false && board.gettiles(newTheseusTile + board.getN()).getup() == false && board.gettiles(newTheseusTile).getup() == false) ||
			(board.hasSupply(newTheseusTile - 3*board.getN()) && newTheseusTile - 3*board.getN() > 0 && board.gettiles(newTheseusTile - 2*board.getN()).getdown() == false && board.gettiles(newTheseusTile - board.getN()).getdown() == false && board.gettiles(newTheseusTile).getdown() == false) || 
			(board.hasSupply(newTheseusTile + 3) && newTheseusTile + 3 < board.getN()*board.getN() && board.gettiles(newTheseusTile + 2).getright() == false && board.gettiles(newTheseusTile + 1).getright() == false && board.gettiles(newTheseusTile).getright() == false && (newTheseusTile + 3) % board.getN() != 0) ||
			(board.hasSupply(newTheseusTile - 3) && newTheseusTile - 3 > 0 && board.gettiles(newTheseusTile - 2).getleft() == false && board.gettiles(newTheseusTile - 1).getleft() == false && board.gettiles(newTheseusTile).getleft() == false && (newTheseusTile - 3) % board.getN() != board.getN() - 1))
		{			
			supplyDistance = 3;
		}
		
		if(((newTheseusTile + 3*board.getN() == MinotaurTile) && newTheseusTile + 3*board.getN() < board.getN()*board.getN() && board.gettiles(newTheseusTile + 2*board.getN()).getup() == false && board.gettiles(newTheseusTile + board.getN()).getup() == false && board.gettiles(newTheseusTile).getup() == false) ||
			((newTheseusTile - 3*board.getN() == MinotaurTile) && newTheseusTile - 3*board.getN() > 0 && board.gettiles(newTheseusTile - 2*board.getN()).getdown() == false && board.gettiles(newTheseusTile - board.getN()).getdown() == false && board.gettiles(newTheseusTile).getdown() == false) || 
			((newTheseusTile + 3  == MinotaurTile) && newTheseusTile + 3 < board.getN()*board.getN() && board.gettiles(newTheseusTile + 2).getright() == false && board.gettiles(newTheseusTile + 1).getright() == false && board.gettiles(newTheseusTile).getright() == false && (newTheseusTile + 3) % board.getN() != 0) ||
			((newTheseusTile - 3 == MinotaurTile) && newTheseusTile - 3 > 0 && board.gettiles(newTheseusTile - 2).getleft() == false && board.gettiles(newTheseusTile - 1).getleft() == false && board.gettiles(newTheseusTile).getleft() == false && (newTheseusTile - 3) % board.getN() != board.getN() - 1))
		{	
				minotaurDistance = 3;
		}
				
        
        Integer[] pathArr = {a, s,supplyDistance,minotaurDistance}; //renews values of the path
        path.add(pathArr);
		}
        
        if(player == 1)
        {
        	Integer[] pathArr = {a};
        	path.add(pathArr);
        }
        return  a;			//optimum direction to move
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * @param gameround is the actual round we are in
	 */
	public void statistics(int gameround)
	{
		String direction = "";		// this parameter will later hold the direction towards we choose to move
		int round = 0; 					// The round (1-100)
		for(int i = 0; i < gameround; i++)
		{
			round = i + 1;				//a starts from a but we count from 1 to 100 not from 0 to 99
			
			if(path.get(i)[0] == 0)
				direction = "upwards";			// we set the direction we decided to move to the string
			else if(path.get(i)[0] == 1)
				direction = "to the right";
			else if(path.get(i)[0] == 2)
				direction = "downwards";
			else if(path.get(i)[0] == 3)
				direction = "to the left";
			
			
			if(round == 1)							//This discrimination happens only for aesthetic reasons to print 1st,2nd and not 1th,2th
			{
				System.out.println("At the " + round + "st " + "round he chose to move " + direction + ", he collected " + path.get(i)[1] + 
								" supply, his distance from the supply is " + path.get(i)[2] + " and his distance from the minotaur is " + path.get(i)[3]);
				System.out.println("");
			}
			else if(round == 2)
			{
				System.out.println("At the " + round + "nd " + "round he chose to move " + direction + ", he collected " + path.get(i)[1] + 
								" supply, his distance from the supply is " + path.get(i)[2] + " and his distance from the minotaur is " + path.get(i)[3]);
				System.out.println("");
			}
			else if(round == 3)
			{
				System.out.println("At the " + round + "rd " + "round he chose to move " + direction + ", he collected " + path.get(i)[1] + 
								" supply, his distance from the supply is " + path.get(i)[2] + " and his distance from the minotaur is " + path.get(i)[3]);
				System.out.println("");
			}
			else
			{
				System.out.println("At the " + round + "th " + "round he chose to move " + direction + ", he collected " + path.get(i)[1] + 
								" supply, his distance from the supply is " + path.get(i)[2] + " and his distance from the minotaur is " + path.get(i)[3]);
				System.out.println("");
			}
		}
		
		System.out.println("The player chose to move upwards " + up + " times, downwards " + down + " times, to the right " + right +
							" times and to the left " + left + " times");
		
	}
	
	public int[] move(int id, int dice,int player) {
		
		int[] position = new int[4];
		switch (dice) {
		
			case 0: // If dice = 0 player gets to move upwards 
				if(id < board.getN()*board.getN() - board.getN()) {		// just an extra check that by the dice is doesn't get out of bounds
					if(board.gettiles(id).getup() == false)
					{
						position[0] = id + board.getN();
						position[1] = board.gettiles(position[0]).getx();
						position[2] = board.gettiles(position[0]).gety();
						for(int i = 0; i < board.getS(); i++) 
						{
							if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0)
							{
								position[3] = board.getsupplies(i).getsupplyId();
								board.getsupplies(i).setx(-1);
								board.getsupplies(i).sety(-1);
								board.getsupplies(i).setsupplyTileId(-1);
								System.out.println("Well done!!! You earned a trophy!!!");
								score++;
								System.out.println("Your score now is " + score +"!!! " + "You need " + (4-score) + " more trophies to win");
							}
							else position[3] = -1;
						}
					}
					else {
						System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
							position[0] = id;
							position[1] = board.gettiles(id).getx();
							position[2] = board.gettiles(id).gety();
							position[3] = -1;
						}
				}
				else {
					System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
					position[0] = id;
					position[1] = board.gettiles(id).getx();
					position[2] = board.gettiles(id).gety();
					position[3] = -1;
				}
				break;
			
				
			case 1: // If dice = 1 player gets to move right 
				if(id % board.getN() < board.getN()-1 ) // just an extra check that by the dice is doesnt get out of bounds
				{			
					if(board.gettiles(id).getright() == false)
					{
						position[0] = id + 1;
						position[1] = board.gettiles(position[0]).getx();
						position[2] = board.gettiles(position[0]).gety();
						for(int i = 0; i <board.getS(); i++)
						{
							if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0) 
							{
								position[3] = board.getsupplies(i).getsupplyId();
								board.getsupplies(i).setx(-1);
								board.getsupplies(i).sety(-1);
								board.getsupplies(i).setsupplyTileId(-1);
								System.out.println("Well done!!! You earned a trophy!!!");
								score++;
								System.out.println("Your score now is " + score +"!!! " + "You need " + (4-score) + " more torphies to win");
							}
							else position[3] = -1;
						}
					}
					else {
						System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
						position[0] = id;
						position[1] = board.gettiles(id).getx();
						position[2] = board.gettiles(id).gety();
						position[3] = -1;
					}
				}
				else 
				{
					System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
					position[0] = id;
					position[1] = board.gettiles(id).getx();
					position[2] = board.gettiles(id).gety();
					position[3] = -1;
				}
				break;
				
			case 2: // If dice = 2 player gets to move downwards 
				if(id > board.getN()-1)
				{						// just an extra check that by the dice is doesnt get out of bounds
					if(board.gettiles(id).getdown() == false)
					{
						position[0] = id - board.getN();
						position[1] = board.gettiles(position[0]).getx();
						position[2] = board.gettiles(position[0]).gety();
						for(int i = 0; i < board.getS(); i++)
						{
							if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0) 
							{
								position[3] = board.getsupplies(i).getsupplyId();
								board.getsupplies(i).setx(0);
								board.getsupplies(i).sety(0);
								board.getsupplies(i).setsupplyTileId(-1);
								System.out.println("Well done!!! You earned a trophy!!!");
								score++;
								System.out.println("Your score now is " + score +"!!! " + "You need " + (4-score) + " more torphies to win");
							}
							else position[3] = -1;
						}
					}
					else {
						System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
						position[0] = id;
						position[1] = board.gettiles(id).getx();
						position[2] = board.gettiles(id).gety();
						position[3] = -1;
					}
				}
				else {
					System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
					position[0] = id;
					position[1] = board.gettiles(id).getx();
					position[2] = board.gettiles(id).gety();
					position[3] = -1;
				}
				break;
			
			case 3: // If dice = 3 player gets to move left
				if(id % board.getN() > 0)	// just an extra check that by the dice is does not get out of bounds
				{			
					if(board.gettiles(id).getleft() == false)
					{
						position[0] = id - 1;
						position[1] = board.gettiles(position[0]).getx();
						position[2] = board.gettiles(position[0]).gety();
						for(int i = 0; i < board.getS(); i++)
						{
							if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0)
							{
								position[3] = board.getsupplies(i).getsupplyId();
								board.getsupplies(i).setx(-1);
								board.getsupplies(i).sety(-1);
								board.getsupplies(i).setsupplyTileId(-1);
								System.out.println("Well done!!! You earned a trophy!!!");
								score++;
								System.out.println("Your score now is " + score +"!!! " + "You need " + (4-score) + " more torphies to win");
							}
							else position[3] = -1;
						}
					}
					else 
					{	
						System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
						position[0] = id;
						position[1] = board.gettiles(id).getx();
						position[2] = board.gettiles(id).gety();
						position[3] = -1;
					}
				}
				else
				{
					System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
					position[0] = id;
					position[1] = board.gettiles(id).getx();
					position[2] = board.gettiles(id).gety();
					position[3] = -1;
				}
				break;
		}
		return position;
	}	
}