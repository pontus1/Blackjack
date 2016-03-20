package mvcPackage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MVCController implements Initializable {

	// @FXML AnchorPane root;

	@FXML
	private BorderPane borderPane;

	@FXML
	private VBox vBox, vbh1, vbh2, vbh3, vbsplith1, vbsplith2, vbsplith3;

	@FXML
	private HBox hb1, hb2, hb3;

	@FXML
	private VBox vBoxButtons;

	@FXML
	private HBox hBoxGameButtons, hBoxBetButtons;

	@FXML
	private HBox dealerCards, playerCards, player2Cards, player3Cards, splitPlayerCards, splitPlayerCards2,
			splitPlayerCards3;

	@FXML
	private StackPane stack1, stack2, stack3;

	@FXML
	private Rectangle rect1, rect2, rect3;

	@FXML
	private RadioButton rbtnOne, rbtnTwo, rbtnThree;

	@FXML
	private Button btnChangeNumOfHands, btnLeaderBoard, btnSaveNQuit;

	@FXML
	private Button btnDealCards, btnHit, btnStand, btnSplit, btnDoubleDown, btnSurrender;

	@FXML
	private Button btn1$, btn5$, btn25$, btn50$, btn100$, btnRemoveBet;

	@FXML
	private Text dealerScore, hand1Score, hand2Score, hand3Score, splitHand1Score, splitHand2Score, splitHand3Score;

	@FXML
	private Label message, lblBalance, lblDisplayBalance, lblTotBet, lblDisplayTotBet;
	
	// GAME OVER
	@FXML
	private Text txtGameOver;
	
	@FXML
	private Button btnClose;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private AbstractGame game;

	private int balance;

	private SimpleBooleanProperty sessionRunning = new SimpleBooleanProperty(false);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// HIDE GAMEOVER NODES
		txtGameOver.setVisible(false);
		btnClose.setVisible(false);

		btnRemoveBet.setText("REMOVE\nBET");

		// INIT BALANCE TO START WITH
		balance = 500;

		// TOGGLE GROUP FOR RADIOBUTTONS
		ToggleGroup tg = new ToggleGroup();
		rbtnOne.setToggleGroup(tg);
		rbtnTwo.setToggleGroup(tg);
		rbtnThree.setToggleGroup(tg);

		startSession(1, balance);

		// SET RADIOBUTTONS CHANGING NUMBER OF HANDS ON ACTION
		btnChangeNumOfHands.setOnAction(event -> {
			if (tg.getSelectedToggle().equals(rbtnOne)) {
				removeAllHands();
				sessionRunning.set(true);
				startSession(1, balance);
			} else if (tg.getSelectedToggle().equals(rbtnTwo)) {
				removeAllHands();
				sessionRunning.set(true);
				startSession(2, balance);
			} else if (tg.getSelectedToggle().equals(rbtnThree)) {
				removeAllHands();
				sessionRunning.set(true);
				startSession(3, balance);
			}
		});
	}

	/**
	 * Start a new game session with a slightly different logic depending on
	 * number of hands chosen to play with.
	 * 
	 * @param numberOfHands
	 *            the number of hands to play with (1 -3).
	 * 
	 * @param currentBalance
	 *            the players current balance.
	 */
	private void startSession(int numberOfHands, int currentBalance) {

		// INFO CONTAINERS CONNECTED TO EACH HAND
		VBox hand1Message = new VBox(85);
		VBox hand2Message = new VBox(85);
		VBox hand3Message = new VBox(85);
		VBox splitHand1Message = new VBox(85);
		VBox splitHand2Message = new VBox(85);
		VBox splitHand3Message = new VBox(85);

		// INFO CONNECTED TO EACH HAND (RESULTS & BETS)
		Text hand1Result = new Text();
		Text hand2Result = new Text();
		Text hand3Result = new Text();
		Text splitHand1Result = new Text();
		Text splitHand2Result = new Text();
		Text splitHand3Result = new Text();

		Text hand1Bet = new Text();
		Text hand2Bet = new Text();
		Text hand3Bet = new Text();
		Text splitHand1Bet = new Text();
		Text splitHand2Bet = new Text();
		Text splitHand3Bet = new Text();

		Text hand1$ = new Text("$");
		Text hand2$ = new Text("$");
		Text hand3$ = new Text("$");
		Text splitHand1$ = new Text("$");
		Text splitHand2$ = new Text("$");
		Text splitHand3$ = new Text("$");

		HBox hand1BetInfo = new HBox(hand1$, hand1Bet);
		HBox hand2BetInfo = new HBox(hand2$, hand2Bet);
		HBox hand3BetInfo = new HBox(hand3$, hand3Bet);
		HBox splitHand1BetInfo = new HBox(splitHand1$, splitHand1Bet);
		HBox splitHand2BetInfo = new HBox(splitHand2$, splitHand2Bet);
		HBox splitHand3BetInfo = new HBox(splitHand3$, splitHand3Bet);

		// TEXT SHOWING THE HAND CURRENTLY AVAILIBLE TO BET ON
		Text textPlaceBet = new Text("PLACE\nBET");
		textPlaceBet.setTextAlignment(TextAlignment.CENTER);

		// STYLE INFO WITH CSS
		hand1Result.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: #7e7e80;");
		hand2Result.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: #7e7e80;");
		hand3Result.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: #7e7e80;");
		splitHand1Result.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: #7e7e80;");
		splitHand2Result.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: #7e7e80;");
		splitHand3Result.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: #7e7e80;");
		hand1Bet.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; -fx-fill: gold;");
		hand2Bet.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; -fx-fill: gold;");
		hand3Bet.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; -fx-fill: gold;");
		splitHand1Bet.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; -fx-fill: gold;");
		splitHand2Bet.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; -fx-fill: gold;");
		splitHand3Bet.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; -fx-fill: gold;");
		hand1$.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: gold;");
		hand2$.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: gold;");
		hand3$.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: gold;");
		splitHand1$.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: gold;");
		splitHand2$.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: gold;");
		splitHand3$.setStyle("-fx-font-family: 'Bodoni MT'; -fx-fill: gold;");
		dealerScore
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill:  #7e7e80"/* DarkGreen: #041E05 */);
		hand1Score
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #7e7e80");
		hand2Score
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #7e7e80");
		hand3Score
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #7e7e80");
		splitHand1Score
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #7e7e80");
		splitHand2Score
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #7e7e80");
		splitHand3Score
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 20; -fx-font-weight: bold; -fx-fill: #7e7e80");
		textPlaceBet
				.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 16; -fx-font-weight: bold; -fx-fill: #7e7e80");

		// STYLE TEXT WITH EFFECT
		Bloom b = new Bloom();

		hand1Result.setEffect(b);
		hand2Result.setEffect(b);
		hand3Result.setEffect(b);
		splitHand1Result.setEffect(b);
		splitHand2Result.setEffect(b);
		splitHand3Result.setEffect(b);

		hand1Bet.setEffect(b);
		hand2Bet.setEffect(b);
		hand3Bet.setEffect(b);
		splitHand1Bet.setEffect(b);
		splitHand2Bet.setEffect(b);
		splitHand3Bet.setEffect(b);

		// ALIGN INFO
		dealerScore.setTranslateX(20);
		hand1Score.setTranslateX(8);
		hand2Score.setTranslateX(8);
		hand3Score.setTranslateX(8);
		splitHand1Score.setTranslateX(8);
		splitHand2Score.setTranslateX(8);
		splitHand3Score.setTranslateX(8);

		textPlaceBet.setTranslateX(20);
		textPlaceBet.setTranslateY(45);

		hand1BetInfo.setPrefWidth(35);
		hand2BetInfo.setPrefWidth(35);
		hand3BetInfo.setPrefWidth(35);
		splitHand1BetInfo.setPrefWidth(35);
		splitHand2BetInfo.setPrefWidth(35);
		splitHand3BetInfo.setPrefWidth(35);

		// SWITCH - CASE CHOOSING GAME_TYPE
		//
		// EACH TYPE OF GAME "IS AN" ABSTRACTGAME SINCE IT INHERITS FROM
		// ABSTRACTGAME.
		// IN THIS CASE A DIFFERENT SUBCLASS IS CREATED IN EACH "CASE" AND THEN
		// ASSIGNED TO THE SUPERCLASS.
		// THE SUPERCLASS CONTAINS A BUNCH OF METHODS THAT ARE OVERRIDDEN BY THE
		// SUBCLASSES. WHENEVER ONE OF THESE METHODS ARE CALLED ON THE
		// SUPERCLASS REFERENCE (game) THE SUBCLASS VERSION OF THAT METHOD WILL
		// BE CALLED. THIS HAPPENS AT RUNTIME AND IS CALLED 
		// DYNAMIC POLYMORPHISM.
		switch (numberOfHands) {
		case 1:
			game = new Game1(currentBalance);
			break;
		case 2:
			game = new Game2(currentBalance);
			break;
		case 3:
			game = new Game3(currentBalance);
			break;
		default:
			break;
		}

		// SPLITHANDS ARE SET TO INVISIBLE UNTIL A HAND SPLITS
		splitPlayerCards.setVisible(false);
		splitPlayerCards2.setVisible(false);
		splitPlayerCards3.setVisible(false);

		// The main purpose of the following bindings is to disable buttons when
		// they're not supposed to be used and vice versa.

		// BIND PROPERTIES "CHANGE NUMBER OF HANDS"
		// (THEESE NEED TO BE BOUND TO THE TOTAL BET ON THE TABLE TO PREVENT
		// MONEY FROM BEING LOST WHEN GAME-TYPE IS CHANGED)
		btnChangeNumOfHands.disableProperty().bind(lblDisplayTotBet.textProperty().greaterThan("0"));
		rbtnOne.disableProperty().bind(lblDisplayTotBet.textProperty().greaterThan("0"));
		rbtnTwo.disableProperty().bind(lblDisplayTotBet.textProperty().greaterThan("0"));
		rbtnThree.disableProperty().bind(lblDisplayTotBet.textProperty().greaterThan("0"));

		// BIND PROPERTIES TEXT
		dealerScore.textProperty().bind(game.dealerScore);
		hand1Score.textProperty().bind(game.hand1Score);
		splitHand1Score.textProperty().bind(game.splitHand1Score);

		// BIND PROPERTIES BETTING ("lblDisplayTotBet" NEEDS TO BE BOUND
		// DIFFRENTLY DEPENDING ON GAME-TYPE)
		hand1Bet.textProperty().bind(game.hand1Bet.getCurrentBet().asString());
		splitHand1Bet.textProperty().bind(game.splitHand1Bet.getCurrentBet().asString());
		lblDisplayBalance.textProperty().bind(game.playerAccount.getBalance().asString());

		if (game instanceof Game1) {
			lblDisplayTotBet.textProperty().bind(game.hand1Bet.getCurrentBet().asString());
		} else if (game instanceof Game2) {
			lblDisplayTotBet.textProperty()
					.bind(game.hand1Bet.getCurrentBet().add(game.hand2Bet.getCurrentBet()).asString());
		} else if (game instanceof Game3) {
			lblDisplayTotBet.textProperty().bind(game.hand1Bet.getCurrentBet()
					.add(game.hand2Bet.getCurrentBet().add(game.hand3Bet.getCurrentBet())).asString());
		}

		// BIND PROPERTIES: BET-BUTTONS
		btn1$.disableProperty().bind(Bindings.or(game.betable.not(), game.playerAccount.getBalance().lessThan(1)));
		btn5$.disableProperty().bind(Bindings.or(game.betable.not(), game.playerAccount.getBalance().lessThan(5)));
		btn25$.disableProperty().bind(Bindings.or(game.betable.not(), game.playerAccount.getBalance().lessThan(25)));
		btn50$.disableProperty().bind(Bindings.or(game.betable.not(), game.playerAccount.getBalance().lessThan(50)));
		btn100$.disableProperty().bind(Bindings.or(game.betable.not(), game.playerAccount.getBalance().lessThan(100)));
		btnRemoveBet.disableProperty().bind(game.betable.not());

		// BIND PROPERTIES: GAME-BUTTONS
		btnDealCards.disableProperty().bind(Bindings.or(game.playable, game.allBetsPlaced.not()));
		btnHit.disableProperty().bind(game.playable.not());
		btnStand.disableProperty().bind(game.playable.not());
		btnDoubleDown.disableProperty().bind(game.doubleable.not());
		btnSurrender.disableProperty().bind(game.surrenderable.not());
		btnSplit.disableProperty().bind(game.splitable.not());

		// ADD INFO TO FIRST HAND AND DEALER
		hand1Message.getChildren().addAll(hand1Score, hand1BetInfo);
		splitHand1Message.getChildren().addAll(splitHand1Score, splitHand1BetInfo);
		dealerCards.getChildren().addAll(dealerScore, game.dealerCards);
		playerCards.getChildren().addAll(hand1Message, game.hand1Cards);
		splitPlayerCards.getChildren().addAll(splitHand1Message, game.splitHand1Cards);
		vbh1.getChildren().add(hand1Result);
		vbsplith1.getChildren().add(splitHand1Result);

		if (game instanceof Game2 || game instanceof Game3) {

			// BIND PROPERTIES TEXT
			hand2Score.textProperty().bind(game.hand2Score);
			splitHand2Score.textProperty().bind(game.splitHand2Score);

			// BIND PROPERTIES BETTING
			hand2Bet.textProperty().bind(game.hand2Bet.getCurrentBet().asString());
			splitHand2Bet.textProperty().bind(game.splitHand2Bet.getCurrentBet().asString());

			// ADD INFO TO SECOND HAND
			hand2Message.getChildren().addAll(hand2Score, hand2BetInfo);
			splitHand2Message.getChildren().addAll(splitHand2Score, splitHand2BetInfo);
			player2Cards.getChildren().addAll(hand2Message, game.hand2Cards);
			splitPlayerCards2.getChildren().addAll(splitHand2Message, game.splitHand2Cards);
			vbh2.getChildren().add(hand2Result);
			vbsplith2.getChildren().add(splitHand2Result);
		}

		if (game instanceof Game3) {

			// BIND PROPERTIES TEXT
			hand3Score.textProperty().bind(game.hand3Score);
			splitHand3Score.textProperty().bind(game.splitHand3Score);

			// BIND PROPERTIES BETTING
			hand3Bet.textProperty().bind(game.hand3Bet.getCurrentBet().asString());
			splitHand3Bet.textProperty().bind(game.splitHand3Bet.getCurrentBet().asString());

			// ADD INFO TO THIRD HAND
			hand3Message.getChildren().addAll(hand3Score, hand3BetInfo);
			splitHand3Message.getChildren().addAll(splitHand3Score, splitHand3BetInfo);
			player3Cards.getChildren().addAll(hand3Message, game.hand3Cards);
			splitPlayerCards3.getChildren().addAll(splitHand3Message, game.splitHand3Cards);
			vbh3.getChildren().add(hand3Result);
			vbsplith3.getChildren().add(splitHand3Result);
		}

		// SET GAME-BUTTONS ON ACTION
		btnDealCards.setOnAction(event -> {
			game.startNewGame();
			playerCards.getChildren().remove(textPlaceBet);
			player2Cards.getChildren().remove(textPlaceBet);
			player3Cards.getChildren().remove(textPlaceBet);
		});

		btnHit.setOnAction(event -> {
			game.hit();
		});

		btnStand.setOnAction(event -> {
			game.stand();
		});

		btnSplit.setOnAction(event -> {
			game.split();
		});

		btnDoubleDown.setOnAction(event -> {
			game.doubleDown();
		});

		btnSurrender.setOnAction(event -> {
			game.surrender();
		});

		// SET LEADER BOARD BUTTONS ON ACTION
		btnLeaderBoard.setOnAction(event -> {
			openLeaderBoardWindow();
		});

		btnSaveNQuit.setOnAction(event -> {
			openSaveWindow();
		});

		// SET HAND-CONTAINERS ON ACTION
		playerCards.setOnMouseClicked(event -> {
			if ((!playerCards.getChildren().contains(textPlaceBet)) && (game.playable.get() == false)) {
				playerCards.getChildren().add(1, textPlaceBet);
			}
		});

		player2Cards.setOnMouseClicked(event -> {
			if ((!player2Cards.getChildren().contains(textPlaceBet)) && (game.playable.get() == false)
					&& (game instanceof Game2 || game instanceof Game3)) {
				player2Cards.getChildren().add(1, textPlaceBet);
			}
		});

		player3Cards.setOnMouseClicked(event -> {
			if ((!player3Cards.getChildren().contains(textPlaceBet)) && (game.playable.get() == false)
					&& (game instanceof Game3)) {
				player3Cards.getChildren().add(1, textPlaceBet);
			}
		});

		// SET BORDERPANE ON ACTION (CLEARS TABLE IF A GAME IS FINNISHED)
		borderPane.setOnMouseClicked(event -> {
			if ((game.finished.get() == true)) {
				game.clearTable();
				game.betable.set(true);
				game.finished.set(false);

				hand1Result.setText("");
				hand2Result.setText("");
				hand3Result.setText("");
				splitHand1Result.setText("");
				splitHand2Result.setText("");
				splitHand3Result.setText("");

				splitPlayerCards.setVisible(false);
				splitPlayerCards2.setVisible(false);
				splitPlayerCards3.setVisible(false);
			}
		});

		// SET BETTING BUTTONS ON ACTION
		btn1$.setOnAction(event -> {
			if (playerCards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(1);
				game.hand1Bet.placeBet(1);
			} else if (player2Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(1);
				game.hand2Bet.placeBet(1);
			} else if (player3Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(1);
				game.hand3Bet.placeBet(1);
			}
		});

		btn5$.setOnAction(event -> {
			if (playerCards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(5);
				game.hand1Bet.placeBet(5);
			} else if (player2Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(5);
				game.hand2Bet.placeBet(5);
			} else if (player3Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(5);
				game.hand3Bet.placeBet(5);
			}
		});

		btn25$.setOnAction(event -> {
			if (playerCards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(25);
				game.hand1Bet.placeBet(25);
			} else if (player2Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(25);
				game.hand2Bet.placeBet(25);
			} else if (player3Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(25);
				game.hand3Bet.placeBet(25);
			}
		});

		btn50$.setOnAction(event -> {
			if (playerCards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(50);
				game.hand1Bet.placeBet(50);
			} else if (player2Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(50);
				game.hand2Bet.placeBet(50);
			} else if (player3Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(50);
				game.hand3Bet.placeBet(50);
			}
		});

		btn100$.setOnAction(event -> {
			if (playerCards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(100);
				game.hand1Bet.placeBet(100);
			} else if (player2Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(100);
				game.hand2Bet.placeBet(100);
			} else if (player3Cards.getChildren().contains(textPlaceBet)) {
				game.playerAccount.decreaseBalance(100);
				game.hand3Bet.placeBet(100);
			}
		});

		btnRemoveBet.setOnAction(event -> {
			if (playerCards.getChildren().contains(textPlaceBet)) {
				int bet = game.hand1Bet.getCurrentBet().get();
				game.hand1Bet.setCurrentBet(0);
				game.playerAccount.increaseBalance(bet);

			} else if (player2Cards.getChildren().contains(textPlaceBet)) {
				int bet = game.hand2Bet.getCurrentBet().get();
				game.hand2Bet.setCurrentBet(0);
				game.playerAccount.increaseBalance(bet);

			} else if (player3Cards.getChildren().contains(textPlaceBet)) {
				int bet = game.hand3Bet.getCurrentBet().get();
				game.hand3Bet.setCurrentBet(0);
				game.playerAccount.increaseBalance(bet);
			}
		});

		// LISTENERS FOR RESULT OF THE GAME AND SPLIT-HANDS
		// (DIFFERS DEPENDING ON GAME-TYPE)
		if (game instanceof Game1) {

			game.playable.addListener((obs, old, newValue) -> {
				if (game.playable.get() == false) {

					String result[] = game.gameResult;
					hand1Result.setText(result[0]);
					splitHand1Result.setText(result[1]);

				} else {
					hand1Result.setText("");
					splitHand1Result.setText("");
				}
			});

			// SPLIT-HAND VISIBILITY
			game.splitMode.addListener((obs, old, newValue) -> {
				if (newValue.booleanValue() == true) {
					splitPlayerCards.setVisible(true);
				}
			});

		} else if (game instanceof Game2) {
			game.playable.addListener((obs, old, newValue) -> {
				if (game.playable.get() == false) {

					String result[] = game.gameResult;
					hand1Result.setText(result[0]);
					splitHand1Result.setText(result[1]);
					hand2Result.setText(result[2]);
					splitHand2Result.setText(result[3]);

				} else {
					hand1Result.setText("");
					splitHand1Result.setText("");
					hand2Result.setText("");
					splitHand2Result.setText("");
				}
			});

			// SPLIT-HAND VISIBILITY
			game.splitMode.addListener((obs, old, newValue) -> {
				if (newValue.booleanValue() == true) {
					if (game.hand1.getTurn().get() == true) {
						splitPlayerCards.setVisible(true);
					} else if (game.hand2.getTurn().get() == true) {
						splitPlayerCards2.setVisible(true);
					}
				}
			});

		} else if (game instanceof Game3) {
			game.playable.addListener((obs, old, newValue) -> {
				if (game.playable.get() == false) {

					String result[] = game.gameResult;
					hand1Result.setText(result[0]);
					splitHand1Result.setText(result[1]);
					hand2Result.setText(result[2]);
					splitHand2Result.setText(result[3]);
					hand3Result.setText(result[4]);
					splitHand3Result.setText(result[5]);

				} else {
					hand1Result.setText("");
					splitHand1Result.setText("");
					hand2Result.setText("");
					splitHand2Result.setText("");
					hand3Result.setText("");
					splitHand3Result.setText("");
				}
			});

			// SPLIT-HAND VISIBILITY
			game.splitMode.addListener((obs, old, newValue) -> {
				if (newValue.booleanValue() == true) {
					if (game.hand1.getTurn().get() == true) {
						splitPlayerCards.setVisible(true);
					} else if (game.hand2.getTurn().get() == true) {
						splitPlayerCards2.setVisible(true);
					} else if (game.hand3.getTurn().get() == true) {
						splitPlayerCards3.setVisible(true);
					}
				}
			});
		}

		// LISTENER FOR SESSION RUNNING
		// (IF GAME-TYPE CHANGES "SESSIONrUNNING" WILL BE
		// SET TO FALSE AND THE CURRENT BALANCE WILL BE
		// COLLECTED)
		sessionRunning.addListener((obs, old, newValue) -> {
			balance = game.playerAccount.getBalance().get();
		});

		// LISTENER FOR WHEN GAME IS NO LONGER PLAYABLE
		game.gameOver.addListener((obs, old, newValue) -> {
			if (game.gameOver.get() == true) {
				gameOver();
			}
		});

	}

	// REMOVE ALL HANDS IN ORDER TO START A NEW GAME-TYPE
	private void removeAllHands() {

		sessionRunning.set(false);

		HBox[] allHands = { dealerCards, playerCards, player2Cards, player3Cards, splitPlayerCards, splitPlayerCards2,
				splitPlayerCards3 };
		for (int i = 0; i < allHands.length; i++) {
			allHands[i].getChildren().clear();
		}

		VBox[] allHandHolders = { vbh1, vbh2, vbh3, vbsplith1, vbsplith2, vbsplith3 };
		for (int i = 0; i < allHandHolders.length; i++) {
			if (allHandHolders[i].getChildren().size() == 2) {
				allHandHolders[i].getChildren().remove(1);
			}
		}
	}

	// END THE GAME
	private void gameOver() {
		borderPane.setDisable(true);
		txtGameOver.setVisible(true);
		btnClose.setVisible(true);
		
		btnClose.setOnAction(e -> {
			Platform.exit();
		});
	}

	// OPEN NEW WINDOW THAT ASKS FOR PLAYERS NAME TO SAVE ON LEADER BOARD
	private void openSaveWindow() {

		VBox root = new VBox();

		Stage stage = new Stage();
		stage.setTitle("ENTER NAME");
		stage.setScene(new Scene(root));

		root.getStylesheets().add(getClass().getResource("mvcApplication.css").toExternalForm());

		TextField txtName = new TextField();
		Label lblEnterName = new Label("Enter your name ");
		Button btnSave = new Button("SAVE & QUIT");
		Button btnCancel = new Button("CANCEL");

		HBox hboxBtns = new HBox(btnSave, btnCancel);

		// SAVES THE SCORE TO THE LEADER BOARD AND CALLS GAME OVER
		btnSave.setOnAction(event -> {
			new LeaderBoard(new HighScore(game.playerAccount.getBalance().get(), txtName.getText()));
			stage.close();
			Platform.exit();
		});

		btnCancel.setOnAction(event -> {
			stage.close();
		});

		root.getChildren().addAll(lblEnterName, txtName, hboxBtns);

		stage.show();
	}

	// OPEN LEADER BOARD
	private void openLeaderBoardWindow() {

		LeaderBoard leaderBoard = new LeaderBoard();

		Label root = new Label(leaderBoard.getLeaderBoard());
		Stage stage = new Stage();
		stage.setTitle("LEADER BOARD");
		stage.setScene(new Scene(root));

		root.getStylesheets().add(getClass().getResource("mvcApplication.css").toExternalForm());
		root.setLineSpacing(3);
		root.setPadding(new Insets(20));
		root.setMinWidth(350);

		stage.show();
	}
}
