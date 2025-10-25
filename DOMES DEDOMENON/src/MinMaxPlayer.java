
import java.util.ArrayList;
import java.util.Random;

public class MinMaxPlayer extends Player{

	//Variables of Heuristic class
	ArrayList<Integer[]> path;
	int minotaurDistance = 0;
	int supplyDistance = 0;
	int up = 0, down = 0, right = 0, left = 0;
	
	
	//Variables of MinMaxPlayer class which show if the move is available (if there are no walls)
	boolean availableUpTheseus = true , availableDownTheseus = true, availableRightTheseus= true, availableLeftTheseus= true;
	boolean availableUpMinotaur= true , availableDownMinotaur= true , availableRightMinotaur= true , availableLeftMinotaur= true ;
	
	public MinMaxPlayer() {
		super();
		path = new ArrayList<Integer[]>();
	}
	
	public MinMaxPlayer(int playerId, String name, Board board, int score, int x , int y) {
		super(playerId, name, board, score, x, y);
		path = new ArrayList<Integer[]>();
	}
	
	
	//The next three functions are copy pasted by the HeyristicPlayer class
	//The only change been made is (in the evaluate function) that if towards the direction of the move 
	//we come across a wall we renew the availableUp/Down/Left/Right of Theseus or Minotaur variable as false
	//Thus, in the making of createMySubtree and createOpponentSubtree we check if the move is available
	//and then create the child (in order to avoid unnecessary checks of best move)
	
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
		
		availableUpTheseus = true;				//due to the fact that this function is being called many times for check reasons we need to update the values again in order to not keep data from previous checks 
		availableDownTheseus = true;
		availableRightTheseus = true;
		availableLeftTheseus = true;
		availableUpMinotaur = true;
		availableDownMinotaur = true;
		availableRightMinotaur = true;
		availableLeftMinotaur = true;
		
		
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
					availableUpTheseus = false;
				}
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 2)
					{
						previousMove = 1;
					}
				}	
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
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
					availableRightTheseus = false;
				}
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 3)
					{
						previousMove = 1;
					}
				}
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
				
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
					availableDownTheseus = false;
				}
				
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 0)
					{
						previousMove = 1;
					}
				}
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
				
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
					availableLeftTheseus = false;
				}
				if(gameround > 1) {
					if(path.get(gameround-1)[0] == 1)
					{
						previousMove = 1;
					}
				}
				evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
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
						availableUpMinotaur = false;
					}
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 2)
						{
							previousMove = 1;
						}
					}	
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
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
						availableRightMinotaur = false;
					}
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 3)
						{
							previousMove = 1;
						}
					}
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
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
						availableDownMinotaur = false;
					}
					
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 0)
						{
							previousMove = 1;
						}
					}
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
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
						availableLeftMinotaur = false;
					}
					if(gameround > 1) {
						if(path.get(gameround-1)[0] == 1)
						{
							previousMove = 1;
						}
					}
					evaluation = goalFunction(NearSupplies,OpponentDistance, previousMove,player); // here we get the actual evaluation based on the parameters we renewed above
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
	public int getNextMove(int TheseusTile, int MinotaurTile, int gameround, int player, Board board)
	{

		int newTheseusTile = TheseusTile;
						
		
		Node root = new Node();
		root.setBoard(board);
		createMySubtree(TheseusTile, MinotaurTile, root, 1, gameround);
		int a = root.children.get(chooseMinMaxMove(root)).getNodeMove(2);
		
				
		
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
        
        
         
        return  a;			//optimum direction to move
	}
	
	
//-----------------------------------------------------------------------------------------------------------------------------------
	
	
	/**This is the function that creates a tree that includes all the available moves that Theseus can do
	 * 
	 * @param currentPos				the current position of Theseus (tileId)
	 * @param opponentCurrentPos		the current position of Minotaur (tileId)
	 * @param root						the root Node of the subtree that is about to be made
	 * @param depth						the depth of the subtree that is about to be made
	 * @param gameround					the current gameround
	 */
	@SuppressWarnings("unchecked")
	void createMySubtree(int currentPos,int opponentCurrentPos, Node root, int depth, int gameround) {
		
		double evaluation;
		
		evaluation = evaluate(currentPos, 0, opponentCurrentPos, gameround, 0);
		if(availableUpTheseus) {
			Node  up = new Node(root, depth, evaluation, root.getboard());
  			up.setNodeMove(0, (currentPos + board.getN())%board.getN());
  			up.setNodeMove(1, (currentPos + board.getN())/board.getN());
  			up.setNodeMove(2, 0);
			root.getChildren().add(up);
			createOpponentSubtree(opponentCurrentPos, currentPos, up, depth+1, gameround, up.getNodeEvaluation());
			
		}
		
		evaluation = evaluate(currentPos, 1, opponentCurrentPos, gameround, 0);
		if(availableRightTheseus) {
			Node  right = new Node(root, depth, evaluation, root.getboard());
			right.setNodeMove(0, (currentPos + 1)%board.getN());
  			right.setNodeMove(1, (currentPos + 1)/board.getN());
  			right.setNodeMove(2, 1);
			root.getChildren().add(right);
			createOpponentSubtree(opponentCurrentPos, currentPos, right, depth+1, gameround, right.getNodeEvaluation());
			
		}
		
		evaluation = evaluate(currentPos, 2, opponentCurrentPos, gameround, 0);
		if(availableDownTheseus) {
			if((currentPos - board.getN())/board.getN()>=0){
			Node  down = new Node(root, depth, evaluation, root.getboard());
			down.setNodeMove(0, (currentPos - board.getN())%board.getN());
  			down.setNodeMove(1, (currentPos - board.getN())/board.getN());
  			down.setNodeMove(2, 2);
			root.getChildren().add(down);
			createOpponentSubtree(opponentCurrentPos, currentPos, down, depth+1, gameround, down.getNodeEvaluation());
			}
		}
		
		evaluation = evaluate(currentPos, 3, opponentCurrentPos, gameround, 0);
		if(availableLeftTheseus) {
			Node  left = new Node(root, depth, evaluation, root.getboard());
			left.setNodeMove(0, (currentPos - 1)%board.getN());
  			left.setNodeMove(1, (currentPos - 1)/board.getN());
  			left.setNodeMove(2, 3);
			root.getChildren().add(left);
			createOpponentSubtree(opponentCurrentPos, currentPos, left, depth+1, gameround, left.getNodeEvaluation());
		}
		
		
	}

	/**This is the function that creates a tree that includes all the available moves that Minotaur can do
	 * 
	 * @param currentPos			the current position of Minotaur (tileId)
	 * @param opponentCurrentPos	the current position of Theseus (tileId)
	 * @param parent				the parent of the subtree that is about to be made
	 * @param depth					the depth of the subtree that is about to be made
	 * @param gameround				the current gameround
	 * @param parentEval			the evaluation of the move of the parent
	 */
	@SuppressWarnings("unchecked")
	void createOpponentSubtree(int currentPos,int opponentCurrentPos, Node parent, int depth, int gameround, double parentEval) {
		
		int[] information = parent.getNodeMove();
		switch(information[2])			
	    {
	       	case 0: 
	       		if((opponentCurrentPos+ board.getN())/(board.getN()) < (board.getN()))
	       			opponentCurrentPos += board.getN();
	       		break;
	       	case 1: 
	       		if((opponentCurrentPos + 1)%(board.getN()) < (board.getN()))
	       			opponentCurrentPos += 1;
       			break;		        
	       	case 2: 
	       		if((opponentCurrentPos - board.getN())/(board.getN()) >= 0)
	       			opponentCurrentPos -= board.getN();
	       		break;
	       	case 3: 
	       		if((opponentCurrentPos - 1)%(board.getN()) >= 0)
	       			opponentCurrentPos -= 1;
	       		break;
	     }
		
		double evaluation;
		
		
		evaluation = evaluate(opponentCurrentPos, 0, currentPos , gameround, 1);
		if(availableUpMinotaur) {
			Node  up = new Node(parent, depth, parentEval - evaluation, parent.getboard());
  			parent.getChildren().add(up);
		}
		
		evaluation = evaluate(opponentCurrentPos, 1, currentPos , gameround, 1);
		if(availableRightMinotaur) {
			Node  right = new Node(parent, depth, parentEval - evaluation, parent.getboard());
  			parent.getChildren().add(right);
		}
		
   		evaluation = evaluate(opponentCurrentPos, 2, currentPos , gameround, 1);
		if(availableDownMinotaur) {
			Node  down = new Node(parent, depth, parentEval - evaluation, parent.getboard());
  			parent.getChildren().add(down);
		}

   		evaluation = evaluate(opponentCurrentPos, 3, currentPos , gameround, 1);
		if(availableLeftMinotaur) {
			Node  left = new Node(parent, depth, parentEval - evaluation, parent.getboard());
  			parent.getChildren().add(left);
		}
		
		
		//we register the array bestMove with the evaluation of every child in order to sort them and keep the minimum
				Node[] bestMove = new Node[parent.getChildren().size()];			//na vevaiothoume oti  doulevei allios na adigrafoun  ola ta paiia se pinaka
				if(parent.getChildren().size()>0) {
					for(int i = 0; i < parent.getChildren().size(); i++)
					{
						bestMove[i] = new Node((Node)parent.getChildren().get(i));
					}
				}
					
				//bubble sort of the childrenEvaluations so as to take the minimum of them 
				double temp;
				for(int i = 0; i < parent.getChildren().size(); i++)							
				{
					for(int j = parent.getChildren().size()-1; j > i; j--)
					{
						if(bestMove[j].getNodeEvaluation() < bestMove[j-1].getNodeEvaluation())
						{
							temp = bestMove[j].getNodeEvaluation();
							bestMove[j].setNodeEvaluation(bestMove[j-1].getNodeEvaluation());
							bestMove[j-1].setNodeEvaluation(temp);
						}
					}
				}
				
			parent.setNodeEvaluation(bestMove[0].getNodeEvaluation());
			
	}
				
				
		
		

	
	
	public int chooseMinMaxMove(Node root) {
		
		
		//Node[] bestMove = (Node[])root.getChildren().toArray();			//na vevaiothoume oti  doulevei allios na adigrafoun  ola ta paiia se pinaka
		
		//we register the array bestMove with the evaluation of every child in order to sort them and keep the minimum
				Node[] bestMove = new Node[root.getChildren().size()];			//na vevaiothoume oti  doulevei allios na adigrafoun  ola ta paiia se pinaka
				for(int i = 0; i < root.getChildren().size(); i++)
				{
					bestMove[i] = new Node((Node)root.children.get(i));
				}
		
			//bubblesort of the childrenEvaluations so as to take the minimum of them 
			double temp;
			for(int i = 0; i < root.getChildren().size(); i++)							
			{
				for(int j = root.getChildren().size()-1; j > i; j--)
				{
					if(bestMove[j].getNodeEvaluation() < bestMove[j-1].getNodeEvaluation())
					{
						temp = bestMove[j].getNodeEvaluation();
						bestMove[j].setNodeEvaluation(bestMove[j-1].getNodeEvaluation());
						bestMove[j-1].setNodeEvaluation(temp);
					}
				}
			}
			
			
			int [] nodes = new int[4];			// aksiologiseis
			int counter = 0;
			 
			
			for(int k = 0; k < root.children.size(); k++) {
				if(((Node)root.children.get(k)).getNodeEvaluation() == bestMove[root.children.size() - 1].getNodeEvaluation() ) {
					nodes[counter++] = k;
				}
			}
			int choice;
			if(counter > 0) {
			Random rand = new Random();			// then randomly we pick an index (which is defined by parameter counter) of the "moves" array 
			choice = rand.nextInt(counter);
			}
			else choice = 0;
			return nodes[choice];
			
						
			//bestMove[root.getNodeMove()].getNodeEvaluation());
			
		
	}
	
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
								board.getsupplies(i).setsupplyTileId(-1000);
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
								board.getsupplies(i).setsupplyTileId(-1000);
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
								board.getsupplies(i).setsupplyTileId(-1000);
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
								board.getsupplies(i).setsupplyTileId(-1000);
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
