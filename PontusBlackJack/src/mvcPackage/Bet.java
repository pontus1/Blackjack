package mvcPackage;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * An implementation of a bet.
 * 
 * @author Pontus Ellboj
 *
 */
public class Bet {

	private SimpleIntegerProperty currentBet;

	/**
	 * Class constructor creating a bet initially set to 0.
	 */
	public Bet() {
		currentBet = new SimpleIntegerProperty(0);
	}

	/**
	 * Class constructor creating a bet initially set to the argument passed to
	 * it.
	 * 
	 * @param sum
	 *            the sum of the bet.
	 */
	public Bet(int sum) {
		currentBet = new SimpleIntegerProperty(sum);
	}

	/**
	 * Place a bet by increasing the value of the bet.
	 * 
	 * @param sum
	 *            the sum to increase the bet with.
	 */
	public void placeBet(int sum) {
		currentBet.set(currentBet.get() + sum);
	}

	/**
	 * Removes the current sum of the bet and thereby setting it to 0.
	 */
	public void removeBet() {
		currentBet.set(0);
	}

	/**
	 * Getter for currentBet.
	 * 
	 * @return the currentBet.
	 */
	public SimpleIntegerProperty getCurrentBet() {
		return currentBet;
	}

	/**
	 * Setter for currentBet modified to take an integer as argument.
	 * 
	 * @param currentBet
	 *            the bet to replace the old with.
	 */
	public void setCurrentBet(int currentBet) {
		this.currentBet.set(currentBet);
	}

}
