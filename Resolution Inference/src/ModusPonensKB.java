

public class ModusPonensKB extends KB {
	
	public ModusPonensKB() {
		super();
		Symbol p = intern("P");
		Symbol q = intern("Q");
		add(p);
		add(new Implication(p, q));
	}
	
	public static void main(String[] arg) {
		ModusPonensKB abc=new ModusPonensKB();
		System.out.println(abc.getSentences());
		
		
	}

}