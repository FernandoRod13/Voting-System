package objects;

import java.util.ArrayList;

/**
 * Output object holds the data that will be formated to be written as an output
 * file
 * 
 * @author Fernando Rodriguez
 *
 */
public class Output {
	private int position;
	private int ballotAmount;
	private int invlidAmount;
	private int blankBallots;
	private CandidateResult winner;
	private ArrayList<CandidateResult> results;

	/**
	 * Constructor without initial winner
	 * 
	 * @param position
	 *            Position of results
	 * @param ballotAmount
	 *            Number of ballots
	 * @param invlidAmount
	 *            Number of Invalid Ballots
	 * @param blankBallots
	 *            Number of Blank Ballots
	 * @param initResult
	 *            First candidate eliminated in counting process
	 */
	public Output(int position, int ballotAmount, int invlidAmount, int blankBallots, CandidateResult initResult) {
		super();
		this.position = position;
		this.ballotAmount = ballotAmount;
		this.invlidAmount = invlidAmount;
		this.blankBallots = blankBallots;
		results = new ArrayList<>();
		results.add(initResult);
		winner = null;

	}

	/**
	 * Constructor With initial Winner and therefore results
	 * 
	 * @param position
	 *            Position of results
	 * @param ballotAmount
	 *            Number of ballots
	 * @param invlidAmount
	 *            Number of Invalid Ballots
	 * @param blankBallots
	 *            Number of Blank Ballots
	 * @param winner
	 *            winner of the position
	 * @param result
	 *            list of remaining candidates
	 */
	public Output(int position, int ballotAmount, int invlidAmount, int blankBallots, CandidateResult winner,
			ArrayList<CandidateResult> result) {
		super();
		this.position = position;
		this.ballotAmount = ballotAmount;
		this.invlidAmount = invlidAmount;
		this.blankBallots = blankBallots;
		results = new ArrayList<>();
		for (CandidateResult c : result)
			results.add(c);
		this.winner = winner;

	}

	/**
	 * Returns the position of the output object
	 * 
	 * @return position for this data
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Returns the losers of the position
	 * 
	 * @return list of those who lost
	 */
	public ArrayList<CandidateResult> getResults() {
		return results;
	}

	/**
	 * Assigns a winner during counting process.
	 * 
	 * @param win
	 *            winner of the position.
	 */
	public void setWinner(CandidateResult win) {
		this.winner = win;
	}

	/**
	 * Returns the winner of this position. used to determine if counting
	 * process is complete
	 * 
	 * @return winner of the position
	 */
	public CandidateResult getWinner() {
		return this.winner;
	}

	/**
	 * Adds a number of candidates eliminated in the counting process. Occurs
	 * when a winner is found before eliminating all candidates
	 * 
	 * @param list
	 *            list of candidates eliminated
	 */
	public void addResults(ArrayList<CandidateResult> list) {
		for (CandidateResult c : list) {
			this.results.add(c);
		}
	}

	/**
	 * Format data to output to file
	 */
	public String toString() {
		String str = "Amount of Votes: " + ballotAmount + "\n";
		str += "Amount of Blank Ballots: " + blankBallots + "\n";
		str += "Amount of InvalidBallots: " + invlidAmount + "\n\n";
		str += "Final Resultes:\n";
		for (CandidateResult r : results) {
			str += "  ";
			str += r.toString();
		}
		str += "\nWinner:\n" + winner.toString();
		return str;

	}

}
