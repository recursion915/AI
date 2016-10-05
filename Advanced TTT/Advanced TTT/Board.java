import java.util.ArrayList;

public class Board {

	private int size;
	private Player[] smallGrid;
	public Board(){
		size=10;
		smallGrid=new Player[10]; 
	}
	public void printsmallBoard(){
		for(int i=1; i<size;i++){
			if(smallGrid[i]==null){
				System.out.print("- ");
			}
			else{
				System.out.print(smallGrid[i]+" ");
			}
			if((i)%3==0){
				System.out.println();
			}
		}
	}
	
	public boolean isEmpty(int n){
		return smallGrid[n]==null;
	}
	public boolean isFull(){
		boolean full=false;
		for(int i=1; i<size;i++){
			if(smallGrid[i]!=null){
				return false;
			}
		}
		return full;
	}
	public Board copy(){
		Board copyBoard=new Board();
		for(int i=0;i<size;i++){
			copyBoard.smallGrid[i]=smallGrid[i];
		}
		
		return copyBoard;
		
	}
	public Player getWinner(){
		Player winner=null;
		if(checkHorizontal()!=null){
			winner=checkHorizontal();
		}
		if(checkVertical()!=null){
			winner=checkVertical();
		}
		if(checkDi()!=null){
			winner=checkDi();
		}
		if(checkAnti()!=null){
			winner=checkAnti();
		}
		
		return winner;
	}
	protected int checkTwo(){
		int score=0;
		score=checkTwoH()+checkTwoV()+checkTwoD()+checkTwoA();
		
		return score;
	}
	//checkHoriziontal Score
	//if two X and one O in a row +100
	//if two O and one X a row -100
	//if two X in a row +300
	//if two O in a row -300
	protected int checkTwoH(){
		Player player=null;
		int score=0;
		
		for(int row=1;row<9;row=row+3){
			{
				int xCount=0;
				int oCount=0;
				int nCount=0;
				for(int j=0;j<3;j++){
					if(smallGrid[row+j]==Player.X){
						xCount++;
					}
					else if(smallGrid[row+j]==Player.O){
						oCount++;
					}
					else{
						nCount++;
					}
				}
				if(xCount==2&&oCount==1){
					score=score+100;
				}
				else if(xCount==2&&nCount==1){
					score=score+300;
				}
				else if(oCount==2&&xCount==1){
					score=score-100;
				}
				else if(oCount==2&&nCount==1){
					score=score-300;
				}
				else{
					score=score+0;
				}
			}
		}
		return score;
	}
	protected int checkTwoV(){
		Player player=null;
		int score=0;
		
		for(int col=1;col<4;col=col+1){
			{
				int xCount=0;
				int oCount=0;
				int nCount=0;
				for(int j=0;j<9;j=j+3){
					if(smallGrid[col+j]==Player.X){
						xCount++;
					}
					else if(smallGrid[col+j]==Player.O){
						oCount++;
					}
					else{
						nCount++;
					}
				}
				if(xCount==2&&oCount==1){
					score=score+100;
				}
				else if(xCount==2&&nCount==1){
					score=score+300;
				}
				else if(oCount==2&&xCount==1){
					score=score-100;
				}
				else if(oCount==2&&nCount==1){
					score=score-300;
				}
				else{
					score=score+0;
				}
			}
		}
		return score;
	}
	protected int checkTwoD(){
		Player player=null;
		int score=0;
		int xCount=0;
		int oCount=0;
		int nCount=0;
		for(int j=1;j<10;j=j+4){
				if(smallGrid[j]==Player.X){
						xCount++;
					}
				else if(smallGrid[j]==Player.O){
						oCount++;
					}
				else{
						nCount++;
					}
				
				if(xCount==2&&oCount==1){
					score=score+50;
				}
				else if(xCount==2&&nCount==1){
					score=score+300;
				}
				else if(oCount==2&&xCount==1){
					score=score-50;
				}
				else if(oCount==2&&nCount==1){
					score=score-100;
				}
				else{
					score=score+0;
				}
			
		}
		return score;
	}
	protected int checkTwoA(){
		Player player=null;
		int score=0;
		int xCount=0;
		int oCount=0;
		int nCount=0;
		for(int j=3;j<10;j=j+2){
				if(smallGrid[j]==Player.X){
						xCount++;
					}
				else if(smallGrid[j]==Player.O){
						oCount++;
					}
				else{
						nCount++;
					}
				
				if(xCount==2&&oCount==1){
					score=score+0;
				}
				else if(xCount==2&&nCount==1){
					score=score+300;
				}
				else if(oCount==2&&xCount==1){
					score=score-0;
				}
				else if(oCount==2&&nCount==1){
					score=score-300;
				}
				else{
					score=score+0;
				}
			
		}
		return score;
	}
	private Player checkHorizontal(){
		Player winner=null;
		for(int row=1;row<9;row=row+3){
			if(smallGrid[row]!=null){
				if(smallGrid[row+1]==smallGrid[row+2]&&smallGrid[row]==smallGrid[row+1]){
					return smallGrid[row];
				}
			}
		}
		return winner;
	}
	private Player checkVertical(){
		Player winner=null;
		for(int col=1;col<4;col=col+1){
			if(smallGrid[col]!=null){
				if(smallGrid[col+3]==smallGrid[col+6]&&smallGrid[col]==smallGrid[col+3]){
					return smallGrid[col];
				}
			}
		}
		return winner;
	}
	private Player checkDi(){
		Player winner=null;
		
			if(smallGrid[1]!=null){
				if(smallGrid[5]==smallGrid[9]&&smallGrid[5]==smallGrid[1]){
					return smallGrid[1];
				}
			
		}
		return winner;
	}
	private Player checkAnti(){
		Player winner=null;
		
			if(smallGrid[3]!=null){
				if(smallGrid[3]==smallGrid[7]&&smallGrid[5]==smallGrid[3]){
					return smallGrid[3];
				}
			
		}
		return winner;
	}
	public void apply(Move move){
		if(move.getPosition()<1||move.getPosition()>9){
			System.out.println("have to be 1-9");
		}
		else if(!isEmpty(move.getPosition())){
			System.out.println(move+"is occupied");
		}
		
		else {
			smallGrid[move.getPosition()]=move.getCurrentPlayer();
		}	
	}
	
	public ArrayList <Move>getPossibleMoves(Player player){
		ArrayList <Move> allmoves=new ArrayList<Move>();
		for(int i=1;i<10;i++){
			if(isEmpty(i)){
				allmoves.add(new Move(player,i));
			}
		}
		return allmoves;
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
	public void printRow(int row){
		if(row==1){
			for(int i=1; i<4;i++){
				if(smallGrid[i]==null){
					System.out.print("- ");
				}
				else{
					System.out.print(smallGrid[i]+" ");
				}
			}
		}
		else if(row==2){
			for(int i=4; i<7;i++){
				if(smallGrid[i]==null){
					System.out.print("- ");
				}
				else{
					System.out.print(smallGrid[i]+" ");
				}
			}
			
		}
		else{
			for(int i=7; i<10;i++){
				if(smallGrid[i]==null){
					System.out.print("- ");
				}
				else{
					System.out.print(smallGrid[i]+" ");
				}

			}
			
		}
	}
	//test
	public static void main(String[]args){
		Board test=new Board();
//		test.smallGrid[7]=Player.X;
//		test.smallGrid[5]=Player.X;
//		test.smallGrid[6]=Player.O;
//		test.smallGrid[1]=Player.O;
//		test.smallGrid[4]=Player.O;
//		test.smallGrid[9]=Player.X;
//		test.smallGrid[3]=Player.X;
		test.apply(new Move(Player.X,3));
		test.apply(new Move(Player.O,2));
		test.apply(new Move(Player.O,1));
		test.apply(new Move(Player.X,4));
		test.apply(new Move(Player.O,5));
		test.apply(new Move(Player.X,6));
		test.apply(new Move(Player.X,7));
		test.printsmallBoard();
		System.out.println("Hscore is"+test.checkTwoH());
		System.out.println("Vscore is"+test.checkTwoV());
		System.out.println("Dscore is"+test.checkTwoD());
		System.out.println("Ascore is"+test.checkTwoA());
//		System.out.println("horizontal winner is "+test.checkHorizontal());
//		System.out.println("vertical winner is "+test.checkVertical());
//		System.out.println("Di winner is "+test.checkDi());
//		System.out.println("anti winner is "+test.checkAnti());
		System.out.println("total winnner "+test.getWinner());
		System.out.println("small grid is full "+test.isFull());
		System.out.println("game is over "+test.isOver());
	}
}
