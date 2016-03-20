package mvcPackage;

/**
 * All possible ranks that needs be used to create standard deck of cards. Each
 * rank contains the correct value according to blackjack rules. (Ace is 11 by
 * default).
 * 
 * @author Pontus Ellboj
 *
 */
enum Rank {
	TWO(2), 
	THREE(3), 
	FOUR(4), 
	FIVE(5), 
	SIX(6), 
	SEVEN(7), 
	EIGHT(8), 
	NINE(9), 
	TEN(10), 
	JACK(10), 
	QUEEN(10), 
	KING(10), 
	ACE(11);

	private final int value;

	// ENUMERATION CONSTRUCTOR
	private Rank(int value) {
		this.value = value;
	}

	/**
	 * Getter for value.
	 * 
	 * @return the value corresponding with the rank.
	 */
	public int getValue() {
		return value;
	}

};
