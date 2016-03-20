package mvcPackage;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.HBox;

/**
 * An implementation of Game-type functioning as an abstract strategy class for
 * concrete subclasses.
 * 
 * @author Pontus Ellboj
 *
 */
public abstract class AbstractGame {

	Deck deck;

	Card splitCard;

	Account playerAccount;

	String[] gameResult;

	Hand dealer, hand1, hand2, hand3, splitHand1, splitHand2, splitHand3;

	HBox dealerCards, hand1Cards, hand2Cards, hand3Cards, splitHand1Cards, splitHand2Cards, splitHand3Cards;

	StringBinding dealerScore, hand1Score, hand2Score, hand3Score, splitHand1Score, splitHand2Score, splitHand3Score;

	SimpleBooleanProperty playable, surrenderable, doubleable, finished, allBetsPlaced, betable, splitable, splitMode,
			gameOver;

	Bet hand1Bet, splitHand1Bet, hand2Bet, splitHand2Bet, hand3Bet, splitHand3Bet;

	/**
	 * Class constructor.
	 * 
	 * @param balance
	 *            the current balance on the player's account.
	 */
	AbstractGame(int balance) {

		// INIT HAND CONTAINERS (DETERMINES HOW AND WHERE THE HANDS ARE SHOWN)
		dealerCards = new HBox(-70
				);
		hand1Cards = new HBox(-70);
		splitHand1Cards = new HBox(-70);

		// INIT HANDS
		dealer = new Hand(dealerCards.getChildren());
		hand1 = new Hand(hand1Cards.getChildren());
		splitHand1 = new Hand(splitHand1Cards.getChildren());

		// INIT SIMPLE BOOLEAN PROPERTIES (CONTROLS STATE OF THE GAME)
		playable = new SimpleBooleanProperty(false);
		surrenderable = new SimpleBooleanProperty(false);
		doubleable = new SimpleBooleanProperty(false);
		finished = new SimpleBooleanProperty(false);
		allBetsPlaced = new SimpleBooleanProperty(false);
		betable = new SimpleBooleanProperty(true);
		splitable = new SimpleBooleanProperty(false);
		splitMode = new SimpleBooleanProperty(false);
		gameOver = new SimpleBooleanProperty(false);

		// BIND PROPERTIES (BINDS TO VALUE PROPERTIES OF EACH HAND)
		hand1Score = hand1.getValueProperty().asString();
		dealerScore = dealer.getValueProperty().asString();
		splitHand1Score = splitHand1.getValueProperty().asString();

		// BETTING
		hand1Bet = new Bet();
		splitHand1Bet = new Bet();
		playerAccount = new Account(balance);
	}

	/**
	 * Start a new Game.
	 * 
	 * Set "tableCleared", "finnished" and "setDoubled" on all hands to false.
	 * Set "playable" and "surrenderable" to true. Set Hand1's turn to true and
	 * the other hands to false. If the player Account's balance is equal to or
	 * higher than Hans1's bet then set "doubleable" to true. Call "reset()" on
	 * all hands and deal two new cards to all hands (one of the dealers cards
	 * is turned upside down). If Hand1 gets a pair or two cards with the same
	 * value set "splitable" to true.
	 */
	abstract protected void startNewGame();

	/**
	 * Call "hit" (take another card) on the hand whose turn it is. The hand is
	 * no longer split-, surrender- or doubleable.
	 */
	abstract protected void hit();

	/**
	 * Call "stand" on the hand whose turn it is, and thereby handing over the
	 * turn to the next hand. If it's a splithands turn than hand over the turn
	 * to the hand it was splitted from. Check all values on the next hand to
	 * determine the possible options for it. If stand is called on dealer's
	 * turn call "endGame()".
	 */
	abstract protected void stand();

	/**
	 * Call "surrender" on the hand whose turn it is. Set "surrendered" to true
	 * and call "stand". Terminate the player's interest in the hand and pass
	 * the turn to the next hand.
	 */
	abstract protected void surrender();

	/**
	 * Deal one more card to the hand and than call "stand()". Double the hand's
	 * bet.
	 */
	abstract protected void doubleDown();

	/**
	 * Split hand in two and set the current splithand's turn to true. Add a
	 * bet to the splitted hand equal to the bet on the hand it was splitted from.
	 */
	abstract protected void split();

	/**
	 * Calculate the value of each hand and compare it to the dealer. Save the
	 * result in "gameResult" and increase balance of the player's account
	 * accordingly. Set "finnished" to true and "allBetsPlaced", "playable",
	 * "surrenderable" and "doubleable" to false. If player account's balance is
	 * lower than 5 set "gameOver" to true.
	 */
	abstract protected void endGame();

	/**
	 * Remove all bets and cards on the table.
	 */
	abstract protected void clearTable();
}
