
public class Biconditional extends BinaryCompoundSentence {
	
	public Biconditional (Sentence lhs, Sentence rhs){
		super(BinaryConnective.IFF,lhs,rhs);
	}

	@Override
	public boolean isSatisfiedBy(Model model) {
		return lhs.isSatisfiedBy(model)==rhs.isSatisfiedBy(model);
	}
	
	

}
