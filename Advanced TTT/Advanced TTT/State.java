import java.util.ArrayList;

//represent a state

public class State {

	private Board board;
	private Player player;
	public State(Board board, Player player)
	{
		this.board=board;
		this.player=player;
	}
//	public State(int size)
//	{
//		this(new Board(size),Player.X);
//	}
	public State(){
		board=new Board();
		this.player=Player.X;
	}
//	public State(int size){
//		board=new Board(9);
//		this.player=Player.X;
//	}
	public State(Player player){
		board=new Board();
		this.player=player;
	}
	
	public Board getBoard(){
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
	public void printState(){
		board.printsmallBoard();
	}
	public void applyState(Move move){
		board.apply(move);
		player=player.nextPlayer();
		
	}
	public State copyState(){
		return new State(board.copy(),player);
	}
	public ArrayList<Move>getPossibleMoves(){
		return board.getPossibleMoves(player);
	}
	
	
}
