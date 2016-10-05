import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class HornClausesSolver {
	
	//setArray contains all known clauses
	ArraySet<Clause> setArray=new ArraySet<Clause>();
//	KB kb = new KB();
	Symbol mythical = new Symbol("Mythical");
	Symbol mortal = new Symbol("Mortal");
	Symbol mammal = new Symbol("Mammal");
	Symbol magical=new Symbol("Magical");
	Symbol horned = new Symbol("Horned");
	Sentence[]sArray=new Sentence[4];
	Clause emptyClause= new Clause();
	public void generateKBSet()
	{
		//if the unicorn is mythical, then it is immortal
		sArray[0] = new Implication(mythical,new Negation(mortal));
		//if it is not mythical, then it is a mortal mammal. 
		sArray[1] = new Implication(new Negation(mythical),new Conjunction(mortal, mammal));
		//If the unicorn is either immortal or a mammal, then it is horned. 
		sArray[2] = new Implication(new Disjunction(new Negation(mortal),mammal),horned);
		//The unicorn is magical if it is horned.
		sArray[3] = new Implication(horned,magical);
//		
//		sArray[0]=mythical;
////		sArray[1]=magical;
		for(int i=0;i<sArray.length;i++){
//		 kb.add(sArray[i]);
		 //convert to CNF
		 Set<Clause> clauses = CNFConverter.convert(sArray[i]);
		 //for each set of CNF, put clauses to setArray
		 setArray.addAll(clauses);
		}
	
	}
	public boolean resolution(Sentence s){
		//clear previous arraySet
		setArray.clear();
		//get KB into the arraySet
		generateKBSet();
		//add the negation of s to the known bases
		//now setArray has all the CNF with negation of s
		Set<Clause> clauses = CNFConverter.convert(new Negation(s));
	    setArray.addAll(clauses);
	    System.out.println("KB clauses are:(including negation of assumption)");
	    System.out.println(setArray);
		//create new empty clause set
		ArraySet<Clause> newClauses=new ArraySet<Clause>();
		ArraySet<Clause> resolvents=new ArraySet<Clause>();
		//for each pair of Ci and Cj
		for(int i=0;i<setArray.size();i++){
			for(int j=i+1;j<setArray.size();j++){
				//check there are complementary literals between two clauses
				if(isComplementary(setArray.get(i),setArray.get(j))){
					resolvents=resolve(setArray.get(i),setArray.get(j));
					
				//if a contradictory statement is found, proved successful
					if(resolvents.containsAll(CNFConverter.convert(s))){
//******************	COMMENT OUT LINE 65-67 FOR EACH STEP FOR PROVE*****************
//						System.out.println("clause "+setArray.get(i)
//						+"and clause"+setArray.get(j)+" can resolve: "+resolvents+", which disagrees with our"
//								+ " assumption"+clauses);
						
					      return true;
					}
					newClauses.addAll(resolvents);
//******************	COMMENT OUT LINE 72 FOR EACH STEP FOR PROVE*****************
//					System.out.println("New generated Clauses in each steps are: "+ newClauses);
					if(setArray.contains(newClauses)){
						return false;
					}	
					setArray.addAll(newClauses);	
				}
			}
		}
//		setArray.clear();
		return false;
		
	}
	
	public boolean isComplementary(Clause c1, Clause c2){
		for(int i=0;i<c1.size();i++){
			for(int j=0;j<c2.size();j++){
				if(c1.get(i).getContent().equals(c2.get(j).getContent())&&!
						c1.get(i).getPolarity().equals(c2.get(j).getPolarity())){
					return true;
				}
			}
		}
		return false;
		
	}
	//resolve two clauses
	public ArraySet<Clause> resolve(Clause c1, Clause c2){
		
		ArraySet<Clause> resultedClauses=new ArraySet<Clause>();
	
		for(int i=0;i<c1.size();i++){
			for(int j=0;j<c2.size();j++){
				if(c1.get(i).getContent().equals(c2.get(j).getContent())&&!
						c1.get(i).getPolarity().equals(c2.get(j).getPolarity())){
	
					if(buildClause(c1.get(i),c2.get(j),c1,c2).size()>0){
					resultedClauses.add(buildClause(c1.get(i),c2.get(j),c1,c2));}
					break;
				}
			}
		}
		
		return resultedClauses;
		
	}
//remove two clauses that complementary to each other and return a good one
	public Clause buildClause(Literal removed1,Literal removed2, Clause c1, Clause c2){
		Clause c = new Clause();
		for(int i=0;i<c1.size();i++){
			if(!c1.get(i).equals(removed1)){
				c.add(c1.get(i));
			}
		}
		for(int i=0;i<c2.size();i++){
			if(!c2.get(i).equals(removed2)){
				c.add(c2.get(i));
			}
		}
		
		return c;
	}

	public static void main(String[] args) {
		
		HornClausesSolver test= new HornClausesSolver();
		boolean proved;
		
		proved=test.resolution( test.mythical);
		System.out.println("Can we prove that the unicorn is mythical? "
 		+  proved);
		
		
		
		proved=test.resolution( test.magical);
	
		System.out.println("Can we prove that the unicorn is magical? "
 		+  proved);
		
		
		
		proved=test.resolution( test.horned);

		System.out.println("Can we prove that the unicorn is horned? "
 		+  proved);
		
		


	}

}
