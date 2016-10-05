import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CNFSolver {
	
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
	
   //try to prove s is true or not
	public boolean resolution(Sentence s){
		//clear previous arraySet
		setArray.clear();
		//get KB into the arraySet
		generateKBSet();
		//add the negation of s to the known bases
		//now setArray has all the CNF with negation of s
		Set<Clause> clauses = CNFConverter.convert(new Negation(s));
	    setArray.addAll(clauses);
		//create new empty clause set
		ArraySet<Clause> newClauses=new ArraySet<Clause>();
		ArraySet<Clause> resolvents=new ArraySet<Clause>();
		//for each pair of Ci and Cj
		for(int i=0;i<setArray.size();i++){
			for(int j=i+1;j<setArray.size();j++){
				//check there are complementary literals between two clauses
				if(isComplementary(setArray.get(i),setArray.get(j))){
					resolvents=resolve(setArray.get(i),setArray.get(j));
					System.out.println("first clause "+setArray.get(i)
					+"second clause"+setArray.get(j)+" reult is "+resolvents);
				
					
					if(resolvents.containsAll(CNFConverter.convert(s))){
						  
					      return true;
					}
					newClauses.addAll(resolvents);
				    System.out.println("setArray is: ");
				    for(int k=0;k<setArray.size();k++){
						
						System.out.println(setArray.get(k));
						}
				    System.out.println("new clauses: ");
				    System.out.println(newClauses);
					if(setArray.contains(newClauses)){
						return false;
					}
//		
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
//				System.out.println(c1.get(i));
				//same symbol but different polarity, return true
				if(c1.get(i).getContent().equals(c2.get(j).getContent())&&!
						c1.get(i).getPolarity().equals(c2.get(j).getPolarity())){
					return true;
				}
			}
		}
		return false;
		
	}
	//resolve two clauses
	//works for everything; but unable to get rid of nonsense clauses
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
//		System.out.println("why you are not here");
//		System.out.println(c);
//		System.out.println("resolved clause is "+c+" and c is null is "+c.size());
		
		return c;
	}
	//helper method to get rid of duplicate clauses
	//e.x {~A disjunction A} is basically 废话(not useful)
//	public Clause eliminateDuplicate(Clause c){
//		Clause d= new Clause();
//		for(int i=0;i<c.size();i++){
//			for(int j=i+1;j<c.size();j++){
//				if(!c.get(i).getContent().equals(c.get(j).getContent())&&!
//						c.get(i).getPolarity().equals(c.get(j).getPolarity()))
//				{
//					d.add(c.get(i));
//				}
//			}
//		}
//		return d;
//	}
	public Clause eliminateDuplicate(Clause c){
		Clause d= c;
		for(int i=0;i<c.size();i++){
			for(int j=i+1;j<c.size();j++){
				if(c.get(i).getContent().equals(c.get(j).getContent())){
					d.remove(c.get(i));
					d.remove(c.get(j));
				}
			}
		}
		return d;
	}
	public static void main(String[] args) {
		
		
		
		CNFSolver test= new CNFSolver();
		//checking resolve for two complementary literals
//		ArraySet<Clause> resultedClauses=new ArraySet<Clause>();
//		Clause c1=new Clause();
//		Clause c3=new Clause();
//		c1.toClauses(new Negation(test.mythical), c1);
//		c1.toClauses(new Negation(test.horned),c1);
//		
//		Clause c2=new Clause();
//		c2.toClauses(test.horned, c2);
//		c2.toClauses(test.mythical,c2);
////		c2.toClauses(new Negation(test.mythical),c2);
////		c2.toClauses(test.immortal, c2);
//    	System.out.println("c1 is "+c1);
//		System.out.println("c2 is "+c2);
//		resultedClauses=test.resolve(c1, c2);
//////		resultedClauses.add(c3);
//		System.out.println(resultedClauses);
		
		//end of test
		
		//test of contains.nul works
//		ArraySet<Clause> resultedClauses=new ArraySet<Clause>();
//		Clause c1=new Clause();
//		Clause c3=new Clause();
//		c1.toClauses(new Negation(test.mythical), c1);
//		
//		Clause c2=new Clause();
//
//		c2.toClauses(test.horned,c2);
//		c2.toClauses(test.mythical,c2);
////		c2.toClauses(test.immortal, c2);
//    	System.out.println("c1 is "+c1);
//		System.out.println("c2 is "+c2);
//		System.out.println("c3 is "+c3);
//		resultedClauses=test.resolve(c1, c2);
//		resultedClauses.add(c3);
//		System.out.println(resultedClauses);
//		
//		System.out.println(resultedClauses.contains(c3));
//		
//		//end of test2
		
//		System.out.println(test.resolve(c1,c2).isEmpty());
		
//		
//////		System.out.println(c1.get(0).getContent());
//       
//		System.out.println("after resolve"+ test.resolve(c1,c2));
//		Set<Clause> newClauses=new HashSet<Clause>();
//		newClauses.addAll(test.resolve(c1,c2));
//		
//		System.out.println(newClauses);
//		System.out.println(test.resolve(c1, c2).size());
//		System.out.println(test.isComplementary(c1,c2));
//		
//		ArraySet<Clause> resultedClauses=new ArraySet<Clause>();
//		Clause c1=new Clause();
//		c1.toClauses(new Negation(test.mythical), c1);
//		c1.toClauses(test.horned,c1);
//		
//		Clause c2=new Clause();
//		c2.toClauses(test.horned, c2);
//		Clause c3=new Clause();
//		c3.toClauses(test.horned, c3);
//		Clause c4=new Clause();
//		c4.toClauses(test.horned,c4);
//		c4.toClauses(new Negation(test.mythical), c4);
//		System.out.println(c1);
//		System.out.println(c2);
//		System.out.println(c3);
//		System.out.println(c4);
//		resultedClauses.add(c1);
//		resultedClauses.add(c2);
//		resultedClauses.add(c3);
//		resultedClauses.add(c4);
//		System.out.println(resultedClauses);
//		System.out.println(resultedClauses.contains(c4));
	
 	//    System.out.println(test.resolution( test.mythical));
  //System.out.println(test.resolution( test.magical));
    	System.out.println(test.resolution( test.horned));
		
//
	}

}
