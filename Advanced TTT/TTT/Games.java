import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Games {

	private State state;
	private Player humanPlayer;
	//this is because regular tic tae toe has limited space
	
	private int maxDepth=9;
	Scanner scan;
	public Games(){
		 scan=new Scanner(System.in);
	}
	public void play(){
		gameStart();
		gameLoop();
		gameOver();
	}
	public void gameStart(){
		humanPlayer=getHumanPlayer();
		state= new State();
	

	}
	public void gameLoop(){
//		System.out.println(state.isOver());
//		state.printState();

		while(!state.isOver()){
			Move move=null;
			state.printState();
			move=getNextMove();
			if(state.getBoard().isOutBound(move.getX(),move.getY())){
				System.out.println("U did this on purpose,throw you out of bound exception: game dies:");
				break;
			}
			if(!state.getBoard().isEmpty(move.getX(),move.getY())){
				System.out.println("Location has been occupied,remake your move:");
				move=getNextMove();
			}
			
			else{
				state.makeState(move);
			}
//			System.out.println(state.getBoard().checkHorizontal());
//			System.out.println(state.getBoard().checkVertical());
//			System.out.println(state.getBoard().checkDiagonal());
//			System.out.println(state.getBoard().checkAnti());
		}
	}
	public void gameOver(){
		state.printState();
		//this should not happen if my A.I works
		//A.I should at least win a draw
		if(state.getWinner()==humanPlayer)
		{
			System.out.println("Cannot believe u won,  ");
//			newGame();
		}
		else if(state.getWinner()==humanPlayer.nextPlayer()){
			System.out.println("U lost,hahahahahaha ");
		}
		else if(state.isOver()){
			state.printState();
			System.out.println("Draw");
		}
		System.out.println("Hey,Yo, Do you dare to play again? Y/N");
		String choice=scan.next();
		if(choice.equalsIgnoreCase("Y")){
			play();
		}
		else if(choice.equalsIgnoreCase("N")){
			System.out.println("C u in Project 2");
		}
		
	}
	protected Move getNextMove(){
		if(state.getPlayer().equals(humanPlayer)){
//			humanPlayer=humanPlayer.nextPlayer();
			return getHumanMove();
		}
		else{
//			humanPlayer=humanPlayer.nextPlayer();
//			return getHumanMove();
			return getAIMove();
		}
	}
	public Move getHumanMove(){
//		System.out.println("Place your x and y coordinates in this format 0,2");
//		String input=scan.next();
//		String[]inputnew=input.split(",");
//		int x=Integer.parseInt(inputnew[0]);
//		int y=Integer.parseInt(inputnew[1]);
		System.out.println("Place your move:");
		String input=scan.next();
		int a=Integer.parseInt(input);
		int x=(a-1)/3;
		int y=(a-1)%3;
		Move move=new Move(humanPlayer,x,y);
		return move;
	}
	//in my utility() gives 1 for x winning

	public Move getAIMove(){
		Move bestmove=null;
		ArrayList<Move> moves=state.getPossibleMoves();
		//if humanPlayer is maxmizing
		//we always want to find the mimi for A.I
		if(humanPlayer.equals(Player.X)){
		int minV=Integer.MAX_VALUE;
		int depth=0;
		for(Move move:moves){
			int v=pruning(result(state,move),Integer.MIN_VALUE,Integer.MAX_VALUE,depth++);
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
			int depth=0;
			for(Move move:moves){
				int v=pruning(result(state,move),Integer.MIN_VALUE,Integer.MAX_VALUE,depth++);
				{
					if(v>maxV){
						bestmove=move;
						maxV=v;
					}
				}
			}
//			
//			System.out.println("My role is: " + bestmove + ", utility: " + maxV);
		}
		
		return bestmove;
	}
	
	//referene:https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning
	//based on the pesudo code there
	
	public int pruning(State s, int a, int b, int currentDepth){
		int alpha=a;
		int beta=b;
		if(s.isOver()||maxDepth==0){
			return utility(s);}
		//if maximizing player
		if(s.getPlayer().equals(Player.X)){
			ArrayList<Move> moves=s.getPossibleMoves();
			int v=Integer.MIN_VALUE;
			//expanding all the moves
			for(Move move: moves){
				v=Math.max(v, pruning(result(s,move),alpha,beta,maxDepth-1));
				alpha=Math.max(alpha, v);
				if (beta<=alpha)
				break;
			}
			return v;
		}
		//if mini player
		else{
			int v=Integer.MAX_VALUE;
			ArrayList<Move> moves=s.getPossibleMoves();
			for(Move move:moves){
				v=Math.min(v, pruning(result(s,move),alpha,beta,maxDepth-1));
				beta=Math.min(beta, v);
				if(beta<=alpha)
					break;
			}
			return v;
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
				}
		return human;
	}
	public State result(State state, Move move){
		State newState=state.copyState();
		newState.makeState(move);
		return  newState;
	}
	public int utility(State state){
		Player winner=state.getWinner();
		if(winner==null){
			return 0;
		}
		else if (winner.equals(Player.X))
		{
			return 1;
		}
		else{
			return -1;
		}
		
	}
	public static void main (String[]args){
		Games abc=new Games();
		abc.play();

//	Board abc= new Board();
//	int x;
//	int y;
//	Move move;
//	do{
//		System.out.println("Place your x and y coordinates in this format 0,2");
//		Scanner scan= new Scanner(System.in);
//		String input=scan.next();
//		String[]inputnew=input.split(",");
//		x=Integer.parseInt(inputnew[0]);
//		y=Integer.parseInt(inputnew[1]);
//		move=new Move(Player.X,x,y);
//		abc.makeMove(move);
////		System.out.println(abc.checkHorizontal());
////		System.out.println(abc.checkVertical());
////		System.out.println(abc.checkDiagonal());
////		System.out.println(abc.checkAnti());
//		
//	}while(!abc.isTerminated());
//	
//	}
	}
}
