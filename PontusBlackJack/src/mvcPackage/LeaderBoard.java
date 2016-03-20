package mvcPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An implementation of the games leader board that holds all the methods used
 * to change it.
 * 
 * @author Pontus Ellboj
 *
 */
public class LeaderBoard {

	private HighScore score;
	private List<HighScore> allScores = new ArrayList<>();;
	private TreeMap<Integer, String> tm;

	/**
	 * Empty constructor for simple instantiation.
	 */
	public LeaderBoard() {
	}

	/**
	 * Constructor used to add a new high score to the leader board (writes to
	 * file).
	 * 
	 * @param score
	 *            the new high score to be added.
	 */
	public LeaderBoard(HighScore score) {
		this.score = score;

		readLeaderBoard();
		addScore();
		sortLeaderBoard();
		writeNewLeaderBoard();
	}

	/**
	 * Get the current leader board as a String
	 * 
	 * @return the current leader board.
	 */
	public String getLeaderBoard() {

		String listOfScores = "";
		List<HighScore> scores = readLeaderBoard();

		for (int i = 0; i < scores.size(); i++) {
			listOfScores += (scores.get(i).getName() + ": " + scores.get(i).getScore()) + "\n";

		}
		System.out.println(listOfScores);

		return listOfScores;
	}

	// READ THE CURRENT LEADER BOARD FROM TEXT FILE ("highscore.txt")
	private List<HighScore> readLeaderBoard() {

		String wholeText = "";
		String[] scoreRead;

		try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {

			while (br.ready()) {
				wholeText += br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		scoreRead = wholeText.split("-");

		for (int i = 0; i < scoreRead.length - 1; i += 2) {
			allScores.add(new HighScore(Integer.parseInt(scoreRead[i]), scoreRead[i + 1]));
		}
		return allScores;
	}

	// ADD A NEW HIGH SCORE TO ARRAYLIST (allScores).
	private void addScore() {
		allScores.add(score);
	}

	// SORT LEADER BOARD.
	// 1. The leader board is contained within an arrayList that is copied to a
	// TreeMap, and thereby automatically sorted.
	// 2. The ArrayList is emptied.
	// 3. The ArrayList is Reversed.
	private void sortLeaderBoard() {

		tm = new TreeMap<>();

		for (HighScore score : allScores) {
			tm.put(score.getScore(), score.getName());
		}

		allScores.clear();

		for (Map.Entry<Integer, String> entry : tm.entrySet()) {
			allScores.add(new HighScore(entry.getKey(), entry.getValue()));
		}

		Collections.reverse(allScores);
	}

	// WRITES LEADER BOARD TO FILE ("highscore.txt").
	private void writeNewLeaderBoard() {

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("highscore.txt", false))) {

			for (int i = 0; i < allScores.size(); i++) {
				bw.write(allScores.get(i).getScore() + "-" + allScores.get(i).getName() + "-\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
