import java.util.Collection;

public class WPSolver implements Solver{

	
	WumpusWorldKB wwkb=new WumpusWorldKB();
	Collection<Symbol> coll=wwkb.getSymbols();
	Symbol[] symbolArray=coll.toArray(new Symbol[coll.size()]);
	//N is row size
	int N=coll.size();
	//NN is col size
	int NN=1<<N;
	boolean[][]truthtable=new boolean[NN][N];
	SolverModel models[]=new SolverModel[NN];
	
	//generate truth table
	
	public void generateTable(){  
		//generating combinations
		for(int nn=0;nn<NN;nn++){
			for(int n=0;n<N;n++){	
				truthtable[nn][N-n-1]=(((nn>>n)&1)==1);
			}
		}
     }
	
	//each row of model is a complete truth table
	public void generateAllModels(){
		
		for(int i=0;i<NN;i++){
			models[i]=new SolverModel();
			for(int j=0;j<N;j++){
			models[i].set(symbolArray[j], truthtable[i][j]);}
		}
		
	
	}
	//this is for printing truth table
     public void printTable(){
    	 
    		for(int i=0; i<truthtable.length; i++) {
//    	        System.out.print("" + i + ": ");
    	        for( int j=0; j<truthtable[0].length; j++) {
    	            System.out.print(truthtable[i][j]+" ");
    	        }
    	        System.out.println("");
    	    }
     }
     
     public void printModels(){
    	 System.out.println("the entire models(size:"+models.length+") are: ");
    	 for(int i=0;i<models.length;i++){
 			for(int j=0;j<symbolArray.length;j++){
 			System.out.print(symbolArray[j]+": "+models[i].get(symbolArray[j])+" ");
 			}
 			System.out.println();
 		}
     }
     
	@Override
	public Model solve(KB kb,Sentence s) {
		for(int i=0;i<models.length;i++){
 			if(models[i].satisfies(kb, models[i])){
 				return models[i];
 			}
 		}
 		//else gg
		return null;
	}

	public static void main(String[]args){
		WPSolver sl= new WPSolver();
		sl.generateTable();
		sl.generateAllModels();
		sl.printModels();
		//symbolArray[0] is P1,2; 
		System.out.println("the correct model satisfying KB is:");
		sl.solve(sl.wwkb, sl.symbolArray[0]).dump();
		System.out.println("P1,2 is proven to be "+sl.solve(sl.wwkb, sl.symbolArray[0]).get(sl.symbolArray[0]));
	}
}
