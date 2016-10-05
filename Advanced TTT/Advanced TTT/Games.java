import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Games {

	private BigState bigState;
	private Player humanPlayer;
//	private BigBoard gameGrid;
	private int boardNumber;
	private int positionNumber;
	private long currentTime,finishTime,elapsedTime;
	Scanner scan;
	public Games(){
		 scan=new Scanner(System.in);
		
	}
	public void play(){
	 boardNumber=-1;
		 positionNumber=-1;
		gameStart();
		gameLoop();
		gameOver();
	}
	public void gameStart(){
		humanPlayer=getHumanPlayer();
//		gameGrid=new BigBoard();
		bigState= new BigState();
	}
	public void gameLoop(){
		while(!bigState.isOver()){
			bigState.printBigState();
			Move move=getNextMove();
			
			if(!bigState.getBoard().bigGrid[boardNumber].isEmpty(move.getPosition())){
				System.out.println("Location has been occupied,remake your move:");
				move=getNextMove();
			}
			else if(bigState.getBoard().bigGrid[boardNumber].isFull()){
				boardNumber++;
			}
			
			else{
				bigState.applyState(move,boardNumber);
				System.out.println("now the player is:"+bigState.getPlayer());
				boardNumber=move.getPosition();
			}
			
			
			
		}
	}
	public void gameOver(){
		bigState.printBigState();
		if(bigState.getWinner()==humanPlayer)
		{
			System.out.println("Cannot believe u won, this won't happen twice ");
		}
		else if(bigState.getWinner()==humanPlayer.nextPlayer()){
			System.out.println("U lost,hahahahahaha ");
		}
		else if(bigState.isOver()){
			bigState.printBigState();
			System.out.println("Draw");
		}
		//ask user want to re-play
		System.out.println("Hey,Yo, Do you dare to play again? Y/N");
		String choice=scan.next();
		//call the play function again
		if(choice.equalsIgnoreCase("Y")){
			play();
		}
		else if(choice.equalsIgnoreCase("N")){
			System.out.println("C u in Project 2");
		}
		
	}
	protected Move getNextMove(){
//		System.out.println("BigState player is "+bigState.getPlayer());
		if(bigState.getPlayer().equals(humanPlayer)){
//			humanPlayer=humanPlayer.nextPlayer();
			return getHumanMove();
		}
		else{
			
//			humanPlayer=humanPlayer.nextPlayer();
//			return getHumanMove();
			return getAIMove();
		}
	}
	public Player getHumanPlayer(){
		Player human=null;
		System.out.print("Do you want to play X or O: ");
	    
				String choice = scan.next();
				if (choice.equalsIgnoreCase("x")) {
				    human= Player.X;
				} 
				else if (choice.equalsIgnoreCase("o")) {
				    human= Player.O;
				    boardNumber=1;
				}
		return human;
	}
	protected Move getHumanMove(){
		//check whether it first time input
		
		if(boardNumber==-1){
		System.out.println("Place your board number:");
		String board=scan.next();
		//base number
		boardNumber=Integer.parseInt(board);
		}
		//or the last board is full
		else if(bigState.getBoard().bigGrid[boardNumber].isFull()){
			System.out.println("Place your board number since your last board is full:");
			String board=scan.next();
			//base number
			boardNumber=Integer.parseInt(board);
		}
		System.out.println("Place your position:");
		String position=scan.next();
		positionNumber=Integer.parseInt(position);
		Move move=new Move(humanPlayer,positionNumber);
		return move;
	}
	//in my utility() gives 1 for x winning

	public Move getAIMove(){
		Move bestmove=null;
		System.out.println("AI turn");
		//after human play, possibles moves
		ArrayList<Move> moves=bigState.getPossibleMoves();
		currentTime=System.currentTimeMillis();
		int depth=8;
		//if humanPlayer is trying to max values
		//we always want to find the mini for A.I
		if(humanPlayer.equals(Player.X)){
		int minV=Integer.MAX_VALUE;
		//check all the moves
			for(Move move:moves){
				int v=pruning(result(bigState,move,bigState.positionNumber),Integer.MIN_VALUE,Integer.MAX_VALUE,depth);
				{
					if(v<minV){
						bestmove=move;
						minV=v;
					}
				}
			}
		}
		//if humanPlayer is minimizing
		//we always want to find the max for A.I
		else{
			int maxV=Integer.MIN_VALUE;
		
			for(Move move:moves){
				int v=pruning(result(bigState,move,bigState.positionNumber),Integer.MIN_VALUE,Integer.MAX_VALUE,depth);
				{
					if(v>maxV){
						bestmove=move;
						maxV=v;
					}
				}
			}
			
		}
		finishTime=System.currentTimeMillis();
		System.out.println("My role is: " + bestmove + ", so your next board postion is: " + bestmove.getPosition());
		elapsedTime=finishTime-currentTime;
		
		System.out.println("I used "+elapsedTime+" ms to finish thinking");
		return bestmove;
	}
	
	//referene:https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
	//based on the pesudo code there
	
	public int pruning(BigState s, int a, int b, int currentDepth){
	
		ArrayList<Move> moves=s.getPossibleMoves();
		if(s.isOver()||currentDepth==0){
			return utility(s);}
		//if maximizing player
		if(s.getPlayer().equals(Player.X)){
			
			int v=Integer.MIN_VALUE;
			//expanding all the moves
			for(Move move: moves){
				v=Math.max(v, pruning(result(s,move,s.positionNumber),a,b,currentDepth-1));
				a=Math.max(a, v);
				if (b<=a)
				break;
			}
			return v;
		}
		//if mini player
		else{
			int v=Integer.MAX_VALUE;
			for(Move move:moves){
				v=Math.min(v, pruning(result(s,move,s.positionNumber),a,b,currentDepth-1));
				b=Math.min(b, v);
				if(b<=a)
					break;
			}
			return v;
		}
	}
//	

	public BigState result(BigState state, Move move, int boardNumber){
		BigState newState=state.copyState();
		newState.applyState(move,boardNumber);
		return  newState;
	}
	public int utility(BigState state){
		Player winner=state.getWinner();
		if(winner==null){
			return state.checkTwoScore();
		}
		else if (winner.equals(Player.X))
		{
			return 1000;
		}
		else {
			return -1000;
		}
		
	}
	
	//test
	public static void main(String[]args){
		Games abc=new Games();
		abc.play();
//		abc.gameStart();
////		abc.bigState.printBigState();
//		abc.bigState.applyState(new Move(Player.X,3), 7);
//		abc.bigState.printBigState();
//		ArrayList<Move> moves=abc.bigState.getPossibleMoves();
//		BigState test,test2;
//		for(Move move:moves){
//			System.out.println(move);
//			test=abc.result(abc.bigState, move, abc.bigState.positionNumber);
//			System.out.println("test state(dup): ");
//			test.printBigState();
//		}
	}
}
