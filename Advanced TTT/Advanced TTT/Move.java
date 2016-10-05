
public class Move {

	private Player player;
	private int position;
	
	public Move(Player player, int position){
		this.player=player;
		this.position=position;
	}
	public Player getCurrentPlayer(){
		return player;
	}
	
	public int getPosition()
	{
		return position;
	}

	//toString test
	public String toString(){
		
		return "Player."+player+", "+"moved to "+position;
	}
	
	
}
