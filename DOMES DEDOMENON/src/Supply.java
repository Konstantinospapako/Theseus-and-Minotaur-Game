

public class Supply {
	
	/*Class that implements the supplies of the game.*/
	
	private int x;
	private int y;
	private int supplyId;
	private int supplyTileId;
	
	//Empty constructor: Set the values to zero
	public Supply(){
		
		x = 0;
		y = 0;
		supplyId = 0;
		supplyTileId = 0;
		
	}
	
	//Constructor with arg a Supply Object: Set the values of a new object equal to the given object
	public Supply(Supply A){
		
		x = A.getx();
		y = A.gety();
		supplyId = A.getsupplyId();
		supplyTileId = A.getsupplyTileId();
		
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
	
	//Sets the supplyId value of an object
	public void setsupplyId(int supplyId) {
		this.supplyId = supplyId;
	}
	
	//Returns the supplyId value of an object
	public int getsupplyId() {
		return supplyId;
	}
	
	//Sets the supplyTileId value of an object
	public void setsupplyTileId(int supplyTileId) {
		this.supplyTileId = supplyTileId;
	}
	
	//Returns the x value of an object
	public int getsupplyTileId() {
		return supplyTileId;
	}
	
}