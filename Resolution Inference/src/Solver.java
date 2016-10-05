public interface Solver {
	
	/**
	 * If the given KB is satisfiable, return a satisfying Model.
	 */
	public Model solve(KB kb, Sentence s);


}
