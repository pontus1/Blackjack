package mvcPackage;

/**
 * An implementation of a high score.
 * 
 * @author Pontus Ellboj
 *
 */
public class HighScore {

	private int score;
	private String name;

	/**
	 * Class constructor.
	 * 
	 * @param score
	 *            the score to create the high score with
	 * @param name
	 *            the name of the player who got the score
	 */
	public HighScore(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}

	public String getName() {
		return name;
	}

}
