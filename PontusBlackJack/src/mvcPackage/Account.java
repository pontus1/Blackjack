package mvcPackage;

import javafx.beans.property.SimpleIntegerProperty;

/**
 * An implementation of the account the player uses to bet with.
 * 
 * @author Pontus Ellboj
 *
 */
public class Account {

	private SimpleIntegerProperty balance;
	
	/**
	 * Constructor that creates an Account with a specified balance.
	 * 
	 * @param initBalance	the initial balance of the account.
	 */
	public Account(int initBalance){
		balance = new SimpleIntegerProperty(initBalance);
	}

	/**
	 * Getter for balance.
	 * 
	 * @return	the current balance.
	 */
	public SimpleIntegerProperty getBalance() {
		return balance;
	}

	/**
	 * Increases the Accounts balance.
	 * 
	 * @param sum	the amount to increase the balance with.
	 */
	public void increaseBalance(int sum) {
		balance.set(balance.get() + sum);
	}
	
	/**
	 * Decreases the Accounts balance.
	 * 
	 * @param sum	the amount to decrease the balance with.
	 */
	public void decreaseBalance(int sum) {
		balance.set(balance.get() - sum);
	}
	
	
}
