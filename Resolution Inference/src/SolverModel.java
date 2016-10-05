import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class SolverModel implements Model {
	
	
	HashMap<Symbol,Boolean> hm=new HashMap<Symbol,Boolean>();

	
	@Override
	public void set(Symbol sym, boolean value) {
		
//		System.out.println("insert "+sym+" as "+value);
//		
		hm.put(sym,value);
		
	}
	@Override
	public boolean get(Symbol sym) {
		// TODO Auto-generated method stub
//		System.out.println("why?");
		return hm.get(sym);
	}
	@Override
	public boolean satisfies(KB kb,Model mod) {
		for(Sentence s:kb.getSentences() )
		{
//			System.out.println(s);
			//if one sentence is not satisfied by model, then return wrong
			if(!satisfies(s,mod))
			{
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean satisfies(Sentence s,Model mod) {
		//if s is satisfied by one of model , then it is true
		
		return s.isSatisfiedBy(mod);

	}
	//print true table for symbols
	@Override
	public void dump() {
		
			System.out.println(hm);
		
	
	}

	public static void main(String[]args){
		SolverModel abc=new SolverModel();
		ModusPonensKB kb=new ModusPonensKB();
//		System.out.println(abc.getMap(0));
//		abc.generateTable();
//		abc.generateAllModels();
//		abc.dump();
//		System.out.println(abc.symbolArray[0]+" satisfies "+abc.satisfies(abc.symbolArray[0]));
//		System.out.println(abc.models[2].get(abc.symbolArray[1]));
//		abc.satisfies(new ModusPonensKB());
		Symbol p= new Symbol("P");
		Symbol q= new Symbol("Q");
		abc.set(p, false);
		abc.set(q, true);
		abc.dump();
//		System.out.println(abc.get(p));
		System.out.println(abc.satisfies(q,abc));
		System.out.println(abc.satisfies(kb,abc));
//		System.out.println(abc.hm);
		
		
	}
}
