import java.util.Random;


public class Player {
	
	/*Class that implements a Player of the game.*/
	
	 int playerId;
	 String name;
	 Board board;
	 int score;
	 int x;
	 int y;

	//Empty constructor: Sets the values to zero
	public Player() {
		playerId = 0;
		name = "";
		board = new Board() ;
		score = 0;
		x = 0;
		y = 0;
	}
	
	//Constructor with arg a Playerid,a name,a board, a score and coordinates: Sets the values of a new object equal to the given ones
	public Player (int playerId, String name,Board board ,int score, int x, int y) {
		this.playerId = playerId;
		this.name = name;
		this.board = board;
		this.score = score;
		this.x = x;
		this.y = y;
	}
	
	//Constructor with arg a Player Object: Sets the values of a new object equal to the given object
	public Player (Player A) {
		playerId = A.getplayerId();
		name = A.getname();
		board = A.getboard();
		score = A.getscore();
		x = A.getx();
		y = A.gety();
	}
	
	//Sets the playerId value of an object
	public void setplayerId(int playerId) {
		this.playerId = playerId;
	}
	
	//Returns the playerId value of an object
	public int getplayerId() {
		return playerId;
	}
	
	//Sets the name value of an object
	public void setname(String name) {
		this.name = name;
	}
	
	//Returns the name value of an object
	public String getname() {
		return name;		
	}
	
	//Sets the board value of an object
	public void setboard(Board board) {
		this.board = board;
	}
	
	//Returns the board value of an object
	public Board getboard() {
		return board;
	}
	
	//Sets the score value of an object
	public void setscore(int score) {
		this.score = score;
	}
	
	//Returns the score value of an object
	public int getscore() {
		return score;
	}
	
	//Sets the x value of an object
	public void setx(int x) {
		this.x = x;
	}
	
	//Returns the x value of an object
	public int getx() {
		return x;
	}
	
	//Sets the y value of an object
	public void sety(int y) {
		this.y = y;
	}
	
	//Returns the y value of an object
	public int gety() {
		return y;
	}
	
	/*Returns an array with four elements based on player's move: 
	The new TileId and the new Coordinates(based on his new position) and the Supply that he collected
	the value dice is a representation of a real dice which indicates players' next move
	the position array is the returning value of the function: contains the data we want to return*/ 
	public int[] move(int id) {
		int dice;
		Random rand = new Random();
		dice = rand.nextInt(4);
		int[] position = new int[4];
		switch (dice) {
		
			case 0: // If dice = 0 player gets to move upwards 
				if(id < board.getN()*board.getN() - board.getN()) {		// just an extra check that by the dice is doesnt get out of bounds
				if(board.gettiles(id).getup() == false){
					position[0] = id + board.getN();
					position[1] = board.gettiles(position[0]).getx();
					position[2] = board.gettiles(position[0]).gety();
					for(int i = 0; i < board.getS(); i++) {
						if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0) {
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
				if(id % board.getN() < board.getN()-1 ) {			// just an extra check that by the dice is doesnt get out of bounds
				if(board.gettiles(id).getright() == false){
					position[0] = id + 1;
					position[1] = board.gettiles(position[0]).getx();
					position[2] = board.gettiles(position[0]).gety();
					for(int i = 0; i <board.getS(); i++) {
						if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0) {
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
				else {
					System.out.println("Oh no! You came across a wall! YOU CANT MOVE!");
					position[0] = id;
					position[1] = board.gettiles(id).getx();
					position[2] = board.gettiles(id).gety();
					position[3] = -1;
			}
				break;
				
			case 2: // If dice = 2 player gets to move downwards 
				if(id > board.getN()-1) {						// just an extra check that by the dice is doesnt get out of bounds
				if(board.gettiles(id).getdown() == false){
					position[0] = id - board.getN();
					position[1] = board.gettiles(position[0]).getx();
					position[2] = board.gettiles(position[0]).gety();
					for(int i = 0; i < board.getS(); i++) {
						if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0) {
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
				if(id % board.getN() > 0) {			// just an extra check that by the dice is does not get out of bounds
				if(board.gettiles(id).getleft() == false){
					position[0] = id - 1;
					position[1] = board.gettiles(position[0]).getx();
					position[2] = board.gettiles(position[0]).gety();
					for(int i = 0; i < board.getS(); i++) {
						if(board.getsupplies(i).getsupplyTileId() == position[0] && playerId == 0) {
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
				else {
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