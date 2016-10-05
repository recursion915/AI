
public class Move {

	private Player player;
	private int x,y;
	
	public Move(Player player, int x, int y){
		this.player=player;
		this.x=x;
		this.y=y;
	}
	public Player getCurrentPlayer(){
		return player;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY(){
		return y;
	}
	//toString test
	public String toString(){
		
		return "Player."+player+", "+"moved to "+x+", "+y;
	}
	
	
}
