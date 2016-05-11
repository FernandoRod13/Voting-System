package objects;
/**
 * Vote object has the position of the candidate, the candidate and the rank for the candidate 
 * it also has the id for the ballot in which the vote is contained.
 * @author Fernando Rodriguez
 *
 */
public class Vote {	
	private int ballotID;
	private int position;
	private int candidate;
	private int rank;
	private String name;
	/**
	 * Constructs a vote object from file data. votes are the core of this project.
	 * @param ballotID id of the ballot of which this vote corresponds to
	 * @param position position of the candidate voted for
	 * @param candidate candidate the vote corresponds to
	 * @param rank rank given to the candidate
	 * @param name name of the candidate
	 */
	public Vote(int ballotID, int position, int candidate, int rank, String name){
		super();
		this.ballotID = ballotID;
		this.position = position;
		this.candidate = candidate;
		this.rank = rank;
		this.name = name;
	}
	/**
	 * Returns the id of the ballot in question
	 * @return ballot id of this vote
	 */
	public int getBallotID() {
		return ballotID;
	}
	/**
	 * Resturns Position index of the candidate
	 * @return Index of the candidates psotion
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * Returns the candidate index 
	 * @return index of the candidate
	 */
	public int getCandidate() {
		return candidate;
	}

	/**
	 * Returns the rank give tot he candidate
	 * @return rank given to the candidate
	 */
	public int getRank(){
		return rank;
	}
	/**
	 * Changes the candiddate index everytime a cnadidate is eliminated
	 */
	public void adjustCandidateIndex(){
		this.candidate = this.candidate-1;
	}
	/**
	 * Chages the rank evertime a candidate is eliminated
	 */
	public void adjustRank(){
		this.rank = this.rank - 1;
	}
	/**
	 * Returns the name of the candidate
	 * @return candidates name
	 */
	public String getName(){
		return this.name;
	}
}
