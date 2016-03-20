package mvcPackage;

/**
 * An implementation of one-handed Game functioning as a concrete strategy for
 * abstract superclass (AbstractGame).
 * 
 * @author Pontus Ellboj
 *
 */
public class Game1 extends AbstractGame {

	/**
	 * Class constructor.
	 * 
	 * @param balance
	 *            the current balance on the player's account.
	 */
	protected Game1(int balance) {
		super(balance);

		// INIT DECK WITH 52 CARDS
		deck = new Deck();

		// CHANGELISTENERS (LISTENING TO VALUE PROPERTIES OF HANDS)
		hand1.getValueProperty().addListener((obs, old, newValue) -> {
			if ((newValue.intValue() >= 21) && (!hand1.isDoubled())) {
				stand();
			}
		});

		splitHand1.getValueProperty().addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				stand();
			}
		});

		// CHANGELISTENER FOR BETTING (BET MUST BE HIGHER THAN 5 AND LOWER
		// THAN 150 IN ORDER TO GET CARDS DEALT)
		hand1Bet.getCurrentBet().addListener((obs, old, newValue) -> {
			if ((newValue.intValue() >= 5) && (newValue.intValue() <= 150)) {
				allBetsPlaced.set(true);
			} else {
				allBetsPlaced.set(false);
			}
		});
	}

	// ************ OVERRIDE ************
	//
	// THIS METHOD WILL OVERRIDE (REPLACE) THE ABSTRACT METHOD WITH THE SAME
	// NAME IN "ABSTRACTGAME" IF AN INSTANCE OF THIS CLASS IS ASSIGNED TO
	// "ABSTRACTGAME".
	// THIS METHOD WILL OBVIOUSLY ALSO RUN IF IT WOULD BE CALLED DIRECTLY FROM
	// AN INSTANCE OF THIS OBJECT.
	// THE ANNOTATION IS OPTIONAL BUT INDICATES THAT THE SIGNATURE OF THE METHOD
	// IS INHERITED (YET OVERRIDDEN). SINCE IT DOESEN'T GIVE A COMPILE-TIME
	// ERROR IT ALSO INDICATES THAT IT IS WRITTEN CORRECTLY.

	@Override
	protected void startNewGame() {

		betable.set(false);
		finished.set(false);

		playable.set(true);
		surrenderable.set(true);
		hand1.setTurn(true);
		hand1.setDoubled(false);
		// splitMode.set(false);

		if (playerAccount.getBalance().get() >= hand1Bet.getCurrentBet().get()) {
			doubleable.set(true);
		}

		//System.out.println("hand1´s turn");

		dealer.reset();
		hand1.reset();
		splitHand1.reset();

		dealer.takeCard(deck.dealNextCard());
		dealer.takeCard(deck.dealNextCard(true));

		hand1.takeCard(deck.dealNextCard());
		hand1.takeCard(deck.dealNextCard());

		// If the two cards dealt has the same value it is possible to split
		// them
		// into two hands. this also requires that the player´s balance is equal
		// to
		// or greater than the initial bet.
		if (((Card) hand1.getCards().get(0)).VALUE == ((Card) hand1.getCards().get(1)).VALUE) {
			if (playerAccount.getBalance().get() >= hand1Bet.getCurrentBet().get()) {
				splitable.set(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#hit()
	 */
	@Override
	protected void hit() {
		// only for one hand:
		surrenderable.set(false);
		doubleable.set(false);

		// Makes splitable false if last hand did not split
		if (splitable.get() == true) {
			splitable.set(false);
		}
		if (splitMode.get() == true) {

			splitHand1.takeCard(deck.dealNextCard());
		} else {
			hand1.takeCard(deck.dealNextCard());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#stand()
	 */
	@Override
	protected void stand() {
		// only for one hand:
		// surrenderable.set(false);
		// Makes splitable false if last hand did not split when it had the
		// option
		if (splitable.get() == true) {
			splitable.set(false);
		}

		if (splitMode.get() == true) {
			splitHand1.makeHandGlow(false);
			hand1.makeHandGlow(true);
			splitMode.set(false);
		} else {
			// If the dealers card is turned (back side facing player) the card
			// will be turned back. The value of the card will be added to
			// dealers total score.
			if (((Card) dealer.getCards().get(1)).turned.get() == true) {
				dealer.turnCard(((Card) dealer.getCards().get(1)));
			}
			while (dealer.getValueProperty().get() < 17) {
				dealer.takeCard(deck.dealNextCard());
			}
			endGame();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#split()
	 */
	@Override
	protected void split() {

		surrenderable.set(false);
		doubleable.set(false);

		splitable.set(false);
		splitMode.set(true);

		splitHand1Bet.placeBet(hand1Bet.getCurrentBet().get());
		playerAccount.decreaseBalance(hand1Bet.getCurrentBet().get());

		splitCard = (Card) hand1.getCards().get(1);
		hand1.removeCardBySplit();

		splitHand1Cards.getChildren().add(splitCard);
		splitHand1.addCardValue(splitCard);
		splitHand1.setSplitted(true);

		splitHand1Score = hand1.getValueProperty().asString();

		hand1.makeHandGlow(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#doubleDown()
	 */
	@Override
	protected void doubleDown() {
		if ((hand1.getTurn().get() == true) && (doubleable.get() == true)) {
			// doubleable.set(false);
			hand1.setDoubled(true);

			playerAccount.decreaseBalance(hand1Bet.getCurrentBet().get());
			hand1Bet.setCurrentBet(hand1Bet.getCurrentBet().get() * 2);
			hand1.takeCard(deck.dealNextCard());
			stand();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#surrender()
	 */
	@Override
	protected void surrender() {
		hand1.setSurrendered(true);
		stand();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#endGame()
	 */
	@Override
	protected void endGame() {

		int dealerValue = dealer.getValueProperty().get();
		String result = "";
		int balance = 0;

		Hand[] allHands = { hand1, splitHand1 };
		Bet[] allBets = { hand1Bet, splitHand1Bet };

		for (int i = 0; i < allHands.length; i++) {

			allHands[i].makeHandGlow(true);

			int handValue = allHands[i].getValueProperty().get();

			if (allHands[i].getValueProperty().get() == 0) {
				result += " -";

			} else if (allHands[i].isSurrendered()) {
				balance += (allBets[i].getCurrentBet().get() / 2);
				result += "SURRENDERED-";

			} else if ((handValue == 21 && allHands[i].getCards().size() == 2) && (allHands[i].isSplitted() == false)
					& !((dealerValue == 21) && (dealer.getCards().size() == 2))) {
				balance += (allBets[i].getCurrentBet().get() * 2.5);
				result += "BLACKJACK-";

			} else if (((dealerValue == handValue) & !((dealer.getCards().size() == 2) && (dealerValue == 21)))
					& !(handValue > 21)) {
				balance += (allBets[i].getCurrentBet().get());
				result += "PUSH-";

			} else if (handValue <= 21 && (dealerValue > 21 || dealerValue < handValue)) {
				balance += (allBets[i].getCurrentBet().get() * 2);
				result += "WON-";

			} else if (handValue > 21) {
				result += "BUSTED-";
			} else {
				result += "LOST-";
			}
		}

		gameResult = result.split("-");
		playerAccount.increaseBalance(balance);

		finished.set(true);
		allBetsPlaced.set(false);
		playable.set(false);
		surrenderable.set(false);
		doubleable.set(false);

		if (playerAccount.getBalance().get() < 5) {
			gameOver.set(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#clearTable()
	 */
	@Override
	protected void clearTable() {
		hand1Bet.removeBet();
		splitHand1Bet.removeBet();
		dealer.reset();
		hand1.reset();
		splitHand1.reset();
	}
}
