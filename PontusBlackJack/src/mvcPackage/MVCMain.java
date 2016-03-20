package mvcPackage;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * The Main class.
 * 
 * @author Pontus Ellboj
 *
 */
public class MVCMain extends Application {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) {
		try {

			URL location = this.getClass().getResource("MVCView.fxml");
			FXMLLoader loader = new FXMLLoader(location);
			Parent root = loader.load();

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("mvcApplication.css").toExternalForm());
			primaryStage.setTitle("Pontus Blackjack");
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Launch Blackjack application.
	 * 
	 * @param args
	 *            command-line arguments.
	 */
	public static void main(String[] args) {

		launch(args);
	}
}
