import java.util.ArrayList;

public class Board {

	protected int size;
	private Player[][] board;

	
	//inialize the constructor with default 3 
	public Board(){
		size=3;
		board= new Player[3][3];
		newGame();
	}
	//overload
	public Board(int n){
		size=n;
		board=new Player[n][n];
		newGame();
	}
	
	public void printStatus(){
		System.out.println("Game Board is: ");
		for(int i=0; i<board.length;i++)
		{
			for(int j=0; j<board.length;j++)
			{
				if(board[i][j]==null)
				{
					System.out.print("- ");
				}else
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
	}

	//re-intialize the game
	public void newGame(){
		for(int i=0; i<board.length;i++)
		{
			for(int j=0; j<board.length;j++)
			{
				board[i][j]=null;
			}
		}
	}
	public ArrayList <Move>getPossibleMoves(Player player){
		ArrayList <Move> allmoves=new ArrayList<Move>();
		for(int x=0;x<size;x++){
			for(int y=0; y<size; y++){
				if(isEmpty(x,y))
				{
					allmoves.add(new Move(player, x,y));
				}
			}
		}
		return allmoves;
	}
	public void makeMove(Move move){
		int x=move.getX();
		int y=move.getY();
		//check boundary
		if(x<0||x>=size||y<0||y>=size){
			System.err.println("IQ out of bounds exception");
		}
		else if(!isEmpty(x,y)){
			System.err.println("Location has been occupied");
		}
		else{
			board[x][y]=move.getCurrentPlayer();
		}
//		//print status
//				printStatus();
//		//print winner, restart the game
//		if(getWinner()!=null)
//		{
//			System.out.println("We have a winner: "+getWinner());
////			newGame();
//		}
		
	}
	public boolean isOutBound(int x, int y){
		if(x<0||x>=size||y<0||y>=size){
			return true;
		}
		return false;
	}
	//check a board is full
	public boolean isFull()
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(board[i][j]==null)
					return false;
			}
		}
		return true;
	}
	public boolean isEmpty(int x, int y){
		return board[x][y]==null;
	}
	public boolean isTerminated()
	{
		if(getWinner()!=null)
		{
			return true;
		}
		else{
			return isFull();
		}
	}
	
	public Board copyBoard(){
		Board dupBoard=new Board(size);
		
		for(int x=0;x<size;x++){
			for(int y=0;y<size;y++)
			{
				dupBoard.board[x][y]=board[x][y];
			}
		}
		return dupBoard;
	}
	

	public Player getWinner(){
		Player winner=null;
		if(checkHorizontal()!=null){
			winner=checkHorizontal();
		}
		if(checkVertical()!=null){
			winner=checkVertical();
		}
		if(checkDiagonal()!=null){
			winner=checkDiagonal();
		}
		if(checkAnti()!=null){
			winner=checkAnti();
		}
//		System.out.println(winner);
		return winner;
	}
	public Player checkHorizontal(){
		Player winner=null;
		for(int x=0;x<size;x++){
			//pretend the first spot is the winner
//			System.out.println(winner);
			//if winner is null, do not need to check this column
			if(board[x][0]!=null){
				winner=board[x][0];
				//this for loop checks one row
				for(int y=0; y<size;y++)
				{
					if(board[x][y]!=winner){
						winner=null;
						break;
						
					}
						
					//if repeated n times, then we have a winner
					else if(y==size-1)
						return winner;
				}
			}
		}
		return winner;
		
	}
	public Player checkVertical(){
		Player winner=null;
		for(int y=0;y<size;y++){
			//pretend the first spot is the winner
			
			if(board[0][y]!=null){
			//this for loop checks one row
				winner=board[0][y];
				for(int x=0; x<size;x++)
				{	
	//				if(board[x][y]==null)
	//					break;
					if(board[x][y]!=winner){
						winner=null;
						break;}
					//if repeated n times, then we have a winner
					else if(x==size-1)
	//					System.out.println(x);
						return winner;
					
				}
			}
			
		}
		return winner;
		
	}
	public Player checkDiagonal(){
		Player winner=board[0][0];
		for(int i=0;i<size;i++)
		{
			if(board[i][i]==null)
				return null;
			else if(!board[i][i].equals(winner))
				return null;
			else if(i==size-1)
				return winner;
		}

		return winner;
		
	}
	public Player checkAnti(){
//		int x=move.getX();
//		int y=move.getY();
		Player winner=board[0][size-1];
		for(int i=0;i<size;i++)
		{
			if(board[i][size-1-i]==null)
				return null;
			else if(!board[i][size-1-i].equals(winner))
				return null;
			else if(i==size-1)
				return winner;
		}
		return winner;
	}
//	public boolean checkVertical(Move move){
//		int x=move.getX();
//		int y=move.getY();
//		for(int i=0;i<size;i++)
//		{
//			if(!board[i][y].equals(move.getCurrentPlayer()))
//				return false;
//			if(i==size-1)
//				return true;
//		}
//		return false;
//	}
//	public boolean checkDiagonal(Move move){
////		int x=move.getX();
////		int y=move.getY();
//		for(int i=0;i<size;i++)
//		{
//			if(!board[i][i].equals(move.getCurrentPlayer()))
//				return false;
//			if(i==size-1)
//				return true;
//		}
//		return false;
//	}

//	public boolean checkAnti(Move move){
////		int x=move.getX();
////		int y=move.getY();
//		for(int i=0;i<size;i++)
//		{
//			if(!board[i][size-1-i].equals(move.getCurrentPlayer()))
//				return false;
//			if(i==size-1)
//				return true;
//		}
//		return false;
//	}
//	public Player getWinner(Move move)
//	{
//		Player winner=move.getCurrentPlayer();
//		if(checkHorizontal(move)||checkVertical(move)||checkDiagonal(move)||checkAnti(move))
//			return winner;
//		else
//			return null;
//	}
	
	// for tic tac toe, has to be 3X or 3O 
	// for n case, has to be nX or nO 
//	public boolean checkHorizontal(Move move)
//	{
//		int x=move.getX();
//		int y=move.getY();
//		for(int i=0;i<size;i++)
//		{
//			if(!board[x][i].equals(move.getCurrentPlayer()))
//				return false;
//			if(i==size-1)
//				return true;
//		}
//		return false;
//	}
}
