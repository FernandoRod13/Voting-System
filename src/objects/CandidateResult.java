package objects;

/**
 * Candidate Result object is used in output object to sotre relevate data for
 * ourput files
 * 
 * @author fernandorodriguez
 *
 */
public class CandidateResult {
	private int candidateIndex;
	private int firstRankPriorityAmount;
	private int round;
	private String name;

	/**
	 * Constructor for candidate usually a looser but can be a winner
	 * 
	 * @param candidateIndex
	 *            Index that identifies the candidate
	 * @param firstRankPriorityAmount
	 *            Amont of rank one votes
	 * @param round
	 *            round eliminated
	 * @param name
	 *            candidate name
	 */
	public CandidateResult(int candidateIndex, int firstRankPriorityAmount, int round, String name) {
		super();
		this.candidateIndex = candidateIndex;
		this.firstRankPriorityAmount = firstRankPriorityAmount;
		this.round = round;
		this.name = name;
	}

	public String toString() {
		String str = "Candidate: " + name + "\n";
		str += "Number of Rank 1 votes:" + firstRankPriorityAmount + "\n";
		str += "Round: " + round + "\n";
		return str;
	}

}
