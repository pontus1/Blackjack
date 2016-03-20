package mvcPackage;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * An implementation of Card type.
 * 
 * @author Pontus Ellboj
 */
public class Card extends Parent {

	/**
	 * Suit of card.
	 */
	protected final Suit SUIT;

	/**
	 * Rank of card.
	 */
	protected final Rank RANK;

	/**
	 * Value of card.
	 */
	protected final int VALUE;
	
	// IMAGEVIEW USED FOR PICTURES OF CARDS 
	private ImageView viewCard;

	/**
	 * The side of the card facing the player. True if the card is turned upside
	 * down.
	 */
	protected SimpleBooleanProperty turned = new SimpleBooleanProperty(false);

	/**
	 * Class constructor
	 * 
	 * @param suit
	 *            the suit of the card
	 * @param rank
	 *            the rank of the card
	 */
	public Card(Suit suit, Rank rank) {

		this.SUIT = suit;
		this.RANK = rank;
		this.VALUE = rank.getValue();

		Image cardImage = new Image("images/" + rank.toString() + "of" + suit.toString() + ".png", 125, 125, true,
				true);
		viewCard = new ImageView(cardImage);

		getChildren().add(new StackPane(viewCard));
	}

	/**
	 * Turn the card upside down (or turn it up if it's already upside down).
	 */
	public void turnCard() {

		if (turned.get() == false) {
			Image backImage = new Image("images/BACKSIDE.png", 125, 125, true, true);
			ImageView backside = new ImageView(backImage);
			getChildren().clear();
			getChildren().add(new StackPane(backside));
			turned.set(true);
		} else {
			getChildren().clear();
			getChildren().add(new StackPane(viewCard));
			turned.set(false);
		}

	}
}
