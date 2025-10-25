/* Liaropoulou Kleopatra 10066 liaropou@ece.auth.gr*/
/* Papakostas Konstanitnos 10127 kdpapako@ece.auth.gr*/

import java.util.*;

public class Game {
	/**
	 * Class that implements the game
	 */

	private static int round;	
	
	//Empty constructor: Set the values to zero
	public Game() {
		round = 0;
	}
	
	/**
	 * 
	 * @param A another game to be
	 */
	public Game(Game A) {
		round = A.getround();
	}
	
	/**
	 * 
	 * @return the value of round
	 */
	public int getround() {
		return round;
	}
	
	//Sets the round value of an object
	public void setround(int round) {
		this.round = round;
	}
	
	public static void main(String[] args) {
		Board board= new Board(15, 4, 338); //We create a new Board with dimensions 15*15, 4 supplies and 338 walls
		board.createBoard();
		
		
		MinMaxPlayer theseus = new MinMaxPlayer(0, "Theseus", board, 0, 0, 0); // We initialize the two players of the game
		HeuristicPlayer minotaur = new HeuristicPlayer(1, "Minotaur", board, 0 ,board.getN()/2, board.getN()/2 , 1);
		int[] theseusarray = new int[4]; // An array that contains the returning data of the function move() for each player
		int[] minotaurarray = {board.getN()*board.getN()/2, board.getN()/2, board.getN()/2,0};
		String[][] representationofBoard = new String[2*board.getN()+1][board.getN()]; // An array that contains the returning data of the function getStringRepresentaion()
		
		
		int gameround = 0; //Initialization of rounds
		System.out.println("ROUND " + (++gameround));
		System.out.println("Initial state of the board");
		representationofBoard = board.getStringRepresentation(0, (board.getN()*board.getN())/2);
		for(int j = 0; j < 2*board.getN()+1 ; j++) {  //We print the board
			for(int i = 0; i < board.getN(); i++) {
				System.out.print(representationofBoard[j][i]);
				if(i == board.getN()-1) {
					System.out.print("\n");
				}
			}
		}
		
		
		System.out.println("Theseus' move:");
		System.out.println("");
		theseusarray = theseus.move(0,theseus.getNextMove(theseusarray[0], minotaurarray[0],gameround, 0,board),0);
		representationofBoard = board.getStringRepresentation(theseusarray[0], (board.getN()*board.getN())/2);
		for(int j = 0; j < 2*board.getN()+1 ; j++) {  //We print the board
			for(int i = 0; i < board.getN(); i++) {
				System.out.print(representationofBoard[j][i]);
				if(i == board.getN()-1) {
					System.out.print("\n");
				}
			}
		}
		
		
		System.out.println("Minotaur's move:");
		minotaurarray = minotaur.move((board.getN()*board.getN())/2,minotaur.getNextMove(theseusarray[0], (board.getN()*board.getN())/2, gameround, 1),1);
		System.out.println("");
		representationofBoard = board.getStringRepresentation(theseusarray[0], minotaurarray[0]);
		for(int j = 0; j < 2*board.getN()+1 ; j++) {  //We print the board
			for(int i = 0; i < board.getN(); i++) {
				System.out.print(representationofBoard[j][i]);
				if(i == board.getN()-1) {
					System.out.print("\n");
				}
			}
		}
		
		System.out.println("");
		
		
		
		while(theseus.getscore() < 4 && gameround<100 && !((theseusarray[1]==minotaurarray[1]) && (theseusarray[2] == minotaurarray[2]))) { // conditions under which the game ends
			System.out.println("ROUND " + (++gameround));
			System.out.println("");
			System.out.println("Theseus' move:");
			System.out.println("");
			theseusarray = theseus.move(theseusarray[0],theseus.getNextMove(theseusarray[0], minotaurarray[0], gameround-1, 0, board),0);
			representationofBoard = board.getStringRepresentation(theseusarray[0], minotaurarray[0]);    ///The Game(each round)
			for(int j = 0; j < 2*board.getN()+1 ; j++) {  //We print the board
				for(int i = 0; i < board.getN(); i++) {
					System.out.print(representationofBoard[j][i]);
					if(i == board.getN()-1) {
						System.out.print("\n");
					}
				}
			}
			
			System.out.println("");
			
			
			
			System.out.println("Minotaur's move:");
			System.out.println("");
			minotaurarray = minotaur.move(minotaurarray[0],minotaur.getNextMove(theseusarray[0], minotaurarray[0], gameround-1, 1),1);
			representationofBoard = board.getStringRepresentation(theseusarray[0], minotaurarray[0]);
			for(int j = 0; j < 2*board.getN()+1 ; j++) {  //We print the board
				for(int i = 0; i < board.getN(); i++) {
					System.out.print(representationofBoard[j][i]);
					if(i == board.getN()-1) {
						System.out.print("\n");
					}
				}
			}
			System.out.println("");
		
		}
		if(theseus.getscore() == 4) {
			System.out.println("Theseus succeded to collect all 4 Supplies from the Maze");
			System.out.println("Theseus is the winner of the game");
		}
																						// End of the game
		else if(gameround == 100) {
			System.out.println("It's a tie  ");	
			}
		else if(theseusarray[1]==minotaurarray[1] && theseusarray[1] == minotaurarray[1]) {
			System.out.println("Oh no!! Minotaur killed Theseus");
			System.out.println("Minotaur is the winner of the game");
		}
		System.out.println("");
		System.out.println("");
		theseus.statistics(gameround);

  }
	
}	