import java.util.ArrayList;

//represent a state

public class BigState {

	private BigBoard board;
	private Player player;
	protected int boardNumber=1;
	protected int positionNumber=1;
	public BigState(BigBoard board, Player player,int boardNumber)
	{
		this.board=board;
		this.player=player;
		this.boardNumber=boardNumber;
	}
//	public State(int size)
//	{
//		this(new Board(size),Player.X);
//	}
	public BigState(){
		board=new BigBoard();
		this.player=Player.X;
	}
//	public State(int size){
//		board=new Board(9);
//		this.player=Player.X;
//	}
	public BigState(Player player){
		board=new BigBoard();
		this.player=player;
	}
	
	public BigBoard getBoard(){
		return board;
	}
	public Player getPlayer(){
		return player;
	}
	public Player getWinner(){
		return board.getWinner();
	}
	public boolean isOver(){
		
		return board.isOver();
	}
	public void printBigState(){
		board.printBigBoard();
	}
	public void applyState(Move move,int boardNumber){
		//keep track of last boardNumber
		this.boardNumber=boardNumber;
		this.positionNumber=move.getPosition();
		board.apply(move,boardNumber);
		player=player.nextPlayer();
		
	}
	//return the total store of Two X/O in a row
	public int checkTwoScore(){
		int score=0;
		for(int i=1;i<10;i++){
			score=score+board.bigGrid[i].checkTwo();
		}
		return score;
	}
	public BigState copyState(){
		return new BigState(board.copy(),player,boardNumber);
	}
	public ArrayList<Move>getPossibleMoves(){
		return board.getPossibleMoves(player,positionNumber);
	}
	
	//test
	public static void main(String[]args){
		BigState abc= new BigState();
	
		abc.applyState(new Move(Player.X,5), 8);
		abc.applyState(new Move(Player.O,3), 5);
		abc.applyState(new Move(Player.X,2), 5);
		abc.applyState(new Move(Player.X,5), 2);
		abc.applyState(new Move(Player.X,5), 5);
		abc.applyState(new Move(Player.X,6), 5);
		abc.applyState(new Move(Player.O,1), 7);
		abc.applyState(new Move(Player.O,9), 7);

//		abc.applyState(new Move(Player.X,4), 3);
		
		abc.printBigState();
		System.out.println("bigState score is "+abc.checkTwoScore());
		System.out.println("bN "+abc.boardNumber+"pn"+abc.positionNumber);
		ArrayList<Move>c=new ArrayList<Move>(abc.getPossibleMoves());
		for(int i=0;i<c.size();i++){
			System.out.println(c.get(i).toString());
		}
//		BigState def=abc.copyState();
//		def.printBigState();
//		def.applyState(new Move(Player.O,4), 9);
//		System.out.println();
//		def.printBigState();
	}
}
