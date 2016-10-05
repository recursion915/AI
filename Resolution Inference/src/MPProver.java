import java.util.Collection;

public class MPProver implements Prover {
		
	ModusPonensKB mpkb=new ModusPonensKB();
	Collection<Symbol> coll=mpkb.getSymbols();
	Symbol[] symbolArray=coll.toArray(new Symbol[coll.size()]);
	//N is col size
	int N=coll.size();
	//NN is row size
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
    	 System.out.println("the entire models are: ");
    	 for(int i=0;i<models.length;i++){
 			for(int j=0;j<symbolArray.length;j++){
 			System.out.print(symbolArray[j]+": "+models[i].get(symbolArray[j])+" ");
 			}
 			System.out.println();
 		}
     }
     
     @Override
 	public boolean entails(KB kb, Sentence s) {
    	 //if one of the model satisfies the entire KB, return true
 		for(int i=0;i<models.length;i++){
 			if(models[i].satisfies(kb, models[i])&&s.isSatisfiedBy(models[i])){
 				System.out.println("The correct model is: ");
 				models[i].dump();
 				return true;
 			}
 		}
 		//else 
 		return false;
 	}
    //test
	
     public static void main(String[]args){
    	 
    	 MPProver mp=new MPProver();
    	
    	 mp.generateTable();
//    	 mp.printTable();
//    	 ModusPonensModel test[]=new ModusPonensModel[4];
//    	 System.out.println(test[1].hm);
    	 mp.generateAllModels();
    	 //print all models
    	 mp.printModels();
    	 //print kb
    	 
    	 //symbolArray[1] is Q; 0 index for P
    	 System.out.print("{P,P implies Q}entails Q is "+mp.entails(mp.mpkb,mp.symbolArray[1]));
    	 
    	 
    	 
     }

	

}
