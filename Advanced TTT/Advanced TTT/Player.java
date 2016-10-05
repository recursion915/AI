//X first player
//O second player
//E as empty
public enum Player {
	X,
	O;
	public Player nextPlayer() {
		if (this == X) {
		    return O;
		} else {
		    return X;
		}
	 }
}
