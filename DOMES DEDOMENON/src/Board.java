import java.util.Random;

public class Board {
	
/*Class that initializes the board of the game*/
	private int N;
	private int S;
	private int W;
	private Tile[] tiles; 
	private Supply[] supplies;
	
	

	public Board() { 
	//empty constructor:sets values to zero and initializes arrays
		W = 0;
		S = 0;
		N = 0;
		tiles = new Tile[N*N];
		supplies = new Supply[S];
	}
	
	public Board(int N, int S, int W) {
	//constructor with arguments 
		
		this.N = N;
		this.S = S;
		this.W = W;
		tiles = new Tile[N*N];
		supplies = new Supply[S];
		
	}
	
	public Board(Board A) { 
	//constructor with argument a board object
		
		N = A.getN();
		S = A.getS();
		W = A.getW();
		tiles = new Tile[A.getN()*A.getN()];
		supplies = new Supply[A.getS()];
	}
	
	//Sets the N value of an object
	public void setN(int N) { 
		this.N = N;
	}

	//Returns the N value of an object
	public int getN() {
		return N;
	}
	
	//Sets the S value of an object
	public void setS(int S) {	
		this.S = S;
	}
	
	//Returns the S value of an object
	public int getS() {
		return S;
	}
	
	//Sets the W value of an object
	public void setW(int W) {	
		this.W = W;
	}
	
	//Returns the W value of an object
	public int getW() {
		return W;
	}
	
	
	//Returns the i-th element of the tiles array
	public Tile gettiles(int i) {
		return tiles[i];
	}
	

	//Returns the i-th element of the supplies array
	public Supply getsupplies(int i) {
		return supplies[i];
	}

	
	//----------------------------------------------------------------------------------------------------------------------------
	
	//a function in which every tile of the board is initialized 
	public void createTile() {
		for(int i = 0; i < N*N; i++)			//initializes the tiles array
			tiles[i]= new Tile();	
		for(int i = 0; i < N*N; i++) { 			//initializes the tiles array with x,y,tileId according to i
			tiles[i].settileId(i);
			tiles[i].setx(i%N);
			tiles[i].sety(i/N);
		}
		
		
		for(int i = 0; i < N*N; i++) {			//construction of outline of board
			if(tiles[i].getx() == 0) {
				tiles[i].setleft(true);
			}
			else if(tiles[i].getx() == N-1) {
				tiles[i].setright(true);
			}
			else if(tiles[i].gety() == 0) {		
				tiles[i].setdown(true);	
			}
			else if(tiles[i].gety() == N-1) {
				tiles[i].setup(true);
			}
		}
		
		
		int wallCount = (N*N*3 +1)/2 - 4*N;			//a variable which keeps the maximum number of walls that can be placed on the board
													// we subtracted 4*N due to the outline walls 
														
		
		int[] remainingWalls = new int [wallCount]; //an array that keeps the position of the walls that are left to
													//be placed
		int[] experimentalArray = new int [N*N];
											// The code below is used in order to avoid having the same tile ids in the Array 
											// by first filling the array with all tile ids except the corners and then shuffling them 
											// and getting the first that can fit in the formal array that has as many elements as the
											// the walls we are able to place in the board.It should be mentioned that by the time WallCount 
											// is the remaining num of walls, every chosen tile will contain only one wall
		int g = 0;
		for(int i = 0; i < N*N; i++) {
			
			if((i == 0) || (i == N*N-1) || (i == N-1) || (i == N*N-N) )		//we except the corners of the board from potential wall positions
				continue;
			
			experimentalArray[g] = i;		
			g++;									//an extra variable that increases only if the tileId is not a corner
		}											
		
		
		Random rand = new Random();
		int index,temp;								// temp-->the variable which swaps, index-->the index which we swap
		for(int i = N*N - 4 - 1; i >= 0; i--) {		//shuffle
			
			index = rand.nextInt(i+1);					
			temp = experimentalArray[index];
			experimentalArray[index] = experimentalArray[i];
			experimentalArray[i] = temp;
		}
		
	
		for (int i = 0; i < wallCount/2; i++) {
			remainingWalls[i] = experimentalArray[i];			
		}
		
		
		int temporary = 0;  												// bubblesort
	    for(int i = 0; i < wallCount/2; i++){  
	    	for(int j = 1; j < (wallCount/2 -i); j++){  
                if(remainingWalls[j-1] > remainingWalls[j]){   
                       temporary = remainingWalls[j-1];  
                       remainingWalls[j-1] = remainingWalls[j];  
                       remainingWalls[j] = temporary;  
               }  
                
	        }  
	    }  
	    
	    for(int i = 0; i < wallCount/2; i++) {			
	    	
	    		    	
	    	int up = tiles[remainingWalls[i]].getup() ? 1 : 0;					//an arithmetic representation of the booleans up,down,left,right so as to sum them up
    		int down = tiles[remainingWalls[i]].getdown() ? 1 : 0;
    		int left = tiles[remainingWalls[i]].getleft() ? 1 : 0;
    		int right = tiles[remainingWalls[i]].getright() ? 1 : 0;
    		if( up + down + left + right < 2 ) {
    			int num = 2 * (int) (Math.random() * 2) + 1;
    			
    			
    			if (num == 1) {													//sets the north wall
    				if(up == 1) {												//if there is already north wall
    					if(remainingWalls[i] +1 < N*N && tiles[remainingWalls[i]+1].gettileId() != N*N-1 && tiles[remainingWalls[i]+1].gettileId() != N*N-N*(N-1) -1) {
	    					int upNext = tiles[remainingWalls[i]+1].getup() ? 1 : 0;
	    		    		int downNext = tiles[remainingWalls[i]+1].getdown() ? 1 : 0;
	    		    		int leftNext = tiles[remainingWalls[i]+1].getleft() ? 1 : 0;
	    		    		int rightNext = tiles[remainingWalls[i]+1].getright() ? 1 : 0;
	    		    		if( upNext + downNext + leftNext + rightNext < 2 ) {
	    		    			tiles[remainingWalls[i]].setright(true);
	    		    			if (tiles[remainingWalls[i]].getx() < (N-1) ) {
	    	    					tiles [remainingWalls[i]+1].setleft(true);
	    		    			}
	    		    		}
    					}
    				}
    		    		
    				else {
    					if(remainingWalls[i] + N < N*N && tiles[remainingWalls[i]+N].gettileId() != N*N-1 && tiles[remainingWalls[i]+N].gettileId() != N*N-N) {
	    					int upNext2 = tiles[remainingWalls[i]+N].getup() ? 1 : 0;
	    		    		int downNext2 = tiles[remainingWalls[i]+N].getdown() ? 1 : 0;
	    		    		int leftNext2 = tiles[remainingWalls[i]+N].getleft() ? 1 : 0;
	    		    		int rightNext2 = tiles[remainingWalls[i]+N].getright() ? 1 : 0;
	    		    		if( (upNext2 + downNext2 + leftNext2 + rightNext2) < 2 ) {
	    		    			tiles[remainingWalls[i]].setup(true);
	    	    				if (tiles[remainingWalls[i]].gety() < (N-1) ) {
	    	    					tiles [remainingWalls[i]+N].setdown(true);
	    	    				}
	    		    		}
    					}
    				}
    				
    			}	
    			
    			if (num == 3) {																		//sets right wall
    				if(remainingWalls[i] + 1 < N*N && tiles[remainingWalls[i]+1].gettileId() != N*N-1 && tiles[remainingWalls[i]+1].gettileId() != N*N-N*(N-1) -1) {
	    				int upNext = tiles[remainingWalls[i]+1].getup() ? 1 : 0;
			    		int downNext = tiles[remainingWalls[i]+1].getdown() ? 1 : 0;
			    		int leftNext = tiles[remainingWalls[i]+1].getleft() ? 1 : 0;
			    		int rightNext = tiles[remainingWalls[i]+1].getright() ? 1 : 0;
			    		if( upNext + downNext + leftNext + rightNext < 2 ) {
			    			tiles[remainingWalls[i]].setright(true);
			    			if (tiles[remainingWalls[i]].getx() < (N-1) ) {
		    					tiles [remainingWalls[i]+1].setleft(true);
			    			}
			    		}
    				}
    				
		    		
    			} 
    		}
    	}
	}    		
	  
	//a function in which every supply of the game is initialized 
	public void createSupply() {
		Random rand = new Random();	
		int randomNum;
		
		for(int i = 0; i < S; i++) {					
			supplies[i] = new Supply();
		}

		for(int i = 0; i < S; i++) {					
			randomNum = rand.nextInt(N*N);
			if(randomNum != 0 && randomNum != N*N/2) {
				supplies[i].setsupplyTileId(randomNum);
				supplies[i].setx(randomNum%N);
				supplies[i].sety(randomNum/N);
				supplies[i].setsupplyId(i);
			}
			
			else { 
				i--;
			}
		}
		
	
	}
	
	public void createBoard() {		//initializes the complete board of the game
		createTile();
		createSupply();
		
	}
	
	
	
	
	public boolean hasSupply(int tileId) {				//a function which returns either there is a supply in the current tileId or not
		for(int k = 0; k < S ;k++) {
			 if(tileId == supplies[k].getsupplyTileId()) {
				 return true;
			 }
		}
		return false;
	}
	
	public int getSupply(int tileId) {					//a function that returns the supplyId in the current tileId
		int a = 0;
		for(int i = 0; i < S; i++) {
			if(tileId == supplies[i].getsupplyTileId()) {
				a = supplies[i].getsupplyId();
			}
		}
		return a;
	}
	
	
//----------------------------------------------------------------------------------------------------------
	
	
	//a functions that returns the complete 2 dimensional array(board) of the round (
	// including walls, supplies, minotaur, theseus
	
	public String[][] getStringRepresentation(int theseusTile, int minotaurTile) {
		String[][] representationArray = new String[2*N+1][N];
		int positionId;												// an expression that matches the current i,j to the respective tileId
		int supplyPosition = 0;										// indicated the id of the supply on the tile given
		for(int j = 0; j < 2*N ; j++) {
				if (j%2 == 0) {										//checks if it should print a +---+---+---+ kind of line
					for(int i = 0; i < N ;i++) {
						positionId = N*N - (j+1)*N + (j/2)*N + i;
						
						if(j==0 ) {						//checks if it is the top wall of the maze and then prints it
							if(i < N-1) {
								
								representationArray[j][i]= "+---";
							}
							else if(i == N-1) { 
								
								representationArray[j][i]= "+---+";
							}
						}
						else {										// if it is not top or bottom checks the boolean getup() function to print respectively 
							if(i < N-1) {
								if(tiles[positionId].getup()) {
									
									representationArray[j][i]= "+---";
									
								}
								else {
									
									representationArray[j][i]= "+   ";
								}
							}
							else if(i == N-1) {
								if(tiles[positionId].getup()) {
									
									representationArray[j][i]= "+---+";
									
								}
								else {
									
									representationArray[j][i]= "+   +";
								}
								
							}	
						}
						
					}
				}										//here is the ending of the representation of +---+---+---+ kind of lines
				
//---------------------------------------------------------------------------------------------------------------------				
				
				else {									//here begins the representation of |   | M |   | s1|   | kind of lines
					for(int i = 0; i < N ;i++) {
						positionId = N*N - (j+1)*N + ((j+1)*N)/2+ i;		//transition of the positionId(which represents the respective 
																			//tileId,in order to get data about that tile)	so as to fit for 
																			//the lines for which the j%2!=0 expression applies
						
						
						if(hasSupply(positionId)) {
							 supplyPosition = getSupply(positionId);
						 } 	
						
//-----------------------------------------------------------------------------------------------------------------
										
						
						if(i == 0) {						// after it prints left wall of maze it checks if...
							
							
							if(tiles[positionId].gettileId() == minotaurTile) { //there is minotaur in this tile.....
								
								if(tiles[positionId].getright()) {
									
									representationArray[j][i]= "| M |";
								}
								else {
									
									representationArray[j][i]= "| M  ";
								}
							}
							
							else if(tiles[positionId].gettileId() == theseusTile) {//if there is theseus in this tile
								
								if(tiles[positionId].getright()) {
									
									representationArray[j][i]= "| T |";
								}
								else {
									
									representationArray[j][i]= "| T  ";
								}
							}
							
							else if(hasSupply(positionId)) {						//if there is any supply in this tile
									
									String s = " s" + Integer.toString((supplyPosition +1));
										if(tiles[positionId].getright()) {
											
											representationArray[j][i]= "|" + s + "|";
										}
										else {
											
											representationArray[j][i]= "|" + s + " ";
										}
								}
	
							else {													//or if there is nothing at all in this tile
								
								if(tiles[positionId].getright()) {
									
									representationArray[j][i]= "|   |";
								}
								else {
									
									representationArray[j][i]= "|    ";
								}
							}	
						}
						
//--------------------------------------------------------------------------------------------------------------------------------------------------------					
						
						else if(i!=0 && i!=N-1) {				//for every tile except for the leftmost or the rightmost ones of the horizontal lines(i)
							
							if(tiles[positionId].gettileId() == minotaurTile) { //there is minotaur in this tile.....
								
								if(tiles[positionId].getright()) {
									
									representationArray[j][i]= " M |";
								}
								else {
									
									representationArray[j][i]= " M  ";
								}
							}
							
							else if(tiles[positionId].gettileId() == theseusTile) {//if there is theseus in this tile
								
								if(tiles[positionId].getright()) {
									
									representationArray[j][i]= " T |";
								}
								else {
									
									representationArray[j][i]= " T  ";
								}
							}
							
							else if(hasSupply(positionId)) {						//if there is any supply in this tile
									
									String s = " s" + Integer.toString((supplyPosition +1));
										if(tiles[positionId].getright()) {
											
											representationArray[j][i]= "" + s +"|";
										}
										else {
											
											representationArray[j][i]= "" + s + " ";
										}
								}
	
							else {													//or if there is nothing at all in this tile
								
								if(tiles[positionId].getright()) {
									
									representationArray[j][i]= "   |";
								}
								else {
									
									representationArray[j][i]= "    ";
								}
							}	
							
						}
						
//-------------------------------------------------------------------------------------------------------------------------------------------------------
				
		
						else if(i==N-1) {			//for the rightmost wall of the maze it checks if....
							
							
							if(tiles[positionId].gettileId() == minotaurTile) {		//there is minotaur in this tile.....
								
								representationArray[j][i]= " M |";
							}
							
							else if(tiles[positionId].gettileId() == theseusTile) {		//if there is theseus in this tile
								
								representationArray[j][i]= " T |";
							}
							
							else if(hasSupply(positionId)) {							//if there is any supply in this tile
									
									String s = " s" + Integer.toString((supplyPosition +1)) + "|";
									representationArray[j][i]= s;
							}
						
							
							else {														//or if there is nothing at all in this tile
								
								representationArray[j][i]= "   |";
							}	
							
							
								
							
						} 
						
//--------------------------------------------------------------------------------------------------------------------
						
					}//the bracket of the i for loop
				}//the bracket of else that prints the j%2!=0 lines
			}//the bracket of the j for loop


		for(int i = 0;i < N; i++) {							//prints the bottom wall...(cause we need to have 2*N+1 => 2*j+1 lines printed 
			if(i < N-1) {
				
				representationArray[2*N][i]= "+---";
			}
			else if(i == N-1) { 
				
				representationArray[2*N][i]= "+---+";
			}
		}
		


		

	return representationArray;
}
}