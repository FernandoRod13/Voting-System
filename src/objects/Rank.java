package objects;
/**
 * Rank object essential for counting votes and deciding who wins
 * @author Fernando Rodriguez
 *
 */
public class Rank {
	private int candidateIndex;
	private int rankAmount;
	private String name;
	/**
	 * Constructs an object of type rank which stores the amount fo votes for a rank
	 * @param candidateIndex index of candidate  which the amount of votes will belong to
	 * @param rankAmount amount of votes
	 * @param name name of the candidate
	 */
	public Rank(int candidateIndex, int rankAmount, String name) {
		super();
		this.candidateIndex = candidateIndex;
		this.rankAmount = rankAmount;
		this.name = name;
	}
	/**
	 * Returns the candidate index
	 * @return index that identifies the candidate
	 */
	public int getCandidateIndex() {
		return candidateIndex;
	}
	/**
	 * Returns the amount of votes for this rank
	 * @return number of votes for the rank
	 */
	public int getRankAmount() {
		return rankAmount;
	}
	/**
	 * Add one vote for this rank
	 */
	public void incrementAmount(){
		this.rankAmount = this.rankAmount+1;
	}
	/**
	 * Returns the name of the candidate
	 * @return candidates name 
	 */
	public String getName(){
		return this.name;
	}

}
