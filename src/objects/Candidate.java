package objects;

/**
 * Candidate object class, holds an index for the candidate, index for position
 * and candidate name
 * 
 * @author Fernando Rodriguez
 *
 */
public class Candidate {
	private String name;
	private int positionsIndex;
	private int candidateIndex;

	/**
	 * Makes an Candidate object with a given name, position index and candidate
	 * index
	 * 
	 * @param name
	 *            Candidate's name
	 * @param positionsIndex
	 *            index corresponding to the Position of the candidate
	 * @param candidateIndex
	 *            index corresponding to the number that identifies the
	 *            candidate
	 */
	public Candidate(String name, int positionsIndex, int candidateIndex) {
		super();
		this.name = name;
		this.positionsIndex = positionsIndex;
		this.candidateIndex = candidateIndex;

	}
	// getters and setters

	/**
	 * Get the Candidate's name
	 * 
	 * @return name of the Candidate
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get index of the Position is running candidate is running for
	 * 
	 * @return index of the position
	 */
	public int getPositionsIndex() {
		return positionsIndex;
	}

	/**
	 * Get Candidate index for the Position
	 * 
	 * @return index of candidate
	 */
	public int getCandidateIndex() {
		return candidateIndex;
	}

	/**
	 * Reduces the index of the candidate every time someone is eliminated
	 * during counting
	 */
	public void adjustCandidateIndex() {
		this.candidateIndex = this.candidateIndex - 1;
	}

}
