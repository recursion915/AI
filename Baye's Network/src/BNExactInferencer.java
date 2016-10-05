import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class BNExactInferencer implements Inferencer {

//	List <Assignment> evidenceList = new ArrayList <Assignment> ();
//	List <RandomVariable> queryList = new ArrayList <RandomVariable> ();

	List <RandomVariable> sortedRVList = new ArrayList <RandomVariable> ();
	BayesianNetwork network = new BayesianNetwork();
	Assignment evidence = new Assignment();
	
	
	@Override
	public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e) {
		Distribution distribution = new Distribution();
		for(int i = 0; i < X.getDomain().size(); i++){
			evidence.set(X, X.getDomain().get(i));
			distribution.put(X.getDomain().get(i), enumerateAll(sortedRVList, evidence));
		}
		distribution.normalize();
//		System.out.println(distribution);
		return distribution;
	}
	/**
	 * Algorithm from AIMA Page 525 Figure 14.9 
	 * @param variableList: BN sorted RV list
	 * @param evidence: evidence
	 * @return
	 */
	public double enumerateAll(List <RandomVariable> variableList, Assignment givenEvidence){
	
		// break case
		if(variableList.size() == 0)
		{
			return 1.0;
		}
		// get the first RV
		RandomVariable currentRV =  variableList.get(0);
		// check this RV is in evidence
		// In the first case, firstRV(Y) is already assigned in e to some value y, 
		// so P(e) is just P(FirstRV=y | the rest of e) × P(the rest of e). 
		if(givenEvidence.variableSet().contains(currentRV))
		{
//			System.out.println(currentRV + " found");
//			System.out.println("evidence: "+givenEvidence.toString());
//			System.out.println(network.getProb(currentRV, givenEvidence));
			return network.getProb(currentRV, givenEvidence) * enumerateAll(rest(variableList), givenEvidence) ;	
		}
		// In the second case, Y is not assigned, 
		// so we have to sum over all possible values y in Y’s domain.
		else{
			double sum = 0.0;
			Assignment tempEvidence = givenEvidence.copy();
			for(int i = 0; i < currentRV.getDomain().size(); i++){
//				System.out.println(currentRV + " not found" + " "+currentRV.getDomain().get(i) );
				tempEvidence.set(currentRV, currentRV.getDomain().get(i));
//				System.out.println("tempevidence " +tempEvidence+ " orignial evidence: "+givenEvidence);
//				System.out.println(network.getProb(currentRV, tempEvidence));
				sum += network.getProb(currentRV, tempEvidence) * enumerateAll(rest(variableList),tempEvidence);
				
			}
			return sum;
		}
		
	}
	
	
	/** remove the first random variable(index 0)
	 *  return the remaining
	 * @param rvlist
	 * @return
	 */
	public List <RandomVariable> rest(List <RandomVariable> rvlist){
		return rvlist.subList(1, rvlist.size());
		
	}
	/**
	 * take into a random variable
	 * @param Y
	 * @return the parentsEvidence
	 * IT IS BOGUS; NOT USED ANY MORE
	 */
	public Assignment parentsEvidence(RandomVariable Y){
		List<BayesianNetwork.Node>parentsList = network.getNodeForVariable(Y).parents;
		Assignment tempEvidence = new Assignment();
		tempEvidence.set(Y, ""+evidence.get(Y));
		for(int i=0;i<parentsList.size();i++){
			if(evidence.variableSet().contains(parentsList.get(i).variable)){
				tempEvidence.set(parentsList.get(i).variable, evidence.get(parentsList.get(i).variable));
			}
		}
		System.out.println("parents evidences are: " + tempEvidence.toString());
		return tempEvidence;
		
	}

	public static void main(String[]args) throws IOException, ParserConfigurationException, SAXException{
	
		BNExactInferencer test =  new BNExactInferencer();
		RandomVariable query = null;
		/**
		 *  use different parser based on different input format
		 */
		if(args[0].contains(".xml")){
//			System.out.println(".xml found");
			XMLBIFParser parser = new XMLBIFParser();
			test.network = parser.readNetworkFromFile(args[0]);
			test.network.print(System.out);
		}
		else{
			BIFParser parser;
			if (args.length == 0) {
			    parser = new BIFParser(System.in);
			} else {
			    parser = new BIFParser(new FileInputStream(args[0]));
			}
			//System.out.println(parser.parse());
			test.network = parser.parseNetwork();
			test.network.print(System.out);
		}

		test.sortedRVList=test.network.getVariableListTopologicallySorted();
		/**
		 *  Convert arguments into queries and evidences
		 */
		for(int i=1;i<args.length;i++){
		/** first check if the args is in RV list
		 * if it is, check the next arg whether in RV domain
		 * if both are true, it is evidence; add into evidence list
	     */ 
			if(test.sortedRVList.contains(test.network.getVariableByName(args[i])) && 
					test.network.getVariableByName(args[i]).getDomain().contains(args[i+1])){
				
				test.evidence.set(test.network.getVariableByName(args[i]), args[i+1]);
				
				// evidence takes two positions at args, so skip one argument
				i = i + 1;
			}
			// otherwise, it is a query
			else{
//				test.queryList.add(test.network.getVariableByName(args[i]));
				query = test.network.getVariableByName(args[i]);
			}
		}
		
		System.out.println("Queries is P(" + query + ") , given evidence: " + test.evidence.toString());
		System.out.println(test.ask(test.network, query, test.evidence ));
		
	}

}
