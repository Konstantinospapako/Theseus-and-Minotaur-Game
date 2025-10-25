import java.util.ArrayList;

public class Node {

	private Node parent;
	 ArrayList<Node> children;
	private int nodeDepth ;
	private int[] nodeMove;
	private Board nodeBoard;
	private double nodeEvaluation;
	
	public Node() {
		
		parent = null;
		children = new ArrayList<Node>(1);
		nodeDepth = 0;
		nodeMove = null;
		nodeBoard = new Board();
		nodeEvaluation = 0;
		
	}
	
	public Node(Node parent, int nodeDepth, double nodeEvaluation,Board board) {
		
		this.parent = new Node(parent);
		children = new ArrayList<Node>(1);
		this.nodeDepth = nodeDepth;
		nodeMove = new int[3];
		nodeBoard = new Board(board);
		this.nodeEvaluation = nodeEvaluation;
		
	}
	
	@SuppressWarnings("unchecked")
	public Node(Node A) {
		
		parent = A.getParent();
		children = A.getChildren();
		nodeDepth = A.getNodeDepth();
		nodeMove = A.getNodeMove();
		nodeBoard = A.getboard();
		nodeEvaluation = A.getNodeEvaluation();
	}
	
	public Node getParent() {
		return parent;
	}
	
	public Board getboard() {
		return nodeBoard;
	}
	
	public int[] getNodeMove() {
		return nodeMove;
	}
	
	public ArrayList getChildren() {
		return children;
	}
	
	public void setNodeDepth(int nodeDepth) {
		this.nodeDepth = nodeDepth;
	}
	
	public int getNodeDepth() {
		return nodeDepth; 
	}
	
	public void setNodeEvaluation(double nodeEvaluation) {
		this.nodeEvaluation = nodeEvaluation;
	}
	
	public double getNodeEvaluation() {
		return nodeEvaluation;
	}
	
	public void setNodeMove(int i, int value) {
		for(int j = 0; j < 3; j++) {
			if(i == j) {
				nodeMove[j] = value;
			}
		}
	}
	public int getNodeMove(int i) {
		return nodeMove[i];
	}	
	
	public void setBoard(Board board) {
		nodeBoard = board;
	}
}
