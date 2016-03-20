package mvcPackage;

import java.io.File;
import java.util.Random;
import javafx.scene.Parent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

// ************ AGGREGATION ************
//
// THE DECK CLASS HAS AN AGGREGATION RELATIONSHIP 
// WITH THE CARD CLASS. THIS IS BECAUSE THE DECK CLASS HAS INSTANCES OF CARD, 
// BUT CARD ALSO EXIST ON IT'S OWN AND MAY BE INSTANTIATED BY OTHER CLASSES 
// (WITHOUT THE DECK CLASS).


/**
 * An implementation of a deck filled with cards. Extends Parent in order to
 * play audio files.
 * 
 * @author Pontus Ellboj
 *
 */
public class Deck extends Parent {

	private int numberOfDecks;
	private int numberOfCards;
	private Card[] cards;

	/**
	 * Constructor that creates set of 52 cards * the number of decks chosen.
	 * 
	 * @param numberOfDecks
	 *            the number of regular decks (52 cards) to create the deck with
	 */
	public Deck(int numberOfDecks) {

		this.numberOfDecks = numberOfDecks;
		numberOfCards = numberOfDecks * 52;
		cards = new Card[numberOfCards];

		refill(numberOfDecks);
		shuffle();
	}

	/**
	 * Overloaded constructor that creates one deck, a set of 52 cards (all
	 * values in a regular deck).
	 */
	public Deck() {
		this(1);
	}

	// SHUFFLE DECK BY RANDOMLY SWPPING PAIRS OF CARDS.
	// Goes through the hole deck and swap each card with another card chosen
	// randomly from the deck (j).
	private void shuffle() {

		Random rand = new Random();
		Card temp;

		int j;
		for (int i = 0; i < numberOfCards; i++) {

			j = rand.nextInt(numberOfCards);
			temp = cards[i];
			cards[i] = cards[j];
			cards[j] = temp;
		}
		playMedia("Shuffle");
	}

	/**
	 * Deal the next card from the top of the deck. Takes the top card of the
	 * deck ([0]), removes it from the array of cards and than returns the the
	 * card. If the deck is empty "refill" is called.
	 * 
	 * @return the dealt card
	 */
	public Card dealNextCard() {

		if (numberOfCards == 0) {
			refill(numberOfDecks);
			numberOfCards = numberOfDecks * 52;
			shuffle();
		}

		Card top = cards[0];

		for (int i = 1; i < numberOfCards; i++) {
			cards[i - 1] = cards[i];
		}
		cards[numberOfCards - 1] = null;

		numberOfCards--;

		playMedia("dealCard");

		return top;
	}

	// ************ OVERLOADING ************
	//
	// HERE IS AN EXAMPLE OF AN OVERLOADED METHOD. METHOD OVERLOADING IN JAVA
	// IS WHEN A CLASS HAS TWO DIFFERENT METHODS (OR CONSTRUCTORS) WITH THE
	// EXACT SAME NAME BUT WITH DIFFERENT PARAMETERS. THIS CLASS HAS TWO METHODS
	// CALLED "DealNextCard". THE FIRST TAKES NO PARAMETERS AND THE SECOND
	// TAKES A BOOLEAN.

	/**
	 * Overloaded method (this one takes a boolean parameter). Deal the next
	 * card from the top of the deck. But instead of showing the cards value it
	 * is upside down.
	 * 
	 * @param upsideDown
	 *            true if the card is to be dealt upside down.
	 * 
	 * @return the dealt card upside down (if true is passed as argument).
	 */
	public Card dealNextCard(Boolean upsideDown) {

		if (numberOfCards == 0) {
			refill(numberOfDecks);
			numberOfCards = numberOfDecks * 52;
			shuffle();
		}

		Card top = cards[0];
		for (int i = 1; i < numberOfCards; i++) {
			cards[i - 1] = cards[i];
		}
		cards[numberOfCards - 1] = null;

		numberOfCards--;

		top.turnCard();

		playMedia("dealCard");

		return top;
	}

	// REFILL DECK
	// Goes through all possible values (suit and rank) of a deck and creates a
	// card for each. This is repeated once for every number of regular decks
	// the deck is created with (numberOfDecks).
	private void refill(int numberOfDecks) {
		int i = 0;

		for (int j = 0; j < numberOfDecks; j++) {
			for (Suit suit : Suit.values()) {
				for (Rank rank : Rank.values()) {
					cards[i++] = new Card(suit, rank);
				}
			}

		}
	}

	// PLAY AUDIO-FILE
	private void playMedia(String audio) {

		File audioFile;
		final String PATH = "src/audio/shuffle.mp3";
		final String PATH2 = "src/audio/cardFlip.wav";

		if (audio.equals("Shuffle")) {
			audioFile = new File(PATH);
		} else if (audio.equals("dealCard")) {
			audioFile = new File(PATH2);
		} else {
			audioFile = null;
			System.out.println("audio error");
		}

		Media media = new Media(audioFile.toURI().toString());
		MediaPlayer mplayer = new MediaPlayer(media);
		mplayer.setAutoPlay(true);

	}
}
