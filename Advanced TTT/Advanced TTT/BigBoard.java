import java.util.ArrayList;

public class BigBoard {
	private int size;
	protected Board[] bigGrid;
	
	public BigBoard(){
		size=10;
		bigGrid=new Board[10]; 
		//dont forget to inialize the small board
		for(int i=0;i<10;i++){
			bigGrid[i]=new Board();
		}
	}
	public void apply(Move move, int boardNumber){
			if(boardNumber<1||boardNumber>9){
				System.out.println("have to be 0-9");
			}
			else if(bigGrid[boardNumber].isFull()){
				System.out.println("hey, this board is full");
			}
			else {
				bigGrid[boardNumber].apply(move);
			}	
	}
	public BigBoard copy(){
		BigBoard copyBoard=new BigBoard();
		for(int i=0;i<size;i++){
			copyBoard.bigGrid[i]=bigGrid[i].copy();
		}
		return copyBoard;
	}
	public ArrayList <Move>getPossibleMoves(Player player,int boardNumber){
		ArrayList <Move> allmoves=new ArrayList<Move>(bigGrid[boardNumber].getPossibleMoves(player));
		return allmoves;
	}
	public Player getWinner(){
		Player winner=null;
		for(int i=1;i<size;i++){
			if(bigGrid[i].getWinner()!=null){
				return bigGrid[i].getWinner();
			}
		}
		return winner;
	}
	//if one of small grids is NOT full, the big grid is not full
	public boolean isFull(){
		for(int i=1;i<size;i++){
			if(!bigGrid[i].isFull()){
				return false;
			}
		}
		return true;
	}
	public boolean isOver(){
		if(getWinner()!=null)
		{
			return true;
		}
		else{
			return isFull();
		}
	}
	//lol...this printing method sucks; almost hard coded
	public void printBigBoard(){
		for(int row=1;row<4;row++){
			bigGrid[1].printRow(row);
			System.out.print("* ");
			bigGrid[2].printRow(row);
			System.out.print("* ");
			bigGrid[3].printRow(row);
			System.out.print("* ");
			System.out.println();
		}
		System.out.println("* * * * * * * * * * * *");
		for(int row=1;row<4;row++){
			bigGrid[4].printRow(row);
			System.out.print("* ");
			bigGrid[5].printRow(row);
			System.out.print("* ");
			bigGrid[6].printRow(row);
			System.out.print("* ");
			System.out.println();
		}
		System.out.println("* * * * * * * * * * * *");
		for(int row=1;row<4;row++){
			bigGrid[7].printRow(row);
			System.out.print("* ");
			bigGrid[8].printRow(row);
			System.out.print("* ");
			bigGrid[9].printRow(row);
			System.out.print("* ");
			System.out.println();
		}
	}
	//test
	public static void main(String[]args){
		BigBoard test=new BigBoard();
//		test.bigGrid[3].apply(new Move(Player.X,3));
//		test.bigGrid[7].apply(new Move(Player.O,9));
//		test.bigGrid[4].apply(new Move(Player.O,8));
		System.out.println(test.isFull());
		System.out.println(test.getWinner());
		test.printBigBoard();
	}
}
