

public class Implication extends BinaryCompoundSentence {

	public Implication(Sentence lhs, Sentence rhs) {
		super(BinaryConnective.IMPLIES, lhs, rhs);
	}

	/**
	 * Return true if this Disjunction is satisfied by the given Model.
	 * That is, if either of its arguments are satisfied by the Model.
	 */
	public boolean isSatisfiedBy(Model model) {
		return !lhs.isSatisfiedBy(model) || rhs.isSatisfiedBy(model);
	}

}
