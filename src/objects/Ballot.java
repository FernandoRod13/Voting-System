package objects;

import java.util.ArrayList;

/**
 * Ballot object has an ID and an amount of votes corresponding to the ballot
 * 
 * @author Fernando Rodriguez
 *
 */
public class Ballot {
	private int id;
	private ArrayList<Vote> votes;

	/**
	 * Ballot holds a list of votes with of which all of them correspond to this
	 * ballot's id
	 * 
	 * @param id
	 *            ballot id
	 * @param vote
	 *            initial vote for this ballot
	 */
	public Ballot(int id, Vote vote) {
		super();
		this.id = id;
		votes = new ArrayList<>();
		votes.add(vote);
	}

	/**
	 * Returns the unique ballot id
	 * 
	 * @return ballot id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns an iterable collection of the votes that belong to this Ballot
	 * 
	 * @return collection of votes
	 */
	public ArrayList<Vote> getVotes() {
		return votes;
	}

	/**
	 * Sort Votes in order of ascending candidate index
	 */
	public void sortVotes() {
		votes.sort(new VoteComparator());
	}

	/**
	 * This method will remove a candidate from all of the votes belonging to
	 * this ballot and adjust the candidate index and rank
	 * 
	 * @param candidate
	 *            candidate in question
	 * @param numOfCandidates
	 *            is the Amount of available candidates
	 */
	public void removeCandidate(int candidate, int numOfCandidates) {
		votes.remove(candidate - 1);
		for (int i = candidate - 1; i < votes.size(); i++)
			votes.get(i).adjustCandidateIndex();
		rankSortVotes();
		for (int i = 0; i < votes.size(); i++) {
			if (votes.get(i).getRank() != i + 1)
				votes.get(i).adjustRank();
		}
		sortVotes();

	}

	/**
	 * sort votes in order of ascending rank
	 */
	public void rankSortVotes() {
		votes.sort(new VoteRankComparator());
	}

}
