public class Tile {
	
	/*Class that implements the Tiles of the board.*/
	
	private int tileId;
	private int x;
	private int y;
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	//Empty constructor: Set the values to zero
	public Tile() {
	
		tileId = 0;
		x = 0;
		y = 0;
		up = false;
		down = false;
		left = false;
		right = false;
		
	}
	//Constructor with arg a tileid,coordinates and four boolean values indicating the existance of a wall at each side of the tile: Sets the values of a new object equal to the given ones
	public Tile(int tileId, int x, int y, boolean up, boolean down, boolean right, boolean left){
		this.tileId = tileId;
		this.x = x;
		this.y = y;
		this.up = up;
		this.down = down;
		this.right = right;
		this.left = left;
	}
	
	//Constructor with arg a Tile Object: Sets the values of a new object equal to the given object
	public Tile(Tile A) {
		
		tileId = A.gettileId();
		x = A.getx();
		y = A.gety();
		up = A.getup();
		down = A.getdown();
		left = A.getleft();
		right = A.getright();
		
	}
	
	//Sets the tileId value of an object
	public void settileId(int tileId) {
		this.tileId = tileId;
	}
	
	//Returns the tileId value of an object
	public int gettileId() {
		return tileId;
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

	//Sets the up value of an object
	public void setup(boolean up) {
		this.up = up;
	}

	//Returns the up value of an object
	public boolean getup() {
		return up;
	}
	
	//Sets the y value of an object
	public void setdown(boolean down) {
		this.down = down;
	}

	//Returns the down value of an object
	public boolean getdown() {
		return down;
	}
	
	//Sets the y value of an object
	public void setleft(boolean left) {
		this.left = left;
	}
	
	//Returns the left value of an object
	public boolean getleft() {
		return left;
	}
	
	//Sets the y value of an object
	public void setright(boolean right) {
		this.right = right;
	}
	
	//Returns the right value of an object
	public boolean getright() {
		return right;
	}


}
