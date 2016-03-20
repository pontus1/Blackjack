package mvcPackage;

import javafx.scene.layout.HBox;

/**
 * An implementation of three-handed Game functioning as a concrete strategy for
 * abstract superclass (AbstractGame).
 * 
 * @author Pontus Ellboj
 *
 */
public class Game3 extends AbstractGame {

	/**
	 * Class constructor.
	 * 
	 * @param balance
	 *            the current balance on the player's account.
	 */
	Game3(int balance) {
		super(balance);

		// INT DECK WITH 208 CARDS (FOUR DECKS)
		deck = new Deck(4);

		// INIT HAND CONTAINERS (DETERMINES HOW AND WHERE THE HANDS ARE SHOWN)
		hand2Cards = new HBox(-70);
		hand3Cards = new HBox(-70);
		splitHand2Cards = new HBox(-70);
		splitHand3Cards = new HBox(-70);

		// INIT HANDS
		hand2 = new Hand(hand2Cards.getChildren());
		hand3 = new Hand(hand3Cards.getChildren());
		splitHand2 = new Hand(splitHand2Cards.getChildren());
		splitHand3 = new Hand(splitHand3Cards.getChildren());

		// BIND PROPERTIES (BINDS TO VALUE PROPERTIES OF EACH HAND)
		hand2Score = hand2.getValueProperty().asString();
		hand3Score = hand3.getValueProperty().asString();
		splitHand2Score = splitHand2.getValueProperty().asString();
		splitHand3Score = splitHand3.getValueProperty().asString();

		// BETTING
		hand2Bet = new Bet();
		splitHand2Bet = new Bet();
		hand3Bet = new Bet();
		splitHand3Bet = new Bet();

		// CHANGELISTENERS (LISTENING TO VALUE PROPERTIES OF HANDS)
		// isDoubled() IS CHECKED IN ORDER TO NOT CALL stand() TWICE
		hand1.getValueProperty().addListener((obs, old, newValue) -> {
			if ((newValue.intValue() >= 21) && (hand1.isDoubled() == false))  {
				stand();
			}
		});

		hand2.getValueProperty().addListener((obs, old, newValue) -> {
			if (((newValue.intValue() >= 21) && (hand2.getTurn().get() == true)) && (!hand2.isDoubled())) {
				stand();
			}
		});

		hand3.getValueProperty().addListener((obs, old, newValue) -> {
			if (((newValue.intValue() >= 21) && (hand3.getTurn().get() == true)) && (!hand3.isDoubled())) {
				stand();
			}
		});

		splitHand1.getValueProperty().addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				stand();
			}
		});

		splitHand2.getValueProperty().addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				stand();
			}
		});

		splitHand3.getValueProperty().addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				stand();
			}
		});

		// CHANGELISTENER FOR IF HAND1 GETS BLACKJACK
		hand1.getTurn().addListener((obs, old, newValue) -> {
			if ((hand1.getTurn().get() == true) && (hand1.getValueProperty().get() >= 21)) {
				stand();
				System.out.println("working");
			}
		});

		// BETTING (ALL BETS MUST BE HIGHER THAN 5 AND LOWER THAN 150 IN ORDER
		// TO GET CARDS DEALT)
		hand1Bet.getCurrentBet().addListener((obs, old, newValue) -> {

			if (((newValue.intValue() >= 5) && (newValue.intValue() <= 150))
					&& ((hand2Bet.getCurrentBet().get() >= 5) && (hand2Bet.getCurrentBet().get() <= 150))
					&& ((hand3Bet.getCurrentBet().get() >= 5) && (hand3Bet.getCurrentBet().get() <= 150))) {
				allBetsPlaced.set(true);
			} else {
				allBetsPlaced.set(false);
			}
		});

		hand2Bet.getCurrentBet().addListener((obs, old, newValue) -> {

			if (((newValue.intValue() >= 5) && (newValue.intValue() <= 150))
					&& ((hand1Bet.getCurrentBet().get() >= 5) && (hand1Bet.getCurrentBet().get() <= 150))
					&& ((hand3Bet.getCurrentBet().get() >= 5) && (hand3Bet.getCurrentBet().get() <= 150))) {
				allBetsPlaced.set(true);
			} else {
				allBetsPlaced.set(false);
			}
		});

		hand3Bet.getCurrentBet().addListener((obs, old, newValue) -> {

			if (((newValue.intValue() >= 5) && (newValue.intValue() <= 150))
					&& ((hand1Bet.getCurrentBet().get() >= 5) && (hand1Bet.getCurrentBet().get() <= 150))
					&& ((hand2Bet.getCurrentBet().get() >= 5) && (hand2Bet.getCurrentBet().get() <= 150))) {
				allBetsPlaced.set(true);
			} else {
				allBetsPlaced.set(false);
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#startNewGame()
	 */
	protected void startNewGame() {

		betable.set(false);
		finished.set(false);

		playable.set(true);
		surrenderable.set(true);
		hand1.setTurn(true);
		hand1.setDoubled(false);
		hand2.setTurn(false);
		hand2.setDoubled(false);
		hand3.setTurn(false);
		hand3.setDoubled(false);

		if (playerAccount.getBalance().get() >= hand1Bet.getCurrentBet().get()) {
			doubleable.set(true);
		}

		// System.out.println("hand1큦 turn");

		dealer.reset();
		hand1.reset();
		hand2.reset();
		hand3.reset();
		splitHand1.reset();
		splitHand2.reset();
		splitHand3.reset();

		dealer.takeCard(deck.dealNextCard());
		dealer.takeCard(deck.dealNextCard(true));

		hand3.takeCard(deck.dealNextCard());
		hand3.takeCard(deck.dealNextCard());
		hand3.makeHandGlow(false);

		hand2.takeCard(deck.dealNextCard());
		hand2.takeCard(deck.dealNextCard());
		hand2.makeHandGlow(false);

		hand1.takeCard(deck.dealNextCard());
		hand1.takeCard(deck.dealNextCard());

		// If the two cards dealt has the same value it is possible to split
		// them
		// into two hands. this also requires that the player큦 balance is equal
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

		surrenderable.set(false);
		doubleable.set(false);

		if (hand1.getTurn().get() == true) {
			if (splitable.get() == true) {
				splitable.set(false);
			}
			if (splitMode.get() == true) {

				// System.out.println("splitHand1큦 turn");

				splitHand1.takeCard(deck.dealNextCard());
			} else {
				hand1.takeCard(deck.dealNextCard());
			}
		} else if (hand2.getTurn().get() == true) {
			if (splitable.get() == true) {
				splitable.set(false);
			}
			if (splitMode.get() == true) {

				// System.out.println("splitHand2큦 turn");

				splitHand2.takeCard(deck.dealNextCard());
			} else {
				hand2.takeCard(deck.dealNextCard());
			}
		} else if (hand3.getTurn().get() == true) {
			if (splitable.get() == true) {
				splitable.set(false);
			}
			if (splitMode.get() == true) {

				// System.out.println("splitHand3큦 turn");

				splitHand3.takeCard(deck.dealNextCard());
			} else {
				hand3.takeCard(deck.dealNextCard());
			}
		} else {
			System.out.println("bugg");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mvcPackage.AbstractGame#stand()
	 */
	protected void stand() {
		if (hand1.getTurn().get() == true) {
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
				hand1.setTurn(false);
				hand2.setTurn(true);
				if (playerAccount.getBalance().get() >= hand2Bet.getCurrentBet().get()) {
					doubleable.set(true);
				} else {
					doubleable.set(false);
				}

				 System.out.println("hand2큦 turn");

				// If the two cards dealt has the same value it is possible to
				// split
				// them
				// into two hands.
				if (((Card) hand2.getCards().get(0)).VALUE == ((Card) hand2.getCards().get(1)).VALUE) {
					if (playerAccount.getBalance().get() >= hand2Bet.getCurrentBet().get()) {
						splitable.set(true);
					}
					// if hand2 has blackjack
				} else if (hand2.getValueProperty().get() == 21) {
					stand();
				}
			}
			// SURRENDER
			surrenderable.set(true);

		} else if (hand2.getTurn().get() == true) {
			// Makes splitable false if last hand did not split when it had the
			// option
			if (splitable.get() == true) {
				splitable.set(false);
			}

			if (splitMode.get() == true) {
				splitHand2.makeHandGlow(false);
				hand2.makeHandGlow(true);
				splitMode.set(false);
			} else {
				hand2.setTurn(false);
				hand3.setTurn(true);
				if (playerAccount.getBalance().get() >= hand3Bet.getCurrentBet().get()) {
					doubleable.set(true);
				} else {
					doubleable.set(false);
				}

				// System.out.println("hand3큦 turn");

				// If the two cards dealt has the same value it is possible to
				// split
				// them
				// into two hands.
				if (((Card) hand3.getCards().get(0)).VALUE == ((Card) hand3.getCards().get(1)).VALUE) {
					if (playerAccount.getBalance().get() >= hand3Bet.getCurrentBet().get()) {
						splitable.set(true);
					}
					// if hand3 has blackjack
				} else if (hand3.getValueProperty().get() == 21) {
					stand();
				}
			}
			if (hand3.getValueProperty().get() != 21) {
				surrenderable.set(true);
			}

		} else if (hand3.getTurn().get() == true) {
			if (splitable.get() == true) {
				splitable.set(false);
			}

			if (splitMode.get() == true) {
				splitHand3.makeHandGlow(false);
				hand3.makeHandGlow(true);
				splitMode.set(false);
			} else {

				// System.out.println("dealers turn");

				// If the dealers card is turned (back side facing player) the
				// card
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

		if (hand1.getTurn().get() == true) {
			splitHand1Bet.placeBet(hand1Bet.getCurrentBet().get());
			playerAccount.decreaseBalance(hand1Bet.getCurrentBet().get());

			splitCard = (Card) hand1.getCards().get(1);
			hand1.removeCardBySplit();
			splitHand1Cards.getChildren().add(splitCard);
			splitHand1.addCardValue(splitCard);
			splitHand1.setSplitted(true);
			splitHand1Score = hand1.getValueProperty().asString();
			hand1.makeHandGlow(false);
		} else if (hand2.getTurn().get() == true) {
			splitHand2Bet.placeBet(hand2Bet.getCurrentBet().get());
			playerAccount.decreaseBalance(hand2Bet.getCurrentBet().get());

			splitCard = (Card) hand2.getCards().get(1);
			hand2.removeCardBySplit();
			splitHand2Cards.getChildren().add(splitCard);
			splitHand2.addCardValue(splitCard);
			splitHand2.setSplitted(true);
			splitHand2Score = hand2.getValueProperty().asString();
			hand2.makeHandGlow(false);
		} else {
			splitHand3Bet.placeBet(hand3Bet.getCurrentBet().get());
			playerAccount.decreaseBalance(hand3Bet.getCurrentBet().get());

			splitCard = (Card) hand3.getCards().get(1);
			hand3.removeCardBySplit();
			splitHand3Cards.getChildren().add(splitCard);
			splitHand3.addCardValue(splitCard);
			splitHand3.setSplitted(true);
			splitHand3Score = hand3.getValueProperty().asString();
			hand3.makeHandGlow(false);
		}
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
		} else if ((hand2.getTurn().get() == true) && (doubleable.get() == true)) {
			// doubleable.set(false);
			hand2.setDoubled(true);

			playerAccount.decreaseBalance(hand2Bet.getCurrentBet().get());
			hand2Bet.setCurrentBet(hand2Bet.getCurrentBet().get() * 2);
			hand2.takeCard(deck.dealNextCard());
			stand();
		} else if ((hand3.getTurn().get() == true) && (doubleable.get() == true)) {
			// doubleable.set(false);
			hand3.setDoubled(true);

			playerAccount.decreaseBalance(hand3Bet.getCurrentBet().get());
			hand3Bet.setCurrentBet(hand3Bet.getCurrentBet().get() * 2);
			hand3.takeCard(deck.dealNextCard());
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
		if (hand1.getTurn().get() == true) {
			hand1.setSurrendered(true);
		} else if (hand2.getTurn().get() == true) {
			hand2.setSurrendered(true);
		} else {
			hand3.setSurrendered(true);
		}
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
		// String[] splittedResult;
		int balance = 0;

		Hand[] allHands = { hand1, splitHand1, hand2, splitHand2, hand3, splitHand3 };
		Bet[] allBets = { hand1Bet, splitHand1Bet, hand2Bet, splitHand2Bet, hand3Bet, splitHand3Bet };

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
		// splittedResult = result.split("-");

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
	protected void clearTable() {
		hand1Bet.removeBet();
		hand2Bet.removeBet();
		hand3Bet.removeBet();
		splitHand1Bet.removeBet();
		splitHand2Bet.removeBet();
		splitHand3Bet.removeBet();
		dealer.reset();
		hand1.reset();
		hand2.reset();
		hand3.reset();
		splitHand1.reset();
		splitHand2.reset();
		splitHand3.reset();
	}
}
