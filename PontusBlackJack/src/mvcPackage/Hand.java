package mvcPackage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;

/**
 * An implementation of a Hand type.
 * 
 * @author Pontus Ellboj
 *
 */
public class Hand {

	private ObservableList<Node> cards;
	private SimpleIntegerProperty value = new SimpleIntegerProperty(0);
	private SimpleBooleanProperty turn = new SimpleBooleanProperty(true);
	private boolean surrendered = false;
	private boolean splitted = false;
	private boolean doubled = false;
	private int aces = 0;

	/**
	 * Class constructor.
	 * 
	 * @param cards
	 *            list to hold cards in the hand.
	 */
	public Hand(ObservableList<Node> cards) {
		this.cards = cards;

		// LISTENERS FOR WHEN TURN OF HAND CHANGES
		// Applies glow-/blur effects depending whether or not it's the hand´s
		// turn.
		turn.addListener((obs, old, newValue) -> {
			if (turn.get() == true) {
				makeHandGlow(true);
			}
			if (turn.get() == false) {
				makeHandGlow(false);
			}
		});
	}

	/**
	 * Add a card to the hand.
	 * 
	 * @param card
	 *            the card to add.
	 */
	public void takeCard(Card card) {
		cards.add(card);

		// if the card is turned:
		// Don't do anything (because the value should not be updated).
		if (card.turned.get() == true) {

		} else {

			// if the card is an ace the value of the card equals 11 as long
			// as the whole value of the hand dosen't exceed 21. Otherwise the
			// value of the card equals 1.
			if (card.RANK == Rank.ACE) {
				aces++;
			}

			if (value.get() + card.VALUE > 21 && aces > 0) {
				value.set(value.get() + card.VALUE - 10);
				aces--;
			} else {
				value.set(value.get() + card.VALUE);
			}
		}
	}

	/**
	 * Remove the second card of the hand along with the value of that card and
	 * set the boolean "splitted" to true.
	 */
	public void removeCardBySplit() {
		splitted = true;
		value.setValue(value.get() / 2);
		cards.remove(1);
	}

	/**
	 * Increase the total value of the hand with the value of a specified card.
	 * 
	 * @param card
	 *            the card which value is to be added to the hand´s value
	 */
	public void addCardValue(Card card) {
		value.set(value.get() + card.VALUE);
	}

	/**
	 * Turn a card upside down (used to turn the dealers second card, making it
	 * visible).
	 * 
	 * @param card
	 *            the card to turn upside down.
	 */
	public void turnCard(Card card) {
		card.turnCard();

		if (card.RANK == Rank.ACE) {
			aces++;
		}

		if (value.get() + card.VALUE > 21 && aces > 0) {
			value.set(value.get() + card.VALUE - 10);
			aces--;
		} else {
			value.set(value.get() + card.VALUE);
		}
	}

	/**
	 * Reset the hand by removing all cards and set all values to default.
	 */
	public void reset() {
		splitted = false;
		surrendered = false;
		cards.clear();
		value.set(0);
		aces = 0;
	}

	/**
	 * Make the cards of the hand glow or make them blurry.
	 * 
	 * @param turn
	 *            whether or not to make the card glow (false makes them
	 *            blurry).
	 */
	public void makeHandGlow(Boolean turn) {

		GaussianBlur gb = new GaussianBlur();
		gb.setRadius(3);
		Glow glow = new Glow();

		if (turn == true) {
			for (int i = 0; i < cards.size(); i++) {
				cards.get(i).setEffect(glow);
			}
		} else if (turn == false) {
			for (int i = 0; i < cards.size(); i++) {
				cards.get(i).setEffect(gb);
			}
		}
	}

	public SimpleIntegerProperty getValueProperty() {
		return value;
	}

	public ObservableList<Node> getCards() {
		return cards;
	}

	public SimpleBooleanProperty getTurn() {
		return turn;
	}

	public void setTurn(Boolean t) {
		this.turn.set(t);
	}

	public boolean isSurrendered() {
		return surrendered;
	}

	public void setSurrendered(boolean surrendered) {
		this.surrendered = surrendered;
	}

	public boolean isSplitted() {
		return splitted;
	}

	public void setSplitted(boolean splitted) {
		this.splitted = splitted;
	}

	public boolean isDoubled() {
		return doubled;
	}

	public void setDoubled(boolean doubled) {
		this.doubled = doubled;
	}

}
