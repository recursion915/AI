import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
/*
 * Not implement Inferencer
 */
public class RejectionSampling {

	
	List <RandomVariable> sortedRVList = new ArrayList <RandomVariable> ();
	BayesianNetwork network = new BayesianNetwork();
	Assignment evidence = new Assignment();
	
	public Distribution ask(BayesianNetwork bn, RandomVariable X, Assignment e, int numberArguments) {
		Assignment tempAss = new Assignment();
		Distribution distribution = new Distribution();
		Assignment duplicateEvidence = e.copy();
		// count ArrayList is size of randomVairableSize
		// this is used to keep record  the number of satisfied sampling for each domain of X
		int [] count = new int[X.getDomain().size()];
	
		// loop through request times
		for(int i = 0; i< numberArguments; i++){
				// get the randomGenerated assignment
				tempAss = priorSample(sortedRVList);
		//		System.out.println("generated random sample is "+tempAss);
				for(int j = 0; j<X.getDomain().size(); j++){
				// add the query domain into evidence
					duplicateEvidence.set(X, X.getDomain().get(j));
				// if generated Assignment satisfies with evidence
				// count++
					if(isConsistent(tempAss, duplicateEvidence)){
						count[j] ++;
					}
				}
				
		}
		// set the distribution
		for (int i = 0; i < X.getDomain().size(); i++){
			distribution.put(X.getDomain().get(i), count[i]);
			System.out.println("For domain " + X.getDomain().get(i) + ", the "
					+ "number of consistent assignment generated is " + count[i]);
		}
		distribution.normalize();
		
		return distribution;
	}

	/*
	 * check the random generated Assignment is consistent with evidence
	 * this is done in O(m+n) where m is size of Assignment and n is the evidence size
	 * convert Assignment/evidence entry into arrayList; then check assList.contains(eList)
	 */
	public boolean isConsistent(Assignment randomAss, Assignment e){
		ArrayList assList = new ArrayList();
		ArrayList eList =new ArrayList();
		Iterator<Entry<RandomVariable, Object>> eIterator = e.entrySet().iterator();
		Iterator<Entry<RandomVariable, Object>> rIterator = randomAss.entrySet().iterator();
		while(eIterator.hasNext()){
			Map.Entry ePair = (Map.Entry) eIterator.next();
			eList.add(ePair);
			
		}
		while(rIterator.hasNext()){
			Map.Entry rPair = (Map.Entry) rIterator.next();
			assList.add(rPair);
			
		}
//		System.out.println("generated assignment is "+assList.toString());
//		System.out.println("evidence assignment is "+eList.toString());
//		System.out.println("Assignment contains evidence is "+ assList.containsAll(eList));
		
		return assList.containsAll(eList);
	}
	
	/*
	 * return a random Assignment based on prior probability
	 */
	public Assignment priorSample(List <RandomVariable> rvList){

		Assignment tempEvidence = new Assignment();
		RandomVariable tempRV;
		for (int i = 0; i< rvList.size(); i++){
//			BayesianNetwork.Node tempNode = network.getNodeForVariable(rvList.get(i));
			tempRV = rvList.get(i);
			// iterate over rvList.get(i) Domain
			tempEvidence.set(tempRV, generateRandomAssignment(tempRV,tempEvidence));
//			System.out.println("now the evidnece is "+tempEvidence.toString());
		}

		
		return tempEvidence;
		
	}
	/*
	 * this method takes in RandomVaraible
	 * based on its domain and corresponding Value, 
	 * generate a random value; if this random value falls into a specific category
	 * return the rv's random generated domain
	 */
	public Object generateRandomAssignment(RandomVariable rv, Assignment evidence){
		Random rand = new Random();
		double limit = 0.0;
		int correctIndex = -1;
		// this double arrayList is to contain specific ranges
		// one index for one range
		// limit is starting at 0: first range is 0- range.get(1)
		// 						   second range is range.get(1)-range.get(2)
		ArrayList <Double> range = new ArrayList  <Double>();
		for(int i = 0; i < rv.getDomain().size(); i++){
			evidence.set(rv, rv.getDomain().get(i));
		//	System.out.println(""+rv+", prob: "+network.getProb(rv, evidence));
			range.add( limit += network.getProb(rv, evidence) );
		}
		// generate a random double from 0.0 to 1.0
		double randomNumber = rand.nextDouble();
		// System.out.println("RN "+randomNumber);
		// loop through the range
		// if this randomNumber is smaller than the range
		// return correctIndex and break the loop
		for(int i = 0; i < range.size(); i++){
			if(randomNumber < range.get(i)){
				correctIndex = i;
				break;
			}
		}
		// now we generate a index, get that Domain from random variable
		// System.out.println("Random selection index is  "+correctIndex+"; selection is "+ rv.getDomain().get(correctIndex));
		// return the domain
		return rv.getDomain().get(correctIndex);
		
	}
	
	
	
	
	/**
	 * Main method is similar to BNExactInferencer
	 * The only difference is first take in paramenter is number of samples
	 * @param args
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	
	public static void main(String[]args) throws IOException, ParserConfigurationException, SAXException{
		
		RejectionSampling test =  new RejectionSampling();
		RandomVariable query = null;
		int numberofSamples = Integer.parseInt(args[0]);
		/**
		 *  use different parser based on different input format
		 *  get BNnewwork from parser
		 */
		if(args[1].contains(".xml")){
//			System.out.println(".xml found");
			XMLBIFParser parser = new XMLBIFParser();
			test.network = parser.readNetworkFromFile(args[1]);
			test.network.print(System.out);
		}
		else{
			BIFParser parser;
			if (args.length == 0) {
			    parser = new BIFParser(System.in);
			} else {
			    parser = new BIFParser(new FileInputStream(args[1]));
			}
			//System.out.println(parser.parse());
			test.network = parser.parseNetwork();
			test.network.print(System.out);
		}

		test.sortedRVList=test.network.getVariableListTopologicallySorted();
		/**
		 *  Convert arguments into queries and evidences
		 */
		for(int i = 2; i < args.length; i++){
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
		

//		System.out.println(test.ask(test.network, query, test.evidence, numberofSamples ));
		System.out.println("Queries is P(" + query + ") , given evidence: " + test.evidence.toString());
//		System.out.println(test.isConsistent(test.priorSample(test.sortedRVList), test.evidence));
		System.out.println("Distribution for " + query + " is " + test.ask(test.network, query, test.evidence, numberofSamples));
	
		
	}

}
